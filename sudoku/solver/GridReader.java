
package sudoku.solver;

import java.io.*;

/**
 * Read the input Sudoku Grid from the file specified in Constants
 * @author  Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class GridReader {
    
    /** Constructor */
    public GridReader() 
    {
    }
    
    /**
     * Read the input Sudoku Grid from the file specified in Constants.
     */
    public static int [][] readGrid() throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(Constants.FILE_PATH)));
        int [][] grid = new int[Constants.SIZE][Constants.SIZE];
        
        for(int i=0; i<Constants.SIZE; i++)
        {
            String s = reader.readLine();
            for(int j=0; j<Constants.SIZE; j++)
            {
                char a = s.charAt(j);
                if(a == Constants.FILE_EMPTY)
                {
                    grid[i][j] = Constants.EMPTY;
                }
                else
                {
                    if(Character.isDigit(a))
                    {
                        int n = Integer.parseInt("" + a);
                        if(n > Constants.SIZE)
                        {
                            // a number greater than Constants.SIZE
                            throw new RuntimeException("Invalid Character " + a + " In Input File " + Constants.FILE_PATH);
                        }
                        grid[i][j] = n-1;
                    }
                    else
                    {
                        // The character is neither a digit nor a '-'
                        throw new RuntimeException("Invalid Character " + a + " In Input File " + Constants.FILE_PATH);
                    }
                }
            }
        }
        
        return grid;
    }
}
