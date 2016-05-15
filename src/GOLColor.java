import java.awt.Color;

public class GOLColor
{

	private Color color;

	private String colorString;

	public GOLColor(String colorString, Color color)
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
