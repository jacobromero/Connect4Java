import java.util.ArrayList;
import java.util.Comparator;

public class Board implements Comparator<Board>{
	public char[][] board = new char[8][8];
    public int value;
    public static boolean humanFirst = false;
    
    //store last move, mainly for print computer's last move
    public String lastMove;
    
    //initalize board to _ character = open space
    public Board(){
    	for(int i = 0; i < 8; i++){
    		for(int j = 0; j < 8; j++){
    			board[i][j] = '_';
    		}
    	}
    	
    	value = 0;
    	lastMove = null;
    }
    
    public Board(boolean first){
    	humanFirst = !first;
    	
    	for(int i = 0; i < 8; i++){
    		for(int j = 0; j < 8; j++){
    			board[i][j] = '_';
    		}
    	}
    	
    	value = 0;
    	lastMove = null;
    }
    
    //can create new boards with a 2d array, and the move used to create that board
    public Board(char[][] currentBoard, String lastMove){
    	this.board = currentBoard;
    	this.lastMove = lastMove;
    }
    
    //evaluation function of a board (see report on rationale/explanation of function)
    public int calculateValue(){
    	int value = 0;
    	
    	//go through board looking for special tokens
    	for(int row = 0; row < 8; row++){
    		for(int col = 0; col < 8; col++){
    			//we see X, so find all/if any other X's are connected to it
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
    				if(rowValue == 1) rowValue = 0;
    				
    				//add value of rows and columns
    				value += colValue + rowValue;
    			}
    			//same but for Human player
    			else if(board[row][col] == 'O'){
    				int colValue = -1;
    				
    				//look down the cols
    				int tmpCol = col - 1;
    				while(tmpCol >= 0 && board[row][tmpCol] == 'O'){
    					colValue *= 10;
    					tmpCol--;
    				}
    				
    				if(humanFirst && tmpCol >= 0 && board[row][tmpCol] == 'X')
    					value += 25;
    				
    				//look up the cols
    				tmpCol = col + 1;
    				while(tmpCol < 8 && board[row][tmpCol] == 'O'){
    					colValue *= 10;
    					tmpCol++;
    				}
    				
    				if(humanFirst && tmpCol < 8 && board[row][tmpCol] == 'X')
    					value += 25;
    				
    				int rowValue = -1;
    				//look backward in row
    				int tmpRow = row - 1;
    				while(tmpRow >= 0 && board[tmpRow][col] == 'O'){
    					rowValue *= 10;
    					tmpRow--;
    				}
    				
    				if(humanFirst && tmpRow >= 0 && board[tmpRow][col] == 'X')
    					value += 25;
    				
    				//look forward in row
    				tmpRow = row + 1;
    				while(tmpRow < 8 && board[tmpRow][col] == 'O'){
    					rowValue *= 10;
    					tmpRow++;
    				}
    				
    				//dont count the single o twice
    				if(rowValue == -1) rowValue = 0;
    				
    				if(humanFirst && tmpRow < 8 && board[tmpRow][col] == 'X')
    					value += 25;
    				
    				value += colValue + rowValue;
    			}
    		}
    	}
    	
    	return value;
    }
    
    //check if the board is a win/loss board
    public int finalState(){
    	//special case, if the board is full, only need to look for 1 space that is open
    	int openSpaces = 0;
    	
    	//1 is for the computer winning, 2 is for the player winning, 0 is for no current winner, 3 for tie game
    	for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
            	int counter = 0;
            	//check computer 4 in a row
            	if(board[row][col] == 'X'){
	            	int tmpMover = col + 1;
	            	counter = 1;
	            	
	            	//check horizontal movement boxes for a connection of 4
	            	while(tmpMover < 8 && board[row][tmpMover++] == 'X'){
						counter++;
						
						if(counter == 4)
							return 1;
					}
	            	
	            	tmpMover = row + 1;
	            	counter = 1;
	            	
	            	//check vertical movements for connection of 4
	            	while(tmpMover < 8 && board[tmpMover++][col] == 'X'){
						counter++;
						
						if(counter == 4)
							return 1;
					}
            	}
            	//check for human player 4 in a row
            	else if(board[row][col] == 'O'){
            		int tmpMover = col + 1;
	            	counter = 1;
	            	
	            	//check horizontal movement boxes for a connection of 4
	            	while(tmpMover < 8 && board[row][tmpMover++] == 'O'){
						counter++;
						
						if(counter == 4)
							return 2;
					}
	            	
	            	tmpMover = row + 1;
	            	counter = 1;
	            	
	            	//check vertical movements for connection of 4
	            	while(tmpMover < 8 && board[tmpMover++][col] == 'O'){
						counter++;
						
						if(counter == 4)
							return 2;
					}
            	}
            	//space is open if it isn't an X or O
            	else{
            		openSpaces++;
            	}
            }
    	}
    	
    	//no open spaces = cat's game
    	if(openSpaces == 0){
    		return 3;
    	}
    	
    	//board is still playable
    	return 0;
    }
    
    //print board, with row/col numbers.
	public void printBoard() {
		char rowChar = 'A';
		System.out.println("\n  1 2 3 4 5 6 7 8");
		for(int row = 0; row < 8; row++){
			System.out.print(rowChar++ + " ");
			for(int col = 0; col < 8; col++){
				System.out.print(board[row][col] + " ");
			}
			System.out.println();
		}
		
	}

	//return all legal moves for the current board, indicating whether the computer is player, or human
	public ArrayList<Board> findLegalMoves(boolean computer) { 
		ArrayList<Board> children = new ArrayList<Board>();
		
		char player = 'O';
		if(computer)
			player = 'X';
		
		//go through board, and make every possible move
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){  
				
				//copy current board, as to not mess it up
				char[][] copy = new char[8][8];
				for (int i = 0; i < 8; i++) {
		        	for (int j = 0; j < 8; j++) {
		        		copy[i][j] = this.board[i][j];
		        	}
		        }
				
                if(copy[row][col] == '_'){
                	copy[row][col] = player;
                	
                	String move =  row + "" + col;
                	
                	//add new calculated move to list of all other moves
                	children.add(new Board(copy, move));
                }
			}
		}
		
		//return all possible moves
		return children;
	}

	@Override
	//implemented method to use collections.max/min
	public int compare(Board board1, Board board2) {		
		int b1Val = board1.calculateValue();
		int b2Val = board2.calculateValue();
		return Integer.compare(b1Val, b2Val);
	}
	
	//string representation of the board.
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
