package no.uib.inf101.model.obstacles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.crossyroad.model.obstacles.Car;

public class CarTest {

@Test
void TestGetCarPosition() {
    // screenWidth=100, cols=5 -> width = 20. 
    // Siden speedX er 3 (>0), vil x bli satt til -20 i konstruktøren.
    Car car = new Car(30, 3, 100, 100, 4, 5);

    assertEquals(30, car.getY());
    assertEquals(-car.getWidth(), car.getX()); 
}

@Test
  void TestMoveCarRight() {
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
  void TestMoveCarLeft() {
    Car car = new Car(0, -3, 100, 100, 4, 5);

    int oldX = car.getX();
    int oldY = car.getY();

    car.move();

    int newX = car.getX();
    int newY = car.getY();

    assertFalse(oldX == newX);
    assertTrue(oldX-3 == newX);
    assertTrue(oldY == newY);
  }


  @Test
  void TestOffScreen() {
    int screenWidth = 100; 
    Car car = new Car(30, 111, screenWidth, 100, 10, 10);
    assertEquals(car.getX(), -10);
    assertFalse(car.isOffScreen(screenWidth));

    car.move();

    assertTrue(car.isOffScreen(screenWidth));
    

  }
}
