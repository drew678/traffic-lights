/**
 * @author Andrew Nail
 */
package project;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class TrafficLightsManager implements Runnable {
	ArrayList<TrafficLight> lights = new ArrayList<TrafficLight>();
	private int numOfLights;

	public TrafficLightsManager(int numOfLights, long countdownGreen, long countdownYellow, long countdownRed) {
		Random rand = new Random();
		this.numOfLights = numOfLights;
		int startingPosition = rand.nextInt(800 - (numOfLights * 100)); // where the lights start generating
		for (int i = 0; i < numOfLights; i++) {
			lights.add(new TrafficLight(startingPosition + i * 100, countdownGreen, countdownYellow, countdownRed));
		}
	}

	public void paint(Graphics g) {
		for (TrafficLight trafficLight : lights) {
			g.setColor(trafficLight.getColor());
			g.fillRect(trafficLight.getX(), 400, 5, 40);
			g.setColor(Color.black);
			g.drawString(trafficLight.getTime(), trafficLight.getX(), 400);
		}
	}

	@Override
	public void run() {

		while (Frame.isStarted()) {
			for (TrafficLight trafficLight : lights) {
				trafficLight.countdown();
			}
		}
	}

	public TrafficLight getLight(int i) {
		return lights.get(i);
	}

	public int getNumOfLights() {
		return numOfLights;
	}
}
