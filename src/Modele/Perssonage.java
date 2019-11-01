package Modele;

import Log.Logger;
import Log.TypeLog;

public abstract class Perssonage {
    protected Case caseActuel;

    /**
     * contructeur par defaut
     *
     * @param caseActuel
     */
    public Perssonage(Case caseActuel) {
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
