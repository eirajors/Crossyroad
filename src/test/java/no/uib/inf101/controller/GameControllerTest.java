package no.uib.inf101.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;

import no.uib.inf101.crossyroad.controller.GameController;
import no.uib.inf101.crossyroad.model.GameModel;
import no.uib.inf101.crossyroad.model.GameState;
import no.uib.inf101.crossyroad.view.GameView;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.IGrid;

public class GameControllerTest {


  @Test
  void testStartGame() {

    IGrid grid = new Grid(10, 10, ' ');
    IGrid backGrid = new Grid(10, 10, ' ');
    GameModel model = new GameModel(grid, backGrid);
    GameView view = new GameView(model);

    GameController controller = new GameController(model, model, view);
    
    assertEquals(model.getGameState(), GameState.HOMESCREEN);

    KeyEvent upKey = new KeyEvent(view, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED);
    controller.keyPressed(upKey);

    assertEquals(GameState.ACTIVE, model.getGameState());

  }

  @Test
  void testPlayerGameController() {

    IGrid grid = new Grid(10, 10, ' ');
    IGrid backGrid = new Grid(10, 10, ' ');
    GameModel model = new GameModel(grid, backGrid);
    GameView view = new GameView(model);

    GameController controller = new GameController(model, model, view);
    model.startGame();

    assertEquals(GameState.ACTIVE, model.getGameState());
    assertEquals(model.getPlayerPosition().row(), grid.rows()-1);


    int colBefore = model.getPlayerPosition().col(); 

    KeyEvent rightKey = new KeyEvent(view, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
    controller.keyPressed(rightKey);

    int newCol = model.getPlayerPosition().col();

    assertEquals(newCol, colBefore +1);
  }

  @Test
  void testPauseGame() {
    IGrid grid = new Grid(10, 10, ' ');
    IGrid backGrid = new Grid(10, 10, ' ');
    GameModel model = new GameModel(grid, backGrid);
    GameView view = new GameView(model);

    GameController controller = new GameController(model, model, view);
    model.startGame();

    assertEquals(GameState.ACTIVE, model.getGameState());

    KeyEvent key = new KeyEvent(view, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
    controller.keyPressed(key);

    assertEquals(model.getGameState(), GameState.PAUSE);


  }

}
