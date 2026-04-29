package no.uib.inf101.crossyroad.view;

import java.util.List;

import no.uib.inf101.crossyroad.model.GameState;
import no.uib.inf101.crossyroad.model.obstacles.Car;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public interface ViewableGameModel {

  /**
   * @return the dimensions of the game board, both rows and columns
   */
  public GridDimension getDimension();

  /**
   * @return all the tiles of the main grid (like player and obstacles)
   */
  public Iterable<GridCell> getTiles();

  /**
   * 
   * @return all the tiles of the backgroundgrid (highway or grass)
   */
  public Iterable<GridCell> getBackgroundTiles();

  /** 
   * @return returns the current level
   */
  public int getLevel();

  /**
   * 
   * @return the current gamestate of the game
   */
  public GameState getGameState();
  
  /**
   * 
   * @return a list of cars which are currently active in the game
   */
  public List<Car> getCars();

}
