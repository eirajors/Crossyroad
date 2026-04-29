package no.uib.inf101.crossyroad.model.roads;

import java.util.List;
import java.util.Random;

import no.uib.inf101.grid.IGrid;

public class RandomRoadFactory {

  private final Random random = new Random();
  private IGrid backgroundGrid; 
   

  /**
   * Adds rows of randomly selected rows to the background
   * @param backgroundGrid the background where the roads are drawn
   */
  public RandomRoadFactory(IGrid backgroundGrid) {
    this.backgroundGrid = backgroundGrid;
    
    fillGrid(this.backgroundGrid);
  }

  /**
   * 
   * @return A randomly selected s which refers to which type of road is to be drawn
   */
  private String getNext() {
    List<String> roads = List.of("grass", "highway", "highwayRight");

    int index = random.nextInt(roads.size());

    String s = roads.get(index);

    return s;
  }

  /**
   * Fills every row of a given grid with randomly selected roads
   * @param grid the grid where the roads are to be filled
   */
  private void fillGrid(IGrid grid) {
    for (int row = 0; row < grid.rows(); row++) {
      String typeRoad = getNext(); 

      new Road(typeRoad, row, grid);
    }
  }

}
