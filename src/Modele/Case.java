package Modele;

import Log.Logger;
import Log.TypeLog;

public class Case {
    private int x = -1;
    private int y = -1;
    private StatusCase statusCase = StatusCase.VIDE;
    private boolean decouvert = false;
    private boolean lue = false;

    /**
     * constructeur par défaut
     * @param x
     * @param y
     */
    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée une case => case:"+this.toString());
    }

    /**
     * retourne le x
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * modifie le x
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * retourne le y
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * modifie le y
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * retourne le status de la case
     * @return status de la case
     */
    public StatusCase getStatusCase() {
        return statusCase;
    }

    /**
     * modifie le status de la case
     * @param statusCase
     */
    public void setStatusCase(StatusCase statusCase) {
        this.statusCase = statusCase;
    }

    /**
     * retourne si la case est decouvert
     * @return decouvert
     */
    public boolean isDecouvert() {
        return decouvert;
    }

    /**
     * modifie le fait que la
     * @param decouvert
     */
    public void setDecouvert(boolean decouvert) {
        this.decouvert = decouvert;
    }

    public boolean isLue() {
        return lue;
    }

    public void setLue(boolean lue) {
        this.lue = lue;
    }

    @Override
    public String toString() {
        return "Case{x:" + this.x + "; y:" + this.y + "; statut:" + this.statusCase + "; decouvert:" + this.decouvert + "; lue" + this.lue + "}";
    }
}
