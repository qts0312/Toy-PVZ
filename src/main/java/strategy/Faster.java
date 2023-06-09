package strategy;

public class Faster extends Strategy {
    private int zombieNum = 33;

    public Faster() {
        super(1);
    }

    @Override
    public int sunTick(int time) {
        return 80;
    }

    @Override
    public int sunBound() {
        return 500;
    }

    @Override
    public int zombieTick(int time) {
        return time > 600 ? 30 : 50;
    }

    @Override
    public int zombieKind(int time) {
        return zombieNum -- > 0 ? 0 : -1;
    }

    @Override
    public int totalTime() {
        return 1200;
    }

    @Override
    public int award() {
        return 100;
    }
}
