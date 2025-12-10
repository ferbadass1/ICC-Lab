/**
 * Excepción: NoEncontradoException
 *
 * Se lanza cuando no se encuentra un elemento en un repositorio.
 */
public class NoEncontradoException extends Exception {

    /**
     * Constructor de la excepción.
     *
     * @param mensaje Descripción legible del error.
     */
    public NoEncontradoException(String mensaje) {
        super(mensaje);
    }
}