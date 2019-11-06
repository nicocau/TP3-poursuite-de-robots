package vue;

public class TrouVision {
    /**
     * Position en x du troup
     */
    private int x;

    /**
     * Position en y du troup
     */
    private int y;

    /**
     * Constructeur par défault
     * @param x
     * @param y
     */
    public TrouVision(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne la possition du troup en x
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la possition du troup en x
     *
     * @return
     */
    public int getY() {
        return y;
    }
}
