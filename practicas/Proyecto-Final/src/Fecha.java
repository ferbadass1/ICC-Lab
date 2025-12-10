/**
 * Clase Modelo: Fecha
 *
 * Representa una fecha simple manejando día, mes y año como enteros.
 * Ofrece operaciones básicas de comparación y conversión, así como la
 * reconstrucción desde cadenas con formato "D-M-A" o "DD-MM-AAAA".
 *
 * Esta clase no valida si la fecha es real en el calendario (por ejemplo,
 * no verifica meses con 30/31 días ni años bisiestos). Solamente da soporte
 * a manipulación y comparación estructural.
 */
public class Fecha {

    /** Día del mes. */
    private int dia;

    /** Mes del año, en rango teórico 1–12. */
    private int mes;

    /** Año en cuatro dígitos. */
    private int anio;

    /**
     * Constructor principal de la clase.
     *
     * No realiza validaciones de calendario; solo almacena los valores dados.
     *
     * @param dia  Día del mes.
     * @param mes  Mes del año.
     * @param anio Año expresado en cuatro dígitos.
     */
    public Fecha(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    /**
     * Método Calculador: hoy
     *
     * Construye una fecha correspondiente a la fecha actual del sistema.
     *
     * @return Una nueva instancia de {@link Fecha} con día, mes y año actuales.
     */
    public static Fecha hoy() {
        java.time.LocalDate ahora = java.time.LocalDate.now();
        return new Fecha(ahora.getDayOfMonth(), ahora.getMonthValue(), ahora.getYear());
    }

    /**
     * Indica si esta fecha es estrictamente anterior a otra fecha dada.
     *
     * La comparación se realiza en orden año → mes → día.
     *
     * @param otra Fecha contra la que se compara.
     * @return true si esta fecha es anterior a {@code otra}; false otherwise.
     */
    public boolean esAnteriorA(Fecha otra) {
        if (this.anio != otra.anio) return this.anio < otra.anio;
        if (this.mes != otra.mes) return this.mes < otra.mes;
        return this.dia < otra.dia;
    }

    /**
     * Indica si esta fecha es estrictamente posterior a otra fecha dada.
     *
     * La comparación se realiza del mismo modo que en {@link #esAnteriorA(Fecha)}.
     *
     * @param otra Fecha contra la que se compara.
     * @return true si esta fecha es posterior a {@code otra}; false en caso contrario.
     */
    public boolean esPosteriorA(Fecha otra) {
        if (this.anio != otra.anio) return this.anio > otra.anio;
        if (this.mes != otra.mes) return this.mes > otra.mes;
        return this.dia > otra.dia;
    }

    /**
     * Indica si esta fecha se encuentra dentro del intervalo definido por
     * {@code inicio} y {@code fin}, de forma inclusiva.
     *
     * @param inicio Fecha de inicio (incluida).
     * @param fin    Fecha de fin (incluida).
     * @return true si esta fecha está entre {@code inicio} y {@code fin}.
     */
    public boolean esEntre(Fecha inicio, Fecha fin) {
        return !this.esAnteriorA(inicio) && !this.esPosteriorA(fin);
    }

    /**
     * Devuelve la representación textual de la fecha en formato "D-M-A".
     *
     * @return Cadena con formato d-m-a.
     */
    @Override
    public String toString() {
        return dia + "-" + mes + "-" + anio;
    }

    /**
     * Método Calculador: fromString
     *
     * Construye una fecha a partir de una cadena con formato "D-M-A" o
     * "DD-MM-AAAA". No valida coherencia de la fecha en calendario.
     *
     * @param s Cadena que contiene una fecha separada por guiones.
     * @return Nueva instancia de la clase {@link Fecha}.
     * @throws FormatoInvalidoException Si la cadena no contiene exactamente 3 partes
     *                                  o si alguno de los elementos no es numérico.
     */
    public static Fecha fromString(String s) throws FormatoInvalidoException {
        String[] partes = s.split("-");
        if (partes.length != 3) {
            throw new FormatoInvalidoException("Fecha inválida: " + s);
        }

        try {
            int d = Integer.parseInt(partes[0]);
            int m = Integer.parseInt(partes[1]);
            int a = Integer.parseInt(partes[2]);
            return new Fecha(d, m, a);
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("Fecha inválida: " + s);
        }
    }
}
