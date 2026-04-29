package no.uib.inf101.crossyroad.controller;


public interface ControllableGameModel {

  /**
   * Moves player by deltarows and deltacols
   * 
   * @param deltaRow how many rows the player is moved by
   * @param deltaCol how many cols the player is moved by
   * @return returns true if the move is allowed, and false otherwise
   */
  public boolean movePlayer(int deltaRow, int deltaCol);

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
   * spawns a new car in the grid on a randomly selected highway
   */
  public void spawnCar(int screenWidth, int screeenHeight);

  public void pauseGame();

  public void goToHomeScreen();

  public void restartGame(); 
  
}
