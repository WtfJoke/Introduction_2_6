import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JButton;

/** 
 * Class which creates a model for Conways "Game of Life"
 * MVC: Model
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLife extends Observable
{
	private Dimension gameBoardSize = null;
	private ArrayList<CellButton> boardButtons = new ArrayList<>(0);
	// Rewrite Logic to 2D Array with size of game board to check neighbors
	
	/**
	 * Constructor
	 * @param gameBoardSize
	 */
	public GameOfLife(Dimension gameBoardSize) 
	{
		this.gameBoardSize = gameBoardSize;
		for(int i = 0; i < gameBoardSize.getHeight(); i++)
		{
			for(int j = 0; j < gameBoardSize.getWidth(); j++)
			{
				JButton tmpButton = new JButton();
				CellButton tmpCellButton = new CellButton(tmpButton, new Cell(i, j));
				this.boardButtons.add(tmpCellButton);
				tmpButton.setBackground(tmpCellButton.getCell().cellColor);
			}
		}
	}
	
	/**
	 * Method which sets the amount of rows and columns of the game board and creates it
	 * @param newDimension the amount of rows and columns of the game board to be set (Dimension)
	 */
	public void setGameBoardSize(Dimension newDimension)
	{
		this.boardButtons.clear();
		this.gameBoardSize = newDimension;
		for(int i = 0; i < this.gameBoardSize.getHeight(); i++)
		{
			for(int j = 0; j < this.gameBoardSize.getWidth(); j++)
			{
				JButton tmpButton = new JButton();
				CellButton tmpCellButton = new CellButton(tmpButton, new Cell(i, j));
				this.boardButtons.add(tmpCellButton);
				tmpButton.setBackground(tmpCellButton.getCell().cellColor);
			}
		}
	}

	/**
	 * Setter for boardButtons
	 * @param boardButtons the boardButtons to set
	 */
	public void setBoardButtons(ArrayList<CellButton> boardButtons) 
	{
		this.boardButtons = boardButtons;
	}
	
	/**
	 * Getter for gameBoardSize
	 * @return the gameBoardSize
	 */
	public Dimension getGameBoardSize() 
	{
		return gameBoardSize;
	}

	/**
	 * Getter for boardButtons
	 * @return the boardButtons
	 */
	public ArrayList<CellButton> getBoardButtons() 
	{
		return boardButtons;
	}

}
