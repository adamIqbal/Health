package com.health.script.runtime;

/**
 * Represents a delegate, which refers to a method.
 *
 */
public final class ScriptDelegate extends Value {
    private static ScriptType type;
    private final ScriptMethod target;
    private final Value thisObj;

    static {
        ScriptTypeBuilder delegate = new ScriptTypeBuilder();
        delegate.setTypeName("delegate");

        ScriptDelegate.type = delegate.buildType();
    }

    /**
     * Creates a new value with the given target.
     *
     * @param target
     *            the method to which the delegate refers.
     */
    public ScriptDelegate(final ScriptMethod target) {
        this(target, null);
    }

    /**
     * Creates a new value with the given target and this-object.
     *
     * @param target
     *            the method to which the delegate refers.
     * @param thisObj
     *            the instance that the method is called on.
     */
    public ScriptDelegate(final ScriptMethod target, final Value thisObj) {
        super(ScriptDelegate.type);

        this.target = target;
        this.thisObj = thisObj;
    }

    /**
     * Invokes the target method with the given arguments.
     *
     * @param args
     *            the arguments to the method.
     * @return the return value of the method.
     * @throws ScriptRuntimeException
     *             if any script runtime errors occur.
     */
    public Value invoke(final Value[] args) throws ScriptRuntimeException {
        Value[] arguments = args;

        // If the method is not a static method, thisObj must be set as it
        // represents the instance that the method is called on
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
