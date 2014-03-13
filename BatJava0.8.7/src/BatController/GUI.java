package BatController;

import gnu.io.NoSuchPortException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

/**
 * 
 * Creates a GUI which allows the user to connect and control the BAT.
 * @author Sean Murphy
 * @author John Miles-Groves
 * @author Odysseus McNally
 * @author Thomas Robins
 * @param <Picture>
 *
 */

public class GUI {

	public String portChoice;
	boolean serial;
	boolean wifi;
	//Serial connectivity class
	final BatSerialConn serialClass;

	//Compass
	JPanel pnlCompass;
	JLabel compassImg;

	//Robot Serial Connection
	JPanel pnlRobConSerial;
	JLabel lblRobConSerialPort;
	JComboBox<String> cmbPortList;
	JButton btnSerialConnect;

	//Robot Control Panel
	JPanel pnlRobotControl;
	JButton btnRobControlAuto;
	JButton btnRobControlFwdStep;
	JButton btnRobControlManual;
	JButton btnRobControlRight;
	JButton btnRobControlReverseStep;
	JButton btnRobControlLeft;
	JButton btnRobControlStop;
	JButton btnDesktopRealTimeMap;

	GuiSensorReadings sensorWindow;
	
	//Misc/Unimplemented
	final JFrame windowFrame;
	final JButton parkButton;
	private JButton btnLaptopRealTimeMap;
	private JButton btnSensorReadingVIew;

	public GUI() {

		serialClass = new BatSerialConn();

		serial = false;
		wifi = false;

		windowFrame = new JFrame("BAT-III Controller");
		parkButton = new JButton("Park");

		windowFrame.setSize(315, 585); // Main window container size (in pixels) change this to resize whole GUI
		windowFrame.getContentPane().setLayout(null);

		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setVisible(true);

		sensorWindow = new GuiSensorReadings();

		setupCompass();		
		setupRobotConnectionPanel();				
		setupRobotControls();
		setupRealTimeMapButton();	

		setupActionListeners();

		windowFrame.setResizable(false);


	}

	public void setupActionListeners() {
		//Real Time Map Button Action Listener
		btnDesktopRealTimeMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				new mapGUI(serialClass);	
			}
		});

		//Stop Button Override Action Listener
		btnRobControlStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				serialClass.setNextCommand("S");
			}
		});

		//Robot Control Manual Buttons
		//Forward Button Action Listener
		btnRobControlFwdStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				serialClass.setNextCommand("^"); // one of these needs to change 
			}
		});

		//Left Button Action Listener
		btnRobControlLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				serialClass.setNextCommand("<");
			}
		});

		//Reverse Button Action Listener
		btnRobControlReverseStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				serialClass.setNextCommand("V");
			}
		});

		//Right Button Action Listener
		btnRobControlRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				serialClass.setNextCommand(">");
			}
		});

		//Robot Control Buttons
		//Manual Control Button Action Listener
		btnRobControlManual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				serialClass.setNextCommand("M");
			}
		});

		//Robot Autopilot Button Action Listener
		btnRobControlAuto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				serialClass.setNextCommand("A");
			}
		});

		//Robot Control Serial Connect Button Action Listener
		btnSerialConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				serialClass.connectButton();
			}
		});

		//Sensor reading view action listener
		btnSensorReadingVIew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sensorWindow.showSensorWindow();
			}
		});


		//Combo box event listener
		cmbPortList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				portChoice = (String) cmbPortList.getSelectedItem();//Get string from combo box selection
				try {		

					serialClass.setChosenPort(portChoice);//Set it to chosen port
				}
				catch (NoSuchPortException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setupRealTimeMapButton() {
		//Real Time Map Button
		btnDesktopRealTimeMap = new JButton("Desktop Map");
		btnDesktopRealTimeMap.setBounds(22, 261, 130, 23);
		windowFrame.getContentPane().add(btnDesktopRealTimeMap);

		//Laptop real time map - NOT IMPLEMENTED YET
		btnLaptopRealTimeMap = new JButton("Laptop Map");
		btnLaptopRealTimeMap.setBounds(169, 261, 130, 23);
		windowFrame.getContentPane().add(btnLaptopRealTimeMap);

		//Sensor reading view button
		btnSensorReadingVIew = new JButton("Sensor Reading View");
		btnSensorReadingVIew.setBounds(22, 293, 277, 23);
		windowFrame.getContentPane().add(btnSensorReadingVIew);
	}

	private void setupRobotControls() {
		//Robot Control panel
		pnlRobotControl = new JPanel();
		pnlRobotControl.setBorder(new TitledBorder(null, "Robot Control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRobotControl.setBounds(22, 113, 277, 137);
		windowFrame.getContentPane().add(pnlRobotControl);
		pnlRobotControl.setLayout(null);

		//Robot Control Autopilot Button
		btnRobControlAuto = new JButton("Auto");
		btnRobControlAuto.setBounds(10, 22, 76, 23);
		pnlRobotControl.add(btnRobControlAuto);

		//Robot Control Forward Step Button
		btnRobControlFwdStep = new JButton("^");
		btnRobControlFwdStep.setBounds(98, 21, 76, 24);
		pnlRobotControl.add(btnRobControlFwdStep);

		//Robot Control Manual Pilot Button
		btnRobControlManual = new JButton("Manual");
		btnRobControlManual.setBounds(184, 21, 76, 24);
		pnlRobotControl.add(btnRobControlManual);

		//Robot Control Right Button
		btnRobControlRight = new JButton(">");
		btnRobControlRight.setBounds(183, 56, 76, 24);
		pnlRobotControl.add(btnRobControlRight);

		//Robot Control Reverse Step Button
		btnRobControlReverseStep = new JButton("V");
		btnRobControlReverseStep.setBounds(98, 56, 76, 24);
		pnlRobotControl.add(btnRobControlReverseStep);

		//Robot Control Left Button
		btnRobControlLeft = new JButton("<");
		btnRobControlLeft.setBounds(10, 56, 76, 24);
		pnlRobotControl.add(btnRobControlLeft);

		//Robot Control Stop Override Button		
		btnRobControlStop = new JButton("Stop");
		btnRobControlStop.setBounds(20, 91, 240, 24);
		pnlRobotControl.add(btnRobControlStop);
	}

	private void setupRobotConnectionPanel() {		
		//Robot Connect Serial panel
		pnlRobConSerial = new JPanel();
		pnlRobConSerial.setBounds(22, 21, 277, 85);
		windowFrame.getContentPane().add(pnlRobConSerial);
		pnlRobConSerial.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Robot Serial Connection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRobConSerial.setLayout(null);

		//Robot Connection Serial Port list label
		lblRobConSerialPort = new JLabel("Serial Port");
		lblRobConSerialPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblRobConSerialPort.setBounds(10, 40, 60, 29);
		pnlRobConSerial.add(lblRobConSerialPort);		

		//Robot Connection Serial Port List combo box		
		cmbPortList = new JComboBox<String>();
		cmbPortList.setBounds(80, 40, 60, 29);
		pnlRobConSerial.add(cmbPortList);		

		//Robot Connection Serial connect button
		btnSerialConnect = new JButton("Connect");
		btnSerialConnect.setBounds(150, 40, 97, 29);
		pnlRobConSerial.add(btnSerialConnect);

		//Populate combo box
		for(int i=0; i < serialClass.getCommPorts().size()  ; i++) {
			cmbPortList.addItem(serialClass.getCommPorts().get(i));
		}		
	}


	private void setupCompass() {
		//Compass
		//Compass panel
		pnlCompass = new JPanel();
		pnlCompass.setBorder(new TitledBorder(null, "Compass", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlCompass.setBounds(22, 327, 200, 218);
		windowFrame.getContentPane().add(pnlCompass);

		//windowFrame.setComponentZOrder( compass, 0 );
		//compass.setLayout(null);
		//JLayeredPane compassImgCon = new JLayeredPane();
		//compassImgCon.setBounds(313, 273, -289, -251);
		//compass.add(compassImgCon);
		//compass.add(compassImgCon, 0);// should move layered pane to the front on JPanel its in

		compassImg = new JLabel("");
		compassImg.setBounds(0, -195, -239, 195);
		//currently doesn't work when exporting to jar
		compassImg.setIcon(new ImageIcon(GUI.class.getResource("images/compassLayerResize.png")));
		pnlCompass.add(compassImg, 0);
		// end compass
	}

}