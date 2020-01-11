package Lancement;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import Modele.Sauvegarde;
import Modele.Objets.Systeme;
import Affichage.Interface;
import Controleur.ThreadPreCalcul;
import Controleur.ThreadTrajectoire;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	static Interface inter;
	static ThreadTrajectoire tt;
	static ThreadPreCalcul tpc = null;
	static Systeme s;
	static Sauvegarde sa;

	public static void main(String[] args) {
		try {
			if (args.length != 0) {
				InputStreamReader file = new InputStreamReader(new FileInputStream(args[0]));
				sa = new Sauvegarde(file);
				launch(args);
			} else {
				sa = new Sauvegarde(
						new InputStreamReader(Main.class.getResourceAsStream("/save/42_Test.astro")));
				launch(args);
			}
		} catch (Exception e) {
			System.out.println("Erreur lors du chargement du fichier."+'\n'+"Veuillez vérifier la syntaxe du fichier astro.");
			System.exit(0);
		}
	}

	@Override
	public void start(Stage primaryStage) {
		s = sa.charger();
		tt = new ThreadTrajectoire(s);
		if(s.getVaisseau() != null) tpc = new ThreadPreCalcul(s);
		
		inter = new Interface();
		inter.sys = s;
		try {
			inter.start(primaryStage);
			tt.calculTrajectoire();
			if(tpc !=null ) tpc.preCalcul();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
