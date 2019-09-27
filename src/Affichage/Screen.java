package Affichage;

import Objets.Cercle;
import Objets.Objet;
import Objets.Soleil;
import Objets.Systeme;
import Util.Sauvegarde;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Screen {

    public void start(Stage primaryStage) throws FileNotFoundException {

        primaryStage.setTitle("Simastro");
        Group groupGraphic = new Group();
        primaryStage.setScene(new Scene(groupGraphic,1920,1080));
        double sceneCenterX = primaryStage.getScene().getWidth()/2;
        double sceneCenterY = primaryStage.getScene().getHeight()/2;

        Sauvegarde s = new Sauvegarde("01_CorpsTombeSurSoleil.astro");
        Systeme sys = s.charger();
        ArrayList<Objet> listObjet = sys.getSatellites();
        for(Objet o: listObjet){
            System.out.println(o.getPosx() +" & " + o.getPosy());
            Circle c = new Circle(o.getPosx()+sceneCenterX,o.getPosy()+sceneCenterY,100);
            if(o.getClass().getName().equals("Objets.Soleil")){

                c.setFill(Color.YELLOW);
            } else {
                c.setFill(Color.GREEN);
            }
            groupGraphic.getChildren().add(c);
        }


        primaryStage.show();
    }
}
