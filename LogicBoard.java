
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class LogicBoard
{
	public static void main(String args[])
	{
		LBoard b = new LBoard();
		b.Display();
		b.Play();
	}

}

class LBoard
{


	private String[][]Board;

	private HashMap<String, Vector<Integer>> pieceCor;

	private Set<String> whiteAlive;
	private Set<String> blackAlive;


	private boolean br0; // to see if it has been  moved
	private boolean br1; // to see if it has been  moved

	private boolean wr0; // to see if it has been  moved
	private boolean wr1; // to see if it has been  moved

	private boolean bk0; // to see if it has been  moved
	private boolean wk0; // to see if it has been  moved

	private boolean whitePawns[];
	private boolean blackPawns[];

	public LBoard()
	{

		whitePawns = new boolean[8];
		for(int i = 0;i < whitePawns.length; i++)
		{
			whitePawns[i] = false;
		}

		blackPawns = new boolean[8];
		for(int i = 0;i < blackPawns.length; i++)
		{
			blackPawns[i] = false;
		}

		pieceCor = new HashMap<String, Vector<Integer>>();

		whiteAlive = new HashSet<String>();
		blackAlive = new HashSet<String>();

		br0 = false; // black rook 0 is not moved
		br1 = false; // black rook 1 is not moved

		wr0 = false; // white rook 0 is not moved
		wr1 = false; // white rook 0 is not moved

		bk0 = false; // black king 0 is not moved

		wk0 = false; // white king 0 is not moved


		Board = new String[8][8];

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				Board[i][j] = "";
			}
		}

		String white = "w";
		String black = "b";

		String pawn = "p";
		String knight = "n";
		String queen = "q";
		String bishop = "b";
		String king = "k";
		String rook = "r";

		int row = 0;
		int col = 0;

		Board[row][0] = white + rook + Integer.toString(0);
		Board[row][1] = white + knight + Integer.toString(0);
		Board[row][2] = white + bishop + Integer.toString(0);
		Board[row][3] = white + queen + Integer.toString(0);
		Board[row][4] = white + king + Integer.toString(0);
		Board[row][5] = white + bishop + Integer.toString(1);
		Board[row][6] = white + knight + Integer.toString(1);
		Board[row][7] = white + rook + Integer.toString(1);

		row++;
		for(; col < 8; col++)
		{
			Board[row][col] = white + pawn + Integer.toString(col);
		}



		row = 7;

		Board[row][0] = black + rook + Integer.toString(0);
		Board[row][1] = black + knight + Integer.toString(0);
		Board[row][2] = black + bishop + Integer.toString(0);
		Board[row][3] = black + queen + Integer.toString(0);
		Board[row][4] = black + king + Integer.toString(0);
		Board[row][5] = black + bishop + Integer.toString(1);
		Board[row][6] = black + knight + Integer.toString(1);
		Board[row][7] = black + rook + Integer.toString(1);

		row--;
		col = 0;
		for(; col < 8; col++)
		{
			Board[row][col] = black + pawn + Integer.toString(col);
		}


		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(Board[i][j].equals("") == false)
				{
					Vector<Integer> v = new Vector<Integer>();
					v.add(i);
					v.add(j);
					pieceCor.put(Board[i][j], v);
					if(Board[i][j].charAt(0) - 'w' == 0)
					{
						whiteAlive.add(Board[i][j]);
					}
					else
					{
						blackAlive.add(Board[i][j]);
					}
				}
			}
		}


	}


	public void Display(){

		String[] arr = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};

		System.out.println("  a  b  c  d  e  f  g  h");

		for(int row = 0; row < 8; row++)
		{
			System.out.println(" -------------------------------");
			System.out.print(arr[row] + " ");
			for(int col = 0; col < 8; col++)
			{

				if(Board[row][col].equals("")){
					System.out.print("|   ");
				}
				else{
					System.out.print("|" + Board[row][col]);
				}
			}

			System.out.println("");
		}

		System.out.println("  -------------------------------");

	}

	public boolean CheckMate(int Player)
	{
	int x = 0;
	int y = 0;
	Vector<Integer> Pos = pieceCor.get("wk0");
	String king = "wk0";
	if(Player == 0) //player is white
      	{
        	Pos = pieceCor.get("wk0");
				x = Pos.get(0);
				y = Pos.get(1);

	}
		else if(Player == 1)
		{
			  Pos = pieceCor.get("bk0");
				x = Pos.get(0);
				y = Pos.get(1);
				king = "bk0";
		}
		Vector<Vector<Integer>> v = getKingTrajectory(king);
		Iterator <Vector<Integer>> itr =  v.iterator();

		while(itr.hasNext())
		{
			Vector<Integer> C = itr.next();
			String capturedPiece = Board[(int)C.get(0)][(int)C.get(1)];
			Board[(int)C.get(0)][(int)C.get(1)] = king;
			Board[x][y] = "";
			if(!check(Player))	//player is not in check
			{
				Board[x][y] = king;
				Board[(int)C.get(0)][(int)C.get(1)] = capturedPiece;
				return false;
			}
			Board[x][y] = king;
			Board[(int)C.get(0)][(int)C.get(1)] = capturedPiece;
		}
		return true;
	}




	public boolean check(int player)
	{
	  //
	  //0 if White
	  //1 if black
			Iterator<String> it = blackAlive.iterator();
			int i = 0;
			int j = 0;
			if(player == 0)
			{
	      //check to see which black pieces are alive and the squares that they cover
	      it = blackAlive.iterator();
				Vector<Integer> kingCord = pieceCor.get("wk0");
				i = (int)kingCord.get(0);
				j = (int)kingCord.get(1);
			}
			else if(player == 1)
			{
				it = whiteAlive.iterator();
				Vector<Integer> kingCord = pieceCor.get("bk0");
				i = (int)kingCord.get(0);
				j = (int)kingCord.get(1);
			}

	      while(it.hasNext())
	      {
	      	//Vector<Vector<int>> v = pieceCor.getTrajectory(it.next());  //add iterator for vector
				 	Vector<Vector<Integer>> v = getTrajectory(it.next());
					Iterator <Vector<Integer>> itr =  v.iterator();
					// v --> [[x,y],[x,y],[x,y],[x,y],[x,y]]
					// v.get(0)
					// how to get an itme from iterator
					while(itr.hasNext())
					{
						Vector<Integer> C = itr.next();
						if( C.get(0) == i && C.get(1)== j )	//if the
							return true;

					}
	      }

	    return false;

	}



	public void Play()
	{
		Scanner input = new Scanner(System.in);

		int count = 0;

		while(!(CheckMate(0)||CheckMate(1)))
		{


			if(count%2 == 0)
				System.out.println("White to play");
			else
				System.out.println("Black to play");

			char player = 'b';
			if(count%2 == 0)
				player = 'w';


			int p = count%2;
			boolean moved = false;

			while(!moved)
			{

				int C = 0;
				int r;
				int c;
				do
				{

						if(C == 0){
							System.out.print("row [1-8] ");
							r = input.nextInt();

							System.out.print("col [1-8] ");
							c = input.nextInt();


							r -= 1;
							c -= 1;

					}
					else
					{
						System.out.println("Invalid move !!!");
						System.out.print("row ");
						r = input.nextInt();
						r -= 1;
						System.out.print("col ");
						c = input.nextInt();
						c -= 1;
					}
					C += 1;
				}while(!isValid(r, c)||Board[r][c].equals("") || (Board[r][c].charAt(0) - player) != 0 );
				char piece = Board[r][c].charAt(1);

				C = 0;
				int r_, c_;
				do
				{

					if(C == 0)
					{
						System.out.println("Move to ?");

						System.out.print("row ");
						r_ = input.nextInt();
						r_ -= 1;
						System.out.print("col ");
						c_ = input.nextInt();
						c_ -= 1;
					}
					else
					{
						System.out.println("Invalid move !!!");
						System.out.print("row ");
						r_ = input.nextInt();
						r_ -= 1;
						System.out.print("col ");
						c_ = input.nextInt();
						c_ -= 1;
					}
					C += 1;
				}while(!isValid(r_, c_) ||!(Board[r_][c_].equals("")|| (Board[r_][c_].charAt(0) - player) != 0));


				switch(piece)
				{
					case 'p': moved = movePawn(p, r, c, r_, c_);
						break;
					case 'k':moved = moveKing(p, r, c, r_, c_);
						break;
					case 'q':moved = moveQueen(p, r, c, r_, c_);
						break;
					case 'n':moved = moveKnight(p, r, c, r_, c_);
						break;
					case 'b':moved = moveBishop(p, r, c, r_, c_);
						break;
					case 'r':moved = moveRook(p, r, c, r_, c_);
						break;
				}
			}

			whiteAlive = new HashSet<String>();
			blackAlive = new HashSet<String>();
			for(int i = 0; i < 8; i++)
			{
				for(int j = 0; j < 8; j++)
				{
					if(Board[i][j].equals("") == false)
					{
						Vector<Integer> v = new Vector<Integer>();
						v.add(i);
						v.add(j);
						pieceCor.replace(Board[i][j], v);
						if(Board[i][j].charAt(0) - 'w' == 0)
						{
							whiteAlive.add(Board[i][j]);
						}
						else
						{
							blackAlive.add(Board[i][j]);
						}
					}
				}
			}
			// String piece = Board[r][c];
			// Board[r_][c_] = piece;
			// Board[r][c] = "";

			count ++;
			Display();
		}

	}




	public boolean movePiece(int player, int r, int c, int r_, int c_)
	{

		// First assume that we perform the move
		String piece1 = Board[r][c];
		String piece2 = Board[r_][c_];
		Board[r_][c_] = piece1;
		Board[r][c] = "";

		// If after the move is performed we still face check we simply revert back the move
		// and return false

		if(check(player))
		{
			Board[r][c] = piece1;
			Board[r_][c_] = piece2;
			return false;
		}

		return true;
	}


	public boolean movePawn(int player, int cur_r, int cur_c, int to_r, int to_c)
	{
		// player --> 0 White
		// player --> 1 Black


		// Need to implement Enpassent
		if(player == 0)
		{
			if(cur_r == 1)
			{
				if(to_r - cur_r == 1 || to_r - cur_r == 2)
				{
					return movePiece(player, cur_r, cur_c, to_r, to_c);
				}
				return false;

			}
			else
			{
				if(to_r - cur_r == 1)
				{
					return movePiece(player, cur_r, cur_c, to_r, to_c);
				}
				return false;
			}

		}

		if(cur_r == 6)
		{
			if(to_r - cur_r == -1 || to_r - cur_r == -2)
			{
				return movePiece(player, cur_r, cur_c, to_r, to_c);
			}
			return false;
		}
		else
		{
			if(to_r - cur_r == -1)
			{
				return movePiece(player, cur_r, cur_c, to_r, to_c);
			}

		}
		return false;
	}


	public boolean moveBishop(int player, int cur_r, int cur_c, int to_r, int to_c)
	{

		int den = Math.abs(to_r - cur_r);
		int num = Math.abs(to_c - cur_c);

		if(den == 0)
			return false;

			int slope = num/den;

			if(slope == 1 || slope == -1)
			{

				char p  = 'b';
				if(player == 0)
					p = 'w';

				// if the place that we move to has the piece of same color
				// we don't move it
				if((Board[to_r][to_c].charAt(0) - p) == 0)
					return false;

				return movePiece(player, cur_r, cur_c, to_r, to_c);

			}
			return false;
	}

	public boolean moveRook(int player, int cur_r, int cur_c, int to_r, int to_c)
	{

		char p = 'w';
		if(player == 1)
			p = 'o';
		if( (cur_r - to_r == 0 && to_c - cur_c != 0)||(cur_c - to_c == 0 && to_r - cur_r != 0))
		{
			if((Board[to_r][to_c].charAt(0) - p) == 0)
					return false;
			boolean result =  movePiece(player, cur_r, cur_c, to_r, to_c);

			if(result)
			{
				if(Board[to_r][to_c].charAt(Board[to_r][to_c].length() - 1) - '0' == 0)
				{
					if(player == 0)
					{
						wr0 = true;
					}
					else
					{
						br0 = true;
					}
				}
				else if(Board[to_r][to_c].charAt(Board[to_r][to_c].length() - 1) - '1' == 0)
				{
					if(player == 0)
					{
						wr1 = true;
					}
					else
					{
						br1 = true;
					}
				}
			}

			return result;
		}

		return false;
	}


	public boolean moveQueen(int player, int cur_r, int cur_c, int to_r, int to_c)
	{
		char p = 'w';
		if(player == 1)
			p = 'o';
		if(moveBishop(player, cur_r, cur_c, to_r, to_c) ^ moveRook(player, cur_r, cur_c, to_r, to_c))
		{
			if((Board[to_r][to_c].charAt(0) - p) == 0)
					return false;
			return true;
		}
		return false;


	}

	public boolean moveKnight(int player, int cur_r, int cur_c, int to_r, int to_c)
	{
		char p = 'w';
		if(player == 1)
			p = 'o';
		if((Math.abs(cur_r - to_r) == 1 && Math.abs(cur_c - to_c) == 2) || (Math.abs(cur_r - to_r) == 2 && Math.abs(cur_c - to_c) == 1) )
		{
			if((Board[to_r][to_c].charAt(0) - p) == 0)
					return false;
			return movePiece(player, cur_r, cur_c, to_r, to_c);
		}
		return false;

	}

	public boolean moveKing(int player, int cur_r, int cur_c, int to_r, int to_c)
	{
		// move king

		// Castling
		//Castling may only be done if the king has never moved, the
		//rook involved has never moved, the squares between the king
		//and the rook involved are unoccupied, the king is not in
		//check, and the king does not cross over or end on a square
		//attacked by an enemy piece.
		if(!check(player))
		{
			if(player == 1 && !bk0)
			{

				if(Board[7][0].equals("br0") && !br0)
				{
					if(to_c - cur_c == -2 && Board[7][1].equals("") && Board[7][2].equals(""))
					{
						boolean result = movePiece(player, cur_r, cur_c, to_r, to_c);
						if(result)
						{
							movePiece(player, 7, 0, 7, 3);
							br0 = true;
							bk0 = true;
							return true;
						}
						return false;
					}
				}
				if(Board[7][7].equals("br1") && !br1)
				{
					if(to_c - cur_c == 2 && Board[7][5].equals("") && Board[7][6].equals(""))
					{
						boolean result = movePiece(player, cur_r, cur_c, to_r, to_c);
						if(result)
						{
							movePiece(player, 7, 0, 7, 5);
							br1 = true;
							bk0 = true;
							return true;
						}
						return false;
					}
				}

			}

			else if(player == 0 && !wk0)
			{
				if(Board[0][0].equals("wr0") && !wr0)
				{
					if(to_c - cur_c == -2 && Board[0][1].equals("") && Board[0][2].equals(""))
					{
						boolean result = movePiece(player, cur_r, cur_c, to_r, to_c);
						if(result)
						{
							movePiece(player, 0, 0, 0, 3);
							wr0 = true;
							wk0 = true;
							return true;
						}
						return false;
					}
				}
				if(Board[0][7].equals("wr1") && !wr1)
				{
					if(to_c - cur_c == 2 && Board[7][5].equals("") && Board[7][6].equals(""))
					{
						boolean result = movePiece(player, cur_r, cur_c, to_r, to_c);
						if(result)
						{
							movePiece(player, 0, 7, 0, 5);
							wr1 = true;
							wk0 = true;
							return true;
						}
						return false;
					}
				}
			}
		}

		// If player did not try to castle
		else if(Math.abs(cur_r - to_r) == 0 && Math.abs(cur_c - to_c) == 0)
			return false;

		else if((Math.abs(cur_r - to_r) <= 1 && Math.abs(cur_c - to_c) <= 1))
		{
			boolean result = movePiece(player, cur_r, cur_c, to_r, to_c);
			if(result)
			{
				if(player == 0)
				{
					bk0 = true;
				}
				else
				{
					wk0 = true;
				}
			}
			return result;
		}
		return false;

	}

	public Vector<Vector<Integer>> getTrajectory(String piece)
	{
		char player = piece.charAt(0);
		char type = piece.charAt(1);
		switch(type)
		{
			case 'k': return getKingTrajectory(piece);
			case 'n': return getKnightTrajectory(piece);
			case 'p': return getPawnTrajectory(piece);
			case 'q': return getQueenTrajectory(piece);
			case 'r': return getRookTrajectory(piece);
			case 'b': return getBishopTrajectory(piece);
		}
		return new Vector<Vector<Integer>>();
	}

	public boolean isValid(int x, int y)
	{
		return (x >= 0 && x < 8) && (y >= 0 && y < 8);
	}

	public Vector<Vector<Integer>> getQueenTrajectory(String piece)
	{

		Vector<Vector<Integer>> bMove = getBishopTrajectory(piece);
		Vector<Vector<Integer>> rMove = getRookTrajectory(piece);

		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();

		Set<Integer> B = new HashSet<Integer>();
		Iterator<Vector<Integer>> i = bMove.iterator();
		while(i.hasNext())
		{
			Vector<Integer> cor = i.next();
			int row = (int)cor.get(0);
			int col = (int)cor.get(1);
			B.add(row*10 + col);

			Vector<Integer> item = new Vector<Integer>();
			item.add(row);
			item.add(col);
			result.add(item);
		}

		i = rMove.iterator();
		while(i.hasNext())
		{
			Vector<Integer> cor = i.next();
			int row = (int)cor.get(0);
			int col = (int)cor.get(1);

			if(B.contains(row*10 + col) == false)
			{
				Vector<Integer> item = new Vector<Integer>();
				item.add(row);
				item.add(col);
				result.add(item);
			}
		}

		return result;
	}

	public Vector<Vector<Integer>> getPawnTrajectory(String piece)
	{
		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();

		char player = piece.charAt(0);
		int index = Integer.parseInt(String.valueOf(piece.charAt(2)));

		int i, j;
		Vector<Integer> curCord = pieceCor.get(piece);
		i = (int)curCord.get(0);
		j = (int)curCord.get(1);
		if(player == 'b')
		{
				// top
				if(blackPawns[index] == false)
				{
					// white starts at bottom
					// move two steps if first move
					if(isValid(i - 2, j) && Board[i - 2][j].equals(""))
					{
						Vector<Integer> c = new Vector<Integer>();
						c.add(i - 2);
						c.add(j);
						result.add(c);
					}

				}
				// move one step if not blocked
				if(isValid(i - 1, j) && Board[i - 1][j].equals(""))
				{
					Vector<Integer> c = new Vector<Integer>();
					c.add(i - 1);
					c.add(j);
					result.add(c);
				}
				// capture diagonally
				if(isValid(i - 1, j - 1))
				{
					if(Board[i-1][j-1].equals("") == false && (Board[i - 1][j - 1].charAt(0) - 'w') == 0)
					{
						Vector<Integer> c = new Vector<Integer>();
						c.add(i - 1);
						c.add(j - 1);
						result.add(c);
					}
				}
				if(isValid(i - 1, j + 1))
				{
					if(Board[i-1][j+1].equals("") == false && (Board[i - 1][j + 1].charAt(0) - 'w') == 0)
					{
						Vector<Integer> c = new Vector<Integer>();
						c.add(i - 1);
						c.add(j + 1);
						result.add(c);
					}
				}
				// need to implement Enpassent
		}
		else
		{
			if(whitePawns[index] == false)
			{
				// black starts at top
				// move two steps if first move
				if(isValid(i + 2, j) && Board[i + 2][j].equals(""))
				{
					Vector<Integer> c = new Vector<Integer>();
					c.add(i + 2);
					c.add(j);
					result.add(c);
				}

			}
			// move one step if not blocked
			if(isValid(i + 1, j) && Board[i + 1][j].equals(""))
			{
				Vector<Integer> c = new Vector<Integer>();
				c.add(i + 1);
				c.add(j);
				result.add(c);
			}
			// capture diagonally
			if(isValid(i + 1, j + 1))
			{
				if(Board[i+1][j+1].equals("") == false && (Board[i + 1][j + 1].charAt(0) - 'b') == 0)
				{
					Vector<Integer> c = new Vector<Integer>();
					c.add(i + 1);
					c.add(j + 1);
					result.add(c);
				}
			}
			if(isValid(i + 1, j - 1))
			{ 
				if( Board[i+1][j-1].equals("") == false && Board[i + 1][j - 1].charAt(0) - 'b' == 0)
				{
					Vector<Integer> c = new Vector<Integer>();
					c.add(i + 1);
					c.add(j - 1);
					result.add(c);
				}
			}
			// need to implement Enpassent
		}
		return result;
	}
	public Vector<Vector<Integer>> getBishopTrajectory(String piece)
	{
		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();
		boolean up, down;
		boolean right, left;

		up = false;
		down = false;
		right = false;
		left = false;

		int x, y;
		Vector<Integer> coordinates = pieceCor.get(piece);
		x = (int)coordinates.get(0);
		y = (int)coordinates.get(1);
		char player = piece.charAt(0);
		char otherPlayer;

		if(player - 'w' == 0)
		{
			otherPlayer = 'b';
		}
		else
		{
			otherPlayer = 'w';
		}

		// left
		int j = y - 1;
		int i = x - 1;

		while(i >= 0 && j >= 0)
		{
			if(isValid(i, j)&&Board[i][j].equals("") == false &&  Board[i][j].charAt(0) - player != 0)
			{
				Vector<Integer> cor = new Vector<Integer>();
				cor.add(i);
				cor.add(j);
				result.add(cor);
				if(Board[i][j].charAt(0) - otherPlayer == 0)
				{
					left = true;
				}
				i -= 1;
				j -= 1;
			}
			if(left)
			{
				break;
			}
			else
			{
				break;
			}
		}

		// right
		j = y + 1;
		i = x + 1;
		while(i  < 8 && j < 8)
		{
			if(isValid(i, j)&&Board[i][j].equals("") == false &&  Board[i][j].charAt(0) - player != 0)
			{
				Vector<Integer> cor = new Vector<Integer>();
				cor.add(i);
				cor.add(j);
				result.add(cor);
				if(Board[i][j].charAt(0) - otherPlayer == 0)
				{
					right = true;
				}
				i += 1;
				j += 1;
			}
			if(right)
			{
				break;
			}
			else
			{
				break;
			}



		}

		// up
		j = y - 1;
		i = x + 1;

		while(i < 8 && j >= 0)
		{
			if(isValid(i, j)&&Board[i][j].equals("") == false &&  Board[i][j].charAt(0) - player != 0)
			{
				Vector<Integer> cor = new Vector<Integer>();
				cor.add(i);
				cor.add(j);
				result.add(cor);
				if(Board[i][j].charAt(0) - otherPlayer == 0)
				{
					up = true;
				}
				i += 1;
				j -= 1;
			}
			if(up)
			{
				break;
			}
			else
			{
				break;
			}



		}

		// down
		j = y + 1;
		i = x - 1;

		while(i >= 0 && j < 8)
		{
			if(isValid(i, j)&&Board[i][j].equals("") == false &&  Board[i][j].charAt(0) - player != 0)
			{
				Vector<Integer> cor = new Vector<Integer>();
				cor.add(i);
				cor.add(j);
				result.add(cor);
				if(Board[i][j].charAt(0) - otherPlayer == 0)
				{
					down = true;
				}
				i -= 1;
				j += 1;
			}
			if(down)
			{
				break;
			}
			else
			{
				break;
			}
		}

		return result;
	}

	public Vector<Vector<Integer>> getRookTrajectory(String piece)
	{
		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();
		boolean up, down;
		boolean right, left;

		up = false;
		down = false;
		right = false;
		left = false;

		int x, y;
		Vector<Integer> coordinates = pieceCor.get(piece);
		x = (int)coordinates.get(0);
		y = (int)coordinates.get(1);
		char player = piece.charAt(0);
		char otherPlayer;

		if(player - 'w' == 0)
		{
			otherPlayer = 'b';
		}
		else
		{
			otherPlayer = 'w';
		}

		// left
		for(int j = y - 1; j >= 0; j--)
		{
			if(isValid(x, j)&&Board[x][j].equals("") == false &&  Board[x][j].charAt(0) - player != 0)
			{
				Vector<Integer> cor = new Vector<Integer>();
				cor.add(x);
				cor.add(j);
				result.add(cor);
				if(Board[x][j].charAt(0) - otherPlayer == 0)
				{
					left = true;
				}
			}
			if(left)
			{
				break;
			}
			else
			{
				break;
			}
		}

		//right
		for(int j = y; j < 8; j++)
		{
			if(isValid(x, j)&&Board[x][j].equals("") == false &&  Board[x][j].charAt(0) - player != 0)
			{
				Vector<Integer> cor = new Vector<Integer>();
				cor.add(x);
				cor.add(j);
				result.add(cor);
				if(Board[x][j].charAt(0) - otherPlayer == 0)
				{
					right = true;
				}
			}
			if(right)
			{
				break;
			}
			else
			{
				break;
			}
		}

		//up
		for(int i = x; x < 8; x++)
		{
			if(isValid(i, y)&&Board[i][y].equals("") == false &&  Board[i][y].charAt(0) - player != 0)
			{
				Vector<Integer> cor = new Vector<Integer>();
				cor.add(i);
				cor.add(y);
				result.add(cor);
				if(Board[i][y].charAt(0) - otherPlayer == 0)
				{
					up = true;
				}
			}
			if(up)
			{
				break;
			}
			else
			{
				break;
			}
		}
		//down
		for(int i = x; x >= 0; x--)
		{
			if(isValid(i, y)&&Board[i][y].equals("") == false &&  Board[i][y].charAt(0) - player != 0)
			{
				Vector<Integer> cor = new Vector<Integer>();
				cor.add(i);
				cor.add(y);
				result.add(cor);
				if(Board[i][y].charAt(0) - otherPlayer == 0)
				{
					down = true;
				}
			}
			if(down)
			{
				break;
			}
			else
			{
				break;
			}
		}

		return result;
	}

	public Vector<Vector<Integer>> getKnightTrajectory(String piece)
	{
		int x, y;
		Vector<Integer> coordinates = pieceCor.get(piece);
		x = (int)coordinates.get(0);
		y = (int)coordinates.get(1);

		char player = piece.charAt(0);
		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();
		if(isValid(x - 2, y - 1) && Board[x - 2][y - 1].equals("") == false &&  Board[x - 2][y - 1].charAt(0) - player != 0)
		{
			Vector<Integer> cor = new Vector<Integer>();
			cor.add(x - 2);
			cor.add(y - 1);
			result.add(cor);

		}
		if(isValid(x + 2, y - 1) && Board[x + 2][y - 1].equals("") == false &&  Board[x + 2][y - 1].charAt(0) - player != 0)
		{
			Vector<Integer> cor = new Vector<Integer>();
			cor.add(x + 2);
			cor.add(y - 1);
			result.add(cor);

		}
		if(isValid(x - 2, y + 1) && Board[x - 2][y + 1].equals("") == false &&  Board[x - 2][y + 1].charAt(0) - player != 0)
		{
			Vector<Integer> cor = new Vector<Integer>();
			cor.add(x - 2);
			cor.add(y + 1);
			result.add(cor);

		}
		if(isValid(x + 2, y + 1) && Board[x + 2][y + 1].equals("") == false &&  Board[x + 2][y + 1].charAt(0) - player != 0)
		{
			Vector<Integer> cor = new Vector<Integer>();
			cor.add(x + 2);
			cor.add(y + 1);
			result.add(cor);

		}

		if(isValid(x - 1, y - 2) && Board[x - 1][y - 2].equals("") == false &&  Board[x - 1][y - 2].charAt(0) - player != 0)
		{
			Vector<Integer> cor = new Vector<Integer>();
			cor.add(x - 1);
			cor.add(y - 2);
			result.add(cor);

		}
		if(isValid(x + 1, y - 2) && Board[x + 1][y - 2].equals("") == false &&  Board[x + 1][y - 2].charAt(0) - player != 0)
		{
			Vector<Integer> cor = new Vector<Integer>();
			cor.add(x + 1);
			cor.add(y - 2);
			result.add(cor);

		}
		if(isValid(x - 1, y + 2) && Board[x - 1][y + 2].equals("") == false &&  Board[x - 1][y + 2].charAt(0) - player != 0)
		{
			Vector<Integer> cor = new Vector<Integer>();
			cor.add(x - 1);
			cor.add(y + 2);
			result.add(cor);

		}
		if(isValid(x + 1, y + 2) && Board[x + 1][y + 2].equals("") == false &&  Board[x + 1][y + 2].charAt(0) - player != 0)
		{
			Vector<Integer> cor = new Vector<Integer>();
			cor.add(x + 1);
			cor.add(y + 2);
			result.add(cor);
		}

		return result;

	}
	public Vector<Vector<Integer>> getKingTrajectory(String piece)
	{
		int x, y;
		Vector<Integer> coordinates = pieceCor.get(piece);
		x = (int)coordinates.get(0);
		y = (int)coordinates.get(1);

		char player = piece.charAt(0);

		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();
		if(x - 1 >= 0){
			if(Board[x-1][y].equals("")||(Board[x - 1][y].equals("") == false && Board[x - 1][y].charAt(0) - player != 0))
			{
				Vector<Integer> cor = new Vector<Integer>();
				cor.add(x - 1);
				cor.add(y);
				result.add(cor);
			}
			if(y - 1 >= 0){
				if(Board[x-1][y-1].equals("")||(Board[x - 1][y - 1].equals("") == false  && Board[x - 1][y - 1].charAt(0) - player != 0))
				{
					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x - 1);
					cor.add(y - 1);
					result.add(cor);
				}
			}
			if(y + 1 < 8){
				if(Board[x-1][y+1].equals("")||(Board[x - 1][y + 1].equals("") == false  && Board[x - 1][y + 1].charAt(0) - player != 0))
				{
					Vector<Integer> cor= new Vector<Integer>();
					cor.add(x - 1);
					cor.add(y + 1);
					result.add(cor);
				}
			}
		}
		if(x + 1 < 8)
		{
			if(y + 1 < 8){
				if(Board[x+1][y+1].equals("")||(Board[x + 1][y + 1].equals("") == false  && Board[x + 1][y + 1].charAt(0) - player != 0))
				{
					Vector<Integer> cor= new Vector<Integer>();
					cor.add(x + 1);
					cor.add(y + 1);
					result.add(cor);
				}
			}
			if(y - 1 >= 0){
				if(Board[x+1][y-1].equals("")||(Board[x + 1][y - 1].equals("") == false  && Board[x + 1][y - 1].charAt(0) - player != 0))
				{
					Vector<Integer> cor= new Vector<Integer>();
					cor.add(x + 1);
					cor.add(y - 1);
					result.add(cor);
				}
			}
		}
		if(y - 1 >= 0){
			if(Board[x][y-1].equals("")||(Board[x][y - 1].equals("") == false  && Board[x][y - 1].charAt(0) - player != 0))
				{
					Vector<Integer> cor= new Vector<Integer>();
					cor.add(x);
					cor.add(y - 1);
					result.add(cor);
				}
		}
		if(y + 1 < 8){
			if(Board[x][y+1].equals("")||(Board[x][y + 1].equals("") == false  && Board[x][y+1].charAt(0) - player != 0))
			{
				Vector<Integer> cor= new Vector<Integer>();
				cor.add(x);
				cor.add(y + 1);
				result.add(cor);
			}
		}

		return result;
	}

}
