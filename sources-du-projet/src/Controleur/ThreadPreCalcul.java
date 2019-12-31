package Controleur;

import java.util.TimerTask;
import java.util.Timer;
import Modele.Objets.Systeme;

public class ThreadPreCalcul {

	private Systeme s;
	private Thread renderThread;

	public void preCalcul() {

		this.renderThread = new Thread(new Runnable() {
			@Override
			public void run() {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						if (s.getRunning() && s.getVaisseau() != null)
							s.methode.preCalcul(s);
					}
				}, (long) ((s.getdT() * 4000) / s.getfA()), 1);
			}
		});
		renderThread.setDaemon(true);
		renderThread.start();
	}

	public ThreadPreCalcul(Systeme space) {
		this.s = space;
	}

	public Systeme getSystem() {
		return s;
	}

}
