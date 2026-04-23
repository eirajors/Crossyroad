package no.uib.inf101;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

public class BoardTest {
  @Test
  void testConstructs() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Grid(3, -1);
    }, "Can not have negative number of cols");
    assertThrows(IllegalArgumentException.class, () -> {
      new Grid(-1, 3);
    }, "Can not have negative number of rows");
  }

  @Test
  void testIterator() {
    Grid grid = new Grid(3, 2, 'x');
    grid.set(new CellPosition(0, 0), 'a');
    grid.set(new CellPosition(1, 1), 'b');
    grid.set(new CellPosition(2, 1), 'c');

    List<GridCell> items = new ArrayList<>();
    for (GridCell coordinateItem : grid) {
      items.add(coordinateItem);
    }

    assertEquals(3 * 2, items.size());
    assertTrue(items.contains(new GridCell(new CellPosition(0, 0), 'a')));
    assertTrue(items.contains(new GridCell(new CellPosition(1, 1), 'b')));
    assertTrue(items.contains(new GridCell(new CellPosition(2, 1), 'c')));
    assertTrue(items.contains(new GridCell(new CellPosition(0, 1), 'x')));
  }

}
