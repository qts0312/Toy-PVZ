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
import java.util.Objects;

public class Choose {
    public static final int NUM = 3;
    public static int ALLOW;

    static {
        ALLOW = 0;
    }

    public Choose(Stage primaryStage, ArrayList<Integer> cards) {
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(80);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("setting.css")).toExternalForm());
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
