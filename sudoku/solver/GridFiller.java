
package sudoku.solver;

import java.util.*;

/**
 * Fills the grid
 * @author  Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class GridFiller 
{
    /** Constructor */
    public GridFiller() 
    {
    }
    
    /**
     * Fill the grid with the values of gr[][]. 
     * gr[][] is created by the GridReader.
     */
    public static Grid fillGrid(int [][] gr) throws Exception
    {
        Grid grid = new Grid();
        
        /* First put all non-modifiable items in the grid */
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
                    LinkedHashSet pv = grid.possibleValues(i, j);
                    space.setModifiable(true);
                    space.setPossibleValues(pv);
                    if(pv.size() == 1)
                    {
                       grid.addToGrid(i, j, ((Integer)pv.iterator().next()).intValue(), true);
                    }
                    else
                    {
                        grid.addToSpaces(space);
                    }
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
            }
        }
        list.reset();
        return list;
    }
}
