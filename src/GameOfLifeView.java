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
	public Color livingCellColor;
	public Color deadCellColor;
	public final int BLOCK_SIZE = 20;
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
		repaint();
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
                g.setColor(GameOfLifeChildFrame.tempLivingColor);
                g.fillRect(BLOCK_SIZE + (BLOCK_SIZE*newPoint.x), BLOCK_SIZE + (BLOCK_SIZE*newPoint.y), BLOCK_SIZE, BLOCK_SIZE);
                repaint();
            }
            for(Point newPoint : gol.getDeadCellList())
            {
            	g.setColor(GameOfLifeChildFrame.tempDeadColor);
                g.fillRect(BLOCK_SIZE + (BLOCK_SIZE*newPoint.x), BLOCK_SIZE + (BLOCK_SIZE*newPoint.y), BLOCK_SIZE, BLOCK_SIZE);
                repaint();
            }
        } 
        catch (ConcurrentModificationException cme) {}
        // Setup grid
        g.setColor(Color.BLACK);
        // Need to implement dead cell draw logic
        for (int i=0; i<=(int)gol.getGameBoardSize().getWidth(); i++) 
        {
        	g.drawLine(((i*BLOCK_SIZE)+BLOCK_SIZE), BLOCK_SIZE, (i*BLOCK_SIZE)+BLOCK_SIZE, BLOCK_SIZE + (BLOCK_SIZE*(int)gol.getGameBoardSize().getHeight()));
        }
        for (int i=0; i<=(int)gol.getGameBoardSize().getHeight(); i++) 
        {
            g.drawLine(BLOCK_SIZE, ((i*BLOCK_SIZE)+BLOCK_SIZE), BLOCK_SIZE*((int)gol.getGameBoardSize().getWidth()+1), ((i*BLOCK_SIZE)+BLOCK_SIZE));
        }
	}
	
	/**
	 * Setter for living cell color
	 * @param livingCell the livingCell to set
	 */
	public void setLivingCellColor(Color livingCellColor) 
	{
		this.livingCellColor = livingCellColor;
	}

	/**
	 * Setter for dead cell color
	 * @param deadCell the deadCell to set
	 */
	public void setDeadCellColor(Color deadCellColor) 
	{
		this.deadCellColor = deadCellColor;
	}
	
	public Color getDeadCellColor()
	{
		return deadCellColor;
	}
	
	public Color getLivingCellColor()
	{
		return livingCellColor;
	}
}
