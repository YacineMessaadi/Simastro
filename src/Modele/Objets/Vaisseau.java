package Modele.Objets;

public class Vaisseau extends Simule{

	private double pprincipal;
	private double pretro;
	
	public Vaisseau(double m, double px, double py, double vx, double vy) {
		super(m, px, py, vx, vy);
		this.pprincipal = 0;
		this.pretro = 0;
	}
	
	public Vaisseau(double m, double px, double py, double vx, double vy, double pprincipal, double pretro) {
		super(m, px, py, vx, vy);
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
	
	

}
