package Controleur;

public enum  TypeDeplacement {
    HAUT (0,-1),
    BAS (0,1),
    GAUCHE (-1,0),
    DROITE(1, 0),
    DIAGONAL_HAUT_GAUCHE(-1, -1),
    DIAGONAL_HAUT_DROITE(1, -1),
    DIAGONAL_BAS_GAUCHE(-1, 1),
    DIAGONAL_BAS_DROITE(1, 1);

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

    public static TypeDeplacement getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
