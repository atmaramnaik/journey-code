// Our grammar is called C3PO.
grammar T;
xJson
    : xValue EOF
    ;

xValue
    : xExpression | xDynamicArray | xObj | xArray
    ;

xObj
    : '{' (xPair (',' xPair)*)? '}'
    ;

xArray
    : '[' (xValue)* ']'
    ;

xPair
    : (expression | STRING) ':' xValue
    ;


xDynamicArray
    : '<' name '>[' xValue ']</' name '>'
    ;

xExpression
    : '$<' expressionVariable '>'
    ;

json
    : (value) EOF
    ;
value
    : expression | primitive | dynamicArray | obj | array
    ;
obj
    : '{' (pair (',' pair)*)? '}'
    ;

array
    : '[' (value)* ']'
    ;

dynamicArray
    : '<' name '>[' value ']</' name '>'
    ;


expression
    : '$<' expressionValue '>'
    ;

expressionValue
    : expressionFunc | expressionVariable | primitive
    ;
expressionFunc
    : '_' name '(' (expressionValue) (',' (expressionValue)*)? ')'
    ;

expressionVariable
    : name ('|' type )?
    ;

type
    : name
    ;
name
    : IDENTIFIER
    ;

IDENTIFIER
    : [A-Za-z][A-Za-z_]*
    ;

pair
    : (expression | STRING) ':' value
    ;


primitive
    : STRING | NUMBER | bool | nullValue
    ;


bool
    : 'true' | 'false'
    ;

nullValue
    : 'null'
    ;

STRING :  '"' (ESC | ~["\\])* '"' ;
fragment ESC :   '\\' (["\\/bfnrt] | UNICODE) ;
fragment UNICODE : 'u' HEX HEX HEX HEX ;
fragment HEX : [0-9a-fA-F] ;
NUMBER
    :   '-'? INT '.' [0-9]+ EXP? // 1.35, 1.35E-9, 0.3, -4.5
    |   '-'? INT EXP             // 1e10 -3e4
    |   '-'? INT                 // -3, 45
    ;
fragment INT :   '0' | [1-9] [0-9]* ; // no leading zeros
fragment EXP :   [Ee] [+\-]? INT ; // \- since - means "range" inside [...]
WS  :   [ \t\n\r]+ -> skip ;
