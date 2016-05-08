import java.awt.Dimension;
import javax.swing.DefaultDesktopManager;
import javax.swing.JApplet;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/** 
 * Class which controls Conways "Game of Life"
 * MVC: Controller
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class MVCGameOfLife extends JApplet
{	
	//Private and public members
	private static final long serialVersionUID = 1L;
	private JDesktopPane desk;
	public static GameOfLifeBoard newGolBoard;
	public static MVCGameOfLife gameOfLife = new MVCGameOfLife();
	public static boolean isNewGame = false;
    
	/**
	 * Constructor
	 */
	public MVCGameOfLife() 
	{
        // Setup the game board size with proper boundaries
		//gol = new GameOfLifeBoard(new Dimension(250/20-2, 290/20-2));
		desk = new JDesktopPane(); //
		desk.setDesktopManager(new DefaultDesktopManager());
		setContentPane(desk);
	}
	
	
	/**
	 * Method which adds children frames to JDesktopPane
	 * @param child child frame to be added (JInternalFrame)
	 * @param x X-Position of the child frame to be added (int)
	 * @param y Y-Position of the child frame to be added (int)
	 */
	public void addChild(JInternalFrame child, int x, int y) 
	{
		child.setLocation(x, y);
		//child.setSize(250, 330);
		child.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		desk.add(child);
		child.setVisible(true);
	}
	
	/**
	 * Method which allows to create multiple independent games
	 */
	public static void createNewGame()
	{	
		GameOfLifeChildFrame.gameNr++;
		newGolBoard = new GameOfLifeBoard(new Dimension(Integer.parseInt(GOLMenu.rowNumber.getText()), Integer.parseInt(GOLMenu.columnNumber.getText())));	
		GameOfLifeChildFrame golChildFrame = new GameOfLifeChildFrame(gameOfLife, MVCGameOfLife.newGolBoard);
		gameOfLife.addChild(golChildFrame, 30, 30);
		Thread gameOfLifeThread = new Thread(golChildFrame);
		gameOfLifeThread.start();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		//MVCGameOfLife gameOfLife = new MVCGameOfLife();
		GOLMenu golMenu = new GOLMenu();
		//GameOfLifeChildFrame golChildFrame = new GameOfLifeChildFrame(gameOfLife, gameOfLife.gol);
		gameOfLife.addChild(golMenu, 10, 10);
		//gameOfLife.addChild(golChildFrame, 30, 30);
		// Create and start Thread
		//Thread gameOfLifeThread = new Thread(golChildFrame);
		//gameOfLifeThread.start();
		Konsole.run(gameOfLife, 512, 512);
	}
}
