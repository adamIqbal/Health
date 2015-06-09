package com.health.interpreter;

import java.util.Objects;

import com.health.script.MyScriptBaseListener;
import com.health.script.MyScriptParser;
import com.health.script.runtime.Context;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.ScriptType;
import com.health.script.runtime.Value;

/**
 * A listener that listens for statement nodes and processes them.
 */
public final class StatementListener extends MyScriptBaseListener {
    private final Context context;
    private final ExpressionValueVisitor expressionVisitor;
    private ScriptType localVariableDeclarationType;

    /**
     * Creates a new {@link StatementListener} with the given context.
     *
     * @param context
     *            the context.
     */
    public StatementListener(final Context context) {
        Objects.requireNonNull(context);

        this.context = context;
        this.expressionVisitor = new ExpressionValueVisitor(context);
    }

    @Override
    public void enterExpressionStatement(final MyScriptParser.ExpressionStatementContext ctx) {
        this.expressionVisitor.visit(ctx.statementExpression());
    }

    @Override
    public void enterLocalVariableDeclaration(final MyScriptParser.LocalVariableDeclarationContext ctx) {
        this.localVariableDeclarationType = this.getType(ctx.localVariableType());
    }

    @Override
    public void exitLocalVariableDeclaration(final MyScriptParser.LocalVariableDeclarationContext ctx) {
        this.localVariableDeclarationType = null;
    }

    @Override
    public void enterLocalVariableDeclarator(final MyScriptParser.LocalVariableDeclaratorContext ctx) {
        ScriptType type = this.localVariableDeclarationType;

        if (ctx.expression() != null) {
            declareLocalWithInitializer(ctx, type);
        } else {
            declareLocal(ctx, type);
        }
    }

    private void declareLocal(final MyScriptParser.LocalVariableDeclaratorContext ctx, final ScriptType type) {
        // If implicit typing is used, the declarator should have contained an initializer
        if (type == null) {
            throw new ScriptRuntimeException("Implicitly-typed local variable must be initialized.");
        }

        context.declareLocal(ctx.IDENTIFIER().getText(), type);
    }

    private void declareLocalWithInitializer(final MyScriptParser.LocalVariableDeclaratorContext ctx,
            final ScriptType type) {
        Value value = this.expressionVisitor.visit(ctx.expression());
        ScriptType actualType = type;

        if (actualType == null) {
            // If implicit typing is used, the value cannot be initialized to null
            if (value == null) {
                throw new ScriptRuntimeException("Cannot assign <null> to an implicitly-typed local variable.");
            } else {
                actualType = value.getType();
            }
        }

        context.declareLocal(ctx.IDENTIFIER().getText(), actualType, value);
    }

    private ScriptType getType(final MyScriptParser.LocalVariableTypeContext ctx) throws ScriptRuntimeException {
        if (ctx.VAR() != null) {
            return null;
        } else {
            return this.context.lookupType(ctx.getText());
        }
    }
}
