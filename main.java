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
		C.setBoard();
	}
}


//import Board;
class chess extends JFrame implements ActionListener
{


	private square[][]b;

	private Icon WPawn, WBish, WRook, WQueen, WKing, WKnight;
	private Icon BPawn, BBish, BRook, BQueen, BKing, BKnight;


	private Board board;


	private JMenuBar menuBar;
	private JMenuItem load, create, save;
	private JMenu menu, theme;
	private JRadioButtonMenuItem dark, light;
	private ButtonGroup Group;
	private File loadedFile;
	private String loadedGame ="";


	private int r,c;
	private int r_, c_;

	private int clicked;

	private int count;

/////////////////////
	public chess()
	{
		super("Chess");
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch(Exception e){
			e.printStackTrace();
		}


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


		this.clicked = 0;

		this.b = new square[8][8];

		this.board = new Board();


		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(720, 720);
//////////////////////////////

		menuBar = new JMenuBar();
		menu = new JMenu("File");
		theme = new JMenu("Theme");
		save = new JMenuItem("Save game");
		save.addActionListener(this);
		create = new JMenuItem("Create new game");
		create.addActionListener(this);
		load = new JMenuItem("Load game");
		load.addActionListener(this);
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
		this.setJMenuBar(menuBar);
		boolean isDarkSelected = dark.isSelected();

/////////////
		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(8, 8,2,2));

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{


				Color white = new Color(238, 238, 210);
				//Color black = new Color(10, 10, 10,150);
				Color black = new Color(118, 115, 110,150);
				Color green = new Color(0, 200,0,  150);
				Color red = new Color(225, 0, 0, 150);


				if(j%2 == (i%2)){

					b[i][j] = new square(black, green, red, Color.WHITE);
				}
				else
				{
					b[i][j] = new square(white, green, red, Color.WHITE);
				}
				b[i][j].addActionListener(this);
				controls.add(b[i][j]);

			}
		}

		super.add(controls);

	}

	public boolean isValid(int r, int c)
	{
		return (r >= 0 && r < 8) && (c >= 0 && c < 8);
	}

	public boolean play()
	{

		if(isValid(this.r, this.c) && isValid(this.r_, this.c_))
		{
			System.out.println(this.r +" "+this.r_ + " "+this.c +" "+ this.c_);

			if(this.board.isValidChessMove(this.r, this.c, this.r_, this.c_, this.count%2))
			{
				int result = -3;

				if(!(this.board.checkMate(0) || this.board.checkMate(1)))
				{
					 result = this.board.playMove(this.r, this.c, this.r_, this.c_, this.count%2);
					 if(result == 0 || result == -2)
					 {
						 this.count += 1;
					 }
				}

				if(result == 0)
				{
					setBoard();
					resetColors(this.r, this.c, this.r_, this.c_);
					return true;
				}

				return false;
			}
			return false;
		}
		return false;
	}

	public void setBoard()
	{

		for(int i = 0; i < 8; i++)//i is rows
		{
			for(int j = 0; j < 8; j++)
			{
				if(board.B[i][j].equals(""))	//empty spot
				{
					b[i][j].setIcon(null);
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
		return;

	}

	public void highlightTrajectory(int x, int y)
	{
		char player = (this.count%2 == 0)?'w':'b';

		if(!(this.board.B[x][y].equals("") == false && this.board.B[x][y].charAt(0) - player == 0))
		{
			return;
		}

		Vector<Vector<Integer>> v = this.board.getTrajectory(this.board.B[x][y]);
		Iterator <Vector<Integer>> itr =  v.iterator();

		System.out.println(this.count);

		System.out.println("Trajectory of " + this.board.B[x][y]);
		System.out.println("-----------------");

		while(itr.hasNext())
		{
			Vector<Integer> C = itr.next();
			int i = C.get(0);
			int j = C.get(1);

			System.out.println(i + " " + j);

			char p = (this.count%2 == 0)?'w': 'b';

			if(this.board.empty(i, j))
			{
				this.b[i][j].activeMode();
			}

			else if(this.board.B[i][j].charAt(0) - p != 0)
			{
				this.b[i][j].dangerMode();
			}
		}
		System.out.println("-----------------");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == save)
		{
			saveGame();
		}
		else if(e.getSource() == create)
		{
			createGame();
		}
		else if(e.getSource() == load)
		{
			loadGame();
		}
		else
		{
			System.out.println("clicked " + this.clicked);
			for(int i = 0; i < b.length; i++)
			{
				for(int j = 0; j < b[0].length; j++)
				{

					if(e.getSource() == b[i][j])
					{
						if(this.clicked == 0 || this.clicked == -1)
						{
							this.clicked = 0;
							this.r = i;
							this.c = j;

							this.r_ = -1;
							this.c_ = -1;

							this.clicked += 1;
						}
						else if(this.clicked == 1)
						{
							this.r_ = i;
							this.c_ = j;
							this.clicked = 0;

						}
					}
				}
			}
		}

		if(clicked == 1)
		{
			char p = (this.count%2 == 0)? 'w':'b';

			if(this.board.B[this.r][this.c].equals("") == false && this.board.B[this.r][this.c].charAt(0) - p == 0)
			{
				this.highlightTrajectory(this.r, this.c);
			}
			else
			{
				this.clicked = -1;
				//setBoard();
			}
		}
		if(clicked == 0)
		{
			resetColors();
			boolean result = this.play();
		}
		return;
	}


	public void resetColors()
	{

		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				this.b[i][j].normalMode();

			}
		}

	}

	public void resetColors(int r, int c, int r_, int c_)
	{

		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if((i == r && j == c)||(i == r_ && j == c_))
				{
					this.b[i][j].lastMoveMode();
				}
				else
				{
					this.b[i][j].normalMode();
				}
			}
		}

	}
////////////////////
	public void saveGame()
	{
		System.out.println("inside saveGame");
		String saveName = "chessGame_";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM_dd_yyy_HH_mm_ss");
		Date date = new Date();
		saveName += dateFormat.format(date);

		String turn = Integer.toString(count%2);

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
				myWriter.write(turn);
				myWriter.close();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

///////////////////

	public void loadGame()
	{
		try
		{

			FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
			dialog.setMode(FileDialog.LOAD);
			dialog.setVisible(true);
			File file = new File( dialog.getFile() );
			//System.out.println(file + " chosen.");
			FileReader fr = new FileReader(file);

			String piece = "";
			int C;
			int row = 0;
			int column = 0;
			int turn;
			this.board = new Board();
			while ((C = fr.read()) != -1)
			{
				if(C-',' == 0)
				{
					// System.out.println(piece);
					System.out.println(row + " " + column);
					this.board.B[row][column] = piece;
					column++;
				//	this.board.display();
					piece = "";
				}
				else
				{
					piece += (char)C;
				}
				if(column == 8)
				{
					row++;
					column = 0;
				}
				if(row == 8)
				{
					C = fr.read();
					piece += (char)C;
					turn = Integer.parseInt(piece);
					this.count = turn;
					System.out.println(turn);
					break;
				}
			}

			setBoard();
			//actionPerformed(null);
			this.board.display();
			resetColors();
		//	System.out.println(loadedGame);
			//then set the board using fuction call:

		}
		catch(Exception ex)
		{

		}

	}

//////////////////
	public void createGame()
	{
		this.board = new Board();
		this.count = 0;
		setBoard();
		resetColors();
	}

/////////////////
	// public void loadBoard(String str)
	// {
	// 	String [][] test;
	// 	String [] token;
	// 	int count = 0;
	// 	for(int i = 0; i < 8; i++)//i is rows
	// 	{
	// 		for(int j = 0; j < 8; j++)
	// 		{
	// 			if(str.charAt(count) == ',')
	// 			{
	//
	// 				continue;
	// 			}
	// 			board.B[i][j] += str.charAt(count);
	// 		}
	// 	}
	//
	// }
	//to get the players move do: this.count%2

/////////////////
}
