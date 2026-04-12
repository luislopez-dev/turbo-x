%%

/*
 * Componente: Especificacion lexico-gramatical TurboX
 * Proyecto: TurboX - Parte 1
 * Autor: Grupo 6 - Sección A
 * Version: 1.0
 * Fecha: 11-04-2026
 * Estado: Operativo para analisis lexico academico
 *
 * Proposito:
 * Definir reglas JFlex para reconocer tokens del lenguaje TurboX.
 * Esta especificacion produce la clase Lexer, que retorna instancias de Token
 * con informacion de tipo, lexema, linea y columna.
 *
 * Alcance de esta fase:
 * - Reconocimiento de tokens del lenguaje base.
 * - Deteccion de errores lexicos por simbolo no valido.
 * - Entrega de posicion exacta (linea/columna) para auditoria.
 */

%public
%class Lexer
%unicode
%line
%column
%type Token
%function nextToken

/*
 * Macros reutilizables del analizador.
 * Se mantienen separadas para facilitar mantenimiento y extension del lenguaje.
 */
ESPACIO = [ \t\r\n\f]+
LETRA = [A-Za-z_]
DIGITO = [0-9]
IDENT = {LETRA}({LETRA}|{DIGITO})*
ENTERO = {DIGITO}+
REAL = {DIGITO}+\.{DIGITO}+
CADENA = \"([^\\\"\n]|\\.)*\"
CARACTER = \'([^\\\'\n]|\\.)\'

%%

/* Regla de ignorados: no produce token, solo avanza el scanner. */
{ESPACIO}                            { /* Ignorar espacios */ }

/* Palabras reservadas de control de flujo. */
"INICIO"                             { return new Token(Token.Tipo.INICIO, yytext(), yyline + 1, yycolumn + 1); }
"FIN"                                { return new Token(Token.Tipo.FIN, yytext(), yyline + 1, yycolumn + 1); }
"SI"                                 { return new Token(Token.Tipo.SI, yytext(), yyline + 1, yycolumn + 1); }
"ENTONCES"                           { return new Token(Token.Tipo.ENTONCES, yytext(), yyline + 1, yycolumn + 1); }
"SINO"                               { return new Token(Token.Tipo.SINO, yytext(), yyline + 1, yycolumn + 1); }
"MIENTRAS"                           { return new Token(Token.Tipo.MIENTRAS, yytext(), yyline + 1, yycolumn + 1); }
"HACER"                              { return new Token(Token.Tipo.HACER, yytext(), yyline + 1, yycolumn + 1); }
"FUNCION"|"Funcion"|"funcion"      { return new Token(Token.Tipo.FUNCION, yytext(), yyline + 1, yycolumn + 1); }
"RETORNAR"|"Retornar"|"retornar"   { return new Token(Token.Tipo.RETORNAR, yytext(), yyline + 1, yycolumn + 1); }
"CLASE"|"Clase"|"clase"            { return new Token(Token.Tipo.CLASE, yytext(), yyline + 1, yycolumn + 1); }
"ATRIBUTO"|"Atributo"|"atributo"   { return new Token(Token.Tipo.ATRIBUTO, yytext(), yyline + 1, yycolumn + 1); }
"PUBLICO"|"Publico"|"publico"      { return new Token(Token.Tipo.MOD_PUBLICO, yytext(), yyline + 1, yycolumn + 1); }
"PRIVADO"|"Privado"|"privado"      { return new Token(Token.Tipo.MOD_PRIVADO, yytext(), yyline + 1, yycolumn + 1); }
"PROTEGIDO"|"Protegido"|"protegido"|"PROTECTED"|"Protected"|"protected" { return new Token(Token.Tipo.MOD_PROTEGIDO, yytext(), yyline + 1, yycolumn + 1); }

/* Tipos de datos admitidos por el lenguaje (incluye variantes de escritura). */
"Logico"|"L\u00f3gico"|"LOGICO"    { return new Token(Token.Tipo.TIPO_LOGICO, yytext(), yyline + 1, yycolumn + 1); }
"Entero"|"ENTERO"                     { return new Token(Token.Tipo.TIPO_ENTERO, yytext(), yyline + 1, yycolumn + 1); }
"Real"|"REAL"                         { return new Token(Token.Tipo.TIPO_REAL, yytext(), yyline + 1, yycolumn + 1); }
"Caracter"|"Car\u00e1cter"|"CARACTER" { return new Token(Token.Tipo.TIPO_CARACTER, yytext(), yyline + 1, yycolumn + 1); }
"Cadena"|"CADENA"                     { return new Token(Token.Tipo.TIPO_CADENA, yytext(), yyline + 1, yycolumn + 1); }

/* Delimitadores usados en declaracion y llamada de funciones. */
"("                                  { return new Token(Token.Tipo.PAREN_IZQ, yytext(), yyline + 1, yycolumn + 1); }
")"                                  { return new Token(Token.Tipo.PAREN_DER, yytext(), yyline + 1, yycolumn + 1); }
","                                  { return new Token(Token.Tipo.COMA, yytext(), yyline + 1, yycolumn + 1); }
"{"                                  { return new Token(Token.Tipo.LLAVE_IZQ, yytext(), yyline + 1, yycolumn + 1); }
"}"                                  { return new Token(Token.Tipo.LLAVE_DER, yytext(), yyline + 1, yycolumn + 1); }

/* Operadores aritmeticos. Nota: "**" debe declararse antes que "*" por prioridad. */
"**"                                 { return new Token(Token.Tipo.OP_POTENCIA_DOBLE, yytext(), yyline + 1, yycolumn + 1); }
"^"                                  { return new Token(Token.Tipo.OP_POTENCIA, yytext(), yyline + 1, yycolumn + 1); }
"+"                                  { return new Token(Token.Tipo.OP_SUMA, yytext(), yyline + 1, yycolumn + 1); }
"-"                                  { return new Token(Token.Tipo.OP_RESTA, yytext(), yyline + 1, yycolumn + 1); }
"*"                                  { return new Token(Token.Tipo.OP_MULTIPLICACION, yytext(), yyline + 1, yycolumn + 1); }
"/"                                  { return new Token(Token.Tipo.OP_DIVISION, yytext(), yyline + 1, yycolumn + 1); }

/* Operadores relacionales. */
"=="                                 { return new Token(Token.Tipo.OP_IGUAL_QUE, yytext(), yyline + 1, yycolumn + 1); }
"!="                                 { return new Token(Token.Tipo.OP_DIFERENTE, yytext(), yyline + 1, yycolumn + 1); }
"="                                  { return new Token(Token.Tipo.OP_ASIGNACION, yytext(), yyline + 1, yycolumn + 1); }
">"                                  { return new Token(Token.Tipo.OP_MAYOR, yytext(), yyline + 1, yycolumn + 1); }
"<"                                  { return new Token(Token.Tipo.OP_MENOR, yytext(), yyline + 1, yycolumn + 1); }

/* Literales numericos, de cadena y caracter. */
{REAL}                               { return new Token(Token.Tipo.LITERAL_REAL, yytext(), yyline + 1, yycolumn + 1); }
{ENTERO}                             { return new Token(Token.Tipo.LITERAL_ENTERO, yytext(), yyline + 1, yycolumn + 1); }
{CADENA}                             { return new Token(Token.Tipo.LITERAL_CADENA, yytext(), yyline + 1, yycolumn + 1); }
{CARACTER}                           { return new Token(Token.Tipo.LITERAL_CARACTER, yytext(), yyline + 1, yycolumn + 1); }

/* Identificadores definidos por patron de letra inicial + alfanumerico. */
{IDENT}                              { return new Token(Token.Tipo.IDENTIFICADOR, yytext(), yyline + 1, yycolumn + 1); }

/*
 * Regla comodin de error:
 * cualquier simbolo no reconocido por reglas previas se marca como ERROR.
 */
.                                    { return new Token(Token.Tipo.ERROR, yytext(), yyline + 1, yycolumn + 1); }

/* Marca de fin de archivo para cierre controlado del ciclo en Main. */
<<EOF>>                              { return Token.eof(yyline + 1, yycolumn + 1); }


