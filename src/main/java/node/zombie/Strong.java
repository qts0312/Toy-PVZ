package node.zombie;

import node.plant.Plant;
import util.Pos;

public class Strong extends Zombie {
    public static final int LIFE = 300;
    public static final int SPEED = 1;
    public static final int DAMAGE = 25;
    public static final String URL = "strong.gif";
    public static final String attackURL = "strong_attack.gif";

    public Strong(int time, Pos pos) {
        super(STRONG,
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
