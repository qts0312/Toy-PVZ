package game;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import node.plant.Plant;
import util.Pos;

import java.util.BitSet;
import java.util.Objects;

/**
 * Sun class
 * <p>
 *     Sun system for the game.
 *     Each game will get an instance of this class to create sunshine label
 *     and maintain its volume.
 * </p>
 */
public class Sun {
    public static final int SUNVALUE = 100;

    private int sun;
    private final Game game;
    private final BitSet suns;

    public Sun(Game game) {
        this.game = game;
        this.sun = 0;
        this.suns = new BitSet();
    }

    public int getSun() {
        return sun;
    }

    public void addSuns(int sun) {
        this.sun += sun;
        if (this.sun > game.strategy.sunBound())
            this.sun = game.strategy.sunBound();
    }

    public void removeSuns(int sun) {
        this.sun -= sun;
    }

    private int index(double x, double y) {
        return (int)((x - Game.LEFT) / Pos.CELL_WIDTH) + (int)((y - Game.TOP) / Pos.CELL_HEIGHT) * 20;
    }

    public void createSuns(double x, double y) {
        if (suns.get(index(x, y)))
            return;

        Label sun = new Label();
        sun.setId("sun");

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/sun.gif")), (double) Pos.CELL_WIDTH / 2, (double) Pos.CELL_HEIGHT / 2, false, false);
        sun.setGraphic(new ImageView(image));

        game.addNode(sun, x, y);
        sun.setOnMouseEntered(event -> {
            game.removeNode(sun);
            addSuns(SUNVALUE);
            suns.set(index(x, y), false);
        });

        suns.set(index(x, y), true);
    }

    /*
    Can you afford this plant?s
     */
    public boolean afford(Plant plant) {
        return sun >= plant.getCost();
    }
}
