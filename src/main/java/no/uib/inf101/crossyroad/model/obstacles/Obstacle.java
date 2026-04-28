package no.uib.inf101.crossyroad.model.obstacles;


import java.util.List;
import java.util.Random;

import no.uib.inf101.grid.CellPosition;

public class Obstacle implements IObstacle{

  private char chr; 
  private CellPosition pos; 
  private List<Character> elements; 
  private Random random; 

  public Obstacle(CellPosition pos) {
    this.chr = ' '; 
    this.pos = pos; 
    this.elements = List.of('b', 'd');
    this.random = new Random(); 
  }

  @Override
  public CellPosition getCellPosition() {
    return pos; 
  }

  @Override
  public char getChar() {
    int randomIndex = random.nextInt(2);
    chr = elements.get(randomIndex);

    return chr; 
  }

} 

