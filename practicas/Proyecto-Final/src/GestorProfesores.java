/**
 * Clase Gestor: GestorProfesores
 *
 * Se encarga de las operaciones de negocio relacionadas con profesores:
 * registro, búsqueda, eliminación y obtención del listado general,
 * apoyándose en los repositorios correspondientes.
 */
public class GestorProfesores {

    /** Repositorio que administra el almacenamiento de profesores. */
    private RepositorioProfesores repoProfesores;

    /** Repositorio que permite consultar los cursos registrados. */
    private RepositorioCursos repoCursos;

    /**
     * Construye un gestor de profesores a partir de los repositorios
     * proporcionados.
     *
     * @param repoProfesores Repositorio de profesores.
     * @param repoCursos     Repositorio de cursos.
     */
    public GestorProfesores(RepositorioProfesores repoProfesores,
                            RepositorioCursos repoCursos) {
        this.repoProfesores = repoProfesores;
        this.repoCursos = repoCursos;
    }

    /**
     * Registra un nuevo profesor en el sistema.
     *
     * Valida que el profesor no sea nulo, que cuente con un nombre
     * no vacío y que su número de trabajador no esté registrado
     * previamente en el repositorio.
     *
     * @param profesor Profesor a registrar.
     * @throws CapacidadLlenaException Si el repositorio no admite más profesores.
     * @throws DatosInvalidosException Si el nombre es inválido o el número de
     *                                 trabajador ya existe.
     */
    public void registrarProfesor(Profesor profesor)
        throws CapacidadLlenaException, DatosInvalidosException {

        if (profesor == null ||
            profesor.getNombreCompleto() == null ||
            profesor.getNombreCompleto().trim().isEmpty()) {
            throw new DatosInvalidosException("Nombre de profesor obligatorio.");
        }

        // Verificar que el ID no esté repetido
        try {
            repoProfesores.buscarPorId(profesor.getId());
            // si NO lanzó excepción, ya existe uno con ese id
            throw new DatosInvalidosException(
                    "Ya existe un profesor con el número de trabajador " + profesor.getId());
        } catch (NoEncontradoException e) {
            // OK: no existe, podemos continuar
        }

        repoProfesores.agregar(profesor);
    }

    /**
     * Busca un profesor a partir de su número de trabajador.
     *
     * @param numTrabajador Número de trabajador del profesor.
     * @return Profesor correspondiente al número indicado.
     * @throws NoEncontradoException Si no existe un profesor con ese número.
     */
    public Profesor buscarProfesor(int numTrabajador) throws NoEncontradoException {
        return repoProfesores.buscarPorId(numTrabajador);
    }

    /**
     * Elimina un profesor del sistema, siempre que no esté asignado a
     * ningún curso registrado.
     *
     * Si el profesor se encuentra como profesor asignado en al menos
     * un curso, la eliminación no está permitida.
     *
     * @param numTrabajador Número de trabajador del profesor a eliminar.
     * @throws NoEncontradoException          Si no existe un profesor con ese número.
     * @throws EliminacionNoPermitidaException Si el profesor está asignado a algún curso.
     */
    public void eliminarProfesor(int numTrabajador)
            throws NoEncontradoException, EliminacionNoPermitidaException {

        Profesor p = repoProfesores.buscarPorId(numTrabajador);
        Curso[] cursos = repoCursos.getTodos();

        for (int i = 0; i < cursos.length; i++) {
            if (cursos[i] != null && cursos[i].getProfesorAsignado() != null &&
                cursos[i].getProfesorAsignado().getId() == p.getId()) {
                throw new EliminacionNoPermitidaException(
                        "No se puede eliminar al profesor, está asignado al curso " + cursos[i].getId());
            }
        }
        repoProfesores.eliminarPorId(numTrabajador);
    }

    /**
     * Devuelve el listado completo de profesores administrados
     * por el repositorio.
     *
     * @return Arreglo de profesores; puede contener posiciones nulas.
     */
    public Profesor[] listarProfesores() {
        return repoProfesores.getTodos();
    }
}
