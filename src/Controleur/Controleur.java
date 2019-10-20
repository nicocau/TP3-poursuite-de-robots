package Controleur;

import Log.Logger;
import Log.TypeLog;
import Main.Main;
import Modele.*;
import Vue.DessinCase;
import Vue.DessinIntrus;
import Vue.DessinPerssonage;
import Vue.MainView;
import javafx.scene.paint.Color;


public class Controleur {
    /**
     * Le singleton
     */
    private static final Controleur controleur = new Controleur();

    /**
     * Le contructeur par défaut
     */
    private Controleur() {
    }


    /**
     * Permet de récupérer le singleton
     *
     * @return Controleur
     */
    public static Controleur getInstance() {
        return Controleur.controleur;
    }

    /**
     * Gere le déplacement des perssonage
     *
     * @param typeDeplacement
     * @param perssonage
     * @param dessinPerssonage
     * @param mainView
     */
    public void Deplacement(TypeDeplacement typeDeplacement, Perssonage perssonage, DessinPerssonage dessinPerssonage, MainView mainView) {
        int caseX = perssonage.getCaseActuel().getX();
        int caseY = perssonage.getCaseActuel().getY();
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Deplacement => perssonage: " + perssonage.toString() + "; typeDeplacement: " + typeDeplacement);
        if (
                caseX + typeDeplacement.getX() >= 0 && caseX + typeDeplacement.getX() < Main.TAILLE_X &&
                        caseY + typeDeplacement.getY() >= 0 && caseY + typeDeplacement.getY() < Main.TAILLE_Y &&
                        Terrain.getInstance().getCaseViaPosition(caseX + typeDeplacement.getX(), caseY + typeDeplacement.getY()) != null &&
                        Terrain.getInstance().getCaseViaPosition(caseX + typeDeplacement.getX(), caseY + typeDeplacement.getY()).getStatusCase() != StatusCase.MUR
        ) {
            dessinPerssonage.calculPosition(typeDeplacement.getX(), typeDeplacement.getY());
            perssonage.setCaseActuel(Terrain.getInstance().getCaseViaPosition(caseX + typeDeplacement.getX(), caseY + typeDeplacement.getY()));

            if (this.checkPerdue()) {
                Terrain.getInstance().getIntrus().setStatusIntru(StatusIntru.PERDU);
                mainView.getScene().setOnKeyPressed(null);
                System.out.println("Perdu");
                mainView.getLittleCycle().stop();
                Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Deplacement piegeur => perssonage: " + perssonage.toString() + "; typeDeplacement: " + typeDeplacement);
                Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Le Joueur vien de perdre");
            } else if (perssonage instanceof Intrus) {
                if (perssonage.getCaseActuel().getStatusCase() == StatusCase.SORTIE && ((Intrus) perssonage).getStatusIntru() == StatusIntru.FUITE) {
                    ((Intrus) perssonage).setStatusIntru(StatusIntru.GAGNER);
                    mainView.getScene().setOnKeyPressed(null);
                    mainView.getLittleCycle().stop();
                    System.out.println("Gagner");
                    Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Deplacement de la victoire => perssonage: " + perssonage.toString() + "; typeDeplacement: " + typeDeplacement);
                    Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Le Joueur vien de gagner");
                }

                this.updateCelluleVisible((Intrus) perssonage, mainView);
            }

            if (perssonage instanceof Robot) {
                this.recherJoueur((Robot) perssonage);
            }
        } else {
            Logger.getInstance().ajouteUneLigne(TypeLog.WARN, "Deplacement Imposible => perssonage: " + perssonage.toString() + "; typeDeplacement: " + typeDeplacement);
        }
    }

    /**
     * Recher le joueur par le robot
     *
     * @param perssonage
     */
    private void recherJoueur(Robot perssonage) {
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Recherche Joueur => Robot" + perssonage);
        int x = perssonage.getCaseActuel().getX();
        int y = perssonage.getCaseActuel().getY();
        for (int i = x - Main.DISTANCE_VUE_ROBOT; i < x + Main.DISTANCE_VUE_ROBOT; i++) {
            for (int j = y - Main.DISTANCE_VUE_ROBOT; j < y + Main.DISTANCE_VUE_ROBOT; j++) {
                if (Math.pow((i - x), 2) + Math.pow((j - y), 2) <= Math.pow(Main.DISTANCE_VUE_ROBOT, 2)) {
                    Case caseTerain = Terrain.getInstance().getCaseViaPosition(i, j);
                    if (caseTerain != null) {
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Joueur détécter => Robot:" + perssonage + "; joueur:" + Terrain.getInstance().getIntrus().toString());
                        if (caseTerain == Terrain.getInstance().getIntrus().getCaseActuel()) {
                            Robot.setCaseIntru(caseTerain);
                            Robot.setNbTickDeRecherche(Main.NB_TICKE_RECHECHERCHE);
                            Terrain.getInstance().getRobots().forEach(robot -> robot.setStatusRobo(StatusRobo.CHASSE));
                        }
                    }
                }
            }
        }
    }

    /**
     * Vérifie si le joueur c'est fait attraper
     *
     * @return perdu
     */
    private boolean checkPerdue() {
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Vérifier que le joueur ne c'est pas fait attraper");
        Case caseIntrus = Terrain.getInstance().getIntrus().getCaseActuel();
        final boolean[] res = {false};
        Terrain.getInstance().getRobots().forEach(robot -> {
            if (robot.getCaseActuel() == caseIntrus) {
                res[0] = true;
                Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Le joueur vien d'être attraper => robot:" + robot.toString() + "; joueur :" + Terrain.getInstance().getIntrus().toString());
            }
        });
        return res[0];
    }

    /**
     * Met a jour la vue des cellues
     *
     * @param perssonage
     * @param mainView
     */
    private void updateCelluleVisible(Intrus perssonage, MainView mainView) {
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Met a jour les case visible");
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Met a jour les case visible => perssonage:" + perssonage);
        int x = perssonage.getCaseActuel().getX();
        int y = perssonage.getCaseActuel().getY();
        for (int i = x - Main.DISTANCE_VUE; i < x + Main.DISTANCE_VUE; i++) {
            for (int j = y - Main.DISTANCE_VUE; j < y + Main.DISTANCE_VUE; j++) {
                if (Math.pow((i - x), 2) + Math.pow((j - y), 2) <= Math.pow(Main.DISTANCE_VUE, 2)) {
                    Case caseTerain = Terrain.getInstance().getCaseViaPosition(i, j);
                    if (caseTerain != null) {
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Met a jour une case visible => case:" + caseTerain + "; perssonage:" + perssonage);
                        caseTerain.setDecouvert(true);
                        DessinCase dessinCase = mainView.getCaseViaPosition(i, j);
                        dessinCase.setDecouvert(true);
                        dessinCase.appliqueCouleur();
                    }
                }
            }
        }
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Met a jour les case visible des message => perssonage:" + perssonage);
        for (int i = x - Main.DISTANCE_VUE_MESSAGE; i < x + Main.DISTANCE_VUE_MESSAGE; i++) {
            for (int j = y - Main.DISTANCE_VUE_MESSAGE; j < y + Main.DISTANCE_VUE_MESSAGE; j++) {
                if (Math.pow((i - x), 2) + Math.pow((j - y), 2) <= Math.pow(Main.DISTANCE_VUE_MESSAGE, 2)) {
                    Case caseTerain = Terrain.getInstance().getCaseViaPosition(i, j);
                    if (caseTerain != null) {
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Met a jour une case visible des message => case:" + caseTerain + "; perssonage:" + perssonage);
                        caseTerain.setLue(true);
                        DessinCase dessinCase = mainView.getCaseViaPosition(i, j);
                        dessinCase.setLue(true);
                        dessinCase.appliqueCouleur();
                    }
                }
            }
        }
    }

    /**
     * Tente de récupérer le message
     *
     * @param mainView
     * @param dessinIntrus
     */
    public void PrendMsg(MainView mainView, DessinIntrus dessinIntrus) {
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Tente de récupérer le message");
        if (Terrain.getInstance().getIntrus().getCaseActuel().getStatusCase() == StatusCase.MESSAGE) {
            Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Récupérer le message => joueur:" + Terrain.getInstance().getIntrus().toString());
            mainView.getScene().setFill(Color.DARKGREEN);
            dessinIntrus.setFill(DessinIntrus.couleurIntrusMessage);
            Terrain.getInstance().getIntrus().setStatusIntru(StatusIntru.FUITE);
        }
    }

    /**
     * Permet de choisir les mouvement du robot
     *
     * @param robot
     * @return
     */
    public TypeDeplacement choseRobotMove(Robot robot) {
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Choisie le mouvement du robot => " + robot.toString());
        TypeDeplacement res = TypeDeplacement.getRandom();
        if (Robot.getNbTickDeRecherche() > 0) {
            if (Robot.getCaseIntru() != null) {
                if (robot.getCaseActuel().getX() > Robot.getCaseIntru().getX()) {
                    if (Terrain.getInstance().getCaseViaPosition(robot.getCaseActuel().getX() + TypeDeplacement.GAUCHE.getX(), robot.getCaseActuel().getY() + TypeDeplacement.GAUCHE.getY()).getStatusCase() != StatusCase.MUR) {
                        res = TypeDeplacement.GAUCHE;
                    }
                } else if (robot.getCaseActuel().getX() < Robot.getCaseIntru().getX()) {
                    if (Terrain.getInstance().getCaseViaPosition(robot.getCaseActuel().getX() + TypeDeplacement.DROITE.getX(), robot.getCaseActuel().getY() + TypeDeplacement.DROITE.getY()).getStatusCase() != StatusCase.MUR) {
                        res = TypeDeplacement.DROITE;
                    }
                } else if (robot.getCaseActuel().getY() > Robot.getCaseIntru().getY()) {
                    if (Terrain.getInstance().getCaseViaPosition(robot.getCaseActuel().getX() + TypeDeplacement.HAUT.getX(), robot.getCaseActuel().getY() + TypeDeplacement.HAUT.getY()).getStatusCase() != StatusCase.MUR) {
                        res = TypeDeplacement.HAUT;
                    }
                } else if (robot.getCaseActuel().getY() < Robot.getCaseIntru().getY()) {
                    if (Terrain.getInstance().getCaseViaPosition(robot.getCaseActuel().getX() + TypeDeplacement.BAS.getX(), robot.getCaseActuel().getY() + TypeDeplacement.BAS.getY()).getStatusCase() != StatusCase.MUR) {
                        res = TypeDeplacement.BAS;
                    }
                } else {
                    Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Reinitialise la recherche des robot");
                    Robot.setCaseIntru(null);
                    Terrain.getInstance().getRobots().forEach(robot1 -> robot1.setStatusRobo(StatusRobo.PATROUILLE));
                }
            }
        } else if (Robot.getCaseIntru() != null) {
            Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Reinitialise la recherche des robot");
            Robot.setCaseIntru(null);
            Terrain.getInstance().getRobots().forEach(robot1 -> robot1.setStatusRobo(StatusRobo.PATROUILLE));
        }
        return res;
    }
}
