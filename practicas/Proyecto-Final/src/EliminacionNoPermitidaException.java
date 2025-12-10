/**
 * Excepción: EliminacionNoPermitidaException
 *
 * Se lanza cuando se intenta eliminar un curso, profesor o alumno
 * violando las reglas del sistema.
 */
public class EliminacionNoPermitidaException extends Exception {

    /**
     * Constructor de la excepción.
     *
     * @param mensaje Descripción legible del error.
     */
    public EliminacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}