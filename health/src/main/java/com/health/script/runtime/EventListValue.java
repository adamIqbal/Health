package com.health.script.runtime;

import com.health.EventList;
import com.health.Table;

public final class EventListValue extends WrapperValue<EventList> {
    private static ScriptType type;

    static {
        ScriptTypeBuilder eventList = new ScriptTypeBuilder();
        eventList.setTypeName("EventList");
        eventList.defineConstructor((args) -> new StringValue());
        eventList.defineMethod(new ScriptMethod("toString",
                (args) -> {
                    return new StringValue(((EventSequenceValue) args[0]).getValue().toString());
                }));
        eventList.defineMethod(new ScriptMethod("toTable",
                (args) -> {
                    return new WrapperValue<Table>(((EventListValue) args[0]).getValue().toTable());
                }));

        EventListValue.type = eventList.buildType();
    }

    public EventListValue(EventList value) {
        super(EventListValue.type, value);
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
