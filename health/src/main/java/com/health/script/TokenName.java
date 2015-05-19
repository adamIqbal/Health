package com.health.script;

import java.util.Objects;

/**
 * Test.
 *
 * @author Martijn
 */
public enum TokenName {
    UNKNOWN(null, TokenType.UNKNOWN),

    IDENTIFIER(null, TokenType.IDENTIFIER),

    // Primitive types
    NUMBER(null, TokenType.NUMBER),
    STRING(null, TokenType.STRING),

    // Query keywords
    FROM("from", TokenType.KEYWORD),
    IN("in", TokenType.KEYWORD),
    WHERE("where", TokenType.KEYWORD),
    SELECT("select", TokenType.KEYWORD),
    GROUP("group", TokenType.KEYWORD),
    BY("by", TokenType.KEYWORD),
    JOIN("join", TokenType.KEYWORD),
    ON("on", TokenType.KEYWORD),

    // Aggregate keywords
    AVG("avg", TokenType.KEYWORD),
    COUNT("count", TokenType.KEYWORD),
    SUM("sum", TokenType.KEYWORD),

    // Operators
    EQUALS("=", TokenType.OPERATOR),
    EQUALS_EQUALS("==", TokenType.OPERATOR),
    BAR_BAR("||", TokenType.OPERATOR),
    AMPERSAND_AMPERSAND("&&", TokenType.OPERATOR),

    // Punctuation
    PERIOD(".", TokenType.PUNCTUATOR),
    COMMA(",", TokenType.PUNCTUATOR),
    SEMICOLON(";", TokenType.PUNCTUATOR),

    // Comments
    LINE_COMMENT(null, TokenType.COMMENT),
    BLOCK_COMMENT(null, TokenType.COMMENT);

    private final TokenType type;
    private final String lexeme;

    TokenName(final String lexeme, final TokenType type) {
        Objects.requireNonNull(type);

        this.lexeme = lexeme;
        this.type = type;
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public TokenType getType() {
        return this.type;
    }
}
