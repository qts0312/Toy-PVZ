package node.zombie;

import node.plant.Plant;
import util.Pos;

public class Jump extends Zombie {
    public static final int LIFE = 100;
    public static final int SPEED = 2;
    public static final int DEMAGE = 10;
    public static final String URL = "jump.gif";
    public static final String attackURL = "jump_attack.gif";

    private boolean canJump;

    public Jump(int time, Pos pos) {
        super(JUMP,
                LIFE,
                SPEED,
                DEMAGE,
                time,
                URL,
                attackURL,
                pos);
        canJump = true;
    }

    @Override
    public void routine() {
        Pos pos = getPos();
        Plant plant = getGame().plants.get(p -> pos.overlap(p.getPos()));

        if (plant != null) {
            if (canJump) {
                getPos().setX(getPos().getX() - 150);
                getLabel().setLayoutX(transX(getPos()));
                canJump = false;
            }
            else{
                getLabel().setLayoutX(transX(getPos()));
                setCanMove(false);
                plant.setLife(-getDamage());
                if (plant.isDead()) {
                    getGame().plants.remove(plant.getPos());
                    getGame().removeNode(plant.getLabel());
                    setCanMove(true);
                }
            }
        } else {
            getLabel().setLayoutX(transX(getPos()));
            setCanMove(true);
        }
    }

    public static double transX(Pos pos) {
        return pos.getX() - 100;
    }

    public static double transY(Pos pos) {
        return pos.getY() - 80;
    }
}
