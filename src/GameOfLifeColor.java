import java.awt.Color;

/**
 * Class which creates the cell colors for Conways "Game Of Life"
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */

public class GameOfLifeColor
{
	// Private members
	private Color color;
	private String colorString;
	
	/**
	 * Constructor
	 * @param colorString name of the color (String)
	 * @param color color of the respective color name (Color)
	 */
	public GameOfLifeColor(String colorString, Color color)
	{
		this.colorString = colorString;
		this.color = color;
	}

	/**
	 * @return the color
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * @return the colorString
	 */
	public String getColorString()
	{
		return colorString;
	}

}
