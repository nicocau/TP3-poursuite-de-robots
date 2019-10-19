package Modele;

import Log.Logger;
import Log.TypeLog;

public class Intrus extends Perssonage {
    private StatusIntru statusIntru = StatusIntru.RECHERCHE;

    public Intrus(Case caseActuel) {
        super(caseActuel);
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée un intrus => intrus: " + this.toString());
    }

    public StatusIntru getStatusIntru() {
        return statusIntru;
    }

    public void setStatusIntru(StatusIntru statusIntru) {
        this.statusIntru = statusIntru;
    }

    @Override
    public String toString() {
        return "Intrus {case:"+this.caseActuel.toString()+"; status:"+this.statusIntru+"}";
    }
}
