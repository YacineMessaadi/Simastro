package Modele.Calculs;

import Modele.Objets.Cercle;
import Modele.Objets.Ellipse;
import Modele.Objets.Objet;
import Modele.Objets.Simule;
import Modele.Objets.Systeme;
import Modele.Objets.Vaisseau;
import Modele.Objets.Vecteur;

public class EulerExplicite implements CalculInterface{

	public void calculTrajectoire(Systeme s) {
		for(Objet o : s.getSatellites()) {
			
			if (o instanceof Cercle) {
				((Cercle) o).calculTrajectoire();
			}

			else if (o instanceof Ellipse) {
				((Ellipse) o).calculTrajectoire();
			}
			
			else if(o instanceof Simule) {
				double xTotal = 0;
				double yTotal = 0;
				for (Objet o1 : s.getSatellites()) {
					if (o != o1) {
						double distX = o1.getPosx() - o.getPosx();
						double distY = o1.getPosy() - o.getPosy();
						double distance = Math.sqrt(distX * distX + distY * distY);
						double angle = Math.atan2(distY, distX);
						double force = s.getGravite() * ((o.getMasse() * o1.getMasse()) / (distance * distance));
						xTotal += (Math.cos(angle) * force);
						yTotal += (Math.sin(angle) * force);
						
					}
				}
				
				if (o instanceof Vaisseau) {
					xTotal += ((Vaisseau) o).getPprincipal() * Math.cos(((Vaisseau) o).getAngle())
							+ ((Vaisseau) o).getPretro() * Math.sin(((Vaisseau) o).getAngle());
					yTotal += ((Vaisseau) o).getPprincipal() * Math.sin(((Vaisseau) o).getAngle())
							- ((Vaisseau) o).getPretro() * Math.cos(((Vaisseau) o).getAngle());
					((Vaisseau) o).setPretro(0);
					((Vaisseau) o).setPprincipal(0);
				}
				
				xTotal /= o.getMasse();
				yTotal /= o.getMasse();
				
				((Simule)o).setAcc(new Vecteur(xTotal,yTotal));
				
				o.setPosx(o.getPosx() + ((Simule)o).getVitx() * 1);
				o.setPosy(o.getPosy() + ((Simule)o).getVity() * 1);
				
				((Simule)o).setVit(((Simule)o).getVit().pas(1, ((Simule)o).getAcc()));
			}
			
		}
			
		
	}

	@Override
	public void preCalcul(Systeme s) {
		Vecteur vit = s.getVaisseau().getVit();
		vit.multiplie(10000);

		s.getVaisseau().setPresPosX(
				s.getVaisseau().getPosx() + vit.getX());
		s.getVaisseau().setPresPosY(
				s.getVaisseau().getPosy() + vit.getY());
		
	}
}
