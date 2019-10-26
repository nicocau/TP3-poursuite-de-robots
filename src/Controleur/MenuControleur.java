package Controleur;

import Main.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class MenuControleur implements Initializable {
    @FXML private Button closeButton;

    public Spinner<Integer> editTailleX;
    public Spinner<Integer> editTailleY;
    public Spinner<Integer> editNbRobot;
    public Spinner<Integer> editVueJouer;
    public Spinner<Integer> editLectureJoueur;
    public Spinner<Integer> editVueRobot;
    public Spinner<Double> editPencentageMure;
    public Spinner<Double> editVittes;
    public Spinner<Integer> editTickeRechercheRobot;
    private HashSet<Spinner> spinners = new HashSet<Spinner>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.editTailleX.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, Main.TAILLE_X));
        this.editTailleX.valueProperty().addListener((obs, oldValue, newValue) -> Main.TAILLE_X = newValue);
        spinners.add(this.editTailleX);
        this.editTailleY.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, Main.TAILLE_Y));
        this.editTailleY.valueProperty().addListener((obs, oldValue, newValue) -> Main.TAILLE_Y = newValue);
        spinners.add(this.editTailleY);
        this.editNbRobot.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, Main.NB_ROBOTS));
        this.editNbRobot.valueProperty().addListener((obs, oldValue, newValue) -> Main.NB_ROBOTS = newValue);
        spinners.add(this.editNbRobot);
        this.editVueJouer.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 5, Main.DISTANCE_VUE));
        this.editVueJouer.valueProperty().addListener((obs, oldValue, newValue) -> Main.DISTANCE_VUE = newValue);
        spinners.add(this.editVueJouer);
        this.editLectureJoueur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 5, Main.DISTANCE_VUE_MESSAGE));
        this.editLectureJoueur.valueProperty().addListener((obs, oldValue, newValue) -> Main.DISTANCE_VUE_MESSAGE = newValue);
        spinners.add(this.editLectureJoueur);
        this.editVueRobot.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 5, Main.DISTANCE_VUE_ROBOT));
        this.editVueRobot.valueProperty().addListener((obs, oldValue, newValue) -> Main.DISTANCE_VUE_ROBOT = newValue);
        spinners.add(this.editVueRobot);
        this.editPencentageMure.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.01, 0.5, Main.POURCENTAGE_MUR, 0.1));
        this.editPencentageMure.valueProperty().addListener((obs, oldValue, newValue) -> Main.POURCENTAGE_MUR = newValue);
        spinners.add(this.editPencentageMure);
        this.editVittes.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(100, 1000, Main.POURCENTAGE_MUR, 100));
        this.editVittes.valueProperty().addListener((obs, oldValue, newValue) -> Main.tempo = newValue);
        spinners.add(this.editVittes);
        this.editTickeRechercheRobot.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, Main.NB_TICKE_RECHECHERCHE));
        this.editTickeRechercheRobot.valueProperty().addListener((obs, oldValue, newValue) -> Main.NB_TICKE_RECHECHERCHE = newValue);
        spinners.add(this.editTickeRechercheRobot);

        this.spinners.forEach(spinner -> {
            spinner.setEditable(true);
        });
    }

    public void valide(MouseEvent mouseEvent) {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
