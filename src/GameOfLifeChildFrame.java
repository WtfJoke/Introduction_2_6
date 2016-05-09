import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/** 
 * Class which creates the views for Conways "Game of Life" children frames
 * MVC: View
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLifeChildFrame extends JInternalFrame implements MouseListener, MouseMotionListener, Observer, Runnable
{	
	//Private and public members
	private static final long serialVersionUID = 1L;
	public static int gameNr = 0, xpos = 30, ypos = 30;
	private static Color livingCellColor;
	private static Color deadCellColor;
	static final Color[] col = {Color.red, Color.green};
    private boolean mouseIsDragging = false;
    private boolean startGame = false;
    private int sleepTime = 2000;
	private Container cp = new Container();
	private MVCGameOfLife mvcGOL;
	private GameOfLifeBoard golBoard;
	private GameOfLifeView golView;
	
	JMenuBar menuBar = new JMenuBar();
	JMenu menuMode = new JMenu("Mode");
	JCheckBoxMenuItem menuModeSet = new JCheckBoxMenuItem("Set");
	JCheckBoxMenuItem menuModePaint = new JCheckBoxMenuItem("Paint");
	JMenuItem menuModeRun = new JMenuItem("Run");
	JMenuItem menuModeStop = new JMenuItem("Pause");
	JMenuItem menuModeReset = new JMenuItem("Reset");
	
	JMenu menuVelocity = new JMenu("Velocity");
	JMenuItem menuVelocityFaster = new JMenuItem("Faster (2x)");
	JMenuItem menuVelocitySlower = new JMenuItem("Slower (0.5x)");
	
	JMenu menuWindow = new JMenu("Window");
	JMenuItem menuWindowNew = new JMenuItem("New View");
	JMenuItem menuWindowResize = new JMenuItem("Resize");
	JMenuItem menuWindowAlignment = new JMenuItem("Change Alignment");
	
	JMenu menuFigure = new JMenu("Figure");
	JMenuItem menuFigureGlider = new JMenuItem("Glider");
	JMenuItem menuWindowSpaceship = new JMenuItem("Light-Weight Spaceship");
	
	JPopupMenu popup = new JPopupMenu();
	JCheckBoxMenuItem livingCells = new JCheckBoxMenuItem("Living Cells");
	JCheckBoxMenuItem deadCells = new JCheckBoxMenuItem("Dead Cells");
	JMenuItem black = new JMenuItem("Black");
	JMenuItem blue = new JMenuItem("Blue");
	JMenuItem cyan = new JMenuItem("Cyan");
	JMenuItem darkGray = new JMenuItem("Darkgray");
	JMenuItem gray = new JMenuItem("Gray");
	JMenuItem green = new JMenuItem("Green");
	JMenuItem lightGray = new JMenuItem("Lightgray");
	JMenuItem magenta = new JMenuItem("Magenta");
	JMenuItem orange = new JMenuItem("Orange");
	JMenuItem pink = new JMenuItem("Pink");
	JMenuItem red = new JMenuItem("Red");
	JMenuItem white = new JMenuItem("White");
	JMenuItem yellow = new JMenuItem("Yellow");
	
	
	//Dimension boardDimension = new Dimension(10, 10); Not necessary???
	
	/**
	 * Constructor
	 * @param mvcGO	MVCGameOfLife reference
	 * @param golBoard GameOfLife reference
	 */
	public GameOfLifeChildFrame(MVCGameOfLife mvcGOL, GameOfLifeBoard golBoard) 
	{	
		super("Game of Life " + (gameNr) + " - View", true, true);
		this.setSize(250,330);
		this.mvcGOL = mvcGOL;
		this.golBoard = golBoard;
		this.golView = new GameOfLifeView(this.golBoard);
		this.golBoard.addObserver(golView);
		if(MVCGameOfLife.isNewGame)
		{
			this.golBoard.setupGameBoard();
			this.golView.deadView = Color.RED;
			this.golView.livingView = Color.GREEN;
			livingCellColor = Color.GREEN;
			deadCellColor = Color.RED;
			MVCGameOfLife.isNewGame = false;
			//this.golBoard.hasChanged();
			//this.golBoard.notifyObservers();
		}
		else
		{
			this.golView.livingView = livingCellColor;
			this.golView.deadView = deadCellColor;
		}
		createMenu();
		cp = getContentPane();
	    addMouseListener(this); 
	    addMouseMotionListener(this);
	    this.golView.updateGameBoardSize();
	    cp.add(this.golView);
		setContentPane(cp);	    
		setIconifiable(true); 
		setMaximizable(true);
		setVisible(true);
	}
	
	/**
	 * Method which updates the game board
	 * @param o observable model (Observable)
	 * @param arg object to be updated (Object)
	 */
	public void update(Observable o, Object arg) 
	{
		revalidate();
		repaint();
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
		menuModeRun.addActionListener(new ActionListener()
		{	
			/**
			 * Method which runs the game
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{
				startGame = true;
			}		
		});
		menuMode.add(menuModeStop);
		menuModeStop.addActionListener(new ActionListener()
		{	
			/**
			 * Method which pauses the game
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{
				startGame = false;
			}		
		});
		menuMode.addSeparator();
		menuMode.add(menuModeReset);
		menuModeReset.addActionListener(new ActionListener()
		{	
			/**
			 * Method which resets the board by adding dead cells to it
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{
				golBoard.setupGameBoard();
				golView.updateGameBoardSize();	
				golBoard.boardChanged();
				golBoard.notifyObservers(golView);				
			}		
		});
		menuBar.add(menuMode);
		menuVelocity.add(menuVelocityFaster);
		menuVelocityFaster.addActionListener(new ActionListener() 
		{
			/**
			 * Method which makes the game speed run twice as fast
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{	
				sleepTime = 1000;
			}
		});
		menuVelocity.add(menuVelocitySlower);
		menuVelocitySlower.addActionListener(new ActionListener() 
		{
			/**
			 * Method which reduces the game speed by half
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{	
				sleepTime = 4000;
			}
		});
		menuBar.add(menuVelocity);
		menuWindow.add(menuWindowNew);
		menuWindow.add(menuWindowResize);
		menuWindow.add(menuWindowAlignment);
		menuWindowAlignment.addActionListener(new ActionListener() 
		{
			/**
			 * Method which creates a new view of the model by creating a new child frame 
			 * and by changing the alignment of the board
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{	
				MVCGameOfLife.isNewGame = false;
				golBoard.setGameBoardSize(new Dimension(golBoard.getGameBoardSize().height, golBoard.getGameBoardSize().width)); 
				mvcGOL.addChild(new GameOfLifeChildFrame(mvcGOL, golBoard), 20, 20);	
			}
		});
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
				GameOfLifeChildFrame golChild = new GameOfLifeChildFrame(mvcGOL, golBoard);
				mvcGOL.addChild(golChild, 20, 20);
				Thread gameOfLifeThread = new Thread(golChild);
				gameOfLifeThread.start();
			}
		});
		popup.add(livingCells);
		livingCells.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				livingCells.setSelected(true);
				deadCells.setSelected(false);
			}
		});
		popup.add(deadCells);
		deadCells.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				livingCells.setSelected(false);
				deadCells.setSelected(true);
			}
		});
		popup.addSeparator();
		popup.add(black);
		black.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells black
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.BLACK;
					livingCellColor = Color.BLACK;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.BLACK;
					deadCellColor = Color.BLACK;
	                repaint();
				}
			}
		});
		popup.add(blue);
		blue.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells blue
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.BLUE;
					livingCellColor = Color.BLUE;
					repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.BLUE;
					deadCellColor = Color.BLUE;
					repaint();
				}
			}
		});
		popup.add(cyan);
		cyan.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells cyan
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.CYAN;
					livingCellColor = Color.CYAN;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.CYAN;
					deadCellColor = Color.CYAN;
	                repaint();
				}
			}
		});
		popup.add(darkGray);
		darkGray.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells dark gray
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.DARK_GRAY;
					livingCellColor = Color.DARK_GRAY;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.DARK_GRAY;
					deadCellColor = Color.DARK_GRAY;
	                repaint();
				}
			}
		});
		popup.add(gray);
		gray.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells gray
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.GRAY;
					livingCellColor = Color.GRAY;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.GRAY;
					deadCellColor = Color.GRAY;
	                repaint();
				}
			}
		});
		popup.add(green);
		green.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells green
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.GREEN;
					livingCellColor = Color.GREEN;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.GREEN;
					deadCellColor = Color.GREEN;
	                repaint();
				}
			}
		});
		popup.add(lightGray);
		lightGray.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells light gray
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.LIGHT_GRAY;
					livingCellColor = Color.LIGHT_GRAY;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.LIGHT_GRAY;
					deadCellColor = Color.LIGHT_GRAY;
	                repaint();
				}
			}
		});
		popup.add(magenta);
		magenta.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells magenta
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.MAGENTA;
					livingCellColor = Color.MAGENTA;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.MAGENTA;
					deadCellColor = Color.MAGENTA;
	                repaint();
				}
			}
		});
		popup.add(orange);
		orange.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells orange
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.ORANGE;
					livingCellColor = Color.ORANGE;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.ORANGE;
					deadCellColor = Color.ORANGE;
	                repaint();
				}
			}
		});
		popup.add(pink);
		pink.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells pink
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.PINK;
					livingCellColor = Color.PINK;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.PINK;
					deadCellColor = Color.PINK;
	                repaint();
				}
			}
		});
		popup.add(red);
		red.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells red
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.RED;
					livingCellColor = Color.RED;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.RED;
					deadCellColor = Color.RED;
	                repaint();
				}
			}
		});
		popup.add(white);
		white.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells white
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.WHITE;
					livingCellColor = Color.WHITE;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.WHITE;
					deadCellColor = Color.WHITE;
	                repaint();
				}
			}
		});
		popup.add(yellow);
		yellow.addActionListener(new ActionListener()
		{	
			/**
			 * Method which sets the color of the cells yellow
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if(livingCells.isSelected())
				{	
					golView.livingView = Color.YELLOW;
					livingCellColor = Color.YELLOW;
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.deadView = Color.YELLOW;
					deadCellColor = Color.YELLOW;
	                repaint();
				}
			}
		});
	}
	
	/**
	 * Method which checks whether the left mouse button is released or not
	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) 
	{	
		mouseIsDragging = false;
		if(e.getButton() == MouseEvent.BUTTON3 && e.isPopupTrigger())
		{
			popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	/**
	 * Method which revives or kills a cell
	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if(menuModeSet.isSelected() && e.getButton() == MouseEvent.BUTTON1)
		{
			int x = e.getPoint().x/golView.getBlockSize()-1;
			int y = e.getPoint().y/golView.getBlockSize()-4;
	        if ((x >= 0) && (x < golBoard.getGameBoardSize().getWidth()) && (y >= 0) && (y < golBoard.getGameBoardSize().getHeight())) 
	        {
	        	if(golBoard.getDeadCellList().contains(new Point(x, y)))
	        	{
	        		golBoard.removeDeadCell(x,y);
		        	golBoard.addLivingCell(x,y);
	        	}
	        	else if(golBoard.getLivingCellList().contains(new Point(x,y)))
	        	{
	        		golBoard.removeLivingCell(x,y);
		        	golBoard.addDeadCell(x,y);
	        	}
	        }
	        golBoard.boardChanged();
	        golBoard.notifyObservers();
		}
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
	public void mouseDragged(MouseEvent e) 
	{
		if(mouseIsDragging && menuModePaint.isSelected())
		{
			int x = e.getPoint().x/golView.getBlockSize()-1;
	        int y = e.getPoint().y/golView.getBlockSize()-4;
	        if ((x >= 0) && (x < golBoard.getGameBoardSize().getWidth()) && (y >= 0) && (y < golBoard.getGameBoardSize().getHeight())) 
	        {
	        	if(golBoard.getDeadCellList().contains(new Point(x, y)))
	        	{
		        	golBoard.removeDeadCell(x,y);
		        	golBoard.addLivingCell(x,y);
	        	}
	        }
	        golBoard.boardChanged();
	        golBoard.notifyObservers();
	  	}	
	}
	
	/**
	 * Method which runs the game logic as a thread
	 */
	@Override
	public void run()
	{
		while(true)
		{
			if(startGame)
			{
				ArrayList<Point> survivingCells = new ArrayList<Point>(0);
	            // Iterate through the array, follow game of life rules
				int boardWidth = golBoard.getGameBoardSize().getSize().width;
				int boardHeight = golBoard.getGameBoardSize().getSize().height;
				boolean[][] gameBoard = new boolean[boardWidth][boardHeight];
	            for (Point livingPoint : golBoard.getLivingCellList()) 
	            {
	                gameBoard[livingPoint.x][livingPoint.y] = true;	// 
	            }
	            int surrounding = 0;
	            for (int i = 0; i < boardWidth; i++) 
	            {
	                for (int j = 0; j < boardHeight; j++) 
	                {
	                	surrounding = 0;
	                    if(i - 1 < 0) // <- X * ?
	                    {
	                    	if(j - 1 < 0) // ? is additionally over the negative y border
	                    	{
	                        	if (gameBoard[boardWidth-1][boardHeight-1]) 	
			                    {							// ? * *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[boardWidth-1][j])   
			                    {							// * * *
			                    	surrounding++;			// ? X *
			                    }							// * * *
			                    if (gameBoard[boardWidth-1][j+1]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// ? * *
			                    if (gameBoard[i][boardHeight-1])   
			                    {							// * ? *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i][j+1])   
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * ? *
			                    if (gameBoard[i+1][boardHeight-1]) 
			                    {							// * * ?
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i+1][j])   
			                    {							// * * *
			                    	surrounding++;			// * X ?
			                    }							// * * *
			                    if (gameBoard[i+1][j+1]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * * ?
	                    	}
	                    	else if(j + 1 > boardHeight - 1) // ? is additionally over the positive y border  
	                    	{
	                    		if (gameBoard[boardWidth-1][j-1]) 	
			                    {							// ? * *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[boardWidth-1][j])   
			                    {							// * * *
			                    	surrounding++;			// ? X *
			                    }							// * * *
			                    if (gameBoard[boardWidth-1][0]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// ? * *
			                    if (gameBoard[i][j-1])   
			                    {							// * ? *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i][0])   
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * ? *
			                    if (gameBoard[i+1][j-1]) 
			                    {							// * * ?
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i+1][j])   
			                    {							// * * *
			                    	surrounding++;			// * X ?
			                    }							// * * *
			                    if (gameBoard[i+1][0]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * * ?
	                    	}
	                    	else // Only <- X * ?
	                    	{
	                        	if (gameBoard[boardWidth-1][j-1]) 	
			                    {							// ? * *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[boardWidth-1][j])   
			                    {							// * * *
			                    	surrounding++;			// ? X *
			                    }							// * * *
			                    if (gameBoard[boardWidth-1][j+1]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// ? * *
			                    if (gameBoard[i][j-1])   
			                    {							// * ? *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i][j+1])   
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * ? *
			                    if (gameBoard[i+1][j-1]) 
			                    {							// * * ?
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i+1][j])   
			                    {							// * * *
			                    	surrounding++;			// * X ?
			                    }							// * * *
			                    if (gameBoard[i+1][j+1]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * * ?
	                    	}
	                    }
	                    else if(i + 1 > boardWidth - 1) //  ? * X ->
	                    {
	                    	if(j - 1 < 0) // ? is additionally over the negative y border  
	                    	{
	                        	if (gameBoard[i-1][boardHeight-1]) 	
			                    {							// ? * *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i-1][j])   
			                    {							// * * *
			                    	surrounding++;			// ? X *
			                    }							// * * *
			                    if (gameBoard[i-1][j+1]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// ? * *
			                    if (gameBoard[i][boardHeight-1])   
			                    {							// * ? *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i][j+1])   
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * ? *
			                    if (gameBoard[0][boardHeight-1]) 
			                    {							// * * ?
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[0][j])   
			                    {							// * * *
			                    	surrounding++;			// * X ?
			                    }							// * * *
			                    if (gameBoard[0][j+1]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * * ?
	                    	}
	                    	else if(j + 1 > boardHeight - 1) // ? is additionally over the positive y border  
	                    	{
	                    		if (gameBoard[i-1][j-1]) 	
			                    {							// ? * *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i-1][j])   
			                    {							// * * *
			                    	surrounding++;			// ? X *
			                    }							// * * *
			                    if (gameBoard[i-1][0]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// ? * *
			                    if (gameBoard[i][j-1])   
			                    {							// * ? *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i][0])   
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * ? *
			                    if (gameBoard[0][j-1]) 
			                    {							// * * ?
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[0][j])   
			                    {							// * * *
			                    	surrounding++;			// * X ?
			                    }							// * * *
			                    if (gameBoard[0][0]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * * ?
	                    	}
	                    	else // Only X -> * ?
	                    	{
	                    		if (gameBoard[i-1][j-1]) 	
			                    {							// ? * *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i-1][j])   
			                    {							// * * *
			                    	surrounding++;			// ? X *
			                    }							// * * *
			                    if (gameBoard[i-1][j+1]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// ? * *
			                    if (gameBoard[i][j-1])   
			                    {							// * ? *
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[i][j+1])   
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * ? *
			                    if (gameBoard[0][j-1]) 
			                    {							// * * ?
			                    	surrounding++;			// * X *
			                    }							// * * *
			                    if (gameBoard[0][j])   
			                    {							// * * *
			                    	surrounding++;			// * X ?
			                    }							// * * *
			                    if (gameBoard[0][j+1]) 
			                    {							// * * *
			                    	surrounding++;			// * X *
			                    }							// * * ?
	                    	}
	                    }
	                    else if(j + 1 > boardHeight - 1) // ? is over the positive y border 
	                    {
	                    	// ? is only over the positive y border 
	                    	if (gameBoard[i-1][j-1]) 	
		                    {							// ? * *
		                    	surrounding++;			// * X *
		                    }							// * * *
		                    if (gameBoard[i-1][j])   
		                    {							// * * *
		                    	surrounding++;			// ? X *
		                    }							// * * *
		                    if (gameBoard[i-1][0]) 
		                    {							// * * *
		                    	surrounding++;			// * X *
		                    }							// ? * *
		                    if (gameBoard[i][j-1])   
		                    {							// * ? *
		                    	surrounding++;			// * X *
		                    }							// * * *
		                    if (gameBoard[i][0])   
		                    {							// * * *
		                    	surrounding++;			// * X *
		                    }							// * ? *
		                    if (gameBoard[i+1][j-1]) 
		                    {							// * * ?
		                    	surrounding++;			// * X *
		                    }							// * * *
		                    if (gameBoard[i+1][j])   
		                    {							// * * *
		                    	surrounding++;			// * X ?
		                    }							// * * *
		                    if (gameBoard[i+1][0]) 
		                    {							// * * *
		                    	surrounding++;			// * X *
		                    }							// * * ?
	                    }
	                    else if(j - 1 < 0)  // ? is additionally over the negative y border  
	                    {	
	                    	if (gameBoard[i-1][boardHeight-1]) 	
		                    {							// ? * *
		                    	surrounding++;			// * X *
		                    }							// * * *
		                    if (gameBoard[i-1][j])   
		                    {							// * * *
		                    	surrounding++;			// ? X *
		                    }							// * * *
		                    if (gameBoard[i-1][j+1]) 
		                    {							// * * *
		                    	surrounding++;			// * X *
		                    }							// ? * *
		                    if (gameBoard[i][boardHeight-1])   
		                    {							// * ? *
		                    	surrounding++;			// * X *
		                    }							// * * *
		                    if (gameBoard[i][j+1])   
		                    {							// * * *
		                    	surrounding++;			// * X *
		                    }							// * ? *
		                    if (gameBoard[i+1][boardHeight-1]) 
		                    {							// * * ?
		                    	surrounding++;			// * X *
		                    }							// * * *
		                    if (gameBoard[i+1][j])   
		                    {							// * * *
		                    	surrounding++;			// * X ?
		                    }							// * * *
		                    if (gameBoard[i+1][j+1]) 
		                    {							// * * *
		                    	surrounding++;			// * X *
		                    }							// * * ?
	                    }
	                    else // ? is not crossing any border
                    	{
	                    	if (gameBoard[i-1][j-1]) 	
		                    {							// ? * *
		                    	surrounding++;			// * X *
		                    }							// * * *
		                    if (gameBoard[i-1][j])   
		                    {							// * * *
		                    	surrounding++;			// ? X *
		                    }							// * * *
		                    if (gameBoard[i-1][j+1]) 
		                    {							// * * *
		                    	surrounding++;			// * X *
		                    }							// ? * *
		                    if (gameBoard[i][j-1])   
		                    {							// * ? *
		                    	surrounding++;			// * X *
		                    }							// * * *
		                    if (gameBoard[i][j+1])   
		                    {							// * * *
		                    	surrounding++;			// * X *
		                    }							// * ? *
		                    if (gameBoard[i+1][j-1]) 
		                    {							// * * ?
		                    	surrounding++;			// * X *
		                    }							// * * *
		                    if (gameBoard[i+1][j])   
		                    {							// * * *
		                    	surrounding++;			// * X ?
		                    }							// * * *
		                    if (gameBoard[i+1][j+1]) 
		                    {							// * * *
		                    	surrounding++;			// * X *
		                    }							// * * ?
                    	}
	                    // Check now surrounding counter
	                    if (gameBoard[i][j]) 
	                    {
	                        // Cell is alive, can the cell survive? (2-3)
	                        if ((surrounding == 2) && (surrounding <= 3)) 
	                        {
	                            survivingCells.add(new Point(i,j)); 
	                        }
	                    } 
	                    else 
	                    {
	                        // Cell is dead, will the cell be reborn? (3)
	                        if (surrounding == 3)
	                        {
	                            survivingCells.add(new Point(i,j));
	                        }
	                    }
	                    surrounding = 0;
	                }
	            }
	            golBoard.resetGameBoard(); // Reset board
	            for (int x = 0; x < boardWidth; x++) 
	            {
	                for (int y = 0; y < boardHeight; y++) 
	                {
	                	if(survivingCells.contains(new Point(x, y)))
	                	{
	                		golBoard.addLivingCell(x,y);
	                	}
	                	else
	                	{
	                		golBoard.addDeadCell(x,y);
	                	}
	                }
	            }
	            golBoard.boardChanged();
		        golBoard.notifyObservers();
		        if(golBoard.getLivingCellList().isEmpty())
		        {
		        	startGame = false;
		        }
	            try 
	            {
	                Thread.sleep(sleepTime);
	                run();
	            } 
	            catch(InterruptedException ex) 
	            {}
			}
			else
			{
	            try 
	            {
	                Thread.sleep(1000);
	                run();
	            } 
	            catch(InterruptedException ex) 
	            {}
			}	
		}
	}
	
	// Unused events
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
