package menu;

import game.Game;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import strategy.Strategy;

import java.util.Objects;

public class Menu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Menu");

        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(80);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("menu.css")).toExternalForm());
        primaryStage.setScene(scene);

        Button start = new Button("Start");
        start.setOnMouseClicked(event -> {
            primaryStage.setScene(null);
            new Setting(primaryStage);
        });

        Button visitor = new Button("Visitor");
        visitor.setOnMouseClicked(event -> {
            primaryStage.setScene(null);
            new Game(primaryStage, Strategy.getInstance(0), Setting.UCards());
        });

        VBox buttons = new VBox(30, start, visitor);
        buttons.setAlignment(Pos.CENTER);

        root.add(buttons, 0, 1);

        primaryStage.show();
    }
}
