package Modele.Objets;

import java.util.ArrayList;
import java.util.Observable;

/**
 * 
 * @author lantoing, dautrich, messaady, tryoeny
 *
 */

public class Systeme extends Observable {

	private double gravite;
	private double fA;
	private double dT;

	private double rayon;
	private ArrayList<Objet> listAstre = new ArrayList<Objet>();

	/**
	 * Systeme Constructeur d'un objet Systeme sans paramètres *
	 */
	public Systeme() {
		listAstre = new ArrayList<Objet>();
		gravite = 9.807;
		fA = 1;
		dT = 1;
		rayon = 500;
	}

	/**
	 * Systeme Constructeur d'un objet Systeme avec spécification des attributs
	 * 
	 * @param gravite
	 * @param fa
	 * @param dT
	 * @param rayon
	 */
	public Systeme(double gravite, double fa, double dT, double rayon) {
		this.gravite = gravite;
		this.fA = fa;
		this.dT = dT;
		this.rayon = rayon;
	}

	/**
	 * addListAstres Ajoute une liste d'Objets en attribut du Systeme
	 * 
	 * @param s
	 */
	public void addListAstres(Objet s) {
		this.listAstre.add(s);
	}
	
	public void deleteAstre(Objet s) {
		this.listAstre.remove(s);
	}

	/**
	 * getSatellites Retourne la liste d'Objets attribuée à un Systeme
	 * 
	 * @return ArrayList<Objet>
	 */
	public ArrayList<Objet> getSatellites() {
		return listAstre;
	}

	public double getfA() {
		return fA;
	}

	public double getdT() {
		return dT;
	}
	
	public double getGravite() {
		return gravite;
	}
	
	public void info() {
		this.setChanged();
		this.notifyObservers();
	}
	
	public Vaisseau getVaisseau() {
		for(Objet s : this.listAstre) {
			if(s instanceof Vaisseau) return (Vaisseau) s;
		}
		return null;
	}

	public double getRayon() {
		return rayon;
	}
}
