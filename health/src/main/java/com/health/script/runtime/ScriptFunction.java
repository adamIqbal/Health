package com.health.script.runtime;

import java.util.function.Function;

@FunctionalInterface
public interface ScriptFunction<T, R> extends Function<T, R> {
    @Override
    default R apply(final T elem) {
        try {
            return applyThrows(elem);
        } catch (final Exception e) {
            throw new ScriptRuntimeException("An unhandled exception occured.", e);
        }
    }

    R applyThrows(final T elem) throws Exception;
}
