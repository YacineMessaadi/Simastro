package Modele;

import java.io.*;

import Modele.Objets.Fixe;
import Modele.Objets.Simule;
import Modele.Objets.Soleil;
import Modele.Objets.Systeme;
import Modele.Objets.Vaisseau;

/**
 * 
 * @author lantoing, dautrich, messaady, tryoeny
 *
 */
public class Sauvegarde {
	private InputStreamReader file;

	/**
	 * Sauvegarde Constructeur d'un objet Sauvegarde
	 * 
	 * @param file
	 */
	public Sauvegarde(InputStreamReader file) {
		this.file = file;
	}

	/**
	 * charger Charge Système depuis une sauvegarde
	 * 
	 * @return Systeme;
	 */
	public Systeme charger() {
		InputStreamReader astroFile = file;
		System.out.print("Le fichier a été chargé.");
		BufferedReader br = null;
		br = new BufferedReader(astroFile);
		Systeme sys = null;
		try {
			String thisLine;
			while ((thisLine = br.readLine()) != null) {
				if (thisLine.startsWith("PARAMS")) {
					System.out.println("PARAMS trouvés !");
					String valeur[] = thisLine.split(" ");
					String valeur2[] = new String[valeur.length];
					for (int i = 1; i < valeur.length; i++) {
						valeur2[i - 1] = valeur[i].split("=")[1];
					}
					sys = new Systeme(Double.parseDouble(valeur2[0]), Double.parseDouble(valeur2[2]),
							Double.parseDouble(valeur2[1]), Double.parseDouble(valeur2[3]));

				} else if (thisLine.startsWith("Soleil:"))
					sys.addListAstres(chargerSoleil(thisLine));
				else if (!thisLine.startsWith("#")) {
					if (thisLine.contains("Vaisseau"))
						sys.addListAstres(chargerVaisseau(thisLine));
					else if (thisLine.contains("Simule") || thisLine.contains("Simulé")) {
						sys.addListAstres(chargerSimule(thisLine));
					}
					else if (thisLine.contains("Fixe"))
						sys.addListAstres(chargerFixe(thisLine));
				}
			}
			return sys;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Vaisseau chargerVaisseau(String thisLine) throws FileNotFoundException {
		String valeur[] = thisLine.split(" ");
		String valeur2[] = new String[valeur.length];
		for (int i = 2; i < valeur.length; i++)
			valeur2[i - 2] = valeur[i].split("=")[1];
		System.out.println("Vaisseau trouvé !");
		if (valeur2.length == 5)
			return new Vaisseau(Double.parseDouble(valeur2[0]), Double.parseDouble(valeur2[1]),
					Double.parseDouble(valeur2[2]), Double.parseDouble(valeur2[3]), Double.parseDouble(valeur2[4]));
		return new Vaisseau(Double.parseDouble(valeur2[0]), Double.parseDouble(valeur2[1]),
				Double.parseDouble(valeur2[2]), Double.parseDouble(valeur2[3]), Double.parseDouble(valeur2[4]),
				Double.parseDouble(valeur2[5]), Double.parseDouble(valeur2[6]));
	}

	/**
	 * chargerSoleil Charge Soleil depuis une sauvegarde
	 * 
	 * @param thisLine
	 * @return Soleil;
	 */
	public static Soleil chargerSoleil(String thisLine) throws FileNotFoundException {
		String valeur[] = thisLine.split(" ");
		String valeur2[] = new String[valeur.length-2];
		String nom = valeur[0].split(":")[0];
		for (int i = 2; i < valeur.length; i++) {
			valeur2[i - 2] = valeur[i].split("=")[1];
		}
		String pow[] = new String[valeur2.length];
		String nbr[] = new String[valeur2.length];
		for (int i = 0; i < valeur2.length; i++) {
			if (valeur2[i].contains("e")) {
				pow[i] = valeur2[i].split("e")[1];
				nbr[i] = valeur2[i].split("e")[0];
			}
			else {
				pow[i] = "0";
				nbr[i] = valeur2[i];
			}
		}

		double m = Double.parseDouble(nbr[0]) * Math.pow(10, Double.parseDouble(pow[0]));
		double px = Double.parseDouble(nbr[1]) * Math.pow(10, Double.parseDouble(pow[1]));
		double py = Double.parseDouble(nbr[2]) * Math.pow(10, Double.parseDouble(pow[2]));

		System.out.println("Soleil trouvé !");
		return new Soleil(nom,m, px, py);
	}

	public static Simule chargerSimule(String thisLine) throws FileNotFoundException {
		String valeur[] = thisLine.split(" ");
		String valeur2[] = new String[valeur.length-2];
		String nom = valeur[0].split(":")[0];
		for (int i = 2; i < valeur.length; i++) {
			valeur2[i - 2] = valeur[i].split("=")[1];
		}
		String pow[] = new String[valeur2.length];
		String nbr[] = new String[valeur2.length];
		for (int i = 0; i < valeur2.length; i++) {
			if (valeur2[i].contains("e")) {
				pow[i] = valeur2[i].split("e")[1];
				nbr[i] = valeur2[i].split("e")[0];
			}
			else {
				pow[i] = "0";
				nbr[i] = valeur2[i];
			}
		}

		double m = Double.parseDouble(nbr[0]) * Math.pow(10, Double.parseDouble(pow[0]));
		double px = Double.parseDouble(nbr[1]) * Math.pow(10, Double.parseDouble(pow[1]));
		double py = Double.parseDouble(nbr[2]) * Math.pow(10, Double.parseDouble(pow[2]));
		double vx = Double.parseDouble(nbr[3]) * Math.pow(10, Double.parseDouble(pow[3]));
		double vy = Double.parseDouble(nbr[4]) * Math.pow(10, Double.parseDouble(pow[4]));

		System.out.println("Simulé trouvé !");
		return new Simule(nom,m, px, py, vx, vy);
	}

	/**
	 * chargerFixe Charge Fixe depuis une sauvegarde
	 * 
	 * @param thisLine
	 * @return Fixe;
	 */
	public static Fixe chargerFixe(String thisLine) throws FileNotFoundException {
		String valeur[] = thisLine.split(" ");
		String valeur2[] = new String[valeur.length];
		String nom = valeur[0].split(":")[0];
		for (int i = 2; i < valeur.length; i++){
			valeur2[i - 2] = valeur[i].split("=")[1];
		}
		System.out.println("Fixe trouvé !");
		return new Fixe(nom,Double.parseDouble(valeur2[0]), Double.parseDouble(valeur2[1]), Double.parseDouble(valeur2[2]));
	}

}
