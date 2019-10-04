package Modele;

import java.util.ArrayList;

public class Terrain {
    private static final Terrain terrain = new Terrain();
    private ArrayList<Case> cases = new ArrayList<Case>();
    private ArrayList<Robot> robots = new ArrayList<Robot>();
    private ArrayList<Case> caisses = new ArrayList<Case>();
    private Intrus intrus;

    private Terrain() {
    }

    public static Terrain getInstance(){
        return Terrain.terrain;
    }

    public static Terrain getTerrain() {
        return terrain;
    }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public void setCases(ArrayList<Case> cases) {
        this.cases = cases;
    }

    public ArrayList<Robot> getRobots() {
        return robots;
    }

    public void setRobots(ArrayList<Robot> robots) {
        this.robots = robots;
    }

    public ArrayList<Case> getCaisses() {
        return caisses;
    }

    public void setCaisses(ArrayList<Case> caisses) {
        this.caisses = caisses;
    }

    public Intrus getIntrus() {
        return intrus;
    }

    public void setIntrus(Intrus intrus) {
        this.intrus = intrus;
    }
}
