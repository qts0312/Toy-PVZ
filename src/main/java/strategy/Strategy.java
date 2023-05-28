package strategy;

public abstract class Strategy {
    public static final int DEFAULT = 0;

    private final int level;

    public Strategy(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public abstract int sunTick(int time);
    public abstract int sunBound();

    public abstract int zombieTick(int time);
    public abstract int zombieKind(int time);

    public abstract int totalTime();

    public static Strategy getInstance(int strategy) {
        switch (strategy) {
            case DEFAULT:
                return new Default();
            default:
                return null;
        }
    }
}
