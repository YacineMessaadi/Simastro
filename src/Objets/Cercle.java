package Objets;

public class Cercle extends Objet {
	
	private Objet centre;
	private double vitx;
	private double vity;

	public Cercle(double m, double px, double py, Objet fo1, double vx, double vy) {
		super(m, px, py);
		centre = fo1;
		vitx = vx;
		vity = vy;	
	}

	public Objet getcentre() {
		return centre;
	}

	public void setcentre(Objet centre) {
		this.centre = centre;
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
	
	

}
