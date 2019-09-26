package Util;

import Objets.Satellite;
import Objets.Simulé;
import Objets.Soleil;
import Objets.Systeme;

import java.io.*;

public class Sauvegarde {
	static String path;

	public Sauvegarde(String path) {
		this.path = "save/" + path;
	}

	public static Systeme charger() throws FileNotFoundException {
		File astroFile = new File(path);
		System.out.print("Le fichier " + astroFile.getName() + " à été chargé.");
		BufferedReader br = new BufferedReader(new FileReader(astroFile));
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
				else if (!thisLine.startsWith("#"))
					sys.addListAstres(chargerSimule(thisLine));

			}
			return sys;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Soleil chargerSoleil(String thisLine) throws FileNotFoundException {
		String valeur[] = thisLine.split(" ");
		String valeur2[] = new String[valeur.length];
		for (int i = 2; i < valeur.length; i++)
			valeur2[i - 2] = valeur[i].split("=")[1];
		System.out.println("Soleil trouvé !");
		return new Soleil(Integer.parseInt(valeur2[0]), Integer.parseInt(valeur2[1]), Integer.parseInt(valeur2[2]));
	}

	public static Simulé chargerSimule(String thisLine) throws FileNotFoundException {
		String valeur[] = thisLine.split(" ");
		String valeur2[] = new String[valeur.length];
		for (int i = 2; i < valeur.length; i++)
			valeur2[i - 2] = valeur[i].split("=")[1];
		System.out.println("Simulé trouvé !");
		return new Simulé(Double.parseDouble(valeur2[0]), Double.parseDouble(valeur2[1]),
				Double.parseDouble(valeur2[2]), Double.parseDouble(valeur2[3]), Double.parseDouble(valeur2[4]));
	}

	public static void main(String[] args) throws FileNotFoundException {
		Sauvegarde s = new Sauvegarde("01_CorpsTombeSurSoleil.astro");
		Systeme sys = s.charger();
		System.out.println(sys.getSatellites().size());
		for(Satellite sat : sys.getSatellites()) System.out.println(sat.getMasse());
	}

}
