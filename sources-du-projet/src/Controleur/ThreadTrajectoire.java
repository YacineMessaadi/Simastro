package Controleur;

import java.util.TimerTask;
import java.util.Timer;
import Modele.Objets.Systeme;

public class ThreadTrajectoire {

	private Systeme s;
	private Thread renderThread;

	public void calculTrajectoire() {
		System.out.println(((s.getdT() * 1000) / s.getfA()));
		this.renderThread = new Thread(new Runnable() {
			@Override
			public void run() {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						if (s.getRunning())
							s.methode.calculTrajectoire(s);
					}
				}, (long) ((s.getdT() * 1000) / s.getfA()), 1);
			}
		});
		renderThread.setDaemon(true);
		renderThread.start();
	}

	public ThreadTrajectoire(Systeme space) {
		this.s = space;
	}

	public Systeme getSystem() {
		return s;
	}

}
