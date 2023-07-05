/**
 * @author Andrew Nail
 */
package project;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class CarManager implements Runnable {
	ArrayList<Car> cars = new ArrayList<Car>();
	TrafficLightsManager lights;

	public CarManager(int numberOfCars, double speedOfCars, TrafficLightsManager lights) {
		Random rand = new Random();
		for (int i = 0; i < numberOfCars; i++) {
			cars.add(new Car(i, 20 * rand.nextInt(40), speedOfCars));
		}
		this.lights = lights;
	}

	@Override
	public void run() {
		while (Frame.isStarted()) {
			System.out.println();
			if (!Frame.isPaused()) {
				for (Car car : cars) {
					car.setWaiting(false); // all cars should move this frame
				}
				for (Car carOne : cars) {
					for (Car carTwo : cars) {
						if (carOne.getNum() != carTwo.getNum() && carOne.getX() + 15 > carTwo.getX() - 15
								&& carOne.getX() + 15 < carTwo.getX() + 15) { // car1 front is ahead of car2 back area
																				// but not ahead of car2
							carOne.setWaiting(true);
						}
					}
				}
				for (Car car : cars) {
					for (int i = 0; i < lights.getNumOfLights(); i++) { // same thing as above but for traffic lights
						if (lights.getLight(i).getColor() == Color.RED) {
							if (car.getX() + 15 > lights.getLight(i).getX() - 15
									&& car.getX() + 15 < lights.getLight(i).getX() + 15) { // car1 front is ahead of
																							// car2 back area but not
																							// ahead of car2
								car.setWaiting(true);
							}
						}
					}
				}
				for (Car car : cars) { // cars that move move
					if (!car.isWaiting()) {
						car.move();
					}
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				;
			}

		}
	}

	public void paint(Graphics g) {
		for (Car car : cars) {
			g.fillRect((int) car.getX(), 400, 20, 20);
			g.drawString("car-" + car.getNum() + " x:" + car.getX() + " " + car.getSpeed() + " p/s", (int) car.getX(),
					450);
		}
	}

}
