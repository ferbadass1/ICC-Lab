/**
 * Clase Abstracta: Persona
 *
 * Modela atributos comunes de profesores y alumnos.
 */
public abstract class Persona {

    /** Nombre completo de la persona. */
    protected String nombreCompleto;

    /**
     * Constructor vacío por defecto.
     */
    public Persona() { }

    /**
     * Constructor con nombre.
     *
     * @param nombreCompleto Nombre completo de la persona.
     */
    public Persona(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Método Abstracto: getIdentificador
     *
     * Devuelve el identificador único de la persona como texto.
     *
     * @return Identificador único en formato String.
     */
    public abstract String getIdentificador();
}
