package no.uib.inf101.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GridTest {

  @Test
  void testThrowsIllegalRows() {
    assertThrows(IllegalArgumentException.class, () -> new Grid(-1, 5));
    assertThrows(IllegalArgumentException.class, () -> new Grid(5, -1));

  }

  @Test
  void iteratorGrid() {
    IGrid grid = new Grid(5, 5);

    for (GridCell cell : grid) {
      assertEquals(grid.get(cell.pos()), ' ');
    }
  }
  
  @Test
  void testPositionOnGrid() {
    IGrid grid = new Grid(5, 5);

    assertFalse(grid.positionIsOnGrid(new CellPosition(6, 0)));
    assertTrue(grid.positionIsOnGrid(new CellPosition(0,4)));
  }

  @Test
  void testSetGet() {
    IGrid grid = new Grid(5, 5);

    for (GridCell cell : grid) {
      assertEquals(grid.get(cell.pos()), ' ');
      grid.set(cell.pos(), 'r');
    }

    for (GridCell cell : grid) {
      assertEquals(grid.get(cell.pos()), 'r');
    }

    assertThrows(IndexOutOfBoundsException.class, () -> grid.set(new CellPosition(8, 0), 'r'));

  }

}