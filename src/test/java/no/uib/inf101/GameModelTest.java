package no.uib.inf101;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.crossyroad.model.GameModel;
import no.uib.inf101.crossyroad.model.GameState;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.IGrid;

public class GameModelTest {

  @Test
  public void movePlayerTest() {
    IGrid grid = new Grid(10, 10, ' ');
    IGrid backGrid = new Grid(10, 10, ' ');

    GameModel model = new GameModel(grid, backGrid, null);

    CellPosition posNow = model.getPlayer().getCellPosition();

    assertTrue(model.movePlayer(0, 1));
    assertFalse(model.movePlayer(11, 0));

    CellPosition posAfter = new CellPosition(posNow.row(), posNow.col() + 1); 

    assertEquals(posAfter, model.getPlayerPosition());
  }

  @Test
  public void testWonGame() {
    IGrid grid = new Grid(10, 10, ' ');
    IGrid backGrid = new Grid(10, 10, ' ');

    GameModel model = new GameModel(grid, backGrid, null);

    assertEquals(model.getGameState(), GameState.ACTIVE);

    assertTrue(model.movePlayer(-9, 0));

    assertEquals(model.getGameState(), GameState.WON);
  }


}
