package com.health.script.runtime;

public final class ScriptDelegate extends Value {
    private static ScriptType type;
    private final ScriptMethod target;
    private final Value thisObj;

    static {
        ScriptTypeBuilder delegate = new ScriptTypeBuilder();
        delegate.setTypeName("delegate");

        ScriptDelegate.type = delegate.buildType();
    }

    public ScriptDelegate(final ScriptMethod target) {
        this(target, null);
    }

    public ScriptDelegate(final ScriptMethod scriptMethod, final Value thisObj) {
        super(ScriptDelegate.type);

        this.target = scriptMethod;
        this.thisObj = thisObj;
    }

    public Value invoke(final Value[] args) throws ScriptRuntimeException {
        Value[] arguments = args;

        if (!this.target.isStatic()) {
            if (this.thisObj == null) {
                throw new ScriptRuntimeException("Object reference not set to an instance of an object.");
            }

            arguments = new Value[args.length + 1];
            arguments[0] = this.thisObj;

            System.arraycopy(args, 0, arguments, 1, args.length);
        }

        return this.target.getFunction().apply(arguments);
    }
}
