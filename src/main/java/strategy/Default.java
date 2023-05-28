package strategy;

public class Default extends Strategy {

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
        return 0;
    }

    @Override
    public int totalTime() {
        return 100;
    }
}
