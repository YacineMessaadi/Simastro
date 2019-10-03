package Lancement;

import Modele.Objets.Objet;
import Modele.Objets.Simule;
import Modele.Objets.Systeme;

public class ThreadTrajectoire implements Runnable {

	private boolean running = true;
	private Systeme s;

	public ThreadTrajectoire(Systeme s) {
		this.s = s;
	}

	public void cancel() {
		this.running = false;
	}

	@Override
	public void run() {
		while (running) {
			try {
				for (Objet o : s.getSatellites()) {
					if (o instanceof Simule) {
						o.setPosx(o.getPosx() + ((Simule) o).getVitx());
						o.setPosy(o.getPosy() + ((Simule) o).getVity());
						System.out.println("Modif");
					}
				}
				Thread.sleep((long) (s.getdT() * 1000));
			} catch (InterruptedException ex) {
				ex.printStackTrace();
				cancel();
			}
		}		
	}

	/*
	 * Pour lancer le thread
	 * 
	 * 
	 * public static void main(String[] args) { ThreadTrajectoire T = new
	 * ThreadTrajectoire(); T.start(); }
	 */

}
