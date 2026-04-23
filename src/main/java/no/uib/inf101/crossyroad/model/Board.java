package no.uib.inf101.crossyroad.model;

import java.util.Iterator;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;
public class Board implements Iterable<GridCell>, GridDimension {

  private final IGrid grid;

  /**
   * A constructor for TetrisBoard
   * Creates a tetrisboard given a grid
   * 
   * @param grid the grid used to create the board
   */
  public Board(IGrid grid) {
    if (grid == null) throw new NullPointerException("Kan ikke være null"); 
    this.grid = grid;
  }
  
  /**
   * Another constructor for TetrisBoard
   * Creates a tetrisboard griven a number of rows and columns
   * 
   * @param rows the number of rows
   * @param cols the number of columns 
   */
  public Board(int rows, int cols) {
    this(new Grid(rows, cols, ' '));
  }

  @Override
  public int rows() {
    return this.grid.rows();
  }

  @Override
  public int cols() {
    return this.grid.cols();
  }

  @Override
  public Iterator<GridCell> iterator() {
    return this.grid.iterator();
  }

  /**
   * Checks whether a position on the board is empty
   * 
   * @param pos the position to check
   * @return true if the position is empty (symbol is '-'), false otherwise
   */
  public boolean isFree(CellPosition pos) {
    if (this.grid.get(pos) == ' ') {
      return true;
    }
    return false;
  }


  /***
   * Checks if the given position is on the board
   * 
   * @param pos the position that is checked
   * @return true if the position is valid, false otherwise 
   */
  public boolean positionIsOnGrid(CellPosition pos) {
    if (pos.col() < 0 || pos.col() >= this.cols()) {
      return false; 
    }
    if (pos.row() < 0 || pos.row() >= this.rows()) {
      return false;
    }
    return true; 
  }

}



