package com.health;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Chunk.
 */
public class ChunkTest {
    private Record defaultRecord;
    private Chunk defaultChunk;

    /**
     * Sets up mocks and default values used during tests.
     */
    @Before
    public void setUp() {
        this.defaultRecord = mock(Record.class);
        this.defaultChunk = new Chunk();
        this.defaultChunk.addRecord(this.defaultRecord);
    }

    /**
     * Tests whether {@link Chunk#addRecord(Record)} throws a
     * {@link NullPointerException} when given a null reference.
     */
    @Test(expected = NullPointerException.class)
    public void addRecord_givenRecordNull_throwsNullPointerException() {
        Chunk chunk = new Chunk();

        chunk.addRecord((Record) null);
    }

    /**
     * Tests whether {@link Chunk#addRecord(Record)} adds the given record when
     * given a valid record.
     */
    @Test
    public void addRecord_givenRecord_addsRecord() {
        Chunk chunk = new Chunk();

        chunk.addRecord(this.defaultRecord);

        assertThat(chunk.getRecords(), hasItem(this.defaultRecord));
    }

    /**
     * Tests whether {@link Chunk#removeRecord(Record)} removes the given record
     * when given a record that was already added.
     */
    @Test
    public void removeRecord_givenRecordExists_removesRecord() {
        Chunk chunk = this.defaultChunk;

        chunk.removeRecord(this.defaultRecord);

        assertThat(chunk.getRecords(), not(hasItem(this.defaultRecord)));
    }

    /**
     * Tests whether {@link Chunk#removeRecord(Record)} does not remove any
     * records when given a record that was not added.
     */
    @Test
    public void removeRecord_givenRecordDoesNotExist_doesNotRemoveRecords() {
        Chunk chunk = this.defaultChunk;

        chunk.removeRecord(mock(Record.class));

        assertThat(chunk.getRecords(), hasItems(this.defaultRecord));
    }

    /**
     * Tests whether {@link Chunk#removeRecord(Record)} does not remove any
     * records when given a null reference.
     */
    @Test
    public void removeRecord_givenRecordNull_doesNotRemoveRecords() {
        Chunk chunk = this.defaultChunk;

        chunk.removeRecord((Record) null);

        assertThat(chunk.getRecords(), hasItems(this.defaultRecord));
    }
}
