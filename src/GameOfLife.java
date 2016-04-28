import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultDesktopManager;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/** 
 * Class for Conways "Game of Life"
 * Using Threads, MDI and MVC
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLife extends JApplet implements MouseListener, MouseMotionListener,Runnable
{
	private static final long serialVersionUID = 1L;
	private JDesktopPane desk;
	private Dimension gameBoardSize = null;
    private ArrayList<Cell> board = new ArrayList<Cell>(0);
	ArrayList<JButton> boardButtons = new ArrayList<JButton>(0);
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
				board.add(new Cell(i, j));
			}
		}
	}
	
	public void showBoard()
	{
		for(int i = 0; i < gameBoardSize.getHeight(); i++)
		{
			for(int j = 0; j < gameBoardSize.getWidth(); j++)
			{
				JButton tmpButton = new JButton();
				tmpButton.setBackground(board.get(i * j).cellColor);
				tmpButton.addMouseListener(this);
				container.add(tmpButton);
				boardButtons.add(tmpButton);
			}
		}
		
	}
	
	/**
	 * Method which set living cell
	 * @param point
	 */
	public void setLivingCell(int indexOfList)
	{
		board.get(indexOfList).switchState();
	}
	
	/**
	 * Method which switch cell
	 * @param cell
	 */
	public void switchCell(Cell cell)
	{
		if(board.contains(cell))
		{
			if(board.get(board.indexOf(cell)).isAlive())
			{
				board.get(board.indexOf(cell)).die();
			}
			else
			{
				board.get(board.indexOf(cell)).reborn();
			}
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
			if(boardButtons.contains(sourceButton))
			{
				setLivingCell(boardButtons.indexOf(sourceButton));
			}
		}
		boardButtons.clear();
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


	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		
	}
}
