import javax.swing.DefaultDesktopManager;
import javax.swing.JApplet;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/** 
 * Class for Conways "Game of Life"
 * Using Threads, MDI and MVC
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLife extends JApplet 
{
	private static final long serialVersionUID = 1L;
	private JDesktopPane desk;
	
	/**
	 * Constructor
	 */
	public GameOfLife() 
	{
		desk = new JDesktopPane(); //
		desk.setDesktopManager(new DefaultDesktopManager()); //
		setContentPane(desk); //
	}
	
	/**
	 * Method which add children to JDesktopPane
	 * @param child
	 * @param x
	 * @param y
	 */
	public void addChild (JInternalFrame child, int x, int y) 
	{ 
		child.setLocation(x, y);
		child.setSize(200, 150);
		child.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		desk.add(child);
		child.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		GameOfLife gameOfLife = new GameOfLife();
		gameOfLife.addChild(new GameOfLifeChild(gameOfLife), 10, 10);
		Konsole.run(gameOfLife, 400, 300);
	}

}
