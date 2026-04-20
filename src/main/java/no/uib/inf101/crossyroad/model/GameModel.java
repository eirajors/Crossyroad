package no.uib.inf101.crossyroad.model;

import no.uib.inf101.crossyroad.controller.ControllableGameModel;
import no.uib.inf101.crossyroad.view.ViewableGameModel;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;

public class GameModel implements ControllableGameModel, ViewableGameModel{

  private GameState gameState; 
  private IGrid grid;
  private Player player; 
  private final Board board; 

  /**
   * The constructor for the GameModel
   * @param grid initiates a gamemodel given a grid
   */
  public GameModel(IGrid grid) {
    this.grid = grid;
    this.board = new Board(grid);
    this.player = new Player(new CellPosition(grid.rows()-1, 7), 'r');
    this.gameState = GameState.ACTIVE;

    grid.set(player.getCellPosition(), player.getChar());
  }

  @Override
  public boolean movePlayer (int deltaRow, int deltaCol) {
    Player candidatePlayer = this.player.shiftPlayer(deltaRow, deltaCol);

    if (gameState != GameState.ACTIVE) {
      return false; 
    }

    if (!this.isLegalPosition(candidatePlayer)) {
      return false; 
    }
    grid.set(this.player.getCellPosition(), ' ');
    this.player = candidatePlayer; 
    grid.set(player.getCellPosition(), player.getChar());

    if (isWon(this.player)) {
      gameState = GameState.WON; 
    }
    
    return true; 
  }

  /**
   * 
   * @param player
   * @return
   */
  private boolean isWon(Player player) {
    if (player.getCellPosition().row() == 0) {
      return true; 
    }
    return false; 

  }

  @Override
  public GameState getGameState() {
    return gameState; 
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

  /**
   * 
   * @return returns the current cellposition of the player
   */
  public CellPosition getPlayerPosition(){
    return this.player.getCellPosition();
  }

  /**
   * @return returns the player
   */
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
