
package sudoku.solver;

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
     * by depth first backtracking and report all possible solutions.
     */
    public static void solve(int [][] gr) throws Exception
    {
        /* Create a grid object */
        Grid grid = GridFiller.fillGrid(gr);
        
        /* Fill the empty spaces with GridSpace objects */
        GridFiller.fillEmptySpaces(grid);
        
        /* Sort the list of GridSpace objects */
        grid.sortSpaces();        
        
        /* Create a double linked list for backtracking */
        PNList list = GridFiller.createPNList(grid);
        
        /* Start filling from the head of this sorted list */
        GridSpace current = list.getHead();
        int solutions = 0;
        while(true)
        {
            if(current == list.getLast())
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
                    grid.resetGridSpace(current.getRow(), current.getColumn());
                    current.reset();
                    current = current.previous();
                    Constants.printGrid(grid.getSudokuGrid());
                    solutions++;
                    if(solutions > Constants.MAX_SOLUTIONS)
                    {
                        System.out.println("\n...stopping after 100 solutions.\n(This behavior can be changed in Constants.java).");
                        break;
                    }
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
                             break;
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
        if(solutions == 0)
        {
            System.out.println("No Solutions exist for the input puzzle.");
        }
    }
}