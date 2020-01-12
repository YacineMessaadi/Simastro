package Affichage;

import Modele.Calculs.EulerExplicite;
import Modele.Calculs.LeapFrog;
import Modele.Calculs.RungeKutta4;
import Modele.Objets.*;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.skins.ModernSkin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;

import Controleur.CollisionController;
import Controleur.PauseController;
import Controleur.SystemeController;
import Controleur.VaisseauControler;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class Interface extends Application {
	/**
	 * BorderPane root : BorderPane directement à la base de la scene. Systeme sys
	 * :Le systeme affiché au centre du root à l'aide d'un canvas, lui meme dans un
	 * Pane. NOTE : Le pane est facultatif il permet juste de gerer plus facilement
	 * les limites du canvas Image soleil : Image par defaut d'un soleil. Image
	 * planete : Image par defaut d'une planete. Image vaisseau : Image par defaut
	 * d'un vaisseau. Image etoileImage : Image par defaut du fond. Canvas canvas :
	 * canvas qui va servir à l'affichage du systeme. double axeX : le decalage à
	 * appliquer à tous les objets en X lors de l'affichage, il permet de simuler un
	 * deplacement de la vue double axeY : le decalage à appliquer à tous les objets
	 * en Y lors de l'affichage, il permet de simuler un deplacement de la vue
	 * double scale : le parametre qui permet de simuler le zoom, on multiplie la
	 * taille des objets par le scale, mais egalement la position
	 */
	Pane root = new Pane();
	Ecran ecran_vaisseau;
	public Systeme sys;
	HashMap<Ecran, Stage> listEcran = new HashMap<Ecran, Stage>();
	ListView<Ecran> choixEcran = new ListView<Ecran>();

	VaisseauControler vc = new VaisseauControler();
	CollisionController cc = new CollisionController();
	PauseController pc = new PauseController();
	SystemeController sc = new SystemeController();
	EventHandler<KeyEvent> keyListener;

	HashMap<Objet, Circle> astresImages = new HashMap<Objet, Circle>();

	// Sert à savoir si l'affichage suit actuellement un objet, pour desactiver le
	// deplacement par exemple
	Fixe libre = new Fixe("Libre", 0, 0, 0);
	public ComboBox<Objet> boxSuivre;

	private Image vaisseau;
	Image rotatedImage = vaisseau;
	Gauge gauge;

	// Permet de generer et sauvegarder la trainee des planetes
	private List trail;

	@Override
	public void start(Stage stage) throws Exception {
		// La gauge de Carburant //
		Gauge gauge = new Gauge();
		gauge.setSkin(new ModernSkin(gauge));
		gauge.setTitle("ESSENCE");
		gauge.setUnit("Litres");
		gauge.setDecimals(0);
		gauge.setValue(100.00);
		gauge.setAnimated(true);
		gauge.setAnimationDuration(500);
		gauge.setValueColor(Color.WHITE);
		gauge.setTitleColor(Color.WHITE);
		gauge.setSubTitleColor(Color.WHITE);
		gauge.setBarColor(Color.BLACK);
		gauge.setNeedleColor(Color.WHITE);
		gauge.setThresholdColor(Color.RED);
		gauge.setThreshold(0);
		gauge.setThresholdVisible(true);
		gauge.setTickLabelColor(Color.GREY);
		gauge.setTickMarkColor(Color.BLACK);
		gauge.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);
		gauge.setPrefSize(140, 140);

		// Permet de diriger le vaisseau //
		Vaisseau v = sys.getVaisseau();
		keyListener = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (sys.getVaisseau() != null) {
					vc.dirigerVaisseau(event, gauge, v, sys);
				}
				event.consume();
			}
		};

		// Taille de la fenetre //
		stage.setWidth(300);
		stage.setHeight(800);
		stage.setOnCloseRequest(e -> e.consume());

		// Création de l'écran qui sert a voir le vaisseau en tout temps //
		if (sys.getVaisseau() != null) {
			Stage stage_vaisseau = new Stage();
			ecran_vaisseau = new Ecran(100, 100, this, "Vaisseau");
			stage.setTitle("Vaisseau");
			Scene scene_vaisseau = new Scene(ecran_vaisseau);
			scene_vaisseau.setOnKeyPressed(keyListener);
			stage_vaisseau.setScene(scene_vaisseau);
			stage_vaisseau.initStyle(StageStyle.UTILITY);
			stage_vaisseau.setOnCloseRequest(e -> e.consume());
			System.out.println("oui");
			stage_vaisseau.show();
			ecran_vaisseau.refresh(sys);
			ecran_vaisseau.setFocus(sys.getVaisseau());
		}

		vaisseau = SwingFXUtils.toFXImage(ImageIO.read(this.getClass().getResource("/resources/vaisseau.png")), null);
		final FileChooser fileChooser = new FileChooser();

		// Menu qui permet de choisir avec quel écran on interagit //
		choixEcran.setCellFactory(new Callback<ListView<Ecran>, ListCell<Ecran>>() {
			@Override
			public ListCell<Ecran> call(ListView<Ecran> lv) {
				return new SimpleEcranListCell();
			}
		});
		choixEcran.getItems().setAll(listEcran.keySet());
		choixEcran.setMaxHeight(100);

		// Permet d'ajouter ou de retirer des écrans //
		Label label_ecran = new Label("Gestion des Ecrans :");
		Button button_ajout_ecran = new Button("Ajouter");
		button_ajout_ecran.setOnAction(e -> {
			ajout_Ecran();
		});
		Button button_retrait_ecran = new Button("Retirer");
		button_retrait_ecran.setOnAction(e -> {
			if (!listEcran.isEmpty()) {
				supprime_Ecran(choixEcran.getFocusModel().getFocusedItem());
			} else {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Pas d'écran");
				errorAlert.setContentText("Il n'y a pas d'écran à supprimer !");
				errorAlert.showAndWait();
			}
		});
		HBox hbox_gestion_ecran = new HBox(button_ajout_ecran, button_retrait_ecran);
		VBox vBox_gestion_ecran = new VBox(label_ecran, hbox_gestion_ecran);

		// Info sur le vaisseau (coordonées, ect) //
		VBox infoVaisseau = new VBox();
		Label position = new Label("Position Vaisseau :");
		Label positionX = new Label("X = " + 0);
		Label positionY = new Label("Y = " + 0);
		Label vitX = new Label("vX = " + 0);
		Label vitY = new Label("vY = " + 0);
		infoVaisseau.getChildren().addAll(position, positionX, positionY, vitX, vitY);
		infoVaisseau.setStyle("-fx-background-color: #e6e6e6;");

		// Timer //
		Label timer = new Label("Timer :");
		timer.setStyle("-fx-text-fill:white;");
		Label temps = new Label("0");
		temps.setStyle("-fx-text-fill:white;");
		VBox time = new VBox();
		time.setAlignment(Pos.CENTER);
		time.setStyle("-fx-background-color:black;");
		time.setMinHeight(43);
		time.setMaxWidth(65);
		time.getChildren().addAll(timer, temps);

		Label objectifVoyager = new Label();
		Label objectifDetruire = new Label();

		// Gestion du focus //
		boxSuivre = new ComboBox<>();
		boxSuivre.setCellFactory(new Callback<ListView<Objet>, ListCell<Objet>>() {
			@Override
			public ListCell<Objet> call(ListView<Objet> listView) {
				return new RichObjetListCell();
			}
		});
		boxSuivre.setButtonCell(new SimpleObjetListCell());
		boxSuivre.getItems().add(libre);
		boxSuivre.getItems().addAll(sys.getSatellites());
		boxSuivre.valueProperty().addListener((src, ov, nv) -> {
			System.out.println(nv.getNom());
			if (nv.getNom().equals("Libre")) {
				choixEcran.getFocusModel().getFocusedItem().setFree();
				System.out.println("L'écran est libre");
			} else if (ov != nv)
				choixEcran.getFocusModel().getFocusedItem().setFocus(nv);
		});

		choixEcran.getSelectionModel().selectedItemProperty().addListener((src, ov, nv) -> {
			boxSuivre.setValue(nv.getFocus());
		});

		// Barre pour charger des fichiers astro//
		MenuBar menuBar = new MenuBar();
		menuBar.setStyle("-fx-alignment: center;");
		Menu fichier = new Menu("Fichier");
		MenuItem ouvrir = new MenuItem("Ouvrir");
		ouvrir.setOnAction(e -> {
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				try {
					Runtime.getRuntime().exec("java -jar simastro.jar " + file.getAbsolutePath());
					System.exit(0);
				} catch (IOException e1) {
					System.out.println("Erreur lors de l'ouverture du fichier.");
				}
			}
		});
		fichier.getItems().addAll(ouvrir);
		menuBar.getMenus().addAll(fichier);

		// Permet de changer les méthodes //
		HBox methodes = new HBox();
		Button methode = new Button("LeapFrog");
		methode.setOnAction(e -> {
			sc.setMethode(sys, new LeapFrog());
			System.out.println(sys.methode.getClass().getName());
		});
		Button methode2 = new Button("RK4");
		methode2.setOnAction(e -> {
			sc.setMethode(sys, new RungeKutta4());
			System.out.println(sys.methode.getClass().getName());
		});
		Button methode3 = new Button("EulerEx");
		methode3.setOnAction(e -> {
			sc.setMethode(sys, new EulerExplicite());
			System.out.println(sys.methode.getClass().getName());
		});
		methodes.getChildren().addAll(infoVaisseau, boxSuivre, methode, methode2, methode3);

		// Permet de gerer le pas de temps, de quitter ou de mettre en pause //

		Button quitter = new Button("Quitter");
		quitter.setOnAction(e -> {
			stage.close();
			System.exit(0);
		});

		Button pause = new Button("Pause/Lecture");
		pause.setOnAction(e -> {
			pc.setPause(sys);
		});

		Label dT = new Label("dT=" + sys.getdT());
		Button minDT = new Button("dT-");
		minDT.setOnAction(e -> {
			sc.minDt(sys);
			dT.setText("dT=" + sys.getdT());
		});

		Button plusDT = new Button("dT+");
		plusDT.setOnAction(e -> {
			sc.plusDt(sys);
			dT.setText("dT=" + sys.getdT());
		});

		HBox hbox_dt = new HBox(quitter, pause, minDT, plusDT, dT);

		/*
		 * 
		 * HBox methodes = new HBox();
		 * 
		 * areaSaut.addEventHandler(KeyEvent.KEY_PRESSED, e -> { try { // Si les touches
		 * tapees ne sont pas les touches de supression // if ((KeyCode.BACK_SPACE !=
		 * e.getCode()) && (KeyCode.DELETE != e.getCode())) {
		 * Integer.parseInt(areaSaut.getText()); // Sauvegarde de la valeur du textbox
		 * dans une variable newString = areaSaut.getText(); } } catch
		 * (NumberFormatException Exception) { // En cas d'exception (caractere non
		 * chiffre tape on reaffiche l'ancien text // areaSaut.selectAll();
		 * areaSaut.replaceSelection(newString); } });
		 * 
		 * buttonSaut.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> { double temp =
		 * sys.setfA(60); try { for (int i = 0; i <
		 * Integer.parseInt(areaSaut.getText()); i++) { for (Ecran ecran: listEcran) {
		 * ecran.refresh(sys); } } sys.setfA(temp); } catch (Exception ex) {
		 * sys.setfA(temp); } });
		 * 
		 * VBox tableauBordDroite = new VBox(); HBox hbox = new HBox();
		 * hbox.setStyle("-fx-background-color: white;"); hbox.setAlignment(Pos.CENTER);
		 * 
		 * 
		 * 
		 * Pane pane = new Pane(); pane.setStyle("-fx-background-color: black;");
		 * 
		 * 
		 * ImageView iv = new ImageView(rotatedImage); iv.setFitHeight(90);
		 * iv.setFitWidth(90);
		 * 
		 * tableauBordDroite.getChildren().addAll(time,gauge,iv);
		 * tableauBordDroite.setAlignment(Pos.CENTER);
		 * 
		 */
		// root.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40),
		// CornerRadii.EMPTY, Insets.EMPTY)));

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Runnable updater = new Runnable() {
					@Override
					public void run() {
						for (Ecran ecran : listEcran.keySet()) {
							ecran.refresh(sys);
						}
						if (ecran_vaisseau != null)
							ecran_vaisseau.refresh(sys);
						cc.checkCollision(sys, astresImages,Interface.this);

						if (sys.getRunning()) {
							double t = Double.parseDouble(temps.getText()) + sys.getdT() * sys.getfA();
							BigDecimal bd = new BigDecimal(t);
							bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
							temps.setText(bd.doubleValue() + "");
							if (v != null) {
								positionX.setText("X = " + Math.round(v.getPosx()));
								positionY.setText("Y = " + Math.round(v.getPosy()));
								vitX.setText("vX = " + v.getVitx());
								vitY.setText("vY = " + v.getVity());
								// iv.setImage(rotatedImage);
								if (sys.getDistVoyager() > -1) {
									if (!sys.voyagerFini())
										objectifVoyager.setText("Vous devez quitter la galaxie\n");
									else
										objectifVoyager.setText("Vous avez réussi à partir !");
								}
								if (!sys.getCible().equals("")) {
									if (!sys.cibleDetruite())
										objectifDetruire.setText("Détruisez la cible : " + sys.getCible());
									else
										objectifDetruire.setText("Vous avez détruit la cible !");
								}
							}

						}

					}
				};
				while (true) {
					try {
						if (sys.getdT() * 1000 / sys.getfA() >= 3)
							Thread.sleep((long) (sys.getdT() * 1000 / sys.getfA()));
						else
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
		stage.setScene(scene);
		stage.setTitle("Simastro");
		VBox vbox_menu = new VBox();
		vbox_menu.getChildren().addAll(menuBar, vBox_gestion_ecran, choixEcran, boxSuivre, methodes, gauge, hbox_dt,
				infoVaisseau, time, objectifVoyager, objectifDetruire);
		root.getChildren().addAll(vbox_menu);
		stage.setFullScreen(false);
		stage.show();

		// ajout d'un Ecran par défaut //
		ajout_Ecran();
		// Petit refresh pour le plaisir //
		for (Ecran ecran : listEcran.keySet()) {
			ecran.refresh(sys);
		}

	}

	/**
	 * Genère une erreur si un fichier n'a pas pu être chargé
	 */
	public void generateError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Erreur !");
		alert.setContentText("Ooops, un fichier n'a pas pu être chargé!");
		alert.showAndWait();
	}

	public class SimpleObjetListCell extends ListCell<Objet> {

		@Override
		protected void updateItem(Objet item, boolean empty) {
			super.updateItem(item, empty);
			DecimalFormat f = new DecimalFormat();
			f.setMaximumFractionDigits(3);
			setText(null);
			if (!empty && item != null) {
				final String text = String.format("%s : %s - %s", item.getNom(), f.format(item.getPosx()),
						f.format(item.getPosy()));
				setText(text);
			}
		}
	}

	public class SimpleEcranListCell extends ListCell<Ecran> {

		@Override
		protected void updateItem(Ecran item, boolean empty) {
			super.updateItem(item, empty);
			setText(null);
			if (!empty && item != null) {
				final String text = String.format("%s Scale : %s", item.getNom(), item.getScale());
				setText(text);
			}
		}
	}

	public class RichObjetListCell extends ListCell<Objet> {

		private final BorderPane borderPane = new BorderPane();
		private final ImageView imageView_objet = new ImageView();
		private final Label nom = new Label();
		private final HBox hbox_coordonne = new HBox();
		private final Label coordoneeX = new Label();
		private final Label coordoneeY = new Label();

		public RichObjetListCell() {
			imageView_objet.setFitWidth(75);
			imageView_objet.setPreserveRatio(true);
			nom.setStyle("-fx-font-weight: bold; -fx-font-size: 1.5em; -fx-opacity: 1.0;");
			coordoneeX.setStyle("-fx-font-size: 0.9em; -fx-font-style: italic; -fx-opacity: 0.5;");
			coordoneeY.setStyle("-fx-font-size: 0.9em; -fx-font-style: italic; -fx-opacity: 0.5;");
			hbox_coordonne.getChildren().addAll(coordoneeX, coordoneeY);
			borderPane.setLeft(imageView_objet);
			borderPane.setRight(nom);
			borderPane.setBottom(hbox_coordonne);
			// descriptionLabel.setStyle("-fx-opacity: 0.75;");
			// descriptionLabel.setGraphic(colorRect);
		}

		@Override
		protected void updateItem(Objet item, boolean empty) {
			super.updateItem(item, empty);
			setGraphic(null);
			setText(null);
			setContentDisplay(ContentDisplay.LEFT);
			if (!empty && item != null) {
				nom.setText(item.getNom());
				coordoneeX.setText("X : " + item.getPosx());
				coordoneeY.setText("Y : " + item.getPosy());
				imageView_objet.setImage(associate_Image(item));
				setText(null);
				setGraphic(borderPane);
			}
		}
	}

	private void ajout_Ecran(double x, double y) {
		Stage stage = new Stage();
		int nbEcran = listEcran.size();
		Ecran ecran = new Ecran(x, y, this, "Ecran" + nbEcran);
		stage.setTitle("Ecran" + nbEcran);
		Scene scene = new Scene(ecran);
		listEcran.put(ecran, stage);
		choixEcran.getItems().setAll(listEcran.keySet());
		stage.setScene(scene);
		stage.initStyle(StageStyle.UTILITY);
		stage.setOnCloseRequest(e -> e.consume());
		scene.setOnKeyPressed(keyListener);
		stage.show();
	}

	private void ajout_Ecran() {
		ajout_Ecran(200, 200);
	}

	private boolean supprime_Ecran(Ecran ecran) {
		if (listEcran.containsKey(ecran)) {
			listEcran.get(ecran).hide();
			listEcran.remove(ecran);
			choixEcran.getItems().setAll(listEcran.keySet());
			return true;
		}
		return false;
	}

	public Image associate_Image(Objet objet) {
		try {
			if (objet.getClass() == Soleil.class) {
				return SwingFXUtils.toFXImage(ImageIO.read(this.getClass().getResource("/resources/soleil.png")), null);

			} else if (objet.getClass() == Vaisseau.class) {
				return SwingFXUtils.toFXImage(ImageIO.read(this.getClass().getResource("/resources/vaisseau.png")),
						null);
			} else {
				return SwingFXUtils.toFXImage(ImageIO.read(this.getClass().getResource("/resources/planete.png")),
						null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
