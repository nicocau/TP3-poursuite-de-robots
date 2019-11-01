package Vue;

import Main.Main;
import Modele.StatusCase;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DessinCase extends Rectangle {

    /**
     * couleur de la casse quand elle est cacher
     */
    public static Color couleurCacher = Color.BLACK;
    /**
     * couleur quand la casse est vide et découvert
     */
    public static Color couleurVide = Color.LIGHTGRAY;
    /**
     * couleur quand la casse est un mur et découvert
     */
    public static Color couleurMur = Color.WHITE;
    /**
     * couleur quand la casse est la casse du message et découvert
     */
    public static Color couleurMessage = Color.YELLOW;
    /**
     * couleur quand la casse est une sortie et découvert
     */
    public static Color couleurSortie = Color.BLUE;
    /**
     * Possition de la case en x
     */
    private int posX;
    /**
     * Position de la case en y
     */
    private int posY;
    /**
     * Status de la case
     */
    private StatusCase statusCase;
    /**
     * Savoir si la case a été découvert
     */
    private boolean decouvert = false;
    /**
     * Savoir si la case a été lue (passer proche)
     */
    private boolean lue = false;

    /**
     * costructeur par defaut
     *
     * @param posX
     * @param posY
     * @param statusCase
     */
    public DessinCase(int posX, int posY, StatusCase statusCase, boolean decouvert, boolean lue) {

        super((posX * Main.tailleCase + Main.tailleCase), (posY * Main.tailleCase + Main.tailleCase), Main.tailleCase, Main.tailleCase);
        this.posX = posX;
        this.posY = posY;
        this.statusCase = statusCase;
        this.decouvert = decouvert;
        this.lue = lue;
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
     *
     * @return x
     */
    public int getPosX() {
        return posX;
    }

    /**
     * retourne la postion en y de la case
     *
     * @return y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * modifie le fait de la découvert
     *
     * @param decouvert
     */
    public void setDecouvert(boolean decouvert) {
        this.decouvert = decouvert;
    }

    /**
     * modifie le fait que la case soit lue
     *
     * @param lue
     */
    public void setLue(boolean lue) {
        this.lue = lue;
    }
}
