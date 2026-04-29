package no.uib.inf101.crossyroad.model.obstacles;


import java.util.List;
import java.util.Random;

import no.uib.inf101.grid.CellPosition;

public class Obstacle implements IObstacle{

  private char chr; 
  private CellPosition pos; 
  private final List<Character> elements; 
  private final Random random; 

  /**
   * Creates a random obstacle at the given cellposition
   * @param pos The cellpostition where the obstacle spawns
   */
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

