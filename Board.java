import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class 
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


	private HashSet<String> whiteAlive;
	private HashSet<String> blackAlive;
	private HashMap<String, Vector<Integer>> M;
	private HashMap<String, Boolean> moved;
	// Vector<Moves>

	//  0: unmoved
	//  1: moved by onestep
	//  2: moved by 2 steps atleast one step before
	//  3: moved by 2 step one step ago
	// -2: promoted
	// -1: dead
	//  4: moved and 2 steps

	private int[] whitePawns;
	private int[] blackPawns;

	public Board()
	{
		B = new String[8][8];


		count = 0;
		whiteAlive = new HashSet<String>();
		blackAlive = new HashSet<String>();

		whitePawns = new int[8];
		blackPawns = new int[whitePawns.length];

		for(int i = 0; i < whitePawns.length; i++)
		{
			whitePawns[i] = 0;
			blackPawns[i] = 0;
		}

		M = new HashMap<String, Vector<Integer>>();
		moved = new HashMap<String, Boolean>();
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
					moved.put(B[i][j], false);

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
				if(!B[i][j].equals("") && (B[i][j].charAt(1) - 'q' == 0 ||B[i][j].charAt(1) - 'k' == 0))
				{
					if(j != 7){
						System.out.printf("| %s  ", t);
					}
					else
					{
						System.out.println("| "+ t + "  |");
					}
				}
				else{
					if(j != 7){
						System.out.printf("| %s ", t);
					}
					else
					{
						System.out.println("| "+ t + " |");
					}
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

		boolean isPawnMove = false;
		while(!(checkMate(0) || checkMate(1))){
			if(count%2 == 0)
			{
				System.out.println("White to play");
			}
			else
			{
				System.out.println("Black to play");
			}
			char player = (count%2 == 0)?'w':'b';

			boolean successfulMove = false;
			do{
				do{
					System.out.println("row: ");
					r = input.nextInt();
					System.out.println("col: ");
					c = input.nextInt();

					r -= 1;c -= 1;

				}while(!filled(count%2, r, c));

				do{
					System.out.println("to row:");
					r_ = input.nextInt();
					System.out.println("to col:");
					c_ = input.nextInt();

					r_ -= 1; c_ -= 1;

				}while(!(isValid(r_, c_) && !filled(count%2, r_, c_)));


				successfulMove = movePiece(r, c, r_, c_);
				if(successfulMove && B[r_][c_].charAt(1) - 'p' == 0)
				{
					isPawnMove = true;
				}
				//System.out.println(successfulMove);
			}while(!successfulMove);




	      if(isPawnMove && B[r_][c_].charAt(1)-'p'==0 && Math.abs(r - r_) == 1 && Math.abs(c-c_) == 1)
	      {
	        int sign = ((B[r_][c_].charAt(0)-'w')==0)?-1:1;
	        if(isValid(r_+sign ,c_) &&!empty(r_+sign,c_))
	        {
	          int index = Integer.parseInt(""+B[r_+sign][c_].charAt(2) );
	          if(count%2 == 0 && blackPawns[index]==3)
	          {
	            B[r_+sign][c_] = "";
	          }
	          else if(count%2 == 1 && whitePawns[index]==3)
	          {
	            B[r_+sign][c_] = "";
	          }
	        }
	      }

      		System.out.println(isPawnMove);
      		System.out.println(promotion(r_, c_));
			if(isPawnMove && promotion(r_,c_))
			{
				String p = (count%2 == 0)?"w":"b";
				System.out.println("What do u want to promote to?");
				System.out.println("q");
				System.out.println("n");
				System.out.println("r");
				System.out.println("b");
				//String promotedTo = input.nextLine();
       			String originalPiece = B[r_][c_];
       			int ind = Integer.parseInt(""+B[r_][c_].charAt(2));


       			M.remove(originalPiece);
       			if(count%2 == 0){whitePawns[ind] = -2; }
       			else{blackPawns[ind] = -2;}

       			char promotedPiece = input.next().charAt(0);
				B[r_][c_] = p+(""+promotedPiece)+String.valueOf(count);
				
				moved.put(B[r_][c_], false);

			}
			display();
			if(count%2 == 0)
			{
				for(int i = 0; i < blackPawns.length; i++)
				{
				  String piece = "bp" + String.valueOf(i);

				  if(blackAlive.contains(piece) && blackPawns[i] == 3){blackPawns[i] = 2;}
				  else if(!blackAlive.contains(piece)){blackPawns[i] = -1;}

				}
			}
			else
			{
				for(int i = 0; i < whitePawns.length; i++)
				{
				  String piece = "wp" + String.valueOf(i);
				  if(whiteAlive.contains(piece) && whitePawns[i] == 3){whitePawns[i] = 2;}
				  else if(!whiteAlive.contains(piece)){whitePawns[i] = -1;}
				}
			}
			M = new HashMap<String, Vector<Integer>>();
			whiteAlive = new HashSet<String>();
			blackAlive = new HashSet<String>();
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


			// Iterator itr = M.entrySet().iterator();
			// while(itr.hasNext())
			// {
			// 	Map.Entry me = (Map.Entry)itr.next();
			// 	Vector<Integer> cordinates = (Vector<Integer>)me.getValue();
			// 	System.out.println((String)me.getKey()+" "+(int)cordinates.get(0) + " " + (int)cordinates.get(1));
			// }

			// for(int i = 0; i < 8; i++)
			// {
			// 	System.out.println(whitePawns[i]);
			// }
			// for(int i = 0; i < 8; i++)
			// {
			// 	System.out.println(blackPawns[i]);
			// }

			count++;
		}
	}

	public boolean movePiece(int r, int c, int r_, int c_)
	{
		/**Returns true if it is possible to move piece from (r, c) to (r_, c_)*/
		String piece = B[r][c];

		boolean isPawn = (B[r][c].charAt(1) - 'p' == 0);

		boolean isKing = (B[r][c].charAt(1) - 'k' == 0);

		int player = (B[r][c].charAt(0) - 'w' == 0)?0:1;


		Vector<Vector<Integer>> trajectory = getTrajectory(B[r][c]);

		Iterator<Vector<Integer>> itr = trajectory.iterator();

		while(itr.hasNext())
		{
			Vector<Integer> cor = itr.next();
      		System.out.println(((int)cor.get(0) + 1) + " " + ((int)cor.get(1) + 1));
			if((int)cor.get(0) == r_ && (int)cor.get(1) == c_)
			{
				B[r_][c_] = B[r][c];
				B[r][c] = "";
				Boolean m = moved.get(piece);


				// Castling
				if(isKing && Math.abs(c - c_) == 2 &&n oCheck(player))
				{
					if(c_ == 2)
					{
						moved.put(B[r][0], true);
						forceMove(r, 0, r, 3);


					}
					else if(c_ == 6)
					{
						moved.put(B[r][0], true);
						forceMove(r, 7, r, 5);
					}
				}

				if(m == false)
				{
					moved.put(piece,true);
				}

				if(isPawn)
				{
					player = B[r_][c_].charAt(0);
					int index = Integer.parseInt("" + B[r_][c_].charAt(2));
					// 2 steps
					if(Math.abs(r_ - r) == 2)
					{
						if( (player - 'w') == 0)
						{whitePawns[index] = 3;}
						else{blackPawns[index] = 3;}
					}
					else if(Math.abs(r_ - r) == 1)
					{
						if( (player - 'w') == 0)
						{whitePawns[index] = 1;}
						else{blackPawns[index] = 1;}
					}
				}
				return true;
			}
		}
		return false;
	}

	public void forceMove(int r, int c, int r_, int c_)
	{
		String piece = B[r][c];

		B[r_][c_] = piece;
		B[r][c] = "";
	}

	public boolean checkMate(int player)
	{

      int counter = 0;
      Vector<Vector<Integer>> v = new Vector<Vector<Integer>>();
	    if(Check(player))
	    {
  			int x = 0;
  			int y = 0;
  			Vector<Integer> Pos;
  			String king = "wk";

  			if(player == 0) //player is white
  			{
  			    Pos = M.get("wk");
  			    x = (int)Pos.get(0);
  			    y = (int)Pos.get(1);

  			}
  			else if(player == 1)
  			{
  			    Pos = M.get("bk");
  			    x = (int)Pos.get(0);
  			    y = (int)Pos.get(1);
  			    king = "bk";
  			}
		  v = getNKingTrajectory(king);

			Iterator <Vector<Integer>> itr =  v.iterator();

			while(itr.hasNext())
			{
				Vector<Integer> C = itr.next();
				String capturedPiece = B[(int)C.get(0)][(int)C.get(1)];
				B[(int)C.get(0)][(int)C.get(1)] = king;
				B[x][y] = "";
				if(!Check(player))	//player is not in check
				{
					B[x][y] = king;
					B[(int)C.get(0)][(int)C.get(1)] = capturedPiece;
					return false;
				}
        else{counter++;}
				B[x][y] = king;
				B[(int)C.get(0)][(int)C.get(1)] = capturedPiece;

	        }

		}
    if(v.size()!= 0)
    {
      return counter == v.size();
    }
    return false;
	}

	public boolean Check(int player)
	{
		//0 if White
		//1 if black
		Iterator<String> it = blackAlive.iterator();
		int i = 0;
		int j = 0;
		if(player == 0)
		{
			//check to see which black pieces are alive and the squares that they cover
			it = blackAlive.iterator();
			Vector<Integer> kingCord = M.get("wk");
			i = (int)kingCord.get(0);
			j = (int)kingCord.get(1);
		}
		else if(player == 1)
		{
			it = whiteAlive.iterator();
			Vector<Integer> kingCord = M.get("bk");
			i = (int)kingCord.get(0);
			j = (int)kingCord.get(1);
		}

	   while(it.hasNext())
	   {

			Vector<Vector<Integer>> v = getNTrajectory(it.next());
			Iterator <Vector<Integer>> itr =  v.iterator();

			while(itr.hasNext())
			{
				Vector<Integer> C = itr.next();
				if( (int)C.get(0) == i && (int)C.get(1)== j )
				 return true;

			}
	   	}

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
				case 'k': return getKingTrajectory(piece);
				case 'q': return getQueenTrajectory(piece);
				case 'n': return getKnightTrajectory(piece);
				case 'b': return getBishopTrajectory(piece);
				case 'r': return getRookTrajectory(piece);
				case 'p': return getPawnTrajectory(piece);
			}
		}
		return new Vector<Vector<Integer>>();
	}

	public Vector<Vector<Integer>> getNTrajectory(String piece)
	{
		/**Returns the tragectory of the piece*/
		if(!piece.equals(""))
		{
			char type = piece.charAt(1);

			switch(type)
			{
				case 'k': return getNKingTrajectory(piece);
				case 'q': return getNQueenTrajectory(piece);
				case 'n': return getNKnightTrajectory(piece);
				case 'b': return getNBishopTrajectory(piece);
				case 'r': return getNRookTrajectory(piece);
				case 'p': return getNPawnTrajectory(piece);
			}
		}
		return new Vector<Vector<Integer>>();
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
		int player = ((p - 'w') == 0)? 0 : 1;
		String capturedPiece = B[r_][c_];


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

	public boolean canCastle(int player)
	{
		return (canCastleSide(player, 0) || canCastleSide(player, 1));
	}

	public boolean canCastleSide(int player, int left)
	{
		String king = "wk";
		String rook = "wr0";
		if(left != 0)
		{
			rook = "wr1";
		}
		if(player != 0)
		{
			king = "bk";
			if(left == 0){
				rook = "br0";
			}
			else
			{
				rook = "br1";
			}
		}
		int limit = 3;
		int sign = -1;
		if(left != 0)
		{
			limit = 2;
			sign = 1;
		}
		boolean kingMoved = moved.get(king);
		boolean rookMoved = moved.get(rook);

		if(!kingMoved && !rookMoved)
		{
			Vector<Integer> cor = M.get(king);
			int x = (int)cor.get(0);
			int y = (int)cor.get(1);
			for(int i = 1; i <= limit;i++)
			{
				if(!(isValid(x, y + sign*i) && empty(x, y + sign*i)))
				{
					return false;
				}
			}
			return true;
		}

		return false;
	}

	public boolean filled(int player, int r, int c)
	{
		/**Returns true if the piece occupied at r, c has the same color as player*/
		char p = (player == 0)?'w':'b';

		if(isValid(r, c) && !B[r][c].equals("") && B[r][c].charAt(0) - p == 0)
		{
			return true;
		}
		return false;
	}

	public boolean empty(int r, int c)
	{
		/**Returns true if the piece occupied at r, c is empty*/
		return B[r][c].equals("");
	}

	public boolean promotion(int r_ , int c_)
	{
		//if(piece.charAt(1) - 'p' == 0)
		//{
      String piece = B[r_][c_];
		  int x = r_;
			char p = piece.charAt(0);
			if(p - 'w' == 0)
			{
				if(x == 7)
					return true;
			}
			else
			{
				if(x == 0)
					return true;
			}
		//}
		return false;
	}

	public Vector<Vector<Integer>> getPawnTrajectory(String piece)
	{
		/** Gets the trajectory of pawn*/

		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();

		char p = piece.charAt(0);
		int player = ((p - 'w') == 0)? 0:1;
		int otherplayer = (player == 0)? 1:0;


		int ind = Integer.parseInt(""+piece.charAt(piece.length() - 1));

		Boolean m = moved.get(piece);

		int dY[] = new int[]{-1, 0, 1};
		int dX[] = new int[]{1};

		if(p -'w' == 0)
		{
			if(whitePawns[ind] == 0)
			{
				dX = new int[]{1, 2};
			}
			else if(whitePawns[ind] <= -1)
			{
				return result;
			}
		}
		else
		{
			dX = new int[]{-1};
			if(blackPawns[ind] == 0)
			{
				dX = new int[]{-1, -2};
			}
			else if(blackPawns[ind] <= -1)
			{
				return result;
			}
		}

		Vector<Integer> pos = M.get(piece);
		int x = (int) pos.get(0); int y = (int) pos.get(1);
		for(int i = 0; i < dX.length; i++)
		{
			int dx = dX[i];
			if(i == 0)
			{
				for(int dy: dY)
				{
          System.out.println("Pawn " + (x +  dx) + " "+ (y + dy));
          // System.out.println(filled(player,  x+dx, y+dy));
          System.out.println(filled(otherplayer,  x+dx, y+dy));
          //System.out.println((dy == 0||(dy != 0 && filled(otherplayer, x+dy, y + dy))));
          //System.out.println(dy == 0);
          //System.out.println((dy != 0 && filled(otherplayer, x+dy, y + dy)));
          System.out.println(dy != 0 );
          System.out.println((filled(otherplayer, x+dy, y + dy)));
          System.out.println(dy);
					if(isValid(x + dx, y + dy) && !filled(player, x+dx, y+dy) && noCheck(piece, x+dx, y+dy)){
						if(dy == 0||(dy != 0 && filled(otherplayer, x+dx, y + dy))){
					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x + dx); cor.add(y + dy);
					result.add(cor);}}
				}
			}
			else if(isValid(x + dx, y)){
        		int sign = (p - 'w' == 0)?1:-1;
				if(empty(x+sign, y) && empty(x+dx, y) && noCheck(piece, x+dx, y)){
					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x + dx); cor.add(y);
					result.add(cor);
				}
			}
		}

		// enpassent

		dY = new int[]{1, -1};
		int dx = (player==0)?1:-1;
		for(int dy: dY){
			if(isValid(x, y+dy) && filled(otherplayer, x, y+dy) && noCheck(piece, x, y+dy) && B[x][y+dy].charAt(1) - 'p' == 0)
			{
				ind = Integer.parseInt(""+B[x][y+dy].charAt(2));
				int val = (otherplayer == 0)?whitePawns[ind]:blackPawns[ind];
				if( isValid(x+dx, y+dy) && val == 3 && B[x][y+dy].charAt(1) - 'p' == 0)
				{
					Vector<Integer> cor = new Vector<Integer>();cor.add(x + 1); cor.add(y+dy);
					result.add(cor);
				}
			}
		}
		return result;
	}

	public Vector<Vector<Integer>> getKingTrajectory(String piece)
	{
		/** Gets the trajectory of King
		includes all possible moves that are valid and do not result in check
		*/

		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();
		int player = ((piece.charAt(0) - 'w') == 0)?0:1;
		Vector<Integer> Cor = M.get(piece);
		int r = (int)Cor.get(0);
		int c = (int)Cor.get(1);

		boolean castleLeft = canCastleSide(player, 0);
		boolean castleRight = canCastleSide(player, 1);


		int dY[] = new int[]{1, 0, -1};
		int dX[] = new int[]{1, 0, -1};

		for(int dy:dY)
		{
			for(int dx:dX)
			{
				int x = r + dx; int y = c + dy;
				if(isValid(x, y) && !filled(player, x, y) && noCheck(piece, x, y))
				{
					Cor = new Vector<Integer>();
					Cor.add(x); Cor.add(y);
					result.add(Cor);
				}
			}
		}

		if(castleLeft)
		{
			Cor = new Vector<Integer>();
			Cor.add(r); Cor.add(c - 2);
			result.add(Cor);

		}
		if(castleRight)
		{
			Cor = new Vector<Integer>();
			Cor.add(r); Cor.add(c + 2);
			result.add(Cor);
		}
		return result;

	}

	public Vector<Vector<Integer>> getRookTrajectory(String piece)
	{
		/** Gets the trajectory of rook
		includes all possible moves that are valid and do not result in check
		*/

		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();

		int player = ((piece.charAt(0) - 'w') == 0)?0:1;
		int otherplayer = (player == 0)?1:0;
		Vector<Integer> Cor = M.get(piece);
		int r = (int)Cor.get(0);
		int c = (int)Cor.get(1);



		int x = r;
		int y = c;

		boolean aboveChecked = false;
		boolean belowChecked = false;
		boolean leftChecked = false;
		boolean rightChecked = false;

		while(!(aboveChecked && belowChecked && leftChecked && rightChecked))
		{
			if(!aboveChecked)
			{x--;}
			else if(!belowChecked)
			{x++;}
			else if(!leftChecked)
			{y--;}
			else if(!rightChecked)
			{y++;}
			else{break;}

			if(isValid(x, y) && noCheck(piece, x, y))
			{
				if(empty(x, y))
				{

					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x);cor.add(y);
					result.add(cor);
				}
				else if(filled(player, x, y))
				{

					if(!aboveChecked) {aboveChecked = true;}
					else if(!belowChecked) {belowChecked = true;}
					else if(!leftChecked){leftChecked = true;}
					else if(!rightChecked){rightChecked = true;}
					else{break;}
					x = r;
					y = c;
				}
				else if(filled(otherplayer, x, y)){

					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x);cor.add(y);
					result.add(cor);
					if(!aboveChecked) {aboveChecked = true;}
					else if(!belowChecked) {belowChecked = true;}
					else if(!leftChecked){leftChecked = true;}
					else if(!rightChecked){rightChecked = true;}
					else{break;}
					x = r;
					y = c;
				}

			}
			if(x < 0){aboveChecked = true;x = r;y = c;}
			if(x > 8){belowChecked = true;x = r;y = c;}
			if(y < 0){leftChecked = true;x = r;y = c;}
			if(y > 8){rightChecked = true;x = r;y = c;}
		}
		return result;
	}


	public Vector<Vector<Integer>> getBishopTrajectory(String piece)
	{
		/** Gets the trajectory of bishop
		includes all possible moves that are valid and do not result in check
		*/

		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();

		int player = ((piece.charAt(0) - 'w') == 0)?0:1;
		int otherplayer = (player == 0)?1:0;

		Vector<Integer> Cor = M.get(piece);
		int r = (int)Cor.get(0);
		int c = (int)Cor.get(1);


		int x = r;
		int y = c;


		boolean upperLeftChecked = false;
		boolean upperRightChecked = false;
		boolean lowerLeftChecked = false;
		boolean lowerRightChecked = false;

		while(!(upperLeftChecked && upperRightChecked && lowerLeftChecked  && lowerRightChecked))
		{
			if(!upperLeftChecked)
			{x--;y--;}
			else if(!upperRightChecked)
			{x--;y++;}
			else if(!lowerLeftChecked )
			{x++;y--;}
			else if(!lowerRightChecked)
			{x++;y++;}
			else{break;}

			if(isValid(x, y) && noCheck(piece, x, y))
			{
				if(empty(x, y))
				{

					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x);cor.add(y);
					result.add(cor);
				}
				else if(filled(player, x, y))
				{

					if(!upperLeftChecked) {upperLeftChecked = true;}
					else if(!upperRightChecked) {upperRightChecked = true;}
					else if(!lowerLeftChecked){lowerLeftChecked = true;}
					else if(!lowerRightChecked){lowerRightChecked = true;}
					else{break;}
					x = r;
					y = c;
				}
				else if(filled(otherplayer, x, y)){

					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x);cor.add(y);
					result.add(cor);
					if(!upperLeftChecked) {upperLeftChecked = true;}
					else if(!upperRightChecked) {upperRightChecked = true;}
					else if(!lowerLeftChecked){lowerLeftChecked = true;}
					else if(!lowerRightChecked){lowerRightChecked = true;}
					else{break;}
					x = r;
					y = c;
				}

			}
			if(x < 0 && y < 0){upperLeftChecked = true;x = r;y = c;}
			if(x < 0 && y > 8){upperRightChecked= true;x = r;y = c;}
			if(x > 8 && y < 0){lowerLeftChecked = true;x = r;y = c;}
			if(x > 8 && y > 8){lowerRightChecked = true;x = r;y = c;}
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
		// int ind = Integer.parseInt(""+piece.charAt(piece.length() - 1));

		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();

		Vector<Integer> cor = M.get(piece);

		int i = cor.get(0);
		int j = cor.get(1);

		System.out.println("i " + i);
		System.out.println("j " + j);

		int dX[] = new int[]{2, 1};
		int dY[] = new int[]{1, 2};

		for(int ind = 0; ind < dX.length; ind++)
		{
			int X[] = new int[]{i + dX[ind], i - dX[ind]};
			int Y[] = new int[]{j + dY[ind], j - dY[ind]};
			for(int a: X)
			{
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
// ---------------------------------------------------------------------------------------
		public Vector<Vector<Integer>> getNPawnTrajectory(String piece)
	{
		/** Gets the trajectory of pawn*/

		// need to implement enpassent
		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();

		char p = piece.charAt(0);
		int player = ((p - 'w') == 0)? 0:1;
		int otherplayer = (player == 0)? 1:0;


		int ind = Integer.parseInt(""+piece.charAt(piece.length() - 1));

		Boolean m = moved.get(piece);

		int dY[] = new int[]{-1, 0, 1};
		int dX[] = new int[]{1};

		//  0: unmoved
		//  1: moved by onestep
		//  2: moved by 2 steps atleast one step before
		//  3: just moved by two step
		// -2: promoted
		// -1: dead
		if(p -'w' == 0)
		{
			if(whitePawns[ind] == 0)
			{
				dX = new int[]{1, 2};
			}
			else if(whitePawns[ind] <= -1)
			{
				return result;
			}
		}
		else
		{
			dX = new int[]{-1};
			if(blackPawns[ind] == 0)
			{
				dX = new int[]{-1, -2};
			}
			else if(blackPawns[ind] <= -1)
			{
				return result;
			}
		}

		Vector<Integer> pos = M.get(piece);
		int x = (int) pos.get(0); int y = (int) pos.get(1);
		for(int i = 0; i < dX.length; i++)
		{
			int dx = dX[i];
			if(i == 0)
			{
				for(int dy: dY)
				{
					if(isValid(x + dx, y + dy) && !filled(player, x+dx, y+dy)){
						if(dy == 0||(dy != 0 && filled(otherplayer, x+dx, y + dy))){
					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x + dx); cor.add(y + dy);
					result.add(cor);}}
				}
			}
			else if(isValid(x + dx, y)){
        		int sign = (p - 'w' == 0)?1:-1;
				if(empty(x+sign, y) && empty(x+dx, y)){
					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x + dx); cor.add(y);
					result.add(cor);
				}
			}
		}

		// enpassent
		// if the enempy pawn advances by 2 in its first move
		// then we can move diagonally and capture it
		dY = new int[]{1, -1};
		int dx = (player==0)?1:-1;
		for(int dy: dY){
			if(isValid(x, y+dy) && filled(otherplayer, x, y+dy) && B[x][y+dy].charAt(1) - 'p' == 0 )
			{
				ind = Integer.parseInt(""+B[x][y+dy].charAt(2));
				int val = (otherplayer == 0)?whitePawns[ind]:blackPawns[ind];
				if( isValid(x+dx, y+dy) && val == 3 && B[x][y+dy].charAt(1) - 'p' == 0)
				{
					Vector<Integer> cor = new Vector<Integer>();cor.add(x + 1); cor.add(y+dy);
					result.add(cor);
				}
			}
		}
		return result;
	}

	public Vector<Vector<Integer>> getNKingTrajectory(String piece)
	{
		/** Gets the trajectory of King
		includes all possible moves that are valid and do not result in check
		*/
		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();
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
				if(isValid(x, y) && !filled(player, x, y))
				{
					Cor = new Vector<Integer>();
					Cor.add(x); Cor.add(y);
					result.add(Cor);
				}
			}
		}
		return result;

	}

	public Vector<Vector<Integer>> getNRookTrajectory(String piece)
	{
		/** Gets the trajectory of rook
		includes all possible moves that are valid and do not result in check
		*/

		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();

		int player = ((piece.charAt(0) - 'w') == 0)?0:1;
		int otherplayer = (player == 0)?1:0;
		Vector<Integer> Cor = M.get(piece);
		int r = (int)Cor.get(0);
		int c = (int)Cor.get(1);



		int x = r;
		int y = c;

		boolean aboveChecked = false;
		boolean belowChecked = false;
		boolean leftChecked = false;
		boolean rightChecked = false;

		while(!(aboveChecked && belowChecked && leftChecked && rightChecked))
		{
			if(!aboveChecked)
			{x--;}
			else if(!belowChecked)
			{x++;}
			else if(!leftChecked)
			{y--;}
			else if(!rightChecked)
			{y++;}
			else{break;}

			if(isValid(x, y))
			{
				if(empty(x, y))
				{

					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x);cor.add(y);
					result.add(cor);
				}
				else if(filled(player, x, y))
				{

					if(!aboveChecked) {aboveChecked = true;}
					else if(!belowChecked) {belowChecked = true;}
					else if(!leftChecked){leftChecked = true;}
					else if(!rightChecked){rightChecked = true;}
					else{break;}
					x = r;
					y = c;
				}
				else if(filled(otherplayer, x, y)){

					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x);cor.add(y);
					result.add(cor);
					if(!aboveChecked) {aboveChecked = true;}
					else if(!belowChecked) {belowChecked = true;}
					else if(!leftChecked){leftChecked = true;}
					else if(!rightChecked){rightChecked = true;}
					else{break;}
					x = r;
					y = c;
				}

			}
			if(x < 0){aboveChecked = true;x = r;y = c;}
			if(x > 8){belowChecked = true;x = r;y = c;}
			if(y < 0){leftChecked = true;x = r;y = c;}
			if(y > 8){rightChecked = true;x = r;y = c;}
		}
		return result;
	}


	public Vector<Vector<Integer>> getNBishopTrajectory(String piece)
	{
		/** Gets the trajectory of bishop
		includes all possible moves that are valid and do not result in check
		*/

		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();

		int player = ((piece.charAt(0) - 'w') == 0)?0:1;
		int otherplayer = (player == 0)?1:0;

		Vector<Integer> Cor = M.get(piece);
		int r = (int)Cor.get(0);
		int c = (int)Cor.get(1);


		int x = r;
		int y = c;


		boolean upperLeftChecked = false;
		boolean upperRightChecked = false;
		boolean lowerLeftChecked = false;
		boolean lowerRightChecked = false;

		while(!(upperLeftChecked && upperRightChecked && lowerLeftChecked  && lowerRightChecked))
		{
			if(!upperLeftChecked)
			{x--;y--;}
			else if(!upperRightChecked)
			{x--;y++;}
			else if(!lowerLeftChecked )
			{x++;y--;}
			else if(!lowerRightChecked)
			{x++;y++;}
			else{break;}

			if(isValid(x, y))
			{
				if(empty(x, y))
				{

					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x);cor.add(y);
					result.add(cor);
				}
				else if(filled(player, x, y))
				{

					if(!upperLeftChecked) {upperLeftChecked = true;}
					else if(!upperRightChecked) {upperRightChecked = true;}
					else if(!lowerLeftChecked){lowerLeftChecked = true;}
					else if(!lowerRightChecked){lowerRightChecked = true;}
					else{break;}
					x = r;
					y = c;
				}
				else if(filled(otherplayer, x, y)){

					Vector<Integer> cor = new Vector<Integer>();
					cor.add(x);cor.add(y);
					result.add(cor);
					if(!upperLeftChecked) {upperLeftChecked = true;}
					else if(!upperRightChecked) {upperRightChecked = true;}
					else if(!lowerLeftChecked){lowerLeftChecked = true;}
					else if(!lowerRightChecked){lowerRightChecked = true;}
					else{break;}
					x = r;
					y = c;
				}

			}
			if(x < 0 && y < 0){upperLeftChecked = true;x = r;y = c;}
			if(x < 0 && y > 8){upperRightChecked= true;x = r;y = c;}
			if(x > 8 && y < 0){lowerLeftChecked = true;x = r;y = c;}
			if(x > 8 && y > 8){lowerRightChecked = true;x = r;y = c;}
		}

		return result;
	}

	public Vector<Vector<Integer>> getNQueenTrajectory(String piece)
	{
		/** Gets the trajectory of Queen
		includes all possible moves that are valid and do not result in check
		*/

		// Bassically concatinate the trajectory of bishop with that of rook
		Vector<Vector<Integer>> result = getNBishopTrajectory(piece);
		Vector<Vector<Integer>> toAdd = getNRookTrajectory(piece);

		Iterator<Vector<Integer>> itr = toAdd.iterator();
		while(itr.hasNext())
		{
			result.add(itr.next());
		}
		return result;
	}

	public Vector<Vector<Integer>> getNKnightTrajectory(String piece)
	{
		/** Gets the trajectory of knight
		includes all possible moves that are valid and do not result in check
		*/
		char player = piece.charAt(0);
		// int ind = Integer.parseInt(""+piece.charAt(piece.length() - 1));

		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();

		Vector<Integer> cor = M.get(piece);

		int i = cor.get(0);
		int j = cor.get(1);

		int dX[] = new int[]{2, 1};
		int dY[] = new int[]{1, 2};

		for(int ind = 0; ind < dX.length; ind++)
		{
			int X[] = new int[]{i + dX[ind], i - dX[ind]};
			int Y[] = new int[]{j + dY[ind], j - dY[ind]};
			for(int a: X){for(int b: Y){
					if(isValid(a, b))
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
