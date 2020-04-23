// Name: Luke Power
// Name: Venkata Vadrevu

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
import java.util.concurrent.*;
import java.net.URL;



public class main
{
	public static void main(String args[])
	{
		chess C = new chess();
		C.setBoard();
		
	}
}



class chess extends JFrame implements ActionListener
{


	private square[][]b;

	public Icon WPawn, WBish, WRook, WQueen, WKing, WKnight;
	public Icon BPawn, BBish, BRook, BQueen, BKing, BKnight;


	Color blueModeWhite;
	Color blueModeBlack;

	Color darkModeBlack;
	Color darkModeWhite;

	Color lightModeBlack;
	Color lightModeWhite;

	Color activateColor;
	Color dangerColor;
	Color lastMoveColor;


	private Board board;

	private String moves;

	private JMenuBar menuBar;
	private JMenuItem load, create, save;
	private JMenu menu, theme;
	private JRadioButtonMenuItem dark, light, blue;
	private ButtonGroup Group;
	private File loadedFile;
	private String loadedGame ="";


	public int r,c;
	public int r_, c_;

	private int clicked;

	public int count;
	public String promotedPiece;

	private boolean filled;
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

		this.filled = false;

		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(720, 720);
		super.setResizable(false);


		menuBar = new JMenuBar();
		menu = new JMenu("File");
		theme = new JMenu("Theme");
		save = new JMenuItem("Save game");
		save.addActionListener(this);
		create = new JMenuItem("Create new game");
		create.addActionListener(this);
		load = new JMenuItem("Load game");
		load.addActionListener(this);

		Group = new ButtonGroup();
		light = new JRadioButtonMenuItem("Brown theme",true);
		dark = new JRadioButtonMenuItem("Green theme");
		blue = new JRadioButtonMenuItem("Blue theme");
		theme.add(blue);
		theme.add(light);
		theme.add(dark);
		Group.add(blue);
		Group.add(light);
		Group.add(dark);
		light.addActionListener(this);
		dark.addActionListener(this);
		blue.addActionListener(this);

		menu.add(create);
		menu.add(save);
		menu.add(load);
		menuBar.add(menu);
		menuBar.add(theme);
		this.setJMenuBar(menuBar);
		
		this.promotedPiece = "";


		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(8, 8,2,2));

		blueModeWhite = new Color(228, 232, 236);
		blueModeBlack = new Color(58, 114, 156);

		darkModeBlack = new Color(118, 150, 86);
		darkModeWhite = new Color(250,250,245);

		lightModeBlack = new Color(181, 136, 99);
		lightModeWhite = new Color(240, 217, 181);

		activateColor = new Color(0, 200,0,  150);
		dangerColor = new Color(225, 0, 0, 150);
		lastMoveColor = new Color(252,239,154);

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{


				if(j%2 == (i%2))
				{
					b[i][j] = new square(lightModeBlack, activateColor, dangerColor, lastMoveColor);
				}
				else
				{
					b[i][j] = new square(lightModeWhite, activateColor, dangerColor, lastMoveColor);
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

	public boolean play() throws InterruptedException
	{
		int result = -4;
		if(this.board.checkMate(0))
		{
			JOptionPane.showMessageDialog(null, "Black Wins!\nSelect File -> Create New Game to play again");
		}
		else if(this.board.checkMate(1)){
			JOptionPane.showMessageDialog(null, "White Wins!\nSelect File -> Create New Gameto play again");
		}

		else
		{
			if(this.board.staleMate(0))
			{
				JOptionPane.showMessageDialog(null, "Draw game!\nSelect File -> Create New Game to play again");
			}

			else if(this.board.staleMate(1))
			{
				JOptionPane.showMessageDialog(null, "Draw game!\nSelect File -> Create New Game to play again");
			}

		}
		
		if(isValid(this.r, this.c) && isValid(this.r_, this.c_))
		{

			if(this.board.isValidChessMove(this.r, this.c, this.r_, this.c_, this.count%2))
			{
				result = -3;

				// need to return winner
				result = this.board.playMove(this.r, this.c, this.r_, this.c_, this.count%2);

				String move = "";
				if(result == 0 || result == -2 || result == 1)
				{
					move = ""+ String.valueOf(r) + "," +  String.valueOf(c) + "," +  String.valueOf(r_) + "," + String.valueOf(c_);
					
					
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
					move +=  "\n";
					this.moves += move;
					this.count += 1;

				}
				//PROMOTION
				else if(result == 1)
				{
					
					String p = (this.count%2 == 0)?"w":"b";
					String originalPiece = this.board.B[this.r_][this.c_];
		   			int ind = Integer.parseInt(""+this.board.B[this.r_][this.c_].charAt(2));

		   			
		   			if(promotedPiece.equals(""))
		   			{
			   			promotionWindow pw = new promotionWindow(this);
			   			
			   		}

			   		move = move +","+promotedPiece+ "\n";
			   		this.moves += move;

		   			this.board.M.remove(originalPiece);

		   			if(this.count%2 == 0){this.board.whitePawns[ind] = -2; }
		   			else{this.board.blackPawns[ind] = -2;}

		   			
		   			this.board.B[this.r_][this.c_] = "";
					this.board.B[this.r_][this.c_] = p+this.promotedPiece+String.valueOf(this.count);
					
					Vector<Integer> coordinates = new Vector<Integer>();
					coordinates.add(this.r_); coordinates.add(this.c_);

					this.board.M.put(this.board.B[this.r_][this.c_], coordinates);
					this.board.moved.put(this.board.B[this.r_][this.c_], false);


					this.promotedPiece = "";
					setBoard();
					resetColors(this.r, this.c, this.r_, this.c_);
					
					this.board.M = new HashMap<String, Vector<Integer>>();
					this.board.whiteAlive = new HashSet<String>();
					this.board.blackAlive = new HashSet<String>();
					for(int i = 0; i < this.board.B.length; i++)
					{
						for(int j = 0; j < this.board.B[0].length; j++)
						{
							if(!this.board.B[i][j].equals(""))
							{
								Vector<Integer> v = new Vector<Integer>();
								v.add(i); v.add(j);
								this.board.M.put(this.board.B[i][j], v);
								if(this.board.B[i][j].charAt(0) - 'w' == 0)
								{
									this.board.whiteAlive.add(this.board.B[i][j]);
								}
								else
								{
									this.board.blackAlive.add(this.board.B[i][j]);
								}
							}
						}
					}
					
					setBoard();
					this.count += 1;

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
				}

			}

		}

		

		if(this.board.checkMate(0))
		{
			JOptionPane.showMessageDialog(null, "Black Wins!\nSelect Create New Game to play again");
		}
		else if(this.board.checkMate(1)){
			JOptionPane.showMessageDialog(null, "White Wins!\nSelect Create New Game to play again");
		}
		int temp = this.count;
		
		this.count = (this.count%2 == 0)?this.count:this.count + 1;
		if(this.board.staleMate(0))
		{
			JOptionPane.showMessageDialog(null, "Draw Game\nSelect Create New Game to play again");
		}
		this.count = temp;
		this.count = (this.count%2 == 0)?this.count + 1:this.count;
		if(this.board.staleMate(1))
		{
			JOptionPane.showMessageDialog(null, "Draw Game\nSelect Create New Game to play again");
		}
		
		this.count = temp;
		if(result == 0 || result == -2 || result == 1)
			return true;
		return false;



	}

	public void setBoard()
	{

		//i is rows j is for cols
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				//empty spot
				if(board.B[i][j].equals(""))	
				{
					b[i][j].setIcon(null);
				}
				//white
				else if(board.B[i][j].charAt(0) - 'w' == 0)	
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

		

		while(itr.hasNext())
		{
			Vector<Integer> C = itr.next();
			int i = C.get(0);
			int j = C.get(1);

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
		

	}

	public void changeTheme(int x)
	{

		Color w,b;

		if(x == 0)
		{
				w = lightModeWhite;
				b = lightModeBlack;
		}
		else if(x == 1)
		{
			w = darkModeWhite;
			b = darkModeBlack;
		}
		else
		{
			w = blueModeWhite;
			b = blueModeBlack;
		}

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(j%2 == (i%2))
				{
					this.b[i][j].setNormalColor(b);
				}
				else
				{
					this.b[i][j].setNormalColor(w);
				}
			}
		}
		super.setVisible(false);
		super.setVisible(true);
		resetColors();

	}


	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == light)
		{
			changeTheme(0);
		}
		if(e.getSource() == dark)
		{
			changeTheme(1);
		}
		if(e.getSource() == blue)
		{
			changeTheme(2);
		}

		if(e.getSource() == save)
		{
			saveGame();
			setBoard();
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
			super.setVisible(false);
			super.setVisible(true);
		}
		else if(e.getSource() == load)
		{
			loadGame();
			setBoard();
			super.setVisible(false);
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
				try{
				boolean result = this.play();
				this.clicked = 0;
				this.r = -1; this.c = -1; this.r_ = -1; this.c_ = -1;
				}
				catch(Exception ex)
				{;}
			}
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
			myWriter.write(String.valueOf((this.count + 1)%2) + "\n");
			myWriter.write(this.moves);
			myWriter.close();

			
		}
		catch (IOException e)
		{
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

			Scanner sc = new Scanner(file);
			int turn = 0;
			createGame();
			this.count = 0;
			while (sc.hasNextLine())
			{ 
     	 		String line = sc.nextLine();
     
     	 		if(line.length() == 1)
     	 		{
     	 			turn = Integer.parseInt(""+line.charAt(0));
     	 		}
     	 		if(line.length() == 7)
     	 		{
     	 			this.r = Integer.parseInt(""+line.charAt(0));
     	 			this.c = Integer.parseInt(""+line.charAt(2));
     	 			this.r_ = Integer.parseInt(""+line.charAt(4));
     	 			this.c_ = Integer.parseInt(""+line.charAt(6));

     	 			play();
     	 			
     	 			this.promotedPiece = "";
     	 		}
     	 		else if(line.length() == 9)
     	 		{
     	 			this.r = Integer.parseInt(""+line.charAt(0));
     	 			this.c = Integer.parseInt(""+line.charAt(2));
     	 			this.r_ = Integer.parseInt(""+line.charAt(4));
     	 			this.c_ = Integer.parseInt(""+line.charAt(6));
     	 			this.promotedPiece = "" + line.charAt(8);
     	 			play();
   
     	 			this.promotedPiece = "";
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



	
class promotionWindow extends JDialog implements ActionListener
{
	public String piece;
	public boolean selected;

	public JRadioButton b1, b2, b3, b4;
	public JButton b5;

	public ButtonGroup g;

	private chess C;
	public promotionWindow()
	{
		super();
		JPanel panel = new JPanel();
		this.getContentPane().add(panel);

		piece = "";
		selected = false;
	}

	public promotionWindow(chess c)
	{
		super(c, "Promote Piece", true);

		this.C = c;


		this.setSize(500, 200);
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(5, 1, 2, 2));


		piece = "";
		selected = false;

		g = new ButtonGroup();
// getClass().getResourceAsStream("file.txt")
		URL URL1 = getClass().getClassLoader().getResource("./Imgs/white_queen.png");
		URL URL2 = getClass().getClassLoader().getResource("./Imgs/white_rook.png");
		URL URL3 = getClass().getClassLoader().getResource("./Imgs/white_bishop.png");
		URL URL4 = getClass().getClassLoader().getResource("./Imgs/white_knight.png");
	
		if(this.C.count%2 == 1)
		{
			URL1 = getClass().getClassLoader().getResource("./Imgs/black_queen.png");
			URL2 = getClass().getClassLoader().getResource("./Imgs/black_rook.png");
			URL3 = getClass().getClassLoader().getResource("./Imgs/black_bishop.png");
			URL4 = getClass().getClassLoader().getResource("./Imgs/black_knight.png");
		}

		if(URL1 == null || URL2 == null || URL3 == null || URL4 == null)
		{
			  b1 = new JRadioButton("Queen");
			  b2 = new JRadioButton("Rook");
			  b3 = new JRadioButton("Bishop");
			  b4 = new JRadioButton("Knight");
		}
		else
		{
			b1 = new JRadioButton("<html><body><img src='" + URL1.toString() +"'width=\"25\" height=\"25\">");
			b2 = new JRadioButton("<html><body><img src='" + URL2.toString() +"'width=\"25\" height=\"25\">");
			b3 = new JRadioButton("<html><body><img src='" + URL3.toString() +"'width=\"25\" height=\"25\">");
			b4 = new JRadioButton("<html><body><img src='" + URL4.toString() +"'width=\"25\" height=\"25\">");
		}

		b5 = new JButton("Click me to Promote");

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);

		b5.addActionListener(this);
		b5.setPreferredSize(new Dimension(40, 40));
		b5.setBackground(new Color(0,255,0, 120));
		

		g.add(b1); g.add(b2); g.add(b3); g.add(b4);g.add(b5);


		panel.add(b1);
		panel.add(b2);
		panel.add(b3);
		panel.add(b4);
		panel.add(b5);

		this.getContentPane().add(panel);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
	
		if(b1.isSelected())
		{
			this.piece = "q";
			this.C.promotedPiece = this.piece;
		}
		else if(b2.isSelected())
		{
			this.piece = "r";
			this.C.promotedPiece = this.piece;
		}
		else if(b3.isSelected())
		{
			this.piece = "b";
			this.C.promotedPiece = this.piece;
		}
		else if(b4.isSelected())
		{
			this.piece = "n";
			this.C.promotedPiece = this.piece;
		}
		if(e.getSource() == b5)
		{
			b5.setBackground(new Color(0,255,0, 250));
			
			this.selected = true;
			b5.setBackground(new Color(0,255,0, 120));
			this.C.promotedPiece = this.piece;
			dispose();
		}
	}
}

