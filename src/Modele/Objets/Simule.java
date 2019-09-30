package Modele.Objets;

public class Simule extends Objet {

	private double vitx;
	private double vity;
	
	public Simule(double m, double px, double py, double vx, double vy){
		super(m, px, py);
		vitx = vx;
		vity = vy;	
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
