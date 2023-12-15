package edu.project4;

import java.util.Random;

public class RandomUtils {
    private static final ThreadLocal<Random> THREAD_LOCAL_RANDOM = ThreadLocal.withInitial(Random::new);

    private RandomUtils() {
    }

    public static Random threadLocalRandom(long seed) {
        Random random = THREAD_LOCAL_RANDOM.get();
        random.setSeed(seed);
        return random;
    }
}
