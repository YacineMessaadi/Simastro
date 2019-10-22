package Modele.Objets;

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
		super("Elipse",m, px, py);
		vitx = vx;
		vity = vy;
		f1 = fixe1;
		f2 = fixe2;
	}
	
	/**
	 * getVitx
	 * Retourne la valeur de l'attribut vitx d'une Ellipse
	 * @return double
	 */
	public double getVitx() {
		return vitx;
	}
	
	/**
	 * setVitx
	 * Attribue la valeur en paramètre à l'attribut vitx
	 * @param vitx
	 */
	public void setVitx(double vitx) {
		this.vitx = vitx;
	}
	
	/**
	 * getVity
	 * Retourne la valeur de l'attribut vity d'une Ellipse
	 * @return double
	 */
	public double getVity() {
		return vity;
	}

	/**
	 * setVity
	 * Attribue la valeur en paramètre à l'attribut vity
	 * @param vity
	 */
	public void setVity(double vity) {
		this.vity = vity;
	}

	/**
	 * getF1
	 * Retourne la valeur de l'attribut f1 d'une Ellipse
	 * @return Fixe
	 */
	public Fixe getF1() {
		return f1;
	}

	/**
	 * setF1
	 * Attribue la valeur en paramètre à l'attribut f1 d'une Ellipse
	 * @param f1
	 */
	public void setF1(Fixe f1) {
		this.f1 = f1;
	}
	
	/**
	 * getF2
	 * Retourne la valeur de l'attribut f2 d'une Ellipse
	 * @return Fixe
	 */
	public Fixe getF2() {
		return f2;
	}

	/**
	 * setF2
	 * Attribue la valeur en paramètre à l'attribut f2 d'une Ellipse
	 * @param f2
	 */
	public void setF2(Fixe f2) {
		this.f2 = f2;
	}



}
