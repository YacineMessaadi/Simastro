package Controleur;

public class Timer {

	private double lastLoopTime;

	public void init() {
		lastLoopTime = System.nanoTime() / 1000_000_000.0;
	}

	public float getEllapsedTime() {
		double time = System.nanoTime() / 1000_000_000.0;
		float ellapsedTime = (float) (time - lastLoopTime);
		lastLoopTime = time;
		return ellapsedTime;
	}

	public double getLastLoopTime() {
		return lastLoopTime;
	}
}
