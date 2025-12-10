/**
 * Clase Gestor: GestorAlumnos
 *
 * Gestiona altas, consultas, listados y eliminaciones de alumnos
 * utilizando repositorios de alumnos y cursos.
 */
public class GestorAlumnos {

    /** Repositorio donde se almacenan y gestionan los alumnos. */
    private RepositorioAlumnos repoAlumnos;

    /** Repositorio donde se almacenan y gestionan los cursos. */
    private RepositorioCursos repoCursos;

    /**
     * Constructor principal del gestor de alumnos.
     *
     * @param repoAlumnos Repositorio que provee operaciones de persistencia de alumnos.
     * @param repoCursos  Repositorio que permite consultar los cursos para validar reglas
     *                    al eliminar alumnos.
     */
    public GestorAlumnos(RepositorioAlumnos repoAlumnos,
                         RepositorioCursos repoCursos) {
        this.repoAlumnos = repoAlumnos;
        this.repoCursos = repoCursos;
    }

    /**
     * Método Gestor: registrarAlumno
     *
     * Registra un nuevo alumno verificando:
     * 
     *     Que el alumno no sea nulo.
     *     Que el nombre completo no sea vacío.
     *     Que el número de cuenta (ID) no esté repetido.
     * 
     *
     * @param alumno Alumno a registrar en el sistema.
     * @throws CapacidadLlenaException   (reservado para futuras validaciones de capacidad).
     * @throws DatosInvalidosException   Si el alumno es nulo, el nombre está vacío
     *                                   o el número de cuenta ya existe.
     */
    public void registrarAlumno(Alumno alumno)
            throws CapacidadLlenaException, DatosInvalidosException {

        if (alumno == null ||
            alumno.getNombreCompleto() == null ||
            alumno.getNombreCompleto().trim().isEmpty()) {
            throw new DatosInvalidosException("Nombre de alumno obligatorio.");
        }

        // Verificar que el ID no esté repetido
        try {
            repoAlumnos.buscarPorId(alumno.getId());
            throw new DatosInvalidosException(
                    "Ya existe un alumno con el número de cuenta " + alumno.getId());
        } catch (NoEncontradoException e) {
            // OK: no existe
        }

        repoAlumnos.agregar(alumno);
    }

    /**
     * Método Gestor: buscarAlumno
     *
     * Busca un alumno por su número de cuenta.
     *
     * @param numeroCuenta Número de cuenta del alumno.
     * @return Alumno encontrado.
     * @throws NoEncontradoException Si no existe un alumno con ese número de cuenta.
     */
    public Alumno buscarAlumno(int numeroCuenta) throws NoEncontradoException {
        return repoAlumnos.buscarPorId(numeroCuenta);
    }

    /**
     * Método Gestor: eliminarAlumno
     *
     * Elimina un alumno del sistema, siempre y cuando no se encuentre
     * inscrito en ningún curso. Si está inscrito en al menos un curso,
     * la operación se rechaza.
     *
     * @param numCuenta Número de cuenta del alumno a eliminar.
     * @throws NoEncontradoException          Si no existe un alumno con ese número de cuenta.
     * @throws EliminacionNoPermitidaException Si el alumno está inscrito en algún curso.
     */
    public void eliminarAlumno(int numCuenta)
            throws NoEncontradoException, EliminacionNoPermitidaException {

        Alumno a = repoAlumnos.buscarPorId(numCuenta);
        Curso[] cursos = repoCursos.getTodos();

        for (int i = 0; i < cursos.length; i++) {
            if (cursos[i] != null) {
                Alumno[] inscritos = cursos[i].alumnosInscritos;
                for (int j = 0; j < cursos[i].contadorAlumnos; j++) {
                    if (inscritos[j] != null && inscritos[j].getId() == a.getId()) {
                        throw new EliminacionNoPermitidaException(
                                "No se puede eliminar al alumno, está inscrito en el curso " + cursos[i].getId());
                    }
                }
            }
        }
        repoAlumnos.eliminarPorId(numCuenta);
    }

    /**
     * Método Gestor: listarAlumnos
     *
     * Obtiene el arreglo completo de alumnos almacenados en el repositorio.
     * Pueden existir posiciones nulas si el repositorio usa un arreglo fijo.
     *
     * @return Arreglo de alumnos administrados por el sistema.
     */
    public Alumno[] listarAlumnos() {
        return repoAlumnos.getTodos();
    }
}
