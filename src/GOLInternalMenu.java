import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** 
 * Class which creates an internal menu for Conways "Game of Life"
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GOLInternalMenu extends JInternalFrame
{	
	private static final long serialVersionUID = 1L;
	//Private and public members
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
	public GOLInternalMenu()
	{	
		super("Game of Life Internal Menu", false, false);
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
