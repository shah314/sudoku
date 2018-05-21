
package sudoku.generator;

import java.util.*;

/**
 * The Sudoku grid.
 * @author  Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class Grid 
{
    public int[][] rows = new int[Constants.SIZE][Constants.SIZE];
    public int[][] columns = new int[Constants.SIZE][Constants.SIZE];
    public int[][] subgrids = new int[Constants.SUBGRID_SIZE*Constants.SUBGRID_SIZE][Constants.SIZE];
    
    public int[][] origRows = new int[Constants.SIZE][Constants.SIZE];
    public int[][] origColumns = new int[Constants.SIZE][Constants.SIZE];
    public int[][] origSubgrids = new int[Constants.SUBGRID_SIZE*Constants.SUBGRID_SIZE][Constants.SIZE];
    
    public int[][] sudokugrid = new int[Constants.SIZE][Constants.SIZE];
    
    public List spaces = new ArrayList();
    
    public List [] subgridSpaces = new List[Constants.SIZE];
    public List [] rowSpaces = new List[Constants.SIZE];
    public List [] columnSpaces = new List[Constants.SIZE];
    
    /** Constructor */
    public Grid()
    {
        for(int i=0; i<Constants.SIZE; i++)
        {
            rowSpaces[i] = new ArrayList();
            columnSpaces[i] = new ArrayList();
            subgridSpaces[i] = new ArrayList();
            for(int j=0; j<Constants.SIZE; j++)
            {
                sudokugrid[i][j]=Constants.EMPTY;
            }
        }
    }
    
    /** 
     * Sort the spaces list such that the space with the lesser possible choices
     * of values comes before the one that has more possible choices.
     */
    public void sortSpaces()
    {
        Collections.sort(spaces);
    }
    
    public void reverseSpaces()
    {
        Collections.reverse(spaces);
    }
    
    /** Get the spaces list for use in the backtracking algorithm */
    public List getSpacesList()
    {
        return spaces;
    }
    
    /** Add to the spaces list */
    public void addToSpaces(GridSpace space)
    {
        spaces.add(space);
    }
    
    /** Reset the Grid space - used in backtracking */
    public void resetGridSpace(int r, int c)
    {
        int v = sudokugrid[r][c];
        int s = identifySubgrid(r, c);
        if(v == Constants.EMPTY)
        {
            return;
        }
        rows[r][v]=0;
        columns[c][v]=0;
        subgrids[s][v]=0;
        sudokugrid[r][c]=Constants.EMPTY;
    }
    
    /** Get the sudoku grid of values */
    public int[][] getSudokuGrid()
    {
        return sudokugrid;
    }
    
    /**
     * Check whether the value is a valid value for the row and column passed in 
     * @r Row
     * @c Column
     * @v Value
     */
    public boolean isValidNextValue(int r, int c, int v)
    {
        int s = identifySubgrid(r, c);
        if(rows[r][v]==0 && columns[c][v] ==0 && subgrids[s][v]==0)
            return true;
        
        return false;
    }
    
    /**
     * Replace the row and column by the new value and make changes to the old value 
     * @r Row
     * @c Column
     * @nv New Value
     */
    public void replaceGridSpace(int r, int c, int nv)
    {
        int subgrid = identifySubgrid(r,c);
        int v = sudokugrid[r][c];
        if(v != Constants.EMPTY)
        {
            rows[r][v]--;
            columns[c][v]--;
            subgrids[subgrid][v]--;
        }
        rows[r][nv] = 1;
        columns[c][nv] = 1;
        subgrids[subgrid][nv] = 1;
        sudokugrid[r][c]=nv;
    }
    
    /** 
     * Return a Set of possible values that this row and column space can
     * have based on the original puzzle grid
     */
    public List possibleValues(int r, int c)
    {
        List pv = new ArrayList();
        for(int i=0; i<Constants.SIZE; i++)
        {
            int ss = identifySubgrid(r, c);
            if(origRows[r][i] == 0 && origColumns[c][i] == 0 && origSubgrids[ss][i] == 0)
            {
                pv.add(new Integer(i));
            }
        }
        
        return pv;
    }
    
    /**
     * Add to the grid column and row this new value. If original is true, add it to the 
     * original puzzle data
     * @r Row
     * @c Column
     * @original Whether the data added is modifiable or part of the original puzzle
     */
    public void addToGrid(int r, int c, int v, boolean original)
    {
        rows[r][v] = 1;
        columns[c][v] = 1;
        int s = identifySubgrid(r, c);
        subgrids[s][v] = 1;
        sudokugrid[r][c]=v;
        
        if(original)
        {
            origRows[r][v] = 1;
            origColumns[c][v] = 1;
            origSubgrids[s][v] = 1;
        }
    }
    
    public void removeFromGrid(int r, int c, int v, boolean original)
    {
        rows[r][v] = 0;
        columns[c][v] = 0;
        int s = identifySubgrid(r, c);
        subgrids[s][v] = 0;
        sudokugrid[r][c]=Constants.EMPTY;
        
        if(original)
        {
            origRows[r][v] = 0;
            origColumns[c][v] = 0;
            origSubgrids[s][v] = 0;
        }
        
    }
    
    /** 
     * Identify the subgrid that this row and this column belong to.
     */
    public int identifySubgrid(int r, int c)
    {
        int rs = r++;
        int cs = c++;
        
        rs = ((int)((rs)/Constants.SUBGRID_SIZE))*Constants.SUBGRID_SIZE;
        cs = (int)cs/Constants.SUBGRID_SIZE +1;
        
        int sub = cs+rs-1;
        return sub;
    }
    
    /** 
     * Is the grid space represented by this row and this column an unfilled space?
     */
    public boolean isEmptySpace(int r, int c)
    {
        if(sudokugrid[r][c] == Constants.EMPTY)
            return true;
        
        return false;
    }
}
