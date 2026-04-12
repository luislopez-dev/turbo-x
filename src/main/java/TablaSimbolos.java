import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tabla de simbolos del analizador lexico TurboX.
 *
 * <p>Responsabilidad principal: almacenar identificadores detectados por el
 * scanner, evitando duplicados y preservando el orden de aparicion para que
 * la salida de consola sea trazable durante la evaluacion.</p>
 *
 * @author Grupo 6 - Sección A
 * @version 1.1
 * @since 11-04-2026
 * @implNote Implementacion en memoria orientada a trazabilidad en consola.
 */
public class TablaSimbolos {

    /**
     * Registro de metadatos para cada identificador encontrado.
     *
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public static class RegistroIdentificador {
        private final String nombre;
        private final String clase;
        private String tipoDato;
        private final int primeraLinea;
        private final int primeraColumna;
        private int ultimaLinea;
        private int ultimaColumna;
        private int apariciones;

        /**
         * Crea un registro inicial con la primera aparicion del identificador.
         *
         * @param nombre lexema del identificador
         * @param linea linea de primera aparicion
         * @param columna columna de primera aparicion
         * @author Grupo 6 - Sección A
         * @since 11-04-2026
         */
        public RegistroIdentificador(String nombre, int linea, int columna) {
            this.nombre = nombre;
            this.clase = "IDENTIFICADOR";
            this.tipoDato = "NO_DECLARADO";
            this.primeraLinea = linea;
            this.primeraColumna = columna;
            this.ultimaLinea = linea;
            this.ultimaColumna = columna;
            this.apariciones = 1;
        }

        /**
         * Actualiza metadatos cuando el mismo identificador vuelve a aparecer.
         *
         * @param linea linea de la nueva aparicion
         * @param columna columna de la nueva aparicion
         * @author Grupo 6 - Sección A
         * @since 11-04-2026
         */
        public void registrarAparicion(int linea, int columna) {
            this.ultimaLinea = linea;
            this.ultimaColumna = columna;
            this.apariciones++;
        }

        /**
         * Asigna el tipo de dato cuando la aparicion pertenece a una declaracion.
         *
         * @param tipoDato tipo declarado para el identificador
         * @author Grupo 6 - Sección A
         * @since 11-04-2026
         */
        public void asignarTipoDato(String tipoDato) {
            if (tipoDato == null || tipoDato.isBlank()) {
                return;
            }
            this.tipoDato = tipoDato;
        }

        /** @author Grupo 6 - Sección A @since 11-04-2026 */
        public String getNombre() {
            return nombre;
        }

        /** @author Grupo 6 - Sección A @since 11-04-2026 */
        public String getClase() {
            return clase;
        }

        /** @author Grupo 6 - Sección A @since 11-04-2026 */
        public String getTipoDato() {
            return tipoDato;
        }

        /** @author Grupo 6 - Sección A @since 11-04-2026 */
        public int getPrimeraLinea() {
            return primeraLinea;
        }

        /** @author Grupo 6 - Sección A @since 11-04-2026 */
        public int getPrimeraColumna() {
            return primeraColumna;
        }

        /** @author Grupo 6 - Sección A @since 11-04-2026 */
        public int getUltimaLinea() {
            return ultimaLinea;
        }

        /** @author Grupo 6 - Sección A @since 11-04-2026 */
        public int getUltimaColumna() {
            return ultimaColumna;
        }

        /** @author Grupo 6 - Sección A @since 11-04-2026 */
        public int getApariciones() {
            return apariciones;
        }
    }

    /**
     * Estructura base de almacenamiento.
     *
     * <p>Clave: nombre del identificador. Valor: metadatos acumulados.
     * Se usa {@link LinkedHashMap} para mantener orden de insercion.</p>
     */
    private final Map<String, RegistroIdentificador> simbolos = new LinkedHashMap<>();

    /**
     * Registra una ocurrencia de identificador y acumula sus metadatos.
     *
     * @param nombre lexema del identificador reconocido
     * @param linea linea de la ocurrencia
     * @param columna columna de la ocurrencia
     * @param tipoDato tipo detectado para la declaracion; puede ser nulo
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public void registrarIdentificador(String nombre, int linea, int columna, String tipoDato) {
        RegistroIdentificador registro = simbolos.get(nombre);
        if (registro == null) {
            RegistroIdentificador nuevoRegistro = new RegistroIdentificador(nombre, linea, columna);
            nuevoRegistro.asignarTipoDato(tipoDato);
            simbolos.put(nombre, nuevoRegistro);
        } else {
            registro.registrarAparicion(linea, columna);
            registro.asignarTipoDato(tipoDato);
        }
    }

    /**
     * Verifica si un identificador ya fue registrado.
     *
     * @param nombre identificador a consultar
     * @return {@code true} si el identificador existe
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public boolean contiene(String nombre) {
        return simbolos.containsKey(nombre);
    }

    /**
     * Expone vista de solo lectura de la tabla para salida o inspeccion.
     *
     * @return mapa inmodificable con simbolos registrados
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public Map<String, RegistroIdentificador> getSimbolos() {
        return Collections.unmodifiableMap(simbolos);
    }

    /**
     * Determina si no se registraron identificadores durante el analisis.
     *
     * @return {@code true} si la tabla esta vacia
     * @author Grupo 6 - Sección A
     * @since 11-04-2026
     */
    public boolean estaVacia() {
        return simbolos.isEmpty();
    }
}

