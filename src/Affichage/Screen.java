package Affichage;

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

public class Screen {

    public void start(Stage primaryStage) throws FileNotFoundException {

        primaryStage.setTitle("Simastro");
        Group groupGraphic = new Group();
        primaryStage.setScene(new Scene(groupGraphic,1500,800));

        Circle soleil = new Circle();
        soleil.setCenterX(750);
        soleil.setCenterY(400);
        soleil.setRadius(100);
        soleil.setFill(Color.YELLOW);
        soleil.setStroke(Color.ORANGE);
        soleil.setStrokeWidth(5);

        Circle terre = new Circle();
        terre.setCenterX(550);
        terre.setCenterY(500);
        terre.setRadius(20);
        terre.setFill(Color.GREEN);
        terre.setStroke(Color.DARKGREEN);
        terre.setStrokeWidth(2);

        Rotate rotate = new Rotate(0,750,400);
        terre.getTransforms().add(rotate);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(new Duration(10000), new KeyValue(rotate.angleProperty(), 360))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        groupGraphic.getChildren().addAll(soleil,terre);
        primaryStage.show();
    }
}
