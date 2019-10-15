package Modele;

public abstract class Perssonage {
    private Case caseActuel;

    public Perssonage(Case caseActuel) {
        this.caseActuel = caseActuel;
    }

    public Case getCaseActuel() {
        return caseActuel;
    }

    public void setCaseActuel(Case caseActuel) {
        this.caseActuel = caseActuel;
    }
}
