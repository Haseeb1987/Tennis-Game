/** 
 * @author Awais Butt
 * Tennis Game
 */

import java.awt.Toolkit;
import java.awt.Dimension;

public class GameDimension  {
	static int minX=0, maxX, minY=0, maxY;
	static int gameScreenWidth, gameScreenHeight;
    static int gameScreenCenter;
	
	public GameDimension()	{
		//System.out.println ( "GameDimension/GameDimension");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();		
		
		maxX = gameScreenWidth  = (int)screenSize.getWidth() ;
		maxY = gameScreenHeight = (int)screenSize.getHeight();	
        gameScreenCenter = gameScreenWidth / 2;
		//System.out.println ( "GameDimension/maxX" + maxX);
		//System.out.println ( "GameDimension/maxY" + maxY);
	}
}