package Objets;

/**
 * 
 * @author lantoing, dautrich, messaady, tryoeny
 *
 */

public class Cercle extends Simule {
	
	private Objet centre;

	public Cercle(double m, double px, double py, Objet fo1, double vx, double vy) {
		super(m, px, py,vx,vy);
		centre = fo1;
		}

	public Objet getcentre() {
		return centre;
	}

	public void setcentre(Objet centre) {
		this.centre = centre;
	}
	

}
