package Lancement;

import java.io.InputStreamReader;

import Modele.Sauvegarde;
import Modele.Objets.Systeme;
import Affichage.Interface;
import Controleur.ThreadTrajectoire;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	static Interface inter;
	static ThreadTrajectoire tt;
	static Systeme s;
	static Sauvegarde sa;

	public static void main(String[] args) {
		try {
			if (args.length != 0) {
				InputStreamReader file = new InputStreamReader(Main.class.getResourceAsStream("/save/" + args[0]));
				sa = new Sauvegarde(file);
				launch(args);
			} else {
				sa = new Sauvegarde(
						new InputStreamReader(Main.class.getResourceAsStream("/save/04_ExempleDuSujet.astro")));
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

		inter = new Interface();
		inter.sys = s;
		try {
			inter.start(primaryStage);
			tt.calculTrajectoire();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
