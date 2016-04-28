import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** 
 * Class which runs a JApplet, JFrame or JPanel as a Java application
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
class Konsole 
{
	/**
	 * Method which returns name of the running JApplet, JFrame or JPanel
	 * @param o JApplet, JPanel or JFrame object
	 * @return t Class name of running JApplet, JFrame of JPanel
	 */
	public static String title(Object o) 
	{
		String t = o.getClass().toString();
		if(t.indexOf("class") != -1) t = t.substring(6);
		System.out.println ("Konsole: running "+t);
		return t;
	}
  
	/**
	 * Method which determines how to close a JFrame
	 * @param frame JFrame object
	 */
	public static void setupClosing(JFrame frame) 
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
  
	/**
	 * Method which runs a JFrame
	 * @param frame JFrame object
	 * @param width width of the JFrame (int)
	 * @param height height of the JFrame (int)
	 */
	public static void run(JFrame frame, int width, int height) 
	{
	    setupClosing(frame);
	    frame.setSize(width, height);
	    frame.setVisible(true);
	}
	
	/**
	 * Method which runs a JApplet
	 * @param frame JApplet object
	 * @param width width of the JApplet (int)
	 * @param height height of the JApplet (int)
	 */
	public static void run(JApplet applet, int width, int height) 
	{
		JFrame frame = new JFrame(title(applet));
		setupClosing(frame);
		frame.getContentPane().add(applet);
		frame.setSize(width, height);
		applet.init();
		applet.start();
		frame.setVisible(true);
	}
  
	/**
	 * Method which runs a JPanel
	 * @param frame JPanel object
	 * @param width width of the JPanel (int)
	 * @param height height of the JPanel (int)
	 */
	public static void run(JPanel panel, int width, int height) 
	{
		JFrame frame = new JFrame(title(panel));
		setupClosing(frame);
		frame.getContentPane().add(panel);
		frame.setSize(width, height);
		frame.setVisible(true);
	}
}
