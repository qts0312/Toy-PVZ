package strategy;

public abstract class Strategy {
    public static final int DEFAULT = 0;

    public abstract int sunTick(int time);
    public abstract int sunBound();

    public abstract int zombieTick(int time);
    public abstract int zombieKind(int time);

    public static Strategy getInstance(int strategy) {
        switch (strategy) {
            case DEFAULT:
                return new Default();
            default:
                return null;
        }
    }
}
