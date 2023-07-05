/**
 * @author Andrew Nail
 */
package project;

public class Car {
	private int num;
	private double x;
	private double speed;
	private boolean waiting;

	public Car(int num, double x, double speed) {
		this.num = num;
		this.x = x;
		this.speed = speed;
		waiting = false;
	}

	public void move() {
		if (x > 800) {
			x -= 800;
		}
		x += speed;
	}

	public double getX() {
		return x;
	}

	public boolean isWaiting() {
		return waiting;
	}

	public void setWaiting(boolean b) {
		waiting = b;
	}

	public double getSpeed() {
		if (!waiting && !Frame.isPaused()) {
			return speed;
		} else {
			return 0;
		}
	}

	public int getNum() {
		return num;
	}
}
