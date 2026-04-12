/**
 * Modelo inmutable de token lexico.
 *
 * <p>Un token representa una unidad reconocida por el scanner:
 * categoria, lexema literal y posicion (linea/columna) dentro del archivo
 * fuente original.</p>
 *
 * @author Grupo 6 - Sección A
 * @version 1.0
 * @since 11-04-2026
 * @implNote Objeto de transferencia utilizado por el lexer y la salida de consola.
 */
public class Token {

    /**
     * Catalogo de categorias lexicas soportadas por TurboX.
     *
     * <p>Estas categorias se mantienen sincronizadas con las reglas de
     * {@code Lexer.flex} y se usan tanto para salida de consola como para
     * decisiones de procesamiento (por ejemplo, registro en tabla de simbolos).</p>
     *
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public enum Tipo {
        INICIO,
        FIN,
        SI,
        ENTONCES,
        SINO,
        MIENTRAS,
        HACER,
        FUNCION,
        RETORNAR,
        CLASE,
        ATRIBUTO,
        MOD_PUBLICO,
        MOD_PRIVADO,
        MOD_PROTEGIDO,
        TIPO_LOGICO,
        TIPO_ENTERO,
        TIPO_REAL,
        TIPO_CARACTER,
        TIPO_CADENA,
        PAREN_IZQ,
        PAREN_DER,
        COMA,
        LLAVE_IZQ,
        LLAVE_DER,
        OP_SUMA,
        OP_RESTA,
        OP_MULTIPLICACION,
        OP_DIVISION,
        OP_POTENCIA,
        OP_POTENCIA_DOBLE,
        OP_MAYOR,
        OP_MENOR,
        OP_IGUAL_QUE,
        OP_DIFERENTE,
        OP_ASIGNACION,
        IDENTIFICADOR,
        LITERAL_ENTERO,
        LITERAL_REAL,
        LITERAL_CADENA,
        LITERAL_CARACTER,
        ERROR,
        EOF
    }

    private final Tipo tipo;
    private final String lexema;
    private final int linea;
    private final int columna;

    /**
     * Construye una instancia de token con posicion absoluta en el archivo.
     *
     * @param tipo categoria lexica del token
     * @param lexema texto exacto reconocido por el lexer
     * @param linea linea de inicio (base 1)
     * @param columna columna de inicio (base 1)
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public Token(Tipo tipo, String lexema, int linea, int columna) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;
    }

    /**
     * Fabrica token de fin de archivo.
     *
     * <p>Se emite cuando el scanner consume completamente la entrada.
     * Permite cerrar el bucle de lectura sin usar valores nulos.</p>
     *
     * @param linea ultima linea conocida
     * @param columna ultima columna conocida
     * @return token de tipo {@link Tipo#EOF}
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public static Token eof(int linea, int columna) {
        return new Token(Tipo.EOF, "<EOF>", linea, columna);
    }

    /**
     * Obtiene la categoria lexica del token.
     *
     * @return categoria lexica del token
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Obtiene el lexema exacto reconocido por el scanner.
     *
     * @return lexema exacto reconocido
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public String getLexema() {
        return lexema;
    }

    /**
     * Obtiene la linea de inicio del token.
     *
     * @return linea de inicio (base 1)
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public int getLinea() {
        return linea;
    }

    /**
     * Obtiene la columna de inicio del token.
     *
     * @return columna de inicio (base 1)
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Determina si el token representa un error lexico.
     *
     * @return {@code true} cuando la categoria es {@link Tipo#ERROR}
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public boolean esError() {
        return tipo == Tipo.ERROR;
    }

    /**
     * Representacion textual estandar para auditoria y pruebas manuales.
     *
     * @return cadena legible con tipo, valor y posicion
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    @Override
    public String toString() {
        return "TOKEN: " + tipo
                + ", VALOR: " + lexema
                + ", LINEA: " + linea
                + ", COLUMNA: " + columna;
    }
}

