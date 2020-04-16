import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.*;

//import Board;
public class main extends JFrame
//extends JPanel implements ActionListner
{

	public static void main(String args[])
	{
		JFrame frame = new JFrame("Chess");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		NewBoard Board = new NewBoard();
		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(8, 8,0,0));
		JButton[][] b = new JButton[8][8];
		Icon WPawn = new ImageIcon("white_pawn.png");
		Icon BPawn = new ImageIcon("black_pawn.png");
		Icon WBish = new ImageIcon("white_bishop.png");
		Icon BBish = new ImageIcon("black_bishop.png");
		Icon WRook = new ImageIcon("white_rook.png");
		Icon BRook = new ImageIcon("black_rook.png");
		Icon WQueen = new ImageIcon("white_queen.png");
		Icon BQueen = new ImageIcon("black_queen.png");
		Icon BKing = new ImageIcon("black_king.png");
		Icon WKing = new ImageIcon("white_king.png");
		Icon BKnight = new ImageIcon("black_knight.png");
		Icon WKnight = new ImageIcon("white_knight.png");

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
		return;
	}
	//test
	// public void paintComponent(Graphics g) {
	//    g.setColor(Color.GREEN);
	//    g.fillRect(0, 0, getSize().width, getSize().height);
	// }
	// public static String display(JButton[][] button,)
	// {
	//
	// }
}



// public class Board extends JComponent
// {
// 	public void paintComponent(Graphics g)
// 	{
// 		super.paintComponent(g);
// 		Graphics2D g2d = (Graphics2D)g;
//
// 		int startX = 50;
// 		int startY = 0;
// 		Rectangle board = new Rectangle(startX,startY, 800,800);
// 		g2d.draw(board);
//
// 		int x = startX;
// 		int y = startY;
//
// 		int sideLen = 80;
//
//
// 	}
// }
