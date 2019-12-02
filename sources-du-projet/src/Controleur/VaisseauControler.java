package Controleur;

import Modele.Objets.Vaisseau;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import eu.hansolo.medusa.Gauge;

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

	public void dirigerVaisseau(KeyEvent event, Gauge gauge, Vaisseau v) {
		if (gauge.getValue() != 0) {
			if (event.getCode() == KeyCode.UP) {
				principaleArriere(v);
				gauge.setValue(gauge.getValue() - 0.1);
			} else if (event.getCode() == KeyCode.DOWN) {
				principaleAvant(v);
				gauge.setValue(gauge.getValue() - 0.1);
			} else if (event.getCode() == KeyCode.LEFT) {
				retroFuseeDroite(v);
				gauge.setValue(gauge.getValue() - 0.1);
			} else if (event.getCode() == KeyCode.RIGHT) {
				retroFuseeGauche(v);
				gauge.setValue(gauge.getValue() - 0.1);
			}
		}
		if (event.getCode() == KeyCode.R) {
			if (gauge.getValue() < 100)
				gauge.setValue(gauge.getValue() + 0.05);
		} else if (event.getCode() == KeyCode.SPACE) {
			// Code du missile
		}
	}

}
