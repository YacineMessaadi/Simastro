package Affichage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
	Image etoileImage = new Image("file:resources/espace.jpg");
	double axeX;
	double axeY;


	public double scale = 1;
	private double xStart = 0;
	private double yStart = 0;
	private boolean following= false;


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
		/*
		ouvrir.setOnAction(e -> {
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				Sauvegarde save = new Sauvegarde(file);
				root.setCenter(generateCanvas(save));
			}
		});
		*/

		MenuItem enregistrer = new MenuItem("Enregistrer");
		MenuItem enregistrerSous = new MenuItem("Enregistrer Sous");
		fichier.getItems().addAll(nouveau, ouvrir, enregistrer, enregistrerSous);
		menuBar.getMenus().addAll(fichier, edition, aide);

		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: black;");

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
			System.exit(0);
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

		root.setCenter(generateCanvas(sys));

		root.setTop(menuBar);
		root.setLeft(tableauBordGauche);
		root.setRight(tableauBordDroite);
		root.setBottom(hbox);

		tableauBordGauche.setPrefWidth(100);
		tableauBordDroite.setPrefWidth(100);

		//BackgroundImage etoileImageBackground = new BackgroundImage(etoileImage, BackgroundRepeat.REPEAT,
		//		BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		//pane.setBackground(new Background(etoileImageBackground));

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
		stage.setFullScreen(true);
		stage.show();
	}

	public void update(Observable o, Object arg1) {
		if (o instanceof Systeme) {
			System.out.println("Update");
			refresh(sys);
		}
	}

	public void refresh(Systeme s){
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		ArrayList<Objet> listObjet = s.getSatellites();
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();


		for (Objet o : listObjet) {
			double moitieX = (canvas.getWidth()/2)/scale;
			double moitieY = (canvas.getHeight()/2)/scale;
			canvas.getGraphicsContext2D().drawImage(etoileImage,Double.MAX_VALUE,Double.MAX_VALUE);
			if (o instanceof Soleil) {
				graphicsContext.drawImage(soleil,(o.getPosx()+moitieX+axeX)*scale,(o.getPosy()+moitieY+axeY)*scale,(o.getMasse())*scale,(o.getMasse())*scale);
				graphicsContext.setFill(Color.BLUE);
				graphicsContext.fillText("Soleil",(o.getPosx()+moitieX+axeX)*scale,(o.getPosy()+moitieY+axeY)*scale);
			} else if (o instanceof Vaisseau) {
				graphicsContext.drawImage(vaisseau,(o.getPosx()+moitieX+axeX)*scale,(o.getPosy()+moitieY+axeY)*scale,(o.getMasse())*scale,(o.getMasse())*scale);
				graphicsContext.setFill(Color.BLUE);
				graphicsContext.fillText("Vaisseau",(o.getPosx()+moitieX+axeX)*scale,(o.getPosy()+moitieY+axeY)*scale);
			} else {
				graphicsContext.drawImage(planete,(o.getPosx()+moitieX+axeX)*scale,(o.getPosy()+moitieY+axeY)*scale,(o.getMasse())*scale,(o.getMasse())*scale);
				graphicsContext.setFill(Color.GREEN);
				graphicsContext.fillText("Planéte",(o.getPosx()+moitieX+axeX)*scale,(o.getPosy()+moitieY+axeY)*scale);
			}
		}
	}


	public Pane generateCanvas(Systeme s) {
		Pane cage = new Pane();
		cage.getChildren().add(canvas);
		canvas.minHeight(0); canvas.minWidth(0);
		canvas.prefHeight(500); canvas.prefWidth(500);
		refresh(s);
		canvas.widthProperty().bind(cage.widthProperty());
		canvas.heightProperty().bind(cage.heightProperty());

		canvas.setOnScroll(e -> {
			//scale += (e.getDeltaY()/1000);

			if(e.getDeltaY()>0) scale *= 1.1;
			else if(e.getDeltaY()<0) scale *= 0.9;
			refresh(s);
			e.consume();
		});

		canvas.setOnMousePressed(event ->{
			xStart = event.getSceneX();
			yStart = event.getSceneY();
			System.out.println("X:"+xStart + " Y:"+yStart);
			event.consume();
		});

		canvas.setOnMouseDragged(event -> {

			axeX -= ( (xStart-event.getSceneX()))/scale;
			axeY -= ( (yStart-event.getSceneY()))/scale;
			System.out.println("axeX:"+axeX + "axeY:"+axeY);
			refresh(s);
			xStart = event.getSceneX();
			yStart = event.getSceneY();
		});

		//canvas.setTranslateX(cage.getLayoutX()/2);
		//canvas.setTranslateY(cage.getLayoutY()/2);

		return cage;
	}

	public Pane generateCanvas(Sauvegarde sauvegarde) {
		Systeme sys = sauvegarde.charger();
		return generateCanvas(sys);
	}

}