import java.awt.Color;
import java.awt.Point;

/** 
 * Class for cells
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class Cell 
{
	Point cellPoint = new Point();
	boolean isAlive = false;
	Color cellColor = Color.RED;

	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public Cell(int x, int y) 
	{
		cellPoint.setLocation(x, y);
	}
	
	/**
	 * Method ... 
	 */
	public void die()
	{
		isAlive = false;
		cellColor = Color.RED;
	}

	/**
	 * Method ... 
	 */
	public void reborn()
	{
		isAlive = true;
		cellColor = Color.GREEN;
	}
	
	/**
	 * Method ... 
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
	 * Method ... 
	 */
	public void setColor(Color newColor)
	{
		cellColor = newColor;
	}
	
	/**
	 * Getter ...
	 * @return the cellPoint
	 */
	public Point getCellPoint() 
	{
		return cellPoint;
	}

	/**
	 * Setter ...
	 * @param cellPoint the cellPoint to set
	 */
	public void setCellPoint(Point cellPoint) 
	{
		this.cellPoint = cellPoint;
	}

	/**
	 * Getter ...
	 * @return the isAlive
	 */
	public boolean isAlive() 
	{
		return isAlive;
	}

}
