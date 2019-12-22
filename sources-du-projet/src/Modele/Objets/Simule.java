package Modele.Objets;

import java.util.LinkedList;

import Modele.Methode;

public class Simule extends Objet {

	private double vitx;
	private double vity;
	private double fx;
	private double fy;
	private LinkedList<Position> trail;
	private int listSize;
	private Double[] trailColor;

	public Simule(String nom, double m, double px, double py, double vx, double vy) {
		super(nom, m, px, py);
		vitx = vx;
		vity = vy;
		trail = new LinkedList<Position>();
		listSize = 200;
		trailColor = new Double[] { Math.random(), Math.random(), Math.random() };
	}
	
	public Simule(double m, double px, double py, double vx, double vy) {
		super("", m, px, py);
		vitx = vx;
		vity = vy;
		trail = new LinkedList<Position>();
		listSize = 100;
		trailColor = new Double[] {1.0,1.0,1.0};
	}

	public double getVitx() {
		return vitx;
	}

	public void setVitx(double vitx) {
		this.vitx = vitx;
	}

	public double getVity() {
		return vity;
	}

	public void setVity(double vity) {
		this.vity = vity;
	}
	
	public double getFx() {
		return fx;
	}

	public void setFx(double fx) {
		this.fx = fx;
	}

	public double getFy() {
		return fy;
	}

	public void setFy(double fy) {
		this.fy = fy;
	}

	public int getListSize() {
		return listSize;
	}

	public LinkedList<Position> getTrail() {
		return trail;
	}

	public Double[] getTrailColor() {
		return trailColor;
	}

	@Override
	public void calculTrajectoire(Systeme s) {
		if (s.methode == Methode.EE) {
			double xTotal = 0;
			double yTotal = 0;
			for (Objet o1 : s.getSatellites()) {
				if (this != o1) {
					double distX = o1.getPosx() - getPosx();
					double distY = o1.getPosy() - getPosy();
					double distance = Math.sqrt(distX * distX + distY * distY);
					double angle = Math.atan2(distY, distX);
					double force = s.getGravite() * ((getMasse() * o1.getMasse()) / (distance * distance));
					xTotal += (Math.cos(angle) * force);
					yTotal += (Math.sin(angle) * force);
					
				}
			}

			setPosx(getPosx() + getVitx());
			setPosy(getPosy() + getVity());
			setVitx(getVitx() + xTotal);
			setVity(getVity() + yTotal);
		}
		
		else if (s.methode == Methode.LF) {
			double xTotal = 0;
			double yTotal = 0;
			for (Objet o1 : s.getSatellites()) {
				if (this != o1) {
					double distX = o1.getPosx() - getPosx();
					double distY = o1.getPosy() - getPosy();
					double distance = Math.sqrt(distX * distX + distY * distY);
					double angle = Math.atan2(distY, distX);
					double force = s.getGravite() * ((getMasse() * o1.getMasse()) / (distance * distance));
					xTotal += (Math.cos(angle) * force);
					yTotal += (Math.sin(angle) * force);
				}
			}
			
			setPosx(getPosx() + getVitx());
			setPosy(getPosy() + getVity());
			
			setVitx(getVitx() + xTotal);
			setVity(getVity() + yTotal);
		
			
		}
	}
	
	public void demiPasLF(Systeme s) {
		double xTotal = 0;
		double yTotal = 0;
		for (Objet o1 : s.getSatellites()) {
			if (this != o1) {
				double distX = o1.getPosx() - getPosx();
				double distY = o1.getPosy() - getPosy();
				double distance = Math.sqrt(distX * distX + distY * distY);
				double angle = Math.atan2(distY, distX);
				double force = s.getGravite() * ((getMasse() * o1.getMasse()) / (distance * distance));
				xTotal += (Math.cos(angle) * force);
				yTotal += (Math.sin(angle) * force);
			}
		}
		//M'
		setVitx(getVitx() + 0.5*xTotal);
		setVity(getVity() + 0.5*yTotal);
	}
	
	
}
