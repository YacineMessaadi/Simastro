package Affichage;

import Modele.Objets.*;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class poubelle {
/*
    // La fonction refresh va nettoyer le canvas et le remplir à l'aide de toutes
    // les coordonées des astres à l'instant où il est appelé,
    // c'est pour cela qu'il a besoin d'un Systeme en param,
    // ATTENTION quand vous le modifiez !
    // La position (SUR LE CANVAS) d'un objet est calculé en prenant en compte :
    // [Sa position recalculée par rapport à la taille de l'ecran],
    // [l'axeX et Y qui correspondent au décallage dû au déplacement à l'aide de la
    // souris] et [le Scale]

    public void refresh(Systeme s) {
        if (boxSuivre.getValue() != libre) {
            axeX = -boxSuivre.getValue().getPosx();
            axeY = -boxSuivre.getValue().getPosy();
        }
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ArrayList<Objet> listObjet = s.getSatellites();
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        astresImages.clear();

        for (Objet o : listObjet) {
            double moitieX = (canvas.getWidth() / 2) / scale;
            double moitieY = (canvas.getHeight() / 2) / scale;
            canvas.getGraphicsContext2D().drawImage(etoileImage, Double.MAX_VALUE, Double.MAX_VALUE);
            if (o instanceof Fixe) {
                graphicsContext.drawImage(soleil, (o.getPosx() + moitieX + axeX) * scale - (o.getMasse()) * scale / 2,
                        (o.getPosy() + moitieY + axeY) * scale - (o.getMasse()) * scale / 2, (o.getMasse()) * scale,
                        (o.getMasse()) * scale);
                graphicsContext.setFill(Color.BLUE);
                graphicsContext.fillText(o.getNom(), (o.getPosx() + moitieX + axeX) * scale,
                        (o.getPosy() + moitieY + axeY) * scale);
            } else if (o instanceof Vaisseau) {
                ImageView iv = new ImageView(vaisseau);
                iv.setRotate(Math.toDegrees(((Vaisseau)o).getAngle()));
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                rotatedImage = iv.snapshot(params, null);
                graphicsContext.drawImage(rotatedImage,
                        (o.getPosx() + moitieX + axeX) * scale - (o.getMasse()) * scale / 2,
                        (o.getPosy() + moitieY + axeY) * scale - (o.getMasse()) * scale / 2,
                        (o.getMasse() * 2000) * scale, (o.getMasse() * 2000) * scale);

                // PreCalcul
                graphicsContext.drawImage(rotatedImage,
                        (((Vaisseau)o).getPresPosX() + moitieX + axeX) * scale - (o.getMasse()) * scale / 2,
                        (((Vaisseau)o).getPresPosY() + moitieY + axeY) * scale - (o.getMasse()) * scale / 2,
                        (o.getMasse() * 2000) * scale, (o.getMasse() * 2000) * scale);
                // PreCalcul

                graphicsContext.beginPath();
                graphicsContext.setStroke(Color.BLUE);
                graphicsContext.moveTo((o.getPosx() + moitieX + axeX) * scale,
                        (o.getPosy() + moitieY + axeY) * scale);
                graphicsContext.lineTo(((((Vaisseau)o).getPresPosX() + moitieX + axeX) * scale),
                        (((Vaisseau)o).getPresPosY() + moitieY + axeY) * scale);

                graphicsContext.stroke();

                graphicsContext.setFill(Color.BLUE);
                graphicsContext.fillText(o.getNom(), (o.getPosx() + moitieX + axeX) * scale,
                        (o.getPosy() + moitieY + axeY) * scale);
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
                graphicsContext.moveTo((((Simule) o).getTrail().peek().getX() + moitieX + axeX) * scale,
                        (((Simule) o).getTrail().peek().getY() + moitieY + axeY) * scale);
                for (Position position : ((Simule) o).getTrail()) {
                    graphicsContext.lineTo(((position.getX() + moitieX + axeX) * scale),
                            (position.getY() + moitieY + axeY) * scale);
                }
                graphicsContext.stroke();
                if(o.getNom().equals("Missile")) {
                    graphicsContext.drawImage(planete, (o.getPosx() + moitieX + axeX) * scale - (o.getMasse()) * scale / 2,
                            (o.getPosy() + moitieY + axeY) * scale - (o.getMasse()) * scale / 2, (o.getMasse()*2000) * scale,
                            (o.getMasse() * 2000) * scale);
                }
                else graphicsContext.drawImage(planete, (o.getPosx() + moitieX + axeX) * scale - (o.getMasse()) * scale / 2,
                        (o.getPosy() + moitieY + axeY) * scale - (o.getMasse()) * scale / 2, (o.getMasse()) * scale,
                        (o.getMasse()) * scale);
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillText(o.getNom(), (o.getPosx() + moitieX + axeX) * scale,
                        (o.getPosy() + moitieY + axeY) * scale);
            }
            else if(o instanceof Objet) {
                if (((Ellipse) o).getTrail().size() >= ((Ellipse) o).getListSize()) {
                    ((Ellipse) o).getTrail().poll();
                    ((Ellipse) o).getTrail().add(new Position(o.getPosx(), o.getPosy()));
                } else {
                    ((Ellipse) o).getTrail().add(new Position(o.getPosx(), o.getPosy()));
                }
                graphicsContext.beginPath();
                graphicsContext.setStroke(Color.color(((Ellipse) o).getTrailColor()[0], ((Ellipse) o).getTrailColor()[1],
                        ((Ellipse) o).getTrailColor()[2]));
                graphicsContext.moveTo((((Ellipse) o).getTrail().peek().getX() + moitieX + axeX) * scale,
                        (((Ellipse) o).getTrail().peek().getY() + moitieY + axeY) * scale);
                for (Position position : ((Ellipse) o).getTrail()) {
                    graphicsContext.lineTo(((position.getX() + moitieX + axeX) * scale),
                            (position.getY() + moitieY + axeY) * scale);
                }
                graphicsContext.stroke();
                graphicsContext.drawImage(planete, (o.getPosx() + moitieX + axeX) * scale - (o.getMasse()) * scale / 2,
                        (o.getPosy() + moitieY + axeY) * scale - (o.getMasse()) * scale / 2, (o.getMasse()) * scale,
                        (o.getMasse()) * scale);
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillText(o.getNom(), (o.getPosx() + moitieX + axeX) * scale,
                        (o.getPosy() + moitieY + axeY) * scale);
            }
            Circle c = new Circle((o.getPosx() + moitieX + axeX) * scale, (o.getPosy() + moitieY + axeY) * scale,
                    (o.getMasse()) * scale / 2);
            astresImages.put(o, c);
        }
    }
 */
}
