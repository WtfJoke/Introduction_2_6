import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
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
public class GameOfLifeChildFrame extends JInternalFrame implements MouseListener, MouseMotionListener, Observer, Runnable
{
	private static final long serialVersionUID = 1L;
	static int nr = - 1, xpos = 30, ypos = 30;
	static final Color[] col = {Color.red, Color.green};
    private boolean mouseIsDragging = false;
	private Container cp = new Container();
	private MVCGameOfLife mvcGOL;
	private GameOfLife gol;
	
	JMenuBar menuBar = new JMenuBar();
	JMenu menuMode = new JMenu("Mode");
	JCheckBoxMenuItem menuModeSet = new JCheckBoxMenuItem("Set");
	JCheckBoxMenuItem menuModePaint = new JCheckBoxMenuItem("Paint");
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
	public GameOfLifeChildFrame(MVCGameOfLife mvcGOL, GameOfLife gol) 
	{
		super("Game of Life " + (++nr), true, true);
		this.mvcGOL = mvcGOL;
		this.gol = gol;
		createMenu();
		cp = getContentPane();
		cp.setLayout(new GridLayout((int)boardDimension.getWidth(), (int)boardDimension.getHeight()));
		//this.gol.setGameBoardSize(boardDimension);
	    addMouseListener(this); 
	    addMouseMotionListener(this);
		for(CellButton cellButton : this.gol.getBoardButtons())
		{
			cellButton.getButton().addMouseListener(this);
			cellButton.getButton().addMouseMotionListener(this);
			cp.add(cellButton.getButton());
		}
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
		
	}
	
	/**
	 * Method which creates a menu
	 */
	public void createMenu()
	{
		// Creating Menu
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
				for(int i = 0; i < gol.getBoardButtons().size(); i++)
				{
					gol.getBoardButtons().get(i).getCell().die();
				}
				cp.removeAll();
				//golRef.showBoard(); Need to be changed
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
				mvcGOL.addChild(new GameOfLifeChildFrame(mvcGOL, gol), 20, 20);
			}
		});
	}
	
	/**
	 * Method which updates the game board by adding JButtons in its respective color to it
	 */
	public void showBoard()
	{
		for(CellButton cellButton : gol.getBoardButtons())
		{
			JButton tmpButton = cellButton.getButton();
			tmpButton.setBackground(cellButton.getCell().cellColor);
			cp.add(tmpButton);
		}
	}
	
	/**
	 * Method which checks whether the left mouse button is released or not
	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) 
	{	
		mouseIsDragging = false;
	}
	
	/**
	 * Method which checks whether the left mouse button is pressed or not
	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mousePressed(MouseEvent e) 
	{	
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			mouseIsDragging = true;
		}
	}
	
	/**
	 * Method which revives several cells if while dragging with the left mouse button
	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) 
	{
		if(mouseIsDragging && menuModePaint.isSelected())
		{
			if(e.getSource() instanceof JButton)
			{
				JButton sourceButton = (JButton)e.getSource();
				int index = 0;
				for(CellButton cellButton : gol.getBoardButtons())
				{
					if(cellButton.getButton().equals(sourceButton) && !(gol.getBoardButtons().get(index).getCell().isAlive))
					{
						gol.getBoardButtons().get(index).getCell().reborn();
					}
					index++;
				}	
			}
			cp.removeAll();
			showBoard();
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Method which revives or kills a cell if clicked on it
	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if(e.getSource() instanceof JButton && menuModeSet.isSelected() && e.getButton() == MouseEvent.BUTTON1)
		{
			JButton sourceButton = (JButton)e.getSource();
			int index = 0;
			for(CellButton cellButton : gol.getBoardButtons())
			{
				if(cellButton.getButton().equals(sourceButton))
				{
					gol.getBoardButtons().get(index).getCell().switchState();
				}
				index++;
			}		
		}
		cp.removeAll();
		showBoard();
		revalidate();
		repaint();
	}
	
	/**
	 * Method which runs the game logic as a thread
	 */
	public void run()
	{
		showBoard();
		revalidate();
		repaint();
	}
	
	// Unused events
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
