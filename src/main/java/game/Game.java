package game;

import database.Info;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import menu.Choose;
import menu.Setting;
import node.bullet.Bullet;
import node.plant.Plant;
import node.zombie.Common;
import node.zombie.Jump;
import node.zombie.Strong;
import node.zombie.Zombie;
import strategy.Strategy;
import util.Manager;
import util.Pos;

import java.util.ArrayList;
import java.util.Objects;

public class Game {
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

        Scene scene = new Scene(root, 1100, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("game.css")).toExternalForm());
        stage.setScene(scene);

//        state = new Pane();
//        state.setId("state");
//        addNode(state, 0, 0);
        
        cardsInit();
        shovelInit();
        sunIndicatorInit();

        stage.show();

        systemRoutine();
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
        for (int i = 0; i < cards.size(); ++ i) {
            int kind = cards.get(i);
            Card card = new Card(this, kind);
            card.setId("card");

            if (kind >= 0 && kind <= 4) {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/" + Plant.forName(kind) + ".png")), 80, 48, false, false);
                card.setGraphic(new javafx.scene.image.ImageView(image));
            }

            addNode(card, 0, i * 80);
        }
    }

    private void shovelInit() {
        Shovel shovel = new Shovel(this);
        shovel.setId("shovel");

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/Shovel.png")), 80, 40, false, false);
        shovel.setGraphic(new javafx.scene.image.ImageView(image));

        addNode(shovel, 0, 80 * cards.size());
    }

    private ProgressBar sunIndicator;
    private Label sunCounter;

    private void sunIndicatorInit() {
        sunIndicator = new ProgressBar();
        sunIndicator.setPrefSize(200, 20);
        sunIndicator.setProgress(0);
        addNode(sunIndicator, 800, 30);

        sunCounter = new Label();
        sunCounter.setText("0");
        sunCounter.setId("sunCounter");
        addNode(sunCounter, 1000, 30);
    }

    private Timeline timeline;
    public static final int LEFT = 250;
    public static final int TOP = 85;
    public static final int COLUMNS = 9;
    public static final int ROWS = 5;

    private void systemRoutine() {
        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            winOrLose();
            if (clockOn(strategy.sunTick(clock), 0)) {
                int x = randomGenerator(0, COLUMNS - 1);
                int y = randomGenerator(0, ROWS - 1);

                sun.createSuns(x * Pos.CELL_WIDTH + LEFT, y * Pos.CELL_HEIGHT + TOP);
            }

            if (clockOn(strategy.zombieTick(clock), 0)) {
                int y = randomGenerator(0, 4);
                int kind = strategy.zombieKind(clock);
                Zombie zombie = Zombie.getInstance(kind, clock, new Pos(LEFT + Pos.CELL_WIDTH * COLUMNS, y * Pos.CELL_HEIGHT + TOP));

                if (zombie != null) {
                    numLine[y] ++;
                    zombies.add(new Manager.Key(clock), zombie);
                    switch (kind) {
                        case Zombie.COMMON:
                            addNode(zombie.getLabel(), Common.transX(zombie.getPos()), Common.transY(zombie.getPos()));
                            break;
                        case Zombie.JUMP:
                            addNode(zombie.getLabel(), Jump.transX(zombie.getPos()), Jump.transY(zombie.getPos()));
                            break;
                        case Zombie.STRONG:
                            addNode(zombie.getLabel(), Strong.transX(zombie.getPos()), Strong.transY(zombie.getPos()));
                    }
                }
            }

            for (Zombie z: zombies.getAll()) {
                if (clockOn(1, 0)) {
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
                        if (clockOn(50, p.getTime()))
                            p.routine(clock);
                        break;
                    case Plant.SQUASH:
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
                    addNode(b.getLabel(), b.getPos().getX() + (double) Pos.CELL_WIDTH / 2, b.getPos().getY() + (double) Pos.CELL_HEIGHT / 10);
                    b.setShown();
                } else {
                    b.move();
                    b.routine();
                }
            }

            clock ++;
            sunIndicator.setProgress((double) sun.getSun() / strategy.sunBound());
            sunCounter.setText(String.valueOf(sun.getSun()));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void winOrLose() {
        if (clock >= strategy.totalTime() && zombies.getAll().size() == 0) {
            winHandle();
            return;
        }

        ArrayList<Zombie> zombieList = this.zombies.getAll();
        for (Zombie z: zombieList) {
            if (z.getPos().getX() <= 250) {
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

        VBox win = new VBox();
        win.setSpacing(30);
        win.setAlignment(javafx.geometry.Pos.CENTER);

        Label label = new Label("You Win!");
        label.setAlignment(javafx.geometry.Pos.CENTER);

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

        win.getChildren().addAll(label, ret, next);

        Scene scene = new Scene(win, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(Game.class.getResource("win.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    private void loseHandle() {
        timeline.stop();

        VBox lose = new VBox();
        lose.setSpacing(30);
        lose.setAlignment(javafx.geometry.Pos.CENTER);

        Label label = new Label("You Lose!");
        label.setAlignment(javafx.geometry.Pos.CENTER);

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

        lose.getChildren().addAll(label, ret, replay);

        Scene scene = new Scene(lose, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(Game.class.getResource("win.css")).toExternalForm());

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
