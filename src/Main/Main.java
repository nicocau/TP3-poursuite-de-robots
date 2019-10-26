package Main;

import Log.Logger;
import Log.TypeLog;
import Vue.MainView;

public class Main {
    public static Integer TAILLE_X = 20;
    public static Integer TAILLE_Y = 30;
    public static Integer NB_ROBOTS = 3;
    public static Integer DISTANCE_VUE = 4;
    public static Integer DISTANCE_VUE_MESSAGE = 2;
    public static Integer DISTANCE_VUE_ROBOT = 3;
    public static Double POURCENTAGE_MUR = 0.1;
    public static Double tempo = 500.0;
    public static final int tailleCase = 10;
    public static Integer NB_TICKE_RECHECHERCHE = 5;

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
