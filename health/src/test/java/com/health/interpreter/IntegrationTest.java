package com.health.interpreter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.health.Column;
import com.health.EventList;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;
import com.health.script.runtime.BooleanValue;
import com.health.script.runtime.Context;
import com.health.script.runtime.EventListValue;
import com.health.script.runtime.NumberValue;
import com.health.script.runtime.ScriptFunction;
import com.health.script.runtime.ScriptRuntimeException;
import com.health.script.runtime.StringValue;
import com.health.script.runtime.Value;
import com.health.script.runtime.WrapperValue;

public class IntegrationTest {
    private Context context;
    private Table table;
    private Table table2;
    private Column[] columns;

    @Before
    public void setUp() {
        context = new Context();

        columns = new Column[] {
                new Column("name", 0, ValueType.String),
                new Column("value", 1, ValueType.Number),
                new Column("date", 2, ValueType.Date),
        };

        table = new Table(Arrays.asList(columns));
        table2 = new Table(Arrays.asList(columns));

        Record r1 = new Record(table);
        r1.setValue("value", 1.0);
        r1.setValue("name", "one");
        r1.setValue("date", LocalDateTime.of(1970, 1, 1, 0, 0));

        Record r2 = new Record(table);
        r2.setValue("value", 4.0);
        r2.setValue("name", "one");
        r2.setValue("date", LocalDateTime.of(1970, 1, 1, 1, 0));

        Record r3 = new Record(table);
        r3.setValue("value", 2.0);
        r3.setValue("name", "two");
        r3.setValue("date", LocalDateTime.of(1970, 1, 2, 0, 0));

        Record r4 = new Record(table2);
        r4.setValue("value", 5.0);
        r4.setValue("name", "three");
        r3.setValue("date", LocalDateTime.of(1971, 1, 1, 0, 0));

        Record r5 = new Record(table2);
        r5.setValue("value", 2.0);
        r5.setValue("name", "four");
        r3.setValue("date", LocalDateTime.of(1971, 1, 2, 0, 0));

        WrapperValue<Table> value = new WrapperValue<Table>(table);
        context.declareLocal("table", value.getType(), value);
    }

    @Test
    public void variableDeclaration() throws IOException {
        Interpreter.interpret("Table myVar;", context);

        assertEquals(true, context.isLocalDefined("myVar"));
    }

    @Test
    public void testVariableInitialization() throws IOException {
        Interpreter.interpret("Double myVar = 7.0;", context);

        Double expected = 7.0;
        Double actual = ((NumberValue) context.lookup("myVar").get()).getValue();

        assertEquals(expected, actual);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void testUninitializedImplicitVariableDeclaration() throws IOException {
        Interpreter.interpret("var myVar;", context);
    }

    @Test
    public void testImplicitVariableInitialization() throws IOException {
        Interpreter.interpret("var myVar = true;", context);

        Boolean expected = true;
        Boolean actual = ((BooleanValue) context.lookup("myVar").get()).getValue();

        assertEquals(expected, actual);
    }

    @Test(expected = ScriptRuntimeException.class)
    public void testImplicitVariableInitializationWithNull() throws IOException {
        Interpreter.interpret("var myVar = null;", context);
    }

    @Test
    public void testAssignment() throws IOException {
        Interpreter.interpret("String myVar = \"one\"; myVar = \"two\";", context);

        String expected = "two";
        String actual = ((StringValue) context.lookup("myVar").get()).getValue();

        assertEquals(expected, actual);
    }

    @Test
    public void testParenthesizedExpression() throws IOException {
        Interpreter.interpret("var myVar = 1; myVar = (2);", context);

        Double expected = 2.0;
        Double actual = ((NumberValue) context.lookup("myVar").get()).getValue();

        assertEquals(expected, actual);
    }

    @Test
    public void testMemberAccessExpression() throws IOException {
        Interpreter.interpret("var myVar = \"abc\"; String result = myVar.toString();", context);

        String expected = "abc";
        String actual = ((StringValue) context.lookup("result").get()).getValue();

        assertEquals(expected, actual);
    }

    @Test
    public void testStaticMethodCall() throws IOException {
        @SuppressWarnings("unchecked")
        ScriptFunction<Value[], Value> function = (ScriptFunction<Value[], Value>) mock(ScriptFunction.class);
        context.declareStaticMethod("myMethod", function);

        Interpreter.interpret("myMethod();", context);

        verify(function).apply(any());
    }

    @Test
    public void testStaticMethodCallWithArguments() throws IOException {
        ArgumentCaptor<Value[]> argsCaptor = ArgumentCaptor.forClass(Value[].class);

        @SuppressWarnings("unchecked")
        ScriptFunction<Value[], Value> function = (ScriptFunction<Value[], Value>) mock(ScriptFunction.class);
        context.declareStaticMethod("myMethod", function);

        Interpreter.interpret("myMethod(true);", context);

        verify(function).apply(argsCaptor.capture());

        Boolean expected = true;
        Boolean actual = ((BooleanValue) argsCaptor.getValue()[0]).getValue();

        assertEquals(expected, actual);
    }

    @Test
    public void testChunkExpression() throws IOException {
        Interpreter.interpret("var result = chunk table by name select sum of value;", context);

        @SuppressWarnings("unchecked")
        Table result = ((WrapperValue<Table>) context.lookup("result").get()).getValue();

        assertThat(result.getRecords(), iterableWithSize(2));
    }

    @Test
    public void testChunkByPeriodExpression() throws IOException {
        Interpreter.interpret("var result = chunk table by date per day "
                + "select value, count of date, average of value, min of value, max of value;", context);

        @SuppressWarnings("unchecked")
        Table result = ((WrapperValue<Table>) context.lookup("result").get()).getValue();

        assertThat(result.getRecords(), iterableWithSize(2));
    }

    @Test
    public void testChunkByPluralPeriodExpression() throws IOException {
        Interpreter.interpret("var result = chunk table by date per 2 hours select value;", context);

        @SuppressWarnings("unchecked")
        Table result = ((WrapperValue<Table>) context.lookup("result").get()).getValue();

        assertThat(result.getRecords(), iterableWithSize(2));
    }

    @Test
    public void testCodeExpression() throws IOException {
        Interpreter.interpret("var result = code table as LOW : value <= 2, HIGH : value >= 2;", context);

        EventList result = ((EventListValue) context.lookup("result").get()).getValue();

        assertThat(result.getList(), iterableWithSize(4));
    }

    @Test
    public void testConstrainExpression() throws IOException {
        Interpreter.interpret("var result = constrain table where value > 1.0 and value < 4.0;", context);

        @SuppressWarnings("unchecked")
        Table result = ((WrapperValue<Table>) context.lookup("result").get()).getValue();

        assertThat(result.getRecords(), iterableWithSize(1));
    }

    @Test
    public void testConnectExpression() throws IOException {
        WrapperValue<Table> value = new WrapperValue<Table>(table2);
        context.declareLocal("table2", value.getType(), value);

        Interpreter.interpret(
                "var result = connect table with table2 where name == name as name and value = value;", context);

        @SuppressWarnings("unchecked")
        Table result = ((WrapperValue<Table>) context.lookup("result").get()).getValue();

        assertThat(result.getRecords(), iterableWithSize(5));
    }
}
