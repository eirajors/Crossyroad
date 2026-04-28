package no.uib.inf101.crossyroad.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public interface ViewableGameModel {

  public GridDimension getDimension();

  public Iterable<GridCell> getTiles();

  /**
   * 
   * @return
   */
  public Iterable<GridCell> getBackgroundTiles();

  /** 
   * @return returns the current level
   */
  public int getLevel();

}
