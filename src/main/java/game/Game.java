package game;

import database.Info;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import menu.Choose;
import menu.Menu;
import menu.Setting;
import node.bullet.Bullet;
import node.plant.Plant;
import node.zombie.Zombie;
import strategy.Default;
import strategy.Strategy;
import util.Manager;
import util.Pos;

import java.util.ArrayList;
import java.util.Objects;

public class Game {
    public static final int BONUS = 50;

    private final Stage stage;
    public Pane root;
    public Strategy strategy;
    public Manager<Pos, Plant> plants;
    public Manager<Manager.Key, Zombie> zombies;
    public Manager<Manager.Key, Bullet> bullets;

    public int clock;
    public Sun sun;
    public int[] numLine = {0, 0, 0, 0, 0, 0};
    private final ArrayList<Integer> cards;

    public Game(Stage primaryStage, Strategy strategy, ArrayList<Integer> cards) {
        root = new Pane();
        this.strategy = strategy;
        this.stage = primaryStage;
        this.cards = cards;
        plants = new Manager<>(true);
        zombies = new Manager<>(false);
        bullets = new Manager<>(false);
        Plant.setGame(this);
        Zombie.setGame(this);
        Bullet.setGame(this);

        clock = 0;
        sun = new Sun(this);

        Scene scene = new Scene(root, 1000, 750);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("game.css")).toExternalForm());
        stage.setScene(scene);

        state = new Pane();
        state.setId("state");
        addNode(state, 0, 0);
        
        cardsInit();
        shovelInit();
        stateInit();

        stage.show();

        systemRoutine();
    }

    private void newNode(Node node, double x, double y, Pane pane) {
        node.setLayoutX(x);
        node.setLayoutY(y);
        pane.getChildren().add(node);
    }

    public void addNode(Node node, double x, double y) {
        node.setLayoutX(x);
        node.setLayoutY(y);
        root.getChildren().add(node);
    }

    public void removeNode(Node node) {
        root.getChildren().remove(node);
    }

    private int randomGenerator(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    private boolean clockOn(int clock, int delta) {
        return (this.clock - delta) % clock == 0;
    }

    private void cardsInit() {
        for (int i = 0; i < Card.CARDSNUM; ++ i) {
            int kind = cards.get(i);
            Card card = new Card(this, kind);
            card.setId("card");
            card.setText("C" + kind);
            newNode(card, 100 * i, 0, state);
        }
    }

    private void shovelInit() {
        Shovel shovel = new Shovel(this);
        shovel.setId("shovel");
        newNode(shovel, 100 * Card.CARDSNUM, 0, state);
    }

    Pane state;
    ProgressBar sunBar;
    Label sunIndicator;
    Label plantIndicator;
    Label zombieIndicator;

    private void stateInit() {
        sunBar = new ProgressBar(0);
        sunBar.setId("progressBar");
        sunBar.setPrefWidth(300);
        newNode(sunBar, 600, 0, state);

        sunIndicator = new Label("0");
        sunIndicator.setPrefWidth(50);
        newNode(sunIndicator, 920, 0, state);

        plantIndicator = new Label("Plant: 0");
        plantIndicator.setPrefWidth(100);
        newNode(plantIndicator, 600, 50, state);

        zombieIndicator = new Label("Zombie: 0");
        zombieIndicator.setPrefWidth(100);
        newNode(zombieIndicator, 600, 100, state);
    }

    private Timeline timeline;

    private void systemRoutine() {
        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            winOrLose();
            if (clockOn(strategy.sunTick(clock), 0)) {
                int x = randomGenerator(0, 8);
                int y = randomGenerator(0, 5);

                sun.createSuns(x * 100, y * 100 + 150);
            }

            if (clockOn(strategy.zombieTick(clock), 0)) {
                int y = randomGenerator(0, 5);
                int kind = strategy.zombieKind(clock);
                Zombie zombie = Zombie.getInstance(kind, clock, new Pos(950, y * 100 + 150));

                if (zombie != null) {
                    numLine[y] ++;
                    zombies.add(new Manager.Key(clock), zombie);
                    addNode(zombie.getLabel(), zombie.getPos().getX(), zombie.getPos().getY());
                }
            }

            for (Zombie z: zombies.getAll()) {
                if (clockOn(10, 0)) {
                    z.move();
                    z.routine();
                }
            }

            for (Plant p: plants.getAll()) {
                switch (p.getKind()) {
                    case Plant.PEASHOOTER:
                        if (clockOn(10, p.getTime()))
                            p.routine(clock);
                        break;
                    case Plant.SUNFLOWER:
                        if (clockOn(30, p.getTime()))
                            p.routine(clock);
                        break;
                    case Plant.SQUASH:
                        if (clockOn(10, 0)) // this time routine follow zombie's step
                            p.routine(clock);
                        break;
                    case Plant.CHERRYBOMB:
                        if (clockOn(25, 0))
                            p.routine(clock);
                        break;
                    default:
                        break;
                }
            }

            for (Bullet b: bullets.getAll()) {
                if (!b.isShown()) {
                    addNode(b.getLabel(), b.getPos().getX(), b.getPos().getY());
                    b.setShown();
                } else {
                    b.move();
                    b.routine();
                }
            }

            clock ++;
            sunBar.setProgress(sun.getSun() / 500.0);
            sunIndicator.setText(String.valueOf(sun.getSun()));
            plantIndicator.setText("Plant: " + plants.getAll().size());
            zombieIndicator.setText("Zombie: " + zombies.getAll().size());
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void winOrLose() {
        if (clock >= strategy.totalTime()) {
            winHandle();
            return;
        }

        ArrayList<Zombie> zombieList = this.zombies.getAll();
        for (Zombie z: zombieList) {
            if (z.getPos().getX() <= 0) {
                loseHandle();
                return;
            }
        }
    }

    private void winHandle() {
        timeline.stop();
        Choose.ALLOW = Math.max(Choose.ALLOW, strategy.getLevel() + 1);
        Setting.entry.level += Choose.ALLOW;
        Setting.entry.money += strategy.award();
        Info.updateEntry(Setting.entry);

        GridPane win = new GridPane();
        win.setHgap(10);
        win.setVgap(10);
        win.setAlignment(javafx.geometry.Pos.CENTER);
        win.setPadding(new Insets(25));

        Label label = new Label("You Win!");
        label.setId("win");

        Button ret = new Button("Return");
        ret.setId("ret");
        ret.setOnAction(e -> {
            stage.setScene(null);
            new Setting(stage);
        });

        Button next = new Button("Next");
        next.setId("next");
        next.setOnAction(e -> {
            stage.setScene(null);
            new Game(stage, Strategy.getInstance(strategy.getLevel() + 1), cards);
        });

        win.add(label, 0, 0);
        win.add(ret, 0, 1);
        win.add(next, 0, 2);

        Scene scene = new Scene(win, 1000, 2750);
        // scene.getStylesheets().add("win.css");

        stage.setScene(scene);
        stage.show();
    }

    private void loseHandle() {
        timeline.stop();

        GridPane lose = new GridPane();
        lose.setHgap(10);
        lose.setVgap(10);
        lose.setAlignment(javafx.geometry.Pos.CENTER);
        lose.setPadding(new Insets(25));

        Label label = new Label("You Lose!");
        label.setId("lose");

        Button ret = new Button("Return");
        ret.setId("ret");
        ret.setOnAction(e -> {
            stage.setScene(null);
            new Setting(stage);
        });

        Button replay = new Button("Replay");
        replay.setId("replay");
        replay.setOnAction(e -> {
            stage.setScene(null);
            new Game(stage, strategy, cards);
        });

        lose.add(label, 0, 0);
        lose.add(ret, 0, 1);
        lose.add(replay, 0, 2);

        Scene scene = new Scene(lose, 1000, 750);
        // scene.getStylesheets().add("lose.css");

        stage.setScene(scene);
        stage.show();
    }

    private void debug(int cycle) {
        if (clockOn(cycle, 0)){
            System.out.println("clock: " + clock);
            System.out.println("sun: " + sun.getSun());
            System.out.println("plants: " + plants.getAll().size());
            System.out.println("zombies: " + zombies.getAll().size());
            System.out.println("bullets: " + bullets.getAll().size());
        }
    }
}
