import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JInternalFrame;

/** 
 * Class for Conways "Game of Life" children
 * 
 * @author Philipp Backes, 191710
 * @author Viet Cuong Nguyen, 191515
 */
public class GameOfLifeChild extends JInternalFrame 
{
	private static final long serialVersionUID = 1L;
	static int nr = - 1, xpos = 30, ypos = 30;
	static final Color[] col = {Color.red, Color.green};
	GameOfLife golRef = new GameOfLife();
	
	public GameOfLifeChild (GameOfLife gol) 
	{
		super("Kind " + (++nr), true, true);
		setBackground(col[nr%col.length]);
		golRef = gol;
		Container cp = getContentPane();
		cp.setLayout(new FlowLayout());
		JButton jb = new JButton ("Neue Farbe");
		cp.add(jb);
		jb.addActionListener(new ActionListener() 
		{
			int i = nr%col.length;
			public void actionPerformed(ActionEvent e) 
			{
				i = (i+1)%col.length;
				setBackground(col[i]);
			}
		});
		jb = new JButton("Neues Fenster");
		cp.add (jb);
		jb.addActionListener (new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				golRef.addChild(new GameOfLifeChild(golRef), xpos, ypos);
				xpos += 20; 
				ypos += 20;
			}
		});
		setIconifiable(true); 
		setMaximizable(true);
	}

}
