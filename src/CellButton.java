import javax.swing.JButton;

/** 
 * Class for cells
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class CellButton extends JButton
{
	private static final long serialVersionUID = 1L;
	private JButton button = new JButton();
	private Cell cell = new Cell(0, 0);
	
	public CellButton(JButton newButton, Cell newCell) 
	{
		button = newButton;
		cell = newCell;
	}

	/**
	 * @return the button
	 */
	public JButton getButton() 
	{
		return button;
	}

	/**
	 * @param button the button to set
	 */
	public void setButton(JButton button) 
	{
		this.button = button;
	}

	/**
	 * @return the cell
	 */
	public Cell getCell() 
	{
		return cell;
	}

	/**
	 * @param cell the cell to set
	 */
	public void setCell(Cell cell) 
	{
		this.cell = cell;
	}
	
	

}
