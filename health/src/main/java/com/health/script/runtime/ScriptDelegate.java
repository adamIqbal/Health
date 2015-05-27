package com.health.script.runtime;

public final class ScriptDelegate extends Value {
    private static ScriptType type;
    private ScriptMethod target;

    static {
        ScriptTypeBuilder delegate = new ScriptTypeBuilder();
        delegate.setTypeName("delegate");

        ScriptDelegate.type = delegate.buildType();
    }

    public ScriptDelegate(ScriptMethod target) {
        super(ScriptDelegate.type);

        this.target = target;
    }

    public Value invoke(Value[] args) {
        return this.target.getFunction().apply(args);
    }
}
