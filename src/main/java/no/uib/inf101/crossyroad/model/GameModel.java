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
  private IGrid backgrounGrid;
  private Player player;
  private List<Car> cars;
  //private int PlayerX;
  //private int PlayerY; 
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
    this.backgrounGrid = backgroundGrid;
    this.frame = frame; 

    this.board = new Board(grid);
    this.background = new Board(backgrounGrid);

    this.player = new Player(new CellPosition(grid.rows()-1, 7), 'r', board, this.frame);
    this.cars = new ArrayList<>();
    this.gameState = GameState.ACTIVE;

    grid.set(player.getCellPosition(), player.getChar());
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

  public boolean checkIsFree(CellPosition pos) {
    return this.board.isFree(pos);
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
      if (backgrounGrid.get(posObs) == 'g') {
        Log obs = new Log(posObs);
        grid.set(posObs, obs.getChar());
        count ++; 
      }
    }
  } 


  // private List<Car> spawnCars(int quantity) {
  //   for (int i = 0; i < quantity; i++) {
  //     spawnCar(i);
  //   }
  //   return cars; 
  // }

  @Override
  public int getTimerDelayCars() {
    return 10;
  }

  @Override
  public void moveCars(int screenWidth) {
    for (Car car : cars) {
      car.move();
      //sjekke kræsj pr bil
      if(checkCrash(car)) {
        gameState = GameState.WON;
      }
    }
    
    cars.removeIf(car -> car.isOffScreen(screenWidth));
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
      if (backgrounGrid.get(new CellPosition(randomHighway, 0)) == 'e') {
        found = true;
      }
    }
    int y = randomHighway * cellHeight ; 

    int speedX = random.nextInt(8 - 2 + 1) + 2;

    cars.add(new Car(-40, y, speedX));
  }

  @Override
  public List<Car> getCars() {
    return cars;
  }

  // public Coordinate getPlayerCoordinate() {
  //   int playerX = this.getPlayerPosition().col() + (view.getWidth());
  // }


  // metode sjekke om kræsj

  private boolean checkCrash(Car car) {
    int carWidth = frame.getWidth() / board.cols(); 
    int carHeight = frame.getHeight() / board.rows(); 
    int carXR = car.getX() + carWidth/2; 
    int carXL = car.getX() - carWidth/2; 
    int carYB = car.getY() + carHeight/2; 
    int carYT = car.getY() - carHeight/2;

    if ((carXR >= player.getPlayerLeftX() && carXL <= player.getPlayerRightX()) && (carYB >= player.getPlayerTopY() && carYT <= player.getPlayerBottomY())) {
      return true; 
    }
    return false; 
  }
}


