package Modele;

import javafx.scene.control.Alert;

import java.io.*;

import Modele.Objets.Fixe;
import Modele.Objets.Objet;
import Modele.Objets.Simule;
import Modele.Objets.Soleil;
import Modele.Objets.Systeme;

	/**
	 * 
	 * @author lantoing, dautrich, messaady, tryoeny
	 *
	 */
public class Sauvegarde {
	private File file;

	/**
	 * Sauvegarde
	 * Constructeur d'un objet Sauvegarde
	 * @param file
	 */
	public Sauvegarde(File file) {
		this.file = file;
	}
	
	/**
	 * charger
	 * Charge Système depuis une sauvegarde
	 * @return Systeme;
	 */
	public Systeme charger(){
		File astroFile = file;
		System.out.print("Le fichier " + astroFile.getName() + " à été chargé.");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(astroFile));
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Le fichier de sauvegarder indiqué n'existe pas");
			alert.show();
		}
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
					if (thisLine.contains("Simulé"))
						sys.addListAstres(chargerSimule(thisLine));
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

	/**
	 * chargerSoleil
	 * Charge Soleil depuis une sauvegarde
	 * @param thisLine
	 * @return Soleil;
	 */
	public static Soleil chargerSoleil(String thisLine) throws FileNotFoundException {
		String valeur[] = thisLine.split(" ");
		String valeur2[] = new String[valeur.length];
		for (int i = 2; i < valeur.length; i++)
			valeur2[i - 2] = valeur[i].split("=")[1];
		System.out.println("Soleil trouvé !");
		return new Soleil(Integer.parseInt(valeur2[0]), Integer.parseInt(valeur2[1]), Integer.parseInt(valeur2[2]));
	}

	public static Simule chargerSimule(String thisLine) throws FileNotFoundException {
		String valeur[] = thisLine.split(" ");
		String valeur2[] = new String[valeur.length];
		for (int i = 2; i < valeur.length; i++)
			valeur2[i - 2] = valeur[i].split("=")[1];
		System.out.println("Simulé trouvé !");
		return new Simule(Double.parseDouble(valeur2[0]), Double.parseDouble(valeur2[1]),
				Double.parseDouble(valeur2[2]), Double.parseDouble(valeur2[3]), Double.parseDouble(valeur2[4]));
	}
	
	/**
	 * chargerFixe
	 * Charge Fixe depuis une sauvegarde
	 * @param thisLine
	 * @return Fixe;
	 */
	public static Fixe chargerFixe(String thisLine) throws FileNotFoundException {
		String valeur[] = thisLine.split(" ");
		String valeur2[] = new String[valeur.length];
		for (int i = 2; i < valeur.length; i++)
			valeur2[i - 2] = valeur[i].split("=")[1];
		System.out.println("Fixe trouvé !");
		return new Fixe(Integer.parseInt(valeur2[0]), Integer.parseInt(valeur2[1]), Integer.parseInt(valeur2[2]));
	}

	public static void main(String[] args) throws FileNotFoundException {
		Sauvegarde s = new Sauvegarde(new File("01_CorpsTombeSurSoleil.astro"));
		Systeme sys = s.charger();
		System.out.println(sys.getSatellites().size());
		for (Objet sat : sys.getSatellites())
			System.out.println(sat.getMasse());
	}

}
