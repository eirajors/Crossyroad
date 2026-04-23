package no.uib.inf101.crossyroad.model.obstacles;


public class Car {
  private int x; 
  private int y;
  private int speedX; 


  public Car(int startX, int y, int speedX) {
    this.x = startX;
    this.y = y; 
    this.speedX = speedX; 
  }

  public boolean move() {
    x += speedX; 

    return true; 
  }

  public int getX() {
    return x; 
  }

  public int getY() {
    return y; 
  }

  public boolean isOffScreen(int screenWidth) {
    if (x > screenWidth || x < -50) {
      return true; 
    }
    return false;
  }

  
}
