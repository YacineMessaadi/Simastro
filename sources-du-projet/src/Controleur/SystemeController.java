package Controleur;

import Modele.Methode;
import Modele.Objets.Systeme;

public class SystemeController {

	public void setMethode(Systeme s, Methode m) {
		s.methode = m;
	}

	public void minDt(Systeme s) {
		s.setdT(s.getdT()-0.01);
	}
	
	public void plusDt(Systeme s) {
		s.setdT(s.getdT()+0.01);
	}
	
}
