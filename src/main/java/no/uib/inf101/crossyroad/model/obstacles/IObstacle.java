package no.uib.inf101.crossyroad.model.obstacles;

import java.util.ArrayList;

import no.uib.inf101.grid.CellPosition;

public interface IObstacle {

  /***
   * Gets the cellposition of a obstacle
   * @return the cellposition of the obstacle
   */
  public CellPosition getCellPosition();

  /**
   * 
   * @return the character of the obstacle, describing what kind of obstacle it is
   */
  public char getChar();

}
