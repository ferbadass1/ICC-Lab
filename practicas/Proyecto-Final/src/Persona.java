/**
 * Clase Abstracta: Persona
 *
 * Representa los atributos y comportamientos comunes entre
 * entidades como alumnos y profesores dentro del sistema.
 * Proporciona el campo de nombre completo y un método abstracto
 * para obtener el identificador único de cada tipo de persona.
 */
public abstract class Persona {

    /** Nombre completo de la persona. */
    protected String nombreCompleto;

    /**
     * Constructor vacío por defecto.
     * Permite crear una persona sin inicializar aún su nombre.
     */
    public Persona() { }

    /**
     * Constructor que inicializa el nombre completo de la persona.
     *
     * @param nombreCompleto Nombre completo asociado a la persona.
     */
    public Persona(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Obtiene el nombre completo de la persona.
     *
     * @return Nombre completo como cadena de texto.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Asigna un nuevo nombre completo a la persona.
     *
     * @param nombreCompleto Nombre completo actualizado.
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Método Abstracto: getIdentificador
     *
     * Devuelve el identificador único de la persona como texto.
     * Cada subclase determina la naturaleza de dicho identificador,
     * por ejemplo: número de trabajador o número de cuenta.
     *
     * @return Identificador único en formato String.
     */
    public abstract String getIdentificador();
}
