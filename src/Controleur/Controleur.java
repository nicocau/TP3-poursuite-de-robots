package controleur;

import log.Logger;
import log.TypeLog;
import main.Main;
import modele.*;
import vue.DessinCase;
import vue.DessinIntrus;
import vue.DessinPerssonage;
import vue.MainView;
import javafx.scene.paint.Color;


public class Controleur {
    /**
     * Crée le terrain et le lie au controlleur
     */
    private Terrain terrain = new Terrain();
    /**
     * Vue principale
     */
    private MainView mainView;

    /**
     * Le contructeur par défaut
     */
    public Controleur(MainView mainView) {
        this.mainView = mainView;
    }

    /**
     * permet de récuper le terrain
     * @return Terrain
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Permet de modifier le terrain
     * @param terrain a modifier
     */
    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    /**
     * Gere le déplacement des personage
     *
     * @param typeDeplacement
     * @param personage
     * @param dessinPerssonage
     * @param mainView
     */
    public void Deplacement(TypeDeplacement typeDeplacement, Personage personage, DessinPerssonage dessinPerssonage, MainView mainView) {
        int caseX = personage.getCaseActuel().getX();
        int caseY = personage.getCaseActuel().getY();
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Deplacement => personage: " + personage.toString() + "; typeDeplacement: " + typeDeplacement);
        if (
                caseX + typeDeplacement.getX() >= 0 && caseX + typeDeplacement.getX() < Main.TAILLE_X &&
                        caseY + typeDeplacement.getY() >= 0 && caseY + typeDeplacement.getY() < Main.TAILLE_Y &&
                        this.terrain.getCaseViaPosition(caseX + typeDeplacement.getX(), caseY + typeDeplacement.getY()) != null &&
                        this.terrain.getCaseViaPosition(caseX + typeDeplacement.getX(), caseY + typeDeplacement.getY()).getStatusCase() != StatusCase.MUR
        ) {
            dessinPerssonage.calculPosition(typeDeplacement.getX(), typeDeplacement.getY(), this, mainView);
            personage.setCaseActuel(this.terrain.getCaseViaPosition(caseX + typeDeplacement.getX(), caseY + typeDeplacement.getY()));

            if (this.checkPerdue()) {
                this.terrain.getIntrus().setStatusIntru(StatusIntru.PERDU);
                mainView.getScene().setOnKeyPressed(null);
                System.out.println("Perdu");
                mainView.getLittleCycle().stop();
                Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Deplacement piegeur => personage: " + personage.toString() + "; typeDeplacement: " + typeDeplacement);
                Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Le Joueur vien de perdre");
            } else if (personage instanceof Intrus) {
                if (personage.getCaseActuel().getStatusCase() == StatusCase.SORTIE && ((Intrus) personage).getStatusIntru() == StatusIntru.FUITE) {
                    ((Intrus) personage).setStatusIntru(StatusIntru.GAGNER);
                    mainView.getScene().setOnKeyPressed(null);
                    mainView.getLittleCycle().stop();
                    System.out.println("Gagner");
                    Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Deplacement de la victoire => personage: " + personage.toString() + "; typeDeplacement: " + typeDeplacement);
                    Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Le Joueur vien de gagner");
                }

                this.updateCelluleVisible((Intrus) personage, mainView);
            }

            if (personage instanceof Robot) {
                this.recherJoueur((Robot) personage);
            }
        } else {
            if (personage instanceof Intrus){
                this.getTerrain().getIntrus().setEnDeplacement(false);
            }
            Logger.getInstance().ajouteUneLigne(TypeLog.WARN, "Deplacement Imposible => personage: " + personage.toString() + "; typeDeplacement: " + typeDeplacement);
        }
    }

    /**
     * Recher le joueur par le robot
     *
     * @param personage
     */
    private void recherJoueur(Robot personage) {
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Recherche Joueur => Robot" + personage);
        int x = personage.getCaseActuel().getX();
        int y = personage.getCaseActuel().getY();
        for (int i = x - Main.DISTANCE_VUE_ROBOT; i < x + Main.DISTANCE_VUE_ROBOT; i++) {
            for (int j = y - Main.DISTANCE_VUE_ROBOT; j < y + Main.DISTANCE_VUE_ROBOT; j++) {
                if (Math.pow((i - x), 2) + Math.pow((j - y), 2) <= Math.pow(Main.DISTANCE_VUE_ROBOT, 2)) {
                    Case caseTerain = this.terrain.getCaseViaPosition(i, j);
                    if (caseTerain != null) {
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Joueur détécter => Robot:" + personage + "; joueur:" + this.terrain.getIntrus().toString());
                        if (caseTerain == this.terrain.getIntrus().getCaseActuel()) {
                            Robot.setCaseIntru(caseTerain);
                            Robot.setNbTickDeRecherche(Main.NB_TICKE_RECHECHERCHE);
                            this.terrain.getRobots().forEach(robot -> robot.setStatusRobo(StatusRobo.CHASSE));
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
        Case caseIntrus = this.terrain.getIntrus().getCaseActuel();
        final boolean[] res = {false};
        this.terrain.getRobots().forEach(robot -> {
            if (robot.getCaseActuel() == caseIntrus) {
                res[0] = true;
                Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Le joueur vien d'être attraper => robot:" + robot.toString() + "; joueur :" + this.terrain.getIntrus().toString());
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
                    Case caseTerain = this.terrain.getCaseViaPosition(i, j);
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
                    Case caseTerain = this.terrain.getCaseViaPosition(i, j);
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
        if (this.terrain.getIntrus().getCaseActuel().getStatusCase() == StatusCase.MESSAGE) {
            Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Récupérer le message => joueur:" + this.terrain.getIntrus().toString());
            mainView.getScene().setFill(Color.DARKGREEN);
            dessinIntrus.setFill(DessinIntrus.couleurIntrusMessage);
            this.terrain.getIntrus().setStatusIntru(StatusIntru.FUITE);
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
                    if (this.terrain.getCaseViaPosition(robot.getCaseActuel().getX() + TypeDeplacement.GAUCHE.getX(), robot.getCaseActuel().getY() + TypeDeplacement.GAUCHE.getY()).getStatusCase() != StatusCase.MUR) {
                        res = TypeDeplacement.GAUCHE;
                    }
                } else if (robot.getCaseActuel().getX() < Robot.getCaseIntru().getX()) {
                    if (this.terrain.getCaseViaPosition(robot.getCaseActuel().getX() + TypeDeplacement.DROITE.getX(), robot.getCaseActuel().getY() + TypeDeplacement.DROITE.getY()).getStatusCase() != StatusCase.MUR) {
                        res = TypeDeplacement.DROITE;
                    }
                } else if (robot.getCaseActuel().getY() > Robot.getCaseIntru().getY()) {
                    if (this.terrain.getCaseViaPosition(robot.getCaseActuel().getX() + TypeDeplacement.HAUT.getX(), robot.getCaseActuel().getY() + TypeDeplacement.HAUT.getY()).getStatusCase() != StatusCase.MUR) {
                        res = TypeDeplacement.HAUT;
                    }
                } else if (robot.getCaseActuel().getY() < Robot.getCaseIntru().getY()) {
                    if (this.terrain.getCaseViaPosition(robot.getCaseActuel().getX() + TypeDeplacement.BAS.getX(), robot.getCaseActuel().getY() + TypeDeplacement.BAS.getY()).getStatusCase() != StatusCase.MUR) {
                        res = TypeDeplacement.BAS;
                    }
                } else {
                    Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Reinitialise la recherche des robot");
                    Robot.setCaseIntru(null);
                    this.terrain.getRobots().forEach(robot1 -> robot1.setStatusRobo(StatusRobo.PATROUILLE));
                }
            }
        } else if (Robot.getCaseIntru() != null) {
            Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Reinitialise la recherche des robot");
            Robot.setCaseIntru(null);
            this.terrain.getRobots().forEach(robot1 -> robot1.setStatusRobo(StatusRobo.PATROUILLE));
        }
        return res;
    }
}
