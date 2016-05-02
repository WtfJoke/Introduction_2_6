import java.awt.Color;
import java.awt.Point;

/** 
 * Class which create cells for Conway's "Game of Life"
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class Cell 
{
	public Point cellPoint = new Point();
	boolean isAlive = false;
	Color cellColor = Color.RED;

	/**
	 * Constructor
	 * @param x X-Position of the cell (int)
	 * @param y Y-Position of the cell (int)
	 */
	public Cell(int x, int y) 
	{
		cellPoint.setLocation(x, y);
	}
	
	/**
	 * Method which makes a cell die
	 */
	public void die()
	{
		isAlive = false;
		cellColor = Color.RED;
	}

	/**
	 * Method which revives a cell
	 */
	public void reborn()
	{
		isAlive = true;
		cellColor = Color.GREEN;
	}
	
	/**
	 * Method which switches the state of a cell from alive to dead and other way around
	 */
	public void switchState()
	{
		if(isAlive)
		{
			isAlive = false;
			cellColor = Color.RED;
		}
		else
		{
			isAlive = true;
			cellColor = Color.GREEN;
		}
	}
	
	/**
	 * Method which sets the color of the cell
	 * @param newColor new color of the cell (Color)
	 */
	public void setColor(Color newColor)
	{
		cellColor = newColor;
	}
	
	/**
	 * Method which returns the point of the cell
	 * @return point of the cell (Point)
	 */
	public Point getCellPoint() 
	{
		return cellPoint;
	}

	/**
	 * Method which sets the point of a cell
	 * @param cellPoint point of the cell to set
	 */
	public void setCellPoint(Point cellPoint) 
	{
		this.cellPoint = cellPoint;
	}

	/**
	 * Method to check whether the cell is alive or not
	 * @return is alive or not (boolean)
	 */
	public boolean isAlive() 
	{
		return isAlive;
	}

}
