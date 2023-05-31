package strategy;

public class Default extends Strategy {
    private int zombieNum = 18;

    public Default() {
        super(0);
    }

    @Override
    public int sunTick(int time) {
        return 50;
    }

    @Override
    public int sunBound() {
        return 500;
    }

    @Override
    public int zombieTick(int time) {
        return time > 150 ? 50 : 80;
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
        return 50;
    }
}
