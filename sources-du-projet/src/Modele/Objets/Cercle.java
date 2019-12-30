package Modele.Objets;

/**
 * 
 * @author lantoing, dautrich, messaady, tryoeny
 *
 */

public class Cercle extends Simule {
	
	private Objet centre;
	private double periode, rayon, dureePeriode;

	public Cercle(double m, Objet centre, double px, double py,double periode) {
		super("Cercle",m, px, py,0,0);
		this.centre = centre;
		this.periode = periode;
		this.rayon = Math.sqrt(Math.pow(centre.getPosx() - px, 2) + Math.pow(centre.getPosy() - py, 2));
		calculTrajectoire();
		}

	public Objet getCentre() {
		return centre;
	}

	public void setCentre(Objet centre) {
		this.centre = centre;
	}

	public double getPeriode() {
		return periode;
	}

	public void setPeriode(double periode) {
		this.periode = periode;
	}

	public double getRayon() {
		return rayon;
	}

	public void setRayon(double rayon) {
		this.rayon = rayon;
	}
	
	public void calculTrajectoire() {
		this.setPosx(centre.getPosx() + Math.cos(this.dureePeriode / this.periode * 2 * Math.PI) * this.rayon);
		this.setPosy(centre.getPosy() + Math.sin(this.dureePeriode / this.periode * 2 * Math.PI) * this.rayon);
		this.dureePeriode = (this.dureePeriode + 1) % this.periode;
	}
	

}
