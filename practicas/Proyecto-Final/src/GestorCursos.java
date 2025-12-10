/**
 * Clase Gestor: GestorCursos
 *
 * Implementa las reglas de negocio sobre cursos, profesores y alumnos
 * según las secciones 2.4 a 2.8 del documento del proyecto.
 */
public class GestorCursos {

    private RepositorioCursos repoCursos;
    private RepositorioProfesores repoProfesores;
    private RepositorioAlumnos repoAlumnos;

    public GestorCursos(RepositorioCursos repoCursos,
                        RepositorioProfesores repoProfesores,
                        RepositorioAlumnos repoAlumnos) {
        this.repoCursos = repoCursos;
        this.repoProfesores = repoProfesores;
        this.repoAlumnos = repoAlumnos;
    }

    /**
     * Registra un curso verificando que no se repita el ID.
     */
    public void registrarCurso(Curso curso)
            throws CapacidadLlenaException, DatosInvalidosException {

        if (curso == null) {
            throw new DatosInvalidosException("Curso nulo.");
        }

        // Verificar ID único del curso
        try {
            repoCursos.buscarPorId(curso.getId());
            throw new DatosInvalidosException(
                    "Ya existe un curso con el ID " + curso.getId());
        } catch (NoEncontradoException e) {
            // OK: no existe
        }

        repoCursos.agregar(curso);
    }

    /**
     * Solo se pueden eliminar los cursos que no tengan alumnos
     * o que ya hayan finalizado (regla 2.8).
     */
    public void eliminarCurso(int idCurso, Fecha hoy)
            throws NoEncontradoException, EliminacionNoPermitidaException {

        Curso c = repoCursos.buscarPorId(idCurso);
        if (!c.puedeEliminarse(hoy)) {
            throw new EliminacionNoPermitidaException(
                    "El curso no puede eliminarse: tiene alumnos y no ha finalizado.");
        }
        repoCursos.eliminarPorId(idCurso);
    }

    public Curso[] listarCursos() {
        return repoCursos.getTodos();
    }

    /**
     * Asignación de profesores (2.4).
     */
    public void asignarProfesor(int idCurso, int numTrabajador, Fecha hoy)
            throws NoEncontradoException, AsignacionProfesorException {

        Curso c = repoCursos.buscarPorId(idCurso);
        Profesor p = repoProfesores.buscarPorId(numTrabajador);

        if (c.haIniciado(hoy)) {
            throw new AsignacionProfesorException("El curso ya inició.");
        }
        if (c.getProfesorAsignado() != null) {
            throw new AsignacionProfesorException("El curso ya tiene un profesor asignado.");
        }
        if (!p.puedeImpartir(c.getNivel(), c.getCategoria())) {
            throw new AsignacionProfesorException(
                    "El profesor no puede impartir el nivel/categoría del curso.");
        }
        c.setProfesorAsignado(p);
    }

    /**
     * Reasignación de profesores (2.5).
     *
     * Hay un error en el PDF, pero se interpreta razonablemente así:
     * - El curso no ha terminado.
     * - El nuevo profesor da el nivel y la categoría.
     */
    public void reasignarProfesor(int idCurso, int numTrabajador, Fecha hoy)
            throws NoEncontradoException, AsignacionProfesorException {

        Curso c = repoCursos.buscarPorId(idCurso);
        Profesor p = repoProfesores.buscarPorId(numTrabajador);

        if (c.haTerminado(hoy)) {
            throw new AsignacionProfesorException("No se puede reasignar, el curso ya terminó.");
        }
        if (!p.puedeImpartir(c.getNivel(), c.getCategoria())) {
            throw new AsignacionProfesorException(
                    "El profesor no puede impartir el nivel/categoría del curso.");
        }
        c.setProfesorAsignado(p);
    }

    /**
     * Inscripción de alumnos (2.6).
     */
    public void inscribirAlumnoEnCurso(int idCurso, int numCuenta, Fecha hoy)
            throws NoEncontradoException, InscripcionException {

        Curso c = repoCursos.buscarPorId(idCurso);
        Alumno a = repoAlumnos.buscarPorId(numCuenta);
        c.inscribirAlumno(a, hoy);
    }

    /**
     * Desinscripción de alumnos (2.7).
     */
    public void desinscribirAlumnoDeCurso(int idCurso, int numCuenta, Fecha hoy)
            throws NoEncontradoException, InscripcionException {

        Curso c = repoCursos.buscarPorId(idCurso);
        Alumno a = repoAlumnos.buscarPorId(numCuenta);
        c.desinscribirAlumno(a, hoy);
    }
}
