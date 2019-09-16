package Satellite;

public class Vaisseau extends Satellite {
	
	private double vitx;
	private double vity;
	private double pprincipal;
	private double pretro;
	
	public Vaisseau(double m, double px, double py, double vx, double vy, double pp, double pr){
		super(m, px, py);
		vitx = vx;
		vity = vy;
		pprincipal = pp;
		pretro = pr;
	}

	public double getVitx() {
		return vitx;
	}

	public void setVitx(double vitx) {
		this.vitx = vitx;
	}

	public double getVity() {
		return vity;
	}

	public void setVity(double vity) {
		this.vity = vity;
	}

	public double getPprincipal() {
		return pprincipal;
	}

	public void setPprincipal(double pprincipal) {
		this.pprincipal = pprincipal;
	}

	public double getPretro() {
		return pretro;
	}

	public void setPretro(double pretro) {
		this.pretro = pretro;
	}
	
	

}
