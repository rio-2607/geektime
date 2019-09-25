package com.beautyboss.slogen.easy.client;

public final class ThreadPoolUtil {

    public static final int  MAXIMUMPOOLLSIZE = 96;

    private ThreadPoolUtil() {

    }

    public static int ioIntesivePoolSize() {

        double blockingCoefficient = 0.9;
        return poolSize(blockingCoefficient);
    }

    public static int poolSize(double blockingCoefficient) {
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        int poolSize = (int) (numberOfCores / (1 - blockingCoefficient));
        return poolSize;
    }
}