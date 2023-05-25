package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import node.bullet.Bullet;
import node.plant.Plant;
import node.zombie.Zombie;
import strategy.Strategy;
import util.Manager;
import util.Pos;

import java.util.Objects;

public class Game {
    public Pane root;
    public Strategy strategy;
    public Manager<Pos, Plant> plants;
    public Manager<Manager.Key, Zombie> zombies;
    public Manager<Manager.Key, Bullet> bullets;

    public int clock;
    public Sun sun;
    public int[] numLine = {0, 0, 0, 0, 0, 0};

    public Game(Stage primaryStage, Strategy strategy) {
        root = new Pane();
        this.strategy = strategy;
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
        primaryStage.setScene(scene);

        state = new Pane();
        state.setId("state");
        addNode(state, 0, 0);
        
        cardsInit();
        shovelInit();
        stateInit();

        primaryStage.show();

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
            Card card = new Card(this, i);
            card.setId("card");
            card.setText("C" + i);
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

    private void systemRoutine() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (clockOn(strategy.sunTick(clock), 0)) {
                int x = randomGenerator(0, 8);
                int y = randomGenerator(0, 5);

                sun.createSuns(x * 100, y * 100 + 150);
            }

            if (clockOn(strategy.zombieTick(clock), 0)) {
                int y = randomGenerator(0, 5);
                Zombie zombie = Zombie.getInstance(strategy.zombieKind(clock), clock, new Pos(950, y * 100 + 150));

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
                        if (clockOn(20, p.getTime()))
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

    private void debug(int cycle) {
        if (clockOn(cycle, 0)) {
            System.out.println("clock: " + clock);
            System.out.println("sun: " + sun.getSun());
            System.out.println("plants: " + plants.getAll().size());
            System.out.println("zombies: " + zombies.getAll().size());
            System.out.println("bullets: " + bullets.getAll().size());
            System.out.println();
        }
    }
}
