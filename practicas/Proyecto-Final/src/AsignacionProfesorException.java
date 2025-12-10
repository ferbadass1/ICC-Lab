/**
 * Excepción: AsignacionProfesorException
 *
 * Representa errores al asignar o reasignar profesores a cursos.
 */
public class AsignacionProfesorException extends Exception {

    /**
     * Constructor de la excepción.
     *
     * @param mensaje Descripción legible del error.
     */
    public AsignacionProfesorException(String mensaje) {
        super(mensaje);
    }
}