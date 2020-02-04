/**
 * @author Awais Butt
 * Tennis Game
 *
 * Ball Class
 */

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;

public class Game extends JFrame {
    GamePanel gamePanel;
    JMenuBar  menuBar;
    Container container;
 	PaddleMovementHandler pmh;

    public Game(String title) {
        super(title);
		new GameDimension();
        initGUI();
    }

    public static void main(String []args){
        new Game("Tennis Game");
    }

    public void initGUI(){
       	container = getContentPane();
   		createGameMenuBar();
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		//setResizable(false);
        setSize(GameDimension.gameScreenWidth, GameDimension.gameScreenHeight);
        setVisible (true);
    }

	public void startNewGame() {
		//System.out.println ( "Game/startNewGame");


		if ( gamePanel != null ) {  // if game is starting second time
			remove ( gamePanel );
			removeKeyListener( pmh );
			gamePanel.stopGame();
			gamePanel.ball.stopBall();
			gamePanel.setVisible(false);
			gamePanel = null;
		}

		gamePanel = new GamePanel();
		add( gamePanel , BorderLayout.CENTER );

		pmh = new PaddleMovementHandler();
		addKeyListener ( pmh );

		setVisible (true);
	}

	public void stopGame() {
		System.out.println ( "Game/stopGame");

		remove ( gamePanel );
		removeKeyListener( pmh );
		gamePanel.stopGame();
		gamePanel.ball.stopBall();
		gamePanel.setVisible(false);
		gamePanel = null;

		JLabel lbl = new JLabel("Game Ended");
		JPanel p = new JPanel();
		p.add( lbl );
		add( p, BorderLayout.CENTER );


		setVisible (true);
	}


    public void createGameMenuBar(){
        menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("Game Options");
        fileMenu.setMnemonic( 'F');


        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.setMnemonic( 'N');

//        JMenuItem playWithComputerItem = new JMenuItem("Play With Computer");
//        playWithComputerItem.setMnemonic( 'P');

        JMenuItem stopGameItem = new JMenuItem("End Game");
        stopGameItem.setMnemonic( 'S');

        JMenuItem loadGameItem = new JMenuItem("Load Game");
        loadGameItem.setMnemonic( 'L');

        JMenuItem saveGameItem = new JMenuItem("Save Game");
        saveGameItem.setMnemonic( 'S');

        JMenuItem exitGameItem = new JMenuItem("Exit");
        exitGameItem.setMnemonic( 'E');


		newGameItem.addActionListener(
            new ActionListener ( ) {
                public void actionPerformed(ActionEvent e) {
					startNewGame();
                }
            }
        );

		stopGameItem.addActionListener(
            new ActionListener ( ) {
                public void actionPerformed(ActionEvent e) {
					stopGame();
                }
            }
        );

        loadGameItem.addActionListener(
            new ActionListener ( ) {
                public void actionPerformed(ActionEvent e) {
					loadGame();
                }
            }
        );

        saveGameItem.addActionListener(
            new ActionListener ( ) {
                public void actionPerformed(ActionEvent e) {
                    try {
						saveGame();
                    } catch(Exception exp) {
                        System.out.println ( exp );
                    }
                }
            }
        );

        exitGameItem.addActionListener(
            new ActionListener ( ) {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            }
        );


	    fileMenu.add( newGameItem  );
	    fileMenu.add( stopGameItem  );
        fileMenu.add( loadGameItem );
        fileMenu.add( saveGameItem );
        fileMenu.add( exitGameItem );


	// Select Game Level Menu
        JMenu gameLevelMenu = new JMenu("Select Game Level");
        gameLevelMenu.setMnemonic( 'S');

        JMenuItem level1Item = new JMenuItem("Level1");
        JMenuItem level2Item = new JMenuItem("Level2");
        JMenuItem level3Item = new JMenuItem("Level3");

		level1Item.addActionListener ( new GameLevelHandler() );
		level2Item.addActionListener ( new GameLevelHandler() );
		level3Item.addActionListener ( new GameLevelHandler() );

		gameLevelMenu.add (level1Item);
		gameLevelMenu.add (level2Item);
		gameLevelMenu.add (level3Item);


	// Help Menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic( 'H');

        menuBar.add( fileMenu );
        menuBar.add( gameLevelMenu );
		menuBar.add( helpMenu );


        setJMenuBar(menuBar);
    }

	public void saveGame() {
		try  {
			File f = new File ("gameData.dat");
			FileOutputStream fos = new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream( fos );
			DataOutputStream dos = new DataOutputStream ( bos);
			dos.writeInt( gamePanel.ball.getX());
			dos.writeInt( gamePanel.ball.getY());
			dos.writeInt( gamePanel.lPaddle.getX());
			dos.writeInt( gamePanel.lPaddle.getY());
			dos.writeInt( gamePanel.rPaddle.getX());
			dos.writeInt( gamePanel.rPaddle.getY());

			dos.flush();
		} catch ( Exception exp) {
			System.out.println ( exp );
		}
	}

	public void loadGame() {
		try {
		    File f = new File ("gameData.dat");
		    FileInputStream fis = new FileInputStream(f);
		    BufferedInputStream bis = new BufferedInputStream( fis );
			DataInputStream dis = new DataInputStream (bis);

			int ballX =  dis.readInt();
			int ballY =  dis.readInt();
			int lPaddleX = dis.readInt();
			int lPaddleY = dis.readInt();
			int rPaddleX = dis.readInt();
			int rPaddleY = dis.readInt();

			startNewGame();

			gamePanel.ball.setX( ballX );
			gamePanel.ball.setY( ballY );
			gamePanel.lPaddle.setX( lPaddleX );
			gamePanel.lPaddle.setY( lPaddleY );
			gamePanel.rPaddle.setX( rPaddleX );
			gamePanel.rPaddle.setY( rPaddleY );

		} catch(Exception exp) {
		    System.out.println ( exp );
		}
	}

	class GameLevelHandler implements ActionListener  {
		public void actionPerformed(ActionEvent ae) {
			String level = ae.getActionCommand() ;

			if ( level.equals ( "Level1" ) ){
				GameConstants.ballSpeed = 5;
			} else if ( level.equals ( "Level2" )  ){
				GameConstants.ballSpeed = 10;
			} else if ( level.equals ( "Level3" )  ){
				GameConstants.ballSpeed = 15;
			}
		}
	}


	class PaddleMovementHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            int code = e.getKeyCode();

            if (code == KeyEvent.VK_UP ) {              // Use up key to move right paddle in upward direction
	            gamePanel.rPaddle.moveNorth();
            } else if (code == KeyEvent.VK_DOWN )  {  	// Use down key to move right paddle in downward direction
     	     	gamePanel.rPaddle.moveSouth();
            }

            if (code == KeyEvent.VK_A ) {              // Use A key to move left paddle in upward direction
            	gamePanel.lPaddle.moveNorth();
            } else if (code == KeyEvent.VK_Z )  {  	// Use Z key to move left paddle in downward direction
				gamePanel.lPaddle.moveSouth();
            }

            repaint(); // no need
        }
    }
}
