package controleur;

import main.Main;
import modele.Niveau;
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
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class MenuControleur implements Initializable {
    /**
     * Liste des niveau
     */
    @FXML
    private ComboBox<Niveau> niveaus;
    /**
     * Bouton de validation
     */
    @FXML
    private Button closeButton;

    /**
     * Spiner pout le nombre de case en x
     */
    @FXML
    private Spinner<Integer> editTailleX;
    /**
     * Spiner pout le nombre de case en y
     */
    @FXML
    private Spinner<Integer> editTailleY;
    /**
     * Spiner pout le nombre de robot
     */
    @FXML
    private Spinner<Integer> editNbRobot;
    /**
     * Spiner pout la distance de vue du joueur
     */
    @FXML
    private Spinner<Integer> editVueJouer;
    /**
     * Spiner pout la distance de lecture du joueur
     */
    @FXML
    private Spinner<Integer> editLectureJoueur;
    /**
     * Spiner pout la distance de vue des robots
     */
    @FXML
    private Spinner<Integer> editVueRobot;
    /**
     * Spiner pout le pourcentage de mur
     */
    @FXML
    private Spinner<Double> editPencentageMure;
    /**
     * Spiner pout la vittesse des robots
     */
    @FXML
    private Spinner<Double> editVittes;
    /**
     * Spiner pout le nombre de tique de recherche des robots
     */
    @FXML
    private Spinner<Integer> editTickeRechercheRobot;
    /**
     * Spiner pout la taille de la mémoire du joueur
     */
    @FXML
    private Spinner<Integer> editTickeMemoire;

    /**
     * Liste des spinners (on peut utiliser un hashSet car on n'a pas besoin de garder l'ordre dans les spinner n'est pas important)
     */
    private HashSet<Spinner> spinners = new HashSet<Spinner>();

    /**
     * Permet d'initialiser les spiner avec les min et les max ainssi que les listener
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.chargeCSV();
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
        this.editTickeMemoire.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, Main.NB_MEMOIRE));
        this.editTickeMemoire.valueProperty().addListener((obs, oldValue, newValue) -> Main.NB_MEMOIRE = newValue);
        spinners.add(this.editTickeMemoire);

        this.spinners.forEach(spinner -> {
            spinner.setEditable(true);
        });
    }

    /**
     * Permet de récupérer les différent niveau stoquer dans le fichier de fonfig des niveau
     */
    private void chargeCSV() {
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

    /**
     * Evenenement déclancer l'ors du clique sur le bouton valiser et permet d'appliquer les valeur ansie que de fermer la fennetre
     * @param mouseEvent
     */
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

    /**
     * Applique le niveau selectioner (modifie les champ et applique les valeur)
     * @param actionEvent
     */
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
