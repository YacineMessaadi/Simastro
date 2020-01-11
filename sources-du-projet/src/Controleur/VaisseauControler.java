package Controleur;

import Modele.Objets.Systeme;
import Modele.Objets.Vaisseau;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import eu.hansolo.medusa.Gauge;

public class VaisseauControler {

	public void setAngle(Vaisseau v, double angle) {
		v.setAngle(angle);
	}

	public void retroFuseeGauche(Vaisseau v) {
		v.setPretro(0.00000005);
		v.setAngle(v.getAngle() - 0.12);
	}

	public void retroFuseeDroite(Vaisseau v) {
		v.setPretro(-0.00000005);
		v.setAngle(v.getAngle() + 0.12);
	}

	public void principaleAvant(Vaisseau v) {
		v.setPprincipal(0.0000005);
	}

	public void principaleArriere(Vaisseau v) {
		v.setPprincipal(-0.0000005);
	}

	public void dirigerVaisseau(KeyEvent event, Gauge gauge, Vaisseau v, Systeme s) {
		if (gauge.getValue() > 0) {
			if (event.getCode() == KeyCode.UP) {
				principaleArriere(v);
				gauge.setValue(gauge.getValue() - 0.01);
			} else if (event.getCode() == KeyCode.DOWN) {
				principaleAvant(v);
				gauge.setValue(gauge.getValue() - 0.01);
			} else if (event.getCode() == KeyCode.LEFT) {
				retroFuseeDroite(v);
				gauge.setValue(gauge.getValue() - 0.01);
			} else if (event.getCode() == KeyCode.RIGHT) {
				retroFuseeGauche(v);
				gauge.setValue(gauge.getValue() - 0.01);
			}
		}
		if (event.getCode() == KeyCode.R) {
			if (gauge.getValue() < 100)
				gauge.setValue(gauge.getValue() + 0.05);
		}
		if(event.getCode() == KeyCode.SPACE && v.getNbMissiles()>0) {
			v.creerMissile(s);
			v.setNbMissiles(v.getNbMissiles()-1);
		}
	}

}
