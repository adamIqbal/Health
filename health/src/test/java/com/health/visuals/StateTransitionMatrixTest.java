package com.health.visuals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Container;
import java.io.File;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.health.Column;
import com.health.Event;
import com.health.EventList;
import com.health.EventSequence;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;
import com.health.operations.Code;

/**
 * Test for State Transition Matrix.
 *
 * @author Lizzy Scholten
 *
 */
public class StateTransitionMatrixTest {
	private List<EventList> listOfSeq;
	private EventList eList;
	private Table table;

    /**
     * Dummy input for testing the State Transition Matrix.
     */
    @Before
    public final void setUp() {
        // Create dummy input values
        final int two = 2;
        final double two2 = 2.0;
        final int three = 3;
        final double three2 = 3.0;
        final int four = 4;
        final double four2 = 4.0;

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("date", 0, ValueType.Date));
        columns.add(new Column("waarde", 1, ValueType.Number));
        columns.add(new Column("name", 2, ValueType.String));

        table = new Table(columns);

        Record tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(1, 1, 1));
        tmp.setValue(1, 1.0);
        tmp.setValue(2, "piet");

        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(2, 2, 2));
        tmp.setValue(1, 2.0);
        tmp.setValue(2, "Jan");

        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(three, two, three));
        tmp.setValue(1, three2);
        tmp.setValue(2, "piet");

        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(four, two, two));
        tmp.setValue(1, four2);
        tmp.setValue(2, "piet");

        tmp = new Record(table);
        tmp.setValue(0, LocalDate.of(four, two, three));
        tmp.setValue(1, two2);
        tmp.setValue(2, "Jan");

        eList = new EventList();

        Event e1 = new Event("A", table.getRecords().get(0));
        Event e2 = new Event("B", table.getRecords().get(1));
        Event e3 = new Event("A", table.getRecords().get(2));
        Event e4 = new Event("B", table.getRecords().get(three));
        Event e5 = new Event("E", table.getRecords().get(four));
        Event e6 = new Event("C", table.getRecords().get(four));
        Event e7 = new Event("A", table.getRecords().get(0));
        Event e8 = new Event("D", table.getRecords().get(1));
        Event e9 = new Event("E", table.getRecords().get(2));
        Event e10 = new Event("B", table.getRecords().get(three));
        Event e11 = new Event("A", table.getRecords().get(four));
        Event e12 = new Event("D", table.getRecords().get(four));

        eList.addEvent(e1);
        eList.addEvent(e2);
        eList.addEvent(e3);
        eList.addEvent(e4);
        eList.addEvent(e5);
        eList.addEvent(e6);
        eList.addEvent(e7);
        eList.addEvent(e8);
        eList.addEvent(e9);
        eList.addEvent(e10);
        eList.addEvent(e11);
        eList.addEvent(e12);

        String[] codePat1 = {"B", "A", "A", "B", "A", "D", "B"};
        String[] codePat2 = {"A", "B", "A", "B"};
        String[] codePat3 = {"A", "B", "A", "C", "D", "E", "A", "C"};
        String[] codePat4 = {"C", "B", "E", "C", "B"};

        EventSequence eSeq1 = new EventSequence(codePat1);
        EventSequence eSeq2 = new EventSequence(codePat2);
        EventSequence eSeq3 = new EventSequence(codePat3);
        EventSequence eSeq4 = new EventSequence(codePat4);

        listOfSeq = new ArrayList<EventList>();

        listOfSeq.addAll(Code.fillEventSequence(eSeq1, eList));
        listOfSeq.addAll(Code.fillEventSequence(eSeq2, eList));
        listOfSeq.addAll(Code.fillEventSequence(eSeq3, eList));
        listOfSeq.addAll(Code.fillEventSequence(eSeq4, eList));
    }

    /**
     * Test that table is indeed saved as file.
     */
    @Test
	public final void fileTest() {
    	Container table = StateTransitionMatrix.createStateTrans(eList, listOfSeq);

    	StateTransitionMatrix.saveFile("StateTransTest", table);

        File f = new File("StateTransTest.pdf");
        assertTrue(f.exists());
    }

    /**
     * Test that constructor cannot be called.
     * @throws Exception
     * 				exception
     */
    @SuppressWarnings("rawtypes")
	@Test
	public final void constructorTest() throws Exception {
        Constructor[] ctors = StateTransitionMatrix.class.getDeclaredConstructors();
        assertEquals("State Transition Matrix class should only have one constructor",
                1, ctors.length);
        Constructor ctor = ctors[0];
        assertFalse("State Transition Matrix class constructor should be inaccessible",
                ctor.isAccessible());
        ctor.setAccessible(true);
        assertEquals("You'd expect the construct to return the expected type",
        		StateTransitionMatrix.class, ctor.newInstance().getClass());
    }

    /**
     * Test state transition matrix creation.
     */
    @Test
	public final void createContainerTest() {
    	StateTransitionMatrix.createStateTrans(eList);
    }

}
