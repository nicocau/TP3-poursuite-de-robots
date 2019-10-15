package Modele;

public class Robot extends Perssonage {
    private StatusRobo statusRobo = StatusRobo.CHASSE;

    public Robot(Case caseActuel) {
        super(caseActuel);
    }

    public StatusRobo getStatusRobo() {
        return statusRobo;
    }

    public void setStatusRobo(StatusRobo statusRobo) {
        this.statusRobo = statusRobo;
    }
}
