
package sudoku.generator;

import java.util.*;
import java.io.*;

/**
 * The generator generates random Sudoku puzzles.
 * Puzzles are written to the standard output in the following format:
 *
    -2-----89
    4--78----
    ---1-3--6
    -1--65--7
    3-5-9-2--
    -8-214---
    53---2---
    -429--5-1
    7-8---64-
 *
 * Each generated puzzle has a unique solution.
 * @author Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class Sudoku 
{   
    /** Creates a new instance of Tester */
    public Sudoku() {
    }
    
    public static void main(String [] args) throws Exception
    {
        generator();
    }
    
    /* Generate a random sudoku puzzle */
    public static void generator() throws Exception
    {
        int [][] grid = null;
        while(true)
        {
            grid = createGrid();
            int count = findGridWithUniqueSolution(grid);
            count = improveUponGeneratedGrid(grid, count);
            if(count <= Constants.MAX_DISPLAYED_NUMBERS)
            {
                break;
            }
        }
        
        printGrid(grid);
    }
    
    /**
     * Remove numbers from the generated grid till the value of count
     * is less than equal to Constants.MAX_DISPLAYED_NUMBERS.
     */
    public static int improveUponGeneratedGrid(int [][] grid, int count) throws Exception
    {
        /*
         * Remove numbers randomly from the grid till
         * the grid contains less than equal to 
         * Constants.MAX_DISPLAYED_NUMBERS
         */
        long start = System.currentTimeMillis();
        Map map = new HashMap();
        while(true)
        {
            long end = System.currentTimeMillis();
            long diff = end - start;
            if(diff > Constants.MAX_TIME)
            {
                return count;
            }
            
            if(count <= Constants.MAX_DISPLAYED_NUMBERS)
            {
                break;
            }
            
            int row = (int)(Math.random()*grid.length);
            int column = (int)(Math.random()*grid.length);
            
            if(map.containsKey("" + row + " " + column))
            {
                /* row and column have already been checked */
                continue;
            }
            
            if(grid[row][column] != Constants.EMPTY)
            {
                int v = grid[row][column];
                grid[row][column] = Constants.EMPTY;
                count--;
                int [] result = MultipleSolutionsChecker.checkMultipleSolutions(grid);
                if(result != null)
                {
                    /* This number cannot be removed */
                    grid[row][column] = v;
                    map.put("" + row + " " + column, "");
                    count++;
                }
            }
        }
        
        return count;
    }
    
    
    /**
     * Find a grid that has a unique solution.
     */
    public static int findGridWithUniqueSolution(int [][] grid) throws Exception
    {
        /*
         * Find a grid that has a unique solution
         */
        int count = Constants.SIZE*Constants.SIZE;
        removeNumbers(grid);
        count-=Constants.REMOVE_FREQ;
        while(true)
        {   
            int [] result = MultipleSolutionsChecker.checkMultipleSolutions(grid);
                    
            if(result != null)
            {
                int row = result[0];
                int column = result[1];
                int value = result[2];
                grid[row][column] = value;
                count+=1;
            }
            else
            {
                break;
            }
        }
        
        return count;
    }
    
    
    /**
     * Remove numbers randomly from the completely filled grid.
     */
    public static void removeNumbers(int [][] grid)
    {
        int count = 0;
        while(true)
        {
            if(count >= Constants.REMOVE_FREQ)
            {
                return;
            }
            
            int row = (int)(Math.random()*grid.length);
            int column = (int)(Math.random()*grid.length);
            
            if(grid[row][column] != Constants.EMPTY)
            {
                grid[row][column] = Constants.EMPTY;
                count++;
            }
        }
    }
    
    /**
     * Create a grid - a solution to an empty puzzle.
     */
    public static int [][] createGrid() throws Exception
    {
        int [][] grid = new int[Constants.SIZE][Constants.SIZE];
        for(int i=0; i<grid.length; i++)
        {
            for(int j=0; j<grid.length; j++)
            {
                grid[i][j] = Constants.EMPTY;
            }
        }
        
        /* 
         * Fill the top row randomly.
         * This ensures complete randomness of the generated puzzles
         */
        int i=0; 
        int j=0;
        int count = 0;
        Map map = new HashMap();
        while(true)
        {
            if(count >= Constants.SIZE)
                break;
            
            int rand = (int)(Math.random()*Constants.SIZE);
            if(!map.containsKey("" + rand))
            {
                grid[i][j] = rand;
                j++;
                map.put("" + rand, "");
                count++;
            }
        }
        
        /* Solve the puzzle and return the grid */
        Grid gr = Filler.fillGrid(grid);
        Filler.fillEmptySpaces(gr);
        gr = Solver.solve(gr);
        return gr.getSudokuGrid();
    }
    
    /**
     * Print the generated puzzle to the standard output.
     */
    public static void printGrid(int [][] grid)
    {
        for(int i=0; i<grid.length; i++)
        {
            for(int j=0; j<grid.length; j++)
            {
                if(grid[i][j] == Constants.EMPTY)
                {
                    System.out.print("-");
                }
                else
                {
                    int v = grid[i][j] + 1;
                    System.out.print(v);
                }
            }
            
            System.out.println("");
        }
    }
}
