package Affichage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import Lancement.VaisseauControler;
import Modele.Sauvegarde;
import Modele.Objets.Objet;
import Modele.Objets.Soleil;
import Modele.Objets.Systeme;
import Modele.Objets.Vaisseau;

public class Interface extends Application {

	BorderPane root = new BorderPane();
	public Systeme sys;
	Image soleil = new Image("file:resources/soleil.png");
	Image planete = new Image("file:resources/planete.png");
	Image vaisseau = new Image("file:resources/vaisseau.png");
	VaisseauControler vc = new VaisseauControler();
	Canvas canvas = new Canvas(500,500);
	public double scale = 1;


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
				root.setCenter(generateCanvas(save));
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
		Label positionX = new Label("X = " + 0);
		Label positionY = new Label("Y = " + 0);
		tableauBordGauche.getChildren().addAll(position, positionX, positionY);

		Label autre = new Label("Autre information ...");
		Label temps = new Label("0");
		tableauBordDroite.getChildren().addAll(autre, temps);

		Button quitter = new Button("Quitter");
		quitter.setOnAction(e -> {
			stage.close();
		});
		hbox.getChildren().add(quitter);

		Vaisseau v = sys.getVaisseau();

		EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				if (v != null) {
					if (event.getCode() == KeyCode.UP)
						vc.principaleArriere(v);
					else if (event.getCode() == KeyCode.DOWN)
						vc.principaleAvant(v);
					else if (event.getCode() == KeyCode.LEFT)
						vc.retroFuseeDroite(v);
					else if (event.getCode() == KeyCode.RIGHT)
						vc.retroFuseeGauche(v);
				} else if (event.getCode() == KeyCode.SPACE) {
					// your code for shooting the missile
				}
				event.consume();
			}
		};

		pane.getChildren().add(canvas);
		root.setTop(menuBar);
		root.setLeft(tableauBordGauche);
		root.setRight(tableauBordDroite);
		root.setCenter(pane);
		root.setBottom(hbox);
		root.setCenter(generateCanvas(sys));

		tableauBordGauche.setPrefWidth(100);
		tableauBordDroite.setPrefWidth(100);

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
						refresh(sys);
						temps.setText((Double.parseDouble(temps.getText()) + sys.getdT() + ""));
						if (v != null) {
							positionX.setText("X = " + Math.round(v.getPosx()));
							positionY.setText("Y = " + Math.round(v.getPosy()));
						}

					}
				};

				while (true) {
					try {
						Thread.sleep((long) (sys.getdT() * 1000));
					} catch (InterruptedException ex) {
					}
					Platform.runLater(updater);
				}
			}

		});
		thread.setDaemon(true);
		thread.start();

		Scene scene = new Scene(root, 900, 700);
		scene.setOnKeyPressed(keyListener);
		stage.setScene(scene);
		stage.setTitle("Simastro");
		stage.show();
	}

	public void update(Observable o, Object arg1) {
		if (o instanceof Systeme) {
			System.out.println("Update");
			root.setCenter(generateCanvas((Systeme) o));
		}
	}

	public void refresh(Systeme s){
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		ArrayList<Objet> listObjet = s.getSatellites();
		canvas.setStyle("-fx-background-color: black;");
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.scale(scale,scale);
		scale = 1;

		for (Objet o : listObjet) {
			if (o instanceof Soleil) {
				graphicsContext.drawImage(soleil,o.getPosx(),o.getPosy(),o.getMasse()*10,o.getMasse()*10 );
				graphicsContext.setFill(Color.BLUE);
				graphicsContext.fillText("Soleil",o.getPosx(),o.getPosy()+10);
			} else if (o instanceof Vaisseau) {
				graphicsContext.drawImage(vaisseau,o.getPosx(),o.getPosy(),o.getMasse()*10,o.getMasse()*10 );
				graphicsContext.setFill(Color.BLUE);
				graphicsContext.fillText("Vaisseau",o.getPosx(),o.getPosy()-(o.getMasse()*5));
			} else {
				graphicsContext.drawImage(planete,o.getPosx(),o.getPosy(),o.getMasse()*10,o.getMasse()*10 );
				graphicsContext.setFill(Color.GREEN);
				graphicsContext.fillText("Planéte",o.getPosx(),o.getPosy()-(o.getMasse()*5));
			}
		}
	}


	public Canvas generateCanvas(Systeme s) {
		refresh(s);
		canvas.setHeight(s.getRayon()*2);
		canvas.setWidth(s.getRayon()*2);
		root.setCenter(canvas);
		canvas.setOnScroll(e -> {
			scale += (e.getDeltaY()/1000);
		});

		canvas.setOnMousePressed(e -> {
			//canvas.getGraphicsContext2D()
		});
		return canvas;
	}


	public Canvas generateCanvas(Sauvegarde sauvegarde) {
		Systeme sys = sauvegarde.charger();
		return generateCanvas(sys);
	}

}
