import java.util.ArrayList;
import java.util.Collections;

public class AI {
	//set default search time to 10 seconds
	private static long searchTime = 10000;
	
	private static long maxSearchTime;
	
	//returns the next action the ai should take
	public static Board getMove(Board currentBoard){
		//find the ending time based on current clock
		maxSearchTime = System.currentTimeMillis() + searchTime;
		
		//generate all moves from current state
		ArrayList<Board> successors = currentBoard.findLegalMoves(true);
		
		//only allow depth 0, and iteratively search deeper
		int depth = 0;
		while(System.currentTimeMillis() > maxSearchTime){
			for(Board child : successors){
				//do the alpha-beta pruning for the min of each of the moves in the list since it's max's successor
				child.value = min(child, Integer.MIN_VALUE, Integer.MAX_VALUE, depth++);
			}
		}
		
		//choose the best of the all the next moves for max
		Board bestMove = Collections.max(successors, new Board());
		return bestMove;
	}
	
	//finds the largest element of the tree, until terminal is reached, time runs out, or depth is reached
	private static int max(Board state, int alpha, int beta, int depth){
		int isTerminal = state.finalState();
		
		//first two cases test if the board is a win/loss
		if(isTerminal == 1){
			return Integer.MAX_VALUE;
		}
		else if(isTerminal == 2){
			return Integer.MIN_VALUE;
		}
		//covers time limit, and depth limit case
		else if(System.currentTimeMillis() > maxSearchTime || depth == 0){
			return state.calculateValue();
		}
		
		int value = Integer.MIN_VALUE;
		
		//generate all possible moves from current state, subject to max's move
		ArrayList<Board> children = state.findLegalMoves(true);
		
		//search through all moves
		for(Board child : children){
			//beta prune, and chose best of the two values
			value = Math.max(value,  min(child, alpha, beta, depth - 1));
			
			//a better min value is found, so return that
			if(value >= beta){
				return value;
			}
			
			//change upper bound
			alpha = Math.max(alpha, value);
		}
		
		//tree finished
		return value;	
	}
	
	//finds the smallest element of the tree, until terminal is reached, time runs out, or depth is reached
	private static int min(Board state, int alpha, int beta, int depth) {
		int isTerminal = state.finalState();
		
		//first two cases test if the board is a win/loss
		if(isTerminal == 1){
			return Integer.MAX_VALUE;
		}
		else if(isTerminal == 2){
			return Integer.MIN_VALUE;
		}
		//covers time limit, and depth limit case
		else if(System.currentTimeMillis() > maxSearchTime || depth == 0){
			return state.calculateValue();
		}
		
		int value = Integer.MAX_VALUE;
		
		//generate all possible moves from current state, subject to min's move
		ArrayList<Board> children = state.findLegalMoves(false);
		
		//search through all moves
		for(Board child : children){
			//alpha prune all moves, and choose lowest of these values
			value = Math.min(value,  max(child, alpha, beta, depth - 1));
			
			if(value <= alpha){
				return value;
			}
			
			beta = Math.min(beta, value);
		}
		
		return value;	
	}

	//set maximum search time of the algorithm
	public static void setSearchTime(int length){
		searchTime = length * 1000;
	}
}
