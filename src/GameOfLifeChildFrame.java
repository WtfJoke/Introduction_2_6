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
import javax.swing.JOptionPane;
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
	JMenuItem menuWindowNew = new JMenuItem("Create New View");
	
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
		
	/**
	 * Constructor
	 * @param mvcGO	MVCGameOfLife reference
	 * @param golBoard GameOfLife reference
	 */
	public GameOfLifeChildFrame(MVCGameOfLife mvcGOL, GameOfLifeBoard golBoard) 
	{	
		super("Game of Life " + (gameNr) + " - View", true, true);
		this.mvcGOL = mvcGOL;
		this.golBoard = golBoard;
		this.golView = new GameOfLifeView(this.golBoard);
		this.golBoard.addObserver(golView);
		this.golBoard.setupGameBoard();
		this.golView.setLivingCellColor(Color.GREEN);
		this.golView.setDeadCellColor(Color.RED);
		if(GOLMenu.isNewGame)
		{	
			try
			{
				this.golView.setBlockSize(Integer.parseInt(GOLMenu.blockSize.getText()));
				this.setSize(Integer.parseInt(GOLMenu.rowNumber.getText()) * Integer.parseInt(GOLMenu.blockSize.getText()) + 50, Integer.parseInt(GOLMenu.columnNumber.getText()) * Integer.parseInt(GOLMenu.blockSize.getText()) + 80);
			}
			catch(NumberFormatException n)
			{
				JOptionPane.showMessageDialog(null, "Block size number invalid or empty! Please type in a whole number in the respective text box", "Error", JOptionPane.ERROR_MESSAGE);
				throw(n);
			}
			GOLMenu.isNewGame = false;
		}
		else
		{	
			try
			{
				this.golView.setBlockSize(Integer.parseInt(GOLInternalMenu.blockSize.getText()));
				this.setSize(Integer.parseInt(GOLInternalMenu.rowNumber.getText()) * Integer.parseInt(GOLInternalMenu.blockSize.getText()) + 50, Integer.parseInt(GOLInternalMenu.columnNumber.getText()) * Integer.parseInt(GOLInternalMenu.blockSize.getText()) + 80);
			}
			catch(NumberFormatException n)
			{
				JOptionPane.showMessageDialog(mvcGOL, "Block size number invalid or empty! Please type in a whole number in the respective text box", "Error", JOptionPane.ERROR_MESSAGE);
				throw(n);
			}		
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
				golBoard.notifyObservers();				
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
		menuWindowNew.addActionListener(new ActionListener() 
		{
			/**
			 * Method which creates a new view of the model by creating a new child frame
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{	
				try
				{
					golBoard.setGameBoardSize(new Dimension(Integer.parseInt(GOLInternalMenu.rowNumber.getText()), Integer.parseInt(GOLInternalMenu.columnNumber.getText())));
				}
				catch(NumberFormatException n)
				{
					JOptionPane.showMessageDialog(mvcGOL, "Row number, column number or both are invalid or empty! Please type in whole numbers in the respective text boxes", "Error", JOptionPane.ERROR_MESSAGE);
					throw(n);
				}				
				GameOfLifeChildFrame golChild = new GameOfLifeChildFrame(mvcGOL, golBoard);
				mvcGOL.addChild(golChild, 20, 20);
				Thread gameOfLifeThread = new Thread(golChild);
				gameOfLifeThread.start();
			}
		});
		menuBar.add(menuWindow);
		menuFigure.add(menuFigureGlider);
		menuFigureGlider.addActionListener(new ActionListener() 
		{
			/**
			 * Method which creates a Glider
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{	
				golBoard.setupGameBoard();
				for(int x = 0; x < golBoard.getGameBoardSize().width; x++)
				{
					for(int y = 0; y < golBoard.getGameBoardSize().height; y++)
					{	
						if(x == 1 && y == 0)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}
						if(x == 2 && y == 1)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}
						if(x == 0 && y == 2)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}	
						if(x == 1 && y == 2)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}	
						if(x == 2 && y == 2)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}	
					}
				}
				if(golBoard.getGameBoardSize().width < 5 || golBoard.getGameBoardSize().height < 5)
				{
					JOptionPane.showMessageDialog(mvcGOL, "Board size too small! Row number and column number of the board must be at least 5", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				repaint();
				golBoard.hasChanged();
				golBoard.notifyObservers();			
			}
		});
		menuFigure.add(menuWindowSpaceship);
		menuWindowSpaceship.addActionListener(new ActionListener() 
		{
			/**
			 * Method which creates a Light-Weight Spaceship
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) 
			{	
				golBoard.setupGameBoard();
				for(int x = 0; x < golBoard.getGameBoardSize().width; x++)
				{
					for(int y = 0; y < golBoard.getGameBoardSize().height; y++)
					{	
						if(x == 0 && y == 0)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}
						if(x == 3 && y == 0)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}
						if(x == 4 && y == 1)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}
						if(x == 0 && y == 2)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}	
						if(x == 4 && y == 2)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}	
						if(x == 1 && y == 3)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}
						if(x == 2 && y == 3)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}	
						if(x == 3 && y == 3)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}	
						if(x == 4 && y == 3)
						{
							golBoard.removeDeadCell(x,y);
							golBoard.addLivingCell(x,y);
						}	
					}
				}
				if(golBoard.getGameBoardSize().width < 7 || golBoard.getGameBoardSize().height < 6)
				{
					JOptionPane.showMessageDialog(mvcGOL, "Board size too small! Row number must be at least 7 and column number must be at least 6", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				repaint();
				golBoard.hasChanged();
				golBoard.notifyObservers();			
			}
		});
		menuBar.add(menuFigure);
		setJMenuBar(menuBar);
		
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
			/**
			 * Method which allows the color of the living cells to be changed
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{	
				/**
				 * Method which allows the color of the dead cells to be changed
				 * @param e Action event to be triggered (ActionEvent)
				 */
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
					golView.setLivingCellColor(Color.BLACK);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.BLACK);
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
					golView.setLivingCellColor(Color.BLUE);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.BLUE);
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
					golView.setLivingCellColor(Color.CYAN);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.CYAN);
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
					golView.setLivingCellColor(Color.DARK_GRAY);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.DARK_GRAY);
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
					golView.setLivingCellColor(Color.GRAY);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.GRAY);
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
					golView.setLivingCellColor(Color.GREEN);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.GREEN);
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
					golView.setLivingCellColor(Color.LIGHT_GRAY);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.LIGHT_GRAY);
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
					golView.setLivingCellColor(Color.MAGENTA);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.MAGENTA);
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
					golView.setLivingCellColor(Color.ORANGE);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.ORANGE);
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
					golView.setLivingCellColor(Color.PINK);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.PINK);
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
					golView.setLivingCellColor(Color.RED);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.RED);
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
					golView.setLivingCellColor(Color.WHITE);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.WHITE);
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
					golView.setLivingCellColor(Color.YELLOW);
	                repaint();
				}
				else if(deadCells.isSelected())
				{	
					golView.setDeadCellColor(Color.YELLOW);
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
	            int surrounding;
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
	                        if ((surrounding == 2) || (surrounding == 3)) 
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
