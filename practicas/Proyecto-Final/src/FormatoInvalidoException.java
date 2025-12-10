/**
 * Excepción: FormatoInvalidoException
 *
 * Se utiliza cuando existe un problema al leer/escribir desde los archivos
 * de persistencia (línea mal formada, tipos incorrectos, etc.).
 */
public class FormatoInvalidoException extends Exception {

    /**
     * Constructor de la excepción.
     *
     * @param mensaje Descripción legible del error.
     */
    public FormatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}