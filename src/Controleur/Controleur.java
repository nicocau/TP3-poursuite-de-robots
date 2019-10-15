package Controleur;

import Main.Main;
import Modele.*;
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

    public void Deplacement(TypeDeplacement typeDeplacement, Perssonage perssonage, DessinPerssonage dessinPerssonage) {
        int caseX = perssonage.getCaseActuel().getX();
        int caseY = perssonage.getCaseActuel().getY();
        if (
                caseX + typeDeplacement.getX() >= 0 && caseX + typeDeplacement.getX() < Main.TAILLE_X &&
                caseY + typeDeplacement.getY() >= 0 && caseY + typeDeplacement.getY() < Main.TAILLE_Y &&
                Terrain.getInstance().getCaseViaPosition(caseX + typeDeplacement.getX(), caseY + typeDeplacement.getY()).getStatusCase() != StatusCase.MUR &&
                Terrain.getInstance().getIntrus().getStatusIntru() != StatusIntru.PERDU  &&
                Terrain.getInstance().getIntrus().getStatusIntru() != StatusIntru.GAGNER
        ) {
            dessinPerssonage.calculPosition(typeDeplacement.getX(), typeDeplacement.getY());
            perssonage.setCaseActuel(Terrain.getInstance().getCaseViaPosition(caseX + typeDeplacement.getX(), caseY + typeDeplacement.getY()));


            if (Terrain.getInstance().getIntrus().getCaseActuel().getStatusCase() == StatusCase.SORTIE && Terrain.getInstance().getIntrus().getStatusIntru() == StatusIntru.FUITE) {
                Terrain.getInstance().getIntrus().setStatusIntru(StatusIntru.GAGNER);
                System.out.println("Gagner");
            }
        }
    }

    public void PrendMsg(MainView mainView) {
        if (Terrain.getInstance().getIntrus().getCaseActuel().getStatusCase() == StatusCase.MESSAGE) {
            mainView.getScene().setFill(Color.DARKGREEN);
            Terrain.getInstance().getIntrus().setStatusIntru(StatusIntru.FUITE);
        }
    }
}
