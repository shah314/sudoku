
/*
Backtracking Sudoku Solver
--------------------------

Author: Shalin Shah
Email: shah.shalin@gmail.com

*/

#include <stdio.h>
#include <algorithm>
#include <vector>
using namespace std;

/* Globals */
int ** sudokuGrid;
bool ** columns;
bool ** rows;
bool ** subgrids;
const int gridSize = 9;
const int empty = -1;
const int max_solutions = 100;

/* Print the Grid to the standard output */
void printGrid()
{
	printf("\n-------------------");
	for(int i=0; i<9; i++)
   {
   	printf("\n");
   	for(int j=0; j<9; j++)
      {
      	printf("%d ", sudokuGrid[i][j]+1);
      }
   }
   printf("\n-------------------");
}

/* Identify the subgrid that the passed in row and column belong to */
inline int identifySubgrid(int r, int c)
{
	int rs = r;
   int cs = c;

	rs = ((int)((rs)/3))*3;
   cs = (int)cs/3 +1;

   int sub = cs+rs-1;
   return sub;
}

/* Add the value to the passed in row and column */
inline void addToGrid(int row, int col, int value)
{
	sudokuGrid[row][col] = value;
   rows[row][value] = true;
   columns[col][value] = true;
   int subgrid = identifySubgrid(row, col);
   subgrids[subgrid][value] = true;
}

/* Replace the value of the passed in row and column with the passed in value */
inline void replaceValue(int row, int col, int value)
{
	int pValue = sudokuGrid[row][col];
	int subgrid = identifySubgrid(row, col);
   if(pValue != empty)
	{
   	rows[row][pValue] = false;
   	columns[col][pValue] = false;
   	subgrids[subgrid][pValue] = false;
   }

   sudokuGrid[row][col] = value;
   rows[row][value] = true;
   columns[col][value] = true;
   subgrids[subgrid][value] = true;
}

/* A space in the grid that has to be filled */
class GridSpace
{
   public:
		vector<int> * possibleValues;
   	int row;
   	int column;
      int count;
      GridSpace * next;
      GridSpace * previous;

   	GridSpace(int r, int c)
      {
      	count = 0;
      	row = r;
         column = c;
      }

      ~GridSpace()
      {
      	if(possibleValues != NULL)
      		delete possibleValues;

         possibleValues = NULL;
      }

      void addPossibleValue(int v)
      {
      	possibleValues->push_back(v);
      }

      int nextValue()
      {
      	if(count == possibleValues->size())
         {
         	return empty;
         }

         int value = possibleValues->at(count);
         count++;
         return value;
      }

      void reset()
      {
      	count = 0;
      }
};

/* A global vector of spaces in non-decreasing order of the number of possible values */
vector<GridSpace*> spaces;

/* Initialize the global arrays - rows, subgrids, columns, and sudokuGrid */
void initializeGlobalArrays()
{
   sudokuGrid = new int*[gridSize];
   columns = new bool*[gridSize];
   rows = new bool*[gridSize];
   subgrids = new bool*[gridSize];

   for(int i=0; i<gridSize; i++)
   {
   	sudokuGrid[i] = new int[gridSize];
      columns[i] = new bool[gridSize];
      rows[i] = new bool[gridSize];
      subgrids[i] = new bool[gridSize];
   	for(int j=0; j<gridSize; j++)
      {
         sudokuGrid[i][j] = empty;
         columns[i][j] = false;
         rows[i][j] = false;
         subgrids[i][j] = false;
      }
   }
}

/* Return a vector of possible values that the passed in space (with row and col) can have */
vector<int> * possibleValues(int row, int col)
{
	vector<int> * pv = new vector<int>();
   int subgrid = identifySubgrid(row, col);
	for(int i=0; i<9; i++)
   {
   	if(!rows[row][i] && !columns[col][i] && !subgrids[subgrid][i])
      {
      	pv->push_back(i);
      }
   }

   return pv;
}

/* A functor to sort spaces in non-decreasing order of possible values */
struct AscendingSpaces
{
	public:
	bool operator()(GridSpace *& space1, GridSpace *& space2)
   {
   	return space1->possibleValues->size() < space2->possibleValues->size();
   }
};

/* Populate the global arrays with values of the passed in puzzle.
   Create a global vector of spaces sorted according to non-decreasing order of
   possible values */
void createGrid(int puzzle[9][9])
{
 	for(int i=0;i<gridSize; i++)
   {
   	for(int j=0; j<gridSize; j++)
      {
      	if(puzzle[i][j] == 0)
         {
         	GridSpace * space = new GridSpace(i, j);
            spaces.push_back(space);
         }
         else
         {
				addToGrid(i, j, puzzle[i][j]-1);
         }
      }
   }

   for(int i=0; i<spaces.size(); i++)
   {
   	GridSpace * space = spaces.at(i);
      int row = space->row;
      int col = space->column;
      space->possibleValues = possibleValues(row, col);
   }

   sort(spaces.begin(), spaces.end(), AscendingSpaces());

   GridSpace * previous = NULL;
   for(int i=0; i<spaces.size(); i++)
   {
   	GridSpace* space = spaces.at(i);
      if(previous == NULL)
      {
      	space->previous = previous;
         previous = space;
      }
      else
      {
      	previous->next = space;
         space->previous = previous;
         previous = space;
      }
   }

   /*for(int i=0; i<spaces.size(); i++)
   {
   	GridSpace * space = spaces.at(i);
      printf("%d:%d - %d\n", space->row, space->column, space->possibleValues->size());
   } */
}

/* Is the passed in value valid for the passed in row and column? */
inline bool isValidValue(int row, int col, int value)
{
	int subgrid = identifySubgrid(row, col);
   if(!rows[row][value] && !columns[col][value] && !subgrids[subgrid][value])
   	return true;

   return false;
}

/* Reset the value in the grid that is represented by the passed in row and column */
inline void resetGridSpace(int row, int col)
{
	int value = sudokuGrid[row][col];
	sudokuGrid[row][col] = empty;
   rows[row][value] = false;
   columns[col][value] = false;
   int subgrid = identifySubgrid(row, col);
   subgrids[subgrid][value] = false;

}

/* Solve the puzzle */
void solve(void)
{
   int solutions = 0;
   GridSpace * current = spaces.at(0);
   GridSpace * last = spaces.at(spaces.size()-1);
   int iterations = 0;
   int backtracks = 0;
   while(true)
   {
   	iterations++;
   	if(current == last)
      {
			int nextValue = current->nextValue();
         while(nextValue != empty && !isValidValue(current->row, current->column, nextValue))
         {
         	nextValue = current->nextValue();
         }

         if(nextValue == empty)
         {
         	current->reset();
            resetGridSpace(current->row, current->column);
            current = current->previous;
            backtracks++;
         }
         else
         {
         	// solved
            replaceValue(current->row, current->column, nextValue);
            printGrid();
				solutions++;
            if(solutions >= max_solutions)
            {
            	printf("\nStopping after %d solutions (Change max_solutions).", max_solutions);
            	break;
            }
            current = current->previous;
            backtracks++;
         }
      }
      else
      {
      	int nextValue = current->nextValue();
         while(nextValue != empty && !isValidValue(current->row, current->column, nextValue))
         {
         	nextValue = current->nextValue();
         }

         if(nextValue == empty)
         {
         	current->reset();
            resetGridSpace(current->row, current->column);
            current = current->previous;
            backtracks++;
            if(current == NULL)
            {
               break;
            }
         }
         else
         {
         	replaceValue(current->row, current->column, nextValue);
            current = current->next;
         }
      }
   }

   if(solutions == 0)
   {
   	printf("\nNo Solutions Exist for the Input Puzzle.\n");
   }
   else
   {
   	printf("\n%d Solution(s). \n\n%d iterations, %d backtracks.\n", solutions, iterations, backtracks);
   }
}

/* Free Memory */
void deleteGlobals(void)
{
	for(int i=0; i<gridSize; i++)
   {
   	delete [] sudokuGrid[i];
      delete [] rows[i];
      delete [] columns[i];
      delete [] subgrids[i];
   }

	for(int i=0; i<spaces.size(); i++)
   {
   	GridSpace * space = spaces.at(i);
      delete space;
   }

   delete [] sudokuGrid;
   delete [] rows;
   delete [] columns;
   delete [] subgrids;
}

/* The puzzle to be solved */
int puzzle1[9][9] =
{
    {7,0,8,5,0,1,0,0,6},
    {0,9,0,0,0,0,8,0,0},
    {0,6,0,0,0,0,0,0,0},
    {4,0,1,0,0,0,0,0,0},
    {0,0,0,0,7,0,0,9,0},
    {0,0,7,0,5,0,0,3,0},
    {8,0,0,7,0,0,1,0,5},
    {0,0,0,2,0,0,4,0,0},
    {5,0,4,3,6,0,9,0,2},
};

/* Main */
int main(void)
{
	initializeGlobalArrays();
   createGrid(puzzle1);
   solve();
   deleteGlobals();
   return 0;
}
