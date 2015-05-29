package com.health.script;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit test for MyScriptScanner (and SymbolMap).
 */
@RunWith(Parameterized.class)
public class ScannerTest {
    private Scanner scanner;
    private final String script;
    private final TokenName[] expectedTokens;

    public ScannerTest(final String script, final TokenName[] expectedTokens) {
        this.script = script;
        this.expectedTokens = expectedTokens;
    }

    @Before
    public void setUpTest() {
        scanner = new MyScriptScanner();
    }

    @Test
    public void scan_givenScript_returnsTokensWithCorrectNames() {
        assertTokenNamesEqual(scan(script), expectedTokens);
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
                // Empty
                { "", names() },
                // Identifiers
                { "ident", names(TokenName.IDENTIFIER) },
                { "Ident", names(TokenName.IDENTIFIER) },
                { "IDENT", names(TokenName.IDENTIFIER) },
                { "id3nt", names(TokenName.IDENTIFIER) },
                { "_ident", names(TokenName.IDENTIFIER) },
                { "id_ent", names(TokenName.IDENTIFIER) },
                { "id_3nt", names(TokenName.IDENTIFIER) },
                { " whitespace", names(TokenName.IDENTIFIER) },
                // Various keywords
                { "var", names(TokenName.VAR) },
                { "object", names(TokenName.OBJECT) },
                { "number", names(TokenName.NUMBER) },
                { "bool", names(TokenName.BOOL) },
                { "string", names(TokenName.STRING) },
                { "null", names(TokenName.NULL) },
                { "true", names(TokenName.TRUE) },
                { "false", names(TokenName.FALSE) },
                // Numbers
                { "1", names(TokenName.NUMBER_LITERAL) },
                { "0.5", names(TokenName.NUMBER_LITERAL) },
                { ".5", names(TokenName.NUMBER_LITERAL) },
                { "1e5", names(TokenName.NUMBER_LITERAL) },
                { "2e+5", names(TokenName.NUMBER_LITERAL) },
                { "2e-5", names(TokenName.NUMBER_LITERAL) },
                { ".2e1", names(TokenName.NUMBER_LITERAL) },
                { "0.2e+2", names(TokenName.NUMBER_LITERAL) },
                // Strings
                { "\"\"", names(TokenName.STRING_LITERAL) },
                { "\"\\'\"", names(TokenName.STRING_LITERAL) },
                { "\"\\\"\"", names(TokenName.STRING_LITERAL) },
                { "\"\\\\\"", names(TokenName.STRING_LITERAL) },
                { "\"\\0\"", names(TokenName.STRING_LITERAL) },
                { "\"\\b\"", names(TokenName.STRING_LITERAL) },
                { "\"\\f\"", names(TokenName.STRING_LITERAL) },
                { "\"\\n\"", names(TokenName.STRING_LITERAL) },
                { "\"\\r\"", names(TokenName.STRING_LITERAL) },
                { "\"\\t\"", names(TokenName.STRING_LITERAL) },
                { "\"string\"", names(TokenName.STRING_LITERAL) },
                // Invalid strings
                { "\"", names(TokenName.STRING_LITERAL) },
                { "\"\n\"", names(TokenName.STRING_LITERAL, TokenName.STRING_LITERAL) },
                { "\"\\p\"", names(TokenName.STRING_LITERAL) },
                // Line comments
                { "//", names(TokenName.LINE_COMMENT) },
                { "//ident", names(TokenName.LINE_COMMENT) },
                { "//one two", names(TokenName.LINE_COMMENT) },
                { "// \"comment\"", names(TokenName.LINE_COMMENT) },
                { "//\nident", names(TokenName.LINE_COMMENT, TokenName.IDENTIFIER) },
                // Block comments
                { "/**/", names(TokenName.BLOCK_COMMENT) },
                { "/***/", names(TokenName.BLOCK_COMMENT) },
                { "/*ident*/", names(TokenName.BLOCK_COMMENT) },
                { "/*one//two*/", names(TokenName.BLOCK_COMMENT) },
                { "/*\"comment*\"", names(TokenName.BLOCK_COMMENT) },
                { "/*\n*/", names(TokenName.BLOCK_COMMENT) },
                { "/**/ident", names(TokenName.BLOCK_COMMENT, TokenName.IDENTIFIER) },
                // Symbols
                { ".", names(TokenName.PERIOD) },
                { "=", names(TokenName.EQUALS) },
                { "==", names(TokenName.EQUALS_EQUALS) },
                { ")=", names(TokenName.CLOSE_PARENTHESIS, TokenName.EQUALS) },
                // Unknown
                { "#", names(TokenName.UNKNOWN) },
                { "/", names(TokenName.UNKNOWN) },
        });
    }

    private List<Token> scan(final String script) {
        ScriptReader reader = new ScriptReader(new Script(script));

        return scanner.scan(reader);
    }

    private static TokenName[] names(final TokenName... names) {
        return names;
    }

    /**
     * Asserts whether the names of the given tokens match the expected names.
     *
     * @param actualTokens
     *            the tokens.
     * @param expectedNames
     *            the expected names.
     */
    private static void assertTokenNamesEqual(final List<Token> actualTokens, final TokenName... expectedNames) {
        TokenName[] actualNames = new TokenName[actualTokens.size()];

        for (int i = 0; i < actualTokens.size(); i++) {
            actualNames[i] = actualTokens.get(i).getName();
        }

        Assert.assertArrayEquals(expectedNames, actualNames);
    }
}
