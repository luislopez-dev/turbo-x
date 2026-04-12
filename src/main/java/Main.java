import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Punto de entrada del analizador lexico TurboX.
 *
 * <p>Este componente coordina el ciclo completo de ejecucion en consola:
 * valida archivo de entrada, inicializa el lexer generado por JFlex,
 * recorre token por token hasta EOF, reporta errores lexicos con posicion
 * y finalmente imprime la tabla de simbolos acumulada.</p>
 *
 * <p>Contexto: entrega de la Parte 1 del proyecto TurboX para el estudio
 * academico de compiladores.</p>
 *
 * @author Grupo 6 - Sección A
 * @version 1.1
 * @since 11-04-2026
 * @implNote Esta clase centraliza el flujo de prueba en consola del scanner.
 */
public class Main {

    /**
     * Convierte el tipo de token lexico a etiqueta de tipo de dato legible.
     *
     * @param tipoToken categoria del token evaluado
     * @return etiqueta del tipo de dato o {@code null} si no es token de tipo
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    private static String extraerTipoDato(Token.Tipo tipoToken) {
        return switch (tipoToken) {
            case TIPO_LOGICO -> "LOGICO";
            case TIPO_ENTERO -> "ENTERO";
            case TIPO_REAL -> "REAL";
            case TIPO_CARACTER -> "CARACTER";
            case TIPO_CADENA -> "CADENA";
            default -> null;
        };
    }


    /**
     * Ejecuta el flujo principal del scanner.
     *
     * <p>Parametros de ejecucion:</p>
     * <ul>
     *   <li>args[0] (opcional): ruta al archivo de codigo fuente.</li>
     *   <li>si no se proporciona, usa {@code src/main/resources/entrada.txt}.</li>
     * </ul>
     *
     * <p>Salida esperada:</p>
     * <ul>
     *   <li>lista de tokens reconocidos, uno por linea;</li>
     *   <li>errores lexicos con linea y columna;</li>
     *   <li>tabla de simbolos final con identificadores unicos.</li>
     * </ul>
     *
     * @param args argumentos de linea de comandos
     * @throws IOException si ocurre un fallo al abrir o leer el archivo fuente
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public static void main(String[] args) throws IOException {
        // Ruta de entrada configurable para pruebas y ejecuciones automatizadas.
        String rutaEntrada = args.length > 0 ? args[0] : "src/main/resources/entrada.txt";
        Path archivo = Path.of(rutaEntrada);

        // Validacion temprana para evitar inicializar el lexer con una ruta invalida.
        if (!Files.exists(archivo)) {
            System.err.println("No se encontro el archivo de entrada: " + archivo.toAbsolutePath());
            return;
        }

        // Estructura de soporte para registrar identificadores durante el escaneo.
        TablaSimbolos tablaSimbolos = new TablaSimbolos();

        // Reader en try-with-resources para garantizar liberacion de recursos.
        try (Reader reader = Files.newBufferedReader(archivo)) {
            Lexer lexer = new Lexer(reader);
            Token token;
            String tipoDatoPendiente = null;

            System.out.println("=== TOKENS RECONOCIDOS ===");
            do {
                // nextToken() recorre secuencialmente el flujo de entrada.
                token = lexer.nextToken();
                System.out.println(token);

                // Si se detecta una palabra de tipo, se marca para el siguiente identificador.
                String tipoDetectado = extraerTipoDato(token.getTipo());
                if (tipoDetectado != null) {
                    tipoDatoPendiente = tipoDetectado;
                }

                // Solo los identificadores se registran en la tabla de simbolos.
                if (token.getTipo() == Token.Tipo.IDENTIFICADOR) {
                    tablaSimbolos.registrarIdentificador(
                            token.getLexema(),
                            token.getLinea(),
                            token.getColumna(),
                            tipoDatoPendiente
                    );

                    // El tipo pendiente se consume al asociarse con el identificador inmediato.
                    tipoDatoPendiente = null;
                }

                // Regla de reporte de errores lexicos con ubicacion exacta.
                if (token.esError()) {
                    System.err.println("Error lexico detectado en linea "
                            + token.getLinea() + ", columna " + token.getColumna()
                            + ": simbolo no reconocido '" + token.getLexema() + "'");
                }
                // Fin de lectura al recibir token EOF emitido por el lexer.
            } while (token.getTipo() != Token.Tipo.EOF);
        }

        // Presentacion final de simbolos recolectados durante el analisis.
        System.out.println("\n=== TABLA DE SIMBOLOS ===");
        if (tablaSimbolos.estaVacia()) {
            System.out.println("(Sin identificadores)");
        } else {
            tablaSimbolos.getSimbolos().forEach((nombre, registro) ->
                    System.out.println("NOMBRE: " + nombre
                            + ", CLASE: " + registro.getClase()
                            + ", TIPO_DATO: " + registro.getTipoDato()
                            + ", APARICIONES: " + registro.getApariciones()
                            + ", PRIMERA_POSICION: (L" + registro.getPrimeraLinea()
                            + ", C" + registro.getPrimeraColumna() + ")"
                            + ", ULTIMA_POSICION: (L" + registro.getUltimaLinea()
                            + ", C" + registro.getUltimaColumna() + ")"));
        }
    }
}

