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


public class main
{
	public static void main(String args[])
	{
		chess C = new chess();
		C.display();
	}
}


//import Board;
class chess extends JFrame implements ActionListener
{


	private square[][]b;
	private Icon WPawn;
	private Icon BPawn;
	private Icon WBish;
	private Icon BBish;
	private Icon WRook;
	private Icon BRook;
	private Icon WQueen;
	private Icon BQueen;
	private Icon BKing;
	private Icon WKing;
	private Icon BKnight;
	private Icon WKnight;
	private Board board;
	private Color selected;

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

		
		this.clicked = 0;
		
		this.b = new square[8][8];
		this.board = new Board();
		

		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(720, 720);

		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(8, 8,2,2));


		
	
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{

				
				Color white = new Color(238, 238, 210);
				Color black = new Color(10, 10, 10,150);
				 
				Color green = new Color(0, 200,0,  150);
				Color red = new Color(225, 0, 0, 150);
				
				
				if(j%2 == (i%2)){
					
					b[i][j] = new square(black, green, red, Color.DARK_GRAY);
				}
				else
				{
					b[i][j] = new square(white, green, red, Color.DARK_GRAY);
				}
				b[i][j].addActionListener(this);
				controls.add(b[i][j]);

			}
		}

		super.add(controls);
		
	}

	public boolean play()
	{
		int result = -3;
		if(!(this.board.checkMate(0) || this.board.checkMate(1)))
		{
			 result = this.board.playMove(this.r, this.c, this.r_, this.c_, this.count%2);
			this.count += 1;
		}
		return (result == 0);
	}


	public void display()
	{

		for(int i = 0; i < 8; i++)//i is rows
		{
			for(int j = 0; j < 8; j++)
			{
				if(board.B[i][j].equals(""))	//empty spot
				{
					Color white = new Color(238, 238, 210);
					Color black = new Color(10, 10, 10,150);
					 
					Color green = new Color(0, 200,0,  150);
					Color red = new Color(225, 0, 0, 150);
					
					this.b[i][j].setIcon(null);
					if(i%2 == j%2) {
						this.b[i][j].setBackground(Color.BLACK);
					}
					else
					{
						this.b[i][j].setBackground(white);
					}
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
		return;

	}

	public void highlightTrajectory(int x, int y)
	{
		
		Vector<Vector<Integer>> v = this.board.getTrajectory(this.board.B[x][y]);
		Iterator <Vector<Integer>> itr =  v.iterator();
		
		//b[x][y].activeMode();
		
		while(itr.hasNext())
		{
			Vector<Integer> C = itr.next();
			int i = C.get(0);
			int j = C.get(1);
			
			if(this.board.empty(i, j))
			{
				this.b[i][j].activeMode();
			}
			
			else if(!this.board.filled(this.count, i, j))
			{
				this.b[i][j].dangerMode();
			}
		}

	}
	
	public void actionPerformed(ActionEvent e)
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
		if(clicked == 1) 
		{
			this.highlightTrajectory(this.r, this.c);
		}
		if(clicked == 0)
		{
			boolean result = this.play();
			
			display();
			for(int i = 0; i < b.length; i++)
			{
				for(int j = 0; j < b[0].length; j++)
				{
					b[i][j].normalMode();
					
					if(result  && ((i == this.r && j == this.c) || (i == this.r_ && j == this.c_) ) )
					{
						b[i][j].lastMoveMode();
					}
					
				}
			}
			
			
			
		}
		return;
	}

}




class square extends JButton
{
	private Color normalColor;
	private Color activeColor;
	private Color dangerColor;
	private Color lastMoveColor;
	private boolean selected;
	private boolean hasIcon;
	
	public square(Color normal, Color active, Color danger, Color lastMove)
	{
		super();
		normalColor = normal;
		activeColor = active;
		dangerColor = danger;
		lastMoveColor = lastMove;
		selected = false;
		hasIcon = false;
		super.setBorder(null);
		super.setOpaque(true);
		super.setBackground(normalColor);
		
	}
	
	public boolean Isfilled()
	{
		return hasIcon;
	}
	
	public void addActionListener(chess obj)
	{
		super.addActionListener(obj);
	}
	
	public void normalMode()
	{
		super.setBackground(normalColor);	
	}
	public void activeMode()
	{
		super.setBackground(activeColor);
	}
	public void dangerMode()
	{
		super.setBackground(dangerColor);
	}
	
	public void lastMoveMode()
	{
		super.setBackground(lastMoveColor);
	}
	
	public void setIcon(Icon icon)
	{
		
		super.setIcon(icon);
		if(icon == null)
			hasIcon = false;
		else
			hasIcon = true;
	}
	
	public void setBackground(Color c)
	{
		super.setBackground(c);
	}
}


