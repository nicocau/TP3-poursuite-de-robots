package Controleur;

public enum  TypeDeplacement {
    HAUT (0,-1),
    BAS (0,1),
    GAUCHE (-1,0),
    DROITE (1,0);

    private int x = 0;
    private int y = 0;

    TypeDeplacement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
