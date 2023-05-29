package menu;

import database.Info;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import node.plant.*;

import java.util.ArrayList;
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
        entry = Info.getEntry("default");
    }


    public Setting(Stage primaryStage) {
        root = new Pane();
        this.stage = primaryStage;

        settingInit();

        Scene scene = new Scene(root, 1000, 750);

        stage.setScene(scene);
        stage.show();
    }

    private void addNode(Node node, double x, double y) {
        node.setLayoutX(x);
        node.setLayoutY(y);
        root.getChildren().add(node);
    }

    private Label money;

    private void settingInit() {
        inputInit();
        moneyInit();
        cardsInit();
        shopInit();
        playInit();
    }

    private void inputInit() {
        HBox hBox = new HBox();

        TextField input = new TextField();
        input.setPromptText("Input your user id");
        input.setMinWidth(500);

        Button submit = new Button("Submit");
        submit.setMinWidth(100);
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
        addNode(hBox, 0, 0);
    }

    private void moneyInit() {
        money = new Label("Money: " + entry.money);
        addNode(money, 0, 50);
    }

    private void cardsInit() {
        CheckBox peashooter = new CheckBox("Peashooter");
        peashooter.setOnAction(event -> {
            if (peashooter.isSelected()) {
                if (entry.peashooter > 0 && kinds.size() < CRADSBOUND)
                    kinds.add(Plant.PEASHOOTER);
                else
                    peashooter.setSelected(false);
            } else {
                kinds.remove((Integer) Plant.PEASHOOTER);
            }
        });

        CheckBox wallnut = new CheckBox("Wallnut");
        wallnut.setOnAction(event -> {
            if (wallnut.isSelected()) {
                if (entry.wallnut > 0 && kinds.size() < CRADSBOUND)
                    kinds.add(Plant.WALLNUT);
                else
                    wallnut.setSelected(false);
            } else {
                kinds.remove((Integer) Plant.WALLNUT);
            }
        });

        CheckBox sunflower = new CheckBox("Sunflower");
        sunflower.setOnAction(event -> {
            if (sunflower.isSelected()) {
                if (entry.sunflower > 0 && kinds.size() < CRADSBOUND)
                    kinds.add(Plant.SUNFLOWER);
                else
                    sunflower.setSelected(false);
            } else {
                kinds.remove((Integer) Plant.SUNFLOWER);
            }
        });

        CheckBox squash = new CheckBox("Squash");
        squash.setOnAction(event -> {
            if (squash.isSelected()) {
                if (entry.squash > 0 && kinds.size() < CRADSBOUND)
                    kinds.add(Plant.SQUASH);
                else
                    squash.setSelected(false);
            } else {
                kinds.remove((Integer) Plant.SQUASH);
            }
        });

        CheckBox cherrybomb = new CheckBox("Cherrybomb");
        cherrybomb.setOnAction(event -> {
            if (cherrybomb.isSelected()) {
                if (entry.cherrybomb > 0 && kinds.size() < CRADSBOUND)
                    kinds.add(Plant.CHERRYBOMB);
                else
                    cherrybomb.setSelected(false);
            } else {
                kinds.remove((Integer) Plant.CHERRYBOMB);
            }
        });

        CheckBox jalapeno = new CheckBox("Jalapeno");
        jalapeno.setOnAction(event -> {
            jalapeno.setSelected(false);
        });

        CheckBox melonpult = new CheckBox("Melonpult");
        melonpult.setOnAction(event -> {
            melonpult.setSelected(false);
        });

        addNode(peashooter, 0, 100);
        addNode(wallnut, 0, 150);
        addNode(sunflower, 0, 200);
        addNode(squash, 0, 250);
        addNode(cherrybomb, 0, 300);
        addNode(jalapeno, 0, 350);
        addNode(melonpult, 0, 400);
    }

    private void shopInit() {
        Label message = new Label("You can also buy cards in the shop");
        message.setTextFill(javafx.scene.paint.Color.GREEN);
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

        addNode(shop, 0, 500);
        addNode(buy, 0, 550);
        addNode(message, 0, 600);
    }

    private void playInit() {
        Button play = new Button("Play");
        play.setMinWidth(100);
        play.setOnMouseClicked(event -> {
            stage.setScene(null);
            while (kinds.size() < CRADSBOUND)
                kinds.add(-1);
            new Choose(stage, kinds);
        });

        addNode(play, 850, 700);
    }

    private void update() {
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
