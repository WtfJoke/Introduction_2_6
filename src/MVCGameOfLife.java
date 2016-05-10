import javax.swing.DefaultDesktopManager;
import javax.swing.JApplet;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/** 
 * Class which creates a frame for a new Conways "Game of Life"
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class MVCGameOfLife extends JApplet
{	
	//Private and public members
	private static final long serialVersionUID = 1L;
	private JDesktopPane desk;
    
	/**
	 * Constructor
	 */
	public MVCGameOfLife() 
	{
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
		child.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		desk.add(child);
		child.setVisible(true);
	}
}
