package Modele;

import java.util.ArrayList;

public class Terrain {
    private static final Terrain terrain = new Terrain();
    private ArrayList<Case> cases = new ArrayList<Case>();
    private ArrayList<Robot> robots = new ArrayList<Robot>();
    private ArrayList<Case> murs = new ArrayList<Case>();
    private Intrus intrus;

    private Terrain() {
    }

    public static Terrain getInstance(){
        return Terrain.terrain;
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

    public ArrayList<Case> getMurs() {
        return murs;
    }

    public void setMurs(ArrayList<Case> murs) {
        this.murs = murs;
    }

    public Intrus getIntrus() {
        return intrus;
    }

    public void setIntrus(Intrus intrus) {
        this.intrus = intrus;
    }
}
