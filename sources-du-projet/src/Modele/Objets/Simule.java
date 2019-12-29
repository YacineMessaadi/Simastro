package Modele.Objets;

import java.util.LinkedList;

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
		listSize = 10000; 
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
	
	public Vecteur getVit() {
		return new Vecteur(vitx, vity);
	}
	
	public void setVit(Vecteur vit) {
		setVitx(vit.getX());
		setVity(vit.getY());
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

	public Vecteur getAcc() {
		return new Vecteur(fx, fy);
	}
	
	public void setAcc(Vecteur acc) {
		setFx(acc.getX());
		setFy(acc.getY());
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
	
	
}
