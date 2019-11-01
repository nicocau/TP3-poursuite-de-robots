package Modele;

import Log.Logger;
import Log.TypeLog;

public class Robot extends Perssonage {
    /**
     * case sur la quelle l'intru a été repérer
     */
    private static volatile Case caseIntru;
    /**
     * nobre de tick depuis la dispariton du joueur
     */
    private static volatile int nbTickDeRecherche = 0;
    /**
     * Le statut du robot (patroulle, chase)
     */
    private StatusRobo statusRobo = StatusRobo.PATROUILLE;

    /**
     * Corstructeur par defaut
     *
     * @param caseActuel
     */
    public Robot(Case caseActuel) {
        super(caseActuel);
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée un robot => robot: " + this.toString());
    }

    /**
     * retoure la case ou l'intrus a été vue la derniére fois
     *
     * @return case intru
     */
    public static Case getCaseIntru() {
        return caseIntru;
    }

    /**
     * modifie la case ou l'intru a été vue
     *
     * @param caseIntru
     */
    public static void setCaseIntru(Case caseIntru) {
        Robot.caseIntru = caseIntru;
    }

    /**
     * retourne le nobre de tick ou les robot von chercher l'intrus
     *
     * @return nb tick restant
     */
    public static int getNbTickDeRecherche() {
        return nbTickDeRecherche;
    }

    /**
     * modifie le nombre de ticke ou les robot von cherhcer le joueur
     *
     * @param nbTickDeRecherche
     */
    public static void setNbTickDeRecherche(int nbTickDeRecherche) {
        Robot.nbTickDeRecherche = nbTickDeRecherche;
    }

    /**
     * retourne le status du robot
     *
     * @return status
     */
    public StatusRobo getStatusRobo() {
        return statusRobo;
    }

    /**
     * modifie le statut du robot
     *
     * @param statusRobo
     */
    public void setStatusRobo(StatusRobo statusRobo) {
        this.statusRobo = statusRobo;
    }

    /**
     * retourne un descriptif de l'objet
     *
     * @return descriptif du robot
     */
    @Override
    public String toString() {
        return "Robot {case:" + this.caseActuel.toString() + "; status:" + this.statusRobo + "; caseIntru" + Robot.caseIntru + " nbTockDeRecherche" + Robot.nbTickDeRecherche + "}";
    }
}
