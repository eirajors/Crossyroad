package no.uib.inf101.crossyroad.model;

import javax.swing.JFrame;

import no.uib.inf101.grid.CellPosition;

public class Player {

  private CellPosition pos; 
  private final char chr; 
  private Board board; 
  private Coordinate coordinates;
  private JFrame frame; 


  public Player (CellPosition pos, char chr, Board board, JFrame frame){
    this.pos = pos;
    this.chr = chr;
    this.board = board;
    this.frame = frame; 
  }

  public Player shiftPlayer (int deltaRow, int deltaCol) {
    int newRow = this.pos.row() + deltaRow;
    int newCol = this.pos.col() + deltaCol;

    return new Player(new CellPosition(newRow, newCol), chr, this.board, this.frame);
  }

  public CellPosition getCellPosition(){
    return pos;
  }

  public char getChar(){
    return chr;
  }

  public int playerWidth() {
    return this.frame.getWidth() / this.board.cols();
  }

  private int playerHeight() {
    return this.frame.getHeight() / this.board.rows(); 
  }

  // private Coordinate playerCoordinate() {
  //   int playerX = ((frame.getWidth() / this.board.cols()) * pos.col()) - (this.playerWidth() / 2);
  //   int playerY = ((frame.getHeight() / this.board.rows()) * pos.row()) - (this.playerHeight() / 2);

  //   return new Coordinate(playerX, playerY);
  // }

  private int getPlayerCenterX() {
    return ((frame.getWidth() / this.board.cols()) * pos.col()) - (this.playerWidth() / 2);
  }

  private int getPlayerCenterY() {
    return ((frame.getHeight() / this.board.rows()) * pos.row()) - (this.playerHeight() / 2);
 
  }

  public int getPlayerRightX() {
    return this.getPlayerCenterX() + (this.playerWidth() / 2);
  }

  public int getPlayerLeftX() {
    return this.getPlayerCenterX() - (this.playerWidth() / 2);
  }

  public int getPlayerBottomY() {
    return this.getPlayerCenterY() + (this.playerHeight() / 2);
  }

  public int getPlayerTopY() {
    return this.getPlayerCenterY() - (this.playerHeight() / 2);
  }
}
