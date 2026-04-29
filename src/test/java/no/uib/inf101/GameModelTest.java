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
  public void testGameState() {
    IGrid grid = new Grid(10, 10, ' ');
    IGrid backGrid = new Grid(10, 10, ' ');

    GameModel model = new GameModel(grid, backGrid);
    assertEquals(model.getGameState(), GameState.HOMESCREEN);

    model.startGame();

    assertEquals(model.getGameState(), GameState.ACTIVE);

  }

  @Test
  public void testMovePlayer() {
    IGrid grid = new Grid(10, 10, ' ');
    IGrid backGrid = new Grid(10, 10, ' ');

    GameModel model = new GameModel(grid, backGrid);
    model.startGame();

    assertEquals(model.getGameState(), GameState.ACTIVE);

    CellPosition posNow = model.getPlayer().getCellPosition();

    assertTrue(model.movePlayer(0, 1));
    assertFalse(model.movePlayer(11, 0));

    CellPosition posAfter = new CellPosition(posNow.row(), posNow.col() + 1); 

    assertEquals(posAfter, model.getPlayerPosition());
  }

  @Test
  public void testMovePlayerIllegal() {
    IGrid grid = new Grid(10, 10, ' ');
    IGrid backGrid = new Grid(10, 10, ' ');

    GameModel model = new GameModel(grid, backGrid);
    assertFalse(model.movePlayer(0, 1));

    model.startGame();

    assertFalse(model.movePlayer(-100, 0));
    assertFalse(model.movePlayer(0, 100));
  }

  @Test
  public void testNewRound() {
    IGrid grid = new Grid(10, 10, ' ');
    IGrid backGrid = new Grid(10, 10, ' ');

    GameModel model = new GameModel(grid, backGrid);
    model.startGame();


    assertEquals(model.getGameState(), GameState.ACTIVE);

    assertTrue(model.movePlayer(-9, 0));

    assertEquals(model.getPlayerPosition().row(), grid.rows()-1);
  }


  @Test
  void testRestartGame() {
    IGrid grid = new Grid(10, 10, ' ');
    IGrid backGrid = new Grid(10, 10, ' ');

    GameModel model = new GameModel(grid, backGrid);
    model.startGame();

    model.movePlayer(-9, 0);
    assertEquals(2, model.getLevel());

    model.restartGame();

    assertEquals(1, model.getLevel());
    assertEquals(GameState.ACTIVE, model.getGameState());

    CellPosition expected = new CellPosition(9, 5);
    assertEquals(expected, model.getPlayerPosition());

    assertTrue(model.getCars().isEmpty());
  }

  @Test 
  void testPause() {
    GameModel model = new GameModel(new Grid(10,10,' '), new Grid(10,10,' '));

    model.startGame();
    model.pauseGame();
    assertEquals(GameState.PAUSE, model.getGameState());

    model.pauseGame();
    assertEquals(GameState.ACTIVE, model.getGameState());
  }

  @Test
  void testGoToHomeScreen() {
    GameModel model = new GameModel(new Grid(10,10,' '), new Grid(10,10,' '));

    model.startGame();
    model.movePlayer(-9, 0);

    model.goToHomeScreen();

    assertEquals(GameState.HOMESCREEN, model.getGameState());
    assertEquals(1, model.getLevel());
  }

  @Test
  void testSpawnCar() {
    GameModel model = new GameModel(new Grid(10,10,' '), new Grid(10,10,' '));

    model.spawnCar(500, 500);

    assertFalse(model.getCars().isEmpty());
  }

  @Test
  void testIncreaseLevel() {
    GameModel model = new GameModel(new Grid(10,10,' '), new Grid(10,10,' '));

    model.startGame();

    int startLevel = model.getLevel();

    model.movePlayer(-9, 0); 

    assertEquals(startLevel + 1, model.getLevel());
  }
}

  


