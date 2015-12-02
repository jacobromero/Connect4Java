import java.util.ArrayList;
import java.util.Comparator;

//TODO nothing I think. this class maybe ok.
public class Board implements Comparator<Board>{
	public char[][] board = new char[8][8];
    public int value;
    public String lastMove;
    
    public Board(){
    	for(int i = 0; i < 8; i++){
    		for(int j = 0; j < 8; j++){
    			board[i][j] = '_';
    		}
    	}
    	
    	value = 0;
    	lastMove = null;
    }
    
    public Board(char[][] currentBoard, String lastMove){
    	this.board = currentBoard;
    	this.lastMove = lastMove;
    	
//    	value = calculateValue();
    }
    
    //TODO fix evaluation function, once fixed we should be good, it evaluated higher for more X's in a row
    //therefore it will attempt to bum rush a win
    public int calculateValue(){
    	int value = 0;
    	
    	for(int row = 0; row < 8; row++){
    		for(int col = 0; col < 8; col++){
    			if(board[row][col] == 'X'){
    				int colValue = 1;
    				
    				//look down the cols
    				int tmpCol = col - 1;
    				while(tmpCol >= 0 && board[row][tmpCol] == 'X'){
    					colValue *= 10;
    					tmpCol--;
    				}
    				//look up the cols
    				tmpCol = col + 1;
    				while(tmpCol < 8 && board[row][tmpCol] == 'X'){
    					colValue *= 10;
    					tmpCol++;
    				}
    				
    				int rowValue = 1;
    				//look backward in row
    				int tmpRow = row - 1;
    				while(tmpRow >= 0 && board[tmpRow][col] == 'X'){
    					rowValue *= 10;
    					tmpRow--;
    				}
    				//look forward in row
    				tmpRow = row + 1;
    				while(tmpRow < 8 && board[tmpRow][col] == 'X'){
    					rowValue *= 10;
    					tmpRow++;
    				}
    				
    				//dont count the single x twice
//    				if(rowValue == 1) rowValue = 0;
    				
    				value += colValue + rowValue;
    			}
    			else if(board[row][col] == 'O'){
    				int colValue = -1;
    				
    				//look down the cols
    				int tmpCol = col - 1;
    				while(tmpCol >= 0 && board[row][tmpCol] == 'O'){
    					colValue *= 10;
    					tmpCol--;
    				}
    				//look up the cols
    				tmpCol = col + 1;
    				while(tmpCol < 8 && board[row][tmpCol] == 'O'){
    					colValue *= 10;
    					tmpCol++;
    				}
    				
    				int rowValue = -1;
    				//look backward in row
    				int tmpRow = row - 1;
    				while(tmpRow >= 0 && board[tmpRow][col] == 'O'){
    					rowValue *= 10;
    					tmpRow--;
    				}
    				//look forward in row
    				tmpRow = row + 1;
    				while(tmpRow < 8 && board[tmpRow][col] == 'O'){
    					rowValue *= 10;
    					tmpRow++;
    				}
    				
    				//dont count the single x twice
    				if(rowValue == -1) rowValue = 0;
    				
    				value += colValue + rowValue;
    			}
    		}
    	}
    	
    	return value;
    }
    
    public int finalState(){
    	//1 is for the computer winning, 2 is for the player winning, 0 is for no current winner, 3 for tie game
        //horizontal cases
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j] == 'X' && board[i][j + 1] == 'X' && board[i][j + 2] == 'X' && board[i][j + 3] == 'X') {
                    return 1;
                } else if (board[i][j] == 'O' && board[i][j + 1] == 'O' && board[i][j + 2] == 'O' && board[i][j + 3] == 'O') {
                    return 2;
                }
            }
        }

        //vertical cases
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 5; i++) {
                if (board[i][j] == 'X' && board[i + 1][j] == 'X' && board[i + 2][j] == 'X' && board[i + 3][j] == 'X') {
                    return 1;
                } else if (board[i][j] == 'O' && board[i + 1][j] == 'O' && board[i + 2][j] == 'O' && board[i + 3][j] == 'O') {
                    return 2;
                }
            }
        }

        //number of tiles marked
        int count = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] != '_') {
                    count++;
                }
            }
        }

        //all tiles marked here means draw, otherwise game isn't done yet
        return (count == 64) ? 3 : 0;
    }

	public void printBoard() {
		char rowChar = 'A';
		System.out.println("\n  1 2 3 4 5 6 7 8");
		for(int row = 0; row < 8; row++){
			System.out.print(rowChar++ + " ");
			for(int col = 0; col < 8; col++){
				System.out.print(board[col][row] + " ");
			}
			System.out.println();
		}
		
	}

	public ArrayList<Board> findLegalMoves(boolean computer) {
		
        
		ArrayList<Board> children = new ArrayList<Board>();
		
		char player = 'O';
		if(computer)
			player = 'X';
		
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){  
				char[][] copy = new char[8][8];
				for (int i = 0; i < 8; i++) {
		        	for (int j = 0; j < 8; j++) {
		        		copy[i][j] = this.board[i][j];
		        	}
		        }
				
                if(copy[row][col] == '_'){
                	copy[row][col] = player;
                	
                	String move = row + "" + col;
                	
                	children.add(new Board(copy, move));
                }
			}
		}
		
		return children;
	}

	@Override
	public int compare(Board board1, Board board2) {		
		int b1Val = board1.calculateValue();
		int b2Val = board2.calculateValue();
		return Integer.compare(b1Val, b2Val);
	}
	
	public String toString(){
		String str = "";
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				str = str + board[i][j] + " ";
			}
			str = str + "\n";
		}
		
		return str;
	}
}
