/**
 * Excepción: InscripcionException
 *
 * Se utiliza para representar errores al inscribir o desinscribir alumnos.
 */
public class InscripcionException extends Exception {

    /**
     * Constructor de la excepción.
     *
     * @param mensaje Descripción legible del error.
     */
    public InscripcionException(String mensaje) {
        super(mensaje);
    }
}