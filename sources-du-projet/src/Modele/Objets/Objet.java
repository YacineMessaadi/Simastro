package Modele.Objets;

/**
 * 
 * @author lantoing, dautrich, messaady, tryoeny
 *
 */
public abstract class Objet{
	private String nom;
	private double masse;
	private double posx;
	private double posy;

	/**
	 * Objet
	 * Constructeur d'un objet Object
	 * @param m
	 * @param px
	 * @param py
	 */
	public Objet(String nom, double m, double px, double py) {
		this.nom = nom;
		masse = m;
		posx = px;
		posy = py;
	}

	/**
	 * getMasse
	 * Retourne la valeur de l'attribut masse de l'Objet
	 * @return double
	 */
	public double getMasse() {
		return masse;
	}

	/**
	 * setMasse
	 * Attribue la valeur en paramètre à l'attribut masse de l'Objet
	 * @param masse
	 */
	public void setMasse(double masse) {
		this.masse = masse;
	}

	/**
	 * getPosx
	 * Retourne la valeur de l'attribut posx de l'Objet
	 * @return double
	 */
	public double getPosx() {
		return posx;
	}

	/**
	 * setPosx
	 * Attribue la valeur en paramètre à l'attribut posx de l'Objet
	 * @param posx
	 */
	public void setPosx(double posx) {
		this.posx = posx;
	}

	/**
	 * getPosy
	 * Retourne la valeur de l'attribut posy de l'Objet
	 * @return double
	 */
	public double getPosy() {
		return posy;
	}

	/**
	 * setPosy
	 * Attribue la valeur en paramètre à l'attribut posy de l'Objet
	 * @param posy
	 */
	public void setPosy(double posy) {
		this.posy = posy;
	}

	public String getNom(){return  nom;}
	
	public void calculTrajectoire(Systeme s) {}
	public void demiPasLF(Systeme s) {}
}
