package com.health.script;

/**
 * Represents the type of a token.
 */
public enum TokenType {
    /**
     * Represents an unknown type.
     */
    UNKNOWN,

    /**
     * Represents an identifier.
     */
    IDENTIFIER,

    /**
     * Represents a number literal.
     */
    NUMBER_LITERAL,

    /**
     * Represents a number literal.
     */
    STRING_LITERAL,

    /**
     * Represents a null literal.
     */
    NULL_LITERAL,

    /**
     * Represents a boolean literal.
     */
    BOOL_LITERAL,

    /**
     * Represents a keyword.
     */
    KEYWORD,

    /**
     * Represents an operator.
     */
    OPERATOR,

    /**
     * Represents a punctuator.
     */
    PUNCTUATOR,

    /**
     * Represents a comment.
     */
    COMMENT,

    /**
     * Represents a built-in type.
     */
    TYPE
}
