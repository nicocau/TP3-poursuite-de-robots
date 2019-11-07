package main;

/**
 * Class contenant les valeur par défaut
 */
public class Main {
    /**
     * nb de casse sur l'axe X
     */
    public static volatile Integer TAILLE_X = 20;
    /**
     * nb de casse sur l'axe X
     */
    public static volatile Integer TAILLE_Y = 30;
    /**
     * nombre de robot sur la carte
     */
    public static volatile Integer NB_ROBOTS = 3;
    /**
     * Distance a la quelle le Personnage peut voir les caisse
     */
    public static volatile Integer DISTANCE_VUE = 4;
    /**
     * Distance a la quelle le Personnage peut detecter que la caisse contien le message rechercher
     */
    public static volatile Integer DISTANCE_VUE_MESSAGE = 2;
    /**
     * Distance a la quelle les robot peuvent voir le joueur
     */
    public static volatile Integer DISTANCE_VUE_ROBOT = 3;
    /**
     * Pourcentage de mur présent sur la carte
     */
    public static volatile Double POURCENTAGE_MUR = 0.1;
    /**
     * Temps de réaction des robots
     */
    public static volatile Double tempo = 500.0;
    /**
     * Nombre de tique pendent les quelle les robot vont chercher le joueur
     */
    public static volatile Integer NB_TICKE_RECHECHERCHE = 5;
    /**
     * Taille de la mémoire du joueur
     */
    public static volatile Integer NB_MEMOIRE = 50;

    /**
     * Nombre de pixelle des casse
     */
    public static final int tailleCase = 10;
}
