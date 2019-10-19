package Modele;

public abstract class Perssonage {
    protected Case caseActuel;

    public Perssonage(Case caseActuel) {
        this.caseActuel = caseActuel;
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
