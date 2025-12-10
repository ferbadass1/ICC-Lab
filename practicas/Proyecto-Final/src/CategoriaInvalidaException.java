/**
 * Excepción: CategoriaInvalidaException
 *
 * Se lanza cuando una categoría no pertenece al catálogo permitido.
 */
public class CategoriaInvalidaException extends DatosInvalidosException {

    /**
     * Constructor de la excepción.
     *
     * @param mensaje Descripción legible del error.
     */
    public CategoriaInvalidaException(String mensaje) {
        super(mensaje);
    }
}