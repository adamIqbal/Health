package com.health.interpreter;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.health.Column;
import com.health.Table;
import com.health.script.runtime.Context;
import com.health.script.runtime.LValue;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.ScriptType;
import com.health.script.runtime.WrapperValue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Column.class, Context.class, LValue.class, WrapperValue.class, Table.class, ScriptType.class })
public class BaseExpressionInterpreterTest {
    private final static String tableName = "table";
    private final static String nonTableName = "noTable";
    private final static String undeclaredName = "undeclared";
    private final static String columnName = "column";
    private final static String nonColumnName = "noColumn";

    private Context context;
    private Table table;

    @Mock
    private WrapperValue<Table> value;

    private LValue lvalue;
    private LValue nonTableLValue;
    private ScriptType type;
    private ScriptType nonTableType;

    @Before
    public void setUp() {
        context = mock(Context.class);
        table = mock(Table.class);

        lvalue = mock(LValue.class);
        nonTableLValue = mock(LValue.class);

        type = mock(ScriptType.class);
        nonTableType = mock(ScriptType.class);

        when(table.getColumn(columnName)).thenReturn(mock(Column.class));
        when(table.getColumn(nonColumnName)).thenReturn(null);

        when(lvalue.get()).thenReturn(value);
        when(lvalue.getType()).thenReturn(type);
        when(value.getValue()).thenReturn(table);

        when(nonTableLValue.getType()).thenReturn(nonTableType);

        when(type.isAssignableFrom(type)).thenReturn(true);
        when(type.isAssignableFrom(nonTableType)).thenReturn(false);

        when(context.lookup(null)).thenThrow(new NullPointerException());
        when(context.lookup(undeclaredName)).thenThrow(new ScriptRuntimeException(""));
        when(context.lookup(tableName)).thenReturn(lvalue);
        when(context.lookup(nonTableName)).thenReturn(nonTableLValue);

        mockStatic(WrapperValue.class);
        when(WrapperValue.getWrapperType(Table.class)).thenReturn(type);
    }

    @Test(expected = NullPointerException.class)
    public void lookupTable_givenContexNull_throwsNullPointerException() {
        BaseExpressionInterpreter.lookupTable(null, tableName);
    }

    @Test(expected = NullPointerException.class)
    public void lookupTable_givenTableNameNull_throwsNullPointerException() {
        BaseExpressionInterpreter.lookupTable(context, null);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void lookupTable_givenContextNotContainingTableName_throwsScriptRuntimeException() {
        BaseExpressionInterpreter.lookupTable(context, undeclaredName);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void lookupTable_givenContextContainingNonTableWithName_throwsScriptRuntimeException() {
        BaseExpressionInterpreter.lookupTable(context, nonTableName);
    }

    @Test
    public void lookupTable_givenContextContainingTableWithName_returnsTable() {
        Table expected = table;
        Table actual = BaseExpressionInterpreter.lookupTable(context, tableName);

        assertSame(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void verifyHasColumn_givenTableNull_throwsNullPointerException() {
        BaseExpressionInterpreter.verifyHasColumn(null, tableName, columnName);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void verifyHasColumn_givenTableNotContainingColumnName_throwsScriptRuntimeException() {
        BaseExpressionInterpreter.verifyHasColumn(table, tableName, nonColumnName);
    }

    @Test
    public void verifyHasColumn_givenTableContainingColumnName_doesNothing() {
        BaseExpressionInterpreter.verifyHasColumn(table, tableName, columnName);
    }
}
