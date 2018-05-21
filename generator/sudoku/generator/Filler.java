
package sudoku.generator;

import java.util.*;

/**
 * Fills the grid and creates the doubly linked list.
 * @author  Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class Filler 
{   
    /**
     * Fill the grid with the values of gr[][]. 
     */
    public static Grid fillGrid(int [][] gr) throws Exception
    {
        Grid grid = new Grid();
        for(int i=0; i<Constants.SIZE; i++)
        {
            for(int j=0; j<Constants.SIZE; j++)
            {
                if(gr[i][j] != Constants.EMPTY)
                {
                    // the space cannot be modified
                    grid.addToGrid(i, j, gr[i][j], true);
                }
            }
        }
        
        return grid;
    }
    
    /** 
     * Fill the empty spaces of the grid with objects 
     * populated with the possible values that this space can 
     * be filled with.
     */
    public static void fillEmptySpaces(Grid grid)
    {
        for(int i=0; i<Constants.SIZE; i++)
        {
            for(int j=0; j<Constants.SIZE; j++)
            {
                if(grid.isEmptySpace(i, j))
                {
                    GridSpace space = new GridSpace(i, j);
                    List pv = grid.possibleValues(i, j);
                    space.setModifiable(true);
                    space.setPossibleValues(pv);
                    grid.addToSpaces(space);
                }
            }
        }
    }
    
    /** 
     * Fill the empty spaces of the grid with objects 
     * populated with the possible values that this space can 
     * be filled with.
     */
    public static void fillReverseEmptySpaces(Grid grid)
    {
        for(int i=0; i<Constants.SIZE; i++)
        {
            for(int j=0; j<Constants.SIZE; j++)
            {
                if(grid.isEmptySpace(i, j))
                {
                    GridSpace space = new GridSpace(i, j);
                    List pv = grid.possibleValues(i, j);
                    space.setModifiable(true);
                    Collections.reverse(pv);
                    space.setPossibleValues(pv);
                    grid.addToSpaces(space);
                }
            }
        }
    }
    
    /**
     * Create the doubly linked list for backtracking.
     */
    public static PNList createPNList(Grid grid)
    {
        PNList list = null;
        List spaces = grid.getSpacesList();
        Collections.sort(spaces);
        for(Iterator it=spaces.iterator(); it.hasNext();)
        {
            GridSpace s = (GridSpace)it.next();
            if(list == null)
            {
                list = new PNList(s);
                continue;
            }
            
            list.addNext(s);
            if(!it.hasNext())
            {
                list.setLast(s);
                s.setLast(true);
            }
        }
        list.reset();
        return list;
    }
}
