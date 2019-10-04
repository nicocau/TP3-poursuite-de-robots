package Modele;

public class Intrus {
    private Case caseActuel;
    private StatusIntru statusIntru = StatusIntru.RECHERCHE;

    public Intrus(Case caseActuel) {
        this.caseActuel = caseActuel;
    }

    public Case getCaseActuel() {
        return caseActuel;
    }

    public void setCaseActuel(Case caseActuel) {
        this.caseActuel = caseActuel;
    }

    public StatusIntru getStatusIntru() {
        return statusIntru;
    }

    public void setStatusIntru(StatusIntru statusIntru) {
        this.statusIntru = statusIntru;
    }
}
