import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.DefaultDesktopManager;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/** 
 * Class for Conways "Game of Life"
 * MVC: Model
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLife extends JApplet implements MouseListener, MouseMotionListener,Runnable
{
	private static final long serialVersionUID = 1L;
	private JDesktopPane desk;
	private Dimension gameBoardSize = null;
    //private Cell board[];
    private ArrayList<CellButton> boardButtons = new ArrayList<>(0);
	public Container container = new Container();
    
	/**
	 * Constructor
	 */
	public GameOfLife() 
	{
		desk = new JDesktopPane(); //
		desk.setDesktopManager(new DefaultDesktopManager()); //
		setContentPane(desk); //
	    addMouseListener(this); 
	    addMouseMotionListener(this);
	}
	
	/**
	 * Method which add children to JDesktopPane
	 * @param child
	 * @param x
	 * @param y
	 */
	public void addChild(JInternalFrame child, int x, int y) 
	{
		child.setLocation(x, y);
		child.setSize(256, 256);
		child.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		desk.add(child);
		child.setVisible(true);
	}
	
	/**
	 * Method which set new size of game board and create it
	 * @param newDimension 
	 */
	public void setBoardSize(Dimension newDimension)
	{
		gameBoardSize = newDimension;
		for(int i = 0; i < gameBoardSize.getHeight(); i++)
		{
			for(int j = 0; j < gameBoardSize.getWidth(); j++)
			{
				JButton tmpButton = new JButton();
				tmpButton.addMouseListener(this);
				container.add(tmpButton);
				CellButton tmpCellButton = new CellButton(tmpButton, new Cell(i, j));
				boardButtons.add(tmpCellButton);
				tmpButton.setBackground(boardButtons.get(i * j).getCell().cellColor);
			}
		}
	}
	
	public void showBoard()
	{
		for(CellButton cellButton : boardButtons)
		{
			JButton tmpButton = cellButton.getButton();
			tmpButton.setBackground(cellButton.getCell().cellColor);
			container.add(tmpButton);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		GameOfLife gameOfLife = new GameOfLife();
		gameOfLife.addChild(new GameOfLifeChild(gameOfLife), 10, 10);
		Konsole.run(gameOfLife, 512, 512);
	}
	
	public void mouseDragged(MouseEvent e)
	{
		// Painting all cells to life
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// Position for new points (living cells)
		if(e.getSource() instanceof JButton)
		{
			JButton sourceButton = (JButton)e.getSource();
			int index = 0;
			for(CellButton cellButton : boardButtons)
			{
				if(cellButton.getButton().equals(sourceButton))
				{
					boardButtons.get(index).getCell().switchState();
				}
				index++;
			}
			
		}
		container.removeAll();
		showBoard();
		revalidate();
		repaint();
		
	}
	
	// Unused events
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}


	// MVC: Controller
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		
	}
}
