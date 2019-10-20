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

    /**
     * costructeur par defaut
     *
     * @param posX
     * @param posY
     * @param statusCase
     */
    public DessinCase(int posX, int posY, StatusCase statusCase) {

        super((posX * Main.tailleCase + Main.tailleCase), (posY * Main.tailleCase + Main.tailleCase), Main.tailleCase, Main.tailleCase);
        this.posX = posX;
        this.posY = posY;
        this.statusCase = statusCase;
        this.appliqueCouleur();
    }

    /**
     * applique la couleur de la case en fonction du statut et du fait quelle est etais vue ou lue
     */
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

    /**
     * retourne la postion en x de la case
     * @return x
     */
    public int getPosX() {
        return posX;
    }

    /**
     * retourne la postion en y de la case
     * @return y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * modifie le fait de la découvert
     * @param decouvert
     */
    public void setDecouvert(boolean decouvert) {
        this.decouvert = decouvert;
    }

    /**
     * modifie le fait que la case soit lue
     * @param lue
     */
    public void setLue(boolean lue) {
        this.lue = lue;
    }
}
