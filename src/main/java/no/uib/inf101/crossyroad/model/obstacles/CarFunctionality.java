package no.uib.inf101.crossyroad.model.obstacles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import no.uib.inf101.crossyroad.model.Player;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.IGrid;

public class CarFunctionality {

  private IGrid backgroundGrid; 
  private List<Car> cars; 
  private JFrame frame;
  private int screenWidth;
  private int screenHeight;
  private int rows; 
  private int cols; 
  private Player player; 
  private Random random; 

  public CarFunctionality(IGrid backgroundGrid, List<Car> cars, JFrame frame, int rows, int cols, Player player) {
    this.backgroundGrid = backgroundGrid; 
    this.cars = cars;
    this.frame = frame; 
    this.screenWidth = this.frame.getWidth();
    this.screenHeight = this.frame.getHeight();
    this.rows = rows; 
    this.cols = cols; 
    this.player = player; 
    this.random = new Random();

  }

  public boolean moveCars(int screenWidth) {
    List<Car> toRemove = new ArrayList<>();

    for (Car car : this.cars) {
      boolean crash = false; 

      for (Car car2 : cars) {
        if (car == car2) continue; 
        if (car.getY() != car2.getY()) continue;
        if (car2.getX() < car.getX()) continue; 
        if (car.getX() + car.getWidth() > car2.getX() - 20) {
          crash = true; 
          break;
        }
      }

      if (!crash) {
        car.move();
      }

      if (car.isOffScreen(screenWidth)) {
        toRemove.add(car);
      }

      if (checkCrash(car)) {
        return false; 
      }
    }
    cars.removeAll(toRemove);
    return true; 
  }


  public void spawnACar(int screenWidth, int screenHeight) {
    int cellHeight = screenHeight / this.rows; 

    boolean found = false; 
    int randomHighway = 0; 

    // hvis koordinatet i den cellen er highway kan man spawne bil
    // bilens sentrum er kolonner * cellebredde - eller + vet ikke 0.5 cellebredde 
    while (!found){
      randomHighway = random.nextInt((rows));
      if ((this.backgroundGrid).get(new CellPosition(randomHighway, 0)) == 'e') {
        found = true;
      }
    }
    int y = randomHighway * cellHeight; 

    int speedX = random.nextInt(8 - 2 + 1) + 2;

    cars.add(new Car(y, speedX, this.screenWidth, this.screenHeight, this.rows, this.cols));

  }

  /***
   * Method to check if a car crashes with the player
   * 
   * @param car the car to check for crashing with the player
   * @return true if it crashes with the player, false otherwise
   */
  private boolean checkCrash(Car car) {

    int carWidth = screenWidth / cols; 
    int carHeight = screenHeight / rows; 
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
