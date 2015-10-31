import connectK.CKPlayer;
import connectK.BoardModel;
import java.awt.Point;



public class NexusAI extends CKPlayer {
	
	

	boolean gravity;
	byte toMove;  //holds inital player whose move it is
	int maxDepth; //stops limits the depth of the search, the search counts down from this int and stops at zero
	//modify this to create a depth limited search
	
	//these are placed in the class to prevent the unneeded copying of there values in the search functions
	//recursive calls
	int bestX = 0; //stores the x value for the best choice of moves
	int bestY = 0; //stores the y value for the best choice of moves
	int max, min; //used in the search to hold the current max, 
	int returnValue; 

	
	//returns the other player 
	byte switchPlayer(byte player)
	{
		if(player == 1)
			return 2;
		else
			return 1;
	}
	
	//constructor
	public NexusAI(byte player, BoardModel state) {
		super(player, state);
		teamName = "Nexus";
		toMove = player;
		maxDepth = 4;
		
	}
	
	//Heuristic function	
	public int evalPosition(BoardModel state)
	{
		return 0;
	}
	
	
	@Override
	public Point getMove(BoardModel state) {
	
		BoardModel clonedState = state.clone();
		int depth = maxDepth;
		search(clonedState, depth, toMove);
		//best x and best y are stored in the class
		return new Point(bestX,bestY);
		
		/*for(int i=0; i<state.getWidth(); ++i)
			for(int j=0; j<state.getHeight(); ++j)
				if(state.getSpace(i, j) == 0)
					return new Point(i,j);*/
		//return null;
	}
	
	//recursive function that implements a minimax search
	public int search(BoardModel state, int depth, byte player)
	{
		
		//make shift positive and negative infinity
		int min = -100000;
		int max = 100000;
		
		if(depth == 0) //if the depth limit is reached
			return evalPosition(state);
		if(!state.hasMovesLeft()) //if the position has a winner return evaluation
			return evalPosition(state);
		
		if(player == 1) //if it is the AI's turn to move, look for max move
		{  
			for(int i=0; i<state.getWidth(); ++i) //for all possible moves
				for(int j=0; j<state.getHeight(); ++j)
					if(state.getSpace(i, j) == 0)
					{
					BoardModel s = state.clone(); //get a clone of the board
					//make a move and recurse
					returnValue = search(s.placePiece(new Point(i,j), player),depth -1, switchPlayer(player));
					//if the value of the heuristic function is greater than the max value so far replace it
						if(returnValue > max)  
						{
							max = returnValue;
							if(depth == maxDepth) //if the search has recursed all the way back to the first level
							{
								//update the best move choice
								bestX = i;
								bestY = j;
							}
						}
					
					}
			return max;
		}
		else
		{
			for(int i=0; i<state.getWidth(); ++i)
				for(int j=0; j<state.getHeight(); ++j)
					if(state.getSpace(i, j) == 0)
					{
						BoardModel s = state.clone();
						returnValue = search(s.placePiece(new Point(i,j), player), depth - 1, switchPlayer(player));
							if(returnValue < min)
							{
								min = returnValue;
							}
					}
			return min;
		}
	}
	
	@Override
	public Point getMove(BoardModel state, int deadline) {
		return getMove(state);
	}
}	
