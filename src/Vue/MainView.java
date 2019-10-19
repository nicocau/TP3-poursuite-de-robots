package Vue;

import Controleur.Controleur;
import Controleur.TypeDeplacement;
import Main.Main;
import Modele.Case;
import Modele.Robot;
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

public class MainView extends Application{

    private double width = 0;
    private double height = 0;
    private Scene scene;
    private Group troupe;
    private ArrayList<DessinCase> dessinCases = new ArrayList<DessinCase>();
    private DessinIntrus dessinIntru;
    private ArrayList<DessinRobots> dessinRobots = new ArrayList<DessinRobots>();

    private final static MainView mainView = new MainView();
    private Timeline littleCycle;

    public static MainView getInstance() {
        return MainView.mainView;
    }

    public Scene getScene() {
        return scene;
    }

    public DessinIntrus getDessinIntru() {
        return dessinIntru;
    }

    public ArrayList<DessinRobots> getDessinRobots() {
        return dessinRobots;
    }

    public Timeline getLittleCycle() {
        return littleCycle;
    }

    public void lancement(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        width = Main.TAILLE_X * Main.tailleCase + (2 * Main.tailleCase);
        height = Main.TAILLE_Y * Main.tailleCase + (2 * Main.tailleCase);
        construirePlateauJeu(primaryStage);
    }

    void construirePlateauJeu(Stage primaryStage) {
        this.troupe = new Group();
        this.scene = new Scene(troupe, width, height, Color.ANTIQUEWHITE);
        this.dessinEnvironnement();
        this.scene.setFill(Color.DARKGRAY);

        DessinIntrus dessinIntrus = this.dessinIntru;
        MainView mainView = this;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent keyEvent){
                switch (keyEvent.getCode()){
                    case UP:
                    case Z:
                        Controleur.getInstance().Deplacement(TypeDeplacement.HAUT,Terrain.getInstance().getIntrus(), dessinIntrus, mainView);
                        break;
                    case DOWN:
                    case S:
                        Controleur.getInstance().Deplacement(TypeDeplacement.BAS,Terrain.getInstance().getIntrus(), dessinIntrus, mainView);
                        break;
                    case LEFT:
                    case Q:
                        Controleur.getInstance().Deplacement(TypeDeplacement.GAUCHE,Terrain.getInstance().getIntrus(), dessinIntrus, mainView);
                        break;
                    case RIGHT:
                    case D:
                        Controleur.getInstance().Deplacement(TypeDeplacement.DROITE,Terrain.getInstance().getIntrus(), dessinIntrus, mainView);
                        break;
                    case SPACE:
                        Controleur.getInstance().PrendMsg(mainView, mainView.dessinIntru);
                        break;
                }
            }
        });

        primaryStage.setTitle("Poursuite de robots");
        primaryStage.setScene(scene);
        primaryStage.show();
        this.littleCycle = new Timeline(new KeyFrame(Duration.millis(Main.tempo),
                event -> {
                    this.animation();
                }));
        littleCycle.setCycleCount(Timeline.INDEFINITE);
        littleCycle.play();
    }

    private void animation() {
        Integer[] i = {0};
        MainView mainView = this;
        Terrain.getInstance().getRobots().forEach(robot -> {
            TypeDeplacement typeDeplacement = Controleur.getInstance().choseRobotMove(robot);
            Controleur.getInstance().Deplacement(typeDeplacement, robot, mainView.getDessinRobots().get(i[0]), mainView);
            i[0] += 1;
        });
        Robot.setNbTickDeRecherche(Robot.getNbTickDeRecherche()-1);
    }

    private void dessinEnvironnement() {
        for (Case caseTerain : Terrain.getInstance().getCases()) {
            DessinCase dessinCase = new DessinCase(caseTerain.getX(), caseTerain.getY(), caseTerain.getStatusCase());
            this.dessinCases.add(dessinCase);
            this.troupe.getChildren().add(dessinCase);
        }
        this.dessinIntru = new DessinIntrus(Terrain.getInstance().getIntrus().getCaseActuel().getX(), Terrain.getInstance().getIntrus().getCaseActuel().getY());
        this.troupe.getChildren().add(dessinIntru);
        Terrain.getInstance().getRobots().forEach(robot -> {
            DessinRobots dessinRobots = new DessinRobots(robot.getCaseActuel().getX(), robot.getCaseActuel().getY());
            this.troupe.getChildren().add(dessinRobots);
            this.dessinRobots.add(dessinRobots);
        });
    }

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
