package com.health.script.runtime;

import java.util.function.Function;

/**
 * Represents a function that accepts one argument and produces a result.
 *
 * @param <T>
 *            the type of the input to the function.
 * @param <R>
 *            the type of the result of the function.
 */
@FunctionalInterface
public interface ScriptFunction<T, R> extends Function<T, R> {
    @Override
    default R apply(final T t) {
        try {
            return applyThrows(t);
        } catch (final Exception e) {
            throw new ScriptRuntimeException("An unhandled exception occured.", e);
        }
    }

    /**
     * Applies this function to the given argument.
     *
     * @param t
     *            the function argument
     * @return the function result
     * @throws Exception
     *             if any exceptions occur.
     */
    R applyThrows(final T t) throws Exception;
}
