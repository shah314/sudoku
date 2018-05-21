
package sudoku.generator;

import java.io.*;
import java.util.*;

/**
 * A backtracking Sudoku Solver.
 * @author  Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class Solver
{       
    /** 
     * Solve the Sudoku grid represented by this two dimensional array gr[][]
     */
    public static Grid solve(Grid grid) throws Exception
    {   
        /* Create a double linked list for backtracking */
        PNList list = Filler.createPNList(grid);
        /* Start filling from the head of this sorted list */
        GridSpace current = list.getHead();
        grid.sortSpaces();
        while(true)
        {
            if(current.isLast())
            {
                int nextValue = current.nextValue();
                while(nextValue != Constants.EMPTY && !grid.isValidNextValue(current.getRow(), current.getColumn(), nextValue))
                {
                    nextValue = current.nextValue();
                }
                
                if(nextValue == Constants.EMPTY)
                {
                    grid.resetGridSpace(current.getRow(), current.getColumn());
                    current.reset();
                    current = current.previous();
                }
                else
                {
                    /* Solved */
                    grid.replaceGridSpace(current.getRow(), current.getColumn(), nextValue);
                    break;
                }
            }
            else
            {
                int nextValue = current.nextValue();
                while(nextValue != Constants.EMPTY && !grid.isValidNextValue(current.getRow(), current.getColumn(), nextValue))
                {
                    nextValue = current.nextValue();
                }
                
                if(nextValue == Constants.EMPTY)
                {
                    grid.resetGridSpace(current.getRow(), current.getColumn());
                    current.reset();
                    current = current.previous();
                    if(current == null)
                    {
                        return null;
                    }
                }
                else
                {
                    grid.replaceGridSpace(current.getRow(), current.getColumn(), nextValue);
                    current = current.next();
                }
            }
        }
        
        /* Solved */
        return grid;
    }
}