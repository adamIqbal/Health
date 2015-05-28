package com.health.script;

import java.util.Objects;

/**
 * Represents the name of a token.
 */
public enum TokenName {
    /**
     * Represents an unknown token.
     */
    UNKNOWN(null, TokenType.UNKNOWN),

    /**
     * Represents an identifier.
     */
    IDENTIFIER(null, TokenType.IDENTIFIER),

    // Literals
    /**
     * Represents a <code>number</code> literal.
     */
    NUMBER_LITERAL(null, TokenType.NUMBER_LITERAL),
    /**
     * Represents a <code>string</code> literal.
     */
    STRING_LITERAL(null, TokenType.STRING_LITERAL),
    /**
     * Represents a <code>null</code> literal.
     */
    NULL("null", TokenType.NULL_LITERAL),
    /**
     * Represents a <code>boolean</code> literal.
     */
    TRUE("true", TokenType.BOOL_LITERAL),
    /**
     * Represents a <code>boolean</code> literal.
     */
    FALSE("false", TokenType.BOOL_LITERAL),

    // Primitive types
    /**
     * Represents the <code>var</code> keyword.
     */
    VAR("var", TokenType.KEYWORD),
    /**
     * Represents the <code>object</code> type.
     */
    OBJECT("object", TokenType.TYPE),
    /**
     * Represents the <code>bool</code> type.
     */
    BOOL("bool", TokenType.TYPE),
    /**
     * Represents the <code>number</code> type.
     */
    NUMBER("number", TokenType.TYPE),
    /**
     * Represents the <code>string</code> type.
     */
    STRING("string", TokenType.TYPE),

    // Keywords
    /**
     * Represents the <code>new</code> keyword.
     */
    NEW("new", TokenType.KEYWORD),

    // Query keywords
    /**
     * Represents the <code>from</code> keyword.
     */
    FROM("from", TokenType.KEYWORD),
    /**
     * Represents the <code>in</code> keyword.
     */
    IN("in", TokenType.KEYWORD),
    /**
     * Represents the <code>where</code> keyword.
     */
    WHERE("where", TokenType.KEYWORD),
    /**
     * Represents the <code>select</code> keyword.
     */
    SELECT("select", TokenType.KEYWORD),
    /**
     * Represents the <code>group</code> keyword.
     */
    GROUP("group", TokenType.KEYWORD),
    /**
     * Represents the <code>by</code> keyword.
     */
    BY("by", TokenType.KEYWORD),
    /**
     * Represents the <code>join</code> keyword.
     */
    JOIN("join", TokenType.KEYWORD),
    /**
     * Represents the <code>on</code> keyword.
     */
    ON("on", TokenType.KEYWORD),
    /**
     * Represents the <code>into</code> keyword.
     */
    INTO("into", TokenType.KEYWORD),
    /**
     * Represents the <code>orderby</code> keyword.
     */
    ORDERBY("orderby", TokenType.KEYWORD),
    /**
     * Represents the <code>ascending</code> keyword.
     */
    ASCENDING("ascending", TokenType.KEYWORD),
    /**
     * Represents the <code>descending</code> keyword.
     */
    DESCENDING("descending", TokenType.KEYWORD),
    /**
     * Represents the <code>let</code> keyword.
     */
    LET("let", TokenType.KEYWORD),

    // Aggregate keywords
    /**
     * Represents the <code>avg</code> keyword.
     */
    AVG("avg", TokenType.KEYWORD),
    /**
     * Represents the <code>count</code> keyword.
     */
    COUNT("count", TokenType.KEYWORD),
    /**
     * Represents the <code>sum</code> keyword.
     */
    SUM("sum", TokenType.KEYWORD),

    // Operators
    /**
     * Represents the <code>=</code> operator.
     */
    EQUALS("=", TokenType.OPERATOR),
    /**
     * Represents the <code>==</code> operator.
     */
    EQUALS_EQUALS("==", TokenType.OPERATOR),
    /**
     * Represents the <code>||</code> operator.
     */
    BAR_BAR("||", TokenType.OPERATOR),
    /**
     * Represents the <code>&amp;&amp;</code> operator.
     */
    AMPERSAND_AMPERSAND("&&", TokenType.OPERATOR),

    // Punctuation
    /**
     * Represents the <code>&#123;</code> punctuator.
     */
    OPEN_BRACE("{", TokenType.PUNCTUATOR),
    /**
     * Represents the <code>&#125;</code> punctuator.
     */
    CLOSE_BRACE("}", TokenType.PUNCTUATOR),
    /**
     * Represents the <code>(</code> punctuator.
     */
    OPEN_PARENTHESIS("(", TokenType.PUNCTUATOR),
    /**
     * Represents the <code>)</code> punctuator.
     */
    CLOSE_PARENTHESIS(")", TokenType.PUNCTUATOR),
    /**
     * Represents the <code>.</code> punctuator.
     */
    PERIOD(".", TokenType.PUNCTUATOR),
    /**
     * Represents the <code>,</code> punctuator.
     */
    COMMA(",", TokenType.PUNCTUATOR),
    /**
     * Represents the <code>;</code> punctuator.
     */
    SEMICOLON(";", TokenType.PUNCTUATOR),

    // Comments
    /**
     * Represents a line comment.
     */
    LINE_COMMENT(null, TokenType.COMMENT),
    /**
     * Represents a block comment.
     */
    BLOCK_COMMENT(null, TokenType.COMMENT);

    private final TokenType type;
    private final String lexeme;

    TokenName(final String lexeme, final TokenType type) {
        Objects.requireNonNull(type);

        this.lexeme = lexeme;
        this.type = type;
    }

    /**
     * Gets the lexeme corresponding to this token name.
     *
     * @return the lexeme corresponding to this token name, or null if there is
     *         none.
     */
    public String getLexeme() {
        return this.lexeme;
    }

    /**
     * Gets the type corresponding to this token name.
     *
     * @return the type corresponding to this token name.
     */
    public TokenType getType() {
        return this.type;
    }
}
