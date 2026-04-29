package no.uib.inf101.crossyroad.view;

import java.util.List;

import no.uib.inf101.crossyroad.model.GameState;
import no.uib.inf101.crossyroad.model.obstacles.Car;
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

  public GameState getGameState();
  
  public List<Car> getCars();

}
