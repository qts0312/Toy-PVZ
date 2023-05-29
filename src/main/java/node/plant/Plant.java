package node.plant;

import game.Game;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import util.Pos;

import java.util.Objects;

/**
 * Plant class
 * This class is used to record the parameters of a plant.
 * And controls the plant's life, position, and display.
 */
public abstract class Plant {
    public static final int PEASHOOTER = 0;
    public static final int WALLNUT = 1;
    public static final int SUNFLOWER = 2;
    public static final int SQUASH = 3;
    public static final int CHERRYBOMB = 4;

    private static Game game;

    public static void setGame(Game game) {
        Plant.game = game;
    }

    public static Game getGame() {
        return game;
    }

    /**
     * Attack class
     * This class is used to record the attack parameters of a plant.
     */
    public static class Attack {
        private final int damage;
        private final int speed;
        private final int range;

        public Attack(int damage, int speed, int range) {
            this.damage = damage;
            this.speed = speed;
            this.range = range;
        }

        public int getDamage() {
            return damage;
        }

        public int getSpeed() {
            return speed;
        }

        public int getRange() {
            return range;
        }
    }

    private final int kind;
    private final int cost;
    private final Attack attack;

    private int time;
    private int life;
    private Label label;
    private Tooltip tooltip;
    private Pos pos;

    public Plant (int kind,
                  int life,
                  int cost,
                  Attack attack,
                  int time,
                  String Url,
                  Pos pos) {
        this.kind = kind;
        this.life = life;
        this.cost = cost;
        this.attack = attack;
        this.time = time;
        this.pos = pos;

        label = new Label();
//        Image image = new Image(Objects.requireNonNull(Plant.class.getResourceAsStream(Url)), 100, 100, false, false);
//        label.setGraphic(new ImageView(image));
        label.setText(forName(kind));
        label.setId("plant");

        tooltip = new Tooltip();
        updateTooltip();
        tooltip.setShowDelay(Duration.millis(500));
    }

    public int getKind() {
        return kind;
    }

    public int getLife() {
        return life;
    }

    public int getCost() {
        return cost;
    }

    public Attack getAttack() {
        return attack;
    }

    public int getTime() {
        return time;
    }

    public int getLine() {
        return (pos.getY() - 150) / 100;
    }

    public Label getLabel() {
        return label;
    }

    public Pos getPos() {
        label.setTooltip(tooltip);
        return pos;
    }

    public void setLife(int delta) {
        life += delta;
        updateTooltip();
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public boolean isDead() {
        return life <= 0;
    }

    private void updateTooltip() {
        tooltip.setText(forName(kind) + "\n" +
                "Life: " + life + "\n" +
                "Cost: " + cost + "\n" +
                "Attack: " + attack.getDamage() + "\n" +
                "Speed: " + attack.getSpeed() + "\n" +
                "Range: " + attack.getRange() + "\n");
    }

    public static Plant getInstance(int kind, int time, Pos pos) {
        switch (kind) {
            case Plant.PEASHOOTER:
                return new PeaShooter(time, pos);
            case Plant.WALLNUT:
                return new WallNut(time, pos);
            case Plant.SUNFLOWER:
                return new SunFlower(time, pos);
            case Plant.SQUASH:
                return new Squash(time, pos);
            case Plant.CHERRYBOMB:
                return new CherryBomb(time, pos);
        }
        return null;
    }

    public static String forName(int kind) {
        switch (kind) {
            case Plant.PEASHOOTER:
                return "PeaShooter";
            case Plant.WALLNUT:
                return "WallNut";
            case Plant.SUNFLOWER:
                return "SunFlower";
            case Plant.SQUASH:
                return "Squash";
            case Plant.CHERRYBOMB:
                return "CherryBomb";
        }
        return null;
    }

    public abstract void routine(int time);
}
