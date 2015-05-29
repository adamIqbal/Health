package com.health;

import java.util.concurrent.Callable;

/**
 * Utility class that provides certain helper functions.
 */
public final class Utils {
    private Utils() {
    }

    /**
     * Selects the first value that is evaluated to a value other than null.
     *
     * @param valueClosures
     *            an array of callables to be evaluated.
     * @param <T>
     *            the type of the result of the callables.
     * @return the first value that is evaluated to a value other than null, or
     *         null if all evaluated to null.
     */
    @SafeVarargs
    public static <T> T firstNonNull(final Callable<T>... valueClosures) {
        T value = null;

        // For each callable
        for (Callable<T> callable : valueClosures) {
            // Try invoking the callable if it is not null
            if (callable != null) {
                try {
                    value = callable.call();
                } catch (Exception e) {
                    continue;
                }
            }

            // If a non-null value was returned, break from the loop
            if (value != null) {
                break;
            }
        }

        return value;
    }
}
