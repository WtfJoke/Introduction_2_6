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
 * Class which creates a model for Conways "Game of Life"
 * MVC: Model
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLife extends JApplet implements MouseListener, MouseMotionListener,Runnable
{	
	//Private and public members
	private static final long serialVersionUID = 1L;
	private JDesktopPane desk;
	private Dimension gameBoardSize = null;
    private ArrayList<CellButton> boardButtons = new ArrayList<>(0);
    private boolean mouseIsDragging = false;
	Container container = new Container();
    
	/**
	 * Constructor
	 */
	public GameOfLife() 
	{
		desk = new JDesktopPane(); //
		desk.setDesktopManager(new DefaultDesktopManager());
		setContentPane(desk);
	    addMouseListener(this); 
	    addMouseMotionListener(this);
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
		child.setSize(256, 256);
		child.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		desk.add(child);
		child.setVisible(true);
	}
	
	/**
	 * Method which sets the amount of rows and columns of the game board and creates it
	 * @param newDimension the amount of rows and columns of the game board to be set (Dimension)
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
				tmpButton.addMouseMotionListener(this);
				container.add(tmpButton);
				CellButton tmpCellButton = new CellButton(tmpButton, new Cell(i, j));
				boardButtons.add(tmpCellButton);
				tmpButton.setBackground(boardButtons.get(i * j).getCell().cellColor);
			}
		}
	}
	
	/**
	 * Method which updates the game board by adding JButtons in its respective color to it
	 */
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
	
	/**
	 * Method which checks whether the left mouse button is released or not
	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) 
	{	
		mouseIsDragging = false;
	}
	
	/**
	 * Method which checks whether the left mouse button is pressed or not
	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mousePressed(MouseEvent e) 
	{	
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			mouseIsDragging = true;
		}
	}
	
	/**
	 * Method which revives several cells if while dragging with the left mouse button
	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) 
	{
		if(mouseIsDragging && GameOfLifeChild.menuModePaint.isSelected())
		{
			if(e.getSource() instanceof JButton)
			{
				JButton sourceButton = (JButton)e.getSource();
				int index = 0;
				for(CellButton cellButton : boardButtons)
				{
					if(cellButton.getButton().equals(sourceButton) && !(boardButtons.get(index).getCell().isAlive))
					{
						boardButtons.get(index).getCell().reborn();
					}
					index++;
				}	
			}
			container.removeAll();
			showBoard();
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Method which revives or kills a cell if clicked on it
	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if(e.getSource() instanceof JButton && GameOfLifeChild.menuModeSet.isSelected() && e.getButton() == MouseEvent.BUTTON1)
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
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
	/**
	 * Method which runs the game logic as a thread
	 * MVC: Controller
	 */
	public void run()
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Method which returns an array list of CellButton objects for the Action event "Reset"
	 * @return array list of CellButton objects (ArrayList<CellButton>)
	 */
	public ArrayList<CellButton> getboardButtons()
	{
		return boardButtons;
	}
}
