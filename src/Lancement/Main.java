package Lancement;

import java.io.File;

import Affichage.Interface;
import Modele.Sauvegarde;
import Modele.Objets.Systeme;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    
	
	static Interface inter;
    static ThreadTrajectoire tt;
    static Systeme s;
    static Sauvegarde sa;
    public static void main(String[] args) {
        if(args.length!=0) {
            sa = new Sauvegarde(new File(args[0]));
            launch(args);
        } else {
            sa = new Sauvegarde(new File("save/42_Test.astro"));
            launch(args);
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
            new Thread(tt).start();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
