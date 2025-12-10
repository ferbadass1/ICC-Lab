/**
 * Interfaz Identificable
 *
 * Define el comportamiento que deben tener las clases que poseen
 * un identificador único. Este identificador es típicamente utilizado
 * para búsquedas, almacenamiento y referencias dentro del sistema.
 */
public interface Identificable {

    /**
     * Obtiene el identificador único del objeto.
     *
     * @return Entero que representa el identificador del objeto.
     */
    int getId();
}
