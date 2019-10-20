package Vue;

import Main.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public abstract class DessinPerssonage extends Circle {
    public static Color couleurRobot = Color.RED;
    public static Color couleurRobotChase = Color.PURPLE;
    public static Color couleurIntrus = Color.GREEN;
    public static Color couleurIntrusMessage = Color.PINK;
    private int posX;
    private int posY;

    public DessinPerssonage(int posX, int posY, Color color) {
        super((posX * Main.tailleCase + Main.tailleCase + Main.tailleCase / 2), (posY * Main.tailleCase + Main.tailleCase + Main.tailleCase / 2), Main.tailleCase / 1.5);
        this.posX = posX;
        this.posY = posY;
        this.setFill(color);
        this.setSmooth(true);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void calculPosition(int x, int y) {
        this.posX += x;
        this.posY += y;
        Timeline timeline = new Timeline();
        KeyFrame bouge = new KeyFrame(new Duration((this instanceof DessinIntrus) ? (float) Main.tempo / 5 : (float) Main.tempo / 2),
                new KeyValue(this.centerXProperty(), posX * Main.tailleCase + Main.tailleCase + Main.tailleCase / 2),
                new KeyValue(this.centerYProperty(), posY * Main.tailleCase + Main.tailleCase + Main.tailleCase / 2));
        timeline.getKeyFrames().add(bouge);
        timeline.play();
        this.setCenterX(posX * Main.tailleCase + Main.tailleCase + Main.tailleCase / 2);
        this.setCenterY(posY * Main.tailleCase + Main.tailleCase + Main.tailleCase / 2);
        this.toFront();
    }
}
