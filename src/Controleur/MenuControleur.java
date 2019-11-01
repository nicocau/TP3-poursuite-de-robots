package Controleur;

import Main.Main;
import Modele.Niveau;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MenuControleur implements Initializable {
    @FXML
    private ComboBox<Niveau> niveaus;
    @FXML
    private Button closeButton;

    @FXML
    private Spinner<Integer> editTailleX;
    @FXML
    private Spinner<Integer> editTailleY;
    @FXML
    private Spinner<Integer> editNbRobot;
    @FXML
    private Spinner<Integer> editVueJouer;
    @FXML
    private Spinner<Integer> editLectureJoueur;
    @FXML
    private Spinner<Integer> editVueRobot;
    @FXML
    private Spinner<Double> editPencentageMure;
    @FXML
    private Spinner<Double> editVittes;
    @FXML
    private Spinner<Integer> editTickeRechercheRobot;

    private HashSet<Spinner> spinners = new HashSet<Spinner>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.chargeCSS();
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

    private void chargeCSS() {
        ObservableList<Niveau> observableList = FXCollections.observableArrayList();
        Path orderPath = Paths.get("./src/Config/config.csv");
        try {
            List<String>lignes = Files.readAllLines(orderPath);
            lignes.forEach(ligne -> {
                String[] split = ligne.split(",");
                observableList.add(new Niveau(split[0],Integer.valueOf(split[1]),Integer.valueOf(split[2]),Double.valueOf(split[3]),Integer.valueOf(split[4]),Double.valueOf(split[5])));
            });
        } catch (IOException e) {
            System.out.println("Impossible de lire le fichier des commandes");
        }
        this.niveaus.setItems(observableList);
    }

    public void valide(MouseEvent mouseEvent) {
        Main.TAILLE_X = this.editTailleX.getValue();
        Main.TAILLE_Y = this.editTailleY.getValue();
        Main.NB_ROBOTS = this.editNbRobot.getValue();
        Main.DISTANCE_VUE = this.editVueJouer.getValue();
        Main.DISTANCE_VUE_MESSAGE = this.editLectureJoueur.getValue();
        Main.DISTANCE_VUE_ROBOT = this.editVueRobot.getValue();
        Main.POURCENTAGE_MUR = this.editPencentageMure.getValue();
        Main.tempo = this.editVittes.getValue();
        Main.NB_TICKE_RECHECHERCHE = this.editTickeRechercheRobot.getValue();
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void modificationNiveau(ActionEvent actionEvent) {
        Niveau niveau = this.niveaus.getValue();
        this.editNbRobot.getValueFactory().setValue(niveau.getNbRobots());
        Main.NB_ROBOTS = niveau.getNbRobots();
        this.editVittes.getValueFactory().setValue(niveau.getVittesseRobot());
        Main.tempo = niveau.getVittesseRobot();
        this.editVueJouer.getValueFactory().setValue(niveau.getChampDeVision());
        Main.DISTANCE_VUE = niveau.getChampDeVision();
        this.editPencentageMure.getValueFactory().setValue(niveau.getDensiteCaise());
        Main.POURCENTAGE_MUR = niveau.getDensiteCaise();
    }
}
