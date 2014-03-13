package BatController;

import gnu.io.*; // RXTX

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;

public class BatSerialConn implements SerialPortEventListener {

	/**
	 * @param args
	 */
	
	private String chosenPort;
	boolean foundPorts;
	private SerialPort serialPort;
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	private PrintStream p;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private int DATA_RATE = 9600;
	public ArrayList<String> availablePorts = new ArrayList<String>();

	CommPortIdentifier serialPortId;
	// static CommPortIdentifier sSerialPortId;

	Enumeration enumComm;

	// String for next message
	private String nextMessageToRobot;

	// String to hold compass angle
	private String compassAngle;

	// String to hold signal strength
	private String signalStrength;

	// Strings to hold IR readings
	private String centreIRSensor;
	private String rightIRSensor;
	private String leftIRSensor;

	// Strings to hold sonar readings
	private String leftFrontSonarInches;
	private String centerSonarInches;
	private String rightFrontSonarInches;
	private String backSonarInches;

	// Declared but not yet used.
	// private String leftSideSonarInches;
	// private String rightSideSonarInches;

	public void connectButton() {

		try {

			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) serialPortId.open(this.getClass()
					.getName(), TIME_OUT);

			System.out.print("my name is " + serialPort.getName());

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(
					serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			p = new PrintStream(output);

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			// need to un swallow this
		}
	}

	private void upDateUI( String message ) {

		// This splits the param around commas, storing each substring as
		// objects in an array

		String[] data = message.split(",");
		// Switch on first index of array
		switch ( data[0] ) {
		case "S":
			// Assign values from robot to strings for display
			sensorDataGetSet.setcenterSonarInches( centerSonarInches = data[1] );
			sensorDataGetSet.setFRSonarInches( rightFrontSonarInches = data[2] );
			sensorDataGetSet.setFLSonarInches( leftFrontSonarInches = data[3] );
			sensorDataGetSet.setbackSonarInches( backSonarInches = data[4] );
			sensorDataGetSet.setcompassAngle( compassAngle = data[5] ) ;
			signalStrength = data[6];// should be 5 ofc but compass broke
			sensorDataGetSet.setcentreIRSensor( centreIRSensor = data[7] );
			rightIRSensor = data[8];
			leftIRSensor = data[9];
			// break;
		}

		// Centre Sonar Panel
		GuiSensorReadings.txtCenterFrontSonarDistInch.setText(sensorDataGetSet.getCentSonarInches());
		GuiSensorReadings.txtCenterFrontSonarDistCM.setText(Double.toString((Double.parseDouble(sensorDataGetSet.getCentSonarInches()) * 2.54)));
		
		// Left Front Sonar Panel
		GuiSensorReadings.txtLeftFrontSonarDistInch.setText(leftFrontSonarInches);
		GuiSensorReadings.txtLeftFrontSonarDistCM.setText(Double.toString((Double.parseDouble(sensorDataGetSet.getFLSonarInches()) * 2.54)));

		// Left Side Sonar Panel
		GuiSensorReadings.txtLeftSideSonarDistInch.setText(compassAngle);
		// GUI.txtLeftSideSonarDistCM.setText(Double.toString((Double.parseDouble(leftSideSonarInches) * 2.54)));

		// Right Front Sonar Panel
		GuiSensorReadings.txtRightFrontSonarDistInch.setText(rightFrontSonarInches);
		GuiSensorReadings.txtRightFrontSonarDistCM.setText(Double.toString((Double.parseDouble(rightFrontSonarInches) * 2.54)));

		// Right Side Sonar Panel
		// GUI.txtRightSideSonarDistInch.setText(rightSideSonarInches);
		// GUI.txtRightSideSonarDistCM.setText(Double.toString((Double.parseDouble(rightSideSonarInches) * 2.54)));

		// Rear Sonar Panel
		GuiSensorReadings.txtRearSonarDistInch.setText(sensorDataGetSet.getbackSonarInches());
		GuiSensorReadings.txtRearSonarDistCM.setText(Double.toString((Double.parseDouble(sensorDataGetSet.getbackSonarInches()) * 2.54)));

		// IR Sensor Panel
		GuiSensorReadings.txtFrontLeftIR.setText(Double.toString((Double.parseDouble(leftIRSensor) * 2.54)));
		GuiSensorReadings.txtFrontCenterIR.setText(Double.toString((Double.parseDouble(sensorDataGetSet.getcentreIRSensor()) * 2.54)));
		GuiSensorReadings.txtFrontRightIR.setText(Double.toString((Double.parseDouble(rightIRSensor) * 2.54)));

	}

	public ArrayList<String> setCommPorts() {

		enumComm = CommPortIdentifier.getPortIdentifiers();
		while (enumComm.hasMoreElements()) {
			serialPortId = (CommPortIdentifier) enumComm.nextElement();
			if (serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				availablePorts.add(serialPortId.getName());

			}
		}
		return availablePorts;
	}

	public ArrayList<String> getCommPorts() {

		if (!foundPorts)
			setCommPorts();
		foundPorts = true;
		return availablePorts;
	}

	public void setChosenPort(String thePort) throws NoSuchPortException {

		chosenPort = thePort;
		serialPortId = CommPortIdentifier.getPortIdentifier(chosenPort);
		System.out.print("the chosen port is " + serialPortId.getName());
	}

	public void setNextCommand(String nextCommand) {

		nextMessageToRobot = nextCommand;
		System.out.print(nextCommand);
	}

	@Override
	public void serialEvent(SerialPortEvent oEvent) {

		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {

			try {

				Thread.sleep(50);
				String inputLine = input.readLine();
				p.print(nextMessageToRobot);
				//System.out.println(inputLine);
				nextMessageToRobot = "N";// Set next message back to default.
				upDateUI(inputLine);

			} catch (Exception e) {
				
				System.err.println(e.toString());
			}
		}
	}
}