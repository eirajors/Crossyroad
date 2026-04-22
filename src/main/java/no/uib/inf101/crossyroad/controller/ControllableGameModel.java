package no.uib.inf101.crossyroad.controller;

import java.util.List;

import no.uib.inf101.crossyroad.model.GameState;
import no.uib.inf101.crossyroad.model.obstacles.Car;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public interface ControllableGameModel {

  /**
   * Moves player by deltarows and deltacols
   * 
   * @param deltaRow how many rows the player is moved by
   * @param deltaCol how many cols the player is moved by
   * @return returns true if the move is allowed, and false otherwise
   */
  public boolean movePlayer(int deltaRow, int deltaCol);

  public GridDimension getDimension();
  
  public Iterable<GridCell> getTiles();

  public Iterable<GridCell> getBackgroundTiles();

  /**
   * @return returns the current gamestate
   */
  public GameState getGameState();

  /**
   * Returns how fast a timer should update itself
   * @return returns an integer indicating how often the timer should repaint
   */
  public int getTimerDelayCars();

  public void moveCars(int width);

  /**
   * spawns a new car in the grid
   */
  public void spawnCar(int screenWidth, int screeenHeight);

  public List<Car> getCars();


}
