grammar MyScript;

parse
    : statement* EOF
    ;

statement
    : declarationStatement
    | expressionStatement
    ;

declarationStatement
    : localVariableDeclaration
    ;

expressionStatement
    : statementExpression ';'
    ;

localVariableDeclaration
    : localVariableType localVariableDeclaratorList
    ;

localVariableType
    : 'var'
    | type
    ;

localVariableDeclaratorList
    : localVariableDeclaratorList ',' localVariableDeclarator
    | localVariableDeclarator
    ;

localVariableDeclarator
    : IDENTIFIER
    | IDENTIFIER '=' expression
    ;

statementExpression
    : primaryExpression '(' argumentList ')'
    | assignmentExpression
    ;

expression
    : nonAssignmentExpression
    | assignmentExpression
    ;

assignmentExpression
    : primaryExpression '=' expression
    ;

nonAssignmentExpression
    : primaryExpression
    | chunkExpression
    | constrainExpression
    ;

primaryExpression
    : literal                                   # literalExpression
    | IDENTIFIER                                # identifierExpression    
    | '(' expression ')'                        # parenthesizedExpression
    | primaryExpression '.' IDENTIFIER          # memberAccessExpression        
    | primaryExpression '(' argumentList ')'    # invocationExpression
    ;

chunkExpression
    : 'chunk'
    ;

constrainExpression
    : 'constrain'
    ;

argumentList
    : argumentList ',' expression
    | expression
    ;

type
    : simpleType
    | typeName
    ;

simpleType
    : 'object'
    | 'number'
    | 'bool'
    | 'string'
    ;

typeName
    : IDENTIFIER
    ;

literal
    : BOOL
    | NUMBER
    | STRING
    | NULL
    ;

IDENTIFIER
    : [a-zA-Z0-9][a-zA-Z0-9_]*
    ;

BOOL
    : 'true'
    | 'false'
    ;

NUMBER
    : DECIMAL_DIGITS '.' DECIMAL_DIGITS EXPONENT?
    | '.' DECIMAL_DIGITS EXPONENT?
    | DECIMAL_DIGITS EXPONENT?
    ;

STRING
    : '"' ~[""\r\n]* '"'
    ;

NULL
    : 'null'
    ;

fragment EXPONENT: [eE][+-] ;
fragment DECIMAL_DIGITS: DECIMAL_DIGIT+ ;
fragment DECIMAL_DIGIT: [0-9] ;