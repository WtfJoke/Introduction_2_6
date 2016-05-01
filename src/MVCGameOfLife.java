import java.awt.Dimension;
import javax.swing.DefaultDesktopManager;
import javax.swing.JApplet;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/** 
 * Class which control Conways "Game of Life"
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
	public GameOfLifeBoard gol;
    
	/**
	 * Constructor
	 */
	public MVCGameOfLife() 
	{
        // Setup the game board size with proper boundaries
		gol = new GameOfLifeBoard(new Dimension(250/20-2, 290/20-2));
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
		child.setSize(250, 290);
		child.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		desk.add(child);
		child.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		MVCGameOfLife gameOfLife = new MVCGameOfLife();
		GameOfLifeChildFrame golChildFrame = new GameOfLifeChildFrame(gameOfLife, gameOfLife.gol);
		gameOfLife.addChild(golChildFrame, 10, 10);
		Thread gameOfLifeThread = new Thread(golChildFrame);
		gameOfLifeThread.start();
		Konsole.run(gameOfLife, 512, 512);
	}
}
