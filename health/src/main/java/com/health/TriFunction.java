package com.health;

/**
 * Represents a function that accepts three arguments.
 *
 * @param <T>
 *            the type of the first argument to the function.
 * @param <U>
 *            the type of the second argument to the function.
 * @param <V>
 *            the type of the third argument to the function.
 */
@FunctionalInterface
public interface TriFunction<T, U, V> {
    /**
     * Applies this function to the given arguments.
     *
     * @param t
     *            the first function argument.
     * @param u
     *            the second function argument.
     * @param v
     *            the third function argument.
     */
    void apply(T t, U u, V v);
}
