package Modele;

public class Robot extends Perssonage {
    private StatusRobo statusRobo = StatusRobo.PATROUILLE;
    private static volatile Case caseIntru;
    private static volatile int nbTickDeRecherche = 0;

    public Robot(Case caseActuel) {
        super(caseActuel);
    }

    public StatusRobo getStatusRobo() {
        return statusRobo;
    }

    public void setStatusRobo(StatusRobo statusRobo) {
        this.statusRobo = statusRobo;
    }

    public static Case getCaseIntru() {
        return caseIntru;
    }

    public static void setCaseIntru(Case caseIntru) {
        Robot.caseIntru = caseIntru;
    }

    public static int getNbTickDeRecherche() {
        return nbTickDeRecherche;
    }

    public static void setNbTickDeRecherche(int nbTickDeRecherche) {
        Robot.nbTickDeRecherche = nbTickDeRecherche;
    }

    @Override
    public String toString() {
        return "Robot {case:" + this.caseActuel.toString() + "; status:" + this.statusRobo + "; caseIntru" + Robot.caseIntru + " nbTockDeRecherche" + Robot.nbTickDeRecherche+"}";
    }
}
