/**
 * Excepción: DatosInvalidosException
 *
 * Representa errores de validación de datos de entrada
 * (campos vacíos, formatos incorrectos, valores fuera de rango, etc.).
 */
public class DatosInvalidosException extends Exception {

    /**
     * Constructor de la excepción.
     *
     * @param mensaje Descripción legible del error.
     */
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
