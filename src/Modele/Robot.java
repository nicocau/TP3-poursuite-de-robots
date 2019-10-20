package Modele;

import Log.Logger;
import Log.TypeLog;

public class Robot extends Perssonage {
    private static volatile Case caseIntru;
    private static volatile int nbTickDeRecherche = 0;
    private StatusRobo statusRobo = StatusRobo.PATROUILLE;

    public Robot(Case caseActuel) {
        super(caseActuel);
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée un robot => robot: " + this.toString());
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

    public StatusRobo getStatusRobo() {
        return statusRobo;
    }

    public void setStatusRobo(StatusRobo statusRobo) {
        this.statusRobo = statusRobo;
    }

    @Override
    public String toString() {
        return "Robot {case:" + this.caseActuel.toString() + "; status:" + this.statusRobo + "; caseIntru" + Robot.caseIntru + " nbTockDeRecherche" + Robot.nbTickDeRecherche + "}";
    }
}
