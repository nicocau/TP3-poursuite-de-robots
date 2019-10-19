package Modele;

import Log.Logger;
import Log.TypeLog;

public abstract class Perssonage {
    protected Case caseActuel;

    public Perssonage(Case caseActuel) {
        this.caseActuel = caseActuel;
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée un perssonage => perssonage: " + this.toString());
    }

    public Case getCaseActuel() {
        return caseActuel;
    }

    public void setCaseActuel(Case caseActuel) {
        this.caseActuel = caseActuel;
    }

    @Override
    public abstract String toString();
}
