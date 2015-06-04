grammar MyScript;

parse
    : statement* EOF
    ;

statement
    : declarationStatement
    | expressionStatement
    ;

declarationStatement
    : localVariableDeclaration ';'
    ;

expressionStatement
    : statementExpression ';'
    ;

localVariableDeclaration
    : localVariableType localVariableDeclaratorList
    ;

localVariableType
    : VAR
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
    : primaryExpression
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
    | IDENTIFIER                                # lookupExpression
    | '(' expression ')'                        # parenthesizedExpression
    | primaryExpression '.' IDENTIFIER          # memberAccessExpression        
    | primaryExpression '(' argumentList? ')'   # invocationExpression
    ;

chunkExpression
    : 'chunk' tableIdent=IDENTIFIER 'by' 'column'? columnIdent=IDENTIFIER periodSpecifier? columnAggregateOperation*
    ;

periodSpecifier
    : 'per' period
    ;

period
    : singularTimeUnit
    | NUMBER pluralTimeUnit
    ;

singularTimeUnit
    : 'day'
    | 'week'
    | 'month'
    | 'year'
    ;

pluralTimeUnit
    : 'days'
    | 'weeks'
    | 'months'
    | 'years'
    ;

constrainExpression
    : 'constrain' tableIdent=IDENTIFIER 'where' conditionalExpression
    ;

conditionalExpression
    : '(' conditionalExpression ')'
    | conditionalExpression 'and' condition
    | conditionalExpression 'or' condition
    | condition
    ;

condition
    : columnIdent=IDENTIFIER comparisonOperator expression
    ;

comparisonOperator
    : '='
    | '<'
    | '<='
    | '>'
    | '>='
    ;

columnAggregateOperation
    : ',' aggregateOperation 'of' IDENTIFIER
    ;

aggregateOperation
    : 'count'
    | 'average'
    | 'sum'
    | 'min'
    | 'max'
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
    : boolLiteral
    | numberLiteral
    | stringLiteral
    | nullLiteral
    ;

boolLiteral returns [boolean value]
    : TRUE {$value = true;}
    | FALSE {$value = false;}
    ;

numberLiteral returns [double value]
    : NUMBER {$value = Double.parseDouble($NUMBER.text);}
    ;

stringLiteral returns [String value]
    : STRING {$value = $STRING.text.substring(1, $STRING.text.length() - 1);}
    ;

nullLiteral
    : NULL
    ;

VAR: 'var';
TRUE: 'true';
FALSE: 'false';

NUMBER
    : DECIMAL_DIGITS '.' DECIMAL_DIGITS EXPONENT?
    | '.' DECIMAL_DIGITS EXPONENT?
    | DECIMAL_DIGITS EXPONENT?
    ;

STRING
    : '"' ~["\r\n]* '"'
    ;

NULL
    : 'null'
    ;

IDENTIFIER
    : [a-zA-Z0-9][a-zA-Z0-9_]*
    ;

SPACES
    : [ \t\r\n] -> skip
    ;

fragment EXPONENT: [eE][+-]DECIMAL_DIGITS ;
fragment DECIMAL_DIGITS: DECIMAL_DIGIT+ ;
fragment DECIMAL_DIGIT: [0-9] ;