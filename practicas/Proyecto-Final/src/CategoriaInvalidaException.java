/**
 * Excepción: CategoriaInvalidaException
 *
 * Se lanza cuando una categoría no pertenece al catálogo permitido.
 */
public class CategoriaInvalidaException extends DatosInvalidosException {
    public CategoriaInvalidaException(String mensaje) {
        super(mensaje);
    }
}