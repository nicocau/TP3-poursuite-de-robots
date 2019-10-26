package Modele;

public class Niveau {
    private String labelle;
    private Integer idNiveau;
    private Integer nbRobots;
    private Double vittesseRobot;
    private Integer champDeVision;
    private Double densiteCaise;

    public Niveau(String labelle, Integer idNiveau, Integer nbRobots, Double vittesseRobot, Integer champDeVision, Double densiteCaise) {
        this.labelle = labelle;
        this.idNiveau = idNiveau;
        this.nbRobots = nbRobots;
        this.vittesseRobot = vittesseRobot;
        this.champDeVision = champDeVision;
        this.densiteCaise = densiteCaise;
    }

    public Integer getNbRobots() {
        return nbRobots;
    }

    public Double getVittesseRobot() {
        return vittesseRobot;
    }

    public Integer getChampDeVision() {
        return champDeVision;
    }

    public Double getDensiteCaise() {
        return densiteCaise;
    }

    @Override
    public String toString() {
        return this.labelle;
    }
}
