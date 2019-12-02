package Controleur;

import java.util.HashMap;

import Modele.Objets.Objet;
import Modele.Objets.Systeme;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CollisionController {

	/*public void checkCollision(Systeme s, HashMap<Objet, Circle> hm) {
		for (Objet o : s.getSatellites()) {
			for (Objet o1 : s.getSatellites()) {
				if (o != o1 && Shape.intersect(hm.get(o), hm.get(o1)).getBoundsInLocal().getWidth() != -1) {
					if (o.getMasse() < o1.getMasse()) {
						s.deleteAstre(o);
					} else {
						s.deleteAstre(o1);
					}
					return;
				} 
			}
		}
	}*/
	
	public boolean checkCollision(Systeme s, HashMap<Objet, Circle> hm) {
		for (Objet o : s.getSatellites()) {
			for (Objet o1 : s.getSatellites()) {
				if (o != o1 && Shape.intersect(hm.get(o), hm.get(o1)).getBoundsInLocal().getWidth() != -1) {
					if (o.getMasse() < o1.getMasse()) {
						o1.setMasse(o1.getMasse()-o.getMasse());
						s.deleteAstre(o);
					} else if(o.getMasse()==o1.getMasse()){
						s.deleteAstre(o1);
						s.deleteAstre(o);
					}
					else {
						o.setMasse(o.getMasse()-o1.getMasse());
						s.deleteAstre(o1);
					}
					return true;
				} 
			}
		}
		return false;
	}


}
