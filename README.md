<H1>Fast Backtracking Sudoku Solvers in Java and C++ and Puzzle Generator in Java</H1>
<i><h3>Shalin Shah</h3></i>

<a href="https://zenodo.org/badge/latestdoi/134314614"><img src="https://zenodo.org/badge/134314614.svg" alt="DOI"></a><br>

<P>Sudoku is a Japanese puzzle in which a 9x9 grid is partially filled with numbers from 1 to 9. A solution to  this puzzle fills in the empty cells of the grid such that there are no repeating numbers in each row, each column and each 3x3 subgrid. The general Sudoku problem is NP-Complete (by reduction from graph coloring). It is not difficult to adapt the code to generate puzzles larger than the 3x3 subgrid.</P>

<H3>Backtracking Solver</H3>
<P>Backtracking Solver (depth-first, non-recursive) fills the grid starting from the cell that has the least possible valid values. For most instances, it reports all possible solutions to the input grid in a few milliseconds. 
<h3>Sudoku Puzzle Generator</h3>
<P>The puzzle generator generates random Sudoku puzzle instances. Generated puzzles have unique solutions. The number of filled entries is a parameter, so the generator can be used to search for sparsely filled grids, possibly below the currently known threshold for which there is a unique solution.</p>
