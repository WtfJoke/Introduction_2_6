import javax.swing.JButton;

/** 
 * Class which connects an Cell object with a JButton object
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class CellButton extends JButton
{	
	//Private members
	private static final long serialVersionUID = 1L;
	private JButton button = new JButton();
	private Cell cell = new Cell(0, 0);
	
	/**
	 * Constructor
	 * @param newButton new JButton of the CellButton object
	 * @param newCell new Cell of the CellButton object
	 */
	public CellButton(JButton newButton, Cell newCell) 
	{
		button = newButton;
		cell = newCell;
	}

	/**
	 * Method which returns the JButton of a CellButton object
	 * @return JButton of a CellButton object (JButton)
	 */
	public JButton getButton() 
	{
		return button;
	}

	/**
	 * Method which sets a JButton to a CellButton object
	 * @param button the JButton to be set
	 */
	public void setButton(JButton button) 
	{
		this.button = button;
	}

	/**
	 * Method which returns the cell of a CellButton object
	 * @return Cell of a CellButton object (Cell)
	 */
	public Cell getCell() 
	{
		return cell;
	}

	/**
	 * Method which sets a cell to a CellButton object
	 * @param cell Cell object to be set
	 */
	public void setCell(Cell cell) 
	{
		this.cell = cell;
	}
}
