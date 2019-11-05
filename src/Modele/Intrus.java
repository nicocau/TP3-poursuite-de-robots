package Modele;

import Log.Logger;
import Log.TypeLog;

public class Intrus extends Perssonage {
    /**
     * status de l'intrus (en recherche, gagner, ...)
     */
    private StatusIntru statusIntru = StatusIntru.RECHERCHE;

    /**
     * Permet de savoir si  la perssone est deja en déplacement
     */
    private boolean enDeplacement = false;

    /**
     * Constructeur par défaut
     *
     * @param caseActuel
     */
    public Intrus(Case caseActuel) {
        super(caseActuel);
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée un intrus => intrus: " + this.toString());
    }

    /**
     * Permet de savoir si  la perssone est deja en déplacement
     * @return
     */
    public boolean isEnDeplacement() {
        return enDeplacement;
    }

    /**
     * Permet de modifier le statut de déplacement du perssonage
     * @param enDeplacement
     */
    public void setEnDeplacement(boolean enDeplacement) {
        this.enDeplacement = enDeplacement;
    }

    /**
     * retourne le status de l'intus
     *
     * @return statu intrus
     */
    public StatusIntru getStatusIntru() {
        return statusIntru;
    }

    /**
     * modifie le status de l'inturs
     *
     * @param statusIntru
     */
    public void setStatusIntru(StatusIntru statusIntru) {
        this.statusIntru = statusIntru;
    }

    @Override
    public String toString() {
        return "Intrus {case:" + this.caseActuel.toString() + "; status:" + this.statusIntru + "}";
    }
}
