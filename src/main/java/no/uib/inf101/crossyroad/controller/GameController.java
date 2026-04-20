package no.uib.inf101.crossyroad.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import no.uib.inf101.crossyroad.view.GameView;

public class GameController implements KeyListener {

  private final ControllableGameModel model;
  private GameView view; 

  public GameController (ControllableGameModel model, GameView view) {
    this.model = model;
    this.view = view;

    this.view.addKeyListener(this);
    this.view.setFocusable(true);
    this.view.requestFocusInWindow();
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {

    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      this.model.movePlayer(0, -1);
    }
    else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      this.model.movePlayer(0, 1);
    }
    else if (e.getKeyCode() == KeyEvent.VK_UP) {
      this.model.movePlayer(-1, 0);

    }
    else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      this.model.movePlayer(1, 0);
    }

    this.view.repaint();

  }

  @Override
  public void keyReleased(KeyEvent e) {}

}