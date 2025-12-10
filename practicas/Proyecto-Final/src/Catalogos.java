/**
 * Clase Calculadora: Catalogos
 *
 * Encargada de definir los valores válidos para categorías, niveles,
 * plataformas y de ofrecer métodos de validación reutilizables.
 */
public final class Catalogos {

    private Catalogos() {}

    /** Categorías válidas de curso. */
    public static final String[] CATEGORIAS = {
        "Computación", "Matemáticas", "Física", "Química", "Biología",
        "Economía", "Deporte", "Literatura", "Filosofía", "Historia", "Otro"
    };

    /** Niveles válidos. */
    public static final String[] NIVELES = {
        "Preparatoria", "Licenciatura", "Posgrado"
    };

    /** Plataformas válidas. */
    public static final String[] PLATAFORMAS = {
        "Meet", "Zoom", "Jitsi", "Skype"
    };

    /**
     * Método Calculador: esValorValido
     *
     * Verifica si un valor de texto se encuentra en un arreglo de opciones válidas.
     *
     * @param valor   Cadena a validar.
     * @param arreglo Arreglo de opciones válidas.
     * @return true si el valor está en el arreglo, false en caso contrario.
     */
    public static boolean esValorValido(String valor, String[] arreglo) {
        if (valor == null) return false;
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i].equalsIgnoreCase(valor.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método Calculador: validarHorario
     *
     * Valida que el horario esté entre las 7:00 y 21:00 horas
     * con formato "HH:MM-HH:MM".
     *
     * @param horario Texto del horario.
     * @throws DatosInvalidosException si el formato o el rango son inválidos.
     */
    public static void validarHorario(String horario) throws DatosInvalidosException {
        if (horario == null) {
            throw new DatosInvalidosException("El horario no puede ser nulo.");
        }
        String[] partes = horario.split("-");
        if (partes.length != 2) {
            throw new DatosInvalidosException("Formato de horario inválido. Usa HH:MM-HH:MM");
        }
        int inicioHora = obtenerHora(partes[0]);
        int finHora = obtenerHora(partes[1]);

        if (inicioHora < 7 || finHora > 21 || inicioHora >= finHora) {
            throw new DatosInvalidosException(
                    "El horario debe estar entre 7:00 y 21:00 y la hora de inicio debe ser menor a la de fin.");
        }
    }

    /**
     * Método Calculador: obtenerHora
     *
     * Extrae la hora (parte entera) de un texto con formato "HH:MM".
     *
     * @param horaStr Cadena de la hora.
     * @return Hora como entero.
     * @throws DatosInvalidosException si la hora no tiene el formato correcto.
     */
    private static int obtenerHora(String horaStr) throws DatosInvalidosException {
        String[] partes = horaStr.split(":");
        if (partes.length != 2) {
            throw new DatosInvalidosException("Hora inválida: " + horaStr);
        }
        try {
            return Integer.parseInt(partes[0]);
        } catch (NumberFormatException e) {
            throw new DatosInvalidosException("Hora inválida: " + horaStr);
        }
    }
}
