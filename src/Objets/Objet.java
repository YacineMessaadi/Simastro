package Objets;

<<<<<<< HEAD
public abstract class Objet{
=======
import javafx.scene.Node;

/**
 * 
 * @author lantoing, dautrich, messaady, tryoeny
 *
 */

public class Objet{
>>>>>>> branch 'master' of https://git-iut.univ-lille1.fr/tryoeny/projet_modelisation.git
	
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
	public Objet(double m, double px, double py) {
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
	
	

}
