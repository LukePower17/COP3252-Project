import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.*;


public class main
{
	public static void main(String args[])
	{
		 chess C = new chess();
		 //C.play();
		 C.display();

	}
}


//import Board;
class chess extends JFrame
//extends JPanel implements ActionListner
{

	private	JButton[][] b;
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

	public chess()
	{
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

		b = new JButton[8][8];
		board = new Board();

		JFrame frame = new JFrame("Chess");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(720, 720);

		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(8, 8,0,0));



		for(int i = 0; i < 8; i++)//i is rows
		{
			for(int j = 0; j < 8; j++)
			{
				//Rectangle sq = new Rectangle(x, y, 100, 100);

				 b[i][j] = new JButton();
				if(i == 1)
				{

					b[i][j].setIcon(WPawn);
				}

				if(j%2 == (i%2)){
					b[i][j].setBackground(new Color(118, 150, 86));

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

	}


}
