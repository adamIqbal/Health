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

/**
 * Implements an interpreter for the script.
 *
 * @author Martijn
 */
public final class MyScriptInterpreter implements Interpreter {
    /**
     * {@inheritDoc}
     */
    @Override
    public void interpret(final Program program, final Context context) throws ScriptRuntimeException {
        Objects.requireNonNull(program, "Argument 'program' cannot be null.");
        Objects.requireNonNull(context, "Argument 'context' cannot be null.");

        for (Statement stmt : program.Statements) {
            interpret(stmt, context);
        }
    }

    /**
     * Interprets a given statement.
     *
     * @param stmt
     *            the statement to interpret.
     * @param context
     *            the runtime environment.
     * @throws ScriptRuntimeException
     *             if any script runtime errors occur.
     */
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

    /**
     * Declares the local variables for a given
     * {@link LocalVariableDeclaratorList}.
     *
     * @param declarations
     *            the declarator list to declare.
     * @param type
     *            the type of the variables to declare.
     * @param context
     *            the runtime environment.
     * @throws ScriptRuntimeException
     *             if any script runtime errors occur.
     */
    private static void declareLocals(
            final LocalVariableDeclaratorList declarations,
            final ScriptType type,
            final Context context)
            throws ScriptRuntimeException {
        // Declare the preceding declarators
        if (declarations.Previous != null) {
            if (type == null) {
                throw new ScriptRuntimeException("Implicitly typed local variables cannot have multiple declarators.");
            }

            declareLocals(declarations.Previous, type, context);
        }

        LocalVariableDeclarator declarator = declarations.Declarator;

        if (declarator.Equals != null) {
            // The declarator contains an initializer, evaluate it
            Value value = evaluate(declarator.Expression, context);
            ScriptType actualType = type;

            // If implicit typing is used, the value cannot be initialized to
            // null
            if (type == null) {
                if (value == null) {
                    throw new ScriptRuntimeException("Cannot assign <null> to an implicitly-typed local variable.");
                } else {
                    actualType = value.getType();
                }
            }

            // Declare a local variable with the identifier, type and value
            context.declareLocal(declarator.Identifier.Identifier.getLexeme(), actualType, value);
        } else {
            // If implicit typing is used, the declarator must contain an
            // initializer
            if (type == null) {
                throw new ScriptRuntimeException("Implicitly-typed local variable must be initialized.");
            }

            // Declare a local variable with the identifier and type
            context.declareLocal(declarator.Identifier.Identifier.getLexeme(), type);
        }
    }

    /**
     * Evaluates a given expression.
     *
     * @param expr
     *            the expression to evaluate.
     * @param context
     *            the runtime environment.
     * @return value resulting from evaluating the expression.
     * @throws ScriptRuntimeException
     *             if any script runtime errors occur.
     */
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
            throw new ScriptRuntimeException("AnonymousObjectCreationExpression is not yet supported.");
        } else if (expr instanceof Identifier) {
            return lValueOf(expr, context).get();
        } else if (expr instanceof MemberAccess) {
            return lValueOf(expr, context).get();
        } else if (expr instanceof InvocationExpression) {
            InvocationExpression invocation = (InvocationExpression) expr;

            Value value = evaluate(invocation.Expression, context);

            if (!(value instanceof ScriptDelegate)) {
                throw new ScriptRuntimeException("Tried to invoke expression that was not a function.");
            }

            ScriptDelegate delegate = (ScriptDelegate) value;

            // TOOD: Handle argument type checking
            return delegate.invoke(evaluateArguments(invocation.Arguments, context));
        } else {
            throw new ScriptRuntimeException(String.format("Unknown expression: '%s'.", expr.getClass().getName()));
        }
    }

    /**
     * Returns the l-value of a given expression.
     *
     * @param expr
     *            the expression to evaluate.
     * @param context
     *            the runtime environment.
     * @return l-value of the expression.
     * @throws ScriptRuntimeException
     *             if the given expression is not an l-value or if any script
     *             runtime errors occur.
     */
    private static LValue lValueOf(final Expression expr, final Context context) throws ScriptRuntimeException {
        if (expr instanceof ParenthesizedExpression) {
            return lValueOf(((ParenthesizedExpression) expr).Expression, context);
        } else if (expr instanceof Identifier) {
            return context.lookup(((Identifier) expr).Identifier.getLexeme());
        } else if (expr instanceof MemberAccess) {
            MemberAccess memberAccess = (MemberAccess) expr;

            // Evaluate the object to be accessed
            Value obj = evaluate(memberAccess.Expression, context);

            if (obj == null) {
                throw new ScriptRuntimeException("Object reference not set to an instance of an object.");
            }

            // Get the name of the member being accessed
            String symbol = memberAccess.Identifier.Identifier.getLexeme();

            // Get the l-value of the member being accessed
            return obj.getMember(symbol);
        } else {
            throw new ScriptRuntimeException(String.format(
                    "Expression '%s' is not an l-value.", expr.getClass().getName()));
        }
    }

    /**
     * Evaluates a {@link LocalVariableType} to obtain the corresponding
     * {@link ScriptType}.
     *
     * @param type
     *            the type to evaluate.
     * @param context
     *            the runtime environment.
     * @return the {@link ScriptType} corresponding to the given type.
     * @throws ScriptRuntimeException
     *             if any script runtime errors occur.
     */
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

    /**
     * Evaluates the arguments for a given {@link ArgumentList}.
     *
     * @param arguments
     *            the argument list to evaluate.
     * @param context
     *            the runtime environment.
     * @throws ScriptRuntimeException
     *             if any script runtime errors occur.
     */
    private static Value[] evaluateArguments(final ArgumentList arguments, final Context context)
            throws ScriptRuntimeException {
        // Create a list for the arguments
        List<Value> values = new ArrayList<Value>();

        if (arguments != null) {
            // Evaluate the arguments in the argument list and add them to the
            // list
            evaluateArguments(arguments, context, values);
        }

        // Convert the list to an array
        Value[] args = new Value[values.size()];
        values.toArray(args);

        return args;
    }

    private static void evaluateArguments(final ArgumentList arguments, final Context context, final List<Value> values)
            throws ScriptRuntimeException {
        // Evaluate the preceding arguments
        if (arguments.Previous != null) {
            evaluateArguments(arguments.Previous, context, values);
        }

        // Evaluate the argument and add it to the list
        values.add(evaluate(arguments.Argument, context));
    }
}
