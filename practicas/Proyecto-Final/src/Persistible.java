/**
 * Interfaz: Persistible
 *
 * Define el comportamiento mínimo para poder guardar y cargar objetos
 * desde archivos de texto de forma sencilla.
 */
public interface Persistible {

    /**
     * Convierte el objeto en una línea de texto para persistencia.
     *
     * @return Representación en texto del objeto.
     */
    String toLineaTexto();

    /**
     * Reconstruye el estado del objeto a partir de una línea de texto.
     *
     * @param linea Cadena con los datos serializados.
     * @throws FormatoInvalidoException si la línea no tiene el formato correcto.
     */
    void fromLineaTexto(String linea) throws FormatoInvalidoException;
}
