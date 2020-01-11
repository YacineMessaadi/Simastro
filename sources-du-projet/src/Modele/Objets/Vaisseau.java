package Modele.Objets;

public class Vaisseau extends Simule {

	private double pprincipal;
	private double pretro;
	private double angle;
	private double presPosX;
	private double presPosY;
	private int nbMissiles = 15;

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
	
	public void creerMissile(Systeme s) {
		s.creerMissile(this);
	}

	public int getNbMissiles() {
		return nbMissiles;
	}
	
	public double getPresPosX() {
		return presPosX;
	}

	public void setPresPosX(double presPosX) {
		this.presPosX = presPosX;
	}

	public double getPresPosY() {
		return presPosY;
	}

	public void setPresPosY(double presPosY) {
		this.presPosY = presPosY;
	}

	public void setNbMissiles(int nbMissiles) {
		this.nbMissiles = nbMissiles;
	}


}
