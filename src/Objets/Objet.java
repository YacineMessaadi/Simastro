package Objets;

public abstract class Objet{
	
	private double masse;
	private double posx;
	private double posy;
	
	public Objet(double m, double px, double py) {
		masse = m;
		posx = px;
		posy = py;
	}

	public double getMasse() {
		return masse;
	}

	public void setMasse(double masse) {
		this.masse = masse;
	}

	public double getPosx() {
		return posx;
	}

	public void setPosx(double posx) {
		this.posx = posx;
	}

	public double getPosy() {
		return posy;
	}

	public void setPosy(double posy) {
		this.posy = posy;
	}
	
	

}
