import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/** 
 * Class for Conways "Game of Life" children
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
	JMenuItem menuModeSet = new JMenuItem("Set");
	JMenuItem menuModePaint = new JMenuItem("Paint");
	JMenuItem menuModeRun = new JMenuItem("Run");
	
	JMenu menuVelocity = new JMenu("Velocity");
	JMenuItem menuVelocityFaster = new JMenuItem("Faster");
	JMenuItem menuVelocitySlower = new JMenuItem("Slower");
	
	JMenu menuWindow = new JMenu("Window");
	JMenuItem menuWindowNew = new JMenuItem("New");
	JMenuItem menuWindowResize = new JMenuItem("Resize");
	
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
		menuMode.add(menuModePaint);
		menuMode.add(menuModeRun);
		menuBar.add(menuMode);
		menuVelocity.add(menuVelocityFaster);
		menuVelocity.add(menuVelocitySlower);
		menuBar.add(menuVelocity);
		menuWindow.add(menuWindowNew);
		menuWindow.add(menuWindowResize);
		menuBar.add(menuWindow);
		menuFigure.add(menuFigureGlider);
		menuFigure.add(menuWindowSpaceship);
		menuBar.add(menuFigure);
		setJMenuBar(menuBar);

		menuWindowNew.addActionListener(new ActionListener() 
		{
			@Override
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
	

	
	@Override
	public void update(Observable o, Object arg) 
	{
		// TODO Auto-generated method stub
		
	}

}
