import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class main
{
	public static void main(String args[])
	{
		chess C = new chess();
		//C.show();
		C.display();
		C.colors();
	}
}


//import Board;
class chess extends JFrame
//extends JPanel implements ActionListner
{

	private	JButton[][] b;
	private JMenuBar menuBar;
	private JMenuItem load, create, save;
	private JMenu menu, theme;
	private JRadioButtonMenuItem dark, light;
	private Icon WPawn, WBish, WRook, WQueen, WKing, WKnight;
	private Icon BPawn, BBish, BRook, BQueen, BKing, BKnight;
	private Board board;
	private Color selected;
	private ButtonGroup Group;
	private File loadedFile;
	private String loadedGame ="";

	class MenuActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			loadGame();
		}
	}

	class MenuActionListenerSave implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			saveGame();
		}
	}
	class MenuActionListenerCreate implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			createGame();
		}
	}
	public chess()
	{
		//setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("./Imgs/white_pawn.png")));
		WPawn = new ImageIcon("./Imgs/white_pawn.png");
		BPawn = new ImageIcon("./Imgs/black_pawn.png");
		WBish = new ImageIcon("./Imgs/white_bishop.png");
		BBish = new ImageIcon("./Imgs/black_bishop.png");
		WRook = new ImageIcon("./Imgs/white_rook.png");
		BRook = new ImageIcon("./Imgs/black_rook.png");
		WQueen = new ImageIcon("./Imgs/white_queen.png");
		BQueen = new ImageIcon("./Imgs/black_queen.png");
		BKing = new ImageIcon("./Imgs/black_king.png");
		WKing = new ImageIcon("./Imgs/white_king.png");
		BKnight = new ImageIcon("./Imgs/black_knight.png");
		WKnight = new ImageIcon("./Imgs/white_knight.png");
		selected = new Color(0, 255, 0,100);
		b = new JButton[8][8];
		board = new Board();
		JFrame frame = new JFrame("Chess");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(720, 720);
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		theme = new JMenu("Theme");
		save = new JMenuItem("Save game");
		save.addActionListener(new MenuActionListenerSave());
		create = new JMenuItem("Create new game");
		create.addActionListener(new MenuActionListenerCreate());
		load = new JMenuItem("Load game");
		load.addActionListener(new MenuActionListener());
		light = new JRadioButtonMenuItem("Light theme");
		Group = new ButtonGroup();
		theme.add(light);
		dark = new JRadioButtonMenuItem("Dark theme", true);
		theme.add(dark);
		Group.add(light);
		Group.add(dark);
		menu.add(create);
		menu.add(save);
		menu.add(load);
		menuBar.add(menu);
		menuBar.add(theme);
		frame.setJMenuBar(menuBar);
		boolean isDarkSelected = dark.isSelected();

		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(8, 8,0,0));

		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch(Exception e){
			e.printStackTrace();
		}

		for(int i = 0; i < 8; i++)//i is rows
		{
			for(int j = 0; j < 8; j++)
			{
				//Rectangle sq = new Rectangle(x, y, 100, 100);

				 b[i][j] = new JButton();

				if(j%2 == (i%2)){

					if(isDarkSelected)
					{
						b[i][j].setBackground(new Color(10, 10, 10,150));
					}
					else
					{
						b[i][j].setBackground(new Color(118, 150, 86));
					}

				}
				else
				{
					b[i][j].setBackground(new Color(238, 238, 210));

				}
				b[i][j].setBorder(null);
				b[i][j].setOpaque(true);

				controls.add(b[i][j]);
			}

		}


		frame.add(controls);

	}


	public void display()
	{

		for(int i = 0; i < 8; i++)//i is rows
		{
			for(int j = 0; j < 8; j++)
			{
				if(board.B[i][j].equals(""))	//empty spot
				{
					continue;
				}

				else if(board.B[i][j].charAt(0) - 'w' == 0)	//white
				{
						if(board.B[i][j].charAt(1) - 'p' == 0)
							b[i][j].setIcon(WPawn);
						else if((board.B[i][j].charAt(1) - 'k' == 0))
							b[i][j].setIcon(WKing);
						else if((board.B[i][j].charAt(1) - 'q' == 0))
							b[i][j].setIcon(WQueen);
						else if((board.B[i][j].charAt(1) - 'r' == 0))
							b[i][j].setIcon(WRook);
						else if((board.B[i][j].charAt(1) - 'n' == 0))
							b[i][j].setIcon(WKnight);
						else if((board.B[i][j].charAt(1) - 'b' == 0))
							b[i][j].setIcon(WBish);
				}
				else		//black
				{
						if(board.B[i][j].charAt(1) - 'p' == 0)
							b[i][j].setIcon(BPawn);
						else if((board.B[i][j].charAt(1) - 'k' == 0))
							b[i][j].setIcon(BKing);
						else if((board.B[i][j].charAt(1) - 'q' == 0))
							b[i][j].setIcon(BQueen);
						else if((board.B[i][j].charAt(1) - 'r' == 0))
							b[i][j].setIcon(BRook);
						else if((board.B[i][j].charAt(1) - 'n' == 0))
							b[i][j].setIcon(BKnight);
						else if((board.B[i][j].charAt(1) - 'b' == 0))
							b[i][j].setIcon(BBish);
				}
			}
		}

	}

	public void colors()
	{

		int x;
		int y;
		Vector<Vector<Integer>> v = board.getTrajectory(board.B[7][6]);
		Iterator <Vector<Integer>> itr =  v.iterator();
		b[7][6].setBackground(selected);
		while(itr.hasNext())
		{
			Vector<Integer> C = itr.next();
			x = C.get(0);
			y = C.get(1);
			b[x][y].setBackground(selected);

		}
	}
		public void saveGame()
		{
			System.out.println("inside saveGame");
			String saveName = "chessGame_";
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM_dd_yyy_HH_mm_ss");
			Date date = new Date();
			saveName += dateFormat.format(date);

			try
			{
					FileWriter myWriter = new FileWriter(saveName);
					for(int i = 0; i < 8; i++)//i is rows
					{
						for(int j = 0; j < 8; j++)
						{
							myWriter.write(board.B[i][j] + ",");
						}
					}
					myWriter.close();
			}
			catch (IOException e)
			{
		 		System.out.println("An error occurred.");
		 		e.printStackTrace();
	 		}
		}
		public void loadGame()
		{
			try
			{

				FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
			  dialog.setMode(FileDialog.LOAD);
			  dialog.setVisible(true);
			  File file = new File( dialog.getFile() );
			  //System.out.println(file + " chosen.");
				BufferedReader br = new BufferedReader(new FileReader(file));

				String st;
 				while ((st = br.readLine()) != null)
    		{
					loadedGame += st;
				}
				System.out.println(loadedGame);
				//then set the board using fuction call:
				setBoard(loadedGame);
			}
			catch(Exception ex)
			{

			}

		}
		public void createGame()
		{
			board = new Board();
		}
		public void setBoard(String str)
		{
			String [][] test;
			String [] token;
			// for(int i = 0; i < 8; i++)//i is rows
			// {
			// 	for(int j = 0; j < 8; j++)
			// 	{
			// 		token[k] = str.split(",");
			// 	}
			// }

		}
		//to get the players move do: this.count%2

	}
