package Modele;

public class Robot {
    private Case caseActuel;
    private StatusRobo statusRobo = StatusRobo.CHASSE;

    public Robot(Case caseActuel) {
        this.caseActuel = caseActuel;
    }

    public Case getCaseActuel() {
        return caseActuel;
    }

    public void setCaseActuel(Case caseActuel) {
        this.caseActuel = caseActuel;
    }

    public StatusRobo getStatusRobo() {
        return statusRobo;
    }

    public void setStatusRobo(StatusRobo statusRobo) {
        this.statusRobo = statusRobo;
    }
}
