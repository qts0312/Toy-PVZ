package node.zombie;

import node.plant.Plant;
import util.Pos;

public class Jump extends Zombie {
    public static final int LIFE = 100;
    public static final int SPEED = 50;
    public static final int DEMAGE = 20;
    public static final String URL = "/resources/jump.gif";

    private boolean canJump;

    public Jump(int time, Pos pos) {
        super(JUMP,
                LIFE,
                SPEED,
                DEMAGE,
                time,
                URL,
                pos);
        canJump = true;
    }

    @Override
    public void routine() {
        Pos pos = getPos();
        Plant plant = getGame().plants.get(p -> pos.overlap(p.getPos()));

        if (plant != null) {
            if (canJump) {
                getPos().setX(getPos().getX() - 100);
                canJump = false;
            }
            else{
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
}
