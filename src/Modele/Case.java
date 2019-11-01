package Modele;

import Log.Logger;
import Log.TypeLog;

/**
 *
 */
public class Case {
    private int x;
    private int y;
    private StatusCase statusCase = StatusCase.VIDE;
    private boolean decouvert = false;
    private boolean lue = false;

    /**
     * constructeur par défaut
     *
     * @param x
     * @param y
     */
    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée une case => case:" + this.toString());
    }

    /**
     * retourne le x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * retourne le y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * retourne le status de la case
     *
     * @return status de la case
     */
    public StatusCase getStatusCase() {
        return statusCase;
    }

    /**
     * modifie le status de la case
     *
     * @param statusCase
     */
    public void setStatusCase(StatusCase statusCase) {
        this.statusCase = statusCase;
    }

    /**
     * modifie le fait que la case est vue
     *
     * @param decouvert
     */
    public void setDecouvert(boolean decouvert) {
        this.decouvert = decouvert;
    }

    /**
     * modifie le fait que la case est lue
     *
     * @param lue
     */
    public void setLue(boolean lue) {
        this.lue = lue;
    }

    public boolean isDecouvert() {
        return decouvert;
    }

    public boolean isLue() {
        return lue;
    }

    /**
     * retourne la description de l'objet
     *
     * @return la description de l'objet
     */
    @Override
    public String toString() {
        return "Case{x:" + this.x + "; y:" + this.y + "; statut:" + this.statusCase + "; decouvert:" + this.decouvert + "; lue" + this.lue + "}";
    }
}
