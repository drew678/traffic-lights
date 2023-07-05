/**
 * @author Andrew Nail
 */
package project;

import java.awt.Graphics;

public class StopWatch implements Runnable {
	private long start;
	private long time;
	private long wait;
	private boolean hold;
	private long holdTime;
	private long deltaHold;

	public StopWatch() {
		start = System.currentTimeMillis();
		wait = 0;
		hold = true;
		deltaHold = 0;
	}

	@Override
	public void run() {
		System.out.println();
		System.out.println();
		while (Frame.isStarted()) {
			long now = System.currentTimeMillis();
			System.out.println();
			if(!Frame.isPaused()) {
				if(!hold) {
					deltaHold += wait;
				}
				hold = true;
				time = (now - start)-deltaHold;
			}else {
				if(hold) {
					holdTime = System.currentTimeMillis();
					hold = false;
				}
				wait = now - holdTime;
			}
		}
	}

	public void paint(Graphics g) {
		String s = String.valueOf(time / 10);
		if(time/10 > 100) {
			s = s.substring(0, s.length() - 2) + ":" + s.substring(s.length() - 2, s.length());
		}
		g.drawString(s, 400, 40);
	}
}
