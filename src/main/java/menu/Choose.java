package menu;

import game.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import strategy.Strategy;

import java.util.ArrayList;

public class Choose {
    public static final int NUM = 2;
    public static int ALLOW;

    static {
        ALLOW = 0;
    }

    public Choose(Stage primaryStage, ArrayList<Integer> cards) {
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1000, 750);
        primaryStage.setScene(scene);

        for (int i = 0; i < NUM; ++ i) {
            Button button = new Button("Choose" + i);
            button.setMinWidth(120);
            Integer integer = i;
            button.setOnMouseClicked(event -> {
                if (integer <= ALLOW) {
                    primaryStage.setScene(null);
                    new Game(primaryStage, Strategy.getInstance(integer), cards);
                }
            });
            root.add(button, 0, i);
        }

        primaryStage.show();
    }
}
