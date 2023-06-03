package menu;

import database.Info;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import node.plant.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Setting {
    public static final int CRADSBOUND = 5;

    public Pane root;
    private final Stage stage;
    private ArrayList<Integer> kinds = new ArrayList<>();

    /*
     * Just a table of user information.
     */
    public static Info.Entry entry = null;

    static {
        // entry = Info.getEntry("default");
        entry = new Info.Entry(
                "default",
                100,
                10000,
                1,
                1,
                1,
                1,
                1,
                1,
                1
        );
    }


    public Setting(Stage primaryStage) {
        root = new Pane();
        this.stage = primaryStage;

        settingInit();
        update();

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("setting.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    private void addNode(Node node, double x, double y) {
        node.setLayoutX(x);
        node.setLayoutY(y);
        root.getChildren().add(node);
    }

    private static Label money;

    private void settingInit() {
        // inputInit();
        moneyInit();
        cardsInit();
        shopInit();
        playInit();
    }

    private void inputInit() {
        HBox hBox = new HBox();

        TextField input = new TextField();
        input.setPromptText("Input your user id");

        Button submit = new Button("Submit");
        submit.setOnMouseClicked(event -> {
            String userId = input.getText();
            entry = Info.getEntry(userId);
            if (entry == null) {
                entry = new Info.Entry(userId);
                Info.createEntry(entry);
            }
            update();
        });

        hBox.getChildren().addAll(input, submit);
        addNode(hBox, 72, 23);
    }

    private void moneyInit() {
        money = new Label("Money: " + entry.money);
        money.setId("money");
        addNode(money, 450 , 95);
    }

    private void cardsInit() {
        CheckBox peashooterBox = new CheckBox();
        peashooterBox.setOnAction(event -> {
            if (peashooterBox.isSelected()) {
                if (entry.peashooter > 0 && kinds.size() < CRADSBOUND)
                    kinds.add(Plant.PEASHOOTER);
                else
                    peashooterBox.setSelected(false);
            } else {
                kinds.remove((Integer) Plant.PEASHOOTER);
            }
        });
        Label peashooterLabel = new Label();
        Image peashooterImage = new Image(Objects.requireNonNull(Setting.class.getResourceAsStream("images/PeaShooter.png")));
        peashooterLabel.setGraphic(new ImageView(peashooterImage));
        peashooterLabel.setId("card");

        CheckBox wallnutBox = new CheckBox();
        wallnutBox.setOnAction(event -> {
            if (wallnutBox.isSelected()) {
                if (entry.wallnut > 0 && kinds.size() < CRADSBOUND)
                    kinds.add(Plant.WALLNUT);
                else
                    wallnutBox.setSelected(false);
            } else {
                kinds.remove((Integer) Plant.WALLNUT);
            }
        });
        Label wallnutLabel = new Label();
        Image wallnutImage = new Image(Objects.requireNonNull(Setting.class.getResourceAsStream("images/WallNut.png")));
        wallnutLabel.setGraphic(new ImageView(wallnutImage));
        wallnutLabel.setId("card");

        CheckBox sunflowerBox = new CheckBox();
        sunflowerBox.setOnAction(event -> {
            if (sunflowerBox.isSelected()) {
                if (entry.sunflower > 0 && kinds.size() < CRADSBOUND)
                    kinds.add(Plant.SUNFLOWER);
                else
                    sunflowerBox.setSelected(false);
            } else {
                kinds.remove((Integer) Plant.SUNFLOWER);
            }
        });
        Label sunflowerLabel = new Label();
        Image sunflowerImage = new Image(Objects.requireNonNull(Setting.class.getResourceAsStream("images/SunFlower.png")));
        sunflowerLabel.setGraphic(new ImageView(sunflowerImage));
        sunflowerLabel.setId("card");

        CheckBox squashBox = new CheckBox();
        squashBox.setOnAction(event -> {
            if (squashBox.isSelected()) {
                if (entry.squash > 0 && kinds.size() < CRADSBOUND)
                    kinds.add(Plant.SQUASH);
                else
                    squashBox.setSelected(false);
            } else {
                kinds.remove((Integer) Plant.SQUASH);
            }
        });
        Label squashLabel = new Label();
        Image squashImage = new Image(Objects.requireNonNull(Setting.class.getResourceAsStream("images/Squash.png")));
        squashLabel.setGraphic(new ImageView(squashImage));
        squashLabel.setId("card");

        CheckBox cherrybombBox = new CheckBox();
        cherrybombBox.setOnAction(event -> {
            if (cherrybombBox.isSelected()) {
                if (entry.cherrybomb > 0 && kinds.size() < CRADSBOUND)
                    kinds.add(Plant.CHERRYBOMB);
                else
                    cherrybombBox.setSelected(false);
            } else {
                kinds.remove((Integer) Plant.CHERRYBOMB);
            }
        });
        Label cherrybombLabel = new Label();
        Image cherrybombImage = new Image(Objects.requireNonNull(Setting.class.getResourceAsStream("images/CherryBomb.png")));
        cherrybombLabel.setGraphic(new ImageView(cherrybombImage));
        cherrybombLabel.setId("card");

        CheckBox jalapenoBox = new CheckBox();
        jalapenoBox.setOnAction(event -> {
            jalapenoBox.setSelected(false);
        });
        Label jalapenoLabel = new Label();
        Image jalapenoImage = new Image(Objects.requireNonNull(Setting.class.getResourceAsStream("images/Jalapeno.png")));
        jalapenoLabel.setGraphic(new ImageView(jalapenoImage));
        jalapenoLabel.setId("card");

        addNode(peashooterBox, 50, 95);
        addNode(peashooterLabel, 80, 95);
        addNode(wallnutBox, 50, 173);
        addNode(wallnutLabel, 80, 173);
        addNode(sunflowerBox, 50, 251);
        addNode(sunflowerLabel, 80, 251);
        addNode(squashBox, 50, 329);
        addNode(squashLabel, 80, 329);
        addNode(cherrybombBox, 50, 407);
        addNode(cherrybombLabel, 80, 407);
        addNode(jalapenoBox, 50, 485);
        addNode(jalapenoLabel, 80, 485);
    }

    private void shopInit() {
        Label message = new Label("You can also buy cards in the shop");
        message.setTextFill(javafx.scene.paint.Color.GREEN);
        message.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        AtomicBoolean needBuy = new AtomicBoolean(false);
        AtomicInteger cost = new AtomicInteger();

        ChoiceBox<String> shop = new ChoiceBox<>(FXCollections.observableArrayList(
                "Peashooter $0",
                "Wallnut $50",
                "Sunflower $100",
                "Squash $200",
                "Cherrybomb $300",
                "Jalapeno $?",
                "Melonpult $?"
        ));
        shop.setOnAction(event -> {
            switch (shop.getValue()) {
                case "Peashooter $0":
                    needBuy.set(false);
                    break;
                case "Wallnut $50":
                    needBuy.set(entry.wallnut == 0);
                    cost.set(50);
                    break;
                case "Sunflower $100":
                    needBuy.set(entry.sunflower == 0);
                    cost.set(100);
                    break;
                case "Squash $200":
                    needBuy.set(entry.squash == 0);
                    cost.set(200);
                    break;
                case "Cherrybomb $300":
                    needBuy.set(entry.cherrybomb == 0);
                    cost.set(300);
                    break;
                default:
                    needBuy.set(true);
                    cost.set(Integer.MAX_VALUE);
                    break;
            }
        });

        Button buy = new Button("Buy");
        buy.setOnMouseClicked(event -> {
            if (needBuy.get()) {
                if (entry.money >= cost.get()) {
                    entry.money -= cost.get();
                    switch (shop.getValue()) {
                        case "Wallnut $50":
                            entry.wallnut = 1;
                            break;
                        case "Sunflower $100":
                            entry.sunflower = 1;
                        case "Squash $200":
                            entry.squash = 1;
                            break;
                        case "Cherrybomb $300":
                            entry.cherrybomb = 1;
                            break;
                        default:
                            break;
                    }
                    update();
                    Info.updateEntry(entry);

                    message.setText("You buy this card successfully");
                    message.setTextFill(javafx.scene.paint.Color.GREEN);
                } else {
                    message.setText("You don't have enough money");
                    message.setTextFill(javafx.scene.paint.Color.RED);
                }
            } else {
                message.setText("You already have this card");
                message.setTextFill(javafx.scene.paint.Color.RED);
            }
        });

        addNode(shop, 500, 200);
        addNode(buy, 650, 200);
        addNode(message, 450, 300);
    }

    private void playInit() {
        Button play = new Button("Play");
        play.setMinWidth(100);
        play.setOnMouseClicked(event -> {
            stage.setScene(null);
            new Choose(stage, kinds);
        });

        addNode(play, 650, 500);
    }

    private static void update() {
        money.setText("Money: " + entry.money);
        Choose.ALLOW = entry.level;
    }

    public static ArrayList<Integer> UCards() {
        ArrayList<Integer> cards = new ArrayList<>();
        cards.add(Plant.PEASHOOTER);
        cards.add(Plant.WALLNUT);
        cards.add(Plant.SUNFLOWER);
        cards.add(-1);
        cards.add(-1);
        return cards;
    }

    private void debug() {
        for (int i: kinds)
            System.out.println(i);
    }
}
