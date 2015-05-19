package com.health.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class SymbolMap {
    private List<Set<Character>> charachterMap;
    private Map<String, TokenName> symbolMap;

    public SymbolMap() {
        this.charachterMap = new ArrayList<Set<Character>>();
        this.symbolMap = new HashMap<String, TokenName>();
    }

    public void insert(final TokenName symbol) {
        Objects.requireNonNull(symbol);

        if (symbol.getLexeme() == null) {
            // TODO: Exception
            throw new IllegalArgumentException("");
        }

        String lexeme = symbol.getLexeme();
        int length = lexeme.length();

        for (int i = 0; i < length; i++) {
            if (i >= this.charachterMap.size()) {
                this.charachterMap.add(new HashSet<Character>());
            }

            this.charachterMap.get(i).add(lexeme.charAt(i));
        }

        this.symbolMap.put(lexeme, symbol);
    }

    public Token tryRead(final ScriptReader reader) {
        Objects.requireNonNull(reader);

        String symbol = this.findLongestPotentialSymbol(reader);
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

    private String findLongestPotentialSymbol(final ScriptReader reader) {
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
