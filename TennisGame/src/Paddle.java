/** 
 * @author Awais Butt
 * Tennis Game
 *
 * Paddle Class 
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Paddle {
    private int x, y;
    private int width, height;
        
    private Image paddleImg = GameConstants.leftPaddleImage;   
    private Graphics2D g2D;
    
    public Paddle(int x, int y, Image img){
        this.x = x;
        this.y = y;
				
		if ( img != null ) 		
	        paddleImg = img;
			
		width  = GameConstants.paddleWidth;
		height = GameConstants.paddleHeight;
		// width  = paddleImg.getWidth();
		// height = paddleImg.getHeight();		
		
		//System.out.println ( "Paddle/Paddle........height: " + height);
    }
    
    public void setX(int x) {		
		this.x = x;
    }
    public int getX() {
        return x;
    }	
    public void setY(int y) {
		//System.out.println ( "setYxxxxxxxxxxxxx" + y);
        if ( !( y < GameDimension.minY  || y  >  GameDimension.maxY ) ) {
            this.y = y;					 
        } 	
    }	
    public int getY() {
        return y;
    }
    public int getWidth(){
        return width;
    }     
    public int getHeight(){
        return height;
    }
    
    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void moveNorth(){
        if (y >= GameDimension.minY + 10){
            y -= GameConstants.paddleSpeed;
        } 
    }    
    public void moveSouth(){
		//System.out.println ( "Paddle/moveSouth" );
        if (y <= GameDimension.maxY - height){
            y += GameConstants.paddleSpeed;
        } 
    }
    
    public void drawPaddle (Graphics g){
		//System.out.println ( "Paddle/drawPaddle");
        this.g2D = (Graphics2D)g;
        if ( paddleImg != null)  
            g2D.drawImage( paddleImg, x,  y,  null);    
		
        //g2D.fillRect(x, y, width, height);
    }
}