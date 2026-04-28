package no.uib.inf101;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.crossyroad.model.obstacles.Car;

public class ObstaclesTest {

  @Test
  void TestMoveCar() {
    Car car = new Car(0, 3, 100, 100, 4, 5);

    int oldX = car.getX();
    int oldY = car.getY();

    car.move();

    int newX = car.getX();
    int newY = car.getY();

    assertFalse(oldX == newX);
    assertTrue(oldX+3 == newX);
    assertTrue(oldY == newY);
  }

  @Test
  void TestGetCarPosition() {
    Car car = new Car(30, 3, 0, 0, 4, 5);

    assertTrue(car.getY() == 30);
    assertTrue(car.getX() == -car.getWidth());
  }

  @Test
  void TestOffScreen() {
    int screenWidth = 100; 
    Car car = new Car(30, 111, screenWidth, 100, 10, 10);
    assertEquals(car.getX(), -10);

    car.move();

    assertTrue(car.isOffScreen(screenWidth));

  }

}
