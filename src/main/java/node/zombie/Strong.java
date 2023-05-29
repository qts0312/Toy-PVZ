package node.zombie;

import node.plant.Plant;
import util.Pos;

public class Strong extends Zombie {
    public static final int LIFE = 300;
    public static final int SPEED = 25;
    public static final int DAMAGE = 50;
    public static final String URL = "strong.png";

    public Strong(int time, Pos pos) {
        super(STRONG,
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
