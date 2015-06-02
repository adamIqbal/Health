package com.health.script;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for SymbolMap.
 */
public class SymbolMapTest {
    private SymbolMap symbols;

    @Before
    public void setUpTest() {
        symbols = new SymbolMap();
    }

    @Test(expected = NullPointerException.class)
    public void symbolMap_insert_givenTokenNameNull_throwsNullPointerException() {
        symbols.insert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void symbolMap_insert_givenTokenNameWithLexemeNull_throwsIllegalArgumentException() {
        symbols.insert(TokenName.IDENTIFIER);
    }
}
