package game;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import node.plant.Plant;
import util.Pos;

public class Shovel extends Label {
    public Shovel(Game game) {
        super();

        this.addEventHandler(MouseEvent.ANY, new ShovelHandler(game, this));
    }
}

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
                double x = node.getLayoutX() + node.getTranslateX() + 50;
                double y = node.getLayoutY() + node.getTranslateY() + 50;
                Pos pos = new Pos((int)(x - x % 100), (int)(y - (y - 150) % 100));
                Plant plant = game.plants.get(p -> pos.overlap(p.getPos()));
                if (plant != null) {
                    game.removeNode(plant.getLabel());
                    game.plants.remove(plant.getPos());
                    game.sun.addSuns(plant.getCost());
                }
                node.setTranslateX(0);
                node.setTranslateY(0);
                break;
        }
    }
}
