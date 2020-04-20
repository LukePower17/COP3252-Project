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

	private String moves;

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

		this.moves = "";

		this.clicked = 0;

		this.b = new square[8][8];

		this.board = new Board();


		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(720, 720);


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
		this.board.display();
		System.out.println("In play");
		
		//boolean notDraworLose = !(this.board.checkMate(0) || this.board.checkMate(1) || this.board.staleMate(0) ||this.board.staleMate(1) );

		//System.out.println(notDraworLose);
		System.out.println("count" + this.count);
		// notDraworLose && 
		if(isValid(this.r, this.c) && isValid(this.r_, this.c_))
		{
			System.out.println(this.r +" "+this.c + " "+this.r_ +" "+ this.c_);

			if(this.board.isValidChessMove(this.r, this.c, this.r_, this.c_, this.count%2))
			{
				int result = -3;
				// need to return winner 

				result = this.board.playMove(this.r, this.c, this.r_, this.c_, this.count%2);

				System.out.println("Result "+result);
				if(result == 0 || result == -2)
				{
					String move = ""+ String.valueOf(r) + "," +  String.valueOf(c) + "," +  String.valueOf(r_) + "," + String.valueOf(c_) + "\n";
					this.moves += move;
					this.count += 1;
				}

				if(result == 0)
				{
					setBoard();
					resetColors(this.r, this.c, this.r_, this.c_);

					if(this.board.Check(0))
					{
						Vector<Integer> cor = this.board.M.get("wk");
						b[(int)cor.get(0)][(int)cor.get(1)].dangerMode();
					}
					else if(this.board.Check(1))
					{
						Vector<Integer> cor = this.board.M.get("bk");
						b[(int)cor.get(0)][(int)cor.get(1)].dangerMode();
					}
					return true;
				}

				return false;
			}
			return false;
		}
		else
		{
			if(this.board.checkMate(0)){
				System.out.println("Black has Won !!!!");
			}
			else if(this.board.checkMate(1)){
				System.out.println("White has Won !!!!");
			}

			else
			{	
				if(this.board.staleMate(0))
				{
					System.out.println("Draw!!!");
				}

				else if(this.board.staleMate(1))
				{
					System.out.println("Draw!!!");
				}
			}
			System.out.println("Out of play");
			return false;
		}
		

	}

	public void setBoard()
	{

		System.out.println("In setBoard");
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
		System.out.println("Out of setBoard");
		return;

	}

	public void highlightTrajectory(int x, int y)
	{
		System.out.println("In highlight trajectory");
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
				System.out.println("activated");
				this.b[i][j].activeMode();
			}
			else if(this.board.B[i][j].charAt(0) - p != 0)
			{
				System.out.println("dangerMode");
				this.b[i][j].dangerMode();
			}
		}
		System.out.println("-----------------");
		System.out.println("Out of highlightTrajectory");

	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println("In actionPerformed");
		System.out.println("this.count "+this.count);
		System.out.println("this.clicked "+ this.clicked);
		if(e.getSource() == save)
		{
			saveGame();
			setBoard();
			this.board.display();
			super.setVisible(false);
			super.setVisible(true);
			resetColors();
		}
		else if(e.getSource() == create)
		{
			createGame();
			setBoard();
			resetColors();
			setBoard();
			this.board.display();
			super.setVisible(false);
			super.setVisible(true);
		}
		else if(e.getSource() == load)
		{
			loadGame();
			setBoard();
			super.setVisible(false);
			
			this.board.display();
			resetColors();
			super.setVisible(true);

		}
		else
		{
			for(int i = 0; i < b.length; i++)
			{
				for(int j = 0; j < b[0].length; j++)
				{

					if(e.getSource() == b[i][j])
					{
						if(this.clicked == 0)
						{

							this.r = i;
							this.c = j;

							this.clicked = 1;
						}
						else if(this.clicked == 1)
						{
							this.r_ = i;
							this.c_ = j;
							this.clicked = 2;

						}
					}
				}
			}
		}

		if(this.clicked == 1)
		{
			char p = (this.count%2 == 0)? 'w':'b';

			if(this.board.B[this.r][this.c].equals("") == false && this.board.B[this.r][this.c].charAt(0) - p == 0)
			{
				this.highlightTrajectory(this.r, this.c);
			}
			else
			{
				this.clicked = 0;
			}
		}
		else if(this.clicked == 2)
		{
			if(this.r == this.r_ && this.c == this.c_)
			{
				this.clicked = 0;
				resetColors();
			}
			else{
				resetColors();
				boolean result = this.play();
				this.clicked = 0;
				this.r = -1; this.c = -1; this.r_ = -1; this.c_ = -1;
			}
		}
		System.out.println("Out of actionPerformed");
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

	public void saveGame()
	{
		String saveName = "chessGame_";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM_dd_yyy_HH_mm_ss");
		Date date = new Date();
		saveName += dateFormat.format(date);

		String turn = Integer.toString((count+1)%2);

		try
		{
				FileWriter myWriter = new FileWriter(saveName);
				myWriter.write(String.valueOf((count + 1)%2) + "\n");
				myWriter.write(this.moves);
				myWriter.close();

				System.out.println(this.moves);
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
		
			FileReader fr = new FileReader(file);

			String piece = "";
			int C;
			int row = 0;
			int column = 0;
			int turn;
			this.board = new Board();
			// turn
			C = fr.read();
			turn = Integer.parseInt("" + (char)C);

			this.count = 0;
			int r, c, r_, c_;
			
			r = -1; c = -1; r_= -1; c_ = -1;
			
			this.board = new Board();
			while ((C = fr.read()) != -1){
				System.out.println("-> "+r+" "+c+" "+r_+" " +c_);
				System.out.println((char)C);
				if((char)C - '\n' == 0)
				{
					C = fr.read();
					if(r == -1)
					{
						r = Integer.parseInt("" + (char)C) ;
					}
				}
				else if((char)C - ',' == 0)
				{

					
					if(c == -1)
					{
						C = fr.read();
						c = Integer.parseInt(""+(char)C);
					}
					else if(r_ == -1)
					{
						C = fr.read();
						r_ = Integer.parseInt(""+(char)C);
					}
					else if(c_ == -1)
					{
						C = fr.read();
						c_ = Integer.parseInt(""+(char)C);
					}
				}

				if(!(r == -1 || r_ == -1 || c== -1 || c_ == -1))
				{
		
					this.r = r;
					this.c = c;
					this.r_ = r_;
					this.c_ = c_;
					play();
					r = -1; c = -1; r_ = -1; c_ = -1;
				}
			}		
		}
		catch(Exception ex)
		{
			;
		}

	}


	public void createGame()
	{
		this.board = new Board();
		this.count = 0;
		this.moves = "";
	}

}
