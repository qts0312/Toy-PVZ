package game;

import javafx.scene.control.Label;
import node.plant.Plant;

import java.util.BitSet;

public class Sun {
    public static final int SUNVALUE = 100;

    private int sun;
    private final Game game;
    private BitSet suns;

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
        return (int)(x / 100) + (int)((y - 150) / 100) * 20;
    }

    public void createSuns(double x, double y) {
        if (suns.get(index(x, y)))
            return;

        Label sun = new Label();
        sun.setId("sun");
        sun.setFocusTraversable(true);
//        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/sun.png")), 50, 50, false, false);
//        sun.setGraphic(new ImageView(image));

        game.addNode(sun, x, y);
        sun.setOnMouseClicked(event -> {
            game.removeNode(sun);
            addSuns(SUNVALUE);
            suns.set(index(x, y), false);
        });

        suns.set(index(x, y), true);
    }

    public boolean afford(Plant plant) {
        return sun >= plant.getCost();
    }
}
