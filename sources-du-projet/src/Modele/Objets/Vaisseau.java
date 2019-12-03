package Modele.Objets;

public class Vaisseau extends Simule {

	private double pprincipal;
	private double pretro;
	private double angle;

	public Vaisseau(double m, double px, double py, double vx, double vy) {
		super("Vaisseau", m, px, py, vx, vy);
		pprincipal = 0;
		pretro = 0;
	}

	public Vaisseau(double m, double px, double py, double vx, double vy, double pprincipal, double pretro) {
		super("Vaisseau", m, px, py, vx, vy);
		this.pprincipal = pprincipal;
		this.pretro = pretro;
	}

	public double getPprincipal() {
		return pprincipal;
	}

	public double getPretro() {
		return pretro;
	}

	public void setPprincipal(double pprincipal) {
		this.pprincipal = pprincipal;
	}

	public void setPretro(double pretro) {
		this.pretro = pretro;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	@Override
	public void calculTrajectoire(Systeme s) {
		double xTotal = 0;
		double yTotal = 0;
		for (Objet o1 : s.getSatellites()) {
			if (this != o1) {
				double distX = o1.getPosx() - getPosx();
				double distY = o1.getPosy() - getPosy();
				double distance = Math.sqrt(distX * distX + distY * distY);
				double angle = Math.atan2(distY, distX);
				double force = s.getGravite() * ((getMasse() * o1.getMasse()) / (distance * distance));
				xTotal += (Math.cos(angle) * force);
				yTotal += (Math.sin(angle) * force);

			}
		}
		
		xTotal += getPprincipal()*Math.cos(getAngle());
		yTotal += getPprincipal()*Math.sin(getAngle());
		((Vaisseau) this).setPretro(0);
		((Vaisseau) this).setPprincipal(0);

		setPosx(getPosx() + getVitx());
		setPosy(getPosy() + getVity());
		setVitx(getVitx() + xTotal);
		setVity(getVity() + yTotal);
	}

}
