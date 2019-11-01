package Modele;

import Controleur.Controleur;
import Log.Logger;
import Log.TypeLog;
import Main.Main;

import java.util.ArrayList;
import java.util.Random;

public class Terrain {

    /**
     * Liste toutes les cases
     */
    private ArrayList<Case> cases = new ArrayList<Case>();
    /**
     * Liste les case encore vide
     */
    private ArrayList<Case> casesVide = new ArrayList<Case>();
    /**
     * Liste des rbots
     */
    private ArrayList<Robot> robots = new ArrayList<Robot>();
    /**
     * Liste de tous les murs
     */
    private ArrayList<Case> murs = new ArrayList<Case>();
    /**
     * Intrus
     */
    private Intrus intrus;

    /**
     * construceur par défaut du terrain, il génére également les élément dépendent du terain
     */
    public Terrain() {

        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Création du Terrain");

        Random random = new Random();
        for (int i = 0; i < Main.TAILLE_X; i++) {
            for (int j = 0; j < Main.TAILLE_Y; j++) {
                Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Création de la casse (" + i + "," + j + ")");
                Case newCase = new Case(i, j);
                this.cases.add(newCase);
                this.casesVide.add(newCase);
            }
        }

        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Ajoute les 4 sortie");
        Case sortie = null;
        int motierX = (Main.TAILLE_X - 1) / 2;
        int motierY = (Main.TAILLE_Y - 1) / 2;
        sortie = this.getCaseViaPosition(motierX, 0);
        this.casesVide.remove(sortie);
        sortie.setStatusCase(StatusCase.SORTIE);
        sortie = this.getCaseViaPosition(motierX, Main.TAILLE_Y - 1);
        this.casesVide.remove(sortie);
        sortie.setStatusCase(StatusCase.SORTIE);
        sortie = this.getCaseViaPosition(0, motierY);
        this.casesVide.remove(sortie);
        sortie.setStatusCase(StatusCase.SORTIE);
        sortie = this.getCaseViaPosition(Main.TAILLE_X - 1, motierY);
        this.casesVide.remove(sortie);
        sortie.setStatusCase(StatusCase.SORTIE);

        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée les murs");
        ArrayList<Case> casesAVirer = new ArrayList<Case>();
        this.casesVide.forEach(c -> {
            if (random.nextDouble() <= Main.POURCENTAGE_MUR) {
                Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Création d'un mur en (" + c.getX() + "," + c.getY() + ")");
                c.setStatusCase(StatusCase.MUR);
                this.getMurs().add(c);
                casesAVirer.add(c);
            }
        });
        casesAVirer.forEach(c -> this.casesVide.remove(c));

        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Vérifie si il reste assez de plasse");
        if (this.casesVide.size() < Main.NB_ROBOTS + 1 + 1) {
            try {
                throw new Exception("Il n'y a pas assez de casse libre pour placer les robots et pour le joueur");
            } catch (Exception e) {
                e.printStackTrace();
                Logger.getInstance().ajouteUneLigne(TypeLog.FATAL, "Impposible poursuivre il n'y a pas assez de casse libre pour placer les robots et pour le joueur");
                System.exit(0);
            }
        }

        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Ajoute le message");
        Case caseMessage = this.casesVide.get(random.nextInt(this.casesVide.size()));
        caseMessage.setStatusCase(StatusCase.MESSAGE);
        this.casesVide.remove(caseMessage);


        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Ajoute les robots");
        for (int i = 0; i < Main.NB_ROBOTS; i++) {
            Case caseRobot = this.casesVide.get(random.nextInt(this.casesVide.size()));
            Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Ajoute un robot => case" + caseRobot);
            this.getRobots().add(new Robot(caseRobot));
        }

        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Ajoute l'intru'");
        Case caseIntru = this.casesVide.get(random.nextInt(this.casesVide.size()));
        this.intrus = new Intrus(caseIntru);
    }

    /**
     * retoure la liste des case
     *
     * @return la liste des case
     */
    public ArrayList<Case> getCases() {
        return cases;
    }

    /**
     * retourne la liste des robots
     *
     * @return liste de robots
     */
    public ArrayList<Robot> getRobots() {
        return robots;
    }

    /**
     * retourne la liste des murs
     *
     * @return liste de murs
     */
    public ArrayList<Case> getMurs() {
        return murs;
    }

    /**
     * retourn l'intu
     *
     * @return intru
     */
    public Intrus getIntrus() {
        return intrus;
    }

    /**
     * retourne une case a partire de la possition donnée
     *
     * @param x
     * @param y
     * @return
     */
    public Case getCaseViaPosition(int x, int y) {
        final Case[] res = {null};
        this.cases.forEach(c -> {
            if (c.getX() == x && c.getY() == y) {
                res[0] = c;
            }
        });
        return res[0];
    }
}
