package BatController;

import java.awt.Shape;

/**
 * The SLAM class
 * All logic is here and object avoidance
 * @author radiance
 *
 */
public class LocalisationFilter {

	private double cFS;  // front sonar is used for drawing data to canvas
	private double cRS;  // rear sonar is used for drawing data to canvas
	private double cFIR; // front inferred is used for drawing data to canvas
	private double fRS;  // front right sonar
	private double fLS;  // front left sonar

	public  int cA = 1;
	private int[][] mapXY;
	private int[][] obsXY;


	private int robotFrontSonar;
	private int robotRcenter;

	private int sqSize = 4;// set this to map square size ( mapGui class x or y / window size (800) )
	private BatSerialConn serialClass;

	private double sensorTemp;
	private int IndexX;
	private int IndexY;

	private double offsetLeft = -45;
	private double offsetRight = 45;

	private double theta; 
	private double hyp; //The hypotonuse - the line from BOT to scanned object
	private double adj; //The adjacent - Will hold the distance along X axis that we need to increment
	private double opp; //The adjacent - line going along Y axis, holding the distance we need to increment
	/**
	 * Constructor
	 * @param instance
	 */
	public LocalisationFilter(BatSerialConn instance) {

		serialClass = instance;
		mapXY = new int[200][200];
		obsXY = new int[200][200];
	}

	/**
	 * Main SLAM processing, decision making and
	 * mapping
	 * @param robot
	 */
	public void update(Shape robot) {

		// Shape temp = robot;
		fLS  =  Double.parseDouble( sensorDataGetSet.getFLSonarInches() );
		fRS  =  Double.parseDouble(sensorDataGetSet.getFRSonarInches());
		cFS  =  Double.parseDouble( sensorDataGetSet.getCentSonarInches() );
		cRS  =  Double.parseDouble( sensorDataGetSet.getbackSonarInches() );
		//	cFIR =  Double.parseDouble( sensorDataGetSet.getcentreIRSensor() );
		cA   =  Integer.parseInt( sensorDataGetSet.getcompassAngle() );
		//cA = rotate( cA );


		calcIndexMapF(robot,cFS);
		calcIndexMapRear(robot,cRS);
		calcIndexMapFL(robot,fLS);
		calcIndexMapFR(robot,fRS);

	}


	public void calcIndexMapF( Shape temp,double sensor){

		sensorTemp = sensor;

		theta = cA*( Math.PI/180 );//The rotation (in degs, for now) - using 45 as an example
		hyp = sensorTemp + (temp.getBounds2D().getHeight()/2 /sqSize );
		opp = hyp * ( Math.cos( theta ) );
		adj = hyp * ( Math.sin( theta ) );

		IndexX = (int) ((( temp.getBounds2D().getCenterX() ) /sqSize ) + adj );
		IndexY = (int) ((( temp.getBounds2D().getCenterY() ) /sqSize ) - opp );

		if (IndexY < 200 && IndexY < 200){

			obsXY [IndexX] [IndexY] = obsXY [IndexX] [IndexY] = +1;

			if( hyp == cFS + (temp.getBounds2D().getHeight()/2 /sqSize ) && cFS <= 18 ){
				mapXY [IndexX] [IndexY] = mapXY [IndexX] [IndexY] = +1;
			}
			else mapXY [IndexX] [IndexY] = mapXY [IndexX] [IndexY] = -1;
		}

		for ( double i = 0; i < sensorTemp;  ) {

			sensorTemp = sensorTemp - 1;
			calcIndexMapF(temp,sensorTemp);
		}
	}


	public void calcIndexMapFL( Shape temp,double sensor ){

		sensorTemp = sensor;

		theta = (cA + offsetLeft)*( Math.PI/180 );//The rotation (in degs, for now) - using 45 as an example
		hyp = sensorTemp + (temp.getBounds2D().getHeight()/2 /sqSize );

		opp = hyp * ( Math.cos( theta ) );
		adj = hyp * ( Math.sin( theta ) );

		IndexX = (int) ((( temp.getBounds2D().getCenterX() ) /sqSize ) + adj );
		IndexY = (int) ((( temp.getBounds2D().getCenterY() ) /sqSize ) - opp );

		if (IndexY < 200 && IndexY < 200){

			obsXY [IndexX] [IndexY] = obsXY [IndexX] [IndexY] = +1;

			if( hyp == fLS + (temp.getBounds2D().getHeight()/2 /sqSize ) && fLS <= 18 ){
				mapXY [IndexX] [IndexY] = mapXY [IndexX] [IndexY] = +1;
			}
			else mapXY [IndexX] [IndexY] = mapXY [IndexX] [IndexY] = -1;
		}

		for ( double i = 0; i < sensorTemp;  ) {

			sensorTemp = sensorTemp - 1;
			calcIndexMapFL(temp,sensorTemp);
		}
	}


	public void calcIndexMapFR( Shape temp,double sensor ) {

		sensorTemp = sensor;

		theta = (cA + offsetRight)*( Math.PI/180 );//The rotation (in degs, for now) - using 45 as an example
		hyp = sensorTemp + (temp.getBounds2D().getHeight()/2 /sqSize );

		opp = hyp * ( Math.cos( theta ) );
		adj = hyp * ( Math.sin( theta ) );

		IndexX = (int) ((( temp.getBounds2D().getCenterX() ) /sqSize ) + adj );
		IndexY = (int) ((( temp.getBounds2D().getCenterY() ) /sqSize ) - opp );

		if (IndexY < 200 && IndexY < 200){

			obsXY [IndexX] [IndexY] = obsXY [IndexX] [IndexY] = +1;

			if( hyp == fRS + (temp.getBounds2D().getHeight()/2 /sqSize ) && fRS <= 18 ){
				mapXY [IndexX] [IndexY] = mapXY [IndexX] [IndexY] = +1;
			}
			else mapXY [IndexX] [IndexY] = mapXY [IndexX] [IndexY] = -1;
		}

		for ( double i = 0; i < sensorTemp;  ) {

			sensorTemp = sensorTemp - 1;
			calcIndexMapFR(temp,sensorTemp);
		}
	}




	public void calcIndexMapRear( Shape temp,double sensor){

		sensorTemp = sensor;

		theta = cA*( Math.PI/180 );//The rotation (in degs, for now) - using 45 as an example
		hyp = sensorTemp + (temp.getBounds2D().getHeight()/2 /sqSize );
		opp = hyp * ( Math.cos( theta ) );
		adj = hyp * ( Math.sin( theta ) );

		IndexX = (int) ((( temp.getBounds2D().getCenterX() ) /sqSize ) - adj );
		IndexY = (int) ((( temp.getBounds2D().getCenterY() ) /sqSize ) + opp );

		if (IndexY < 200 && IndexY < 200){

			obsXY [IndexX] [IndexY] = obsXY [IndexX] [IndexY] = +1;

			if( hyp == cRS + (temp.getBounds2D().getHeight()/2 /sqSize ) && cRS <= 18 ){
				mapXY [IndexX] [IndexY] = mapXY [IndexX] [IndexY] = +1;
			}
			else mapXY [IndexX] [IndexY] = mapXY [IndexX] [IndexY] = -1;
		}

		for ( double i = 0; i < sensorTemp;  ) {

			sensorTemp = sensorTemp - 1;
			calcIndexMapRear(temp,sensorTemp);
		}
	}

	/**
	 * Used for testing without robot
	 * Arduino required though with test program
	 * @param A
	 * @return compass
	 */
	public int rotate(int A){

		if ( cA == 359 ){
			cA = 1;
		}
		cA = cA + 1;
		return cA;
	}

	/**
	 * returns map array
	 * @return
	 */
	public int[][] getMapXY() {

		return mapXY;
	}

	/**
	 * returns observed array
	 * @return
	 */
	public int[][] getObsXY() {

		return obsXY;
	}

	/**
	 * Send move forward command to robot
	 */
	public void moveForward() {

		serialClass.setNextCommand("^");
	}

	/**
	 * Send move reverse to robot
	 */
	public void moveBackwards() {

		serialClass.setNextCommand("V");
	}


	public void rotateLeft() {

		serialClass.setNextCommand("<");
	}


	public void rotateRight() {

		serialClass.setNextCommand(">");
	}


	public void stop() {

		serialClass.setNextCommand("S");
	}
}
