import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/** 
 * Class which creates the views for Conways "Game of Life" children frames
 * MVC: View
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLifeChild extends JInternalFrame implements Observer
{
	private static final long serialVersionUID = 1L;
	static int nr = - 1, xpos = 30, ypos = 30;
	static final Color[] col = {Color.red, Color.green};
	Container cp = new Container();
	GameOfLife golRef = new GameOfLife();
	
	JMenu menuMode = new JMenu("Mode");
	static JCheckBoxMenuItem menuModeSet = new JCheckBoxMenuItem("Set");
	static JCheckBoxMenuItem menuModePaint = new JCheckBoxMenuItem("Paint");
	JMenuItem menuModeRun = new JMenuItem("Run");
	JMenuItem menuModeStop = new JMenuItem("Stop");
	JMenuItem menuModeReset = new JMenuItem("Reset");
	
	JMenu menuVelocity = new JMenu("Velocity");
	JMenuItem menuVelocityFaster = new JMenuItem("Faster");
	JMenuItem menuVelocitySlower = new JMenuItem("Slower");
	
	JMenu menuWindow = new JMenu("Window");
	JMenuItem menuWindowNew = new JMenuItem("New");
	JMenuItem menuWindowResize = new JMenuItem("Resize");
	JMenuItem menuWindowAlignment = new JMenuItem("Change Alignment");
	
	JMenu menuFigure = new JMenu("Figure");
	JMenuItem menuFigureGlider = new JMenuItem("Glider");
	JMenuItem menuWindowSpaceship = new JMenuItem("Light-Weight Spaceship");
	
	Dimension boardDimension = new Dimension(10, 10);
	
	/**
	 * Constructor
	 * @param gol GameOfLife Reference
	 */
	public GameOfLifeChild(GameOfLife gol) 
	{
		super("Game of Life " + (++nr), true, true);
		setBackground(col[nr%col.length]);
		golRef = gol;
		
		// Creating Menu
		JMenuBar menuBar = new JMenuBar();
		menuMode.add(menuModeSet);
		menuModeSet.addActionListener(new ActionListener()
		{	
			/**
			 * Method which enables the Set-Mode and disables the Paint-Mode if clicking on it
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{
				menuModeSet.setSelected(true);
				menuModePaint.setSelected(false);
			}
		});
		menuMode.add(menuModePaint);
		menuModePaint.addActionListener(new ActionListener()
		{	
			/**
			 * Method which disables the Set-Mode and enables the Paint-Mode if clicking on it
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{	
				menuModePaint.setSelected(true);
				menuModeSet.setSelected(false);
			}
		});
		menuMode.addSeparator();
		menuMode.add(menuModeRun);
		menuMode.add(menuModeStop);
		menuMode.addSeparator();
		menuMode.add(menuModeReset);
		menuModeReset.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				int index = 0;
				for(CellButton cellButton : golRef.getboardButtons())
				{
					golRef.getboardButtons().get(index).getCell().die();
					index++;
				}
				cp.removeAll();
				golRef.showBoard();
				revalidate();
				repaint();
			}		
		});
		menuBar.add(menuMode);
		menuVelocity.add(menuVelocityFaster);
		menuVelocity.add(menuVelocitySlower);
		menuBar.add(menuVelocity);
		menuWindow.add(menuWindowNew);
		menuWindow.add(menuWindowResize);
		menuWindow.add(menuWindowAlignment);
		menuBar.add(menuWindow);
		menuFigure.add(menuFigureGlider);
		menuFigure.add(menuWindowSpaceship);
		menuBar.add(menuFigure);
		setJMenuBar(menuBar);

		menuWindowNew.addActionListener(new ActionListener() 
		{
			/**
			 * Method which creates a new view of the model by creating a new child frame
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{	
				golRef.addChild(new GameOfLifeChild(golRef), 20, 20);			
			}
		});
		cp = getContentPane();
		cp.setLayout(new GridLayout((int)boardDimension.getWidth(), (int)boardDimension.getHeight()));
		golRef.container = cp;
		golRef.setBoardSize(boardDimension);
		setContentPane(cp);	    
		setIconifiable(true); 
		setMaximizable(true);
	}
	
	/**
	 * Method which updates the game board
	 * @param o observable model (Observable)
	 * @param arg object to be updated (Object)
	 */
	public void update(Observable o, Object arg) 
	{
		// TODO Auto-generated method stub
		
	}
}
