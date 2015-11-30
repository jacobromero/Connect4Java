import java.util.ArrayList;

//TODO nothing I think. this class maybe ok.
public class Board {
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
    	board = currentBoard;
    	this.lastMove = lastMove;
    	
//    	value = calculateValue();
    }
    
    public int calculateValue(){
    	int value = 0;
    	for(int row = 0; row < 8; row++){
    		for(int col = 0; col < 8; col++){
    			if(board[col][row] == 'X'){
    				//evaluate the column that has an X in it
    				int tmpvalue = 1;
    				int tmp = col - 1;
    				while(tmp >= 0 && board[tmp][row] == 'X'){
    					tmpvalue *= 10;
    					tmp--;
    				}
    				tmp = col + 1;
    				while(tmp < 8 && board[tmp][row] == 'X'){
    					tmpvalue *= 10;
    					tmp++;
    				}
    				value += tmpvalue;
    				
    				//evaluate the row that has an X in it
    				tmpvalue = 1;
    				tmp = row - 1;
    				while(tmp >= 0 && board[col][tmp] == 'X'){
    					tmpvalue *= 10;
    					tmp--;
    				}
    				tmp = col + 1;
    				while(tmp < 8 && board[col][tmp] == 'X'){
    					tmpvalue *= 10;
    					tmp++;
    				}
    				value += tmpvalue;
    			}
    			else if(board[col][row] == 'O'){
    				//evaluate the column that has an X in it
    				int tmpvalue = -1;
    				int tmp = col - 1;
    				while(tmp >= 0 && board[tmp][row] == 'O'){
    					tmpvalue *= 10;
    					tmp--;
    				}
    				tmp = col + 1;
    				while(tmp < 8 && board[tmp][row] == 'O'){
    					tmpvalue *= 10;
    					tmp++;
    				}
    				value += tmpvalue;
    				
    				//evaluate the row that has an X in it
    				tmpvalue = -1;
    				tmp = row - 1;
    				while(tmp >= 0 && board[col][tmp] == 'O'){
    					tmpvalue *= 10;
    					tmp--;
    				}
    				tmp = col + 1;
    				while(tmp < 8 && board[col][tmp] == 'O'){
    					tmpvalue *= 10;
    				}
    				value += tmpvalue;
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
                		copy[j][i] = board[j][i];
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
}
