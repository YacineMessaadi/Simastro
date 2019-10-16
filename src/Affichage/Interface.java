package Affichage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import Lancement.CollisionController;
import Lancement.VaisseauControler;
import Modele.Sauvegarde;
import Modele.Objets.Objet;
import Modele.Objets.Soleil;
import Modele.Objets.Systeme;
import Modele.Objets.Vaisseau;

public class Interface extends Application {
    /**
     * BorderPane root : BorderPane directement à la base de la scene.
     * Systeme sys :Le systeme affiché au centre du root à l'aide d'un canvas, lui meme dans un Pane.
     * NOTE : Le pane est facultatif il permet juste de gerer plus facilement les limites du canvas Image soleil : Image par defaut d'un soleil.
     * Image planete : Image par defaut d'une planete. Image vaisseau : Image par defaut d'un vaisseau.
     * Image etoileImage : Image par defaut du fond.
     * Canvas canvas : canvas qui va servir à l'affichage du systeme.
     * double axeX : le decalage à appliquer à tous les objets en X lors de l'affichage, il permet de simuler un deplacement de la vue
     * double axeY : le decalage à appliquer à tous les objets en Y lors de l'affichage, il permet de simuler un deplacement de la vue
     * double scale : le parametre qui permet de simuler le zoom, on multiplie la taille des objets par le scale, mais egalement la position
     */
    BorderPane root = new BorderPane();
    public Systeme sys;
    Image soleil = new Image("file:resources/soleil.png");
    Image planete = new Image("file:resources/planete.png");
    Image vaisseau = new Image("file:resources/vaisseau.png");
    VaisseauControler vc = new VaisseauControler();
    CollisionController cc = new CollisionController();
    Canvas canvas = new Canvas(500, 500);
    Canvas trace = new Canvas(500, 500);
    Image etoileImage = new Image("file:resources/espace.jpg");
    double axeX;
    double axeY;

    HashMap<Objet, Circle> astresImages = new HashMap<Objet, Circle>();

    public double scale = 1;

    // Quand on fait OnMousePressed sur le canvas, on enregistre la position du curseur pour calculer le décalage à appliquer

    private double xStart = 0;
    private double yStart = 0;
    // Sert à savoir si l'affichage suit actuellement un objet, pour desactiver le deplacement par exemple

    private boolean following = false;

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
         * ouvrir.setOnAction(e -> { File file = fileChooser.showOpenDialog(stage); if
         * (file != null) { Sauvegarde save = new Sauvegarde(file);
         * root.setCenter(generateCanvas(save)); } });
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

        // BackgroundImage etoileImageBackground = new BackgroundImage(etoileImage,
        // BackgroundRepeat.REPEAT,
        // BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        // pane.setBackground(new Background(etoileImageBackground));

        // root.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40),
        // CornerRadii.EMPTY, Insets.EMPTY)));

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        cc.checkCollision(sys, astresImages);
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

    // La fonction refresh va nettoyer le canvas et le remplir à l'aide de toutes les coordonées des astres à l'instant où il est appelé,
    // c'est pour cela qu'il a besoin d'un Systeme en param,
    // ATTENTION quand vous le modifiez !
    // La position (SUR LE CANVAS) d'un objet est calculé en prenant en compte :
    // [Sa position recalculée par rapport à la taille de l'ecran],
    // [l'axeX et Y qui correspondent au décallage dû au déplacement à l'aide de la souris] et [le Scale]

    public void refresh(Systeme s) {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ArrayList<Objet> listObjet = s.getSatellites();
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        astresImages.clear();

        for (Objet o : listObjet) {
            double moitieX = (canvas.getWidth() / 2) / scale;
            double moitieY = (canvas.getHeight() / 2) / scale;
            canvas.getGraphicsContext2D().drawImage(etoileImage, Double.MAX_VALUE, Double.MAX_VALUE);
            if (o instanceof Soleil) {
                graphicsContext.drawImage(soleil, (o.getPosx() + moitieX + axeX) * scale - (o.getMasse()) * scale / 2,
                        (o.getPosy() + moitieY + axeY) * scale - (o.getMasse()) * scale / 2, (o.getMasse()) * scale,
                        (o.getMasse()) * scale);
                graphicsContext.setFill(Color.BLUE);
                graphicsContext.fillText("Soleil", (o.getPosx() + moitieX + axeX) * scale,
                        (o.getPosy() + moitieY + axeY) * scale);
            } else if (o instanceof Vaisseau) {
                graphicsContext.drawImage(vaisseau, (o.getPosx() + moitieX + axeX) * scale - (o.getMasse()) * scale / 2,
                        (o.getPosy() + moitieY + axeY) * scale - (o.getMasse()) * scale / 2, (o.getMasse()) * scale,
                        (o.getMasse()) * scale);
                graphicsContext.setFill(Color.BLUE);
                graphicsContext.fillText("Vaisseau", (o.getPosx() + moitieX + axeX) * scale,
                        (o.getPosy() + moitieY + axeY) * scale);
            } else {
                graphicsContext.drawImage(planete, (o.getPosx() + moitieX + axeX) * scale - (o.getMasse()) * scale / 2,
                        (o.getPosy() + moitieY + axeY) * scale - (o.getMasse()) * scale / 2, (o.getMasse()) * scale,
                        (o.getMasse()) * scale);
                graphicsContext.setFill(Color.GREEN);
                graphicsContext.fillText("Planète", (o.getPosx() + moitieX + axeX) * scale,
                        (o.getPosy() + moitieY + axeY) * scale);
                trace.getGraphicsContext2D().fillRect((o.getPosx() + moitieX + axeX) * scale,
                        (o.getPosy() + moitieY + axeY) * scale, 1, 1);
            }
            Circle c = new Circle((o.getPosx() + moitieX + axeX) * scale, (o.getPosy() + moitieY + axeY) * scale,
                    (o.getMasse()) * scale / 2);
            astresImages.put(o, c);
        }
    }

    /**
     * Genere un Pane qui encapsule un Canvas, lui même genéré grâce au Systeme donné en param
     *
     * @param s
     * @return Pane
     */
    public Pane generateCanvas(Systeme s) {
        Pane cage = new Pane();
        cage.getChildren().add(canvas);
        cage.getChildren().add(trace);
        trace.setMouseTransparent(true);
        canvas.minHeight(0);
        canvas.minWidth(0);
        canvas.prefHeight(500);
        canvas.prefWidth(500);
        trace.minHeight(0);
        trace.minWidth(0);
        trace.prefHeight(500);
        trace.prefWidth(500);
        refresh(s);
        canvas.widthProperty().bind(cage.widthProperty());
        canvas.heightProperty().bind(cage.heightProperty());
        trace.widthProperty().bind(cage.widthProperty());
        trace.heightProperty().bind(cage.heightProperty());

        canvas.setOnScroll(e -> {
            // scale += (e.getDeltaY()/1000);

            if (e.getDeltaY() > 0)
                scale *= 1.1;
            else if (e.getDeltaY() < 0)
                scale *= 0.9;
            trace.getGraphicsContext2D().clearRect(0, 0, trace.getWidth(), trace.getHeight());
            refresh(s);
            e.consume();
        });

        canvas.setOnMousePressed(event -> {
            xStart = event.getSceneX();
            yStart = event.getSceneY();
            System.out.println("X:" + xStart + " Y:" + yStart);
            event.consume();
        });

        canvas.setOnMouseDragged(event -> {

            axeX -= ((xStart - event.getSceneX())) / scale;
            axeY -= ((yStart - event.getSceneY())) / scale;
            System.out.println("axeX:" + axeX + "axeY:" + axeY);
            trace.getGraphicsContext2D().clearRect(0, 0, trace.getWidth(), trace.getHeight());
            refresh(s);
            xStart = event.getSceneX();
            yStart = event.getSceneY();
        });

        // canvas.setTranslateX(cage.getLayoutX()/2);
        // canvas.setTranslateY(cage.getLayoutY()/2);

        return cage;
    }

    /**
     * Genère un Pane qui encapsule un Canvas, lui même genéré grâce à la sauvegarde en param
     *
     * @param sauvegarde
     * @return Pane
     */
    public Pane generateCanvas(Sauvegarde sauvegarde) {
        Systeme sys = sauvegarde.charger();
        return generateCanvas(sys);
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
}