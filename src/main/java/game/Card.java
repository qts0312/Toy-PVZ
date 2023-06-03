package game;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import node.plant.Plant;
import util.Pos;

/**
 * Card class
 * <p>
 *     Label for player to choose and create a new plant into field.
 *     To provide a uniform abstraction, we implement Card as a subclass of javafx.scene.control.Label.
 * </p>
 */
public class Card extends Label {

    /**
     * Card constructor.
     * @param game pointer to the game
     * @param kind kind of the plant this card representing
     */
    public Card(Game game, int kind) {
        super();

        this.setOnMouseClicked(event -> {
            event.consume();
            Plant plant = Plant.getInstance(kind, game.clock, null);
            Label label;

            // Install special event handler
            if (plant != null && game.sun.afford(plant)) {
                label = plant.getLabel();
                label.addEventHandler(MouseEvent.ANY, new Handler(game, plant));
                game.addNode(label, event.getSceneX() - (double) Pos.CELL_WIDTH / 2, event.getSceneY() - (double) Pos.CELL_HEIGHT / 2);
            }
        });
    }
}

/**
 * Handler class
 * <p>
 *     Special event handler for Card.
 * </p>
 */
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
                double x = plant.getLabel().getLayoutX() + (double) Pos.CELL_WIDTH / 2;
                double y = plant.getLabel().getLayoutY()  + (double) Pos.CELL_HEIGHT / 2;
                if (inField(x, y)) {
                    canMove = false;
                    int row = (int) ((y - 85) / Pos.CELL_HEIGHT);
                    int col = (int) ((x - 250) / Pos.CELL_WIDTH);
                    plant.setPos(new Pos(col * Pos.CELL_WIDTH + 250, row * Pos.CELL_HEIGHT + 85));
                    if (game.plants.add(plant.getPos(), plant)) {
                        plant.getLabel().setLayoutX(plant.getPos().getX());
                        plant.getLabel().setLayoutY(plant.getPos().getY());
                        game.sun.removeSuns(plant.getCost());
                        return;
                    }
                }
                game.removeNode(plant.getLabel());
                break;
        }
    }

    private boolean inField(double x, double y) {
        return x >= 250 && x <= 250 + 9 * Pos.CELL_WIDTH && y >= 85 && y <= 85 + 5 * Pos.CELL_HEIGHT;
    }
}
