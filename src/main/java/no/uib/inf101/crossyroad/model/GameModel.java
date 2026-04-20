package no.uib.inf101.crossyroad.model;

import no.uib.inf101.crossyroad.controller.ControllableGameModel;
import no.uib.inf101.crossyroad.view.ViewableGameModel;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;

public class GameModel implements ControllableGameModel, ViewableGameModel{

  private IGrid grid;
  private Player player; 
  private final Board board; 
  private boolean won;

  public GameModel(IGrid grid) {
    this.grid = grid;
    this.board = new Board(grid);
    this.player = new Player(new CellPosition(grid.rows()-1, 7), 'r');
    this.won = false; 

    grid.set(player.getCellPosition(), player.getChar());
  }

  /**
   * Moves player by deltarows and deltacols
   * 
   * @param deltaRow how many rows the player is moved by
   * @param deltaCol how many cols the player is moved by
   * @return returns true if the move is allowed, and false otherwise
   */
  public boolean movePlayer (int deltaRow, int deltaCol) {
    Player candidatePlayer = this.player.shiftPlayer(deltaRow, deltaCol);
    
    if (!this.isLegalPosition(candidatePlayer)) {
      return false; 
    }
    grid.set(this.player.getCellPosition(), ' ');
    this.player = candidatePlayer; 
    grid.set(player.getCellPosition(), player.getChar());

    won = isWon(candidatePlayer);
    
    return true; 
  }

  public boolean isWon(Player player) {
    if (player.getCellPosition().row() == 0) {
      return true; 
    }
    return false; 

  }

  /**
   * Checks if the position the player wants to be moved to is available/legal
   * 
   * @param candidatePlayer the position where the player might be moved to 
   * @return returns true if the move is legal, and false otherwise 
   */
  private boolean isLegalPosition (Player candidatePlayer) {
    if (!this.board.positionIsOnGrid(candidatePlayer.getCellPosition())) {
      return false; 
    }
    if (!this.board.isFree(candidatePlayer.getCellPosition())) {
      return false; 
    }
    return true; 
  }


  public CellPosition getPlayerPosition(){
    return this.player.getCellPosition();
  }

  public Player getPlayer() {
    return this.player; 
  }

  @Override
  public GridDimension getDimension() {
    return this.board;
  }

  @Override
  public Iterable<GridCell> getTiles() {
    return this.board;
  }

}
