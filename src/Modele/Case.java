package Modele;

public class Case {
    private int x = -1;
    private int y = -1;
    private StatusCase statusCase = StatusCase.VIDE;
    private boolean decouvert = false;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public StatusCase getStatusCase() {
        return statusCase;
    }

    public void setStatusCase(StatusCase statusCase) {
        this.statusCase = statusCase;
    }

    public boolean isDecouvert() {
        return decouvert;
    }

    public void setDecouvert(boolean decouvert) {
        this.decouvert = decouvert;
    }
}
