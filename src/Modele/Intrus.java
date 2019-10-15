package Modele;

public class Intrus extends Perssonage {
    private StatusIntru statusIntru = StatusIntru.RECHERCHE;

    public Intrus(Case caseActuel) {
        super(caseActuel);
    }

    public StatusIntru getStatusIntru() {
        return statusIntru;
    }

    public void setStatusIntru(StatusIntru statusIntru) {
        this.statusIntru = statusIntru;
    }
}
