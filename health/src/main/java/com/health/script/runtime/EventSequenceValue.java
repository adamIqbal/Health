package com.health.script.runtime;

import com.health.EventSequence;
import com.health.Table;

public final class EventSequenceValue extends WrapperValue<EventSequence> {
    private static ScriptType type;

    static {
        ScriptTypeBuilder sequence = new ScriptTypeBuilder();
        sequence.setTypeName("sequence");
        sequence.defineConstructor((args) -> new StringValue());
        sequence.defineMethod(new ScriptMethod("toString",
                (args) -> {
                    return new StringValue(((EventSequenceValue) args[0]).getValue().toString());
                }));
        sequence.defineMethod(new ScriptMethod("toTable",
                (args) -> {
                    return new WrapperValue<Table>(((EventSequenceValue) args[0]).getValue().toTable());
                }));

        EventSequenceValue.type = sequence.buildType();
    }

    public EventSequenceValue(EventSequence value) {
        super(EventSequenceValue.type, value);
    }

    /**
     * Gets the {@link ScriptType} corresponding to {@link EventSequenceValue}.
     *
     * @return the {@link ScriptType} corresponding to
     *         {@link EventSequenceValue}.
     */
    public static ScriptType getStaticType() {
        return EventSequenceValue.type;
    }
}
