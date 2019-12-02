package Controleur;

import Modele.Methode;
import Modele.Objets.Objet;
import Modele.Objets.Systeme;
import javafx.application.Platform;

public class ThreadTrajectoire {

	private static final int FPS = 60;
	public static double G;
	public static double fa;

	private Systeme s;
	private Thread renderThread;
	private double rayon;

	private Timer timer;

	public void calculTrajectoire() {
		this.renderThread = new Thread(new Runnable() {
			float tempsEcoule;
			float cpt = 0f;

			@Override
			public void run() {
				timer.init();
				if (s.methode == Methode.LF)
					for (Objet o : s.getSatellites())
						o.demiPasLF(s);
				while (true) {
					tempsEcoule = timer.getEllapsedTime();
					cpt += tempsEcoule;

					while (s.getRunning() && cpt >= s.getdT()) {

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								for (Objet o : s.getSatellites()) {
									o.calculTrajectoire(s);
								}
							}
						});
						cpt -= s.getdT() / fa;
					}
					threadSleep();
				}

			}
		});
		renderThread.setDaemon(true);
		renderThread.start();
	}

	public ThreadTrajectoire(Systeme space) {
		this.s = space;
		this.timer = new Timer();
		rayon = s.getRayon();
		ThreadTrajectoire.fa = s.getfA();
		ThreadTrajectoire.G = s.getGravite();
	}

	public Systeme getSystem() {
		return s;
	}

	public void threadSleep() {
		double endTime = timer.getLastLoopTime() + 1f / FPS;
		while (System.nanoTime() / 1000_000_000.0 < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setRayon(int rayon) {
		this.rayon = rayon;
	}

	public double getRayon() {
		return rayon;
	}

}
