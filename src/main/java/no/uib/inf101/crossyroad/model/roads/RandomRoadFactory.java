package no.uib.inf101.crossyroad.model.roads;

import java.util.List;
import java.util.Random;

import no.uib.inf101.grid.IGrid;

public class RandomRoadFactory {

  private final Random random = new Random();
  private IGrid backgroundGrid; 
   

  public RandomRoadFactory(IGrid backgroundGrid) {
    this.backgroundGrid = backgroundGrid;
    
    fillGrid(this.backgroundGrid);
  }

  private String getNext() {
    List<String> roads = List.of("grass", "highway", "highwayRight");

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
