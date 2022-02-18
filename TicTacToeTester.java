import java.util.Scanner;

public class TicTacToeTester
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        
        String p1 = "", p2 = "";
        System.out.println("Enter your name Player One:");
        p1 = input.nextLine();
        System.out.println("Enter your name Player Two: (Enter nothing to play against the computer)");
        p2 = input.nextLine();
        
        while(true)
        {
            TicTacToe game = new TicTacToe(p1, p2);
            System.out.println("Initial Game Board:");
            game.printBoard();
            
            game.playGame();
            
            System.out.println("Want to play again? Press enter if so.");
            if(!input.nextLine().equalsIgnoreCase(""))
            {
                System.out.println("Thanks for playing!");
                break;
            }
        }
    }
}
