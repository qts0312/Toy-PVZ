package node.zombie;

import node.plant.Plant;
import util.Pos;

/**
 * Common class
 * Common extends zombie
 */
public class Common extends Zombie {
    public static final int LIFE = 100;
    public static final int SPEED = 50;
    public static final int DAMAGE = 20;
    public static final String URL = "zombie_common.png";

    public Common(int time, Pos pos) {
        super(COMMON,
                LIFE,
                SPEED,
                DAMAGE,
                time,
                URL,
                pos);
    }

    @Override
    public void routine() {
        Pos pos = getPos();
        Plant plant = getGame().plants.get(p -> pos.overlap(p.getPos()));

        if (plant != null) {
            setCanMove(false);
            plant.setLife(-getDamage());
            if (plant.isDead()) {
                getGame().plants.remove(plant.getPos());
                getGame().removeNode(plant.getLabel());
                setCanMove(true);
            }
        }
    }
}
