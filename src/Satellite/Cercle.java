package Satellite;

public class Cercle extends Satellite {
	
	private Foyer f1;
	private double vitx;
	private double vity;

	public Cercle(double m, double px, double py, Foyer fo1, double vx, double vy) {
		super(m, px, py);
		f1 = fo1;
		vitx = vx;
		vity = vy;	
	}

	public Foyer getF1() {
		return f1;
	}

	public void setF1(Foyer f1) {
		this.f1 = f1;
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
