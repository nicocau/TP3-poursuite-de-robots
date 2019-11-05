package modele;

import log.Logger;
import log.TypeLog;

public abstract class Personage {
    /**
     * Permet de récupérer la casse sur la quelle se trouvre l'utilisateur
     */
    protected Case caseActuel;

    /**
     * contructeur par defaut
     *
     * @param caseActuel
     */
    public Personage(Case caseActuel) {
        this.caseActuel = caseActuel;
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée un perssonage => perssonage: " + this.toString());
    }

    /**
     * retourne la case actuelle
     *
     * @return case actuel
     */
    public Case getCaseActuel() {
        return caseActuel;
    }

    /**
     * modifie la case actuelle
     *
     * @param caseActuel
     */
    public void setCaseActuel(Case caseActuel) {
        this.caseActuel = caseActuel;
    }
}
