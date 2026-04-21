package no.uib.inf101.crossyroad.model;


import no.uib.inf101.grid.CellPosition;

public class Log implements IObstacle{

  private char chr; 
  private CellPosition pos; 

  public Log(CellPosition pos) {
    this.chr = 'b'; 
    this.pos = pos; 
  }

  @Override
  public CellPosition getCellPosition() {
    return pos; 
  }

  @Override
  public char getChar() {
    return chr; 
  }

} 

