package Main;

import Log.Logger;
import Log.TypeLog;
import Vue.MainView;

public class Main {
    public static final int TAILLE_X = 20;
    public static final int TAILLE_Y = 30;
    public static final int NB_ROBOTS = 3;
    public static final int DISTANCE_VUE = 4;
    public static final int DISTANCE_VUE_MESSAGE = 2;
    public static final int DISTANCE_VUE_ROBOT = 5;
    public static final double POURCENTAGE_MUR = 0.1;
    public static final long tempo = 500;
    public static final int tailleCase = 10;
    public static final int NB_TICKE_RECHECHERCHE = 5;

    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Lance le jeux");
        MainView.getInstance().lancement(args);
    }
}
