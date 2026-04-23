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

  /**
   * 
   * @return
   */
  public GridDimension getDimension();
  
  /**
   * 
   * @return
   */
  public Iterable<GridCell> getTiles();

  /**
   * 
   * @return
   */
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

  /**
   * Iterates through a list cars, and each car across the board. 
   * When a car reaches the end of the board, it gets removed from the list
   * 
   * @param width the width of the screen 
   */
  public void moveCars(int width);

  /**
   * spawns a new car in the grid
   */
  public void spawnCar(int screenWidth, int screeenHeight);

  /**
   * 
   * @return
   */
  public List<Car> getCars();


}
