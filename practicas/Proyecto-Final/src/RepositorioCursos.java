/**
 * Clase Repositorio: RepositorioCursos
 *
 * Implementa un repositorio concreto para objetos {Curso},
 * aprovechando la funcionalidad genérica proporcionada por
 * {RepositorioBase}. Permite almacenar, buscar, eliminar
 * y reconstruir cursos desde su representación en texto.
 */
public class RepositorioCursos extends RepositorioBase<Curso> {

    /**
     * Constructor del repositorio de cursos.
     *
     * Inicializa el repositorio con un arreglo de tamaño fijo,
     * el cual determina la capacidad máxima de cursos que pueden
     * almacenarse simultáneamente.
     *
     * @param capacidad Cantidad máxima de cursos.
     */
    public RepositorioCursos(int capacidad) {
        super(new Curso[capacidad]);  
    }

    /**
     * Reconstruye un objeto {Curso} a partir de una línea de texto
     * previamente serializada mediante {toLineaTexto()}.
     *
     * Este método es invocado por la infraestructura de persistencia del
     * repositorio cuando se llama a {cargarTodo(String)}.
     *
     * @param linea Cadena que contiene los datos del curso.
     * @return Instancia de {Curso} creada a partir de la línea.
     * @throws FormatoInvalidoException Si la línea no cumple con el formato esperado.
     */
    @Override
    protected Curso crearDesdeLinea(String linea) throws FormatoInvalidoException {
        Curso c = new Curso();
        c.fromLineaTexto(linea);
        return c;
    }
}
