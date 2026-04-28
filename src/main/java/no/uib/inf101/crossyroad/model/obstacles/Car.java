package no.uib.inf101.crossyroad.model.obstacles;


public class Car {
  private int x;
  private int y;
  private int speedX; 
  private int cols;
  private int rows; 
  private int screenWidth;
  private int screenHeight; 


  public Car(int y, int speedX, int screenWidth, int screenHeight, int rows, int cols) {
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.cols = cols; 
    this.rows = rows; 
    this.y = y; 
    this.speedX = speedX; 

    if (this.speedX > 0) {
      this.x = -screenWidth/cols;
    }
    else {
      this.x = screenWidth;
    }
  }

  /**
   * 
   * updates the x-value, making the car move forward with the given speed
   */
  public void move() {
    x += speedX; 
  }

  /**
   * 
   * @return the left x-coordinate of the car
   */
  public int getX() {
    return this.x; 
  }

  /***
   * 
   * @return the top y-coordinate of the car
   */
  public int getY() {
    return this.y; 
  }

  public int getSpeed() {
    return this.speedX;
  }

  /**
   * 
   * @return the width of the car
   */
  public int getWidth(){
    return this.screenWidth / this.cols;
  }

  /***
   * 
   * @return the height of the car
   */
  public int getHeight() {
    return this.screenHeight / this.rows; 
  }

  /**
   * checks if a car has moved off the screen so that it can be removed
   * 
   * @param screenWidth the screenwidth of the frame where the game is drawn
   * @return true if the car is offscreen, false otherwise
   */
  public boolean isOffScreen(int screenWidth) {
    if (x >= screenWidth || x < -this.getWidth()) {
      return true; 
    }
    return false;
  }

  
}
