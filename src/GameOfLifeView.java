import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/** 
 * Class which creates the views for Conways "Game of Life" children frames
 * MVC: View
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLifeView extends JPanel implements Observer
{
	private static final long serialVersionUID = 1L;
	private Color deadCellColor;
	private Color livingCellColor;
	private int blockSize;
	private GameOfLifeBoard gol;
	
	/**
	 * Constructor
	 * @param gol GameOfLife Reference
	 */
	public GameOfLifeView(GameOfLifeBoard gol)
	{
		this.gol = gol;
	}
	
	/**
	 * Method which updates the game board (GUI)
	 * @param o observable model (Observable)
	 * @param arg object to be updated (Object)
	 */
	public void update(Observable o, Object arg) 
	{
		// Update board size?
		revalidate();
		repaint();
	}
	
	/**
	 * Method which updates the game board size 
	 */
	public void updateGameBoardSize()
	{
		ArrayList<Point> removeDeadCellList = new ArrayList<Point>(0);
		ArrayList<Point> removeLivingCellList = new ArrayList<Point>(0);
		for (Point current : gol.getDeadCellList())
		{
			if ((current.x > (int)gol.getGameBoardSize().getWidth()-1) || (current.y > (int)gol.getGameBoardSize().getHeight()-1)) 
			{
				removeDeadCellList.add(current);
			}
		}
		for (Point current : gol.getLivingCellList())
		{
			if ((current.x > (int)gol.getGameBoardSize().getWidth()-1) || (current.y > (int)gol.getGameBoardSize().getHeight()-1)) 
			{
				removeLivingCellList.add(current);
			}
		}
		gol.getDeadCellList().removeAll(removeDeadCellList);
		gol.getLivingCellList().removeAll(removeLivingCellList);
		//repaint();
	}
	
	/**
	 * Method which draws the board
	 * @param g Graphic reference
	 */
	protected void paintComponent(Graphics g)
	{     
		super.paintComponent(g);
        try 
        {	
        	for (Point newPoint : gol.getLivingCellList()) 
            {	
        		// Draw new point
                g.setColor(livingCellColor);
                g.fillRect(blockSize + (blockSize*newPoint.x), blockSize + (blockSize*newPoint.y), blockSize, blockSize);
                gol.hasChanged();
                gol.notifyObservers();
            }
            for(Point newPoint : gol.getDeadCellList())
            {	
                g.setColor(deadCellColor);
                g.fillRect(blockSize + (blockSize*newPoint.x), blockSize + (blockSize*newPoint.y), blockSize, blockSize);
                gol.hasChanged();
                gol.notifyObservers();
            }
        }
        catch (ConcurrentModificationException cme) {}
        // Setup grid
        g.setColor(Color.BLACK);
        // Need to implement dead cell draw logic
        for (int i=0; i<=(int)gol.getGameBoardSize().getWidth(); i++) 
        {
        	g.drawLine(((i*blockSize)+blockSize), blockSize, (i*blockSize)+blockSize, blockSize + (blockSize*(int)gol.getGameBoardSize().getHeight()));
        }
        for (int i=0; i<=(int)gol.getGameBoardSize().getHeight(); i++) 
        {
            g.drawLine(blockSize, ((i*blockSize)+blockSize), blockSize*((int)gol.getGameBoardSize().getWidth()+1), ((i*blockSize)+blockSize));
        }
	}
	
	/**
	 * Getter for Block Size
	 * @return the blockSize
	 */
	public int getBlockSize() 
	{
		return blockSize;
	}

	/**
	 * Setter for Block Size
	 * @param blockSize the blockSize to set
	 */
	public void setBlockSize(int blockSize) 
	{
		this.blockSize = blockSize;
	}
	
	/**
	 * Setter for living cell color
	 * @param newColor the new color of the living cell to set
	 */
	public void setLivingCellColor(Color newColor)
	{
		this.livingCellColor = newColor;
	}
	
	/**
	 * Setter for dead cell color
	 * @param newColor the new color of the dead cell to set
	 */
	public void setDeadCellColor(Color newColor)
	{
		this.deadCellColor = newColor;
	}
}
