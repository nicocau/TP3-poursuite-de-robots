package vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controleur.Controleur;
import controleur.MenuControleur;
import controleur.TypeDeplacement;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import log.Logger;
import log.TypeLog;
import main.Main;
import modele.Case;
import modele.Robot;
import modele.StatusRobo;
import modele.Terrain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class MainView extends Application {

    /**
     * Dossier dans le quelle se trouve les fichier de sauvegarde
     */
    public static final String SRC_SAVE = "./src/save/";
    /**
     * Le controleur qui va gerer le lien entre la vue et le controleur
     */
    private Controleur controleur;
    /**
     * Largeur de la fenetre
     */
    private double width = 0;
    /**
     * Hauteur de la fenetre
     */
    private double height = 0;
    /**
     * Scenne principale
     */
    private Scene scene;
    /**
     * Troupe des éléments lier a la scene
     */
    private Group troupe;
    /**
     * Liste des dessin des cases
     */
    private ArrayList<DessinCase> dessinCases = new ArrayList<DessinCase>();
    /**
     * Dessin de l'intrus
     */
    private DessinIntrus dessinIntru;
    /**
     * Liste des dessins des robots
     */
    private ArrayList<DessinRobots> dessinRobots = new ArrayList<DessinRobots>();
    /**
     * Cycle de vie des robots (déplace les robots)
     */
    private Timeline littleCycle;

    /**
     * Canvas qui permet de gérer le broullard
     */
    private Canvas canvas;

    /**
     * Permet de récuperer le dessin de l'intru
     *
     * @return
     */
    public DessinIntrus getDessinIntru() {
        return dessinIntru;
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
     * Retourne le canvas pour le broullard
     *
     * @return
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * methode initialisant la vue
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        if (Files.exists(Paths.get(SRC_SAVE + "main.json")) && Files.exists(Paths.get(SRC_SAVE + "terrain.json"))) {
            this.controleur = new Controleur(this);
            this.chargeSauvegarde();
            this.controleur.getTerrain().getIntrus().setEnDeplacement(false);
        } else {
            this.ouvreMenu(primaryStage);
            this.controleur = new Controleur(this);
        }
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
        String sceneFile = "/vuefxml/menu.fxml";
        AnchorPane page = null;
        FXMLLoader fxmlLoader =null;
        try
        {
            fxmlLoader = new FXMLLoader(getClass().getResource( sceneFile ));
            page =fxmlLoader.load();
        }
        catch ( Exception ex )  {ex.printStackTrace();}

        if (page!=null)
        {
            Stage dialogStage = new Stage();

            dialogStage.setTitle("Configuration de la partie");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene miniScene = new Scene(page);
            dialogStage.setScene(miniScene);
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
        MenuBar menuBar = new MenuBar();

        Menu menuActions = new Menu("Actions");
        MenuItem menuPause = new MenuItem("Pause");
        MenuItem menuReprise = new MenuItem("Reprise");
        menuPause.setOnAction(e -> {
            this.pause();
            menuActions.getItems().set(0, menuReprise);
        });
        menuReprise.setOnAction(e -> {
            this.reprend();
            menuActions.getItems().set(0, menuPause);
        });
        MenuItem menuSauvegarder = new MenuItem("Sauvegarder");
        menuSauvegarder.setOnAction(e -> this.sauvegarde());
        MenuItem menuQuitter = new MenuItem("Quitter");
        menuQuitter.setOnAction(e -> primaryStage.close());
        menuActions.getItems().addAll(menuPause, menuSauvegarder, menuQuitter);

        menuBar.getMenus().addAll(menuActions);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        this.troupe = new Group();
        root.setCenter(troupe);
        this.dessinEnvironnement();
        this.scene = new Scene(root, width + 10, height + 30, Color.ANTIQUEWHITE);
        this.scene.setFill(Color.DARKGRAY);

//        Canevas
        this.canvas = new Canvas((Main.TAILLE_X + 1) * Main.tailleCase, (Main.TAILLE_Y + 1) * Main.tailleCase);
        this.canvas.setBlendMode(BlendMode.SCREEN);
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(Main.tailleCase, Main.tailleCase, canvas.getWidth(), canvas.getHeight());
        graphicsContext.setLineCap(StrokeLineCap.ROUND);
        graphicsContext.setLineJoin(StrokeLineJoin.ROUND);
        this.troupe.getChildren().add(this.canvas);


        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Crée les touche");
        this.ajouteLesComande(scene, this);

        primaryStage.setTitle("Poursuite de robots");
        primaryStage.setScene(scene);
        primaryStage.show();
        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Ajoute la timeline");
        this.littleCycle = new Timeline(new KeyFrame(Duration.millis(Main.tempo),
                event -> {
                    Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "nouveau ticke");
                    this.animation();
                    Logger.getInstance().ajouteUneLigne(TypeLog.DEBUG, "Oubliepassafe");
                    this.dessinIntru.oubliePassage(this);
                }));
        littleCycle.setCycleCount(Timeline.INDEFINITE);
        littleCycle.play();
    }

    /**
     * Mets les comandes quand le jeux tourne
     * @param scene
     * @param mainView
     */
    private void ajouteLesComande(Scene scene, MainView mainView) {
        scene.setOnKeyPressed(keyEvent -> {
            if (!mainView.controleur.getTerrain().getIntrus().isEnDeplacement()) {
                switch (keyEvent.getCode()) {
                    case UP:
                    case Z:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Déplace le joueur en haut");
                        mainView.controleur.Deplacement(TypeDeplacement.HAUT, mainView.controleur.getTerrain().getIntrus(), mainView.getDessinIntru(), mainView);
                        break;
                    case DOWN:
                    case S:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Déplace le joueur en bas");
                        mainView.controleur.Deplacement(TypeDeplacement.BAS, mainView.controleur.getTerrain().getIntrus(), mainView.getDessinIntru(), mainView);
                        break;
                    case LEFT:
                    case Q:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Déplace le joueur a gauche");
                        mainView.controleur.Deplacement(TypeDeplacement.GAUCHE, mainView.controleur.getTerrain().getIntrus(), mainView.getDessinIntru(), mainView);
                        break;
                    case RIGHT:
                    case D:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Déplace le joueur a droit");
                        mainView.controleur.Deplacement(TypeDeplacement.DROITE, mainView.controleur.getTerrain().getIntrus(), mainView.getDessinIntru(), mainView);
                        break;
                    case SPACE:
                        Logger.getInstance().ajouteUneLigne(TypeLog.INFO, "Clique sur espace");
                        mainView.controleur.PrendMsg(mainView, mainView.dessinIntru);
                        break;
                }
            }
            switch (keyEvent.getCode()) {
                case M:
                    mainView.sauvegarde();
                    break;
                case P:
                    mainView.pause();
                    break;
                case R:
                    mainView.reprend();
                    break;
            }
        });
    }

    /**
     * Mettes les commande quand le jeux est en pausse
     * @param scene
     * @param mainView
     */
    private void ajouteLesComandePause(Scene scene, MainView mainView) {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case M:
                    mainView.sauvegarde();
                    break;
                case P:
                    mainView.pause();
                    break;
                case R:
                    mainView.reprend();
                    break;
            }
        });
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
            DessinCase dessinCase = new DessinCase(caseTerain.getX(), caseTerain.getY(), caseTerain.getStatusCase(), caseTerain.isDecouvert(), caseTerain.isLue());
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

    /**
     * Permetre d'enregistre la partie
     */
    private void sauvegarde() {
        this.controleur.getTerrain().getIntrus().setEnDeplacement(false);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Path orderPath;
        orderPath = Paths.get(SRC_SAVE + "main.json");
        try {
            if (Files.exists(orderPath)) {
                Files.delete(orderPath);
            }
            HashMap<String, Object> mainArray = new HashMap<>();
            mainArray.put("TAILLE_X", Main.TAILLE_X);
            mainArray.put("TAILLE_Y", Main.TAILLE_Y);
            mainArray.put("NB_ROBOTS", Main.NB_ROBOTS);
            mainArray.put("DISTANCE_VUE", Main.DISTANCE_VUE);
            mainArray.put("DISTANCE_VUE_MESSAGE", Main.DISTANCE_VUE_MESSAGE);
            mainArray.put("DISTANCE_VUE_ROBOT", Main.DISTANCE_VUE_ROBOT);
            mainArray.put("POURCENTAGE_MUR", Main.POURCENTAGE_MUR);
            mainArray.put("tempo", Main.tempo);
            mainArray.put("NB_TICKE_RECHECHERCHE", Main.NB_TICKE_RECHECHERCHE);
            String json = gson.toJson(mainArray);
            Files.writeString(orderPath, json);
        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier de sauvegarde");
        }
        orderPath = Paths.get(SRC_SAVE + "terrain.json");
        try {
            if (Files.exists(orderPath)) {
                Files.delete(orderPath);
            }
            String json = gson.toJson(this.controleur.getTerrain());
            Files.writeString(orderPath, json);
        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier de sauvegarde");
        }
        this.controleur.getTerrain().getIntrus().setEnDeplacement(false);
    }

    /**
     * Permet de réucupérer et de charger la sauvegarde
     */
    private void chargeSauvegarde() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Path orderPath;
        orderPath = Paths.get(SRC_SAVE + "main.json");
        try {
            if (Files.exists(orderPath)) {
                HashMap<String, Object> mainArray = new HashMap<String, Object>();
                mainArray = gson.fromJson(Files.readString(orderPath), mainArray.getClass());
                Double tmp;
                tmp = (Double) mainArray.get("TAILLE_X");
                Main.TAILLE_X = tmp.intValue();
                tmp = (Double) mainArray.get("TAILLE_Y");
                Main.TAILLE_Y = tmp.intValue();
                tmp = (Double) mainArray.get("NB_ROBOTS");
                Main.NB_ROBOTS = tmp.intValue();
                tmp = (Double) mainArray.get("DISTANCE_VUE");
                Main.DISTANCE_VUE = tmp.intValue();
                tmp = (Double) mainArray.get("DISTANCE_VUE_MESSAGE");
                Main.DISTANCE_VUE_MESSAGE = tmp.intValue();
                tmp = (Double) mainArray.get("DISTANCE_VUE_ROBOT");
                Main.DISTANCE_VUE_ROBOT = tmp.intValue();
                Main.POURCENTAGE_MUR = (Double) mainArray.get("POURCENTAGE_MUR");
                Main.tempo = (Double) mainArray.get("tempo");
                tmp = (Double) mainArray.get("NB_TICKE_RECHECHERCHE");
                Main.NB_TICKE_RECHECHERCHE = tmp.intValue();
            }
        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier de sauvegarde");
        }
        orderPath = Paths.get(SRC_SAVE + "terrain.json");
        try {
            if (Files.exists(orderPath)) {
                this.controleur.setTerrain(gson.fromJson(Files.readString(orderPath), Terrain.class));
            }
        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier de sauvegarde");
        }
    }

    /**
     * Permetre de metre en pause la partie
     */
    private void pause() {
        this.ajouteLesComandePause(this.scene, this);
        this.littleCycle.pause();
    }

    /**
     * Permet de reprendre la partie aprés la pause
     */
    private void reprend() {
        this.ajouteLesComande(this.scene, this);
        this.littleCycle.play();
    }
}
