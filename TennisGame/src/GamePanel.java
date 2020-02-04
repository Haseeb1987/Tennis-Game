/** 
 * @author Awais Butt
 * Tennis Game
 *
 * Game Panel 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.Image;

public class GamePanel extends JPanel implements ActionListener {
    Paddle lPaddle;
    Paddle rPaddle;
    Ball ball;

	
	Timer gameSpeed;
	boolean isGameOver = false;

    public GamePanel(){	
		startGame();
    }

    public void paintComponent(Graphics g){
		//System.out.println ( "GamePanel/paintComponent" );
	
        super.paintComponent(g);

        // draw Center Line 
        g.setColor( Color.YELLOW );
		//System.out.println ( GameDimension.gameScreenCenter );
        g.drawLine(GameDimension.gameScreenCenter-2, 0, GameDimension.gameScreenCenter-2, GameDimension.gameScreenHeight );

        g.setColor( Color.BLACK );
        g.drawLine(GameDimension.gameScreenCenter-1, 0, GameDimension.gameScreenCenter-1, GameDimension.gameScreenHeight );
        g.drawLine(GameDimension.gameScreenCenter, 0, GameDimension.gameScreenCenter, GameDimension.gameScreenHeight);            
        g.drawLine(GameDimension.gameScreenCenter+1, 0, GameDimension.gameScreenCenter+1, GameDimension.gameScreenHeight);

        g.setColor(Color.yellow);
        g.drawLine(GameDimension.gameScreenCenter+2, 0, GameDimension.gameScreenCenter+2, GameDimension.gameScreenHeight);

        g.setColor(Color.black);
        g.setFont( new Font("Monospace",Font.BOLD, 24) );
        g.drawString("Tennis Game", GameDimension.gameScreenCenter - 80 , 20);

		if ( isGameOver ) {
	        g.setColor(Color.green);
			g.drawString("Game Over", GameDimension.gameScreenCenter - 80 , 100);			
			stopGame();
		}
		
        //draw paddle 
		//System.out.println ( "GamePanel/paintComponent....... before drawing paddle ");            
		g.setColor( Color.BLUE );
        lPaddle.drawPaddle(g);

		g.setColor( Color.RED );
        rPaddle.drawPaddle(g);

        //draw a ball 
		g.setColor(Color.yellow);
        ball.drawBall(g);            
	}	
	
	public void startGame() {
		//System.out.println ( "GamePanel/GamePanel" );						
		Image ballImg =    GameConstants.ballImage;		
		Image lPaddleImg = GameConstants.leftPaddleImage;
		Image rPaddleImg = GameConstants.rightPaddleImage;
		

		lPaddle = new Paddle(0, 0, lPaddleImg );
		rPaddle = new Paddle(GameDimension.gameScreenWidth - GameConstants.paddleWidth, GameDimension.gameScreenHeight - GameConstants.paddleHeight, rPaddleImg);
		ball    = new Ball(100, 100, ballImg);
		
		//System.out.println ( "GamePanel/startGame.......before registering handler" );						
		
		gameSpeed = new Timer (GameConstants.gameDelay, this);  
		gameSpeed.start();
		repaint();                    	
	}

	public void stopGame() {
		gameSpeed.stop();
		ball.stopBall();
	}
	
	
	public void actionPerformed(ActionEvent ae) {
		//System.out.println ( "GamePanel/actionPerformed..****************.........");		
		repaint();
		detectCollision();
	}
	
	

	public void detectCollision() {
        if ( ball.getX() <= GameDimension.minX + GameConstants.paddleWidth - 20)    { 	// if ball strikes with EAST/LEFT paddle
			//System.out.println ( "GamePanel/detectCollision" + ball.getX() );		
        	if ( ball.getY() >= lPaddle.getY() - 20 && ball.getY() <= lPaddle.getY() +  lPaddle.getHeight() - 100 ) { // if ball strkes with lPaddle
				//System.out.println ( "GamePanel/detectCollision.......BallY,  lPaddleY" + ball.getY() + "--" + lPaddle.getY() );
				if ( ball.ballDirection == Ball.Direction.NORTHEAST ) {
                	ball.ballDirection = Ball.Direction.NORTHWEST;
	            } else if ( ball.ballDirection == Ball.Direction.SOUTHEAST ) {
    	            ball.ballDirection = Ball.Direction.SOUTHWEST;
        	    }	
        	}
        }
		

		if ( ball.getX() < GameDimension.minX )    { 	// if ball strikes with EAST/LEFT wall		
			if ( ball.getY() <= lPaddle.getY()  || ball.getY() >= lPaddle.getY() + lPaddle.getHeight() - 100) {  // if ball strikes with Left Wall
				//System.out.println ( "GamePanel/detectCollision.......BallY,  lPaddleY" + ball.getY() + "--" + lPaddle.getY() );
				isGameOver = true;				
			} 		
        } 
		
		
											
		if ( ball.getX() >= GameDimension.maxX - GameConstants.paddleWidth - 40 ) {	// if ball strikes with WEST/RIGHT paddle
			System.out.println ( "GamePanel/DetectCollision........BallX: " + ball.getX() );
			if (ball.getY() >= rPaddle.getY() - 20  &&  ball.getY() <= rPaddle.getY() + rPaddle.getHeight() - 100 ) { // if ball strikes with rPaddle
				if ( ball.ballDirection == Ball.Direction.SOUTHWEST ) {
				    ball.ballDirection = Ball.Direction.SOUTHEAST;
				} else if ( ball.ballDirection == Ball.Direction.NORTHWEST) {
				    ball.ballDirection = Ball.Direction.NORTHEAST;
				}								
			} 
		}
			

		if ( ball.getX() >= GameDimension.maxX - 60) {	// if ball strikes with WEST/RIGHT wall			
			if ( ball.getY() <= rPaddle.getY()  || ball.getY() >= rPaddle.getY() + rPaddle.getHeight() - 100 ) { // if ball strikes with Righ Wall
				System.out.println ( "GamePanel/DetectCollision...collision....BallX: " + ball.getX() );
				isGameOver = true;	
			} 		
        } 	
    }
	
	
	
}