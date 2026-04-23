package no.uib.inf101.crossyroad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Timer;

import no.uib.inf101.crossyroad.view.GameView;

public class GameController implements KeyListener, ActionListener {

  private final ControllableGameModel model;
  private final GameView view; 
  private Random random;

  private Timer timer; 

  private int tickCount; 
  private int spawnInterval;

  public GameController (ControllableGameModel model, GameView view) {
    this.model = model;
    this.view = view;
    this.random = new Random(); 
    this.timer = new Timer(model.getTimerDelayCars(), this);
    this.tickCount = 0;
    this.spawnInterval = 100;
     

    this.view.addKeyListener(this);
    this.view.setFocusable(true);
    this.view.requestFocusInWindow();

    this.timer.start();

  }

  public void updateView() {
    view.update(model.getTiles(), model.getBackgroundTiles(), model.getDimension(), model.getGameState(), model.getCars());
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
    updateView();

  }

  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void actionPerformed(ActionEvent a) {
    model.moveCars(view.getWidth()); 

    tickCount++; 
    if (tickCount >= spawnInterval) {
      model.spawnCar(view.getWidth(), view.getHeight());
      tickCount = 0; 
      spawnInterval = random.nextInt((500 - 30) + 1) + 30;
    }
    //this.view.repaint();
    updateView();
  }

  

}