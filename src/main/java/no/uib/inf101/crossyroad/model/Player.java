package no.uib.inf101.crossyroad.model;

import no.uib.inf101.grid.CellPosition;

public class Player {

  private CellPosition pos; 
  private final char chr; 
  private final Board board; 

  /**
   * Constructur
   * @param pos what position is the player supposed to be drawn in 
   * @param chr what is the character of the player, so that the icon can be fetched from the iconTheme
   * @param board which board is the player supposed to be drawn on 
   */
  public Player (CellPosition pos, char chr, Board board){
    this.pos = pos;
    this.chr = chr;
    this.board = board;
  }

  /**
   * 
   * @param deltaRow how many rows the player should move
   * @param deltaCol how many columns the player should move
   * @return a new player where the column or row has been shifted with the given amount
   */
  public Player shiftPlayer (int deltaRow, int deltaCol) {
    int newRow = this.pos.row() + deltaRow;
    int newCol = this.pos.col() + deltaCol;

    return new Player(new CellPosition(newRow, newCol), chr, this.board);
  }

  /**
   * 
   * @return the current cellposition of the player
   */
  public CellPosition getCellPosition(){
    return pos;
  }

  /**
   * 
   * @return the players given Character
   */
  public char getChar(){
    return chr;
  }

}
