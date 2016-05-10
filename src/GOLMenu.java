import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** 
 * Class which creates a menu for Conways "Game of Life"
 * MVC: Controller
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */

public class GOLMenu extends JFrame
{
	private static final long serialVersionUID = 1L;
	//Private and public members
	public static boolean isNewGame = false;
	public static GameOfLifeBoard newGolBoard;
	private Container cp = new Container();
	JLabel rowAmount = new JLabel("Row Amount");
	JLabel columnAmount = new JLabel ("Column Amount");
	JLabel block = new JLabel("Block Size");
	public static JTextField rowNumber = new JTextField();
	public static JTextField columnNumber = new JTextField();
	public static JTextField blockSize = new JTextField();
	JButton newGame = new JButton("New Game Of Life");
	JPanel wholeWindow = new JPanel();
	
	public GOLMenu()
	{	
		this.setSize(250,150);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
		wholeWindow.setLayout(new FlowLayout(FlowLayout.CENTER));
		newGame.setPreferredSize(new Dimension(150,20));
		newGame.addActionListener(new ActionListener()
		{		
			public void actionPerformed(ActionEvent e) 
			{	
				GOLMenu.createNewGame();
			}			
		});
		wholeWindow.add(newGame);
		cp.add(wholeWindow);	
	}	
	
	/**
	 * Method which allows to create multiple independent games
	 */
	public static void createNewGame()
	{	
		GameOfLifeChildFrame.gameNr++;
		GOLMenu.isNewGame = true;
		MVCGameOfLife gameOfLife = new MVCGameOfLife();
		gameOfLife.addChild(new GOLInternalMenu(), 20, 20);	
		try
		{
			newGolBoard = new GameOfLifeBoard(new Dimension(Integer.parseInt(GOLMenu.rowNumber.getText()), Integer.parseInt(GOLMenu.columnNumber.getText())));
		}
		catch (NumberFormatException n)
		{
			JOptionPane.showMessageDialog(null, "Row number, column number or both are invalid or empty! Please type in whole numbers in the respective text boxes", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		GameOfLifeChildFrame golChild = new GameOfLifeChildFrame(gameOfLife, newGolBoard);
		gameOfLife.addChild(golChild, 30, 30);
		Thread gameOfLifeThread = new Thread(golChild);
		gameOfLifeThread.start();
		Konsole.run(gameOfLife, 512, 512); 
	}
	
	public static void main(String[] args)
	{
		GOLMenu newMenu = new GOLMenu();
		newMenu.setTitle("Game Of Life Menu");
		newMenu.setVisible(true);		
	}
}
