package node.plant;

import node.bullet.Bullet;
import util.Manager;
import util.Pos;

/**
 * PeaShooter class
 * Pea shooter extends the plant.
 */
public class PeaShooter extends Plant {
    public static final int LIFE = 100;
    public static final int COST = 100;
    public static final int DEMAGE = 0;
    public static final int SPEED = 0;
    public static final int RANGE = 0;
    public static final String URL = "peashooter.png";

    public PeaShooter(int time, Pos pos) {
        super(PEASHOOTER,
                LIFE,
                COST,
                new Attack(DEMAGE, SPEED, RANGE),
                time,
                URL,
                pos);
    }

    @Override
    public void routine(int time) {
        if (getGame().numLine[getLine()] > 0) {
            Pos pos = new Pos(getPos().getX() + 50, getPos().getY());
            Bullet common = Bullet.getInstance(Bullet.COMMON, time, pos, this);

            if (common != null) {
                System.out.println(time + " " + getTime());
                getGame().bullets.add(new Manager.Key(time, getTime()), common);
            }
        }
    }
}
