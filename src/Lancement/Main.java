package Lancement;

import java.io.InputStreamReader;

import Lancement.ThreadTrajectoire;
import Modele.Sauvegarde;
import Modele.Objets.Systeme;
import Affichage.Interface;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    
	
	static Interface inter;
    static ThreadTrajectoire tt;
    static Systeme s;
    static Sauvegarde sa;
    
    public static void main(String[] args) {
        if(args.length!=0) {
        	System.out.println(Main.class.getClassLoader().getResourceAsStream("/save/" + args[0]));
        	InputStreamReader file = new InputStreamReader(Main.class.getResourceAsStream("/save/" + args[0]));
            sa = new Sauvegarde(file);
            launch(args);
        } else {
            sa = new Sauvegarde(new InputStreamReader(Main.class.getResourceAsStream("/save/04_ExempleDuSujet.astro")));
            launch(args);
        }
    }

    @Override
    public void start(Stage primaryStage) {
    	s = sa.charger();
    	System.out.println(s.getfA());
    	tt = new ThreadTrajectoire(s);
    	
    	inter = new Interface();
    	inter.sys = s;
        try {
        	inter.start(primaryStage);
            tt.calculTrajectoire();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
