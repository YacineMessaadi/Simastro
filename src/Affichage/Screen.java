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
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
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

    }

    public static Group generateGroup(Sauvegarde sauvegarde) {
        Group groupGraphic = new Group();
        groupGraphic.setStyle("-fx-background-color: black;");
        Systeme sys = sauvegarde.charger();

        double sceneCenterX = groupGraphic.getLayoutX() / 2;
        double sceneCenterY = groupGraphic.getLayoutY() / 2;
        ArrayList<Objet> listObjet = sys.getSatellites();

        for (Objet o : listObjet) {
            System.out.println(o.getPosx() + " & " + o.getPosy());
            Circle c = new Circle(o.getPosx() + sceneCenterX, o.getPosy() + sceneCenterY, (15 + o.getMasse()) * 2);
            if (o.getClass().getName().equals("Objets.Soleil")) {
                c.setFill(Color.YELLOW);
                c.setStrokeWidth(c.getRadius() / 10);
                c.setStroke(Color.ORANGE);
            } else {
                c.setFill(Color.GREEN);
                c.setStrokeWidth(c.getRadius() / 10);
                c.setStroke(Color.DARKGREEN);
            }
            groupGraphic.getChildren().add(c);
        }
        return groupGraphic;
    }
}
