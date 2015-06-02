package com.health.interpreter;

import java.util.Objects;

import com.health.script.MyScriptBaseListener;
import com.health.script.MyScriptParser;
import com.health.script.runtime.Context;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.ScriptType;
import com.health.script.runtime.Value;

public final class StatementListener extends MyScriptBaseListener {
    private Context context;
    private ScriptType localVariableDeclarationType;
    private ExpressionValueVisitor expressionVisitor;
    private ExpressionLValueVisitor lValueVisitor;

    public StatementListener(final Context context) {
        Objects.requireNonNull(context);

        this.context = context;
        this.expressionVisitor = new ExpressionValueVisitor(context);
        this.lValueVisitor = new ExpressionLValueVisitor(context);

        this.expressionVisitor.setLValueVisitor(this.lValueVisitor);
        this.lValueVisitor.setExpressionVisitor(this.expressionVisitor);
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

        // If implicit typing is used, the value cannot be initialized to
        // null
        if (ctx.expression() != null) {
            Value value = this.expressionVisitor.visit(ctx.expression());

            if (type == null) {
                if (value == null) {
                    throw new ScriptRuntimeException("Cannot assign <null> to an implicitly-typed local variable.");
                } else {
                    type = value.getType();
                }
            }

            // Declare a local variable with the identifier, type and value
            context.declareLocal(ctx.IDENTIFIER().getText(), type, value);
        } else {
            // If implicit typing is used, the declarator must contain an
            // initializer
            if (type == null) {
                throw new ScriptRuntimeException("Implicitly-typed local variable must be initialized.");
            }

            // Declare a local variable with the identifier and type
            context.declareLocal(ctx.IDENTIFIER().getText(), type);
        }
    }

    private ScriptType getType(final MyScriptParser.LocalVariableTypeContext ctx) throws ScriptRuntimeException {
        if (ctx.VAR() != null) {
            return null;
        } else {
            return this.context.lookupType(ctx.getText());
        }
    }
}
