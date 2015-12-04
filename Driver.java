import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

//TODO fix evaluation function on the board, in order to allow the program to not bum rush a win.

public class Driver {
	//scanner for user input
	static Scanner kb = new Scanner(System.in);
	
	public static void main(String[] args) {
		//initialize hashmap for indexing rows by a character
		HashMap<Character, Integer> rows = new HashMap<Character, Integer>();
		initMap(rows);
		
		//get who will move first
		boolean moveFirst = whosFirst();
		//and set search time for computer
		AI.setSearchTime(getThinkTime());
		
		//create new board instance
		Board currentBoard = new Board(moveFirst);
		
		String playerMove;
		int row, col;
		
		//if the human moves first enter this loop
		if(!moveFirst){
			//print current board (blank here)
			currentBoard.printBoard();
			
			//get move, and parse sting to integers
			playerMove = getUserMove();
			
			row = rows.get(playerMove.charAt(0));
			col = Integer.parseInt(playerMove.substring(1)) - 1;
			
			//put move in board
			currentBoard.board[row][col] = 'O';
		}
		
		//for the first move I want my program to select one of the unoccupied middle squares since it has most options for connect 4
		Random rng = new Random();
		do{
			row = rng.nextInt(2) + 3;
			col = rng.nextInt(2) + 3;
		}while(currentBoard.board[row][col] != '_');
		currentBoard.board[row][col] = 'X';
		char rowMove = (char) ('A' + row);

		currentBoard.lastMove = "" + rowMove + (col + 1);
		
		//continue game until someone wins, or tiles run out.
		int isFinished = 0;
		while(isFinished == 0){	
			currentBoard.printBoard();
			System.out.println("Computer plays: " + currentBoard.lastMove);
			
			//same logic, get player move insert into board
			playerMove = getUserMove();
			row = rows.get(playerMove.charAt(0));
			col = Integer.parseInt(playerMove.substring(1)) - 1;
			
			//make sure position isn't already occupied
			while(isillegal(currentBoard, col, row)){
				playerMove = getUserMove();
				
				row = rows.get(playerMove.charAt(0));
				col = Integer.parseInt(playerMove.substring(1)) - 1;
			}
			currentBoard.board[row][col] = 'O';
			
			//check if the wins the game, if so end loop
			isFinished = currentBoard.finalState();		
			if(isFinished != 0){
				break;
			}
			//otherwise computer's turn
			else{
				//print current board after player move
				currentBoard.printBoard();
				
				//get the AI move, we can directly assign a new board, returned from the function get move, to the board to simulate making a move.
				currentBoard = AI.getMove(currentBoard);

				//transform integer of a row, back to a character
				rowMove = (char) ('A' + Character.getNumericValue(currentBoard.lastMove.charAt(0)));
				
				currentBoard.lastMove = "" + rowMove + (Character.getNumericValue(currentBoard.lastMove.charAt(1)) + 1);
				
				//check if that move wins the game
				isFinished = currentBoard.finalState();	
			}
		}
		
		//game is finished so print out last board and do something based on who won
		currentBoard.printBoard();
		//computer won
		if(isFinished == 1){
			System.out.println("Computer plays: " + currentBoard.lastMove);
			System.out.println("\n\nComputer wins!");
		}
		//human won
		else if(isFinished == 2)
			System.out.println("\n\nHuman wins!");
		//draw game
		else
			System.out.println("\n\nCats game.");
		
	}
	
	//get if the human or computer will move first
	private static boolean whosFirst(){
		String userInput = "";
		
		//keep getting input until input is correct format
		while(true){
			System.out.print("Would you like to go first ((y)es/)(n)o)? ");
			userInput = kb.nextLine();
			char answer = userInput.toLowerCase().charAt(0);
			
			if(answer == 'y'){
				return false;
			}
			else if(answer == 'n'){
				return true;
			}
			else{
				System.out.println("I didn't understand your input, please enter yes/y or no/n");
			}
		}
	}
	
	//get time the computer will be allowed to search for a move
	private static int getThinkTime(){
		int time = 30;
		
		//keep getting time while input does not match
		boolean flag = true;
		while(flag){
			System.out.print("How many seconds should the computer think? ");
			try{
				time = kb.nextInt();
				kb.nextLine();
				flag = false;
			}
			catch(InputMismatchException ime){
				kb.nextLine();
				System.out.println("\nError parsing string, Input a number.");
				time = 0;
			}
		}
		
		return time;
	}
	
	//get human move
	private static String getUserMove(){
		String userInput = "";
		
		//regex match the string, if it doesn't match them get input again
		while(!userInput.matches("[a-hA-H][1-8]")){
			System.out.print("\nChoose your next move: ");
			userInput = kb.nextLine();
			
			if(!userInput.matches("[a-hA-H][1-8]"))
				System.out.println("Unrecognized Input enter a valid move [a-hA-H][1-8]");
		}
		
		return userInput;
	}
	
	//check if move is legal, i.e. position isn't already occupied
	private static boolean isillegal(Board currentBoard, int col, int row){
		if(currentBoard.board[row][col] != '_'){
			System.out.println("Illegal move, that position is alread occupied.");
			return true;
		}
		else
			return false;
	}
	
	//initialize hashmap for letter indexing of rows 
	private static void initMap(HashMap<Character, Integer> map){
		int counter = 0;
		for(char i = 'a'; i <= 'h'; i++){
			map.put(i, counter++);
		}
	}

}
