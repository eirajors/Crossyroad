package no.uib.inf101.crossyroad.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import no.uib.inf101.crossyroad.controller.ControllableGameModel;
import no.uib.inf101.crossyroad.model.obstacles.Car;
import no.uib.inf101.crossyroad.model.obstacles.Log;
import no.uib.inf101.crossyroad.view.ViewableGameModel;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;

public class GameModel implements ControllableGameModel, ViewableGameModel {

  private GameState gameState; 
  private IGrid grid;
  private IGrid backgroundGrid;
  private Player player;
  private List<Car> cars;

  private final Board board; 
  private final Board background; 
  private Random random = new Random();
  private JFrame frame; 

  /**
   * The constructor for the GameModel
   * @param grid initiates a gamemodel given a grid
   */
  public GameModel(IGrid grid, IGrid backgroundGrid, JFrame frame) {
    this.grid = grid;
    this.backgroundGrid = backgroundGrid;
    this.frame = frame; 

    this.board = new Board(grid);
    this.background = new Board(this.backgroundGrid);

    this.player = new Player(new CellPosition(grid.rows()-1, 7), 'r', this.board, this.frame);
    this.cars = new ArrayList<>();
    this.gameState = GameState.ACTIVE;

    this.grid.set(player.getCellPosition(), player.getChar());
    new RoadFactory(backgroundGrid);
    spawnObstacles(20);
        
  }

  @Override
  public boolean movePlayer (int deltaRow, int deltaCol) {
    Player candidatePlayer = this.player.shiftPlayer(deltaRow, deltaCol);

    if (gameState != GameState.ACTIVE) {
      return false; 
    }

    if (!this.isLegalPosition(candidatePlayer)) {
      return false; 
    }
    grid.set(this.player.getCellPosition(), ' ');
    
    this.player = candidatePlayer; 
    grid.set(player.getCellPosition(), player.getChar());

    if (isWon(this.player)) {
      gameState = GameState.WON; 
    }
    
    return true; 
  }

  /**
   * 
   * @param player
   * @return
   */
  private boolean isWon(Player player) {
    if (player.getCellPosition().row() == 0) {
      return true; 
    }
    return false; 

  }

  @Override
  public GameState getGameState() {
    return gameState; 
  }

  /**
   * Checks if the position the player wants to be moved to is available/legal
   * 
   * @param candidatePlayer the position where the player might be moved to 
   * @return returns true if the move is legal, and false otherwise 
   */
  private boolean isLegalPosition (Player candidatePlayer) {
    if (!this.board.positionIsOnGrid(candidatePlayer.getCellPosition())) {
      return false; 
    }
    if (!this.board.isFree(candidatePlayer.getCellPosition())) {
      return false; 
    }
    return true; 
  }

  /**
   * 
   * @return returns the current cellposition of the player
   */
  public CellPosition getPlayerPosition(){
    return this.player.getCellPosition();
  }

  /**
   * @return returns the player
   */
  public Player getPlayer() {
    return this.player; 
  }

  @Override
  public GridDimension getDimension() {
    return this.board;
  }

  @Override
  public Iterable<GridCell> getTiles() {
    return this.board;
  }

  @Override
  public Iterable<GridCell> getBackgroundTiles() {
    return this.background; 
  }

  /**
   * spawns the obstacles on the road (that are not able to move) 
   * by setting the symbol on the grid for that obstacle
   * 
   * @param quantity how many obstacles to spawn
   */
  private void spawnObstacles(int quantity) {
    int count = 0; 
    for (count = 0; count <= quantity;) {
      int randRow = random.nextInt(grid.rows() -2) + 1; 
      int randCol = random.nextInt(grid.cols()-2) + 1;

      CellPosition posObs = new CellPosition(randRow, randCol);
      if (backgroundGrid.get(posObs) == 'g') {
        Log obs = new Log(posObs);
        grid.set(posObs, obs.getChar());
        count ++; 
      }
    }
  } 


  @Override
  public int getTimerDelayCars() {
    return 10;
  }

  @Override
  public void moveCars(int screenWidth) {
    List<Car> toRemove = new ArrayList<>();
    for (Car car : cars) {
      car.move();
      //sjekke kræsj pr bil
      if(checkCrash(car)) {
        gameState = GameState.WON;
      }
      if (car.isOffScreen(screenWidth)) {
        toRemove.add(car);
        }
    }
    cars.removeAll(toRemove);
    
  }

  @Override
  public void spawnCar(int screenWidth, int screenHeight) {
    int cellHeight = (screenHeight) / board.rows(); 

    boolean found = false; 
    int randomHighway = 0; 

    // hvis koordinatet i den cellen er highway kan man spawne bil
    // bilens sentrum er kolonner * cellebredde - eller + vet ikke 0.5 cellebredde 
    while (!found){
      randomHighway = random.nextInt((board.rows()));
      if (backgroundGrid.get(new CellPosition(randomHighway, 0)) == 'e') {
        found = true;
      }
    }
    int y = randomHighway * cellHeight; 

    int speedX = random.nextInt(8 - 2 + 1) + 2;

    cars.add(new Car(-40, y, speedX));
  }

  @Override
  public List<Car> getCars() {
    return cars;
  }

  /***
   * Method to check if a car crashes with the player
   * 
   * @param car the car to check for crashing with the player
   * @return true if it crashes with the player, false otherwise
   */
  private boolean checkCrash(Car car) {
    int carWidth = frame.getWidth() / board.cols(); 
    int carHeight = frame.getHeight() / board.rows(); 
    int carXR = car.getX() + carWidth; 
    int carXL = car.getX(); 
    int carYB = car.getY() + carHeight; 
    int carYT = car.getY();

    if (carXR < player.getPlayerLeftX() + player.playerWidth() /2 || (player.getPlayerRightX() - player.playerWidth()/2) < carXL){
      return false; 
    }
    else if (carYT > (player.getPlayerBottomY() - player.playerHeight()/2) || (player.getPlayerTopY() + player.playerHeight()/2) > carYB) {
      return false; 
    }
    return true; 
  }
}


