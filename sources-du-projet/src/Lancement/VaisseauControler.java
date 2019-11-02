package Lancement;

import Modele.Objets.Vaisseau;

public class VaisseauControler {
	
	public void retroFuseeGauche(Vaisseau v) {
		v.setPretro(-0.005);
		System.out.println("Gauche");
	}
	
	public void retroFuseeDroite(Vaisseau v) {
		v.setPretro(0.005);
		System.out.println("Droite");
	}
	
	public void principaleAvant(Vaisseau v) {
		v.setPprincipal(-0.005);
		System.out.println("Avant");
	}
	
	public void principaleArriere(Vaisseau v) {
		v.setPprincipal(0.005);
		System.out.println("Arriere");
	}
	
}
