/**
 * Interfaz Persistible
 *
 * Se utiliza en el sistema para almacenar objetos en archivos
 * y recuperarlos posteriormente.
 */
public interface Persistible {

    /**
     * Convierte el objeto en una línea de texto lista para guardar
     * en un archivo. El formato exacto depende de la clase que lo implemente.
     *
     * @return Cadena que representa al objeto de forma serializada.
     */
    String toLineaTexto();

    /**
     * Reconstruye el objeto a partir de una cadena generada previamente
     * por {#toLineaTexto()}. Debe validar que la estructura del texto
     * sea la adecuada para el tipo de objeto.
     *
     * @param linea Texto con los datos del objeto.
     * @throws FormatoInvalidoException Si el formato no coincide o la información
     *                                  no puede interpretarse correctamente.
     */
    void fromLineaTexto(String linea) throws FormatoInvalidoException;
}
