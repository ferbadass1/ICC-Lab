/**
 * Clase Gestor: GestorCursos
 *
 * Implementa las reglas de negocio sobre cursos, profesores y alumnos
 * 
 */
public class GestorCursos {

    /** Repositorio donde se almacenan y gestionan los cursos. */
    private RepositorioCursos repoCursos;

    /** Repositorio donde se almacenan y gestionan los profesores. */
    private RepositorioProfesores repoProfesores;

    /** Repositorio donde se almacenan y gestionan los alumnos. */
    private RepositorioAlumnos repoAlumnos;

    /**
     * Constructor principal del gestor de cursos.
     *
     * @param repoCursos      Repositorio de cursos.
     * @param repoProfesores  Repositorio de profesores.
     * @param repoAlumnos     Repositorio de alumnos.
     */
    public GestorCursos(RepositorioCursos repoCursos,
                        RepositorioProfesores repoProfesores,
                        RepositorioAlumnos repoAlumnos) {
        this.repoCursos = repoCursos;
        this.repoProfesores = repoProfesores;
        this.repoAlumnos = repoAlumnos;
    }

    /**
     * Método Gestor: registrarCurso
     *
     * Registra un curso verificando que no se repita el ID del curso.
     *
     * Reglas:
     * 
     *     El curso no puede ser nulo.
     *     El ID del curso debe ser único en el repositorio.
     * 
     *
     * @param curso Curso a registrar.
     * @throws CapacidadLlenaException Si el repositorio de cursos no admite más registros.
     * @throws DatosInvalidosException Si el curso es nulo o el ID ya existe.
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
     * Método Gestor: eliminarCurso
     *
     * Solo se pueden eliminar los cursos que no tengan alumnos
     * o que ya hayan finalizado.
     *
     * @param idCurso ID del curso a eliminar.
     * @param hoy     Fecha actual para evaluar si el curso ya terminó.
     * @throws NoEncontradoException           Si no existe un curso con ese ID.
     * @throws EliminacionNoPermitidaException Si el curso tiene alumnos y no ha finalizado.
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

    /**
     * Devuelve la lista completa de cursos administrados.
     *
     * @return Arreglo de cursos del repositorio (puede contener posiciones nulas).
     */
    public Curso[] listarCursos() {
        return repoCursos.getTodos();
    }

    /**
     * Asignación de profesores (regla 2.4).
     *
     * Reglas:
     * 
     *     El curso no debe haber iniciado.
     *     El curso no debe tener ya un profesor asignado.
     *     El profesor debe poder impartir el nivel y la categoría del curso.
     * 
     *
     * @param idCurso      ID del curso.
     * @param numTrabajador Número de trabajador del profesor.
     * @param hoy          Fecha actual.
     * @throws NoEncontradoException        Si no se encuentra el curso o el profesor.
     * @throws AsignacionProfesorException  Si se viola alguna de las reglas anteriores.
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
     * Busca un curso por su ID.
     *
     * @param idCurso Identificador único del curso.
     * @return Curso encontrado.
     * @throws NoEncontradoException Si no existe un curso con ese ID.
     */
    public Curso buscarCurso(int idCurso) throws NoEncontradoException {
        return repoCursos.buscarPorId(idCurso);
    }

    /**
     * Reasignación de profesores.
     *
     * Reglas:
     * 
     *     El curso aún no ha iniciado.
     *     El nuevo profesor debe poder impartir el nivel y la categoría del curso.
     * 
     *
     * @param idCurso       ID del curso.
     * @param numTrabajador Nuevo número de trabajador del profesor.
     * @param hoy           Fecha actual.
     * @throws NoEncontradoException        Si no se encuentra el curso o el profesor.
     * @throws AsignacionProfesorException  Si el curso ya inició o el profesor no cumple requisitos.
     */
    public void reasignarProfesor(int idCurso, int numTrabajador, Fecha hoy)
            throws NoEncontradoException, AsignacionProfesorException {

        Curso c = repoCursos.buscarPorId(idCurso);
        Profesor p = repoProfesores.buscarPorId(numTrabajador);

        if (c.haIniciado(hoy)) {
            throw new AsignacionProfesorException("No se puede reasignar, el curso ya inició.");
        }
        if (!p.puedeImpartir(c.getNivel(), c.getCategoria())) {
            throw new AsignacionProfesorException(
                    "El profesor no puede impartir el nivel/categoría del curso.");
        }

        c.setProfesorAsignado(p);
    }

    /**
     * Inscripción de alumnos
     *
     *
     * @param idCurso  ID del curso.
     * @param numCuenta Número de cuenta del alumno.
     * @param hoy      Fecha actual.
     * @throws NoEncontradoException Si no se encuentra el curso o el alumno.
     * @throws InscripcionException  Si se viola alguna regla de inscripción.
     */
    public void inscribirAlumnoEnCurso(int idCurso, int numCuenta, Fecha hoy)
            throws NoEncontradoException, InscripcionException {

        Curso c = repoCursos.buscarPorId(idCurso);
        Alumno a = repoAlumnos.buscarPorId(numCuenta);
        c.inscribirAlumno(a, hoy);
    }

    /**
     * Desinscripción de alumnos
     *
     *
     * @param idCurso  ID del curso.
     * @param numCuenta Número de cuenta del alumno.
     * @param hoy      Fecha actual.
     * @throws NoEncontradoException Si no se encuentra el curso o el alumno.
     * @throws InscripcionException  Si se viola alguna regla de desinscripción.
     */
    public void desinscribirAlumnoDeCurso(int idCurso, int numCuenta, Fecha hoy)
            throws NoEncontradoException, InscripcionException {

        Curso c = repoCursos.buscarPorId(idCurso);
        Alumno a = repoAlumnos.buscarPorId(numCuenta);
        c.desinscribirAlumno(a, hoy);
    }
}
