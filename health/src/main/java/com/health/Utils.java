package com.health;

import java.util.concurrent.Callable;

public final class Utils {
    private Utils() {
    }

    @SafeVarargs
    public static <T> T firstNonNull(final Callable<T>... valueClosures) {
        T value = null;

        for (Callable<T> callable : valueClosures) {
            if (callable != null) {
                try {
                    value = callable.call();
                } catch (Exception e) {
                }
            }

            if (value != null) {
                break;
            }
        }

        return value;
    }
}
