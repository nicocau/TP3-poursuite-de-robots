package Controleur;

import Main.Main;
import Modele.*;
import Vue.DessinCase;
import Vue.DessinIntrus;
import Vue.DessinPerssonage;
import Vue.MainView;
import javafx.scene.paint.Color;

public class Controleur {
    private static final Controleur controleur = new Controleur();

    private Controleur() {
    }

    public static Controleur getInstance() {
        return Controleur.controleur;
    }

    public void Deplacement(TypeDeplacement typeDeplacement, Perssonage perssonage, DessinPerssonage dessinPerssonage, MainView mainView) {
        int caseX = perssonage.getCaseActuel().getX();
        int caseY = perssonage.getCaseActuel().getY();
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
            } else if (perssonage instanceof Intrus) {
                if (perssonage.getCaseActuel().getStatusCase() == StatusCase.SORTIE && ((Intrus) perssonage).getStatusIntru() == StatusIntru.FUITE) {
                    ((Intrus) perssonage).setStatusIntru(StatusIntru.GAGNER);
                    mainView.getScene().setOnKeyPressed(null);
                    mainView.getLittleCycle().stop();
                    System.out.println("Gagner");
                }

                this.updateCelluleVisible((Intrus) perssonage, mainView);
            }

            if (perssonage instanceof Robot) {
                this.recherJoueur((Robot) perssonage);
            }
        }
    }

    private void recherJoueur(Robot perssonage) {
        int x = perssonage.getCaseActuel().getX();
        int y = perssonage.getCaseActuel().getY();
        for (int i = x - Main.DISTANCE_VUE_ROBOT; i < x + Main.DISTANCE_VUE_ROBOT; i++) {
            for (int j = y - Main.DISTANCE_VUE_ROBOT; j < y + Main.DISTANCE_VUE_ROBOT; j++) {
                if (Math.pow((i - x), 2) + Math.pow((j - y), 2) <= Math.pow(Main.DISTANCE_VUE_ROBOT, 2)) {
                    Case caseTerain = Terrain.getInstance().getCaseViaPosition(i, j);
                    if (caseTerain != null) {
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

    private boolean checkPerdue() {
        Case caseIntrus = Terrain.getInstance().getIntrus().getCaseActuel();
        final boolean[] res = {false};
        Terrain.getInstance().getRobots().forEach(robot -> {
            if (robot.getCaseActuel() == caseIntrus) {
                res[0] = true;
            }
        });
        return res[0];
    }

    private void updateCelluleVisible(Intrus perssonage, MainView mainView) {
        int x = perssonage.getCaseActuel().getX();
        int y = perssonage.getCaseActuel().getY();
        for (int i = x - Main.DISTANCE_VUE; i < x + Main.DISTANCE_VUE; i++) {
            for (int j = y - Main.DISTANCE_VUE; j < y + Main.DISTANCE_VUE; j++) {
                if (Math.pow((i - x), 2) + Math.pow((j - y), 2) <= Math.pow(Main.DISTANCE_VUE, 2)) {
                    Case caseTerain = Terrain.getInstance().getCaseViaPosition(i, j);
                    if (caseTerain != null) {
                        caseTerain.setDecouvert(true);
                        DessinCase dessinCase = mainView.getCaseViaPosition(i, j);
                        dessinCase.setDecouvert(true);
                        dessinCase.appliqueCouleur();
                    }
                }
            }
        }
        for (int i = x - Main.DISTANCE_VUE_MESSAGE; i < x + Main.DISTANCE_VUE_MESSAGE; i++) {
            for (int j = y - Main.DISTANCE_VUE_MESSAGE; j < y + Main.DISTANCE_VUE_MESSAGE; j++) {
                if (Math.pow((i - x), 2) + Math.pow((j - y), 2) <= Math.pow(Main.DISTANCE_VUE_MESSAGE, 2)) {
                    Case caseTerain = Terrain.getInstance().getCaseViaPosition(i, j);
                    if (caseTerain != null) {
                        caseTerain.setLue(true);
                        DessinCase dessinCase = mainView.getCaseViaPosition(i, j);
                        dessinCase.setLue(true);
                        dessinCase.appliqueCouleur();
                    }
                }
            }
        }
    }

    public void PrendMsg(MainView mainView, DessinIntrus dessinIntrus) {
        if (Terrain.getInstance().getIntrus().getCaseActuel().getStatusCase() == StatusCase.MESSAGE) {
            mainView.getScene().setFill(Color.DARKGREEN);
            dessinIntrus.setFill(DessinIntrus.couleurIntrusMessage);
            Terrain.getInstance().getIntrus().setStatusIntru(StatusIntru.FUITE);
        }
    }

    public TypeDeplacement choseRobotMove(Robot robot) {
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
                    Robot.setCaseIntru(null);
                    Terrain.getInstance().getRobots().forEach(robot1 -> robot1.setStatusRobo(StatusRobo.PATROUILLE));
                }
            }
        } else if (Robot.getCaseIntru() != null) {
            Robot.setCaseIntru(null);
        }
        return res;
    }
}
