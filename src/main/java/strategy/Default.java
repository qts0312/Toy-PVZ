package strategy;

public class Default extends Strategy {
    private int zombieNum = 17;

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
        return time > 300 ? 30 : 50;
    }

    @Override
    public int zombieKind(int time) {
        if (zombieNum > 0) {
            zombieNum--;
            return (int)(Math.random() * 100) % 3;
        } else {
            return -1;
        }
    }

    @Override
    public int totalTime() {
        return 600;
    }

    @Override
    public int award() {
        return 50;
    }
}
