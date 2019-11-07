package vue;

import controleur.Controleur;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import main.Main;

public abstract class DessinPerssonage extends Circle {
    /**
     * Couleur des robot quand il sont en patroulle
     */
    public static Color couleurRobot = Color.RED;
    /**
     * Couleur des robot quand il sont en chase
     */
    public static Color couleurRobotChase = Color.PURPLE;
    /**
     * Couleur de l'intru avant qu'il trouve les message
     */
    public static Color couleurIntrus = Color.GREEN;
    /**
     * Couleur de l'intru aprés avoir trouver le message
     */
    public static Color couleurIntrusMessage = Color.PINK;
    /**
     * Possition du perssonage en x
     */
    protected int posX;
    /**
     * Possition du perssonage en y
     */
    protected int posY;

    /**
     * constructeur par defaut
     *
     * @param posX
     * @param posY
     * @param color
     */
    public DessinPerssonage(int posX, int posY, Color color) {
        super((posX * Main.tailleCase + Main.tailleCase + Main.tailleCase), (posY * Main.tailleCase + Main.tailleCase + Main.tailleCase / 2), Main.tailleCase / 1.5);
        this.posX = posX;
        this.posY = posY;
        this.setFill(color);
        this.setSmooth(true);
    }

    /**
     * calcul et déplace le perssonage
     *  @param x
     * @param y
     * @param controleur
     */
    public void calculPosition(int x, int y, Controleur controleur, MainView mainView) {
        if (this instanceof DessinIntrus) {
            controleur.getTerrain().getIntrus().setEnDeplacement(true);
        }
        this.posX += x;
        this.posY += y;
        DessinPerssonage dessinPerssonage = this;
        Timeline timeline = new Timeline();
        KeyFrame bouge = new KeyFrame(new Duration(Main.tempo),
                (ActionEvent -> {
                    if (dessinPerssonage instanceof DessinIntrus){
                        controleur.getTerrain().getIntrus().setEnDeplacement(false);
                    }
                }),
                new KeyValue(this.centerXProperty(), posX * Main.tailleCase + Main.tailleCase + Main.tailleCase / 2),
                new KeyValue(this.centerYProperty(), posY * Main.tailleCase + Main.tailleCase + Main.tailleCase / 2)
        );
        timeline.getKeyFrames().add(bouge);
        timeline.play();
        this.setCenterX(posX * Main.tailleCase + Main.tailleCase + Main.tailleCase / 2);
        this.setCenterY(posY * Main.tailleCase + Main.tailleCase + Main.tailleCase / 2);
        this.toFront();
    }
}
