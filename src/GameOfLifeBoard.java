import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

/** 
 * Class which creates a model for Conways "Game of Life"
 * MVC: Model
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLifeBoard extends Observable
{
	private Dimension gameBoardSize = null;
	private ArrayList<Point> cellList = new ArrayList<Point>(0);
	//private ArrayList<Point> deadCellList = new ArrayList<Point>(0);
	
	/**
	 * Method to call setChanged
	 */
	public void boardChanged()
	{
		setChanged();
	}
	
	/**
	 * Add cells to cellList
	 * @param x
	 * @param y
	 */
	public void addCell(int x, int y) 
	{
        if (!cellList.contains(new Point(x,y))) 
        {
        	cellList.add(new Point(x,y));
        }
    }
	
	/**
	 * Remove specific cell from cellList
	 * @param x
	 * @param y
	 */
	public void removeCell(int x, int y) 
	{
		cellList.remove(new Point(x,y));
    }
	
	/**
	 * Getter for cellList
	 * @return the cellList
	 */
	public ArrayList<Point> getCellList() 
	{
		return cellList;
	}

	/**
	 * Setter for cellList
	 * @param cellList the cellList to set
	 */
	public void setCellList(ArrayList<Point> cellList) 
	{
		this.cellList = cellList;
	}

	/**
	 * Constructor
	 * @param gameBoardSize
	 */
	public GameOfLifeBoard(Dimension gameBoardSize) 
	{
		this.gameBoardSize = gameBoardSize;
	}
	
	/**
	 * Method to reset point list
	 */
	public void resetGameBoard()
	{
		cellList.clear();
	}
	
	/**
	 * Method which sets the amount of rows and columns of the game board and creates it
	 * @param newDimension the amount of rows and columns of the game board to be set (Dimension)
	 */
	public void setGameBoardSize(Dimension newDimension)
	{
		this.cellList.clear();
		this.gameBoardSize = new Dimension((int)(newDimension.getWidth()/20-2),(int) (newDimension.getHeight()/20-2)); 
	}

	/**
	 * Getter for gameBoardSize
	 * @return the gameBoardSize
	 */
	public Dimension getGameBoardSize() 
	{
		return gameBoardSize;
	}

}
