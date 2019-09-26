package Objets;

public class Cercle extends Satellite {
	
	private Satellite centre;
	private double vitx;
	private double vity;

	public Cercle(double m, double px, double py, Satellite fo1, double vx, double vy) {
		super(m, px, py);
		centre = fo1;
		vitx = vx;
		vity = vy;	
	}

	public Satellite getcentre() {
		return centre;
	}

	public void setcentre(Satellite centre) {
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
