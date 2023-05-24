package node.bullet;

import game.Game;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import node.plant.Plant;
import util.Pos;

import java.util.Objects;

/**
 * Bullet class
 * This class is used to record the parameters of a bullet.
 * And controls the bullet's life, position, and display.
 */
public abstract class Bullet {
    public static final int COMMON = 0;

    private static Game game;

    public static void setGame(Game game) {
        Bullet.game = game;
    }

    public static Game getGame() {
        return game;
    }

    private final int kind;
    private final int speed;
    private final int damage;
    private final int time;

    private boolean shown;
    private final Label label;
    private final Pos pos;
    private final Plant holder;

    public Bullet(int kind,
                  int speed,
                  int damage,
                  int time,
                  String Url,
                  Pos pos,
                  Plant holder) {
        this.kind = kind;
        this.speed = speed;
        this.damage = damage;
        this.time = time;
        this.shown = false;
        this.pos = pos;
        this.holder = holder;

        label = new Label();
//        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Url)), 100, 100, false, false);
//        label.setGraphic(new ImageView(image));
        label.setText("B");
        label.setId("bullet");
    }

    public int getKind() {
        return kind;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public int getTime() {
        return time;
    }

    public boolean isShown() {
        return shown;
    }

    public Label getLabel() {
        return label;
    }

    public Pos getPos() {
        return pos;
    }

    public Plant getHolder() {
        return holder;
    }

    public void setShown() {
        this.shown = true;
    }

    public void move() {
        pos.setX(pos.getX() + speed);
        label.setLayoutX(pos.getX());
    }

    public static Bullet getInstance(int kind, int time, Pos pos, Plant holder) {
        switch (kind) {
            case COMMON:
                return new Common(time, pos, holder);
            default:
                return null;
        }
    }

    public abstract void routine();
}
