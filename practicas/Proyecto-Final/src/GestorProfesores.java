/**
 * Clase Gestor: GestorProfesores
 *
 * Encargada de las operaciones de negocio sobre profesores:
 * alta, búsqueda, edición y eliminación segura.
 */
public class GestorProfesores {

    private RepositorioProfesores repoProfesores;
    private RepositorioCursos repoCursos;

    public GestorProfesores(RepositorioProfesores repoProfesores,
                            RepositorioCursos repoCursos) {
        this.repoProfesores = repoProfesores;
        this.repoCursos = repoCursos;
    }

        /**
     * Registra un nuevo profesor verificando que:
     * - tenga nombre válido
     * - su número de trabajador no esté repetido
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
     * Solo se puede eliminar un profesor que NO esté asignado a ningún curso.
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

    public Profesor[] listarProfesores() {
        return repoProfesores.getTodos();
    }
}
