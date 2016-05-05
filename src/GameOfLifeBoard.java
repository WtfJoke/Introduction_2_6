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
	private ArrayList<Point> deadCellList = new ArrayList<Point>(0);
	private ArrayList<Point> livingCellList = new ArrayList<Point>(0);
	
	/**
	 * Method to call setChanged
	 */
	public void boardChanged()
	{
		setChanged();
	}
	
	/**
	 * Add a cell to deadCellList
	 * @param x
	 * @param y
	 */
	public void addDeadCell(int x, int y) 
	{
        deadCellList.add(new Point(x,y));
    }
	
	/**
	 * Add a cell to livingCellList
	 * @param x
	 * @param y
	 */
	public void addLivingCell(int x, int y) 
	{
        livingCellList.add(new Point(x,y));
    }
	
	/**
	 * Remove a specific cell from deadCellList
	 * @param x
	 * @param y
	 */
	public void removeDeadCell(int x, int y) 
	{
		deadCellList.remove(new Point(x,y));
    }
	
	/**
	 * Remove a specific cell from livingCellList
	 * @param x
	 * @param y
	 */
	public void removeLivingCell(int x, int y) 
	{
		livingCellList.remove(new Point(x,y));
    }
	
	/**
	 * Getter for deadCellList
	 * @return the deadCellList
	 */
	public ArrayList<Point> getDeadCellList() 
	{
		return deadCellList;
	}
	
	/**
	 * Getter for livingCellList
	 * @return the livingCellList
	 */
	public ArrayList<Point> getLivingCellList() 
	{
		return livingCellList;
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
	 * Method which setup the game board by adding dead cells
	 */
	public void setupGameBoard()
	{	
		deadCellList.clear();
		livingCellList.clear();
		for(int x = 0; x < this.getGameBoardSize().width; x++)
		{
			for(int y = 0; y < this.getGameBoardSize().height; y++)
			{	
				deadCellList.add(new Point(x,y));			
			}
		}
	}
	
	/**
	 * Method which reset the game board
	 */
	public void resetGameBoard()
	{	
		deadCellList.clear();
		livingCellList.clear();
	}
	
	/**
	 * Method which sets the amount of rows and columns of the game board and creates it
	 * @param newDimension the amount of rows and columns of the game board to be set (Dimension)
	 */
	public void setGameBoardSize(Dimension newDimension)
	{
		//this.cellList.clear();
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
