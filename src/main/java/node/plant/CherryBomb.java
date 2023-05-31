package node.plant;

import javafx.scene.image.Image;
import node.zombie.Zombie;
import util.Manager;
import util.Pos;

import java.util.ArrayList;
import java.util.Objects;

public class CherryBomb extends Plant {
public static final int LIFE = 0;
    public static final int COST = 150;
    public static final int DEMAGE = 100;
    public static final int SPEED = 0;
    public static final int RANGE = 0;
    public static final String URL = "cherrybomb.gif";

    private Image bombImage;

    public CherryBomb(int time, Pos pos) {
        super(CHERRYBOMB,
                LIFE,
                COST,
                new Attack(DEMAGE, SPEED, RANGE),
                time,
                URL,
                pos);
//        bombImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/cherrybomb_bomb.gif")), Pos.CELL_WIDTH * 2, Pos.CELL_HEIGHT * 2, false, false);
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void routine(int time) {
//        getLabel().setGraphic(new javafx.scene.image.ImageView(bombImage));

        ArrayList<Zombie> zombies = getGame().zombies.getAll();
        Pos pos = getPos();

        getGame().plants.remove(getPos());
        getGame().removeNode(getLabel());
        for (Zombie z: zombies) {
            if (pos.distance(z.getPos()) <= 2 * Pos.CELL_WIDTH) {
                z.setLife(-getAttack().getDamage());
                if (z.isDead()) {
                    getGame().zombies.remove(new Manager.Key(z.getTime()));
                    getGame().removeNode(z.getLabel());
                    getGame().numLine[z.getLine()]--;
                }
            }
        }
    }
}
