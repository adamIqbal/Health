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
    | connectExpression
    | codeExpression
    ;

primaryExpression
    : literal                                   # literalExpression
    | IDENTIFIER                                # lookupExpression
    | '(' expression ')'                        # parenthesizedExpression
    | primaryExpression '.' IDENTIFIER          # memberAccessExpression        
    | primaryExpression '(' argumentList? ')'   # invocationExpression
    ;

chunkExpression
    : 'chunk' table=IDENTIFIER 'by' column=IDENTIFIER periodSpecifier? (','? 'select' chunkSelectionList)?
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

chunkSelectionList
    : chunkSelectionList ',' columnOrAggregateOperation
    | columnOrAggregateOperation
    ;

columnOrAggregateOperation
    : (aggregateOperation 'of')? column=IDENTIFIER
    ;

aggregateOperation
    : 'count'
    | 'average'
    | 'sum'
    | 'min'
    | 'max'
    ;

constrainExpression
    : 'constrain' table=IDENTIFIER 'where' conditionalExpression
    ;

conditionalExpression
    : '(' conditionalExpression ')'
    | conditionalExpression booleanOperator condition
    | condition
    ;

booleanOperator
    : 'and'
    | 'or'
    ;

condition
    : column=IDENTIFIER comparisonOperator expression
    ;

comparisonOperator
    : '=='
    | '='
    | '!='
    | '<'
    | '<='
    | '>'
    | '>='
    ;

connectExpression
    : 'connect' table1=IDENTIFIER 'with' table2=IDENTIFIER 'where' columnConnectionList
    ;

columnConnectionList
    : columnConnectionList 'and' columnConnection
    | columnConnection
    ;

columnConnection
    : column1=IDENTIFIER ('=' | '==') column2=IDENTIFIER ('as' newName=IDENTIFIER)?
    ;

codeExpression
    : 'code' table=IDENTIFIER 'as' codeList
    ;

codeList
    : codeList ',' code
    | code
    ;

code
    : IDENTIFIER ':' conditionalExpression
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

COMMENT
    : '//' ~[\r\n]* -> skip
    ;

WHITESPACE
    : [ \r\t\u000C'\n]+ -> skip
    ;

fragment EXPONENT: [eE][+-]DECIMAL_DIGITS ;
fragment DECIMAL_DIGITS: DECIMAL_DIGIT+ ;
fragment DECIMAL_DIGIT: [0-9] ;