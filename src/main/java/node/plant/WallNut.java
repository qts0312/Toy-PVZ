package node.plant;

import util.Pos;

/**
 * WallNut class
 * Wall nut extends the plant.
 */
public class WallNut extends Plant {
    public static final int LIFE = 1000;
    public static final int COST = 50;
    public static final int DEMAGE = 0;
    public static final int SPEED = 0;
    public static final int RANGE = 0;
    public static final String URL = "wallnut.gif";

    public WallNut(int time, Pos pos) {
        super(WALLNUT,
                LIFE,
                COST,
                new Attack(DEMAGE, SPEED, RANGE),
                time,
                URL,
                pos);
    }

    @Override
    public void routine(int time) {
        // Do nothing
    }
}
