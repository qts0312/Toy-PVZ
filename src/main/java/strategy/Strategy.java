package strategy;

/**
 * Strategy class
 * <p>
 *     Game strategy of levels,
 * </p>
 */
public abstract class Strategy {
    public static final int DEFAULT = 0;
    public static final int FASTER = 1;
    public static final int TEST = 2;

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

    public abstract int award();

    public static Strategy getInstance(int strategy) {
        switch (strategy) {
            case DEFAULT:
                return new Default();
            case FASTER:
                return new Faster();
            case TEST:
                return new Test();
            default:
                return null;
        }
    }
}
