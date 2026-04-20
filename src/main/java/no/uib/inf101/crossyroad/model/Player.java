package no.uib.inf101.crossyroad.model;

import no.uib.inf101.grid.CellPosition;

public class Player {

  private CellPosition pos; 
  private final char chr; 
  private Board board; 


  public Player (CellPosition pos, char chr){
    this.pos = pos;
    this.chr = chr;
  }

  public Player shiftPlayer (int deltaRow, int deltaCol) {
    int newRow = this.pos.row() + deltaRow;
    int newCol = this.pos.col() + deltaCol;

    return new Player(new CellPosition(newRow, newCol), chr);
  }


  public CellPosition getCellPosition(){
    return pos;
  }

  public char getChar(){
    return chr;
  }
}
