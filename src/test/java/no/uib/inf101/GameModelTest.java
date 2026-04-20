package no.uib.inf101;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.crossyroad.model.Board;
import no.uib.inf101.crossyroad.model.GameModel;
import no.uib.inf101.crossyroad.model.Player;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

public class GameModelTest {

  @Test
  public void movePlayerTest() {
    Grid grid = new Grid(10, 10, ' ');
    GameModel model = new GameModel(grid);

    Player player = new Player(new CellPosition(0, 0), 'r');

    assertTrue(model.movePlayer(0, 1));
    assertEquals(new CellPosition(0, 1), model.getPlayerPosition());
  }

}
