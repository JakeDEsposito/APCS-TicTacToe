import java.util.Scanner;

public class TicTacToe
{
    private int turn = 2;
    private boolean computer = false;
    private Computer computerObj = null;
    private String[] players = new String[2];
    private String[][] board;
    
    private Scanner input = new Scanner(System.in);
   
    public TicTacToe(String player1, String player2)
    {
        System.out.println("Starting Game...");
        this.players[0] = player1;
        if(!player2.equals(""))
            this.players[1] = player2;
        else
        {
            computer = true;
            computerObj = new Computer(this);
            this.players[1] = Computer.generateName(player1);
        }
        board = createBoard();
    }
    
    public TicTacToe() {}
    
    public String[] getPlayers()
    {
        return players;
    }
    
    public int getTurn()
    {
        return turn % 2;
    }
    
    public void printBoard()
    {
        System.out.println();
        for(String[] row : board)
        {
            for(String col: row)
                System.out.print(col + " ");
            System.out.println();
        }
    }
    
    private String[][] createBoard()
    {
        String[][] board = {{" ", "1", "2", "3"},
                            {"1", "-", "-", "-"},
                            {"2", "-", "-", "-"},
                            {"3", "-", "-", "-"}};
        return board;
    }
    
    public String[][] getBoard()
    {
        return board;
    }
    
    public boolean pickLocation(int row, int col)
    {
        if(row > 3 || col > 3)
            return false;
        return board[row][col] == "-";
    }
    
    public void takeTurn(int row, int col)
    {
        board[row][col] = turn % 2 == 1 ? "O" : "X";
        turn++;
    }
    
    private boolean checkRow()
    {
        for(String[] row : board)
            if(row[1] == row[2] && row[2] == row[3] && row[1] != "-")
                return true;
        return false;
    }
    
    private boolean checkCol()
    {
        for(int i = 0; i < board.length; i++)
            if(board[1][i] == board[2][i] && board[2][i] == board[3][i] && board[1][i] != "-")
                return true;
        return false;
    }
    
    private boolean checkDiag()
    {
        if(board[1][1] == board[2][2] && board[2][2] == board[3][3] && board[1][1] != "-")
            return true;
        if(board[1][3] == board[2][2] && board[2][2] == board[3][1] && board[1][3] != "-")
            return true;
        return false;
    }
    
    public boolean checkWin()
    {
        return checkRow() || checkCol() || checkDiag();
    }
    
    public boolean checkBoard()
    {
        for(int x = 1; x < 4; x++) for(int y = 1; y < 4; y++)
            if(board[x][y].equals("-"))
                return false;
        return true;
    }
    
    private int stringIsInteger(String s) {
        int result;
        
        try
        {
            result = Integer.parseInt(s);
        }
        catch(NumberFormatException e)
        {
            return 4;
        }
        catch(NullPointerException e)
        {
            return 4;
        }
        
        return result;
    }
    
    public void playGame()
    {
        int row = 0;
        int col = 0;
        while(!checkWin())
        {
            if(checkBoard())
                break;
            
            if(turn % 2 == 1 && computer)
            {
                computerObj.calculate(board);
                row = computerObj.getRow();
                col = computerObj.getCol();
                System.out.println("\n" + getPlayers()[getTurn()] + " made there move");
            }
            else if(turn % 2 == 0 || turn % 2 == 1)
            {
                System.out.println("Pick the row " + getPlayers()[getTurn()]);
                row = stringIsInteger(input.nextLine());
                System.out.println("Pick the column " + getPlayers()[getTurn()]);
                col = stringIsInteger(input.nextLine());
            }
            if(pickLocation(row, col))
                takeTurn(row, col);
            else
                System.out.println("That move is not available. Please try again.");
            
            printBoard();
        }
        if(!checkBoard())
        {
            turn++;
            System.out.println(getPlayers()[getTurn()] + " wins!");
        }
        else
            System.out.println("Tie!");
    }
}

class Computer
{
    private TicTacToe game;
    private int row = 0, col = 0;
    private static boolean DEBUG = false;
    private String[][] board;
    
    public Computer(TicTacToe game)
    {
        this.game = game;
        this.board = game.getBoard();
    }
    
    public static String generateName(String playerName)
    {
        //if(playerName.equals("")) return "";
        if(playerName.equals("DEBUG"))
        {
            DEBUG = true;
            return "DB-COMPUTER";
        }
        
        if(playerName.equals("GLaDOS")) return "Hal 9000";
        if(playerName.equals("Shrek")) return "Donkey";
        if(playerName.equals("Gabe Newell")) return "Three";
        if(playerName.equals("Gordon Freeman")) return "G-Man";
        if(playerName.equals("Steve Jobs")) return "Bill Gates";
        if(playerName.equals("TAE86")) return "DRIFT";
        if(playerName.equals("Michael De Santa")) return "Trevor Philips";
        if(playerName.equals("Carl Johnson")) return "Big Smoke";
        if(playerName.equals("Java")) return "C++";
        return "Computer";
    }
    
    public void calculate(String[][] board)
    {
        this.board = board;
        
        if(moveRow("O") || moveCol("O") || moveDiag("O")){}
        else if(moveRow("X") || moveCol("X") || moveDiag("X")){}
        else if(moveAgr() || moveAgr(2, 2) || moveAgr(1, 1) || moveAgr(1, 3) || moveAgr(3, 1) || moveAgr(3, 3)){}
        else moveRandom();
    }
    
    private boolean moveAgr()
    {
        int count = 0;
        for(String[] i : board)
            for(String j : i)
                if(j.equals("-"))
                    count++;
        
        if(count <= 6) 
        {
            if(game.pickLocation(2, 1)) 
            {
                this.row = 2;
                this.col = 1;
            }
            else if(game.pickLocation(1, 2))
            {
                this.row = 1;
                this.col = 2;
            }
            else if(game.pickLocation(2, 3))
            {
                this.row = 2;
                this.col = 3;
            }
            else if(game.pickLocation(3, 2))
            {
                this.row = 3;
                this.col = 2;
            }
            
            if(DEBUG) DEBUG_RUN(this.row + " " + this.col);
            
            return true;
        }
        
        return false;
    }
    
    private boolean moveAgr(int row, int col)
    {
        if(game.pickLocation(row, col))
        {
            this.row = row;
            this.col = col;
            
            if(DEBUG) DEBUG_RUN(row + " " + col);
            
            return true;
        }
        return false;
    }
    
    private boolean moveRow(String check)
    {
        for(int row = 1; row < 4; row++)
        {
            String[] moves = board[row];
            int count = 0;
            for(int col = 1; col < 4; col++)
            {
                if(board[row][col].equals(check))
                    count++;
            }
            if(count > 1)
            {
                for(int mov = 1; mov < 4; mov++)
                {
                    if(moves[mov].equals("-"))
                    {
                        this.row = row;
                        this.col = mov;
                        
                        if(DEBUG) DEBUG_RUN(check);
                        
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean moveCol(String check)
    {
        for(int row = 1; row < 4; row++)
        {
            String[] moves = {"", board[1][row], board[2][row], board[3][row]};
            int count = 0;
            for(int col = 1; col < 4; col++)
            {
                if(board[col][row].equals(check))
                    count++;
            }
            if(count > 1)
            {
                for(int mov = 1; mov < 4; mov++)
                {
                    if(moves[mov].equals("-"))
                    {
                        this.row = mov;
                        this.col = row;
                        
                        if(DEBUG) DEBUG_RUN(check);
                        
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean moveDiag(String check)
    {
        int count;
        
        for(int i = 0; i <= 4; i += 4)
        {
            count = 0;
            for(int j = 1; j < 4; j++)
                if(board[j][Math.abs(j - i)].equals(check))
                    count++;
            if(count > 1)
            {
                this.row = 0;
                
                for(int j = 1; j < 4; j++)
                    if(board[j][Math.abs(j - i)].equals("-"))
                        this.row = j;
                
                if(this.row != 0)
                { 
                    this.col = Math.abs(this.row - i);
                    
                    if(DEBUG) DEBUG_RUN(check);
                    
                    return true;
                }
            }
            
        }
        return false;
    }
    
    private void moveRandom()
    {
        while(true)
        {
            row = (int)(Math.random() * 3) + 1;
            col = (int)(Math.random() * 3) + 1;
            
            if(game.pickLocation(row, col))
            {
                if(DEBUG) DEBUG_RUN("nothing");
                break;
            }
        }
    }
    
    private void DEBUG_RUN(String check)
    {
        System.out.println(new Throwable().getStackTrace()[1].getMethodName() + " checked " + check + " and made move " + this.row + " " + this.col);
    }
    
    public int getRow()
    {
        return row;
    }
    
    public int getCol()
    {
        return col;
    }
    
}
