package Modele.Objets;

/**
 * 
 * @author lantoing, dautrich, messaady, tryoeny
 *
 */

public class Simul� extends Objet {

	private double vitx;
	private double vity;
	
	/**
	 * Simulé
	 * Constructeur d'un objet Simulé avec attributs spécifiés
	 * @param m
	 * @param px
	 * @param py
	 * @param vx
	 * @param vy
	 */
	public Simul�(double m, double px, double py, double vx, double vy){
		super(m, px, py);
		vitx = vx;
		vity = vy;	
	}
	
	/**
	 * getVitx
	 * Retourne la vitesse en x
	 * @return double
	 */
	public double getVitx() {
		return vitx;
	}
	
	/**
	 * setVitx
	 * Attribuer la valeur passée en paramètre à l'attribut vitx
	 * @param vitx
	 */
	public void setVitx(double vitx) {
		this.vitx = vitx;
	}
	
	/**
	 * getVity
	 * Retourne la vitesse en y
	 * @return double
	 */
	public double getVity() {
		return vity;
	}

	/**
	 * setVity
	 * Attribuer la valeur passée en paramètre à l'attribut vity
	 * @param vity
	 */
	public void setVity(double vity) {
		this.vity = vity;
	}


}
