/**
 * Excepción: FormatoInvalidoException
 *
 * Se utiliza cuando existe un problema al leer/escribir desde los archivos
 * de persistencia (línea mal formada, tipos incorrectos, etc.).
 */
public class FormatoInvalidoException extends Exception {
    public FormatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}