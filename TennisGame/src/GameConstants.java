/** 
 * @author Awais Butt
 * Tennis Game
 *
 * Game Constants 
 */


import java.awt.Image;
import java.awt.Toolkit;


public class GameConstants  {    
    public static int ballSpeed = 7;
    public static int paddleSpeed = 10;

	
	public static int paddleHeight = 220;
	public static int paddleWidth  = 44;	
	public static int ballRadius   = 5;
	
	public static int ballDelay = 30;
	public static int gameDelay = 30;		
    
    public static Image leftPaddleImage  = Toolkit.getDefaultToolkit().getImage("images/paddle1.gif");
    public static Image rightPaddleImage = Toolkit.getDefaultToolkit().getImage("images/paddle2.gif");
    public static Image ballImage = Toolkit.getDefaultToolkit().getImage("images/balla.gif");	
    
}
