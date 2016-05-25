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

public class GameOfLifeMenu extends JFrame
{
	private static final long serialVersionUID = 1L;
	// Private and public members
	public static boolean isNewGame = false;
	public static GameOfLifeMenu newMenu;
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
	
	/**
	 * Constructor
	 */
	public GameOfLifeMenu()
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
			/**
			 * Method which creates a new independent "Game Of Life"
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{	
				GameOfLifeMenu.createNewGame();
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
		GameOfLifeMenu.isNewGame = true;
		MVCGameOfLife gameOfLife = new MVCGameOfLife();
		gameOfLife.addChild(new GameOfLifeNewViewOptionMenu(), 20, 20);	
		GameOfLifeView golView = new GameOfLifeView(newGolBoard);
		try
		{	
			if(Integer.parseInt(GameOfLifeMenu.rowNumber.getText()) < 10 || Integer.parseInt(GameOfLifeMenu.rowNumber.getText()) > 60)
			{
				JOptionPane.showMessageDialog(newMenu, "Row number must be between 10 and 60, Column number between 10 and 30 and block size number between 20 and 30", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(Integer.parseInt(GameOfLifeMenu.columnNumber.getText()) < 10 || Integer.parseInt(GameOfLifeMenu.columnNumber.getText()) > 30)
			{
				JOptionPane.showMessageDialog(newMenu, "Row number must be between 10 and 60, Column number between 10 and 30 and block size number between 20 and 30", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(Integer.parseInt(GameOfLifeMenu.blockSize.getText()) < 20 || Integer.parseInt(GameOfLifeMenu.blockSize.getText()) > 30)
			{
				JOptionPane.showMessageDialog(newMenu, "Row number must be between 10 and 60, Column number between 10 and 30 and block size number between 20 and 30", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}	
			newGolBoard = new GameOfLifeBoard(new Dimension(Integer.parseInt(GameOfLifeMenu.rowNumber.getText()), Integer.parseInt(GameOfLifeMenu.columnNumber.getText())));	
			golView.setBlockSize(Integer.parseInt(GameOfLifeMenu.blockSize.getText()));	
		}
		catch (NumberFormatException n)
		{
			return;
		}
		GameOfLifeChildFrame.gameNr++;
		GameOfLifeChildFrame golChild = new GameOfLifeChildFrame(gameOfLife, newGolBoard);
		gameOfLife.addChild(golChild, 30, 30);
		Thread gameOfLifeThread = new Thread(golChild);
		gameOfLifeThread.start();
		Konsole.run(gameOfLife, 512, 512); 
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{	
		newMenu = new GameOfLifeMenu();
		newMenu.setTitle("Game Of Life Menu");
		newMenu.setVisible(true);		
	}
}
