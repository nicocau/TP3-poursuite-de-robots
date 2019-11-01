package Main;

import Controleur.Controleur;
import Log.Logger;
import Log.TypeLog;
import Vue.MainView;

public class Main {
    public static volatile Integer TAILLE_X = 20;
    public static volatile Integer TAILLE_Y = 30;
    public static volatile Integer NB_ROBOTS = 3;
    public static volatile Integer DISTANCE_VUE = 4;
    public static volatile Integer DISTANCE_VUE_MESSAGE = 2;
    public static volatile Integer DISTANCE_VUE_ROBOT = 3;
    public static volatile Double POURCENTAGE_MUR = 0.1;
    public static volatile Double tempo = 500.0;
    public static volatile Integer NB_TICKE_RECHECHERCHE = 5;

    public static final int tailleCase = 10;

    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Lance le jeux");
//        Controleur controleur = new Controleur(args);

    }
}
