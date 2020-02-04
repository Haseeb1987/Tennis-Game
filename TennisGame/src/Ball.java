/** 
 * @author Awais Butt
 * Tennis Game
 *
 * Ball Class 
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;


public class Ball implements Runnable, ActionListener {
    private int x, y;

    private Timer timer;
    private int ballDelay = GameConstants.ballDelay;

    enum Direction  { NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST  };
    Direction ballDirection = Direction.SOUTHWEST;	

    private Image ballImg = GameConstants.ballImage; 
    private Graphics2D g2D;

	
    public Ball(int x, int y, Image img) {	
        this.x = x;
        this.y = y;        
	
		if ( img != null )
	        this.ballImg = img;
        timer = new Timer(ballDelay, this);        
    }   		 
	
    public void setX(int x) {
        this.x = x;
    }
    public int getX() {
        return x;
    }		
    public void setY(int y) {
        this.y = y;
    }	
    public int getY() {
        return y;
    }	   
    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }  	
	public Timer getTimer() {
		return timer;
	}
        
    public void run()	{
        timer.start();	// start timer in new thread
    }		

    public void drawBall(Graphics g) {
        g2D = (Graphics2D) g;
		//g2D.fillOval(x, y, GameConstants.ballRadius, GameConstants.ballRadius);
		if ( ballImg != null)  
	        g2D.drawImage( ballImg, x,  y,  null);    
			
	    new Thread( this ).start();
    }
    public void reDrawBall(){
		//g2D.fillOval(x, y, GameConstants.ballRadius, GameConstants.ballRadius);		
	    if ( ballImg != null)  
    	    g2D.drawImage( ballImg, x,  y,  null);
    }
	
    public void actionPerformed(ActionEvent ae) {
		//System.out.println ( "Ball/actionPerformed...." + x);
        moveBall();
    }
	
    public void moveBall() {
        switch ( ballDirection  ) {
            case NORTH: 	// Moving UP
                y -= GameConstants.ballSpeed;
                break;			
            case SOUTH:  	// Moving Down
                y += GameConstants.ballSpeed;
                break;
            case EAST: 	// Moving Left
                x -= GameConstants.ballSpeed;
                break;
            case WEST: 	// Moving Right
                x += GameConstants.ballSpeed;
                break;
            case NORTHEAST:	// Moving Diagonally UpLeft
                x -= GameConstants.ballSpeed;
                y -= GameConstants.ballSpeed;
                break;			
            case NORTHWEST: 	// Moving Diagonally UpRight
                x += GameConstants.ballSpeed;
                y -= GameConstants.ballSpeed;			
                break;							
            case SOUTHEAST:	// Moving Diagonally DownLeft
                x -= GameConstants.ballSpeed;
                y += GameConstants.ballSpeed;						
                break;			
            case SOUTHWEST: 	// Moving Diagonally DownRight
                x += GameConstants.ballSpeed;
                y += GameConstants.ballSpeed;									
                break;				
        }
        checkCollisonWithWalls();
    }
	
    public void checkCollisonWithWalls() {						
        if ( x < GameDimension.minX  )    { 	// if ball strikes with EAST/LEFT wall
            if ( ballDirection == Direction.NORTHEAST ) {
                ballDirection = Direction.NORTHWEST;
            } else if ( ballDirection == Direction.SOUTHEAST ) {
                ballDirection = Direction.SOUTHWEST;
            }			
        } else if ( x >= GameDimension.maxX - GameConstants.ballRadius - 45 ) {	// if ball strikes with WEST/RIGHT wall
            if ( ballDirection == Direction.SOUTHWEST ) {
                ballDirection = Direction.SOUTHEAST;
            } else if ( ballDirection == Direction.NORTHWEST) {
                ballDirection = Direction.NORTHEAST;
            }					
        } else if ( y < GameDimension.minY ) {	// if ball strikes with NORTH/Top wall
            if ( ballDirection == Direction.NORTHWEST ) {
                ballDirection = Direction.SOUTHWEST;
            } else if ( ballDirection == Direction.NORTHEAST) {
                ballDirection = Direction.SOUTHEAST;
            }											
        } else if ( y >= GameDimension.maxY - GameConstants.ballRadius  - 105  ) { // if ball strikes with SOUTH/Bottom wall
            if ( ballDirection == Direction.SOUTHWEST ) {
                ballDirection = Direction.NORTHWEST;
            } else if ( ballDirection == Direction.SOUTHEAST) {
                ballDirection = Direction.NORTHEAST;
            }							
        } 		
		
		reDrawBall( );		        
    }
	
	public void stopBall() {
		timer.stop();
		timer.removeActionListener(this);
	}
}

