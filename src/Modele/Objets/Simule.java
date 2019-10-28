package Modele.Objets;

import javafx.geometry.Pos;

import java.util.LinkedList;
import java.util.List;

public class Simule extends Objet {

	private double vitx;
	private double vity;
	private LinkedList<Position> trail;
	private int listSize;
	
	public Simule(String nom,double m, double px, double py, double vx, double vy){
		super(nom,m, px, py);
		vitx = vx;
		vity = vy;
		trail = new LinkedList<Position>();
		listSize = 100000;
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
}
