import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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
	private GameOfLifeColor(String colorString, Color color)
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

	public static List<GameOfLifeColor> asList() 
	{
		List<GameOfLifeColor> colors = new ArrayList<>();
		colors.add(new GameOfLifeColor("Black", Color.BLACK));
		colors.add(new GameOfLifeColor("Blue", Color.BLUE));
		colors.add(new GameOfLifeColor("Cyan", Color.CYAN));
		colors.add(new GameOfLifeColor("Darkgray", Color.DARK_GRAY));
		colors.add(new GameOfLifeColor("Gray", Color.GRAY));
		colors.add(new GameOfLifeColor("Green", Color.GREEN));
		colors.add(new GameOfLifeColor("Lightgray", Color.LIGHT_GRAY));
		colors.add(new GameOfLifeColor("Magenta", Color.MAGENTA));
		colors.add(new GameOfLifeColor("Orange", Color.ORANGE));
		colors.add(new GameOfLifeColor("Pink", Color.PINK));
		colors.add(new GameOfLifeColor("Red", Color.RED));
		colors.add(new GameOfLifeColor("White", Color.WHITE));
		colors.add(new GameOfLifeColor("Yellow", Color.YELLOW));
		return colors;
	}

}
