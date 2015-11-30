import java.util.ArrayList;

//TODO fix alpha beta pruning, not returning, i think i need to have it return a move to make rather than a board.
//Look at 420 notes...
public class AI {
	private static long searchTime = 10000;
	private static long maxSearchTime;
	
	public static Board getMove(Board currentBoard){
		Board nextMove = null;
		maxSearchTime = System.currentTimeMillis() + searchTime;
		
		int depth = 0;
		while(System.currentTimeMillis() <= maxSearchTime)
			nextMove = alphaBeta(currentBoard, true, Integer.MAX_VALUE, Integer.MIN_VALUE, depth++);
		
		return nextMove;
	}
	
	public static void setSearchTime(int length){
		searchTime = length * 1000;
	}
	
	public static Board alphaBeta(Board currentBoard, boolean computer, int bestValue, int worstValue, int depth){
		int finalState = currentBoard.finalState();
		if(System.currentTimeMillis() > maxSearchTime || finalState == 1 || finalState == 2){
			return currentBoard;
		}
		
		Board tmp = null;
		ArrayList<Board> children = currentBoard.findLegalMoves(computer);
		if(computer){
			for(Board child : children){
				tmp = alphaBeta(child, false, worstValue, bestValue, depth - 1);
				tmp.value = tmp.calculateValue();
				if(tmp.value > bestValue){
					bestValue = tmp.value;
				}
				else if (bestValue >= worstValue) {
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
