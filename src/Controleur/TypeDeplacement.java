package Controleur;

public enum TypeDeplacement {
    HAUT(0, -1),
    BAS(0, 1),
    GAUCHE(-1, 0),
    DROITE(1, 0),
    DIAGONAL_HAUT_GAUCHE(-1, -1),
    DIAGONAL_HAUT_DROITE(1, -1),
    DIAGONAL_BAS_GAUCHE(-1, 1),
    DIAGONAL_BAS_DROITE(1, 1);

    private int x = 0;
    private int y = 0;

    /**
     * Constructeur des type de déplacment
     *
     * @param x
     * @param y
     */
    TypeDeplacement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * retourne une valeur aléatoir
     *
     * @return typeDeDeplacement
     */
    public static TypeDeplacement getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }

    /**
     * retourne le x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * retour le y
     *
     * @return y
     */
    public int getY() {
        return y;
    }
}
