package Controleur;

import Modele.Objets.Systeme;

public class PauseController {

	public void setPause(Systeme s) {
		s.setRunning(!s.getRunning());
	}
	
}
