package no.uib.inf101.crossyroad.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import no.uib.inf101.crossyroad.controller.ControllableGameModel;
import no.uib.inf101.crossyroad.model.obstacles.Car;
import no.uib.inf101.crossyroad.model.obstacles.CarFunctionality;
import no.uib.inf101.crossyroad.model.obstacles.Obstacle;
import no.uib.inf101.crossyroad.model.roads.RandomRoadFactory;
import no.uib.inf101.crossyroad.view.ViewableGameModel;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;

public class GameModel implements ControllableGameModel, ViewableGameModel {

  private GameState gameState; 
  private IGrid grid;
  private IGrid backgroundGrid;
  private Player player;
  private List<Car> cars;
  //private CarFunctionality carFunctionality; 

  private Board board; 
  private Board background; 
  private Random random = new Random();
  private JFrame frame; 
  private int level; 

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
    this.level = 0; 

    this.grid.set(player.getCellPosition(), player.getChar());
    new RandomRoadFactory(backgroundGrid);
    //this.carFunctionality = new CarFunctionality(backgroundGrid, cars, frame, this.board.rows(), this.board.cols(), player);
    spawnObstacles(20);     
  }

  /**
   * A method to create a new board, background, cars and obstacles when a 
   * player reaches the other side
   */
  private void newRound() {
    this.grid = new Grid(15, 20, ' ');
    this.backgroundGrid = new Grid(15, 20, ' ');
    this.board = new Board(this.grid);
    this.background = new Board(this.backgroundGrid);
    this.player = new Player(new CellPosition(grid.rows()-1, 7), 'r', this.board, this.frame);
    this.level++; 
    this.cars.clear();
    this.grid.set(getPlayerPosition(), player.getChar());
    new RandomRoadFactory(backgroundGrid);
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
      this.newRound(); 
    }
    
    return true; 
  }

  /**
   * Checks if the player has arrived at the other side of the board
   * 
   * @param player checks the position of the player
   * @return returns true if the player is on the other side, and false otherwise
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

  @Override
  public CellPosition getPlayerPosition(){
    return this.player.getCellPosition();
  }

  @Override
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

  @Override
  public int getLevel() {
    return this.level; 
  }

  /**
   * spawns the obstacles on the road (that are not able to move) 
   * by setting the symbol on the grid for that obstacle
   * 
   * @param quantity how many obstacles to spawn
   * @throws char if an object is without a character
   */
  private void spawnObstacles(int quantity) {
    int count = 0; 
    for (count = 0; count <= quantity;) {
      int randRow = random.nextInt(grid.rows() -2) + 1; 
      int randCol = random.nextInt(grid.cols()-2) + 1;

      CellPosition posObs = new CellPosition(randRow, randCol);
      if (backgroundGrid.get(posObs) == 'g') {
        Obstacle obs = new Obstacle(posObs);
        char o = obs.getChar();
        if (o == ' '){
          throw new IllegalArgumentException("Character for an obstacle can not be empty.");
        }
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
      boolean crash = false; 

      for (Car car2 : cars) {
        if (car == car2) continue; 
        if (car.getY() != car2.getY()) continue;
        if (car.getSpeed() > 0) {
          if (car2.getX() < car.getX()) continue; 
          if (car.getX() + car.getWidth() > car2.getX() - 20) {
            crash = true; 
            break;
          }
        }
        else {
          if (car.getX() < car2.getX()) continue;
          if (car.getX() < car2.getX() + car2.getWidth() + 20) {
            crash = true; 
            break;
          }
        }
      }

      if(!crash){
        car.move();
      }
      //sjekke kræsj pr bil
      if(checkCrash(car)) {
        gameState = GameState.GAMEOVER;
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
    Character highwayChar = null; 

    // hvis koordinatet i den cellen er highway kan man spawne bil
    // bilens sentrum er kolonner * cellebredde - eller + vet ikke 0.5 cellebredde 
    while (!found){
      randomHighway = random.nextInt((board.rows()));
      if (backgroundGrid.get(new CellPosition(randomHighway, 0)) == 'e' || backgroundGrid.get(new CellPosition(randomHighway, 0)) == 's' ) {
        highwayChar = backgroundGrid.get(new CellPosition(randomHighway, 0));
        found = true;
      }
    }
    int y = randomHighway * cellHeight; 

    int speedX = random.nextInt(8 - 2 + 1) + 2;
    if (highwayChar.equals('e')){
      
      cars.add(new Car(y, speedX, screenWidth, screenHeight, board.rows(), board.cols()));
    }
    else if (highwayChar.equals('s')) {
      speedX = speedX * -1;
      cars.add(new Car(y, speedX, screenWidth, screenHeight, board.rows(), board.cols()));
    }
    else {
      throw new IllegalArgumentException("The while-loop has not chosen a highway to spawn a car");
    }
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


