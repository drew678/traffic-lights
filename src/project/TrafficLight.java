/**
 * @author Andrew Nail
 */
package project;

import java.awt.Color;

public class TrafficLight {
	private int x;
	private long countdownGreen;
	private long countdownYellow;
	private long countdownRed;
	private long currentTime;
	private Color color;
	private long now;
	private long before;
	private long holdTime;
	private long deltaHold;
	private boolean hold;

	public TrafficLight(int x, long countdownGreen, long countdownYellow, long countdownRed) {
		this.x = x;
		this.countdownGreen = countdownGreen;
		this.countdownYellow = countdownYellow;
		this.countdownRed = countdownRed;
		currentTime = countdownGreen;
		color = Color.GREEN;
		before = 0;
		holdTime = 0;
		deltaHold = 0;
		hold = false;
	}

	public Color getColor() {
		return color;
	}

	public int getX() {
		return x;
	}

	public void countdown() {
		System.out.println();
		if (!Frame.isPaused()) {
			now = System.currentTimeMillis();
			if (hold) {
				before = now;
				hold = false;
			}
			long delta = now - before;
			before = now;
			currentTime -= delta;
			if (currentTime <= 0) {
				if (color == Color.GREEN) {
					currentTime = countdownYellow;
					color = Color.YELLOW;
				} else if (color == color.YELLOW) {
					currentTime = countdownRed;
					color = Color.RED;
				} else if (color == color.RED) {
					currentTime = countdownGreen;
					color = Color.GREEN;
				}
			}
		} else {
			hold = true;
		}

	}

	public String getTime() {
		String s = String.valueOf(currentTime);
		if (currentTime >= 100) {
			s = s.substring(0, s.length() - 2) + ":" + s.substring(s.length() - 2, s.length());
		}
		return s;

	}
}
