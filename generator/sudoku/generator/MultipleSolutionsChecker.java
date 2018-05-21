
package sudoku.generator;
import java.util.*;

/**
 * Check for multiple solutions to the puzzle.
 * @author Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class MultipleSolutionsChecker 
{   
    /**
     * Check for multiple solutions to a puzzle.
     */
    public static int [] checkMultipleSolutions(int [][] gr) throws Exception
    {  
        /* Reference: http://www.setbb.com/phpbb/viewtopic.php?t=166&view=next&mforum=sudoku */ 
        Grid grid1 = Filler.fillGrid(gr);
        Grid grid2 = Filler.fillGrid(gr);
        
        /* Fill the grid with possible values random */
        Filler.fillEmptySpaces(grid1);
        /* Fill the grid with possible values reversed.
         * This will make the algorithm choose possible values in
         * reverse order */
        Filler.fillReverseEmptySpaces(grid2);
        
        grid1 = Solver.solve(grid1);
        grid2 = Solver.solve(grid2);
        
        return checkMismatch(grid1, grid2);
    }
    
    /**
     * Check for multiple solutions and return a randomly chosen value
     * which is the cause of multiple solutions.
     */
    public static int[] checkMismatch(Grid grid1, Grid grid2)
    {
        int [][] sg1 = grid1.getSudokuGrid();
        int [][] sg2 = grid2.getSudokuGrid();
        List list = new ArrayList();
        for(int i=0; i<Constants.SIZE; i++)
        {
            for(int j=0; j<Constants.SIZE; j++)
            {
                if(sg1[i][j] != sg2[i][j])
                {
                    list.add(new int[]{i, j, sg1[i][j]});
                    list.add(new int[]{i, j, sg2[i][j]});
                }
            }
        }
        
        if(list.isEmpty())
            return null;
        
        int rand = (int)(Math.random()*list.size());
        return (int [])list.get(rand);
    }
}
