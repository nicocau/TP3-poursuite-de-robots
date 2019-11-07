package vue;

import controleur.Controleur;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.Main;

import java.util.LinkedList;

public class DessinIntrus extends DessinPerssonage {

    private LinkedList<TrouVision> trouVisions = new LinkedList<TrouVision>();

    /**
     * constructeur du dessin de l'intrus
     *
     * @param posX
     * @param posY
     */
    public DessinIntrus(int posX, int posY) {
        super(posX, posY, DessinPerssonage.couleurIntrus);
    }

    @Override
    public void calculPosition(int x, int y, Controleur controleur, MainView mainView) {
        super.calculPosition(x, y, controleur, mainView);
        GraphicsContext graphicsContext = mainView.getCanvas().getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillOval((this.posX - 3) * Main.tailleCase, (this.posY-3) * Main.tailleCase, Main.tailleCase * Main.DISTANCE_VUE * 2, Main.tailleCase * Main.DISTANCE_VUE * 2);
        trouVisions.addLast(new TrouVision(this.posX, this.posY));
    }

    public void oubliePassage(MainView mainView){
        if (this.trouVisions.size() > Main.NB_MEMOIRE) {
            TrouVision trouVision = this.trouVisions.pop();
            GraphicsContext graphicsContext = mainView.getCanvas().getGraphicsContext2D();
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillOval((trouVision.getX() - 3) * Main.tailleCase, (trouVision.getY()-3) * Main.tailleCase, Main.tailleCase * Main.DISTANCE_VUE * 2, Main.tailleCase * Main.DISTANCE_VUE * 2);
            trouVision = this.trouVisions.get(0);
            graphicsContext.setFill(new Color(0.0, 0.0, 0.0, 0.25));
            graphicsContext.fillOval((trouVision.getX() - 3) * Main.tailleCase, (trouVision.getY()-3) * Main.tailleCase, Main.tailleCase * Main.DISTANCE_VUE * 2, Main.tailleCase * Main.DISTANCE_VUE * 2);
            trouVision = this.trouVisions.get(1);
            graphicsContext.setFill(new Color(0.0, 0.0, 0.0, 0.5));
            graphicsContext.fillOval((trouVision.getX() - 3) * Main.tailleCase, (trouVision.getY() - 3) * Main.tailleCase, Main.tailleCase * Main.DISTANCE_VUE * 2, Main.tailleCase * Main.DISTANCE_VUE * 2);
            trouVision = this.trouVisions.get(2);
            graphicsContext.setFill(new Color(0.0, 0.0, 0.0, 0.75));
            graphicsContext.fillOval((trouVision.getX() - 3) * Main.tailleCase, (trouVision.getY() - 3) * Main.tailleCase, Main.tailleCase * Main.DISTANCE_VUE * 2, Main.tailleCase * Main.DISTANCE_VUE * 2);

            this.trouVisions.stream().skip(4).forEach(trouVisionFor -> {
                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillOval((trouVisionFor.getX() - 3) * Main.tailleCase, (trouVisionFor.getY() - 3) * Main.tailleCase, Main.tailleCase * Main.DISTANCE_VUE * 2, Main.tailleCase * Main.DISTANCE_VUE * 2);
            });
        }
    }
}
