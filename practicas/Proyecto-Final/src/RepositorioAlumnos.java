/**
 * Clase Repositorio: RepositorioAlumnos
 *
 * Implementa un repositorio concreto para objetos {Alumno},
 * utilizando el comportamiento genérico definido en {RepositorioBase}.
 * Permite almacenar, buscar, eliminar y reconstruir alumnos desde texto.
 */
public class RepositorioAlumnos extends RepositorioBase<Alumno> {

    /**
     * Constructor del repositorio de alumnos.
     *
     * Inicializa el repositorio con un arreglo de tamaño fijo que
     * determina la cantidad máxima de alumnos que se pueden almacenar.
     *
     * @param capacidad Cantidad máxima de alumnos que se pueden almacenar.
     */
    public RepositorioAlumnos(int capacidad) {
        super(new Alumno[capacidad]);
    }

    /**
     * Crea una instancia de {Alumno} a partir de una línea de texto
     * previamente generada por su método {toLineaTexto()}.
     *
     * @param linea Cadena que contiene los datos de un alumno serializados.
     * @return Objeto {Alumno} reconstruido.
     * @throws FormatoInvalidoException Si la línea no cumple con el formato esperado.
     */
    @Override
    protected Alumno crearDesdeLinea(String linea) throws FormatoInvalidoException {
        Alumno a = new Alumno();
        a.fromLineaTexto(linea);
        return a;
    }
}
