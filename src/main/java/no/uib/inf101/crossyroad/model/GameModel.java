package no.uib.inf101.crossyroad.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import no.uib.inf101.crossyroad.controller.ControllableGameModel;
import no.uib.inf101.crossyroad.model.obstacles.Car;
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

  private Board board; 
  private Board background; 
  private final Random random = new Random();
  private int level; 
  private int obstaclesToSpawn;

  /**
   * The constructor for the GameModel
   * @param grid initiates a gamemodel given a grid
   */
  public GameModel(IGrid grid, IGrid backgroundGrid) {
    this.grid = grid;
    this.backgroundGrid = backgroundGrid;

    this.board = new Board(grid);
    this.background = new Board(this.backgroundGrid);

    this.player = new Player(new CellPosition(grid.rows()-1, (int)(grid.cols()/2)), 'r', this.board);
    this.cars = new ArrayList<>();
    this.gameState = GameState.HOMESCREEN;
    this.level = 1; 
    this.obstaclesToSpawn = 20; 

    this.grid.set(player.getCellPosition(), player.getChar());
    new RandomRoadFactory(backgroundGrid);
    spawnObstacles(obstaclesToSpawn);     
  }

  @Override
  public void startGame() {
    this.gameState = GameState.ACTIVE; 
  }

/**
 * A method which resets the board. It will generate a new randomly generated board. 
 */
  private void initializeBoard() {
    int rows = grid.rows();
    int cols = grid.cols();
    
    this.grid = new Grid(rows, cols, ' ');
    this.backgroundGrid = new Grid(rows, cols, ' ');
    this.board = new Board(this.grid);
    this.background = new Board(this.backgroundGrid);
    
    this.player = new Player(new CellPosition(rows - 1, cols / 2), 'r', this.board);
    this.cars.clear();
    this.grid.set(getPlayerPosition(), player.getChar());
    
    new RandomRoadFactory(backgroundGrid);
}

  @Override
  public void restartGame() {
    this.level = 1;  
    this.obstaclesToSpawn = 20; 

    initializeBoard();

    spawnObstacles(this.obstaclesToSpawn);
    this.gameState = GameState.ACTIVE;
  }

  /**
   * A method to create a new board, background, cars and obstacles when a 
   * player reaches the other side
   */
  private void newRound() {
    this.level++; 

    initializeBoard();

    if (obstaclesToSpawn <= 32) {
      obstaclesToSpawn += 1; 
    }
    spawnObstacles(obstaclesToSpawn);
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

  @Override
  public void pauseGame() {
    if (this.gameState == GameState.ACTIVE) {
      this.gameState = GameState.PAUSE;
    } else {
      this.gameState = GameState.ACTIVE;
    }
  }

  @Override
  public void goToHomeScreen() {
    this.restartGame();
    gameState = GameState.HOMESCREEN; 
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

  /**
   * 
   * @return the players current cellposition
   */
  public CellPosition getPlayerPosition(){
    return this.player.getCellPosition();
  }

  /**
   * 
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

  @Override
  public int getLevel() {
    return this.level; 
  }

  /**
   * Spawns the stillstanding obstacles on a grass road
   * by setting the symbol on the grid for that obstacle
   * It will also check if there are already too many obstacles in the road, 
   * if so it will not spawn any more obstacles
   * 
   * @param quantity how many obstacles to spawn
   * @throws IllegalArgumentException if an object has an invalid character
   */
  private void spawnObstacles(int quantity) {
    int count = 0; 
    int maxTries = 1000; 
    int tries = 0; 

    while (count <= quantity && tries < maxTries) {
      tries ++; 
      int randRow = random.nextInt(grid.rows() -2) + 1; 
      int randCol = random.nextInt(grid.cols()-2) + 1;

      CellPosition posObs = new CellPosition(randRow, randCol);

      if (backgroundGrid.get(posObs) == 'g' && grid.get(posObs) == ' ') {
        int countObsInRow = 0; 

        for (int col = 0; col < grid.cols(); col++) {
          Character chr = grid.get(new CellPosition(randRow, col));
          if (chr == 'd' || chr == 'b') {
          countObsInRow++;
          }
        }
        if (countObsInRow < grid.cols() - 2) {
          Obstacle obs = new Obstacle(posObs);
          char o = obs.getChar();
          if (o == ' '){
          throw new IllegalArgumentException("Character for an obstacle can not be empty.");
          }
          grid.set(posObs, o);
          count++;
        }
      }
    }
  } 

  @Override
  public int getTimerDelayCars() {
    return 10;
  }

  @Override
  public void moveCars(int screenWidth, int screenHeight) {
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
      if(checkCrash(car, screenWidth, screenHeight)) {
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

    int minSpeed = level; 
    if (level >= 6) {
      minSpeed = 6; 
    }
    int speedX = random.nextInt(8 - minSpeed + 1) + minSpeed;
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
   * Checks whether a given car has collided with the player
   * 
   * @param car the car to check collision with
   * @param screenWidth width of the screen in pixels
   * @param screenHeight height of the screen in pixels
   * @return true if the car overlaps with the player, false otherwise
   */
  private boolean checkCrash(Car car, int screenWidth, int screenHeight) {
    int cellWidth = screenWidth / board.cols(); 
    int cellHeight = screenHeight / board.rows(); 
    
    int carXL = car.getX();
    int carXR = carXL + cellWidth; 
    int carYT = car.getY();
    int carYB = carYT + cellHeight; 

    int playerCol = player.getCellPosition().col(); 
    int playerRow = player.getCellPosition().row(); 
    int playerXL = playerCol * cellWidth;
    int playerXR = playerXL + cellWidth;
    int playerYT = playerRow * cellHeight;
    int playerYB = playerYT + cellHeight;

    

    if (carXR < playerXL + cellWidth /2 || ((playerXR) - cellWidth/2) < carXL){
      return false; 
    }
    else if (carYT > (playerYB - cellHeight/2) || (playerYT + cellHeight/2) > carYB) {
      return false; 
    }
    return true; 
  }



}


