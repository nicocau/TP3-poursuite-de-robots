package Modele;

public class Niveau {
    /**
     * Libelle du niveau
     */
    private String labelle;
    /**
     * Id du niveau
     */
    private Integer idNiveau;
    /**
     * Nombre de robots
     */
    private Integer nbRobots;
    /**
     * Vitesse des robot
     */
    private Double vittesseRobot;
    /**
     * Distance de vue du joueur
     */
    private Integer champDeVision;
    /**
     * Densité de caisse sur le plateau
     */
    private Double densiteCaise;

    /**
     * Contructeur par défeaut
     *
     * @param labelle
     * @param idNiveau
     * @param nbRobots
     * @param vittesseRobot
     * @param champDeVision
     * @param densiteCaise
     */
    public Niveau(String labelle, Integer idNiveau, Integer nbRobots, Double vittesseRobot, Integer champDeVision, Double densiteCaise) {
        this.labelle = labelle;
        this.idNiveau = idNiveau;
        this.nbRobots = nbRobots;
        this.vittesseRobot = vittesseRobot;
        this.champDeVision = champDeVision;
        this.densiteCaise = densiteCaise;
    }

    /**
     * Permet de connaitre le nombre de robots
     *
     * @return nombre de robot
     */
    public Integer getNbRobots() {
        return nbRobots;
    }

    /**
     * Permet de récupérer la vittese des robots
     *
     * @return vittesse des robots
     */
    public Double getVittesseRobot() {
        return vittesseRobot;
    }

    /**
     * Permet de récupérer la distance de vision du joueur
     *
     * @return distance de vue du joueur
     */
    public Integer getChampDeVision() {
        return champDeVision;
    }

    /**
     * Permet de récupérer la denssité de caisse du terrain
     *
     * @return densité de caise du terrain
     */
    public Double getDensiteCaise() {
        return densiteCaise;
    }

    /**
     * Permet de récupérer le string correspondent au niveau
     *
     * @return string du niveau
     */
    @Override
    public String toString() {
        return this.labelle;
    }
}
