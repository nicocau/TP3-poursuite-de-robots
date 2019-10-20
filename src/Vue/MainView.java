package Vue;

import Controleur.Controleur;
import Controleur.TypeDeplacement;
import Log.Logger;
import Log.TypeLog;
import Main.Main;
import Modele.Case;
import Modele.Robot;
import Modele.StatusRobo;
import Modele.Terrain;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class MainView extends Application {

    private final static MainView mainView = new MainView();
    private double width = 0;
    private double height = 0;
    private Scene scene;
    private Group troupe;
    private ArrayList<DessinCase> dessinCases = new ArrayList<DessinCase>();
    private DessinIntrus dessinIntru;
    private ArrayList<DessinRobots> dessinRobots = new ArrayList<DessinRobots>();
    private Timeline littleCycle;

    /**
     * retourne le singleton
     *
     * @return le singleton
     */
    public static MainView getInstance() {
        return MainView.mainView;
    }

    /**
     * permet de récuperer la scene
     *
     * @return screne
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * retourne les dessin des robots
     *
     * @return dessin robots
     */
    public ArrayList<DessinRobots> getDessinRobots() {
        return dessinRobots;
    }

    /**
     * retoure la timeline
     *
     * @return timeline
     */
    public Timeline getLittleCycle() {
        return littleCycle;
    }

    /**
     * permet de lancer la vue
     *
     * @param args
     */
    public void lancement(String[] args) {
        launch(args);
    }

    /**
     * methode initialisant la vue
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        width = Main.TAILLE_X * Main.tailleCase + (2 * Main.tailleCase);
        height = Main.TAILLE_Y * Main.tailleCase + (2 * Main.tailleCase);
        construirePlateauJeu(primaryStage);
    }

    /**
     * methode permetant de crée la vue
     *
     * @param primaryStage
     */
    void construirePlateauJeu(Stage primaryStage) {
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée le plateau");
        this.troupe = new Group();
        this.scene = new Scene(troupe, width, height, Color.ANTIQUEWHITE);
        this.dessinEnvironnement();
        this.scene.setFill(Color.DARKGRAY);

        DessinIntrus dessinIntrus = this.dessinIntru;
        MainView mainView = this;
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée les touche");
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP:
                    case Z:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Déplace le joueur en haut");
                        Controleur.getInstance().Deplacement(TypeDeplacement.HAUT, Terrain.getInstance().getIntrus(), dessinIntrus, mainView);
                        break;
                    case DOWN:
                    case S:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Déplace le joueur en bas");
                        Controleur.getInstance().Deplacement(TypeDeplacement.BAS, Terrain.getInstance().getIntrus(), dessinIntrus, mainView);
                        break;
                    case LEFT:
                    case Q:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Déplace le joueur a gauche");
                        Controleur.getInstance().Deplacement(TypeDeplacement.GAUCHE, Terrain.getInstance().getIntrus(), dessinIntrus, mainView);
                        break;
                    case RIGHT:
                    case D:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Déplace le joueur a droit");
                        Controleur.getInstance().Deplacement(TypeDeplacement.DROITE, Terrain.getInstance().getIntrus(), dessinIntrus, mainView);
                        break;
                    case SPACE:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Clique sur espace");
                        Controleur.getInstance().PrendMsg(mainView, mainView.dessinIntru);
                        break;
                }
            }
        });

        primaryStage.setTitle("Poursuite de robots");
        primaryStage.setScene(scene);
        primaryStage.show();
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Ajoute la timeline");
        this.littleCycle = new Timeline(new KeyFrame(Duration.millis(Main.tempo),
                event -> {
                    Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "nouveau ticke");
                    this.animation();
                }));
        littleCycle.setCycleCount(Timeline.INDEFINITE);
        littleCycle.play();
    }

    /**
     * manime les éléments
     */
    private void animation() {
        Integer[] i = {0};
        MainView mainView = this;
        Terrain.getInstance().getRobots().forEach(robot -> {
            TypeDeplacement typeDeplacement = Controleur.getInstance().choseRobotMove(robot);
            Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Anime le robot => robot:" + robot + "; deplacement: " + typeDeplacement);
            Controleur.getInstance().Deplacement(typeDeplacement, robot, mainView.getDessinRobots().get(i[0]), mainView);
            mainView.getDessinRobots().get(i[0]).setFill((robot.getStatusRobo() == StatusRobo.PATROUILLE) ? DessinPerssonage.couleurRobot : DessinPerssonage.couleurRobotChase);
            i[0] += 1;
        });
        Robot.setNbTickDeRecherche(Robot.getNbTickDeRecherche() - 1);
    }

    /**
     * methode permetant de crée les élément comme les caise et le robots
     */
    private void dessinEnvironnement() {
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Dessine les case");
        for (Case caseTerain : Terrain.getInstance().getCases()) {
            DessinCase dessinCase = new DessinCase(caseTerain.getX(), caseTerain.getY(), caseTerain.getStatusCase());
            this.dessinCases.add(dessinCase);
            this.troupe.getChildren().add(dessinCase);
        }
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Desine l'intrus");
        this.dessinIntru = new DessinIntrus(Terrain.getInstance().getIntrus().getCaseActuel().getX(), Terrain.getInstance().getIntrus().getCaseActuel().getY());
        this.troupe.getChildren().add(dessinIntru);
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Dessine les robot");
        Terrain.getInstance().getRobots().forEach(robot -> {
            DessinRobots dessinRobots = new DessinRobots(robot.getCaseActuel().getX(), robot.getCaseActuel().getY());
            this.troupe.getChildren().add(dessinRobots);
            this.dessinRobots.add(dessinRobots);
        });
    }

    /**
     * permet de retouner un dessin de case a partire de sa position
     *
     * @param x
     * @param y
     * @return case
     */
    public DessinCase getCaseViaPosition(int x, int y) {
        final DessinCase[] res = {null};
        this.dessinCases.forEach(c -> {
            if (c.getPosX() == x && c.getPosY() == y) {
                res[0] = c;
            }
        });
        return res[0];
    }
}
