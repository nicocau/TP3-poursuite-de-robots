package Vue;

import Main.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainView extends Application implements EventHandler<MouseEvent> {

    double width = 0;
    double height = 0;
    Scene scene;
    Group troupe;
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
        this.dessinEnvironnement(troupe);
        scene.setFill(Color.DARKGRAY);
        dessinEnvironnement(troupe);

        primaryStage.setTitle("Poursuite de robots");
        primaryStage.setScene(scene);
        primaryStage.show();
        Timeline littleCycle = new Timeline(new KeyFrame(Duration.millis(Main.tempo),
                event -> {

                }));
        littleCycle.setCycleCount(Timeline.INDEFINITE);
        littleCycle.play();
    }

    private void dessinEnvironnement(Group troupe) {

    }


    @Override
    public void handle(MouseEvent mouseEvent) {

    }
}
