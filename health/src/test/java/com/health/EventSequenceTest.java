package com.health;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.health.operations.Connect;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EventList.class, Table.class, Connect.class })
public class EventSequenceTest {
    private final static String[] codePattern = { "A", "B" };
    private final static boolean isConnected = true;
    private final static String code = "AB";
    private EventSequence sequence;

    @Before
    public void setUp() {
        sequence = new EventSequence(codePattern, code, isConnected);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_givenPatternNull_throwsNullPointerException() {
        new EventSequence(null);
    }

    @Test
    public void constructor_setsIsConnectect() {
        EventSequence seq = new EventSequence(codePattern, isConnected);

        assertEquals(isConnected, seq.isConnected());
    }

    @Test
    public void constructor_setsCode() {
        EventSequence seq = new EventSequence(codePattern, code, isConnected);

        assertEquals(code, seq.getCode());
    }

    @Test
    public void constructor_setsCodePattern() {
        EventSequence seq = new EventSequence(codePattern, code, isConnected);

        assertThat(seq.getCodePattern(), arrayContaining(codePattern));
    }

    @Test
    public void constructor_initializesGetSequences() {
        EventSequence seq = new EventSequence(codePattern);

        assertNotNull(seq.getSequences());
    }

    @Test
    public void testAddSequence() {
        EventList eventList = new EventList();

        sequence.addSequence(eventList);

        assertThat(sequence.getSequences(), hasItem(eventList));
    }

    @Test
    public void testRemoveSequence() {
        EventList eventList = new EventList();

        sequence.addSequence(eventList);
        sequence.removeSequence(eventList);

        assertThat(sequence.getSequences(), empty());
    }

    @Test
    public void testToTable() {
        mockStatic(Connect.class);

        EventList eventList1 = mock(EventList.class);
        EventList eventList2 = mock(EventList.class);

        Table table1 = mock(Table.class);
        Table table2 = mock(Table.class);
        Table table3 = mock(Table.class);

        when(eventList1.toTable()).thenReturn(table1);
        when(eventList2.toTable()).thenReturn(table2);

        when(Connect.connect(table1, table2, null)).thenReturn(table3);

        sequence.addSequence(eventList1);
        sequence.addSequence(eventList2);

        assertSame(table3, sequence.toTable());
    }
}
