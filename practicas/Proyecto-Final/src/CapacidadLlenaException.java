/**
 * Excepción: CapacidadLlenaException
 *
 * Se lanza cuando un repositorio basado en arreglos ya no tiene espacio.
 */
public class CapacidadLlenaException extends Exception {
    public CapacidadLlenaException(String mensaje) {
        super(mensaje);
    }
}