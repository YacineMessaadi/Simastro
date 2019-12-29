package Modele.Objets;

import java.util.LinkedList;

/**
 * 
 * @author lantoing, dautrich, messaady, tryoeny
 *
 */

public class Ellipse extends Objet {

	private Fixe f1;
	private Fixe f2;
	private double dureePeriode;
	private double periode;
	private LinkedList<Position> trail;
	private int listSize;
	private Double[] trailColor;

	/**
	 * Ellipse Constructeur d'un objet Ellispe avec attributs spécifiés
	 * 
	 * @param m
	 * @param px
	 * @param py
	 * @param vx
	 * @param vy
	 * @param fixe1
	 * @param fixe2
	 */
	public Ellipse(String nom, double m, Fixe fixe1, Fixe fixe2, double px, double py, double periode) {
		super(nom, m, px, py);
		f1 = fixe1;
		f2 = fixe2;
		this.periode = periode;
		trail = new LinkedList<Position>();
		listSize = 10000; 
		trailColor = new Double[] { Math.random(), Math.random(), Math.random() };
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
	
	/**
	 * getF1 Retourne la valeur de l'attribut f1 d'une Ellipse
	 * 
	 * @return Fixe
	 */
	public Fixe getF1() {
		return f1;
	}

	/**
	 * setF1 Attribue la valeur en paramètre à l'attribut f1 d'une Ellipse
	 * 
	 * @param f1
	 */
	public void setF1(Fixe f1) {
		this.f1 = f1;
	}

	/**
	 * getF2 Retourne la valeur de l'attribut f2 d'une Ellipse
	 * 
	 * @return Fixe
	 */
	public Fixe getF2() {
		return f2;
	}

	/**
	 * setF2 Attribue la valeur en paramètre à l'attribut f2 d'une Ellipse
	 * 
	 * @param f2
	 */
	public void setF2(Fixe f2) {
		this.f2 = f2;
	}

	private Vecteur getCenter() {
		return new Vecteur((f1.getPosx() + f2.getPosx()) / 2, (f1.getPosy() + f2.getPosy()) / 2);
	}

	private double getCos() {
		return (f2.getPosx() - f1.getPosx())
				/ Math.sqrt(Math.pow(f2.getPosy() - f1.getPosy(), 2) + Math.pow(f2.getPosx() - f1.getPosx(), 2));
	}

	private double getSin() {
		return (f2.getPosy() - f1.getPosy())
				/ Math.sqrt(Math.pow(f2.getPosy() - f1.getPosy(), 2) + Math.pow(f2.getPosx() - f1.getPosx(), 2));
	}

	private double getA() {
		return (Math.sqrt(Math.pow(getPosx() - f1.getPosx(), 2) + Math.pow(getPosy() - f1.getPosx(), 2)) / 2)
				+ (Math.sqrt(Math.pow(getPosx() - f2.getPosx(), 2) + Math.pow(getPosy() - f2.getPosx(), 2)) / 2);
	}

	private double getB() {
		return Math.sqrt(Math.pow(getA(), 2)
				- (0.25 * (Math.pow(f2.getPosx() - f1.getPosx(), 2) + Math.pow(f2.getPosy() - f1.getPosy(), 2))));
	}

	private Vecteur getAstrePos() {
		double x = this.getCenter().getX()
				+ this.getA() * this.getCos() * Math.cos(this.dureePeriode / this.periode * 2 * Math.PI)
				- this.getB() * this.getSin() * Math.sin(this.dureePeriode / this.periode * 2 * Math.PI);

		double y = this.getCenter().getY()
				+ this.getA() * this.getSin() * Math.cos(this.dureePeriode / this.periode * 2 * Math.PI)
				+ this.getB() * this.getCos() * Math.sin(this.dureePeriode / this.periode * 2 * Math.PI);

		return new Vecteur(x, y);
	}

	public void calculTrajectoire(Systeme s) {
		Vecteur v = this.getAstrePos();
		this.setPosx(v.getX());
		this.setPosy(v.getY());
		this.dureePeriode = (this.dureePeriode + s.getdT()) % this.periode;
	}

}
