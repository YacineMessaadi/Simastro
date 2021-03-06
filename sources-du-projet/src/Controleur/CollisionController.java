package Controleur;

import java.util.HashMap;

import Affichage.Interface;
import Modele.Objets.Objet;
import Modele.Objets.Systeme;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CollisionController {

	/*
	 * public void checkCollision(Systeme s, HashMap<Objet, Circle> hm) { for (Objet
	 * o : s.getSatellites()) { for (Objet o1 : s.getSatellites()) { if (o != o1 &&
	 * Shape.intersect(hm.get(o), hm.get(o1)).getBoundsInLocal().getWidth() != -1) {
	 * if (o.getMasse() < o1.getMasse()) { s.deleteAstre(o); } else {
	 * s.deleteAstre(o1); } return; } } } }
	 */

	public boolean checkCollision(Systeme s, HashMap<Objet, Circle> hm,Interface intf) {
		for (Objet o : s.getSatellites()) {
			for (Objet o1 : s.getSatellites()) {
				if (o != o1 && Shape.intersect(hm.get(o), hm.get(o1)).getBoundsInLocal().getWidth() != -1) {
					if (o.getNom().equals("Missile"))
						o.setMasse(5);
					else if (o1.getNom().equals("Missile"))
						o1.setMasse(5);
					if (o.getMasse() < o1.getMasse()) {
						o1.setMasse(o1.getMasse() - o.getMasse());
						s.deleteAstre(o);
						intf.boxSuivre.getItems().remove(o);
						if (!o1.getNom().equals("Missile") && !o.getNom().equals("Missile") && o.getMasse() > 2) {
							s.explosion(s, o, o1);
						}
					} else if (o.getMasse() == o1.getMasse()) {
						s.deleteAstre(o1);
						intf.boxSuivre.getItems().remove(o1);
						s.deleteAstre(o);
						intf.boxSuivre.getItems().remove(o);
						if (!o1.getNom().equals("Missile") && !o.getNom().equals("Missile") && o1.getMasse() > 2 && o.getMasse() > 2) {
							s.explosion(s, o, o1);
						}
					} else {
						o.setMasse(o.getMasse() - o1.getMasse());
						s.deleteAstre(o1);
						intf.boxSuivre.getItems().remove(o1);
						if (!o1.getNom().equals("Missile") && o1.getMasse() > 2) {
							s.explosion(s, o1, o);
						}
					}
					return true;
				}
			}
		}

		return false;

	}

}
