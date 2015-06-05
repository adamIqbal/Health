package com.health.interpreter;

import java.util.ArrayList;
import java.util.List;

import com.health.Table;
import com.health.operations.ColumnConnection;
import com.health.operations.Connect;
import com.health.script.MyScriptParser;
import com.health.script.runtime.Context;
import com.health.script.runtime.TableValue;
import com.health.script.runtime.Value;

public final class ConnectExpressionInterpreter extends TableExpressionInterpreter {
    protected ConnectExpressionInterpreter(
            final Context context,
            final ExpressionValueVisitor expressionVisitor) {
        super(context, expressionVisitor);
    }

    public Value interpret(final MyScriptParser.ConnectExpressionContext ctx) {
        String table1Ident = ctx.table1.getText();
        String table2Ident = ctx.table2.getText();

        Table table1 = lookupTable(table1Ident);
        Table table2 = lookupTable(table2Ident);

        List<ColumnConnection> connections = evaluateColumnConnections(ctx.columnConnectionList());

        return new TableValue(Connect.connect(table1, table2, connections));
    }

    private List<ColumnConnection> evaluateColumnConnections(final MyScriptParser.ColumnConnectionListContext ctx) {
        List<ColumnConnection> connections = new ArrayList<ColumnConnection>();

        evaluateColumnConnections(ctx, connections);

        return connections;
    }

    private void evaluateColumnConnections(final MyScriptParser.ColumnConnectionListContext ctx,
            final List<ColumnConnection> connections) {
        if (ctx.columnConnectionList() != null) {
            evaluateColumnConnections(ctx.columnConnectionList(), connections);
        }

        connections.add(evaluateColumnConnection(ctx.columnConnection()));
    }

    private ColumnConnection evaluateColumnConnection(final MyScriptParser.ColumnConnectionContext ctx) {
        String newName;

        if (ctx.newName != null) {
            newName = ctx.newName.getText();
        } else {
            newName = ctx.column1.getText();
        }

        return new ColumnConnection(ctx.column1.getText(), ctx.column2.getText(), newName);
    }
}