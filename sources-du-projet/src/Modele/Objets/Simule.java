package Modele.Objets;


import java.util.LinkedList;

public class Simule extends Objet {

	private double vitx;
	private double vity;
	private LinkedList<Position> trail;
	private int listSize;
	private Double[] trailColor;
	
	public Simule(String nom,double m, double px, double py, double vx, double vy){
		super(nom,m, px, py);
		vitx = vx;
		vity = vy;
		trail = new LinkedList<Position>();
		listSize = 100000;
		trailColor = new Double[] {Math.random(), Math.random(), Math.random()};
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

	public int getListSize(){ return listSize;}

	public LinkedList<Position> getTrail(){return trail;}
	
	public Double[] getTrailColor() {
		return trailColor;
	}
}
