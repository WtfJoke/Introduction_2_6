import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** 
 * Class which creates a option menu for the settings of all subsequent new views of a game
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLifeNewViewOptionMenu extends JInternalFrame
{	
	private static final long serialVersionUID = 1L;
	// Private and public members
	private Container cp = new Container();
	JLabel rowAmount = new JLabel("Row Amount");
	JLabel columnAmount = new JLabel ("Column Amount");
	JLabel block = new JLabel("Block Size");
	public static JTextField rowNumber = new JTextField();
	public static JTextField columnNumber = new JTextField();
	public static JTextField blockSize = new JTextField();
	JPanel wholeWindow = new JPanel();
	
	/**
	 * Constructor
	 */
	public GameOfLifeNewViewOptionMenu()
	{	
		super("New View Settings Menu", false, false);
		this.setSize(250,125);
		cp = getContentPane();
		wholeWindow.setLayout(new FlowLayout(FlowLayout.LEFT));
		rowAmount.setPreferredSize(new Dimension(100,20));
		rowNumber.setPreferredSize(new Dimension(100,20));		
		wholeWindow.add(rowAmount);
		wholeWindow.add(rowNumber);		
		columnAmount.setPreferredSize(new Dimension(100,20));
		columnNumber.setPreferredSize(new Dimension(100,20));
		wholeWindow.add(columnAmount);
		wholeWindow.add(columnNumber);
		block.setPreferredSize(new Dimension(100,20));
		wholeWindow.add(block);
		blockSize.setPreferredSize(new Dimension(100,20));	
		wholeWindow.add(blockSize);
		cp.add(wholeWindow);
		setIconifiable(true); 
		setMaximizable(true);
	}	
}
