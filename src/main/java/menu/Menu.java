package menu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
//        root.setBackground(new Background(new BackgroundImage(
//                new Image((Objects.requireNonNull(Menu.class.getResourceAsStream("menu.png")))),
//                null, null, null, null)));

        Scene scene = new Scene(root, 1000, 750);
        // scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("menu.css")).toExternalForm());
        primaryStage.setScene(scene);

        Label label = new Label("Plants VS Zombies");

        Button start = new Button("Start");
        start.setMinWidth(120);
        start.setOnMouseClicked(event -> {
            primaryStage.setScene(null);
            new Choose(primaryStage);
        });

        Button settings = new Button("Settings");
        settings.setMinWidth(120);
        settings.setOnMouseClicked(event -> {
            // TODO: Settings
        });
        VBox buttons = new VBox(30, start, settings);
        buttons.setAlignment(Pos.CENTER);

        root.add(label, 0, 0);
        root.add(buttons, 0, 1);

        primaryStage.show();
    }
}
