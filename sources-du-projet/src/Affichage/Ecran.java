package Affichage;

import Modele.Objets.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Ecran extends Pane {

	// WOW Tellement de param ! laisse moi te guider //

	final Interface intf;

	// On charge d'abord toute les images dont on a besoin
	private Image imageSoleilStandard;
	private Image imagePlaneteStandard;
	private Image imageVaisseauStandard;
	private Image imageFondStandard;

	private boolean libre;
	private Objet focus;
	private double scale;
	private double decalageX;
	private double decalageY;
	private Canvas canvas;
	private double moitieX;
	private double moitieY;
	private double xStart = 0;
	private double yStart = 0;
	public String nom;

	public Ecran(double x, double y, Interface intf, String nom) {
		this.nom = nom;
		this.intf = intf;
		libre = true;
		focus = new Simule("Focus", 0, 0, 0, 0, 0);
		scale = 1;
		this.setWidth(x);
		this.setHeight(y);
		canvas = new Canvas();
		canvas.widthProperty().bind(this.widthProperty());
		canvas.heightProperty().bind(this.heightProperty());

		moitieX = (canvas.getWidth() / 2) / scale;
		moitieY = (canvas.getHeight() / 2) / scale;

		try {
			imageSoleilStandard = SwingFXUtils
					.toFXImage(ImageIO.read(this.getClass().getResource("/resources/soleil.png")), null);
			imageVaisseauStandard = SwingFXUtils
					.toFXImage(ImageIO.read(this.getClass().getResource("/resources/vaisseau.png")), null);
			imageFondStandard = SwingFXUtils
					.toFXImage(ImageIO.read(this.getClass().getResource("/resources/espace.jpg")), null);
			imagePlaneteStandard = SwingFXUtils
					.toFXImage(ImageIO.read(this.getClass().getResource("/resources/planete.png")), null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// permet de zoomer //
		canvas.setOnScroll(e -> {
			if (e.getDeltaY() > 0) {
				scale *= 1.1;
			} else if (e.getDeltaY() < 0) {
				scale *= 0.9;
			}
			e.consume();
		});

		// permet de deplacer la vue sur un écran, mais a besoin de deux parametre

		canvas.setOnMousePressed(event -> {
			canvas.requestFocus();
			xStart = event.getSceneX();
			yStart = event.getSceneY();
			event.consume();
		});

		canvas.setOnMouseDragged(event -> {

			decalageX -= ((xStart - event.getSceneX())) / scale;
			decalageY -= ((yStart - event.getSceneY())) / scale;
			xStart = event.getSceneX();
			yStart = event.getSceneY();
		});
		BackgroundImage backgroundStandard = new BackgroundImage(imageFondStandard, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		this.setBackground(new Background(backgroundStandard));
		this.getChildren().add(canvas);
	}

	public Ecran(Interface intf, Canvas canvas, String nom) {
		this(100, 100, intf, nom);
		this.canvas = canvas;
	}

	public double calcX(Objet o) {
		return (((o.getPosx() + moitieX + decalageX) * scale) - ((o.getMasse()) * scale / 2));
	}

	public double calcY(Objet o) {
		return (((o.getPosy() + moitieY + decalageY) * scale) - ((o.getMasse()) * scale / 2));
	}

	public double calcH(Objet o, double bonus) {
		// Au vue du fait que les fenetres sont TOUJOURS carré, on n'a pas besoin de
		// differencier hauteur et largeur;
		return ((o.getMasse() * bonus) * scale);
	}

	public double calcH(Objet o) {
		// Au vue du fait que les fenetres sont TOUJOURS carré, on n'a pas besoin de
		// differencier hauteur et largeur;
		return calcH(o, 1);
	}

	public double calcW(Objet o, double bonus) {
		// Au vue du fait que les fenetres sont TOUJOURS carré, on n'a pas besoin de
		// differencier hauteur et largeur;
		return ((o.getMasse() * bonus) * scale);
	}

	public double calcW(Objet o) {
		// Au vue du fait que les fenetres sont TOUJOURS carré, on n'a pas besoin de
		// differencier hauteur et largeur;
		return calcW(o, 1);
	}

	// La fonction refresh va nettoyer le canvas et le remplir à l'aide de toutes
	// les coordonées des astres à l'instant où il est appelé,
	// c'est pour cela qu'il a besoin d'un Systeme en param,
	// ATTENTION quand vous le modifiez !
	// La position (SUR LE CANVAS) d'un objet est calculé en prenant en compte :
	// [Sa position recalculée par rapport à la taille de l'ecran],
	// [le decalageX et Y qui correspondent au décallage dû au déplacement à l'aide
	// de la
	// souris] et [le Scale]
	public void refresh(Systeme s) {
		if (!libre) {
			decalageX = -focus.getPosx();
			decalageY = -focus.getPosy();
		}
		moitieX = (canvas.getWidth() / 2) / scale;
		moitieY = (canvas.getHeight() / 2) / scale;

		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		ArrayList<Objet> listObjet = s.getSatellites();
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		intf.astresImages.clear();

		for (Objet o : listObjet) {
			canvas.getGraphicsContext2D().drawImage(imageFondStandard, Double.MAX_VALUE, Double.MAX_VALUE);
			if (o instanceof Soleil) {
				graphicsContext.drawImage(imageSoleilStandard, calcX(o), calcY(o), calcW(o), calcH(o));
				graphicsContext.setFill(Color.BLUE);
				graphicsContext.fillText(o.getNom(), (o.getPosx() + moitieX + decalageX) * scale,
						(o.getPosy() + moitieY + decalageY) * scale);
			} else if (o instanceof Vaisseau) {
				ImageView iv = new ImageView(imageVaisseauStandard);
				iv.setRotate(Math.toDegrees(((Vaisseau) o).getAngle()));
				SnapshotParameters params = new SnapshotParameters();
				params.setFill(Color.TRANSPARENT);
				Image rotatedImage = iv.snapshot(params, null);
				graphicsContext.drawImage(rotatedImage, calcX(o), calcY(o), (o.getMasse() * 4000) * scale,
						(o.getMasse() * 4000) * scale);

				// PreCalcul
				graphicsContext.drawImage(rotatedImage,
						(((Vaisseau) o).getPresPosX() + moitieX + decalageX) * scale - (o.getMasse()) * scale / 2,
						(((Vaisseau) o).getPresPosY() + moitieY + decalageY) * scale - (o.getMasse()) * scale / 2,
						(o.getMasse() * 2000) * scale, (o.getMasse() * 2000) * scale);
				// PreCalcul

				graphicsContext.beginPath();
				graphicsContext.setStroke(Color.BLUE);
				graphicsContext.moveTo((o.getPosx() + moitieX + decalageX) * scale,
						(o.getPosy() + moitieY + decalageY) * scale);
				graphicsContext.lineTo(((((Vaisseau) o).getPresPosX() + moitieX + decalageX) * scale),
						(((Vaisseau) o).getPresPosY() + moitieY + decalageY) * scale);

				graphicsContext.stroke();

				graphicsContext.setFill(Color.BLUE);
				graphicsContext.fillText(o.getNom(), (o.getPosx() + moitieX + decalageX) * scale,
						(o.getPosy() + moitieY + decalageY) * scale);
			} else if (o instanceof Simule) {
				if (((Simule) o).getTrail().size() >= ((Simule) o).getListSize()) {
					((Simule) o).getTrail().poll();
					((Simule) o).getTrail().add(new Position(o.getPosx(), o.getPosy()));
				} else {
					((Simule) o).getTrail().add(new Position(o.getPosx(), o.getPosy()));
				}
				graphicsContext.beginPath();
				graphicsContext.setStroke(Color.color(((Simule) o).getTrailColor()[0], ((Simule) o).getTrailColor()[1],
						((Simule) o).getTrailColor()[2]));
				assert ((Simule) o).getTrail().peek() != null;
				assert ((Simule) o).getTrail().peek() != null;
				graphicsContext.moveTo((((Simule) o).getTrail().peek().getX() + moitieX + decalageX) * scale,
						(((Simule) o).getTrail().peek().getY() + moitieY + decalageY) * scale);
				for (Position position : ((Simule) o).getTrail()) {
					graphicsContext.lineTo(((position.getX() + moitieX + decalageX) * scale),
							(position.getY() + moitieY + decalageY) * scale);
				}
				graphicsContext.stroke();
				if (o.getNom().equals("Missile")) {
					graphicsContext.drawImage(imagePlaneteStandard, calcX(o), calcY(o), (o.getMasse() * 20000) * scale,
							(o.getMasse() * 20000) * scale);
				} else
					graphicsContext.drawImage(imagePlaneteStandard, calcX(o), calcY(o), calcW(o), calcH(o));
				graphicsContext.setFill(Color.WHITE);
				graphicsContext.fillText(o.getNom(), (o.getPosx() + moitieX + decalageX) * scale,
						(o.getPosy() + moitieY + decalageY) * scale);
			}
			else if(o instanceof Ellipse) {
				if (((Ellipse) o).getTrail().size() >= ((Ellipse) o).getListSize()) {
					((Ellipse) o).getTrail().poll();
					((Ellipse) o).getTrail().add(new Position(o.getPosx(), o.getPosy()));
				} else {
					((Ellipse) o).getTrail().add(new Position(o.getPosx(), o.getPosy()));
				}
				graphicsContext.beginPath();
				graphicsContext.setStroke(Color.color(((Ellipse) o).getTrailColor()[0], ((Ellipse) o).getTrailColor()[1],
						((Ellipse) o).getTrailColor()[2]));
				graphicsContext.moveTo((((Ellipse) o).getTrail().peek().getX() + moitieX + decalageX) * scale,
						(((Ellipse) o).getTrail().peek().getY() + moitieY + decalageY) * scale);
				for (Position position : ((Ellipse) o).getTrail()) {
					graphicsContext.lineTo(((position.getX() + moitieX + decalageX) * scale),
							(position.getY() + moitieY + decalageY) * scale);
				}
				graphicsContext.stroke();
				 graphicsContext.drawImage(imagePlaneteStandard, (o.getPosx() + moitieX + decalageX) * scale - (o.getMasse()) * scale / 2,
						(o.getPosy() + moitieY + decalageY) * scale - (o.getMasse()) * scale / 2, (o.getMasse()) * scale,
						(o.getMasse()) * scale);
				graphicsContext.setFill(Color.WHITE);
				graphicsContext.fillText(o.getNom(), (o.getPosx() + moitieX + decalageX) * scale,
						(o.getPosy() + moitieY + decalageY) * scale);
			}

			Circle c = new Circle((o.getPosx() + moitieX + decalageX) * scale,
					(o.getPosy() + moitieY + decalageY) * scale, (o.getMasse()) * scale / 2);
			intf.astresImages.put(o, c);
		}
	}

	public void setFocus(Objet objet) {
		focus = objet;
		libre = false;
	}

	public void setFree() {
		libre = true;
	}

	public Objet getFocus() {
		return focus;
	}

	public String getNom() {
		return nom;
	}

	public double getScale() {
		return scale;
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
