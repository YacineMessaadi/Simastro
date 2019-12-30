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
	private double a;
	private double b;
	private double c;
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
		a = getPoint1();
		b = getPoint2();
		c = getC();
		calculTrajectoire();
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
		return new Vecteur(Math.abs((f1.getPosx() + f2.getPosx())) / 2, Math.abs((f1.getPosy() + f2.getPosy()) / 2));
	}

	private double getPoint1() {
		return (Math.sqrt((Math.pow(f1.getPosx() - getPosx(), 2))
						+ (Math.pow(f1.getPosy() - getPosy(), 2))
				)
				+
				(Math.sqrt((Math.pow(f2.getPosx() - getPosx(), 2))
						+ (Math.pow(f2.getPosy() - getPosy(), 2))
				))) /2
				;
	}

	private double getPoint2() {
		return Math.sqrt(Math.pow(a, 2)
				- Math.pow(c, 2));
	}

	private double getC() {
		return (Math.sqrt((Math.pow(f1.getPosx() - f2.getPosx(), 2))
			+ (Math.pow(f1.getPosy() - f2.getPosy(), 2))
			)
	) /2;
	}
	
	private double getE() {
		return a / c;
	}

	/*
	 * public void calculTrajectoire(Systeme s) { this.setPosx(this.getPoint1()
	 * *Math.cos(this.dureePeriode / this.periode * 2 * Math.PI));
	 * this.setPosy(this.getPoint2() *Math.sin(this.dureePeriode / this.periode * 2
	 * * Math.PI));
	 * 
	 * this.dureePeriode = (this.dureePeriode + 1); }
	 */

	public void calculTrajectoire() {
		this.setPosx(getCenter().getX() + Math.cos(this.dureePeriode / this.periode * 2 * Math.PI) * a);
		this.setPosy((getCenter().getY() + Math.sin(this.dureePeriode / this.periode * 2 * Math.PI) * b) / getE());
		this.dureePeriode = (this.dureePeriode + 1) % this.periode;
	}

}
