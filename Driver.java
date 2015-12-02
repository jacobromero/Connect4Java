import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

//TODO implement iterative deepening search on alpha-beta pruning
//TODO implement the time limit on alpha-beta pruning
//TODO change/fix evaluation function on the board, in order to allow the program to not bum rush a win.
public class Driver {
	static Scanner kb = new Scanner(System.in);
	public static void main(String[] args) {
		Board currentBoard = new Board();
//		char[][] c = {{'X', '_', '_', '_', '_', '_', '_', '_'},
//						{'_', '_', '_', '_', '_', '_', '_', '_'},
//						{'_', 'O', 'O', 'O', 'O', '_', '_', '_'},
//						{'_', '_', '_', '_', '_', '_', '_', '_'},
//						{'_', '_', '_', '_', '_', '_', '_', '_'},
//						{'_', '_', '_', '_', '_', '_', '_', '_'},
//						{'_', '_', '_', '_', '_', '_', '_', '_'},
//						{'_', '_', '_', '_', '_', '_', '_', '_'}};
//		
//		currentBoard.board = c;
//		
//		ArrayList<Board> b = currentBoard.findLegalMoves(true);
//		
//		for(Board ba : b){
//			System.out.println(ba);
//			ba.value = ba.calculateValue();
//			System.out.println(ba.value);
//		}
		
		
//		System.out.println(currentBoard.calculateValue());
		
		HashMap<Character, Integer> rows = new HashMap<Character, Integer>();
		initMap(rows);
		
		boolean moveFirst = whosFirst();
		AI.setSearchTime(getThinkTime());
		
		char player;		
		String playerMove;
		int row, col;
		
		
		if(!moveFirst){
			currentBoard.printBoard();
			player = 'O';
			playerMove = getUserMove();
			row = rows.get(playerMove.charAt(0));
			col = Integer.parseInt(playerMove.substring(1)) - 1;
			
			currentBoard.board[col][row] = player;
			currentBoard.value = currentBoard.calculateValue();
			currentBoard.lastMove = row + "" + col;
		}
		
		player = 'X';
		Random rng = new Random();
		do{
			row = rng.nextInt(2) + 3;
			col = rng.nextInt(2) + 3;
		}while(currentBoard.board[row][col] != '_');
		currentBoard.board[row][col] = player;
//		currentBoard.value = currentBoard.calculateValue();
		
		player = 'O';
		
		int isFinished = currentBoard.finalState();	
		while(isFinished == 0){
			currentBoard.printBoard();
			
			playerMove = getUserMove();
			
			row = rows.get(playerMove.charAt(0));
			col = Integer.parseInt(playerMove.substring(1)) - 1;
			
			while(isillegal(currentBoard, col, row)){
				playerMove = getUserMove();
				
				row = rows.get(playerMove.charAt(0));
				col = Integer.parseInt(playerMove.substring(1)) - 1;
			}
			currentBoard.board[col][row] = player;
			
			isFinished = currentBoard.finalState();	
			
			if(isFinished != 0){
				break;
			}
			else{
				currentBoard = AI.getMove(currentBoard);
				isFinished = currentBoard.finalState();	
			}
		}
		
		currentBoard.printBoard();
		if(isFinished == 1)
			System.out.println("\n\nComputer wins!");
		else if(isFinished == 2)
			System.out.println("\n\nHuman wins!");
		else
			System.out.println("\n\nCats game.");
		
	}
	
	private static boolean whosFirst(){
		String userInput = "";
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
	
	private static int getThinkTime(){
		int time = 30;
		
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
	
	private static String getUserMove(){
		String userInput = "";
		while(!userInput.matches("[a-hA-H][1-8]")){
			System.out.print("\nChoose your next move: ");
			userInput = kb.nextLine();
			
			if(!userInput.matches("[a-hA-H][1-8]"))
				System.out.println("Unrecognized Input enter a valid move [a-hA-H][1-8]");
		}
		
		return userInput;
	}
	
	private static boolean isillegal(Board currentBoard, int col, int row){
		if(currentBoard.board[row][col] != '_'){
			System.out.println("Illegal move, that position is alread occupied.");
			return true;
		}
		else
			return false;
	}
	
	private static void initMap(HashMap<Character, Integer> map){
		int counter = 0;
		for(char i = 'a'; i <= 'h'; i++){
			map.put(i, counter++);
		}
	}

}
