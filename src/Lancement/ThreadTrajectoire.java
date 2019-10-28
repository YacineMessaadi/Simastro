package Lancement;

import Modele.Objets.Objet;
import Modele.Objets.Simule;
import Modele.Objets.Systeme;
import Modele.Objets.Vaisseau;
import javafx.application.Platform;

public class ThreadTrajectoire {

	private static final int FPS = 30;
	public static double dt;
	public static double G;
	public static double fa; 
	
	private Systeme s;
	private Thread renderThread;

	private boolean isRunning = false;
	private double rayon;

	private Timer timer;

	public void calculTrajectoire() {
		this.renderThread = new Thread(new Runnable() {
			float tempsEcoule;
			float cpt = 0f;
			
			@Override
			public void run() {
				timer.init();
				while(true) {
					tempsEcoule = timer.getEllapsedTime();
					cpt += tempsEcoule;

					while (isRunning && cpt >= dt) {
						
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
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
												double force = s.getGravite() * ((o.getMasse() * o1.getMasse()) / (distance * distance));
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
							}
						});
						cpt -= dt/fa;
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
		isRunning = true;
		rayon = s.getRayon();
		ThreadTrajectoire.dt = s.getdT();
		ThreadTrajectoire.fa = s.getfA();
		ThreadTrajectoire.G = s.getGravite();
	}

	public Systeme getModel() {
		return s;
	}

	public void threadSleep() {
		double endTime = timer.getLastLoopTime() + 1f/FPS;
		while (timer.getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRayon(int rayon) {
		this.rayon = rayon;
	}

	public double getRayon() {
		return rayon;
	}



}
