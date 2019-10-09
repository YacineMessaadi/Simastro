package Lancement;

import Modele.Objets.Objet;
import Modele.Objets.Simule;
import Modele.Objets.Systeme;
import Modele.Objets.Vaisseau;

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
						double xTotal = 0;
						double yTotal = 0;
						for (Objet o1 : s.getSatellites()) {
							if (o != o1) {
								double distX = o1.getPosx() - o.getPosx();
								double distY = o1.getPosy() - o.getPosy();
								double distance = Math.sqrt(distX * distX + distY * distY);
								double angle = Math.atan2(distY, distX);
								double force = gravitation(o.getMasse(), o1.getMasse(), distance);
								xTotal += (Math.cos(angle) * force);
								yTotal += (Math.sin(angle) * force);

							}
						}

						if (o instanceof Vaisseau) {
							xTotal += ((Vaisseau) o).getPretro();
							yTotal += ((Vaisseau) o).getPprincipal();
							((Vaisseau) o).setPretro(0);
							((Vaisseau) o).setPprincipal(0);
						}

						((Simule) o).setVitx(((Simule) o).getVitx() + xTotal);
						((Simule) o).setVity(((Simule) o).getVity() + yTotal);
						o.setPosx(o.getPosx() + ((Simule) o).getVitx());
						o.setPosy(o.getPosy() + ((Simule) o).getVity());
					}
				}
				Thread.sleep((long) (s.getdT() * 1000*(1/s.getfA())));
			} catch (InterruptedException ex) {
				ex.printStackTrace();
				cancel();
			}
		}
	}

	public double gravitation(double ma, double mb, double distance) {
		return s.getGravite() * ((ma * mb) / (distance * distance));
	}

	/*
	 * Pour lancer le thread
	 * 
	 * 
	 * public static void main(String[] args) { ThreadTrajectoire T = new
	 * ThreadTrajectoire(); T.start(); }
	 */

}
