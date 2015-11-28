import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {

	static Scanner kb = new Scanner(System.in);
	public static void main(String[] args) {
		boolean moveFirst = whosFirst();
		int timeTotThink = getThinkTime();
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

}
