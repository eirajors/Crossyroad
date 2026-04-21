package no.uib.inf101.crossyroad.controller;

import no.uib.inf101.crossyroad.model.GameState;
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


}
