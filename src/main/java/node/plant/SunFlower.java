package node.plant;

import util.Pos;

/**
 * WallNut class
 * Wall nut extends the plant.
 */
public class SunFlower extends Plant {
    public static final int LIFE = 100;
    public static final int COST = 50;
    public static final int DEMAGE = 0;
    public static final int SPEED = 0;
    public static final int RANGE = 0;
    public static final String URL = "/resources/sunflower.gif";

    public SunFlower(int time, Pos pos) {
        super(SUNFLOWER,
                LIFE,
                COST,
                new Attack(DEMAGE, SPEED, RANGE),
                time,
                URL,
                pos);
    }

    @Override
    public void routine(int time) {
        getGame().sun.createSuns(getPos().getX(), getPos().getY());
    }
}
