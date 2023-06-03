package game;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import node.plant.Plant;
import util.Pos;

/**
 * Shovel class
 * <p>
 *     Use shovel to remove an existing plant in the field.
 *     Its design motivation is the same as Card.
 * </p>
 */
public class Shovel extends Label {
    public Shovel(Game game) {
        super();

        this.addEventHandler(MouseEvent.ANY, new ShovelHandler(game, this));
    }
}

/**
 * ShovelHandler class
 * <p>
 *     Special event handler for Shovel.
 * </p>
 */
class ShovelHandler implements EventHandler<MouseEvent> {
    private double X;
    private double Y;
    Node node;
    private final Game game;

    public ShovelHandler(Game game, Node node) {
        this.game = game;
        this.node = node;
    }

    @Override
    public void handle(MouseEvent event) {
        event.consume();

        switch (event.getEventType().getName()) {
            case "MOUSE_PRESSED":
                X = event.getSceneX();
                Y = event.getSceneY();
                break;
            case "MOUSE_DRAGGED":
                node.setTranslateX(event.getSceneX() - X);
                node.setTranslateY(event.getSceneY() - Y);
                break;
            case "MOUSE_RELEASED":
                double x = node.getLayoutX() + node.getTranslateX() + (double) Pos.CELL_WIDTH / 2;
                double y = node.getLayoutY() + node.getTranslateY() + (double) Pos.CELL_HEIGHT / 2;
                Pos pos = new Pos((int)(x - (x - 250) % Pos.CELL_WIDTH), (int)(y - (y - 85) % Pos.CELL_HEIGHT));
                Plant plant = game.plants.get(p -> pos.overlap(p.getPos()));
                if (plant != null) {
                    game.removeNode(plant.getLabel());
                    game.plants.remove(plant.getPos());
                    game.sun.addSuns(plant.getCost() / 2);
                }
                node.setTranslateX(0);
                node.setTranslateY(0);
                break;
        }
    }
}
