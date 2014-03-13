package BatController;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
/**
 * Main mapGUI class
 * @author S Murphy
 *
 */
public class mapGUI {

	private JFrame mapFrame = new JFrame( "Real Time Map" );

	LocalisationFilter filter;
	BatSerialConn serialClass;

	public mapGUI(BatSerialConn instance) {

		mapFrame.getContentPane().add( new Map() );
		mapFrame.setVisible( true );  // displays frame
		mapFrame.setSize( 805, 828 );
		mapFrame.getContentPane().setSize( 800, 800 );
		mapFrame.setResizable( false );
		mapFrame.setLocationRelativeTo( null );
		mapFrame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );// allows the map to close separate from main GUI
		serialClass = instance;
	}

	/**
	 * inner class
	 * @author S Murphy
	 *
	 */
	private class Map extends Canvas {

		private static final long serialVersionUID = 1L;

		private int colX = 200;// col
		private int rowY = 200;// row
		private Graphics doubleGfxHolder; // holder for double buffer
		private Image OffScreen;// image object for double buffer
		private boolean st = false;
		private Robot robot;
		private Rectangle robotShape;

		private int[][] mapXY = new int[colX][rowY];
		private int[][] obsXY = new int[colX][rowY];
		/**
		 * inner class constructor
		 */
		public Map() {	

			filter = new LocalisationFilter(serialClass);
			robot = new Robot(0, 0, 21, 25);
			robotShape = null;
		}
		/**
		 * Called by repaint()
		 */
		public void update( Graphics g )
		{
			paint(g);
		}

		/**
		 * Paint called from update
		 */
		@Override
		public void paint ( Graphics g )
		{
			Dimension d = getSize();
			checkOffScreenImage();// check if screen size has changed
			doubleGfxHolder =  OffScreen.getGraphics();
			doubleGfxHolder.setColor( getBackground() );
			doubleGfxHolder.fillRect( 0, 0, d.width, d.height );
			drawUpdateRTdata (  OffScreen.getGraphics() );// get g context we create in drawupdateRTdata
			g.drawImage( OffScreen, 0, 0, null );// first pass
		}

		/**
		 * Check if screen size has changed
		 * in preparation for double buffer
		 */
		private void checkOffScreenImage() {

			Dimension d = getSize();
			if ( OffScreen == null || OffScreen.getWidth( null ) != d.width
					|| OffScreen.getHeight( null ) != d.height ) {

				OffScreen = createImage( d.width,d.height );
			}
		}

		/**
		 * Draw the actual picture from buffer
		 * currently 50ms delay
		 * @param g
		 */
		public void drawUpdateRTdata ( Graphics g )
		{

			final long startTime = System.currentTimeMillis();
			// int k; // used for drawing grid lines
			double width = getSize().width;
			double height = getSize().height;
			double htOfX = height / colX;
			double wdOfX = width / rowY;

			int sqSize = (int) htOfX;

			if ( st == false ) {

				int robotStartElavationInRows = 25; // Change this to move starting position of robot (up or down on map)
				int robotStartX = (int) ( ( ( rowY*wdOfX )/2) - ( wdOfX * 2 ) ); // robots X start position on map (passed to g.fillRect)
				int robotStartY = (int) ( ( colX*htOfX ) - ( htOfX * robotStartElavationInRows ) ); // robots Y start position on map (passed to g.fillRect)
				robot.setX( robotStartX );
				robot.setY( robotStartY );
				System.out.println( robotStartX );
				System.out.println( robotStartY );
				st = true;
				robotShape = new Rectangle( robot.getX(), robot.getY() , robot.getWidth(), robot.getHeight() );
			}

			int compass = filter.cA;
			double radians = compass*(Math.PI/180);
			Graphics2D g2d = ( Graphics2D ) g;
			Rectangle actRobot = robotShape.getBounds();
			AffineTransform at = new AffineTransform();

			// update the SLAM filter then draw
			filter.update(robotShape);

			mapXY = filter.getMapXY();
			obsXY = filter.getObsXY();

			for (int x = 0; x < mapXY.length ; x++ ) {
				for (int y = 0 ; y < mapXY.length; y++){

					if ( obsXY[x][y] == 0 ) {// never observed
						g.setColor(Color.CYAN);
						g.fillRect( x * sqSize, y * sqSize , sqSize, sqSize );
					}

					if ( mapXY[x][y] > 0 && obsXY[x][y] > 0 ) {// probably occupied
						g.setColor(Color.BLUE);
						g.fillRect( x * sqSize, y * sqSize , sqSize, sqSize );
					}

					if ( mapXY[x][y] < 0 && obsXY[x][y] > 0 ) {// probably unoccupied
						g.setColor(Color.WHITE);
						g.fillRect( x * sqSize, y * sqSize , sqSize, sqSize );
					}
				}
			}

			g.setColor(Color.RED);

			Shape temp;
			at.rotate(radians, actRobot.getX() + actRobot.width/2, actRobot.getY() + actRobot.height/2);	
			g2d.draw( temp = at.createTransformedShape( actRobot ) );
			g2d.setTransform(at);
			g2d.draw(  robotShape  );

			// can be used to measure time to complete map repaint
			final long endTime = System.currentTimeMillis();
			System.out.println("Total execution time: " + ( endTime - startTime ) );
			try {

				Thread.sleep( 0 );

			} catch ( InterruptedException e ) {

				e.printStackTrace();
			}
			repaint();
		}
	}
}