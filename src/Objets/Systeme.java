package Objets;

import java.util.ArrayList;

public class Systeme {

    private double gravite;
    private double fA;
    private double dT;
    double rayon;
    ArrayList<Objet> listAstre = new ArrayList<Objet>();

    public Systeme(){
        listAstre = new ArrayList<Objet>();
        gravite = 1; fA = 1; dT = 1; rayon = 500;
    }

    public Systeme(double gravite, double fa, double dT, double rayon){
        this.gravite=gravite; this.fA=fa; this.dT=dT; this.rayon=rayon;
    }
    
    public void addListAstres(Objet s) {
    	this.listAstre.add(s);
    }
    
    public ArrayList<Objet> getSatellites(){
    	return listAstre;
    }

}
