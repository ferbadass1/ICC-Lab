/**
 * Clase Modelo: Curso
 *
 * Representa un curso en línea con sus datos, fechas, profesor asignado
 * y alumnos inscritos.
 */
public class Curso implements Identificable, Persistible {

    private int idCurso;
    private String nombre;
    private String categoria;
    private String nivel;
    private String horario;
    private String plataforma;
    private int numeroEstudiantesMax;
    private String descripcion;

    private Fecha fechaInicioInscripciones;
    private Fecha fechaFinInscripciones;
    private Fecha fechaInicioCurso;
    private Fecha fechaFinCurso;

    Profesor profesorAsignado;          // paquete-visible para uso en gestor
    Alumno[] alumnosInscritos;
    int contadorAlumnos;

    public Curso() { }

    /**
     * Constructor completo de curso. Valida horario y catálogos básicos.
     */
    public Curso(int idCurso, String nombre, String categoria, String nivel,
                 String horario, String plataforma, int numeroEstudiantesMax,
                 String descripcion,
                 Fecha fechaInicioInscripciones, Fecha fechaFinInscripciones,
                 Fecha fechaInicioCurso, Fecha fechaFinCurso)
            throws DatosInvalidosException {

        this.idCurso = idCurso;
        this.nombre = nombre;
        this.categoria = categoria;
        this.nivel = nivel;
        this.horario = horario;
        this.plataforma = plataforma;
        this.numeroEstudiantesMax = numeroEstudiantesMax;
        this.descripcion = descripcion;
        this.fechaInicioInscripciones = fechaInicioInscripciones;
        this.fechaFinInscripciones = fechaFinInscripciones;
        this.fechaInicioCurso = fechaInicioCurso;
        this.fechaFinCurso = fechaFinCurso;

        validarDatosBasicos();
        this.alumnosInscritos = new Alumno[numeroEstudiantesMax];
        this.contadorAlumnos = 0;
    }

    /**
     * Método Calculador: validarDatosBasicos
     *
     * Valida categoría, nivel, plataforma, horario y número de estudiantes,
     * conforme al documento de especificación.
     */
    private void validarDatosBasicos() throws DatosInvalidosException {
        if (!Catalogos.esValorValido(categoria, Catalogos.CATEGORIAS)) {
            throw new DatosInvalidosException("Categoría de curso inválida: " + categoria);
        }
        if (!Catalogos.esValorValido(nivel, Catalogos.NIVELES)) {
            throw new DatosInvalidosException("Nivel de curso inválido: " + nivel);
        }
        if (!Catalogos.esValorValido(plataforma, Catalogos.PLATAFORMAS)) {
            throw new DatosInvalidosException("Plataforma inválida: " + plataforma);
        }
        if (numeroEstudiantesMax <= 0) {
            throw new DatosInvalidosException("El número de estudiantes debe ser mayor a cero.");
        }
        Catalogos.validarHorario(horario);
    }

    @Override
    public int getId() {
        return idCurso;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getNivel() {
        return nivel;
    }

    public void setProfesorAsignado(Profesor profesorAsignado) {
        this.profesorAsignado = profesorAsignado;
    }

    public Profesor getProfesorAsignado() {
        return profesorAsignado;
    }

    /**
     * Método Calculador: estaEnPeriodoInscripcion
     */
    public boolean estaEnPeriodoInscripcion(Fecha hoy) {
        return hoy.esEntre(fechaInicioInscripciones, fechaFinInscripciones);
    }

    /**
     * Método Calculador: haIniciado
     */
    public boolean haIniciado(Fecha hoy) {
        return !hoy.esAnteriorA(fechaInicioCurso);
    }

    /**
     * Método Calculador: haTerminado
     */
    public boolean haTerminado(Fecha hoy) {
        return hoy.esPosteriorA(fechaFinCurso);
    }

    /**
     * Método Calculador: estaEnClases
     *
     * Determina si el curso está actualmente en clases
     * (entre fechaInicioCurso y fechaFinCurso inclusive).
     */
    public boolean estaEnClases(Fecha hoy) {
        return hoy.esEntre(fechaInicioCurso, fechaFinCurso);
    }

    /**
     * Método Calculador: puedeEditarCompletamente
     *
     * Regla del PDF:
     * - Solo se pueden editar completamente cursos que
     *   NO estén en clases y que tengan más de 3 alumnos.
     *
     * @param hoy Fecha actual.
     * @return true si se puede editar completamente, false en caso contrario.
     */
    public boolean puedeEditarCompletamente(Fecha hoy) {
        return !estaEnClases(hoy) && contadorAlumnos > 3;
    }

    /**
     * Método Calculador: puedeEliminarse
     *
     * Regla del PDF:
     * - Solo se pueden eliminar los cursos que no tengan alumnos
     *   o que ya hayan finalizado.
     */
    public boolean puedeEliminarse(Fecha hoy) {
        return (contadorAlumnos == 0) || haTerminado(hoy);
    }

    /**
     * Método Calculador: inscribirAlumno
     *
     * Aplica TODAS las reglas de la sección 2.6 del documento.
     *
     * @param a   Alumno a inscribir.
     * @param hoy Fecha actual.
     * @throws InscripcionException Si se viola alguna de las reglas de inscripción.
     */
    public void inscribirAlumno(Alumno a, Fecha hoy) throws InscripcionException {

        if (!estaEnPeriodoInscripcion(hoy)) {
            throw new InscripcionException("La fecha de inscripción no está abierta.");
        }
        if (haIniciado(hoy)) {
            throw new InscripcionException("El curso ya inició.");
        }
        if (contadorAlumnos >= numeroEstudiantesMax) {
            throw new InscripcionException("El curso está lleno.");
        }
        if (!a.puedeInscribirOtroCurso()) {
            throw new InscripcionException("El alumno ya tiene 6 materias en curso.");
        }
        for (int i = 0; i < contadorAlumnos; i++) {
            if (alumnosInscritos[i].getId() == a.getId()) {
                throw new InscripcionException("El alumno ya está inscrito en el curso.");
            }
        }

        alumnosInscritos[contadorAlumnos] = a;
        contadorAlumnos++;
        a.agregarCurso(this);
    }

    /**
     * Método Calculador: desinscribirAlumno
     *
     * Regla del PDF (2.7):
     * - Solo se puede quitar a un alumno si la fecha de finalización
     *   del curso aún no ha concluido.
     */
    public void desinscribirAlumno(Alumno a, Fecha hoy) throws InscripcionException {
        if (haTerminado(hoy)) {
            throw new InscripcionException(
                    "No se puede desinscribir, la fecha de finalización ya concluyó.");
        }
        boolean encontrado = false;
        for (int i = 0; i < contadorAlumnos; i++) {
            if (alumnosInscritos[i].getId() == a.getId()) {
                for (int j = i; j < contadorAlumnos - 1; j++) {
                    alumnosInscritos[j] = alumnosInscritos[j + 1];
                }
                alumnosInscritos[contadorAlumnos - 1] = null;
                contadorAlumnos--;
                a.quitarCurso(this);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new InscripcionException("El alumno no está inscrito en este curso.");
        }
    }

    // ========= Persistible ===========

    @Override
    public String toLineaTexto() {
        return idCurso + "|" + nombre + "|" + categoria + "|" + nivel + "|" +
                horario + "|" + plataforma + "|" + numeroEstudiantesMax + "|" +
                descripcion.replace("|", "/") + "|" +
                fechaInicioInscripciones.toString() + "|" +
                fechaFinInscripciones.toString() + "|" +
                fechaInicioCurso.toString() + "|" +
                fechaFinCurso.toString();
    }

    @Override
    public void fromLineaTexto(String linea) throws FormatoInvalidoException {
        String[] partes = linea.split("\\|");
        if (partes.length < 12) {
            throw new FormatoInvalidoException("Línea inválida para Curso: " + linea);
        }
        try {
            this.idCurso = Integer.parseInt(partes[0]);
            this.nombre = partes[1];
            this.categoria = partes[2];
            this.nivel = partes[3];
            this.horario = partes[4];
            this.plataforma = partes[5];
            this.numeroEstudiantesMax = Integer.parseInt(partes[6]);
            this.descripcion = partes[7];
            this.fechaInicioInscripciones = Fecha.fromString(partes[8]);
            this.fechaFinInscripciones = Fecha.fromString(partes[9]);
            this.fechaInicioCurso = Fecha.fromString(partes[10]);
            this.fechaFinCurso = Fecha.fromString(partes[11]);
            this.alumnosInscritos = new Alumno[numeroEstudiantesMax];
            this.contadorAlumnos = 0;
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("Error al parsear Curso: " + linea);
        }
    }
}
