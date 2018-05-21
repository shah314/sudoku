
package sudoku.solver;

/**
 * A backtracking Sudoku Solver. 
 * The input file uses a '-' as a space that is to be filled.
 *
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
     
 * @author  Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class Sudoku 
{    
    public static void main(String[] args) 
    {
        try
        {
            /* Read the input file */
            int [][] grid = GridReader.readGrid();
            
            /* Solve the puzzle */
            Solver.solve(grid);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }   
}
