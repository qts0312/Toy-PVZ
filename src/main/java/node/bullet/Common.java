package node.bullet;

import node.plant.Plant;
import node.zombie.Zombie;
import util.Manager;
import util.Pos;


public class Common extends Bullet{
    public static final int SPEED = 30;
    public static final int DAMAGE = 10;
    public static final String URL = "common.gif";

    public Common(int time, Pos pos, Plant holder) {
        super(COMMON,
                SPEED,
              DAMAGE,
              time,
              URL,
              pos,
                holder);
    }

    @Override
    public void routine() {
        Pos pos = getPos();
        Zombie zombie = getGame().zombies.get(z -> pos.distance(z.getPos()) < Pos.CELL_WIDTH);


        if (zombie != null && pos.getX() - zombie.getPos().getX() > 20) {
            getGame().bullets.remove(new Manager.Key(getTime(), getHolder().getTime()));
            getGame().removeNode(getLabel());
            zombie.setLife(-getDamage());

            if (zombie.isDead()) {
                getGame().zombies.remove(new Manager.Key(zombie.getTime()));
                getGame().removeNode(zombie.getLabel());
                getGame().numLine[zombie.getLine()]--;
            }
        }
    }
}
