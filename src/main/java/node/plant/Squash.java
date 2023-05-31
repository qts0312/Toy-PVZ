package node.plant;

import node.zombie.Zombie;
import util.Manager;
import util.Pos;

import java.util.ArrayList;

public class Squash extends Plant {
    public static final int LIFE = 100;
    public static final int COST = 50;
    public static final int DEMAGE = 200;
    public static final int SPEED = 0;
    public static final int RANGE = 0;
    public static final String URL = "squash.gif";

    public Squash(int time, Pos pos) {
        super(SQUASH,
                LIFE,
                COST,
                new Attack(DEMAGE, SPEED, RANGE),
                time,
                URL,
                pos);
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void routine(int time) {
        ArrayList<Zombie> zombies = getGame().zombies.getAll();
        Pos pos = getPos();

        boolean bomb = false;
        for (Zombie z : zombies) {
            if (pos.overlap(z.getPos())) {
                bomb = true;
                z.setLife(-getAttack().getDamage());
                if (z.isDead()) {
                    getGame().zombies.remove(new Manager.Key(z.getTime()));
                    getGame().removeNode(z.getLabel());
                    getGame().numLine[z.getLine()]--;
                }
            }
        }

        if (bomb) {
            getGame().plants.remove(pos);
            getGame().removeNode(getLabel());
        }
    }
}
