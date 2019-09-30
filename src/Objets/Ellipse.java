package Objets;

/**
 * 
 * @author lantoing, dautrich, messaady, tryoeny
 *
 */

public class Ellipse extends Objet {

	private double vitx;
	private double vity;
	private Fixe f1;
	private Fixe f2;
	
	/**
	 * Ellipse
	 * Constructeur d'un objet Ellispe avec attributs spécifiés
	 * @param m
	 * @param px
	 * @param py
	 * @param vx
	 * @param vy
	 * @param fixe1
	 * @param fixe2
	 */
	public Ellipse(double m, double px, double py, double vx, double vy, Fixe fixe1, Fixe fixe2){
		super(m, px, py);
		vitx = vx;
		vity = vy;
		f1 = fixe1;
		f2 = fixe2;
	}
	
	/**
	 * 
	 * @return
	 */
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

	public Fixe getF1() {
		return f1;
	}

	public void setF1(Fixe f1) {
		this.f1 = f1;
	}

	public Fixe getF2() {
		return f2;
	}

	public void setF2(Fixe f2) {
		this.f2 = f2;
	}



}
