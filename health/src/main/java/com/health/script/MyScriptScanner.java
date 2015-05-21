package com.health.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO.
 *
 * @author Martijn
 */
public final class MyScriptScanner implements Scanner {
    private Map<String, TokenName> keywordMap;
    private SymbolMap symbolMap;

    public MyScriptScanner() {
        this.keywordMap = new HashMap<String, TokenName>();
        this.symbolMap = new SymbolMap();

        for (TokenName token : TokenName.values()) {
            if (token.getType() == TokenType.KEYWORD
                    || token.getType() == TokenType.TYPE) {
                this.keywordMap.put(token.getLexeme(), token);
            } else if (token.getType() == TokenType.OPERATOR
                    || token.getType() == TokenType.PUNCTUATOR) {
                this.symbolMap.insert(token);
            }
        }
    }

    @Override
    public List<Token> scan(final ScriptReader reader) {
        List<Token> tokens = new ArrayList<Token>();

        while (true) {
            Token next = readNext(reader);

            if (next == null) {
                break;
            }

            tokens.add(next);
        }

        return tokens;
    }

    private void skipWhitespace(final ScriptReader reader) {
        int ch = reader.peek();

        while (ch != -1 && Character.isWhitespace((char) ch)) {
            reader.skip(1);

            ch = reader.peek();
        }
    }

    private Token readNext(final ScriptReader reader) {
        skipWhitespace(reader);

        if (reader.peek() == -1) {
            return null;
        }

        char next = (char) reader.peek();

        if (next == '_' || Character.isLetter(next)) {
            return readIdentifierOrKeyword(reader);
        } else if (Character.isDigit(next)
                || (next == '.' && Character.isDigit(reader.peek(1)))) {
            return readNumberLiteral(reader);
        } else if (next == '"') {
            return readStringLiteral(reader);
        } else if (next == '/' && reader.peek(1) == '/') {
            return readLineComment(reader);
        } else if (next == '/' && reader.peek(1) == '*') {
            return readBlockComment(reader);
        }

        Token symbol = this.symbolMap.tryRead(reader);

        if (symbol != null) {
            return symbol;
        }

        return readUnknown(reader);
    }

    private Token readUnknown(final ScriptReader reader) {
        // TODO Auto-generated method stub
        return null;
    }

    private Token readIdentifierOrKeyword(final ScriptReader reader) {
        Token identifier = readIdentifier(reader);

        TokenName keyword = this.keywordMap.get(identifier.getLexeme());

        if (keyword != null) {
            return new Token(
                    identifier.getScript(),
                    identifier.getStart(),
                    identifier.getEnd(),
                    keyword);
        } else {
            return identifier;
        }
    }

    private static Token readIdentifier(final ScriptReader reader) {
        assert reader != null;
        assert reader.peek() == '_' || Character.isLetter((char) reader.peek());

        int start = reader.getPosition();

        while (true) {
            char next = (char) reader.peek();

            if (next == '_' || Character.isLetterOrDigit(next)) {
                reader.skip(1);
            } else {
                break;
            }
        }

        int end = reader.getPosition();

        return new Token(reader.getScript(), start, end, TokenName.IDENTIFIER);
    }

    private static Token readNumberLiteral(final ScriptReader reader) {
        assert reader != null;
        assert Character.isDigit((char) reader.peek())
                || reader.peek() == '.'
                && Character.isDigit((char) reader.peek(1));

        int start = reader.getPosition();

        // Read digit sequence
        while (Character.isDigit((char) reader.peek())) {
            reader.skip(1);
        }

        // Read decimal separator
        if ((char) reader.peek() == '.') {
            // Read .
            reader.skip(1);

            // Read digit sequence
            while (Character.isDigit((char) reader.peek())) {
                reader.skip(1);
            }
        }

        // Read exponent part
        if (Character.toLowerCase((char) reader.peek()) == 'e') {
            // Read e
            reader.skip(1);

            // Read exponent sign
            if ((char) reader.peek() == '+' || (char) reader.peek() == '-') {
                reader.skip(1);
            }

            // Read digit sequence
            while (Character.isDigit((char) reader.peek())) {
                reader.skip(1);
            }
        }

        int end = reader.getPosition();

        return new Token(reader.getScript(), start, end,
                TokenName.NUMBER_LITERAL);
    }

    private static Token readStringLiteral(final ScriptReader reader) {
        assert reader != null;
        assert reader.peek() == '"';

        StringBuilder string = new StringBuilder();

        int start = reader.getPosition();
        int end;

        // Skip the "
        reader.skip(1);

        while (true) {
            int next = reader.peek();

            if (next == -1 || next == '\n' || next == '"') {
                reader.skip(1);

                end = reader.getPosition();

                break;
            }

            if (next == '\\') {
                int escaped = readEscapedCharacter(reader);

                if (escaped == -1) {
                    // TODO: Add diagnostic
                    string.append((char) escaped);
                } else {
                    string.append((char) escaped);
                }
            } else {
                string.append((char) reader.read());
            }
        }

        return new Token(reader.getScript(), start, end,
                TokenName.STRING_LITERAL, string.toString());
    }

    private static int readEscapedCharacter(final ScriptReader reader) {
        assert reader != null;
        assert reader.peek() == '\\';

        reader.skip(1);

        switch (reader.peek()) {
        case '\'':
            reader.skip(1);
            return '\'';
        case '"':
            reader.skip(1);
            return '\"';
        case '\\':
            reader.skip(1);
            return '\\';
        case '0':
            reader.skip(1);
            return '\0';
        case 'b':
            reader.skip(1);
            return '\b';
        case 'f':
            reader.skip(1);
            return '\f';
        case 'n':
            reader.skip(1);
            return '\n';
        case 'r':
            reader.skip(1);
            return '\r';
        case 't':
            reader.skip(1);
            return '\t';
        default:
            return -1;
        }
    }

    private static Token readLineComment(final ScriptReader reader) {
        assert reader != null;
        assert reader.peek() == '/' && reader.peek(1) == '/';

        int start = reader.getPosition();

        // Skip the //
        reader.skip(2);

        while (true) {
            int next = reader.peek();

            if (next == -1 || next == '\n') {
                break;
            }

            reader.skip(1);
        }

        int end = reader.getPosition();

        return new Token(reader.getScript(), start, end, TokenName.LINE_COMMENT);
    }

    private static Token readBlockComment(final ScriptReader reader) {
        assert reader != null;
        assert reader.peek() == '/' && reader.peek(1) == '*';

        int start = reader.getPosition();
        int prev = '\0';

        // Skip the /*
        reader.skip(2);

        while (true) {
            int next = reader.peek();

            if (next == -1 || next == '\n') {
                break;
            }

            reader.skip(1);

            if (prev == '*' && next == '/') {
                break;
            }

            prev = next;
        }

        int end = reader.getPosition();

        return new Token(reader.getScript(), start, end,
                TokenName.BLOCK_COMMENT);
    }
}
