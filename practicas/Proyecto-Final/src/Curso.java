/**
 * Clase Modelo: Curso
 *
 * Representa un curso en línea con sus datos generales, fechas de inscripción y
 * de impartición, profesor asignado y alumnos inscritos. Además, implementa
 * reglas de negocio para inscripción, edición y eliminación conforme al PDF
 * de especificación.
 */
public class Curso implements Identificable, Persistible {

    /** Identificador único del curso. */
    private int idCurso;

    /** Nombre del curso. */
    private String nombre;

    /** Categoría del curso (por ejemplo, Computación, Matemáticas, etc.). */
    private String categoria;

    /** Nivel educativo del curso (Preparatoria, Licenciatura o Posgrado). */
    private String nivel;

    /** Horario del curso, en formato "HH:MM-HH:MM". */
    private String horario;

    /** Plataforma en la que se imparte el curso (Meet, Zoom, etc.). */
    private String plataforma;

    /** Número máximo de estudiantes permitidos en el curso. */
    private int numeroEstudiantesMax;

    /** Descripción textual del curso. */
    private String descripcion;

    /** Fecha de inicio del periodo de inscripciones. */
    private Fecha fechaInicioInscripciones;

    /** Fecha de fin del periodo de inscripciones. */
    private Fecha fechaFinInscripciones;

    /** Fecha de inicio del curso. */
    private Fecha fechaInicioCurso;

    /** Fecha de fin del curso. */
    private Fecha fechaFinCurso;

    /** Profesor asignado actualmente al curso (puede ser null). */
    Profesor profesorAsignado;

    /** Arreglo de alumnos inscritos en el curso. */
    Alumno[] alumnosInscritos;

    /** Contador de alumnos actualmente inscritos en el curso. */
    int contadorAlumnos;

    /**
     * ID del profesor leído desde archivo al reconstruir el curso.
     * Se usa como dato auxiliar para reestablecer relaciones.
     */
    private int idProfesorGuardado = -1;

    /**
     * Arreglo auxiliar con los IDs de alumnos leídos desde archivo
     * al reconstruir el curso.
     */
    private int[] idsAlumnosGuardados;

    /** Cantidad de IDs de alumnos almacenados en {@link #idsAlumnosGuardados}. */
    private int contadorAlumnosGuardados;

    /**
     * Constructor vacío para permitir construcción gradual del objeto.
     */
    public Curso() { }

    /**
     * Constructor completo de curso. Valida horario y catálogos básicos.
     *
     * @param idCurso                  Identificador del curso.
     * @param nombre                   Nombre del curso.
     * @param categoria                Categoría del curso.
     * @param nivel                    Nivel educativo del curso.
     * @param horario                  Horario del curso, formato "HH:MM-HH:MM".
     * @param plataforma               Plataforma en la que se impartirá.
     * @param numeroEstudiantesMax     Número máximo de estudiantes.
     * @param descripcion              Descripción del curso.
     * @param fechaInicioInscripciones Fecha de inicio de inscripciones.
     * @param fechaFinInscripciones    Fecha de fin de inscripciones.
     * @param fechaInicioCurso         Fecha de inicio del curso.
     * @param fechaFinCurso            Fecha de término del curso.
     * @throws DatosInvalidosException Si alguno de los datos básicos es inválido.
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
     *
     * @throws DatosInvalidosException Si alguno de los valores es inválido.
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

    /**
     * Obtiene el identificador único del curso.
     *
     * @return ID del curso.
     */
    @Override
    public int getId() { return idCurso; }

    /**
     * Obtiene la categoría del curso.
     *
     * @return Categoría del curso.
     */
    public String getCategoria() { return categoria; }

    /**
     * Obtiene el nivel educativo del curso.
     *
     * @return Nivel del curso.
     */
    public String getNivel() { return nivel; }

    /**
     * Obtiene el profesor actualmente asignado al curso.
     *
     * @return Profesor asignado o null si no hay.
     */
    public Profesor getProfesorAsignado() { return profesorAsignado; }

    /**
     * Obtiene el nombre del curso.
     *
     * @return Nombre del curso.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene el horario del curso.
     *
     * @return Cadena con el horario.
     */
    public String getHorario() { return horario; }

    /**
     * Obtiene la plataforma en la que se imparte el curso.
     *
     * @return Plataforma del curso.
     */
    public String getPlataforma() { return plataforma; }

    /**
     * Obtiene la descripción del curso.
     *
     * @return Descripción.
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Obtiene la fecha de inicio del periodo de inscripciones.
     *
     * @return Fecha de inicio de inscripciones.
     */
    public Fecha getFechaInicioInscripciones() { return fechaInicioInscripciones; }

    /**
     * Obtiene la fecha de fin del periodo de inscripciones.
     *
     * @return Fecha de fin de inscripciones.
     */
    public Fecha getFechaFinInscripciones() {  return fechaFinInscripciones; }

    /**
     * Obtiene la fecha de inicio del curso.
     *
     * @return Fecha de inicio del curso.
     */
    public Fecha getFechaInicioCurso() { return fechaInicioCurso;  }

    /**
     * Obtiene la fecha de término del curso.
     *
     * @return Fecha de fin del curso.
     */
    public Fecha getFechaFinCurso() {  return fechaFinCurso;  }

    /**
     * Devuelve el arreglo interno de alumnos inscritos.
     *
     * @return Arreglo de alumnos (puede contener null en posiciones vacías).
     */
    public Alumno[] getAlumnosInscritos() { return alumnosInscritos; }

    /**
     * Devuelve el número actual de alumnos inscritos.
     *
     * @return Número de alumnos.
     */
    public int getContadorAlumnos() { return contadorAlumnos;  }

    /**
     * Devuelve el ID de profesor almacenado al reconstruir el curso desde archivo.
     *
     * @return ID de profesor guardado o -1 si no había.
     */
    public int getIdProfesorGuardado() { return idProfesorGuardado; }

    /**
     * Devuelve el arreglo de IDs de alumnos almacenados al reconstruir desde archivo.
     *
     * @return Arreglo de IDs de alumnos guardados.
     */
    public int[] getIdsAlumnosGuardados() { return idsAlumnosGuardados; }

    /**
     * Devuelve la cantidad de IDs de alumnos guardados.
     *
     * @return Número de IDs de alumnos guardados.
     */
    public int getContadorAlumnosGuardados() { return contadorAlumnosGuardados; }

    /**
     * Devuelve el número máximo de estudiantes permitidos en el curso.
     *
     * @return Límite de estudiantes.
     */
    public int getNumeroEstudiantesMax() { return numeroEstudiantesMax; }

    /**
     * Establece el nombre del curso.
     *
     * @param nombre Nuevo nombre del curso.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Establece la categoría del curso.
     *
     * @param categoria Nueva categoría.
     */
    public void setCategoria(String categoria) { this.categoria = categoria; }

    /**
     * Establece el nivel del curso.
     *
     * @param nivel Nuevo nivel.
     */
    public void setNivel(String nivel) { this.nivel = nivel; }

    /**
     * Establece el horario del curso.
     *
     * @param horario Nuevo horario.
     */
    public void setHorario(String horario) { this.horario = horario; }

    /**
     * Establece la plataforma en que se imparte el curso.
     *
     * @param plataforma Nueva plataforma.
     */
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }

    /**
     * Establece la descripción del curso.
     *
     * @param descripcion Nueva descripción.
     */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * Establece la fecha de inicio del periodo de inscripciones.
     *
     * @param f Nueva fecha de inicio de inscripciones.
     */
    public void setFechaInicioInscripciones(Fecha f) { this.fechaInicioInscripciones = f; }

    /**
     * Establece la fecha de fin del periodo de inscripciones.
     *
     * @param f Nueva fecha de fin de inscripciones.
     */
    public void setFechaFinInscripciones(Fecha f) { this.fechaFinInscripciones = f; }

    /**
     * Establece la fecha de inicio del curso.
     *
     * @param f Nueva fecha de inicio.
     */
    public void setFechaInicioCurso(Fecha f) { this.fechaInicioCurso = f; }

    /**
     * Establece la fecha de término del curso.
     *
     * @param f Nueva fecha de término.
     */
    public void setFechaFinCurso(Fecha f) { this.fechaFinCurso = f; }

    /**
     * Asigna un profesor al curso.
     *
     * @param profesorAsignado Profesor que se asigna.
     */
    public void setProfesorAsignado(Profesor profesorAsignado) { this.profesorAsignado = profesorAsignado;}

    /**
     * Método Calculador: estaEnPeriodoInscripcion
     *
     * Indica si la fecha dada se encuentra dentro del periodo de inscripciones,
     * considerando ambas fechas (inicio y fin) de forma inclusiva.
     *
     * @param hoy Fecha a evaluar.
     * @return true si {@code hoy} está entre fechaInicioInscripciones y fechaFinInscripciones.
     */
    public boolean estaEnPeriodoInscripcion(Fecha hoy) {
        return hoy.esEntre(fechaInicioInscripciones, fechaFinInscripciones);
    }

    /**
     * Método Calculador: haIniciado
     *
     * Indica si, en la fecha dada, el curso ya ha iniciado.
     *
     * @param hoy Fecha a evaluar.
     * @return true si {@code hoy} es igual o posterior a fechaInicioCurso.
     */
    public boolean haIniciado(Fecha hoy) {
        return !hoy.esAnteriorA(fechaInicioCurso);
    }

    /**
     * Método Calculador: haTerminado
     *
     * Indica si, en la fecha dada, el curso ya ha concluido.
     *
     * @param hoy Fecha a evaluar.
     * @return true si {@code hoy} es posterior a fechaFinCurso.
     */
    public boolean haTerminado(Fecha hoy) {
        return hoy.esPosteriorA(fechaFinCurso);
    }

    /**
     * Método Calculador: estaEnClases
     *
     * Determina si el curso está actualmente en clases
     * (entre fechaInicioCurso y fechaFinCurso inclusive).
     *
     * @param hoy Fecha a evaluar.
     * @return true si el curso se encuentra en periodo de clases.
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
     *
     * @param hoy Fecha actual.
     * @return true si se permite eliminar el curso.
     */
    public boolean puedeEliminarse(Fecha hoy) {
        return (contadorAlumnos == 0) || haTerminado(hoy);
    }

    /**
     * Método Calculador: inscribirAlumno
     *
     * Aplica TODAS las reglas de la sección 2.6 del documento.
     * Valida periodo de inscripción, que el curso no haya iniciado,
     * que haya cupo, que el alumno no esté saturado de materias y que
     * no esté ya inscrito.
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
     *
     * @param a   Alumno a desinscribir.
     * @param hoy Fecha actual.
     * @throws InscripcionException Si el curso ya terminó o el alumno no está inscrito.
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

    /**
     * Convierte el curso en una línea de texto serializada para almacenamiento.
     *
     * Formato:
     * idCurso|nombre|categoria|nivel|horario|plataforma|numeroEstudiantesMax|
     * descripcion|fechaInicioInscripciones|fechaFinInscripciones|
     * fechaInicioCurso|fechaFinCurso|idProfesor|listaIdsAlumnos
     *
     * donde la lista de IDs de alumnos es una lista separada por comas.
     *
     * @return Cadena con la representación textual del curso.
     */
    @Override
    public String toLineaTexto() {
        // Profesor asignado: guardamos su ID o -1 si no hay
        int idProfesor = (profesorAsignado != null) ? profesorAsignado.getId() : -1;

        // Lista de alumnos inscritos: IDs separados por coma
        StringBuilder sbAlumnos = new StringBuilder();
        for (int i = 0; i < contadorAlumnos; i++) {
            if (alumnosInscritos[i] != null) {
                if (sbAlumnos.length() > 0) {
                    sbAlumnos.append(",");
                }
                sbAlumnos.append(alumnosInscritos[i].getId());
            }
        }

        return idCurso + "|" + nombre + "|" + categoria + "|" + nivel + "|" +
                horario + "|" + plataforma + "|" + numeroEstudiantesMax + "|" +
                descripcion.replace("|", "/") + "|" +
                fechaInicioInscripciones.toString() + "|" +
                fechaFinInscripciones.toString() + "|" +
                fechaInicioCurso.toString() + "|" +
                fechaFinCurso.toString() + "|" +
                idProfesor + "|" + sbAlumnos;
    }

    /**
     * Reconstruye el objeto curso a partir de una línea de texto previamente
     * generada por {@link #toLineaTexto()}.
     *
     * También inicializa estructuras internas para posteriormente vincular
     * profesor y alumnos mediante sus IDs guardados.
     *
     * @param linea Cadena con los datos serializados del curso.
     * @throws FormatoInvalidoException Si la línea no cumple el formato esperado.
     */
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

            // Inicializar arreglo de alumnos inscritos en memoria
            this.alumnosInscritos = new Alumno[numeroEstudiantesMax];
            this.contadorAlumnos = 0;

            // Inicializar estructura para IDs guardados
            this.idsAlumnosGuardados = new int[numeroEstudiantesMax];
            this.contadorAlumnosGuardados = 0;
            this.idProfesorGuardado = -1;

            // Si hay info extra (profesor y alumnos), la procesamos
            if (partes.length >= 13) {
                this.idProfesorGuardado = Integer.parseInt(partes[12]);
            }
            if (partes.length >= 14 && partes[13] != null && !partes[13].isEmpty()) {
                String[] ids = partes[13].split(",");
                for (int i = 0; i < ids.length && i < numeroEstudiantesMax; i++) {
                    try {
                        this.idsAlumnosGuardados[contadorAlumnosGuardados] =
                                Integer.parseInt(ids[i].trim());
                        contadorAlumnosGuardados++;
                    } catch (NumberFormatException e) {
                        // ignoramos IDs mal formados
                    }
                }
            }

        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("Error al parsear Curso: " + linea);
        }
    }
}
