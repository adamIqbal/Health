package com.health.script;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.health.script.AST.ArgumentList;
import com.health.script.AST.LocalVariableDeclarator;
import com.health.script.AST.LocalVariableDeclaratorList;
import com.health.script.AST.LocalVariableType;
import com.health.script.AST.Program;
import com.health.script.AST.SimpleType;
import com.health.script.AST.TypeName;
import com.health.script.AST.VarType;
import com.health.script.AST.Expressions.AnonymousObjectCreationExpression;
import com.health.script.AST.Expressions.Assignment;
import com.health.script.AST.Expressions.BooleanLiteral;
import com.health.script.AST.Expressions.Expression;
import com.health.script.AST.Expressions.Identifier;
import com.health.script.AST.Expressions.InvocationExpression;
import com.health.script.AST.Expressions.MemberAccess;
import com.health.script.AST.Expressions.NullLiteral;
import com.health.script.AST.Expressions.NumberLiteral;
import com.health.script.AST.Expressions.ParenthesizedExpression;
import com.health.script.AST.Expressions.StringLiteral;
import com.health.script.AST.Statements.DeclarationStatement;
import com.health.script.AST.Statements.ExpressionStatement;
import com.health.script.AST.Statements.Statement;
import com.health.script.runtime.BooleanValue;
import com.health.script.runtime.LValue;
import com.health.script.runtime.NumberValue;
import com.health.script.runtime.ScriptDelegate;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.ScriptType;
import com.health.script.runtime.StringValue;
import com.health.script.runtime.Value;

public final class MyScriptInterpreter implements Interpreter {

    @Override
    public void interpret(final Program program, final Context context) throws ScriptRuntimeException {
        Objects.requireNonNull(program, "Argument 'program' cannot be null.");
        Objects.requireNonNull(context, "Argument 'context' cannot be null.");

        for (Statement stmt : program.Statements) {
            interpret(stmt, context);
        }
    }

    private static void interpret(final Statement stmt, final Context context) throws ScriptRuntimeException {
        if (stmt instanceof DeclarationStatement) {
            DeclarationStatement declStmt = (DeclarationStatement) stmt;

            ScriptType type = getType(declStmt.Declaration.Type, context);

            declareLocals(declStmt.Declaration.Declarators, type, context);
        } else if (stmt instanceof ExpressionStatement) {
            ExpressionStatement exprStmt = (ExpressionStatement) stmt;

            evaluate(exprStmt.Expression, context);
        } else {
            throw new ScriptRuntimeException(String.format("Unknown statement: '%s'.", stmt.getClass().getName()));
        }
    }

    private static void declareLocals(
            final LocalVariableDeclaratorList declarations,
            final ScriptType type,
            final Context context)
            throws ScriptRuntimeException {
        if (declarations.Previous != null) {
            if (type == null) {
                throw new ScriptRuntimeException("Implicitly typed local variables cannot have multiple declarators.");
            }

            declareLocals(declarations.Previous, type, context);
        }

        LocalVariableDeclarator declarator = declarations.Declarator;

        if (declarator.Equals != null) {
            Value value = evaluate(declarator.Expression, context);
            ScriptType actualType = type;

            if (type == null) {
                if (value == null) {
                    throw new ScriptRuntimeException("Cannot assign <null> to an implicitly-typed local variable.");
                } else {
                    actualType = value.getType();
                }
            }

            context.declareLocal(declarator.Identifier.Identifier.getLexeme(), actualType, value);
        } else {
            if (type == null) {
                throw new ScriptRuntimeException("Implicitly-typed local variable must be initialized.");
            }

            context.declareLocal(declarator.Identifier.Identifier.getLexeme(), type);
        }
    }

    private static Value evaluate(final Expression expr, final Context context) throws ScriptRuntimeException {
        if (expr instanceof NullLiteral) {
            return null;
        } else if (expr instanceof NumberLiteral) {
            return new NumberValue((double) ((NumberLiteral) expr).Literal.getValue());
        } else if (expr instanceof BooleanLiteral) {
            return new BooleanValue((boolean) ((BooleanLiteral) expr).Literal.getValue());
        } else if (expr instanceof StringLiteral) {
            return new StringValue((String) ((StringLiteral) expr).Literal.getValue());
        } else if (expr instanceof Assignment) {
            Assignment assignment = (Assignment) expr;

            LValue left = lValueOf(assignment.Left, context);
            Value right = evaluate(assignment.Right, context);

            left.set(right);

            return right;

        } else if (expr instanceof ParenthesizedExpression) {
            return evaluate(((ParenthesizedExpression) expr).Expression, context);
        } else if (expr instanceof AnonymousObjectCreationExpression) {
            throw new ScriptRuntimeException("Not supported.");
        } else if (expr instanceof Identifier) {
            return lValueOf(expr, context).get();
        } else if (expr instanceof MemberAccess) {
            return lValueOf(expr, context).get();
        } else if (expr instanceof InvocationExpression) {
            InvocationExpression invocation = (InvocationExpression) expr;

            Value val = evaluate(invocation.Expression, context);

            if (!(val instanceof ScriptDelegate)) {
                throw new ScriptRuntimeException("Tried to invoke expression that was not a function.");
            }

            ScriptDelegate delegate = (ScriptDelegate) val;

            // TOOD: Handle arguments
            return delegate.invoke(evaluateArguments(invocation.Arguments, context));
        } else {
            throw new ScriptRuntimeException(String.format("Unknown expression: '%s'.", expr.getClass().getName()));
        }
    }

    private static LValue lValueOf(final Expression expr, final Context context) throws ScriptRuntimeException {
        if (expr instanceof ParenthesizedExpression) {
            return lValueOf(((ParenthesizedExpression) expr).Expression, context);
        } else if (expr instanceof Identifier) {
            return context.lookup(((Identifier) expr).Identifier.getLexeme());
        } else if (expr instanceof MemberAccess) {
            MemberAccess memberAccess = (MemberAccess) expr;
            Value obj = evaluate(memberAccess.Expression, context);

            if (obj == null) {
                throw new ScriptRuntimeException("Object reference not set to an instance of an object.");
            }

            String symbol = memberAccess.Identifier.Identifier.getLexeme();

            return obj.getMember(symbol);
        } else {
            throw new ScriptRuntimeException(String.format(
                    "Expression '%s' is not an l-value.", expr.getClass().getName()));
        }
    }

    private static ScriptType getType(final LocalVariableType type, final Context context)
            throws ScriptRuntimeException {
        if (type instanceof VarType) {
            return null;
        } else if (type instanceof SimpleType) {
            return context.lookupType(((SimpleType) type).Type.getLexeme());
        } else if (type instanceof TypeName) {
            return context.lookupType(((TypeName) type).Name.Identifier.getLexeme());
        } else {
            throw new ScriptRuntimeException(String.format("Unknown type: '%s'.", type.getClass().getName()));
        }
    }

    private static Value[] evaluateArguments(final ArgumentList arguments, final Context context)
            throws ScriptRuntimeException {
        List<Value> values = new ArrayList<Value>();

        if (arguments != null) {
            evaluateArguments(arguments, context, values);
        }

        Value[] args = new Value[values.size()];
        values.toArray(args);

        return args;
    }

    private static void evaluateArguments(final ArgumentList arguments, final Context context, final List<Value> values)
            throws ScriptRuntimeException {
        if (arguments.Previous != null) {
            evaluateArguments(arguments.Previous, context, values);
        }

        values.add(evaluate(arguments.Argument, context));
    }
}
