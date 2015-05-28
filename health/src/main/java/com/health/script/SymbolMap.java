package com.health.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a map of symbols.
 *
 * @author Martijn
 */
public final class SymbolMap {
    private List<Set<Character>> charachterMap;
    private Map<String, TokenName> symbolMap;

    /**
     * Creates an empty {@link SymbolMap}.
     */
    public SymbolMap() {
        this.charachterMap = new ArrayList<Set<Character>>();
        this.symbolMap = new HashMap<String, TokenName>();
    }

    /**
     * Adds a given symbol to this map.
     *
     * @param symbol
     *            the symbol to add.
     */
    public void insert(final TokenName symbol) {
        Objects.requireNonNull(symbol, "Argument symbol cannot be null.");

        if (symbol.getLexeme() == null) {
            throw new IllegalArgumentException("The given TokenName must have a lexeme that is not null.");
        }

        String lexeme = symbol.getLexeme();
        int length = lexeme.length();

        // For each character in the symbol, add the character at the nth
        // position to the nth characterMap
        for (int i = 0; i < length; i++) {
            // Grow the character map as necessary
            if (i >= this.charachterMap.size()) {
                this.charachterMap.add(new HashSet<Character>());
            }

            this.charachterMap.get(i).add(lexeme.charAt(i));
        }

        // Add the symbol to the symbol map
        this.symbolMap.put(lexeme, symbol);
    }

    /**
     * Tries to read any of the symbols in this symbol map from the given
     * reader.
     *
     * @param reader
     *            the reader to read from.
     * @return a token representing a symbol, or null if no symbol could be
     *         read.
     */
    public Token tryRead(final ScriptReader reader) {
        Objects.requireNonNull(reader);

        String symbol = this.readLongestPotentialSymbol(reader);
        TokenName name = this.findSymbol(reader, symbol);

        if (name != null) {
            int length = name.getLexeme().length();

            // Record the position of the first character
            int start = reader.getPosition();
            int end = start + length;

            // Skip the symbol
            reader.skip(length);

            return new Token(reader.getScript(), start, end, name);
        }

        // The token is not a symbol, return null
        return null;
    }

    /**
     * Reads from the given reader as long as any symbol contains the a
     * character at the same location as the reader.
     *
     * @param reader
     *            the reader to read from.
     * @return a string representing the longest possible symbol that could be
     *         read from the reader.
     */
    private String readLongestPotentialSymbol(final ScriptReader reader) {
        int length = 0;
        int maxLength = this.charachterMap.size();

        StringBuilder string = new StringBuilder();

        // Read until the length of the longest symbol
        while (length < maxLength) {
            int ch = reader.peek(length);
            // As long as any symbol contains the given character at the same
            // location, continue reading
            if (ch == -1 || !this.charachterMap.get(length).contains((char) ch)) {
                break;
            }

            string.append((char) ch);
            length++;
        }

        return string.toString();
    }

    /**
     * Finds the largest symbol in a given string.
     *
     * @param reader
     *            the reader to read from.
     * @param candidateSymbol
     *            a string.
     * @return the largest symbol in a given string, or null if a symbol could
     *         not be found.
     */
    private TokenName findSymbol(final ScriptReader reader,
            final String candidateSymbol) {
        String symbol = candidateSymbol;
        int length = symbol.length();

        // While the length of the possible symbol is greater than zero
        while (length > 0) {
            // See if the symbolMap contains a rule for this string
            TokenName name = this.symbolMap.get(symbol);

            if (name != null) {
                return name;
            }

            symbol = symbol.substring(0, --length);
        }

        return null;
    }
}
