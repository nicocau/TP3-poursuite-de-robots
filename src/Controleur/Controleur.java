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
}
