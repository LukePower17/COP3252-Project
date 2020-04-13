import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class NewBoard
{
	public static void main(String args[])
	{
		Board b = new Board();
		b.display();
		b.play();
	}

}

class Board{

	private String[][] B;
	private int count;
	private boolean[] blackRooks;
	private boolean[] whiteRooks;
	private boolean[] whitePawns;
	private boolean[] blackPawns;
	private HashSet<String> whiteAlive;
	private HashSet<String> blackAlive;
	private HashMap<String, Vector<Integer>> M;
	public Board()
	{
		B = new String[8][8];
		
		blackRooks = new boolean[]{false, false};
		whiteRooks = new boolean[]{false, false};
		whitePawns = new boolean[8];
		for(int i = 0; i < whitePawns.length; i++)
		{
			whitePawns[i] = false;
		}

		blackPawns = new boolean[8];
		for(int i = 0; i < blackPawns.length; i++)
		{
			blackPawns[i] = false;
		}

		count = 0;
		whiteAlive = new HashSet<String>();
		blackAlive = new HashSet<String>();
		M = new HashMap<String, Vector<Integer>>();

		// white is at the top
		String p[] = new String[]{"w", "b"};
		// intialize the pawns
		for(int j = 0; j < p.length; j++)
		{	
			int r = (j == 0)? 1:6; 
			for(int i = 0; i < 8; i++)
			{
				B[r][i] = p[j]+"p" + Integer.toString(i); 
			}
			int r_ = (r == 1)? 0:7;
			B[r_][0] = p[j]+"r0";
			B[r_][1] = p[j]+"n0";
			B[r_][2] = p[j]+"b0";
			B[r_][3] = p[j]+"q";
			B[r_][4] = p[j]+"k";
			B[r_][5] = p[j]+"b1";
			B[r_][6] = p[j]+"n1";
			B[r_][7] = p[j]+"r1";
		}
		for(int i = 2; i < 6; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				B[i][j] = "";
			}
		}
		
		// initalize sets and Map
		for(int i = 0; i < B.length; i++)
		{
			for(int j = 0; j < B[0].length; j++)
			{
				if(!B[i][j].equals(""))
				{
					Vector<Integer> v = new Vector<Integer>();
					v.add(i); v.add(j);
					M.put(B[i][j], v);

					if(B[i][j].charAt(0) - 'w' == 0)
					{
						whiteAlive.add(B[i][j]);
					}
					else
					{
						blackAlive.add(B[i][j]);
					}
				}
			}
		}
	}

	public void display()
	{
		/**Prints the board to the screen*/
		System.out.println("    a      b      c    d    e    f    g      h\n");

		for(int i = 0; i < 8; i++)
		{
			System.out.printf((i+1) + " ");
			for(int j = 0; j < 8; j++)
			{

				String t = (B[i][j].equals(""))?"   ":B[i][j];
				if(j != 7){
					System.out.printf("| %s ", t);
				}
				else
				{
					System.out.println("| "+ t + " |");
				}
			}
			System.out.println("  -----------------------------------------------");
		}
	}

	public void play()
	{
		int r, c;
		int r_, c_;
		Scanner input = new Scanner(System.in);

		while(!(checkMate(0) || checkMate(1))){
			if(count%2 == 0)
			{
				System.out.println("White to play");
			}
			else
			{
				System.out.println("Black to play");
			}
			char player = (count%2)?'w':'b';
			
			boolean succesfulMove = false;
			do{
				do{
					System.out.println("row: ");
					r = input.nextInt();
					System.out.println("col: ");
					c = input.nextInt();
				}while(!filled(count%2, r, c));

				do{
					System.out.println("to row:");
					r_ = input.nextInt();
					System.out.println("to col:");
					c_ = input.nextInt();
				}while(!(isValid(r_, c_) && !filled(count%2, r, c)));

				successfulMove = MovePiece(r, c, r_, c_);
			}while(!succesfulMove);
			
			display();
			
			count++;
		}
	}

	public boolean movePiece(int r, int c, int r_, int c_)
	{
		/**Returns true if it is possible to move piece from (r, c) to (r_, c_)*/
		String piece = B[r][c];
		
		Vector<Vector<Integer>> tragectory = getTragectory(B[r][c]);

		Iterator<Vector<Integer>> itr = tragectory.iterator();

		while(itr.hasNext())
		{
			Vector<Integer> c = itr.next();

			if((int)c.get(0) == r_ && (int)c.get(1) == c_)
				return true;
		}
		return false;
	}

	public boolean checkMate(int player)
	{
		// need to implementt
		return false;
	}

	public boolean Check(int player)
	{
		// need to implementt
		return false;
	}

	public Vector<Vector<Integer>> getTrajectory(String piece)
	{
		/**Returns the tragectory of the piece*/
		if(!piece.equals(""))
		{
			char type = piece.charAt(1);

			switch(type)
			{
				case 'k': return getKingTragectory(piece);
				case 'q': return getQueenTrajectory(piece);
				case 'n': return getKnightTrajectory(piece);
				case 'b': return getBishopTrajectory(piece);
				case 'r': return getRookTrajectory(piece);
				case 'p': return getPawnTrajectory(piece);
			}
		}
	}

	public boolean isValid(int x, int y)
	{
		/**Returns true if the piece occupied at x, y is in the board*/
		return (x >= 0 && x < 8) && (y >= 0 && y < 8);
	}

	public boolean noCheck(String piece, int r_, int c_)
	{

		/** Checks if moving the piece to r_ c_ results in a check
			returns true if the move results in a check
		*/
		char p = piece.charAt(0);
		int player = ((p - 'w') == 0): 0 ? 1;
		String capturedPiece = B[r_][c_]
		

		Vector<Integer> Cor = M.get(piece);
		int r = (int)Cor.get(0);
		int c = (int)Cor.get(1);

		B[r_][c_] = B[r][c];
		B[r][c] = "";

		boolean result = !(Check(player));
		
		B[r_][c_] = capturedPiece;
		B[r][c] = piece;

		return result;
	}

	public boolean filled(int player, int r, int c)
	{
		/**Returns true if the piece occupied at r, c has the same color as player*/
		char p = (player == 0)?'w':'b';
		if(isValid(r, c) && !B[r][c].equals("") && B[r][c].charAt(0) - player == 0)
			return true;
		return false;
	}

	public boolean empty(int r, int c)
	{
		/**Returns true if the piece occupied at r, c is empty*/
		return B[r][c].equals("");
	}

	public Vector<Vector<Integer>> getPawnTrajectory(String piece)
	{
		// need to implement
		return new Vector<Vector<Integer>>();
	}

	public Vector<Vector<Integer>> getKingTrajectory(String piece)
	{
		/** Gets the trajectory of King
		includes all possible moves that are valid and do not result in check
		*/
		Vector<Vector<Integer> result = new Vector<Vector<Integer>>();
		int player = ((piece.charAt(0) - 'w') == 0)?0:1;
		Vector<Integer> Cor = M.get(piece);
		int r = (int)Cor.get(0);
		int c = (int)Cor.get(1);

		int dY[] = new int[]{1, 0, -1};
		int dX[] = new int[]{1, 0, -1};

		for(int dy:dY)
		{
			for(int dx:dX)
			{
				int x = r + dx; int y = c + dy;
				if(isValid(x, y) && !filled(player, x, y) && noCheck(piece, x, y))
				{
					Vector<Integer> C = new Vector<Integer>();
					C.add(x); C.add(y);
					result.add(C);
				}
			}
		}


	}

	public Vector<Vector<Integer>> getRookTrajectory(String piece)
	{
		/** Gets the trajectory of rook 
		includes all possible moves that are valid and do not result in check
		*/

		Vector<Vector<Integer> result = new Vector<Vector<Integer>>();

		int player = ((piece.charAt(0) - 'w') == 0)?0:1;

		Vector<Integer> Cor = M.get(piece);
		int r = (int)Cor.get(0);
		int c = (int)Cor.get(1);

		// check vertically
		int x = r - 1;
		int y = c;

		boolean aboveChecked = false;
		boolean belowChecked = false;
		boolean leftChecked = false;
		boolean rightChecked = false;
		
		while(!(aboveChecked && belowChecked && leftChecked && rightChecked))
		{
			if(isValid(x, y) && noCheck(piece, x, y))
			{
				if(empty(x, y))
				{
					Vector<Integer> c = new Vector<Integer>();
					c.add(x);c.add(y);
					result.add(c);
				}
				if(filled(player, x, y))
				{
					if(!aboveChecked)
					{
						aboveChecked = true;
						x = r;
						y = c;
					}
					else if(!belowChecked)
					{
						belowChecked = true;
						x = r;
						y = c;
					}
					else if(!leftChecked)
					{
						leftChecked = true;
						x = r;
						y = c;
					}
					else if(!rightChecked)
					{
						rightChecked = true;
						x = r;
						y = c;
					}
				}
				else{
					Vector<Integer> c = new Vector<Integer>();
					c.add(x);c.add(y);
					result.add(c);
					if(!aboveChecked)
					{
						aboveChecked = true;
						x = r;
						y = c;
					}
					else if(!belowChecked)
					{
						belowChecked = true;
						x = r;
						y = c;
					}
					else if(!leftChecked)
					{
						leftChecked = true;
						x = r;
						y = c;
					}
					else if(!rightChecked)
					{
						rightChecked = true;
						x = r;
						y = c;
					}
				}
			}
			if(!aboveChecked)
			{x--;}
			else if(!belowChecked)
			{x++;}
			else if(!leftChecked)
			{y--;}
			else if(!rightChecked)
			{y++;}
		}
		return result;
	}


	public Vector<Vector<Integer>> getBishopTrajectory(String piece)
	{
		/** Gets the trajectory of bishop 
		includes all possible moves that are valid and do not result in check
		*/

		Vector<Vector<Integer> result = new Vector<Vector<Integer>>();

		int player = ((piece.charAt(0) - 'w') == 0)?0:1;

		Vector<Integer> Cor = M.get(piece);
		int r = (int)Cor.get(0);
		int c = (int)Cor.get(1);

		// check vertically
		int x = r - 1;
		int y = c;
		

		boolean upperLeftChecked = false;
		boolean upperRightChecked = false;
		boolean lowerLeftChecked = false;
		boolean lowerRightChecked = false;
		
		while(!(upperLeftChecked && upperRightChecked && lowerLeftChecked && lowerRightChecked))
		{
			if(isValid(x, y) && noCheck(piece, x, y))
			{
				if(empty(x, y))
				{
					Vector<Integer> c = new Vector<Integer>();
					c.add(x);c.add(y);
					result.add(c);
				}
				if(filled(player, x, y))
				{
					if(!upperLeftChecked)
					{
						upperLeftChecked = true;
						x = r;
						y = c;
					}
					else if(!upperRightChecked)
					{
						upperRightChecked = true;
						x = r;
						y = c;
					}
					else if(!lowerLeftChecked)
					{
						lowerLeftChecked = true;
						x = r;
						y = c;
					}
					else if(!lowerRightChecked)
					{
						lowerRightChecked = true;
						x = r;
						y = c;
					}
				}
				else{
					Vector<Integer> c = new Vector<Integer>();
					c.add(x);c.add(y);
					result.add(c);
					if(!upperLeftChecked)
					{
						upperLeftChecked = true;
						x = r;
						y = c;
					}
					else if(!upperRightChecked)
					{
						upperRightChecked = true;
						x = r;
						y = c;
					}
					else if(!lowerLeftChecked)
					{
						lowerLeftChecked = true;
						x = r;
						y = c;
					}
					else if(!lowerRightChecked)
					{
						lowerRightChecked = true;
						x = r;
						y = c;
					}

				}
			}
			if(!upperLeftChecked)
			{x--;y--;}
			else if(!upperRightChecked)
			{x--;y++}
			else if(!lowerLeftChecked)
			{x++;y--;}
			else if(!lowerRightChecked)
			{x++;y++;}
		}
		return result;
	}

	public Vector<Vector<Integer>> getQueenTrajectory(String piece)
	{
		/** Gets the trajectory of Queen
		includes all possible moves that are valid and do not result in check
		*/

		// Bassically concatinate the trajectory of bishop with that of rook
		Vector<Vector<Integer>> result = getBishopTrajectory(piece);
		Vector<Vector<Integer>> toAdd = getRookTrajectory(piece);

		Iterator<Vector<Integer>> itr = toAdd.iterator();
		while(itr.hasNext())
		{
			result.add(itr.next());
		}
		return result;
	}

	public Vector<Vector<Integer>> getKnightTrajectory(String piece)
	{
		/** Gets the trajectory of knight
		includes all possible moves that are valid and do not result in check
		*/
		char player = piece.charAt(0);
		int ind = Integer.parseInt(""+piece.charAt(piece.length() - 1));

		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();

		Vector<Integer> cor = M.get(piece);

		int i = cor.get(0);
		int j = cor.get(1);

		int dX[] = new int[]{2, 1};
		int dY[] = new int[]{1, 2}

		for(int ind = 0; ind < dX.length; ind++)
		{
			int x = i + dX[ind];
			int y = j + dY[ind];

			int y_ = j - dY[ind];
			int x_ = i - dX[ind];

			int X[] = new int[]{x, x_};
			int Y[] = new int[]{y, y_};
			for(int a: X){
				for(int b: Y){
					if(isValid(a, b) && noCheck(piece, a, b))
					{
						if(B[a][b].equals(""))
						{
							Vector<Integer> C = new Vector<Integer>();
							C.add(a);
							C.add(b);

							result.add(C);
						}
						else if(B[a][b].charAt(0) - player != 0)
						{
							Vector<Integer> C = new Vector<Integer>();
							C.add(a);
							C.add(b);

							result.add(C);
						}
					}
				}
			}

		}
		return result;
	}
}