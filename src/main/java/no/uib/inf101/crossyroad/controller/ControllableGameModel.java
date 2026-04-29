package no.uib.inf101.crossyroad.controller;


public interface ControllableGameModel {

  /**
   * Attempts to move a player by the given row and column 
   * 
   * @param deltaRow how many rows the player is moved by
   * @param deltaCol how many cols the player is moved by
   * @return returns true if the move is successfull, and false if it was unsuccessful, or the gameState is not ACTIVE
   */
  public boolean movePlayer(int deltaRow, int deltaCol);


  /**
   * Sets the gamestate to ACTIVE so that the game can start
   */
  public void startGame();


  /**
   * Returns how fast a timer should update itself
   * @return returns an integer indicating how often the timer should repaint
   */
  public int getTimerDelayCars();

  /**
   * Iterates through a list cars, and each car across the board. 
   * When a car reaches the end of the board, it gets removed from the list
   * Also checks if a car is about to crash with another car. If so that car will not be moved
   * 
   * @param width the width of the screen 
   */
  public void moveCars(int screenWidth, int screenHeight);

  /**
   * Spawns a car on a random highway. The direction and speed depend on the type of road
   * 
   * @param screenWidth width of the screen in pixels
   * @param screeenHeight height of the screen in pixels
   */
  public void spawnCar(int screenWidth, int screeenHeight);
  
  /**
   * Sets the gamestate to PAUSE 
   */
  public void pauseGame();

  /**
   * Sets the gamestate to HOMESCREEN. Will also restart the game
   */
  public void goToHomeScreen();

  /**
   * Restarts the game to its initial state. This includes creating new grids, resetting the player position, clearing all cars,
   * resetting the level to 1, generating new background and obstacles. 
   * After calling this method, the game state is set to ACTIVE.
  */
  public void restartGame(); 
  
}
