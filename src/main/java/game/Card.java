package game;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import node.plant.Plant;
import util.Pos;

public class Card extends Label {
    public static final int CARDSNUM = 4;

    public Card(Game game, int kind) {
        super();

        this.setOnMouseClicked(event -> {
            event.consume();
            Plant plant = Plant.getInstance(kind, game.clock, null);
            Label label;
            if (plant != null && game.sun.afford(plant)) {
                label = plant.getLabel();
                label.addEventHandler(MouseEvent.ANY, new Handler(game, plant));
                game.addNode(label, event.getSceneX() - 50, event.getSceneY() - 50);
            }
        });
    }
}

class Handler implements EventHandler<MouseEvent> {
    private double X;
    private double Y;
    private final Game game;
    private final Plant plant;

    private boolean canMove;

    public Handler(Game game, Plant plant) {
        this.game = game;
        this.plant = plant;
        this.canMove = true;
    }

    @Override
    public void handle(MouseEvent event) {
        event.consume();
        if(!canMove)
            return;

        switch (event.getEventType().getName()) {
            case "MOUSE_PRESSED":
                X = event.getSceneX();
                Y = event.getSceneY();
                break;
            case "MOUSE_DRAGGED":
                plant.getLabel().setLayoutX(plant.getLabel().getLayoutX() + event.getSceneX() - X);
                plant.getLabel().setLayoutY(plant.getLabel().getLayoutY() + event.getSceneY() - Y);
                X = event.getSceneX();
                Y = event.getSceneY();
                break;
            case "MOUSE_RELEASED":
                double x = plant.getLabel().getLayoutX() + 50;
                double y = plant.getLabel().getLayoutY()  + 50;
                if (inField(x, y)) {
                    canMove = false;
                    int row = (int) ((y - 150) / Pos.CELL_HEIGHT);
                    int col = (int) (x / Pos.CELL_WIDTH);
                    plant.setPos(new Pos(col * Pos.CELL_WIDTH, row * Pos.CELL_HEIGHT + 150));
                    if (game.plants.add(plant.getPos(), plant)) {
                        plant.getLabel().setLayoutX(col * Pos.CELL_WIDTH);
                        plant.getLabel().setLayoutY(row * Pos.CELL_HEIGHT + 150);
                        game.sun.removeSuns(plant.getCost());
                        return;
                    }
                }
                game.removeNode(plant.getLabel());
                break;
        }
    }

    private boolean inField(double x, double y) {
        return x >= 0 && x <= 1000 && y >= 150 && y <= 750;
    }
}
