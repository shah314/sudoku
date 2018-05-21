
package sudoku.solver;

import java.util.*;

/**
 * Represents a space in the sudoku grid.
 * @author  Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class GridSpace implements Comparable
{
    private int row;
    private int column;
    private LinkedHashSet pValues = new LinkedHashSet();
    private Iterator it = null;
    private boolean isModifiable = false;
    private GridSpace next;
    private GridSpace previous;
    
    /** Constructor */
    public GridSpace(int r, int c) 
    {
        row = r;
        column = c;
    }
    
    /**
     * Get the next grid space
     */
    public GridSpace next()
    {
        return next;
    }
    
    /**
     * Get the previous grid space
     */
    public GridSpace previous()
    {
        return previous;
    }
    
    /**
     * Set the next grid space
     */
    public void setNext(GridSpace n)
    {
        next = n;
    }
    
    /**
     * Get the previous grid space
     */
    public void setPrevious(GridSpace p)
    {
        previous = p;
    }
    
    /**
     * Is this space modifiable?
     */
    public void setModifiable(boolean f)
    {
        isModifiable = f;
    }
    
    /**
     * Is this space modifiable?
     */
    public boolean isModifiable()
    {
        return isModifiable;
    }
    
    /**
     * Get the row of the grid space
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Set the row of the grid space
     */
    public void setRow(int r)
    {
        row = r;
    }
    
    /**
     * Get the column of the grid space
     */
    public int getColumn()
    {
        return column;
    }
    
    /**
     * Get the column of the grid space
     */
    public void setColumn(int c)
    {
        column = c;
    }
    
    /**
     * Add to the possible values that this space can be filled with
     */
    public void addPossibleValue(int i)
    {
        pValues.add(new Integer(i));
    }
    
    /**
     * Set the possible values that this space can be filled with
     */
    public void setPossibleValues(LinkedHashSet p)
    {
        pValues = p;
        it = pValues.iterator();
    }
    
    /**
     * Is this the last grid space?
     */
    public boolean hasNext()
    {
        return it.hasNext();
    }
    
    /**
     * Get the next possible value that this grid space can be filled with
     */
    public int nextValue()
    {   
        if(it.hasNext())
            return ((Integer)it.next()).intValue();
        else
            return Constants.EMPTY;
    }
    
    /**
     * Reset this grid space. Used while backtracking
     */
    public void reset()
    {
        it = pValues.iterator();
    }
    
    /**
     * Get the list of possible values that this grid space can be filled with
     */
    public LinkedHashSet getPossibleValues()
    {
        return pValues;
    }
    
    /**
     * How many possible values can this grid space be filled with?
     * Used while sorting.
     */
    public int possibleValues()
    {
        return pValues.size();
    }
    
    /** 
     * compareTo method of the interface Comparable
     * Used while sorting
     */
    public int compareTo(Object obj) 
    {
        GridSpace ep = (GridSpace)obj;
        if(ep.possibleValues() > possibleValues())
        {
            return -1;
        }
        else if(ep.possibleValues() < possibleValues())
        {
            return 1;
        }        
        
        return 0;
    }
}