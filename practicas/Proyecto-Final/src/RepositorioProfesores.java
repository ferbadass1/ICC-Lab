/**
 * Clase Repositorio: RepositorioProfesores
 *
 * Implementa un repositorio concreto para objetos {Profesor},
 * reutilizando la infraestructura genérica ofrecida por
 * {RepositorioBase}. Permite almacenar, buscar, eliminar
 * y reconstruir profesores desde texto.
 */
public class RepositorioProfesores extends RepositorioBase<Profesor> {

    /**
     * Constructor del repositorio de profesores.
     *
     * Inicializa el repositorio con un arreglo fijo que determina
     * la capacidad máxima de profesores que pueden almacenarse simultáneamente.
     *
     * @param capacidad Cantidad máxima de profesores.
     */
    public RepositorioProfesores(int capacidad) {
        super(new Profesor[capacidad]);   // ⬅️ IMPORTANTE
    }

    /**
     * Reconstruye un objeto {Profesor} a partir de una línea de texto
     * generada por su método {toLineaTexto()}.
     *
     * Este método es utilizado por las operaciones de persistencia de
     * {RepositorioBase}, particularmente durante la lectura de archivos
     * en {cargarTodo(String)}.
     *
     * @param linea Cadena con los datos serializados del profesor.
     * @return Instancia de {Profesor} reconstruida a partir de la línea.
     * @throws FormatoInvalidoException Si la estructura o contenido de la línea
     *                                  no coincide con el formato esperado.
     */
    @Override
    protected Profesor crearDesdeLinea(String linea) throws FormatoInvalidoException {
        Profesor p = new Profesor();
        p.fromLineaTexto(linea);
        return p;
    }
}
