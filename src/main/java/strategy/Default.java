package strategy;

public class Default extends Strategy {
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
        return time > 600 ? 50 : 80;
    }

    @Override
    public int zombieKind(int time) {
        return 0;
    }
}
