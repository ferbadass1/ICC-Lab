/**
 * Excepción: InscripcionException
 *
 * Se utiliza para representar errores al inscribir o desinscribir alumnos.
 */
public class InscripcionException extends Exception {
    public InscripcionException(String mensaje) {
        super(mensaje);
    }
}