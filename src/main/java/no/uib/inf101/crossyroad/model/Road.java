package no.uib.inf101.crossyroad.model;

import java.util.HashMap;
import java.util.List;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.IGrid;

public class Road {
  private final List<String> ROAD_STYLES = List.of("grass", "highway");

  private final String typeRoad; 
  private IGrid grid; 
  private final int row;

  private static final HashMap<String, Character> FARGER = new HashMap<>();

  static {
    FARGER.put("grass", 'g'); 
    FARGER.put("highway", 'e');
  }

  public Road(String typeRoad, int row, IGrid grid) {
    this.typeRoad = typeRoad; 
    this.row = row; 
    this.grid = grid; 

    if (!ROAD_STYLES.contains(typeRoad)) {
      throw new IllegalArgumentException("Wrong argument. It should be 'grass' or 'highway''" ); 
    }

    char color = FARGER.get(typeRoad);

    setColor(color, row, grid);
  }

  /**
   * Sets the colorSymbol of a row in the grid for the background
   * 
   * @param colorSymbol the symbol of the color to be set
   * @param row which row is to be set this color
   * @param grid the grid of the background
   */
  private void setColor(char colorSymbol, int row, IGrid grid) {
    for (GridCell cell : grid) {
      if (cell.pos().row() == row) {
        grid.set(cell.pos(), colorSymbol);
      }
    }
  }

  
}
