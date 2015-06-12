package com.health.script.runtime;

import java.util.Objects;

import com.health.EventList;

/**
 * Represents a table value in the script.
 */
public final class EventListValue extends ComplexValue {
    private static ScriptType type;
    private final EventList events;

    static {
        ScriptTypeBuilder events = new ScriptTypeBuilder();
        events.setTypeName("EventList");
        events.defineConstructor((args) -> null);
        events.defineMethod(new ScriptMethod("toString",
                (args) -> {
                    return new StringValue(((EventListValue) args[0]).events.toString());
                }));

        EventListValue.type = events.buildType();
    }

    /**
     * Creates a new value with the given underlying event list.
     *
     * @param events
     *            the event list.
     */
    public EventListValue(final EventList events) {
        super(EventListValue.type);

        Objects.requireNonNull(events);

        this.events = events;
    }

    /**
     * Gets the underlying table of this script event list.
     *
     * @return the underlying table of this script event list.
     */
    public EventList getValue() {
        return this.events;
    }

    /**
     * Gets the {@link ScriptType} corresponding to {@link EventListValue}.
     *
     * @return the {@link ScriptType} corresponding to {@link EventListValue}.
     */
    public static ScriptType getStaticType() {
        return EventListValue.type;
    }
}
