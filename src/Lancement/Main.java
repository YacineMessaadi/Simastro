package Lancement;

import Affichage.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    Screen screen = new Screen();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            screen.start(primaryStage);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
