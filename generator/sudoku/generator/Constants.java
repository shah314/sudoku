
package sudoku.generator;

/**
 * Constants
 * @author Shalin Shah
 * Email: shah.shalin@gmail.com
 */
public class Constants 
{
    /* 
     * How many numbers to remove before checking for multiple solutions?
     * The higher this number, the less dense and harder the generated puzzle.
     * Typical value is 55 for 9x9 puzzles.
     * This parameter need not be changed.
     */
    public static final int REMOVE_FREQ = 55;
    
    /*
     * Maximum number of displayed numbers. 
     * Low values can slow down the generator considerably.
     * Typical value 28
     */
    public static final int MAX_DISPLAYED_NUMBERS = 28;
    
    /* Size of the puzzle e.g. 9 for 9x9 */
    public static final int SIZE = 9;
    
    /* An empty cell */
    public static final int EMPTY = -1;
    
    /* Size of the each subgrid e.g. 3 for 3x3 */
    public static final int SUBGRID_SIZE = 3;
    
    /* The maximum time in milliseconds to spend on a generated grid 
     * before a random restart */
    public static final long MAX_TIME = 200; // milliseconds   
}
