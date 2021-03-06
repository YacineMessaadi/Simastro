package Modele.Objets;

import java.util.ArrayList;
import java.util.Observable;

import Modele.Calculs.CalculInterface;
import Modele.Calculs.EulerExplicite;
import Modele.Calculs.RungeKutta4;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author lantoing, dautrich, messaady, tryoeny
 *
 */

public class Systeme extends Observable {

	private double gravite;
	private double fA;
	private double dT;
	public CalculInterface methode;
	private double distVoyager = -1;
	private double vitCosmique = 0;
	private boolean running = true;
	private String cible = "";
	private double rayon;
	private ArrayList<Objet> listAstre = new ArrayList<Objet>();
	private ObservableList<Objet> observableListAstre = FXCollections.observableArrayList(listAstre);

	/**
	 * Systeme Constructeur d'un objet Systeme sans paramètres *
	 */
	public Systeme() {
		listAstre = new ArrayList<Objet>();
		gravite = 9.807;
		fA = 1;
		dT = 1;
		rayon = 500;
		methode = new RungeKutta4();
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
		this.methode = new RungeKutta4();
	}

	/**
	 * addListAstres Ajoute une liste d'Objets en attribut du Systeme
	 * 
	 * @param s
	 */
	public void addListAstres(Objet s) {
		this.listAstre.add(s);
		observableListAstre.add(s);
	}

	public void deleteAstre(Objet s) {
		this.listAstre.remove(s);
		observableListAstre.add(s);
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

	public double setfA(double value) {
		double temp = fA;
		fA = value;
		return temp;
	}

	public double getdT() {
		return dT;
	}

	public void setdT(double dT) {
		this.dT = dT;
	}

	public double getGravite() {
		return gravite;
	}

	public Vaisseau getVaisseau() {
		for (Objet s : this.listAstre) {
			if (s instanceof Vaisseau)
				return (Vaisseau) s;
		}
		return null;
	}

	public Soleil getSoleil() {
		for (Objet s : this.listAstre) {
			if (s instanceof Soleil)
				return (Soleil) s;
		}
		return null;
	}

	public double getRayon() {
		return rayon;
	}

	public boolean getRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void creerMissile(Vaisseau v) {
		Simule missile = new Simule("Missile", v.getMasse() / 10, v.getPosx() + 2 * Math.cos(v.getAngle()),
				v.getPosy() + 2 * Math.sin(v.getAngle()), 0.05 * Math.cos(v.getAngle()), 0.05 * Math.sin(v.getAngle()));
		listAstre.add(missile);
	}

	public void explosion(Systeme s, Objet o, Objet o2) {
		for (int i = 0; i < 5; i++) {
			s.addListAstres(new Simule(0.2, o.getPosx() + 1 + Math.random() * i,
					o.getPosy() - 1 + Math.random() * i, Math.random() * i, Math.random() * i));
		}
		for (int i = 0; i < 5; i++) {
			s.addListAstres(new Simule(0.2, o.getPosx() - 1 + Math.random() * i,
					o.getPosy() + 1 + Math.random() * i, Math.random() * -i, Math.random() * -i));
		}
	}

	public double getDistVoyager() {
		return distVoyager;
	}

	public void setDistVoyager(double distVoyager) {
		this.distVoyager = distVoyager;
		double distX = getSoleil().getPosx() - getVaisseau().getPosx();
		double distY = getSoleil().getPosy() - getVaisseau().getPosy();
		double distance = Math.sqrt(distX * distX + distY * distY);
		this.vitCosmique = Math.sqrt((2 * gravite * getSoleil().getMasse()) / 1000 * distance);
	}

	public boolean voyagerFini() {
		double distX = getSoleil().getPosx() - getVaisseau().getPosx();
		double distY = getSoleil().getPosy() - getVaisseau().getPosy();
		double distance = Math.sqrt(distX * distX + distY * distY);

		System.out.println("Vit Cosm = " + vitCosmique);
		System.out.println("Vaisseau" + getVaisseau().getVit().getNorme());

		return distance >= distVoyager && getVaisseau().getVit().getNorme() >= vitCosmique;
	}

	public String getCible() {
		return cible;
	}

	public void setCible(String cible) {
		this.cible = cible;
	}

	public boolean cibleDetruite() {
		for (Objet o : getSatellites()) {
			if (o.getNom().equals(cible)) {
				return false;
			}
		}
		return true;
	}

	public ObservableList<Objet> getObservableSatellites(){
		return observableListAstre;
	}

	
	
}
