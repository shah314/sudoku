
package sudoku.solver;

/**
 * A doubly linked list implementation used for backtracking.
 * @author  Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class PNList 
{   
    public GridSpace current;
    public GridSpace head;
    public GridSpace last;
    public int cur = 0;
    
    /** Constructor */
    public PNList(GridSpace c) 
    {
        current = c;
        head = c;
        head.setPrevious(null);
    }
    
    /** 
     * Get the current grid space 
     */
    public GridSpace getCurrent()
    {
        return current;
    }
    
    /** 
     * Get the head of this list
     */
    public GridSpace getHead()
    {
        return head;
    }
    
    /** 
     * Get the last grid space of this list
     */
    public GridSpace getLast()
    {
        return last;
    }
    
    /** 
     * Set the last grid space of this list
     */
    public void setLast(GridSpace s)
    {
        last = s;
        last.setNext(null);
    }
    
    /** 
     * Add next to current
     */
    public void addNext(GridSpace s)
    {
        current.setNext(s);
        s.setPrevious(current);
        current = current.next();
    }
    
    /** 
     * Reset the list to start the traversal from the head
     */
    public void reset()
    {
        current = head;
    }
}
