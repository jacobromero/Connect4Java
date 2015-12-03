import java.util.ArrayList;
import java.util.Collections;

//TODO fix alpha beta pruning, not returning, i think i need to have it return a move to make rather than a board.
//Look at 420 notes...
public class AI {
	private static long searchTime = 10000;
	private static long maxSearchTime;
	
	public static Board getMove(Board currentBoard){
		maxSearchTime = System.currentTimeMillis() + searchTime;
		
		ArrayList<Board> successors = currentBoard.findLegalMoves(true);
		
		int depth = 0;
		while(System.currentTimeMillis() > maxSearchTime){
			for(Board child : successors){
				child.value = min(child, Integer.MIN_VALUE, Integer.MAX_VALUE, depth++);
			}
		}
		
		
		
		Board bestMove = Collections.max(successors, new Board());
		
		return bestMove;
	}
	
	private static int max(Board state, int alpha, int beta, int depth){
		int isTerminal = state.finalState();
		if(System.currentTimeMillis() > maxSearchTime || depth == 0 || isTerminal == 1 || isTerminal == 2){
			return state.calculateValue();
		}
		
		int value = Integer.MIN_VALUE;
		ArrayList<Board> children = state.findLegalMoves(true);
		
		for(Board child : children){
			value = Math.max(value,  min(child, alpha, beta, depth - 1));
			
			if(value >= beta){
				return value;
			}
			
			alpha = Math.max(alpha, value);
		}
		
		return value;	
	}
	
	private static int min(Board state, int alpha, int beta, int depth) {
		int isTerminal = state.finalState();
		if(System.currentTimeMillis() > maxSearchTime || depth == 0 || isTerminal == 1 || isTerminal == 2){
			return state.calculateValue();
		}
		
		int value = Integer.MAX_VALUE;
		ArrayList<Board> children = state.findLegalMoves(false);
		
		for(Board child : children){
			value = Math.max(value,  max(child, alpha, beta, depth - 1));
			
			if(value <= alpha){
				return value;
			}
			
			beta = Math.min(beta, value);
		}
		
		return value;	
	}

	public static void setSearchTime(int length){
		searchTime = length * 1000;
	}
	
	public static Board alphaBeta(Board currentBoard, boolean computer, int bestValue, int worstValue, int depth){
	
		//only return best board...
		int finalState = currentBoard.finalState();
		if(depth == 0 || System.currentTimeMillis() > maxSearchTime || finalState == 1 || finalState == 2){
			return currentBoard;
		}
		
		Board tmp = null;
		ArrayList<Board> children = currentBoard.findLegalMoves(computer);
		if(computer){
			for(Board child : children){
				if(System.currentTimeMillis() < maxSearchTime){
					tmp = alphaBeta(child, false, worstValue, bestValue, depth - 1);
					tmp.value = tmp.calculateValue();
					if(tmp.value > bestValue){
						bestValue = tmp.value;
					}
					else if (bestValue >= worstValue) {
	                    break;
	                }
				}
				else{
					break;
				}
			}
			
			return tmp;
		}
		else{
			for(Board child : children){
				tmp = alphaBeta(child, true, worstValue, bestValue, depth - 1);
				tmp.value = tmp.calculateValue();
				if(tmp.value < worstValue){
					bestValue = tmp.value;
				}
				else if (bestValue <= worstValue) {
                    break;
				}
			}
			return tmp;
		}
	}
}
