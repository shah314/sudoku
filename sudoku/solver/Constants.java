
package sudoku.solver;

/**
 * Constants
 * @author  Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class Constants 
{
    /* The sudoku file The format uses '-' as an empty space.
     * Sample:
     
        -9-67--4-
        -----8---
        4-3-1----
        --9-2-8--
        3-6---9-2
        --2-3-1--
        ----6-7-9
        ---4-----
        -1--82-3-
     
     */
    
    public static final String FILE_PATH = "D:\\javacode\\sudoku\\puzzle instances\\current\\sudoku.txt";
    /* An empty grid cell is represented by this character in the file */
    public static final char FILE_EMPTY = '-';
    /* An empty cell is represented by this value */
    public static final int EMPTY = -1;
    /* Size of a row and a column. Change this for larger puzzles */
    public static int SIZE = 9;
    /* The size of a subgrid */
    public static final int SUBGRID_SIZE = (int)Math.sqrt(SIZE);
    /* Maximum number of solutions to be printed out if the puzzle has more than one */
    public static final int MAX_SOLUTIONS = 100;
    
    /**
     * Print the grid to the standard output
     */
    public static void printGrid(int [][] array)
    {
        System.out.println("--------------------");
        for(int i=0; i<Constants.SIZE; i++)
        {
            for(int j=0; j<Constants.SIZE; j++)
            {
                System.out.print((array[i][j]+1) + " ");
            }
            System.out.println("");
        }
        System.out.println("--------------------");
    }
    
    public static int [][]copyArray(int [][] arr)
    {
        int [][] array = new int[Constants.SIZE][Constants.SIZE];
        for(int i=0; i<Constants.SIZE; i++)
        {
            for(int j=0; j<Constants.SIZE; j++)
            {
                array[i][j] = arr[i][j];
            }
        }
        
        return array;
    }
}
