package Vue;

import Controleur.Controleur;
import Controleur.TypeDeplacement;
import Main.Main;
import Modele.Case;
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
    private DessinIntrus DessinIntru;

    private final static MainView mainView = new MainView();

    public static MainView getInstance() {
        return MainView.mainView;
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
        troupe = new Group();
        scene = new Scene(troupe, width, height, Color.ANTIQUEWHITE);
        this.dessinEnvironnement();
        scene.setFill(Color.DARKGRAY);
        this.dessinEnvironnement();
        this.animation();
        DessinIntrus dessinIntrus = this.DessinIntru;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent keyEvent){
                switch (keyEvent.getCode()){
                    case UP:
                    case Z:
                        Controleur.getInstance().Deplacement(TypeDeplacement.HAUT,Terrain.getInstance().getIntrus(), dessinIntrus);
                        break;
                    case DOWN:
                    case S:
                        Controleur.getInstance().Deplacement(TypeDeplacement.BAS,Terrain.getInstance().getIntrus(), dessinIntrus);
                        break;
                    case LEFT:
                    case Q:
                        Controleur.getInstance().Deplacement(TypeDeplacement.GAUCHE,Terrain.getInstance().getIntrus(), dessinIntrus);
                        break;
                    case RIGHT:
                    case D:
                        Controleur.getInstance().Deplacement(TypeDeplacement.DROITE,Terrain.getInstance().getIntrus(), dessinIntrus);
                        break;
                }
            }
        });

        primaryStage.setTitle("Poursuite de robots");
        primaryStage.setScene(scene);
        primaryStage.show();
        Timeline littleCycle = new Timeline(new KeyFrame(Duration.millis(Main.tempo),
                event -> {
                    this.animation();
                }));
        littleCycle.setCycleCount(Timeline.INDEFINITE);
        littleCycle.play();
    }

    private void animation() {

    }

    private void dessinEnvironnement() {
        for (Case caseTerain : Terrain.getInstance().getCases()) {
            DessinCase dessinCase = new DessinCase(caseTerain.getX(), caseTerain.getY(), caseTerain.getStatusCase());
            this.dessinCases.add(dessinCase);
            this.troupe.getChildren().add(dessinCase);
        }
        this.DessinIntru = new DessinIntrus(Terrain.getInstance().getIntrus().getCaseActuel().getX(), Terrain.getInstance().getIntrus().getCaseActuel().getY());
        this.troupe.getChildren().add(DessinIntru);
    }
}
