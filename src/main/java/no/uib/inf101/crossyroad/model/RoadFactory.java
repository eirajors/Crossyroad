package no.uib.inf101.crossyroad.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import no.uib.inf101.grid.IGrid;

public class RoadFactory {

  private final Random random = new Random();
  private IGrid backgroundGrid; 
   

  public RoadFactory(IGrid backgroundGrid) {
    this.backgroundGrid = backgroundGrid;
    
    fillGrid(backgroundGrid);
  }

  private String getNext() {
    List<String> roads = List.of("grass", "highway");

    int index = random.nextInt(roads.size());

    String s = roads.get(index);

    return s;
  }

  private void fillGrid(IGrid grid) {
    for (int row = 0; row < grid.rows(); row++) {
      String typeRoad = getNext(); 

      new Road(typeRoad, row, grid);
    }
  }

}
