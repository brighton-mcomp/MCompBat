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
import javax.swing.WindowConstants;
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

public class GuiSensorReadings extends JFrame{

	public String portChoice;
	boolean serial;
	boolean wifi;
	//Serial connectivity class
	final BatSerialConn serialClass;

	//Left Side Sonar Displays
	static JTextField txtLeftSideSonarName;
	static JTextField txtLeftSideSonarAddress;
	static JTextField txtLeftSideSonarDistInch;
	static JTextField txtLeftSideSonarDistCM;

	//Left Front Sonar Displays
	static JTextField txtLeftFrontSonarAddress;
	static JTextField txtLeftFrontSonarDistInch;
	static JTextField txtLeftFrontSonarDistCM;
	static JTextField txtLeftFrontSonarName;

	//Centre Front Sonar Displays
	static JTextField txtCenterFrontSonarName;
	static JTextField txtCenterFrontSonarAddress;
	static JTextField txtCenterFrontSonarDistInch;
	static JTextField txtCenterFrontSonarDistCM;

	//Right Front Sonar Displays
	static JTextField txtRightFrontSonarName;
	static JTextField txtRightFrontSonarAddress;
	static JTextField txtRightFrontSonarDistInch;
	static JTextField txtRightFrontSonarDistCM;

	//Right Side Sonar Displays
	static JTextField txtRightSideSonarName;
	static JTextField txtRightSideSonarAddress;
	static JTextField txtRightSideSonarDistInch;
	static JTextField txtRightSideSonarDistCM;

	//Rear Sonar Displays
	static JTextField txtRearSonarDistInch;
	static JTextField txtRearSonarDistCM;
	static JTextField txtRearSonarAddress;
	static JTextField txtRearSonarName;

	//IR Sensors
	static JTextField txtFrontLeftIR;
	static JTextField txtFrontCenterIR;
	static JTextField txtFrontRightIR;
	JLabel compassImg;

	//Misc/Unimplemented
	final JFrame windowFrame;


	public GuiSensorReadings() {

		serialClass = new BatSerialConn();

		serial = false;
		wifi = false;

		windowFrame = new JFrame("Sensor Output");

		windowFrame.setSize(690, 390); // Main window container size (in pixels) change this to resize whole GUI
		windowFrame.getContentPane().setLayout(null);

		windowFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//windowFrame.setVisible(true);
		setupIR();		
		setupLeftFrontSonar();		
		setupLeftSideSonar();
		setupCentreFrontSonar();		
		setupRearSonar();		
		setupRightFrontSonar();
		setupRightSideSonar();

		windowFrame.setResizable(false);
	}
	
	public void showSensorWindow()
	{
		windowFrame.setVisible(true);
	}


	private void setupRightSideSonar() {
		//Right Side Sonar Sensor
		JPanel pnlRightSideSonar = new JPanel();
		pnlRightSideSonar.setLayout(null);
		pnlRightSideSonar.setBorder(new TitledBorder(null, "Right Side Sonar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRightSideSonar.setBounds(458, 223, 218, 137);
		windowFrame.getContentPane().add(pnlRightSideSonar);

		//Right Side Sonar Name Label
		JLabel lblRightSideSonarName = new JLabel("Name");
		lblRightSideSonarName.setBounds(10, 25, 46, 14);
		pnlRightSideSonar.add(lblRightSideSonarName);

		//Right Side Sonar Name Text Field
		txtRightSideSonarName = new JTextField();
		txtRightSideSonarName.setEditable(false);
		txtRightSideSonarName.setColumns(10);
		txtRightSideSonarName.setBounds(122, 22, 86, 20);
		pnlRightSideSonar.add(txtRightSideSonarName);

		//Right Side Sonar Address Label
		JLabel lblRightSideSonarAddress = new JLabel("Address");
		lblRightSideSonarAddress.setBounds(10, 50, 90, 14);
		pnlRightSideSonar.add(lblRightSideSonarAddress);

		//Right Side Sonar Address Text Field
		txtRightSideSonarAddress = new JTextField();
		txtRightSideSonarAddress.setEditable(false);
		txtRightSideSonarAddress.setColumns(10);
		txtRightSideSonarAddress.setBounds(122, 47, 86, 20);
		pnlRightSideSonar.add(txtRightSideSonarAddress);

		//Right Side Sonar Distance Inches Label
		JLabel lblRightSideSonarDistInch = new JLabel("Distance (inch)");
		lblRightSideSonarDistInch.setBounds(10, 75, 90, 14);
		pnlRightSideSonar.add(lblRightSideSonarDistInch);

		//Right Side Sonar Distance Inches Text Field
		txtRightSideSonarDistInch = new JTextField();
		txtRightSideSonarDistInch.setEditable(false);
		txtRightSideSonarDistInch.setColumns(10);
		txtRightSideSonarDistInch.setBounds(122, 72, 86, 20);
		pnlRightSideSonar.add(txtRightSideSonarDistInch);

		//Right Side Sonar Distance CM Label
		JLabel lblRightSideSonarDistCM = new JLabel("Distance (cm)");
		lblRightSideSonarDistCM.setBounds(10, 100, 90, 14);
		pnlRightSideSonar.add(lblRightSideSonarDistCM);

		//Right Side Sonar Distance CM Text Field
		txtRightSideSonarDistCM = new JTextField();
		txtRightSideSonarDistCM.setEditable(false);
		txtRightSideSonarDistCM.setColumns(10);
		txtRightSideSonarDistCM.setBounds(122, 97, 86, 20);
		pnlRightSideSonar.add(txtRightSideSonarDistCM);
	}

	private void setupRightFrontSonar() {
		//Right Front Sonar Sensor
		JPanel pnlRightFrontSonar = new JPanel();
		pnlRightFrontSonar.setLayout(null);
		pnlRightFrontSonar.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Right Front Sonar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRightFrontSonar.setBounds(458, 75, 218, 137);
		windowFrame.getContentPane().add(pnlRightFrontSonar);

		//Right Front Sonar Name Label
		JLabel lblRightFrontSonarName = new JLabel("Name");
		lblRightFrontSonarName.setBounds(10, 25, 46, 14);
		pnlRightFrontSonar.add(lblRightFrontSonarName);

		//Right Front Sonar Name Text Field
		txtRightFrontSonarName = new JTextField();
		txtRightFrontSonarName.setEditable(false);
		txtRightFrontSonarName.setColumns(10);
		txtRightFrontSonarName.setBounds(122, 22, 86, 20);
		pnlRightFrontSonar.add(txtRightFrontSonarName);

		//Right Front Sonar Address Label
		JLabel lblRightFrontSonarAddress = new JLabel("Address");
		lblRightFrontSonarAddress.setBounds(10, 50, 76, 14);
		pnlRightFrontSonar.add(lblRightFrontSonarAddress);

		//Right Front Sonar Address Text Field
		txtRightFrontSonarAddress = new JTextField();
		txtRightFrontSonarAddress.setEditable(false);
		txtRightFrontSonarAddress.setColumns(10);
		txtRightFrontSonarAddress.setBounds(122, 47, 86, 20);
		pnlRightFrontSonar.add(txtRightFrontSonarAddress);

		//Right Front Sonar Distance Inches Label
		JLabel lblRightFrontSonarDistInch = new JLabel("Distance (inch)");
		lblRightFrontSonarDistInch.setBounds(10, 76, 88, 14);
		pnlRightFrontSonar.add(lblRightFrontSonarDistInch);

		//Right Front Sonar Distance Inches Text Field
		txtRightFrontSonarDistInch = new JTextField();
		txtRightFrontSonarDistInch.setEditable(false);
		txtRightFrontSonarDistInch.setColumns(10);
		txtRightFrontSonarDistInch.setBounds(122, 73, 86, 20);
		pnlRightFrontSonar.add(txtRightFrontSonarDistInch);

		//Right Front Sonar Distance CM Label
		JLabel lblRightFrontSonarDistCM = new JLabel("Distance (cm)");
		lblRightFrontSonarDistCM.setBounds(10, 101, 88, 14);
		pnlRightFrontSonar.add(lblRightFrontSonarDistCM);

		//Right Front Sonar Distance CM Text Field
		txtRightFrontSonarDistCM = new JTextField();
		txtRightFrontSonarDistCM.setEditable(false);
		txtRightFrontSonarDistCM.setColumns(10);
		txtRightFrontSonarDistCM.setBounds(122, 98, 86, 20);
		pnlRightFrontSonar.add(txtRightFrontSonarDistCM);
	}

	private void setupRearSonar() {
		//Rear Sonar Sensor
		//Rear Sonar Panel
		JPanel pnlRearSonar = new JPanel();
		pnlRearSonar.setLayout(null);
		pnlRearSonar.setBorder(new TitledBorder(null, "Rear Sonar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRearSonar.setBounds(228, 223, 220, 137);
		windowFrame.getContentPane().add(pnlRearSonar);

		//Rear Sonar Name Label
		JLabel lblRearSonarName = new JLabel("Name");
		lblRearSonarName.setBounds(10, 25, 46, 14);
		pnlRearSonar.add(lblRearSonarName);

		//Rear Sonar Name Text Field
		txtRearSonarName = new JTextField();
		txtRearSonarName.setEditable(false);
		txtRearSonarName.setBounds(122, 22, 86, 20);
		pnlRearSonar.add(txtRearSonarName);
		txtRearSonarName.setColumns(10);

		//Rear Sonar Address Label
		JLabel lblRearSonarAddress = new JLabel("Address");
		lblRearSonarAddress.setBounds(10, 50, 46, 14);
		pnlRearSonar.add(lblRearSonarAddress);

		//Rear Sonar Address Text Field
		txtRearSonarAddress = new JTextField();
		txtRearSonarAddress.setEditable(false);
		txtRearSonarAddress.setBounds(122, 47, 86, 20);
		pnlRearSonar.add(txtRearSonarAddress);
		txtRearSonarAddress.setColumns(10);

		//Rear Sonar Distance Inches Label
		JLabel lblRearSonarDistInch = new JLabel("Distance (inch)");
		lblRearSonarDistInch.setBounds(10, 76, 102, 14);
		pnlRearSonar.add(lblRearSonarDistInch);

		//Rear Sonar Distance Inches Text Field
		txtRearSonarDistInch = new JTextField();
		txtRearSonarDistInch.setEditable(false);
		txtRearSonarDistInch.setColumns(10);
		txtRearSonarDistInch.setBounds(122, 73, 86, 20);
		pnlRearSonar.add(txtRearSonarDistInch);

		//Rear Sonar Distance CM Label
		JLabel lblRearSonarDistCM = new JLabel("Distance (cm)");
		lblRearSonarDistCM.setBounds(10, 101, 86, 14);
		pnlRearSonar.add(lblRearSonarDistCM);

		//Rear Sonar Distance CM Text Field
		txtRearSonarDistCM = new JTextField();
		txtRearSonarDistCM.setEditable(false);
		txtRearSonarDistCM.setColumns(10);
		txtRearSonarDistCM.setBounds(122, 98, 86, 20);
		pnlRearSonar.add(txtRearSonarDistCM);
	}

	private void setupCentreFrontSonar() {
		//Centre Front Sonar Sensor
		JPanel pnlCenterFrontSonar = new JPanel();
		pnlCenterFrontSonar.setLayout(null);
		pnlCenterFrontSonar.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Center Front Sonar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlCenterFrontSonar.setBounds(230, 75, 218, 137);
		windowFrame.getContentPane().add(pnlCenterFrontSonar);

		//Centre Front Sonar Name Label
		JLabel lblCenterFrontSonarName = new JLabel("Name");
		lblCenterFrontSonarName.setBounds(10, 25, 46, 14);
		pnlCenterFrontSonar.add(lblCenterFrontSonarName);

		//Centre Front Sonar Name Text Field
		txtCenterFrontSonarName = new JTextField();
		txtCenterFrontSonarName.setEditable(false);
		txtCenterFrontSonarName.setColumns(10);
		txtCenterFrontSonarName.setBounds(122, 22, 86, 20);
		pnlCenterFrontSonar.add(txtCenterFrontSonarName);

		//Centre Front Sonar Address Label		
		JLabel lblCenterFrontSonarAddress = new JLabel("Address");
		lblCenterFrontSonarAddress.setBounds(10, 50, 86, 14);
		pnlCenterFrontSonar.add(lblCenterFrontSonarAddress);

		//Centre Front Sonar Address Text Field
		txtCenterFrontSonarAddress = new JTextField();
		txtCenterFrontSonarAddress.setEditable(false);
		txtCenterFrontSonarAddress.setColumns(10);
		txtCenterFrontSonarAddress.setBounds(122, 47, 86, 20);
		pnlCenterFrontSonar.add(txtCenterFrontSonarAddress);

		//Centre Front Sonar Distance Inches Label
		JLabel lblCenterFrontSonarDistInch = new JLabel("Distance (inch)");
		lblCenterFrontSonarDistInch.setBounds(10, 76, 86, 14);
		pnlCenterFrontSonar.add(lblCenterFrontSonarDistInch);

		//Centre Front Sonar Distance Inches Text Field
		txtCenterFrontSonarDistInch = new JTextField();
		txtCenterFrontSonarDistInch.setEditable(false);
		txtCenterFrontSonarDistInch.setColumns(10);
		txtCenterFrontSonarDistInch.setBounds(122, 73, 86, 20);
		pnlCenterFrontSonar.add(txtCenterFrontSonarDistInch);

		//Centre Front Sonar Distance CM Label
		JLabel lblCenterFrontSonarDistCM = new JLabel("Distance (cm)");
		lblCenterFrontSonarDistCM.setBounds(10, 101, 82, 14);
		pnlCenterFrontSonar.add(lblCenterFrontSonarDistCM);

		//Centre Front Sonar Distance CM Text Field
		txtCenterFrontSonarDistCM = new JTextField();
		txtCenterFrontSonarDistCM.setEditable(false);
		txtCenterFrontSonarDistCM.setColumns(10);
		txtCenterFrontSonarDistCM.setBounds(122, 98, 86, 20);
		pnlCenterFrontSonar.add(txtCenterFrontSonarDistCM);
	}

	private void setupLeftSideSonar() {
		//Left Side Sonar Sensors
		JPanel pnlLeftSideSonar = new JPanel();
		pnlLeftSideSonar.setLayout(null);
		pnlLeftSideSonar.setBorder(new TitledBorder(null, "Left Side Sonar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlLeftSideSonar.setBounds(10, 223, 208, 137);
		windowFrame.getContentPane().add(pnlLeftSideSonar);

		//Left Side Sonar Name Label
		JLabel lblLeftSideSonarName = new JLabel("Name");
		lblLeftSideSonarName.setBounds(10, 25, 46, 14);
		pnlLeftSideSonar.add(lblLeftSideSonarName);

		//Left Side Sonar Name Text Field
		txtLeftSideSonarName = new JTextField();
		txtLeftSideSonarName.setEditable(false);
		txtLeftSideSonarName.setColumns(10);
		txtLeftSideSonarName.setBounds(102, 22, 86, 20);
		pnlLeftSideSonar.add(txtLeftSideSonarName);

		//Left Side Sonar Address Label
		JLabel lblLeftSideSonarAddress = new JLabel("Address");
		lblLeftSideSonarAddress.setBounds(10, 50, 82, 14);
		pnlLeftSideSonar.add(lblLeftSideSonarAddress);

		//Left Side Sonar Address Text Field
		txtLeftSideSonarAddress = new JTextField();
		txtLeftSideSonarAddress.setEditable(false);
		txtLeftSideSonarAddress.setColumns(10);
		txtLeftSideSonarAddress.setBounds(102, 47, 86, 20);
		pnlLeftSideSonar.add(txtLeftSideSonarAddress);

		//Left Side Sonar Distance Inches Label
		JLabel lblLeftSideSonarDistInch = new JLabel("Distance (inch)");
		lblLeftSideSonarDistInch.setBounds(10, 76, 86, 14);
		pnlLeftSideSonar.add(lblLeftSideSonarDistInch);

		//Left Side Sonar Distance Inches Text Field
		txtLeftSideSonarDistInch = new JTextField();
		txtLeftSideSonarDistInch.setEditable(false);
		txtLeftSideSonarDistInch.setColumns(10);
		txtLeftSideSonarDistInch.setBounds(102, 73, 86, 20);
		pnlLeftSideSonar.add(txtLeftSideSonarDistInch);

		//Left Side Sonar Distance CM Label
		JLabel lblLeftSideSonarDistCM = new JLabel("Distance (cm)");
		lblLeftSideSonarDistCM.setBounds(10, 101, 86, 14);
		pnlLeftSideSonar.add(lblLeftSideSonarDistCM);

		//Left Side Sonar Distance CM Text Field
		txtLeftSideSonarDistCM = new JTextField();
		txtLeftSideSonarDistCM.setEditable(false);
		txtLeftSideSonarDistCM.setColumns(10);
		txtLeftSideSonarDistCM.setBounds(102, 98, 86, 20);
		pnlLeftSideSonar.add(txtLeftSideSonarDistCM);
	}

	private void setupLeftFrontSonar() {
		//Left Front Sonar Sensor 
		JPanel pnlLeftFrontSonar = new JPanel();
		pnlLeftFrontSonar.setLayout(null);
		pnlLeftFrontSonar.setBorder(new TitledBorder(null, "Left Front Sonar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlLeftFrontSonar.setBounds(10, 75, 210, 137);
		windowFrame.getContentPane().add(pnlLeftFrontSonar);

		//Left Front Sonar Sensor Name Label
		JLabel lblLeftFrontSonarName = new JLabel("Name");
		lblLeftFrontSonarName.setBounds(10, 25, 46, 14);
		pnlLeftFrontSonar.add(lblLeftFrontSonarName);

		//Left Front Sonar Sensor Name Text Field
		txtLeftFrontSonarName = new JTextField();
		txtLeftFrontSonarName.setEditable(false);
		txtLeftFrontSonarName.setColumns(10);
		txtLeftFrontSonarName.setBounds(112, 22, 86, 20);
		pnlLeftFrontSonar.add(txtLeftFrontSonarName);

		//Left Front Sonar Sensor Address Label
		JLabel lblLeftFrontSonarAddress = new JLabel("Address");
		lblLeftFrontSonarAddress.setBounds(10, 50, 86, 14);
		pnlLeftFrontSonar.add(lblLeftFrontSonarAddress);

		//Left Front Sonar Sensor Address Text Field
		txtLeftFrontSonarAddress = new JTextField();
		txtLeftFrontSonarAddress.setEditable(false);
		txtLeftFrontSonarAddress.setColumns(10);
		txtLeftFrontSonarAddress.setBounds(112, 47, 86, 20);
		pnlLeftFrontSonar.add(txtLeftFrontSonarAddress);

		//Left Front Sonar Sensor Distance Inches Label
		JLabel lblLeftFrontSonarDistInch = new JLabel("Distance (inch)");
		lblLeftFrontSonarDistInch.setBounds(10, 76, 92, 14);
		pnlLeftFrontSonar.add(lblLeftFrontSonarDistInch);

		//Left Front Sonar Sensor Distance Inches Text Field
		txtLeftFrontSonarDistInch = new JTextField();
		txtLeftFrontSonarDistInch.setText((String) null);
		txtLeftFrontSonarDistInch.setEditable(false);
		txtLeftFrontSonarDistInch.setColumns(10);
		txtLeftFrontSonarDistInch.setBounds(112, 73, 86, 20);
		pnlLeftFrontSonar.add(txtLeftFrontSonarDistInch);

		//Left Front Sonar Sensor Distance CM Text Field 
		txtLeftFrontSonarDistCM = new JTextField();
		txtLeftFrontSonarDistCM.setEditable(false);
		txtLeftFrontSonarDistCM.setColumns(10);
		txtLeftFrontSonarDistCM.setBounds(112, 98, 86, 20);
		pnlLeftFrontSonar.add(txtLeftFrontSonarDistCM);

		//Left Front Sonar Sensor Distance CM Label
		JLabel lblLeftFrontSonarDistCM = new JLabel("Distance (cm)");
		lblLeftFrontSonarDistCM.setBounds(10, 101, 92, 14);
		pnlLeftFrontSonar.add(lblLeftFrontSonarDistCM);
	}

	private void setupIR() {
		//Front IR Sensors Panel 
		JPanel pnlFrontIRSensor = new JPanel();
		pnlFrontIRSensor.setBorder(new TitledBorder(null, "Front Infrared Sensors (cm)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlFrontIRSensor.setBounds(66, 11, 526, 53);
		windowFrame.getContentPane().add(pnlFrontIRSensor);
		pnlFrontIRSensor.setLayout(null);

		//IR Sensors
		//Front Left IR Sensor text field
		txtFrontLeftIR = new JTextField();
		txtFrontLeftIR.setEditable(false);
		txtFrontLeftIR.setBounds(62, 23, 94, 19);
		pnlFrontIRSensor.add(txtFrontLeftIR);
		txtFrontLeftIR.setColumns(10);

		//Front Left IR Label
		JLabel lblFrontIRLeft = new JLabel("Left");
		lblFrontIRLeft.setHorizontalAlignment(SwingConstants.CENTER);
		lblFrontIRLeft.setBounds(29, 27, 28, 12);
		pnlFrontIRSensor.add(lblFrontIRLeft);

		//Front Center IR Sensor text field
		txtFrontCenterIR = new JTextField();
		txtFrontCenterIR.setEditable(false);
		txtFrontCenterIR.setBounds(240, 23, 86, 19);
		pnlFrontIRSensor.add(txtFrontCenterIR);
		txtFrontCenterIR.setColumns(10);

		//Front Center IR Labels
		JLabel lblFrontIRCenter = new JLabel("Center");
		lblFrontIRCenter.setHorizontalAlignment(SwingConstants.CENTER);
		lblFrontIRCenter.setBounds(197, 27, 46, 12);
		pnlFrontIRSensor.add(lblFrontIRCenter);

		//Front Right IR Sensor text field
		txtFrontRightIR = new JTextField();
		txtFrontRightIR.setEditable(false);
		txtFrontRightIR.setBounds(415, 23, 86, 19);
		pnlFrontIRSensor.add(txtFrontRightIR);
		txtFrontRightIR.setColumns(10);

		//Front Right IR Sensor label
		JLabel lblFrontIRRight = new JLabel("Right");
		lblFrontIRRight.setHorizontalAlignment(SwingConstants.CENTER);
		lblFrontIRRight.setBounds(374, 26, 46, 14);
		pnlFrontIRSensor.add(lblFrontIRRight);
	}

}