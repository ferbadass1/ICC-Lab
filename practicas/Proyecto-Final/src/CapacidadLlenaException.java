/**
 * Excepción: CapacidadLlenaException
 *
 * Se lanza cuando un repositorio basado en arreglos ya no tiene espacio.
 */
public class CapacidadLlenaException extends Exception {

    /**
     * Constructor de la excepción.
     *
     * @param mensaje Descripción legible del error.
     */
    public CapacidadLlenaException(String mensaje) {
        super(mensaje);
    }
}