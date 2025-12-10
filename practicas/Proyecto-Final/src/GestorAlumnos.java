/**
 * Clase Gestor: GestorAlumnos
 *
 * Gestiona altas, ediciones y bajas de alumnos.
 */
public class GestorAlumnos {

    private RepositorioAlumnos repoAlumnos;
    private RepositorioCursos repoCursos;

    public GestorAlumnos(RepositorioAlumnos repoAlumnos,
                         RepositorioCursos repoCursos) {
        this.repoAlumnos = repoAlumnos;
        this.repoCursos = repoCursos;
    }

    /**
     * Registra un nuevo alumno verificando:
     * - nombre no vacío
     * - ID (número de cuenta) no repetido
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
     * Solo se puede eliminar un alumno que no esté inscrito en ningún curso.
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

    public Alumno[] listarAlumnos() {
        return repoAlumnos.getTodos();
    }
}
