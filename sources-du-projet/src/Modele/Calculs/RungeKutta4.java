package Modele.Calculs;

import Modele.Objets.Cercle;
import Modele.Objets.Ellipse;
import Modele.Objets.Objet;
import Modele.Objets.Simule;
import Modele.Objets.Systeme;
import Modele.Objets.Vaisseau;
import Modele.Objets.Vecteur;

public class RungeKutta4 implements CalculInterface {

	public void calculTrajectoire(Systeme s) {
		for (Objet o : s.getSatellites()) {

			if (o instanceof Cercle) {
				((Cercle) o).calculTrajectoire();
			}

			else if (o instanceof Ellipse) {
				((Ellipse) o).calculTrajectoire();
			}

			else if (o instanceof Simule) {
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

				((Simule) o).setAcc(new Vecteur(xTotal, yTotal));

				Vecteur vit = ((Simule) o).getVit();
				Vecteur acc = ((Simule) o).getAcc();
				double dt = 1;

				Vecteur v1 = vit;
				Vecteur v2 = vit.pas(dt / 2, v1);
				Vecteur v3 = vit.pas(dt / 2, v2);
				Vecteur v4 = vit.pas(dt, v3);

				o.setPosx(o.getPosx() + (dt / 6) * (v1.getX() + 2 * v2.getX() + 2 * v3.getX() + v4.getX()));
				o.setPosy(o.getPosy() + (dt / 6) * (v1.getY() + 2 * v2.getY() + 2 * v3.getY() + v4.getY()));

				Vecteur k1 = acc;
				Vecteur k2 = acc.pas(dt / 2, k1);
				Vecteur k3 = acc.pas(dt / 2, k2);
				Vecteur k4 = acc.pas(dt, k3);

				((Simule) o).setVit(
						new Vecteur(vit.getX() + (dt / 6) * (k1.getX() + 2 * k2.getX() + 2 * k3.getX() + k4.getX()),
								vit.getY() + (dt / 6) * (k1.getY() + 2 * k2.getY() + 2 * k3.getY() + k4.getY())));

			}

		}

	}

	@Override
	public void preCalcul(Systeme s) {
		Vecteur vit = s.getVaisseau().getVit();
		vit.multiplie(10000);
		double dt = 1;

		Vecteur v1 = vit;
		Vecteur v2 = vit.pas(dt / 2, v1);
		Vecteur v3 = vit.pas(dt / 2, v2);
		Vecteur v4 = vit.pas(dt, v3);

		s.getVaisseau().setPresPosX(
				s.getVaisseau().getPosx() + (dt / 6) * (v1.getX() + 2 * v2.getX() + 2 * v3.getX() + v4.getX()));
		s.getVaisseau().setPresPosY(
				s.getVaisseau().getPosy() + (dt / 6) * (v1.getY() + 2 * v2.getY() + 2 * v3.getY() + v4.getY()));
	}

}
