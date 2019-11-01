package Vue;

import Controleur.Controleur;
import Controleur.MenuControleur;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class MainView extends Application {

    private Controleur controleur;
//    private final static MainView mainView = new MainView();
    private double width = 0;
    private double height = 0;
    private Scene scene;
    private Group troupe;
    private ArrayList<DessinCase> dessinCases = new ArrayList<DessinCase>();
    private DessinIntrus dessinIntru;
    private ArrayList<DessinRobots> dessinRobots = new ArrayList<DessinRobots>();
    private Timeline littleCycle;

    public Controleur getControleur() {
        return controleur;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
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

//    /**
//     * permet de lancer la vue
//     *
//     * @param args
//     * @param controleur
//     */
//    public void lancement(String[] args, Controleur controleur) {
//        this.setControleur(controleur);
//        this.controleur.setMainView(this);
//        launch(args);
//    }

    /**
     * methode initialisant la vue
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.controleur = new Controleur(this);
        this.ouvreMenu(primaryStage);
        width = Main.TAILLE_X * Main.tailleCase + (2 * Main.tailleCase);
        height = Main.TAILLE_Y * Main.tailleCase + (2 * Main.tailleCase);
        construirePlateauJeu(primaryStage);
    }

    /**
     * Permet d'ouvrire le menue
     * @param primaryStage
     */
    private void ouvreMenu(Stage primaryStage) {

        //chargement du fichier FXML
        String sceneFile = "/VueFXML/menu.fxml";
        AnchorPane page = null;
        FXMLLoader fxmlLoader =null;
        try
        {
            fxmlLoader = new FXMLLoader(getClass().getResource( sceneFile ));
            page =fxmlLoader.load();
        }
        catch ( Exception ex )  {ex.printStackTrace();}

        //si le chargement a reussi
        if (page!=null)
        {
            //creation d'une petite fenetre et de son theatre
            Stage dialogStage = new Stage();

            dialogStage.setTitle("Choix des couleurs...");
            //fenetre modale, obligation de quitter pour revenir a la fenetre principale
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogue modale liee a la fenetre parente
            dialogStage.initOwner(primaryStage);
            //creation de la scene a partir de la page chargee du fichier fxml
            Scene miniScene = new Scene(page);
            dialogStage.setScene(miniScene);
            //recuperation du controleur associe a la fenetre
            MenuControleur controller = fxmlLoader.getController();
            //affichage de la fenetre
            dialogStage.showAndWait();
        }

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
                        mainView.controleur.Deplacement(TypeDeplacement.HAUT, mainView.controleur.getTerrain().getIntrus(), dessinIntrus, mainView);
                        break;
                    case DOWN:
                    case S:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Déplace le joueur en bas");
                        mainView.controleur.Deplacement(TypeDeplacement.BAS, mainView.controleur.getTerrain().getIntrus(), dessinIntrus, mainView);
                        break;
                    case LEFT:
                    case Q:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Déplace le joueur a gauche");
                        mainView.controleur.Deplacement(TypeDeplacement.GAUCHE, mainView.controleur.getTerrain().getIntrus(), dessinIntrus, mainView);
                        break;
                    case RIGHT:
                    case D:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Déplace le joueur a droit");
                        mainView.controleur.Deplacement(TypeDeplacement.DROITE, mainView.controleur.getTerrain().getIntrus(), dessinIntrus, mainView);
                        break;
                    case SPACE:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Clique sur espace");
                        mainView.controleur.PrendMsg(mainView, mainView.dessinIntru);
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
        this.controleur.getTerrain().getRobots().forEach(robot -> {
            TypeDeplacement typeDeplacement = this.controleur.choseRobotMove(robot);
            Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Anime le robot => robot:" + robot + "; deplacement: " + typeDeplacement);
            this.controleur.Deplacement(typeDeplacement, robot, mainView.getDessinRobots().get(i[0]), mainView);
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
        for (Case caseTerain : this.controleur.getTerrain().getCases()) {
            DessinCase dessinCase = new DessinCase(caseTerain.getX(), caseTerain.getY(), caseTerain.getStatusCase());
            this.dessinCases.add(dessinCase);
            this.troupe.getChildren().add(dessinCase);
        }
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Desine l'intrus");
        this.dessinIntru = new DessinIntrus(this.controleur.getTerrain().getIntrus().getCaseActuel().getX(), this.controleur.getTerrain().getIntrus().getCaseActuel().getY());
        this.troupe.getChildren().add(dessinIntru);
        Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Dessine les robot");
        this.controleur.getTerrain().getRobots().forEach(robot -> {
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
