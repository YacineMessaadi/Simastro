package Affichage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import Modele.Sauvegarde;
import Modele.Objets.Objet;
import Modele.Objets.Systeme;

public class Interface extends Application{

	BorderPane root = new BorderPane();
	public Systeme sys;
	ImagePattern soleil = new ImagePattern(new Image("file:resources/soleil.png"));
	ImagePattern planete = new ImagePattern(new Image("file:resources/planete.png"));
	
	
	@Override
	public void start(Stage stage) throws Exception {
		final FileChooser fileChooser = new FileChooser();
		
		
		VBox tableauBordGauche = new VBox();
		tableauBordGauche.setStyle("-fx-background-color: #e6e6e6;");
		VBox tableauBordDroite = new VBox();
		tableauBordDroite.setStyle("-fx-background-color: #e6e6e6;");
		HBox hbox = new HBox();
		hbox.setStyle("-fx-background-color: white;");
		hbox.setAlignment(Pos.CENTER);

		MenuBar menuBar = new MenuBar();
		menuBar.setStyle("-fx-alignment: center;");
		Menu fichier = new Menu("Fichier");
		Menu edition = new Menu("Edition");
		Menu aide = new Menu("Aide");
		MenuItem nouveau = new MenuItem("Nouveau");
		nouveau.setOnAction(e -> {

		});

		MenuItem ouvrir = new MenuItem("Ouvrir");
		ouvrir.setOnAction(e -> {
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				Sauvegarde save = new Sauvegarde(file);
				root.setCenter(generateGroup(save));
			}
		});

		MenuItem enregistrer = new MenuItem("Enregistrer");
		MenuItem enregistrerSous = new MenuItem("Enregistrer Sous");
		fichier.getItems().addAll(nouveau, ouvrir, enregistrer, enregistrerSous);
		menuBar.getMenus().addAll(fichier, edition, aide);

		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: black;");
		Canvas canvas = new Canvas();

		Label position = new Label("Position du vaisseau ...");
		tableauBordGauche.getChildren().add(position);

		Label autre = new Label("Autre information ...");
		tableauBordDroite.getChildren().add(autre);

		Button quitter = new Button("Quitter");
		quitter.setOnAction(e -> {
			stage.close();
		});
		hbox.getChildren().add(quitter);

		pane.getChildren().add(canvas);
		root.setTop(menuBar);
		root.setLeft(tableauBordGauche);
		root.setRight(tableauBordDroite);
		root.setCenter(pane);
		root.setBottom(hbox);
		
		root.setCenter(generateGroup(sys));
		
		Image etoileImage = new Image("file:resources/espace.jpg", true);
		BackgroundImage etoileImageBackground = new BackgroundImage(etoileImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		root.setBackground(new Background(etoileImageBackground));
		// root.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40),
		// CornerRadii.EMPTY, Insets.EMPTY)));

		Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                    	root.setCenter(generateGroup(sys));
                    }
                };

                while (true) {
                    try {
                    	Thread.sleep((long) (sys.getdT()*1000));
                    } catch (InterruptedException ex) {
                    }
                    Platform.runLater(updater);
                }
            }

        });
        thread.setDaemon(true);
        thread.start();
		
		Scene scene = new Scene(root, 900, 700);
		stage.setScene(scene);
		stage.setTitle("Simastro");
		stage.show();
	}
	

	public void update(Observable o, Object arg1) {
		if (o instanceof Systeme) {
			System.out.println("Update");
			root.setCenter(generateGroup((Systeme)o));
		}
	}
	
	public Group generateGroup(Sauvegarde sauvegarde) {
		Group groupGraphic = new Group();
		groupGraphic.setStyle("-fx-background-color: black;");
		Canvas fond = new Canvas(500, 500);
		GraphicsContext gc = fond.getGraphicsContext2D();
		gc.setFill(Color.BLACK);

		groupGraphic.getChildren().add(fond);
		Systeme s = sauvegarde.charger();
		this.sys = s;

		double sceneCenterX = groupGraphic.getLayoutX() / 2;
		double sceneCenterY = groupGraphic.getLayoutY() / 2;
		ArrayList<Objet> listObjet = s.getSatellites();

		for (Objet o : listObjet) {
			System.out.println(o.getPosx() + " & " + o.getPosy());
			Circle c = new Circle(o.getPosx() + sceneCenterX, o.getPosy() + sceneCenterY, (15 + o.getMasse()) * 2);
			if (o.getClass().getName().equals("Modele.Objets.Soleil")) {
				c.setFill(soleil);
				c.setStrokeWidth(c.getRadius() / 10);
			} else {
				c.setFill(planete);
				c.setStrokeWidth(c.getRadius() / 10);
			}
			groupGraphic.getChildren().add(c);
		}
		return groupGraphic;
	}
	
	public Group generateGroup(Systeme s) {
		Group groupGraphic = new Group();
		groupGraphic.setStyle("-fx-background-color: black;");
		Canvas fond = new Canvas(500, 500);
		GraphicsContext gc = fond.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		groupGraphic.getChildren().add(fond);
		double sceneCenterX = groupGraphic.getLayoutX() / 2;
		double sceneCenterY = groupGraphic.getLayoutY() / 2;
		ArrayList<Objet> listObjet = s.getSatellites();

		for (Objet o : listObjet) {
			System.out.println(o.getPosx() + " & " + o.getPosy());
			Circle c = new Circle(o.getPosx() + sceneCenterX, o.getPosy() + sceneCenterY, (15 + o.getMasse()) * 2);
			if (o.getClass().getName().equals("Modele.Objets.Soleil")) {
				c.setFill(soleil);
				c.setStrokeWidth(c.getRadius() / 10);
			} else {
				c.setFill(planete);
				c.setStrokeWidth(c.getRadius() / 10);
			}
			groupGraphic.getChildren().add(c);
		}
		return groupGraphic;
	}


}
