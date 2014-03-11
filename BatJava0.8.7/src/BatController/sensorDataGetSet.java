package BatController;

public class sensorDataGetSet {

	private static String nextMessageToRobot;

	// String to hold compass angle
	private static String compassAngle;

	// String to hold signal strength
	private static String signalStrength;

	// Strings to hold IR readings
	private static String centreIRSensor;
	private static String rightIRSensor;
	private static String leftIRSensor;

	// Strings to hold sonar readings
	private static String lFSonarInches;
	private static String centerSonarInches;
	private static String rightFSonarInches;
	private static String backSonarInches;

	public static void setcenterSonarInches( String csi ) {

		centerSonarInches = csi;
	}
	
	public static void setFLSonarInches(String fli) {

		lFSonarInches = fli;
	}

	public static void setcentreIRSensor( String cir ) {

		centreIRSensor = cir;
	}


	public static void setbackSonarInches( String bsi ){

		backSonarInches = bsi;
	}

	public static void setcompassAngle( String cA ){

		compassAngle = cA;
	}

	public static void setFRSonarInches( String rFS ){

		rightFSonarInches = rFS;
	}

	
	
	
	
	
	
	public static String getcompassAngle() {

		return compassAngle;
	}

	public static String getCentSonarInches() {

		return centerSonarInches;
	}

	public static String getcentreIRSensor() {

		return centreIRSensor;
	}

	public static String getbackSonarInches() {

		return backSonarInches;
	}

	public static String getFLSonarInches() {

		return lFSonarInches;
	}
	
	public static String getFRSonarInches() {

		return rightFSonarInches;
	}
	
}
