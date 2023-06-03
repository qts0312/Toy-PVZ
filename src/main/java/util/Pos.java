package util;

/**
 * Pos class
 * <p>
 *      Position of a cell in the play field.
 *      Besides, we record parameters of the cell.
 * </p>
 */

public class Pos {
    public static final int CELL_WIDTH = 81;
    public static final int CELL_HEIGHT = 98;

    /*
     * Y |
     *   |
     *   |--------
     *         X
     */
    private int X;
    private int Y;

    /**
     * Constructor of Pos
     * @param x the leftmost position of the cell
     * @param y the topmost position of the cell
     */
    public Pos(int x, int y) {
        X = x;
        Y = y;
    }

    public Pos(Pos pos) {
        X = pos.getX();
        Y = pos.getY();
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    /**
     * Check if the cell is overlapped with the given position
     * @param pos the given position
     * @return true if the cell is overlapped with the given position
     */
    public boolean overlap(Pos pos) {
        return Math.abs(X - pos.getX()) < CELL_WIDTH / 4 &&
                Math.abs(Y - pos.getY()) < CELL_HEIGHT / 4;
    }

    /*
     * Manhattan distance
     */
    public int distance(Pos pos) {
        return Math.max(Math.abs(X - pos.getX()), Math.abs(Y - pos.getY()));
    }

    @Override
    public String toString() {
        return "(" + X + ", " + Y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pos) {
            Pos pos = (Pos) obj;
            return X == pos.getX() && Y == pos.getY();
        }
        return false;
    }

    /**
     * Hash function.
     * If use this member as key of hashmap, it's required that only one object in a cell.
     * @return hash value
     */
    @Override
    public int hashCode() {
        return X / CELL_WIDTH * 1000 + Y / CELL_HEIGHT;
    }
}
