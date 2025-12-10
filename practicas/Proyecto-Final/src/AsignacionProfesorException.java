/**
 * Excepción: AsignacionProfesorException
 *
 * Representa errores al asignar o reasignar profesores a cursos.
 */
public class AsignacionProfesorException extends Exception {
    public AsignacionProfesorException(String mensaje) {
        super(mensaje);
    }
}