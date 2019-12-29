package Modele.Objets;

public class Vecteur {

	private double x;
	private double y;

	public Vecteur() {
		this.x = 0.00;
		this.y = 0.00;
	}

	public Vecteur(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getNorme() {
		return Math.sqrt((x * x) + (y * y));
	}

	public void additione(Vecteur v) {
		this.x += v.getX();
		this.y += v.getY();
	}

	public void multiplie(double facteur) {
		this.x *= facteur;
		this.y *= facteur;
	}

	public Vecteur pas(double dt, Vecteur vecteur) {
		return new Vecteur(this.x + dt * vecteur.getX(), this.y + dt * vecteur.getY());
	}

	public Vecteur divise(double facteur) {
		return new Vecteur(this.x /= facteur, this.y /= facteur);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
	
	
	
}