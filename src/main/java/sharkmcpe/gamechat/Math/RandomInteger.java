package sharkmcpe.gamechat.Math;

import java.util.SplittableRandom;

public class RandomInteger {
    public static final SplittableRandom random = new SplittableRandom();

    public static int rand(int min, int max) {
        if (min == max) {
            return max;
        }
        return random.nextInt(max + 1 - min) + min;
    }
}