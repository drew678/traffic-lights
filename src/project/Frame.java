/**
 * @author Andrew Nail
 */
package project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Frame {
	private static JPanel buttonPanel, timerPanel;
	private static JButton startButton, pauseButton;

	private static boolean start;
	private static boolean pause;

	public static boolean isPaused() {
		return pause;
	}

	public static boolean isStarted() {
		return start;
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();

			}
		});
	}

	private static void createAndShowGUI() {
		start = false;
		pause = false;

		SwingUtilities.isEventDispatchThread();
		JFrame f = new JFrame("Traffic Simulation");

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(BorderLayout.CENTER, new MyPanel());
		buttonPanel = new JPanel();
		timerPanel = new JPanel();

		startButton = new JButton("start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == startButton) {
					if (start) {
						start = false;
						startButton.setText("start");
					} else {
						start = true;
						startButton.setText("stop");
					}
				}
			}
		});

		pauseButton = new JButton("pause");
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == pauseButton) {
					if (pause) {
						pause = false;
						pauseButton.setText("pause");
					} else {
						pause = true;
						pauseButton.setText("continue");
					}
				}
			}
		});
		buttonPanel.add(startButton);
		buttonPanel.add(pauseButton);
		f.add(BorderLayout.SOUTH, buttonPanel);
		f.add(BorderLayout.NORTH, timerPanel);

		f.setSize(800, 600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation((screen.width - 800) / 2, (screen.height - 600) / 2);
		f.setResizable(false);
		f.setVisible(true);

	}
}

class MyPanel extends JPanel implements ActionListener, Runnable {
	CarManager car;
	TrafficLightsManager trafficLights;
	StopWatch watch;
	private boolean alreadyStarted;

	public MyPanel() {
		Thread t = new Thread(this);
		t.start();
	}

	public void setup() {

		trafficLights = new TrafficLightsManager(3, 10000, 2000, 12000);
		Thread t1 = new Thread(trafficLights);
		t1.start();
		car = new CarManager(5, 1, trafficLights);
		Thread t2 = new Thread(car);
		t2.start();
		watch = new StopWatch();
		Thread t3 = new Thread(watch);
		t3.start();
		Timer timer = new Timer(10, this);
		timer.start();
//		System.out.println("the timer is running" + timer.isRunning());
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (Frame.isStarted() && alreadyStarted) {
			car.paint(g);
			watch.paint(g);
			trafficLights.paint(g);
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	@Override
	public void run() {
		alreadyStarted = false;
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (Frame.isStarted() && !alreadyStarted) {
				setup();
				alreadyStarted = true;
			} else if (!Frame.isStarted() && alreadyStarted) {
				alreadyStarted = false;
			}
		}
	}
}