package Affichage;

import Util.Sauvegarde;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class Interface extends Application{


	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		final FileChooser fileChooser = new FileChooser();
		BorderPane root = new BorderPane();
		VBox tableauBordGauche = new VBox();
		VBox tableauBordDroite = new VBox();
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		
				
		MenuBar menuBar = new MenuBar();
		menuBar.setStyle("-fx-alignment: center;");		
		Menu fichier = new Menu("Fichier");
		Menu edition = new Menu("Edition");
		Menu aide = new Menu("Aide");
		MenuItem nouveau=new MenuItem("Nouveau");
		nouveau.setOnAction(e->{

		});

		MenuItem ouvrir = new MenuItem("Ouvrir");
		ouvrir.setOnAction(e ->{
			File file = fileChooser.showOpenDialog(stage);
			if(file!=null){
				Sauvegarde save = new Sauvegarde(file);
				root.setCenter(Screen.generateGroup(save));
			}
		});

		MenuItem enregistrer = new MenuItem("Enregistrer");
		MenuItem enregistrerSous = new MenuItem("Enregistrer Sous");
		fichier.getItems().addAll(nouveau,ouvrir,enregistrer,enregistrerSous);
		menuBar.getMenus().addAll(fichier,edition,aide);
		
		Pane pane = new Pane();	
		pane.setStyle("-fx-background-color: black;");
		Canvas canvas = new Canvas();
		
		Label position = new Label("Position du vaisseau ...");
		tableauBordGauche.getChildren().add(position);
		
		Label autre = new Label("Autre information ...");
		tableauBordDroite.getChildren().add(autre);
		
		Button quitter = new Button("Quitter");
		quitter.setOnAction(e->{
			stage.close();
		});
		hbox.getChildren().add(quitter);
		
		pane.getChildren().add(canvas);
		root.setTop(menuBar);
		root.setLeft(tableauBordGauche);
		root.setRight(tableauBordDroite);
		root.setCenter(pane);
		root.setBottom(hbox);
		
		Scene scene = new Scene(root, 900, 700);
		stage.setScene(scene);
		stage.setTitle("Simastro");
		stage.show();
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

}
