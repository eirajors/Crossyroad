package no.uib.inf101.crossyroad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.Timer;

import no.uib.inf101.crossyroad.model.GameState;
import no.uib.inf101.crossyroad.view.GameView;
import no.uib.inf101.crossyroad.view.ViewableGameModel;

public class GameController implements KeyListener, ActionListener, MouseListener {

  private final ControllableGameModel model;
  private final ViewableGameModel viewModel;
  private final GameView view;  
  private Random random;

  private Timer timer; 

  private int tickCount; 
  private int spawnInterval;

  public GameController (ControllableGameModel model, ViewableGameModel viewModel, GameView view) {
    this.model = model;
    this.viewModel = viewModel;
    this.view = view;
    this.random = new Random(); 
    this.timer = new Timer(model.getTimerDelayCars(), this);
    this.tickCount = 0;
    this.spawnInterval = 70;
     

    this.view.addKeyListener(this);
    this.view.addMouseListener(this);
    this.view.setFocusable(true);
    this.view.requestFocusInWindow();

    this.timer.start();

  }

  public void updateView() {
    view.update();
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {

    if (viewModel.getGameState() == GameState.ACTIVE) {
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
      else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        this.model.pauseGame();
      }
    }


    else if (viewModel.getGameState() == GameState.PAUSE) {
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        this.model.pauseGame();
      }
      else if (e.getKeyCode() == KeyEvent.VK_H){  
        this.model.goToHomeScreen();
      }

      else if (e.getKeyCode() == KeyEvent.VK_R) {
        this.model.restartGame();
      }
    }

    else if (viewModel.getGameState() == GameState.HOMESCREEN) {
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        this.model.startGame();
      }
    }

    else if (viewModel.getGameState() == GameState.GAMEOVER) {
      if (e.getKeyCode() == KeyEvent.VK_H){  
        this.model.goToHomeScreen();
      }
    }

    this.view.repaint();
    updateView();

  }

  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void actionPerformed(ActionEvent a) {
    if (viewModel.getGameState() == GameState.ACTIVE) {
      model.moveCars(view.getWidth(), view.getHeight()); 

      tickCount++; 
      if (tickCount >= spawnInterval) {
        model.spawnCar(view.getWidth(), view.getHeight());
        tickCount = 0; 
        spawnInterval = random.nextInt((80 - 30) + 1);
      }
      updateView();
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    float x = e.getX(); 
    float y = e.getY();

    if (this.viewModel.getGameState() == GameState.PAUSE) {

      float pauseXL = view.getWidth() - (view.getWidth() / viewModel.getDimension().cols()) - 10;
      float pauseYT = view.getHeight() / viewModel.getDimension().rows();
      float pauseW = view.getWidth() / viewModel.getDimension().cols();
      float pauseH = view.getHeight() / viewModel.getDimension().rows();

      float restartXL = (view.getWidth()/2)-100;
      float restartYT = (view.getHeight()/2)+150;
      float restartW = (view.getWidth() / viewModel.getDimension().cols())+150;
      float restartH = view.getHeight() / viewModel.getDimension().rows();

      float quitXL = (view.getWidth()/2)-100;
      float quitYT = (view.getHeight()/2)+200;
      float quitW = (view.getWidth() / viewModel.getDimension().cols())+150;
      float quitH = view.getHeight() / viewModel.getDimension().rows();

      if (pauseXL <= x && x <= pauseXL + pauseW && pauseYT <= y && y <= pauseYT + pauseH) {
        this.model.pauseGame();
      }
      
      else if (restartXL <= x && x <= restartXL + restartW && restartYT <= y && y <= restartYT + restartH) {
        this.model.restartGame();
      }
      
      else if (quitXL <= x && x <= quitXL + quitW && quitYT <= y && y <= quitYT + quitH) {
        this.model.goToHomeScreen();
      }
    }

    else if (this.viewModel.getGameState() == GameState.ACTIVE) {
      int pauseXL = view.getWidth() - (view.getWidth() / viewModel.getDimension().cols()) - 10;
      int pauseYT = view.getHeight() / viewModel.getDimension().rows();
      int pauseW = view.getWidth() / viewModel.getDimension().cols();
      int pauseH = view.getHeight() / viewModel.getDimension().rows();

      if (pauseXL <= x && x <= pauseXL + pauseW && pauseYT <= y && y <= pauseYT + pauseH) {
        this.model.pauseGame();
      }
    }

    else if (this.viewModel.getGameState() == GameState.GAMEOVER) {
      float restartXL = (view.getWidth()/2)-100;
      float restartYT = (view.getHeight()/2)+150;
      float restartW = (view.getWidth() / viewModel.getDimension().cols())+150;
      float restartH = view.getHeight() / viewModel.getDimension().rows();

      float quitXL = (view.getWidth()/2)-100;
      float quitYT = (view.getHeight()/2)+200;
      float quitW = (view.getWidth() / viewModel.getDimension().cols())+150;
      float quitH = view.getHeight() / viewModel.getDimension().rows();

      if (restartXL <= x && x <= restartXL + restartW && restartYT <= y && y <= restartYT + restartH) {
        this.model.restartGame();
      }
      
      else if (quitXL <= x && x <= quitXL + quitW && quitYT <= y && y <= quitYT + quitH) {
        this.model.goToHomeScreen();
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {} 

  @Override
  public void mouseExited(MouseEvent e) {}  

}