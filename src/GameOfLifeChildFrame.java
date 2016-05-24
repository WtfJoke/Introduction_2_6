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
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLifeChildFrame extends JInternalFrame implements MouseListener, MouseMotionListener, Observer, Runnable
{
	// Private and public members
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

	/**
	 * Constructor
	 * @param mvcGO MVCGameOfLife reference
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
		if (GameOfLifeMenu.isNewGame)
		{
			this.golView.setBlockSize(Integer.parseInt(GameOfLifeMenu.blockSize.getText()));
			this.setSize(Integer.parseInt(GameOfLifeMenu.rowNumber.getText()) * Integer.parseInt(GameOfLifeMenu.blockSize.getText()) + 50,
					Integer.parseInt(GameOfLifeMenu.columnNumber.getText()) * Integer.parseInt(GameOfLifeMenu.blockSize.getText()) + 100);
			GameOfLifeMenu.isNewGame = false;
		} else
		{
			this.golView.setBlockSize(Integer.parseInt(GameOfLifeNewViewOptionMenu.blockSize.getText()));
			this.setSize(Integer.parseInt(GameOfLifeNewViewOptionMenu.rowNumber.getText()) * Integer.parseInt(GameOfLifeNewViewOptionMenu.blockSize.getText()) + 50,
					Integer.parseInt(GameOfLifeNewViewOptionMenu.columnNumber.getText()) * Integer.parseInt(GameOfLifeNewViewOptionMenu.blockSize.getText()) + 100);
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
			 * Method which enables the Set-Mode and disables the Paint-Mode if
			 * clicking on it
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
			 * Method which disables the Set-Mode and enables the Paint-Mode if
			 * clicking on it
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
			 * Method which creates a new view of the model by creating a new
			 * child frame
			 * @param e Action event to be triggered (ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				try
				{	
					if(Integer.parseInt(GameOfLifeNewViewOptionMenu.rowNumber.getText()) < 10 || Integer.parseInt(GameOfLifeNewViewOptionMenu.rowNumber.getText()) > 60)
					{
						JOptionPane.showMessageDialog(mvcGOL, "Row number must be between 10 and 60, Column number between 10 and 30 and block size number between 20 and 30", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(Integer.parseInt(GameOfLifeNewViewOptionMenu.columnNumber.getText()) < 10 || Integer.parseInt(GameOfLifeNewViewOptionMenu.columnNumber.getText()) > 30)
					{
						JOptionPane.showMessageDialog(mvcGOL, "Row number must be between 10 and 60, Column number between 10 and 30 and block size number between 20 and 30", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(Integer.parseInt(GameOfLifeNewViewOptionMenu.blockSize.getText()) < 20 || Integer.parseInt(GameOfLifeNewViewOptionMenu.blockSize.getText()) > 30)
					{
						JOptionPane.showMessageDialog(mvcGOL, "Row number must be between 10 and 60, Column number between 10 and 30 and block size number between 20 and 30", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					golBoard.setGameBoardSize(new Dimension(Integer.parseInt(GameOfLifeNewViewOptionMenu.rowNumber.getText()), Integer.parseInt(GameOfLifeNewViewOptionMenu.columnNumber.getText())));		
				} 
				catch (NumberFormatException n)
				{
					return;
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
				for (int x = 0; x < golBoard.getGameBoardSize().width; x++)
				{
					for (int y = 0; y < golBoard.getGameBoardSize().height; y++)
					{
						if (x == 1 && y == 0)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 2 && y == 1)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 0 && y == 2)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 1 && y == 2)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 2 && y == 2)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
					}
				}
				golBoard.boardChanged();
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
				for (int x = 0; x < golBoard.getGameBoardSize().width; x++)
				{
					for (int y = 0; y < golBoard.getGameBoardSize().height; y++)
					{
						if (x == 0 && y == 0)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 3 && y == 0)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 4 && y == 1)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 0 && y == 2)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 4 && y == 2)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 1 && y == 3)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 2 && y == 3)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 3 && y == 3)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
						if (x == 4 && y == 3)
						{
							golBoard.removeDeadCell(x, y);
							golBoard.addLivingCell(x, y);
						}
					}
				}
				golBoard.boardChanged();
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

		createColors(new GameOfLifeColor("Black", Color.BLACK), new GameOfLifeColor("Blue", Color.BLUE), new GameOfLifeColor("Cyan", Color.CYAN), new GameOfLifeColor("Darkgray", Color.DARK_GRAY),
				new GameOfLifeColor("Gray", Color.GRAY), new GameOfLifeColor("Green", Color.GREEN), new GameOfLifeColor("Lightgray", Color.LIGHT_GRAY), new GameOfLifeColor("Magenta", Color.MAGENTA),
				new GameOfLifeColor("Orange", Color.ORANGE), new GameOfLifeColor("Pink", Color.PINK), new GameOfLifeColor("Red", Color.RED), new GameOfLifeColor("White", Color.WHITE),
				new GameOfLifeColor("Yellow", Color.YELLOW));
	}

	/**
	 * Method which creates colors for color selection menu
	 * @param availableColors List of all colors
	 */
	private void createColors(GameOfLifeColor... availableColors)
	{
		for (GameOfLifeColor golColor : availableColors)
		{
			JMenuItem item = new JMenuItem(golColor.getColorString());
			popup.add(item);
			item.addActionListener(new ActionListener()
			{
				/**
				 * Method which sets the color of the cells
				 * @param e Action event to be triggered (ActionEvent)
				 */
				public void actionPerformed(ActionEvent e)
				{
					if (livingCells.isSelected())
					{
						golView.setLivingCellColor(golColor.getColor());
						repaint();
					} 
					else if (deadCells.isSelected())
					{
						golView.setDeadCellColor(golColor.getColor());
						repaint();
					}
				}
			});
		}
	}

	/**
	 * Method which checks whether the left mouse button is released or not
	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mouseReleased(MouseEvent e)
	{
		mouseIsDragging = false;
		if (e.getButton() == MouseEvent.BUTTON3 && e.isPopupTrigger())
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
		if (menuModeSet.isSelected() && e.getButton() == MouseEvent.BUTTON1)
		{
			int x = e.getPoint().x / golView.getBlockSize() - 1;
			int y = e.getPoint().y / golView.getBlockSize() - 4;
			if ((x >= 0) && (x < golBoard.getGameBoardSize().getWidth()) && (y >= 0) && (y < golBoard.getGameBoardSize().getHeight()))
			{
				if (golBoard.getDeadCellList().contains(new Point(x, y)))
				{
					golBoard.removeDeadCell(x, y);
					golBoard.addLivingCell(x, y);
				} else if (golBoard.getLivingCellList().contains(new Point(x, y)))
				{
					golBoard.removeLivingCell(x, y);
					golBoard.addDeadCell(x, y);
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
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			mouseIsDragging = true;
		}
	}

	/**
	 * Method which revives several cells if while dragging with the left mouse
	 * button
 	 * @param e Mouse event to be triggered (MouseEvent)
	 */
	public void mouseDragged(MouseEvent e)
	{
		if (mouseIsDragging && menuModePaint.isSelected())
		{
			int x = e.getPoint().x / golView.getBlockSize() - 1;
			int y = e.getPoint().y / golView.getBlockSize() - 4;
			if ((x >= 0) && (x < golBoard.getGameBoardSize().getWidth()) && (y >= 0) && (y < golBoard.getGameBoardSize().getHeight()))
			{
				if (golBoard.getDeadCellList().contains(new Point(x, y)))
				{
					golBoard.removeDeadCell(x, y);
					golBoard.addLivingCell(x, y);
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
		while (true)
		{
			if (startGame)
			{
				ArrayList<Point> survivingCells = new ArrayList<Point>(0);
				// Iterate through the array, follow game of life rules
				int boardWidth = golBoard.getGameBoardSize().getSize().width;
				int boardHeight = golBoard.getGameBoardSize().getSize().height;
				boolean[][] gameBoard = new boolean[boardWidth][boardHeight];

				for (Point livingPoint : golBoard.getLivingCellList())
				{
					gameBoard[livingPoint.x][livingPoint.y] = true; //
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
								survivingCells.add(new Point(i, j));
							}
						} 
						else
						{
							// Cell is dead, will the cell be reborn? (3)
							if (surrounding == 3)
							{
								survivingCells.add(new Point(i, j));
							}
						}
					}
				}
				golBoard.resetGameBoard();
				for (int x = 0; x < boardWidth; x++)
				{
					for (int y = 0; y < boardHeight; y++)
					{
						if (survivingCells.contains(new Point(x, y)))
						{
							golBoard.addLivingCell(x, y);
						} 
						else
						{
							golBoard.addDeadCell(x, y);
						}
					}
				}
				golBoard.boardChanged();
				golBoard.notifyObservers();
				if (golBoard.getLivingCellList().isEmpty())
				{
					startGame = false;
				}
				try
				{
					Thread.sleep(sleepTime);
					run();
				} catch (InterruptedException ex)
				{
				}
			} else
			{
				try
				{
					Thread.sleep(1000);
					run();
				} catch (InterruptedException ex)
				{
				}
			}
		}
	}

	// Unused events
	@Override
	public void mouseMoved(MouseEvent e){}
	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}
}
