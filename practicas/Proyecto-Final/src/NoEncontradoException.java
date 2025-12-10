/**
 * Excepción: NoEncontradoException
 *
 * Se lanza cuando no se encuentra un elemento en un repositorio.
 */
public class NoEncontradoException extends Exception {
    public NoEncontradoException(String mensaje) {
        super(mensaje);
    }
}