package strategy;

public class Test extends Strategy {
    private final int[] zombieNum = {12, 12, 7};

    public Test() {
        super(2);
    }
    @Override
    public int sunBound() {
        return 500;
    }

    @Override
    public int sunTick(int time) {
        return 50;
    }

    @Override
    public int zombieTick(int time) {
        if (time <= 600)
            return 60;
        else
            return 30;
    }

    private int randZombieKind(int max) {
        int rand = (int) (Math.random() * 100) % max;
        while (zombieNum[rand] == 0) {
            rand = (int) (Math.random() * 100) % max;
        }
        zombieNum[rand]--;
        return rand;
    }

    @Override
    public int zombieKind(int time) {
        if (time <= 600)
            return randZombieKind(2);
        else if (time <= 1200)
            return randZombieKind(3);
        else
            return -1;
    }

    @Override
    public int totalTime() {
        return 1350;
    }

    @Override
    public int award() {
        return 100;
    }
}
