package Vue;

import Main.Main;
import Modele.StatusCase;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DessinCase extends Rectangle {

    private int posX;
    private int posY;
    private StatusCase statusCase;
    public static Color couleurVide = Color.BLACK;
    public static Color couleurMur = Color.WHITE;
    public static Color couleurMessage = Color.YELLOW;
    public static Color couleurSortie = Color.BLUE;

    public DessinCase(int posX, int posY, StatusCase statusCase) {

        super((posX * Main.tailleCase + Main.tailleCase), (posY * Main.tailleCase + Main.tailleCase), Main.tailleCase, Main.tailleCase);
        this.posX = posX;
        this.posY = posY;
        this.statusCase = statusCase;
        this.appliqueCouleur();
    }

    private void appliqueCouleur() {
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
                this.setFill(DessinCase.couleurMessage);
                break;
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
}
