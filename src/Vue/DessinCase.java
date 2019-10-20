package Vue;

import Main.Main;
import Modele.StatusCase;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DessinCase extends Rectangle {

    public static Color couleurCacher = Color.BLACK;
    public static Color couleurVide = Color.LIGHTGRAY;
    public static Color couleurMur = Color.WHITE;
    public static Color couleurMessage = Color.YELLOW;
    public static Color couleurSortie = Color.BLUE;
    private int posX;
    private int posY;
    private StatusCase statusCase;
    private boolean decouvert = false;
    private boolean lue = false;

    public DessinCase(int posX, int posY, StatusCase statusCase) {

        super((posX * Main.tailleCase + Main.tailleCase), (posY * Main.tailleCase + Main.tailleCase), Main.tailleCase, Main.tailleCase);
        this.posX = posX;
        this.posY = posY;
        this.statusCase = statusCase;
        this.appliqueCouleur();
    }

    public void appliqueCouleur() {
        if (this.decouvert) {
            switch (this.statusCase) {
                case SORTIE:
                    this.setFill(DessinCase.couleurSortie);
                    break;
                case MUR:
                    this.setFill(DessinCase.couleurMur);
                    break;
                case VIDE:
                    this.setFill(DessinCase.couleurVide);
                    break;
                case MESSAGE:
                    this.setFill((this.lue) ? DessinCase.couleurMessage : DessinCase.couleurMur);
                    break;
            }
        } else {
            this.setFill(DessinCase.couleurCacher);
        }
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public StatusCase getStatusCase() {
        return statusCase;
    }

    public void setStatusCase(StatusCase statusCase) {
        this.statusCase = statusCase;
    }

    public boolean isDecouvert() {
        return decouvert;
    }

    public void setDecouvert(boolean decouvert) {
        this.decouvert = decouvert;
    }

    public boolean isLue() {
        return lue;
    }

    public void setLue(boolean lue) {
        this.lue = lue;
    }
}
