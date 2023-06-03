package node.zombie;

import node.plant.Plant;
import util.Pos;


public class Common extends Zombie {
    public static final int LIFE = 100;
    public static final int SPEED = 2;
    public static final int DAMAGE = 10;
    public static final String URL = "common.gif";
    public static final String attackURL = "common_attack.gif";

    public Common(int time, Pos pos) {
        super(COMMON,
                LIFE,
                SPEED,
                DAMAGE,
                time,
                URL,
                attackURL,
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
        } else {
            setCanMove(true);
        }
    }

    public static double transX(Pos pos) {
        return pos.getX() - 45;
    }

    public static double transY(Pos pos) {
        return pos.getY() - 12;
    }
}
