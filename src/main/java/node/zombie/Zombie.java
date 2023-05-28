package node.zombie;

import game.Game;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import util.Pos;

import java.util.Objects;

/**
 * Zombie class
 * This class is used to record the parameters of a zombie.
 * And controls the zombie's life, position, and display.
 */
public abstract class Zombie {
    public static final int COMMON = 0;
    public static final int JUMP = 1;

    private static Game game;

    public static void setGame(Game game) {
        Zombie.game = game;
    }

    public static Game getGame() {
        return game;
    }

    private final int kind;
    private final int speed;
    private final int damage;
    private final int time;

    private int life;
    private boolean canMove;
    private Label label;
    private Tooltip tooltip;
    private Pos pos;

    public Zombie(int kind,
                  int life,
                  int speed,
                  int damage,
                  int time,
                  String Url,
                  Pos pos) {
        this.kind = kind;
        this.life = life;
        this.speed = speed;
        this.damage = damage;
        this.time = time;
        this.canMove = true;
        this.pos = pos;

        this.label = new Label();
//        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Url)), 100, 100, false, false);
//        label.setGraphic(new ImageView(image));
        label.setText(forName(kind));
        label.setId("zombie");

        tooltip = new Tooltip();
        updateTooltip();
        tooltip.setShowDelay(Duration.millis(100));
        label.setTooltip(tooltip);
    }

    public int getKind() {
        return kind;
    }

    public int getLife() {
        return life;
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

    public boolean getCanMove() {
        return canMove;
    }

    public int getLine() {
        return (pos.getY() - 150) / 100;
    }

    public Label getLabel() {
        return label;
    }

    public Pos getPos() {
        return pos;
    }

    public void setLife(int delta) {
        this.life += delta;
        updateTooltip();
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isDead() {
        return life <= 0;
    }

    public void updateTooltip() {
        tooltip.setText(forName(kind) + "\n" +
                "Life: " + life + "\n" +
                "Speed: " + speed + "\n" +
                "Damage: " + damage + "\n");
    }

    public void move() {
        if (canMove) {
            pos.setX(pos.getX() - speed);
            label.setLayoutX(pos.getX());
        }
    }

    public static Zombie getInstance(int kind, int time, Pos pos) {
        switch (kind) {
            case COMMON:
                return new Common(time, pos);
            case JUMP:
                return new Jump(time, pos);
            default:
                return null;
        }
    }

    public static String forName(int kind) {
        switch (kind) {
            case COMMON:
                return "CommonZombie ";
            case JUMP:
                return "JumpZombie";
            default:
                return null;
        }
    }

    public abstract void routine();
}
