import java.util.Scanner;

/**
 * Clase Vista: MenuConsola
 *
 * Implementa la interfaz de texto para interactuar con el usuario
 * y delega la lógica en los gestores. Incluye validaciones para
 * capturar datos válidos de forma iterativa.
 */
public class MenuConsola {

    /** Gestor responsable de la lógica relacionada con cursos. */
    private GestorCursos gestorCursos;

    /** Gestor responsable de la lógica relacionada con profesores. */
    private GestorProfesores gestorProfesores;

    /** Gestor responsable de la lógica relacionada con alumnos. */
    private GestorAlumnos gestorAlumnos;

    /** Objeto para leer datos desde la entrada estándar. */
    private Scanner scanner;

    /**
     * Constructor del menú de consola.
     *
     * @param gestorCursos      Gestor de cursos.
     * @param gestorProfesores  Gestor de profesores.
     * @param gestorAlumnos     Gestor de alumnos.
     */
    public MenuConsola(GestorCursos gestorCursos,
                       GestorProfesores gestorProfesores,
                       GestorAlumnos gestorAlumnos) {
        this.gestorCursos = gestorCursos;
        this.gestorProfesores = gestorProfesores;
        this.gestorAlumnos = gestorAlumnos;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal y redirige a los submenús correspondientes.
     * Se mantiene en ejecución hasta que el usuario elige la opción de salir.
     */
    public void mostrarMenuPrincipal() {
        int opcion = -1;
        do {
            System.out.println("\n=== SISTEMA DE GESTION DE CURSOS UNAM :) ===");
            System.out.println("1. Gestionar Profesores");
            System.out.println("2. Gestionar Alumnos");
            System.out.println("3. Gestionar Cursos");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuProfesores();
                case 2 -> menuAlumnos();
                case 3 -> menuCursos();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida, intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    // ================== MENÚ PROFESORES ==================

    /**
     * Muestra el submenú de gestión de profesores y permite
     * registrar, listar, editar y eliminar profesores.
     * Se repite hasta que el usuario decide volver al menú principal.
     */
    public void menuProfesores() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Profesores ---");
            System.out.println("1. Registrar profesor");
            System.out.println("2. Listar profesores");
            System.out.println("3. Editar profesor");      
            System.out.println("4. Eliminar profesor");   
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> registrarProfesor();
                case 2 -> listarProfesores();
                case 3 -> editarProfesor();    
                case 4 -> eliminarProfesor();  
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    /**
     * Captura los datos de un profesor, los valida de forma iterativa
     * y solicita al gestor el registro del nuevo profesor.
     */
    private void registrarProfesor() {
        try {
            System.out.println("\n--- Registro de Profesor ---");

            int num = leerEnteroPositivo("Número de trabajador: ");
            String nombre = leerSoloLetras("Nombre completo (solo letras): ");

            // Niveles que da (múltiples)
            String[] niveles = leerOpcionesMultiples(
                    "Niveles que da (puede elegir más de uno): ",
                    Catalogos.NIVELES
            );

            // Categorías que da (múltiples)
            String[] categorias = leerOpcionesMultiples(
                    "Categorías que da (puede elegir más de una): ",
                    Catalogos.CATEGORIAS
            );

            // Título que posee (texto, sin números)
            String titulo = leerSoloLetras("Título que posee (por ejemplo: 'Licenciatura en ...'): ");

            // Año del título (rango razonable)
            int anioActual = java.time.LocalDate.now().getYear();
            int anioTitulo = leerEnteroEnRango(
                    "Año de obtención del título (1900-" + anioActual + "): ",
                    1900, anioActual
            );

            Profesor p = new Profesor();
            p.setNumeroTrabajador(num);
            p.setNombreCompleto(nombre);
            p.setNivelesQueDa(niveles);
            p.setCategoriasQueDa(categorias);
            p.setTitulo(titulo);
            p.setAnioTitulo(anioTitulo);

            gestorProfesores.registrarProfesor(p);
            System.out.println("Profesor registrado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al registrar profesor: " + e.getMessage());
        }
    }

    /**
     * Muestra en consola el listado de todos los profesores registrados,
     * incluyendo su número de trabajador, nombre, niveles, categorías
     * y datos del título. Si no hay profesores, informa al usuario.
     */
    private void listarProfesores() {
        Profesor[] profesores = gestorProfesores.listarProfesores();
        System.out.println("\nProfesores registrados:");

        if (profesores == null) {
            System.out.println("No hay profesores registrados.");
            return;
        }

        boolean hay = false;
        for (int i = 0; i < profesores.length; i++) {
            Profesor p = profesores[i];
            if (p != null) {
                hay = true;
                System.out.println("----------------------------------------");
                System.out.println("ID (núm. trabajador): " + p.getId());
                System.out.println("Nombre:              " + p.getNombreCompleto());

                // Niveles que da
                System.out.print("Niveles que da:      ");
                String[] niveles = p.getNivelesQueDa();
                if (niveles != null && niveles.length > 0) {
                    for (int j = 0; j < niveles.length; j++) {
                        if (niveles[j] != null) {
                            System.out.print(niveles[j]);
                            if (j < niveles.length - 1) System.out.print(", ");
                        }
                    }
                    System.out.println();
                } else {
                    System.out.println("No especificado");
                }

                // Categorías que da
                System.out.print("Categorías que da:   ");
                String[] cats = p.getCategoriasQueDa();
                if (cats != null && cats.length > 0) {
                    for (int j = 0; j < cats.length; j++) {
                        if (cats[j] != null) {
                            System.out.print(cats[j]);
                            if (j < cats.length - 1) System.out.print(", ");
                        }
                    }
                    System.out.println();
                } else {
                    System.out.println("No especificado");
                }

                System.out.println("Título:              " + p.getTitulo());
                System.out.println("Año del título:      " + p.getAnioTitulo());
            }
        }

        if (!hay) {
            System.out.println("No hay profesores registrados.");
        }
    }

    /**
     * Edita los datos de un profesor existente sin modificar su número
     * de trabajador. Permite cambiar nombre, niveles, categorías,
     * título y año del título de manera interactiva.
     */
    private void editarProfesor() {
        try {
            System.out.println("\n--- Edición de Profesor ---");
            int num = leerEnteroPositivo("Número de trabajador del profesor a editar: ");

            Profesor p = gestorProfesores.buscarProfesor(num);

            int opcion;
            do {
                System.out.println("\nProfesor actual:");
                System.out.println("ID (trabajador): " + p.getId());
                System.out.println("1) Nombre:            " + p.getNombreCompleto());
                System.out.print  ("2) Niveles que da:    ");
                String[] niveles = p.getNivelesQueDa();
                if (niveles != null && niveles.length > 0) {
                    for (int i = 0; i < niveles.length; i++) {
                        if (niveles[i] != null) {
                            System.out.print(niveles[i]);
                            if (i < niveles.length - 1) System.out.print(", ");
                        }
                    }
                } else {
                    System.out.print("(ninguno)");
                }
                System.out.println();

                System.out.print("3) Categorías que da: ");
                String[] cats = p.getCategoriasQueDa();
                if (cats != null && cats.length > 0) {
                    for (int i = 0; i < cats.length; i++) {
                        if (cats[i] != null) {
                            System.out.print(cats[i]);
                            if (i < cats.length - 1) System.out.print(", ");
                        }
                    }
                } else {
                    System.out.print("(ninguna)");
                }
                System.out.println();

                System.out.println("4) Título:            " + p.getTitulo());
                System.out.println("5) Año del título:    " + p.getAnioTitulo());
                System.out.println("0) Terminar edición");
                System.out.print("Elige el campo a modificar: ");

                opcion = leerEntero();

                switch (opcion) {
                    case 1 -> {
                        String nombre = leerSoloLetras("Nuevo nombre completo (solo letras): ");
                        p.setNombreCompleto(nombre);
                        System.out.println("Nombre actualizado.");
                    }
                    case 2 -> {
                        String[] nuevosNiveles = leerOpcionesMultiples(
                                "Nuevos niveles que da:", Catalogos.NIVELES);
                        p.setNivelesQueDa(nuevosNiveles);
                        System.out.println("Niveles actualizados.");
                    }
                    case 3 -> {
                        String[] nuevasCategorias = leerOpcionesMultiples(
                                "Nuevas categorías que da:", Catalogos.CATEGORIAS);
                        p.setCategoriasQueDa(nuevasCategorias);
                        System.out.println("Categorías actualizadas.");
                    }
                    case 4 -> {
                        String titulo = leerSoloLetras("Nuevo título que posee: ");
                        p.setTitulo(titulo);
                        System.out.println("Título actualizado.");
                    }
                    case 5 -> {
                        int anioActual = java.time.LocalDate.now().getYear();
                        int anio = leerEnteroEnRango(
                                "Nuevo año de obtención del título (1900-" + anioActual + "): ",
                                1900, anioActual);
                        p.setAnioTitulo(anio);
                        System.out.println("Año de título actualizado.");
                    }
                    case 0 -> System.out.println("Fin de la edición de profesor.");
                    default -> System.out.println("Opción inválida.");
                }

            } while (opcion != 0);

        } catch (NoEncontradoException e) {
            System.out.println("No se encontró un profesor con ese número de trabajador.");
        } catch (Exception e) {
            System.out.println("Error al editar profesor: " + e.getMessage());
        }
    }

    /**
     * Elimina un profesor a partir de su número de trabajador,
     * delegando en el gestor la verificación de que no esté asignado
     * a ningún curso.
     */
    private void eliminarProfesor() {
        try {
            System.out.println("\n--- Eliminación de Profesor ---");
            int num = leerEnteroPositivo("Número de trabajador del profesor a eliminar: ");

            gestorProfesores.eliminarProfesor(num);
            System.out.println("Profesor eliminado correctamente.");

        } catch (EliminacionNoPermitidaException e) {
            System.out.println("No se puede eliminar: " + e.getMessage());
        } catch (NoEncontradoException e) {
            System.out.println("No se encontró un profesor con ese número de trabajador.");
        } catch (Exception e) {
            System.out.println("Error al eliminar profesor: " + e.getMessage());
        }
    }

    // ================== MENÚ ALUMNOS ==================

    /**
     * Muestra el submenú de gestión de alumnos y permite
     * registrar, listar, editar y eliminar registros de alumnos.
     * Se mantiene en ejecución hasta que el usuario decide volver.
     */
    public void menuAlumnos() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Alumnos ---");
            System.out.println("1. Registrar alumno");
            System.out.println("2. Listar alumnos");
            System.out.println("3. Editar alumno");     
            System.out.println("4. Eliminar alumno");  
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> registrarAlumno();
                case 2 -> listarAlumnos();
                case 3 -> editarAlumno();     
                case 4 -> eliminarAlumno();   
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    /**
     * Captura y registra un nuevo alumno en el sistema,
     * validando número de cuenta, nombre, edad y escuela de procedencia.
     */
    private void registrarAlumno() {
        try {
            System.out.println("\n--- Registro de Alumno ---");
            int num = leerEnteroPositivo("Número de cuenta: ");
            String nombre = leerSoloLetras("Nombre completo: ");
            int edad = leerEnteroEnRango("Edad: ", 1, 100);;
            String escuela = leerSoloLetras("Escuela de procedencia: ");

            Alumno a = new Alumno(nombre, num, edad, escuela);
            gestorAlumnos.registrarAlumno(a);
            System.out.println("Alumno registrado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al registrar alumno: " + e.getMessage());
        }
    }

    /**
     * Muestra el listado de alumnos registrados con sus
     * datos básicos. Si no hay alumnos, informa al usuario.
     */
    private void listarAlumnos() {
        Alumno[] alumnos = gestorAlumnos.listarAlumnos();
        System.out.println("\nAlumnos registrados:");

        if (alumnos == null) {
            System.out.println("No hay alumnos registrados.");
            return;
        }

        boolean hay = false;
        for (int i = 0; i < alumnos.length; i++) {
            Alumno a = alumnos[i];
            if (a != null) {
                hay = true;
                System.out.println("----------------------------------------");
                System.out.println("Número de cuenta:    " + a.getId());
                System.out.println("Nombre:              " + a.getNombreCompleto());
                System.out.println("Edad:                " + a.getEdad());
                System.out.println("Escuela de procedencia: " + a.getEscuelaProcedencia());
            }
        }

        if (!hay) {
            System.out.println("No hay alumnos registrados.");
        }
    }

    /**
     * Edita selectivamente los datos de un alumno existente,
     * permitiendo modificar nombre, edad y escuela de procedencia.
     */
    private void editarAlumno() {
        try {
            System.out.println("\n--- Edición de Alumno ---");
            int num = leerEnteroPositivo("Número de cuenta del alumno a editar: ");

            Alumno a = gestorAlumnos.buscarAlumno(num);

            int opcion;
            do {
                System.out.println("\nAlumno actual:");
                System.out.println("Número de cuenta: " + a.getId());
                System.out.println("1) Nombre:              " + a.getNombreCompleto());
                System.out.println("2) Edad:                " + a.getEdad());
                System.out.println("3) Escuela procedencia: " + a.getEscuelaProcedencia());
                System.out.println("0) Terminar edición");
                System.out.print("Elige el campo a modificar: ");

                opcion = leerEntero();

                switch (opcion) {
                    case 1 -> {
                        String nombre = leerSoloLetras("Nuevo nombre completo (solo letras): ");
                        a.setNombreCompleto(nombre);
                        System.out.println("Nombre actualizado.");
                    }
                    case 2 -> {
                        int edad = leerEnteroEnRango("Nueva edad (0-120): ", 0, 120);
                        a.setEdad(edad);
                        System.out.println("Edad actualizada.");
                    }
                    case 3 -> {
                        String escuela = leerSoloLetras("Nueva escuela de procedencia: ");
                        a.setEscuelaProcedencia(escuela);
                        System.out.println("Escuela actualizada.");
                    }
                    case 0 -> System.out.println("Fin de la edición de alumno.");
                    default -> System.out.println("Opción inválida.");
                }

            } while (opcion != 0);

        } catch (NoEncontradoException e) {
            System.out.println("No se encontró un alumno con ese número de cuenta.");
        } catch (Exception e) {
            System.out.println("Error al editar alumno: " + e.getMessage());
        }
    }

    /**
     * Elimina un alumno identificado por su número de cuenta,
     * delegando al gestor la verificación de que no esté vinculado
     * a cursos que impidan su eliminación.
     */
    private void eliminarAlumno() {
        try {
            System.out.println("\n--- Eliminación de Alumno ---");
            int num = leerEnteroPositivo("Número de cuenta del alumno a eliminar: ");

            gestorAlumnos.eliminarAlumno(num);
            System.out.println("Alumno eliminado correctamente.");

        } catch (EliminacionNoPermitidaException e) {
            System.out.println("No se puede eliminar: " + e.getMessage());
        } catch (NoEncontradoException e) {
            System.out.println("No se encontró un alumno con ese número de cuenta.");
        } catch (Exception e) {
            System.out.println("Error al eliminar alumno: " + e.getMessage());
        }
    }

    // ================== MENÚ CURSOS ==================

    /**
     * Muestra el submenú de gestión de cursos y permite registrar,
     * listar, asignar profesor, inscribir y desinscribir alumnos,
     * reasignar profesor, editar y eliminar cursos, así como ver
     * la lista de alumnos de un curso.
     */
    public void menuCursos() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Cursos ---");
            System.out.println("1. Registrar curso");
            System.out.println("2. Listar cursos");
            System.out.println("3. Asignar profesor a curso");
            System.out.println("4. Inscribir alumno en curso");
            System.out.println("5. Desinscribir alumno de curso");
            System.out.println("6. Reasignar profesor a curso");
            System.out.println("7. Editar curso");           
            System.out.println("8. Eliminar curso");         
            System.out.println("9. Ver alumnos de un curso");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> registrarCurso();
                case 2 -> listarCursos();
                case 3 -> asignarProfesorACurso();
                case 4 -> inscribirAlumnoEnCurso();
                case 5 -> desinscribirAlumnoDeCurso();
                case 6 -> reasignarProfesorACurso();
                case 7 -> editarCurso();          
                case 8 -> eliminarCurso();       
                case 9 -> verAlumnosDeCurso();    
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    /**
     * Captura todos los datos de un curso y los valida de forma iterativa:
     * identificador, nombre, categoría, nivel, horario, plataforma,
     * cupo máximo, descripción y las fechas de inscripciones y del curso.
     * Una vez construida la entidad, pide al gestor que registre el curso.
     */
    private void registrarCurso() {
        try {
            System.out.println("\n--- Registro de Curso ---");

            int id = leerEnteroPositivo("ID del curso: ");
            String nombre = leerNoVacio("Nombre del curso: ");

            // Catálogo: categoría
            String categoria = leerOpcionCatalogo(
                    "Categoría (elige una opción): ",
                    Catalogos.CATEGORIAS
            );

            // Catálogo: nivel
            String nivel = leerOpcionCatalogo(
                    "Nivel (elige una opción): ",
                    Catalogos.NIVELES
            );

            // Horario con formato y rango validado
            String horario = leerHorarioValido("Horario (ej. 07:00-09:00, entre 7 y 21h): ");

            // Plataforma válida
            String plataforma = leerOpcionCatalogo(
                    "Plataforma (elige una opción): ",
                    Catalogos.PLATAFORMAS
            );

            int max = leerEnteroEnRango("Cupo máximo (1-500): ", 1, 500);
            String desc = leerNoVacio("Descripción breve: ");

            System.out.println("\n--- Fechas del curso (formato DD-MM-AAAA) ---");

            Fecha hoy = Fecha.hoy();
            Fecha fechaIniIns;
            Fecha fechaFinIns;
            Fecha fechaIniCurso;
            Fecha fechaFinCurso;

            // 1) Inicio de inscripciones: no anterior a hoy
            fechaIniIns = leerFechaNoAnteriorAHoy("Fecha inicio de inscripciones: ", hoy);

            // 2) Fin de inscripciones: no anterior a hoy y no antes del inicio
            do {
                fechaFinIns = leerFechaNoAnteriorAHoy("Fecha fin de inscripciones: ", hoy);
                if (fechaFinIns.esAnteriorA(fechaIniIns)) {
                    System.out.println("La fecha de fin de inscripciones no puede ser anterior al inicio de inscripciones.");
                }
            } while (fechaFinIns.esAnteriorA(fechaIniIns));

            // 3) Inicio del curso: no anterior a hoy y DEBE ser posterior al fin de inscripciones
            do {
                fechaIniCurso = leerFechaNoAnteriorAHoy("Fecha inicio del curso: ", hoy);
                if (!fechaIniCurso.esPosteriorA(fechaFinIns)) {
                    System.out.println("La fecha de inicio del curso debe ser posterior a la fecha de fin de inscripciones.");
                    System.out.println("No pueden ser el mismo día ni antes.");
                }
            } while (!fechaIniCurso.esPosteriorA(fechaFinIns));

            // 4) Fin del curso: no anterior a hoy y no antes del inicio del curso
            do {
                fechaFinCurso = leerFechaNoAnteriorAHoy("Fecha fin del curso: ", hoy);
                if (fechaFinCurso.esAnteriorA(fechaIniCurso)) {
                    System.out.println("La fecha de fin del curso no puede ser anterior a la fecha de inicio del curso.");
                }
            } while (fechaFinCurso.esAnteriorA(fechaIniCurso));

            Curso c = new Curso(id, nombre, categoria, nivel,
                    horario, plataforma, max, desc,
                    fechaIniIns, fechaFinIns, fechaIniCurso, fechaFinCurso);

            gestorCursos.registrarCurso(c);
            System.out.println("Curso registrado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al registrar curso: " + e.getMessage());
        }
    }

    /**
     * Muestra en pantalla la lista de cursos registrados junto con
     * sus datos principales, profesor asignado y alumnos inscritos.
     * Si no hay cursos, informa al usuario.
     */
    private void listarCursos() {
        Curso[] cursos = gestorCursos.listarCursos();
        System.out.println("\nCursos registrados:");

        if (cursos == null) {
            System.out.println("No hay cursos registrados.");
            return;
        }

        boolean hay = false;
        for (int i = 0; i < cursos.length; i++) {
            Curso c = cursos[i];
            if (c != null) {
                hay = true;
                System.out.println("========================================");
                System.out.println("ID del curso:        " + c.getId());
                System.out.println("Nombre:              " + c.getNombre());
                System.out.println("Nivel:               " + c.getNivel());
                System.out.println("Categoría:           " + c.getCategoria());
                System.out.println("Plataforma:          " + c.getPlataforma());
                System.out.println("Horario:             " + c.getHorario());
                System.out.println("Descripción:         " + c.getDescripcion());
                System.out.println("Inicio inscripciones:" + c.getFechaInicioInscripciones());
                System.out.println("Fin inscripciones:   " + c.getFechaFinInscripciones());
                System.out.println("Inicio curso:        " + c.getFechaInicioCurso());
                System.out.println("Fin curso:           " + c.getFechaFinCurso());

                // Profesor asignado
                Profesor p = c.getProfesorAsignado();
                if (p != null) {
                    System.out.println("Profesor asignado:   " + p.getId()
                            + " | " + p.getNombreCompleto());
                } else {
                    System.out.println("Profesor asignado:   (ninguno)");
                }

                // Alumnos inscritos
                Alumno[] alumnos = c.getAlumnosInscritos();
                int nAlumnos = c.getContadorAlumnos();
                System.out.println("Alumnos inscritos:   " + nAlumnos);

                if (nAlumnos > 0 && alumnos != null) {
                    for (int j = 0; j < nAlumnos; j++) {
                        if (alumnos[j] != null) {
                            System.out.println("   - " + alumnos[j].getId()
                                    + " | " + alumnos[j].getNombreCompleto());
                        }
                    }
                }
            }
        }

        if (!hay) {
            System.out.println("No hay cursos registrados.");
        }
    }

    /**
     * Permite editar campos específicos de un curso, siempre que
     * éste cumpla las condiciones definidas para ser editable
     * completamente. Permite modificar nombre, categoría, nivel,
     * horario, plataforma, descripción y fechas.
     */
    private void editarCurso() {
        try {
            System.out.println("\n--- Edición de Curso ---");
            int id = leerEnteroPositivo("ID del curso a editar: ");

            Curso c = gestorCursos.buscarCurso(id);
            Fecha hoy = Fecha.hoy();

            if (!c.puedeEditarCompletamente(hoy)) {
                System.out.println("No se puede editar completamente este curso.");
                System.out.println("Requisito: que no esté en clases y que tenga más de 3 alumnos.");
                return;
            }

            int opcion;
            do {
                System.out.println("\nCurso actual:");
                System.out.println("ID:                  " + c.getId());
                System.out.println("1) Nombre:           " + c.getNombre());
                System.out.println("2) Categoría:        " + c.getCategoria());
                System.out.println("3) Nivel:            " + c.getNivel());
                System.out.println("4) Horario:          " + c.getHorario());
                System.out.println("5) Plataforma:       " + c.getPlataforma());
                System.out.println("6) Descripción:      " + c.getDescripcion());
                System.out.println("7) Fechas (inscripciones/curso)");
                System.out.println("0) Terminar edición");
                System.out.print("Elige el campo a modificar: ");

                opcion = leerEntero();

                switch (opcion) {
                    case 1 -> {
                        String nombre = leerNoVacio("Nuevo nombre del curso: ");
                        c.setNombre(nombre);
                        System.out.println("Nombre actualizado.");
                    }
                    case 2 -> {
                        String categoria = leerOpcionCatalogo(
                                "Nueva categoría:", Catalogos.CATEGORIAS);
                        c.setCategoria(categoria);
                        System.out.println("Categoría actualizada.");
                    }
                    case 3 -> {
                        String nivel = leerOpcionCatalogo(
                                "Nuevo nivel:", Catalogos.NIVELES);
                        c.setNivel(nivel);
                        System.out.println("Nivel actualizado.");
                    }
                    case 4 -> {
                        String horario = leerHorarioValido(
                                "Nuevo horario (ej. 07:00-09:00, entre 7 y 21): ");
                        c.setHorario(horario);
                        System.out.println("Horario actualizado.");
                    }
                    case 5 -> {
                        String plataforma = leerOpcionCatalogo(
                                "Nueva plataforma:", Catalogos.PLATAFORMAS);
                        c.setPlataforma(plataforma);
                        System.out.println("Plataforma actualizada.");
                    }
                    case 6 -> {
                        String desc = leerNoVacio("Nueva descripción: ");
                        c.setDescripcion(desc);
                        System.out.println("Descripción actualizada.");
                    }
                    case 7 -> {
                        // Reutilizamos la lógica de fechas con las mismas reglas
                        System.out.println("\n--- Nuevas fechas del curso (formato DD-MM-AAAA) ---");
                        Fecha hoy2 = Fecha.hoy();
                        Fecha fechaIniIns;
                        Fecha fechaFinIns;
                        Fecha fechaIniCurso;
                        Fecha fechaFinCurso;

                        fechaIniIns = leerFechaNoAnteriorAHoy("Fecha inicio de inscripciones: ", hoy2);

                        do {
                            fechaFinIns = leerFechaNoAnteriorAHoy("Fecha fin de inscripciones: ", hoy2);
                            if (fechaFinIns.esAnteriorA(fechaIniIns)) {
                                System.out.println("La fecha de fin de inscripciones no puede ser anterior al inicio.");
                            }
                        } while (fechaFinIns.esAnteriorA(fechaIniIns));

                        do {
                            fechaIniCurso = leerFechaNoAnteriorAHoy("Fecha inicio del curso: ", hoy2);
                            if (!fechaIniCurso.esPosteriorA(fechaFinIns)) {
                                System.out.println("La fecha de inicio del curso debe ser posterior al fin de inscripciones.");
                            }
                        } while (!fechaIniCurso.esPosteriorA(fechaFinIns));

                        do {
                            fechaFinCurso = leerFechaNoAnteriorAHoy("Fecha fin del curso: ", hoy2);
                            if (fechaFinCurso.esAnteriorA(fechaIniCurso)) {
                                System.out.println("La fecha de fin del curso no puede ser anterior a la de inicio.");
                            }
                        } while (fechaFinCurso.esAnteriorA(fechaIniCurso));

                        c.setFechaInicioInscripciones(fechaIniIns);
                        c.setFechaFinInscripciones(fechaFinIns);
                        c.setFechaInicioCurso(fechaIniCurso);
                        c.setFechaFinCurso(fechaFinCurso);

                        System.out.println("Fechas del curso actualizadas.");
                    }
                    case 0 -> System.out.println("Fin de la edición de curso.");
                    default -> System.out.println("Opción inválida.");
                }

            } while (opcion != 0);

        } catch (NoEncontradoException e) {
            System.out.println("No se encontró un curso con ese ID.");
        } catch (Exception e) {
            System.out.println("Error al editar curso: " + e.getMessage());
        }
    }

    /**
     * Elimina un curso identificado por su ID,
     * solicitando al gestor que verifique si cumple con las
     * condiciones para su eliminación.
     */
    private void eliminarCurso() {
        try {
            System.out.println("\n--- Eliminación de Curso ---");
            int id = leerEnteroPositivo("ID del curso a eliminar: ");
            Fecha hoy = Fecha.hoy();

            gestorCursos.eliminarCurso(id, hoy);
            System.out.println("Curso eliminado correctamente.");

        } catch (EliminacionNoPermitidaException e) {
            System.out.println("No se puede eliminar el curso: " + e.getMessage());
        } catch (NoEncontradoException e) {
            System.out.println("No se encontró un curso con ese ID.");
        } catch (Exception e) {
            System.out.println("Error al eliminar curso: " + e.getMessage());
        }
    }

    /**
     * Asigna un profesor a un curso a partir de los identificadores
     * capturados por consola. Utiliza la fecha actual para validar
     * las condiciones de la asignación.
     */
    private void asignarProfesorACurso() {
        try {
            System.out.println("\n--- Asignar Profesor a Curso ---");
            int idCurso = leerEnteroPositivo("ID del curso: ");
            int numProf = leerEnteroPositivo("Número de trabajador del profesor: ");

            Fecha hoy = Fecha.hoy();  // usamos la fecha actual del sistema

            gestorCursos.asignarProfesor(idCurso, numProf, hoy);
            System.out.println("Profesor asignado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al asignar profesor: " + e.getMessage());
        }
    }

    /**
     * Inscribe un alumno en un curso a partir de los identificadores
     * capturados. Delega en el gestor la validación y registro
     * de la inscripción.
     */
    private void inscribirAlumnoEnCurso() {
        try {
            System.out.println("\n--- Inscribir Alumno en Curso ---");
            int idCurso = leerEnteroPositivo("ID del curso: ");
            int numCuenta = leerEnteroPositivo("Número de cuenta del alumno: ");

            Fecha hoy = Fecha.hoy();  // fecha actual del sistema

            gestorCursos.inscribirAlumnoEnCurso(idCurso, numCuenta, hoy);
            System.out.println("Alumno inscrito correctamente.");

        } catch (Exception e) {
            System.out.println("Error al inscribir alumno: " + e.getMessage());
        }
    }

    /**
     * Desinscribe a un alumno de un curso, identificados por su ID
     * de curso y número de cuenta. La lógica y restricciones se
     * aplican desde el gestor de cursos.
     */
    private void desinscribirAlumnoDeCurso() {
        try {
            System.out.println("\n--- Desinscribir Alumno de Curso ---");
            int idCurso = leerEnteroPositivo("ID del curso: ");
            int numCuenta = leerEnteroPositivo("Número de cuenta del alumno: ");

            Fecha hoy = Fecha.hoy();

            gestorCursos.desinscribirAlumnoDeCurso(idCurso, numCuenta, hoy);
            System.out.println("Alumno desinscrito correctamente.");

        } catch (Exception e) {
            System.out.println("Error al desinscribir alumno: " + e.getMessage());
        }
    }

    /**
     * Reasigna el profesor de un curso a otro profesor,
     * utilizando la fecha actual para validar las condiciones
     * de la reasignación.
     */
    private void reasignarProfesorACurso() {
        try {
            System.out.println("\n--- Reasignar Profesor a Curso ---");
            int idCurso = leerEnteroPositivo("ID del curso: ");
            int numProf = leerEnteroPositivo("Número de trabajador del nuevo profesor: ");

            Fecha hoy = Fecha.hoy();  // usamos la fecha actual del sistema

            gestorCursos.reasignarProfesor(idCurso, numProf, hoy);
            System.out.println("Profesor reasignado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al reasignar profesor: " + e.getMessage());
        }
    }

    /**
     * Muestra en consola la lista de alumnos inscritos en un curso
     * específico. Si el curso no existe o no tiene alumnos, informa
     * al usuario.
     */
    private void verAlumnosDeCurso() {
        try {
            System.out.println("\n--- Lista de alumnos de un curso ---");
            int id = leerEnteroPositivo("ID del curso: ");

            Curso c = gestorCursos.buscarCurso(id);
            Alumno[] alumnos = c.getAlumnosInscritos();
            int n = c.getContadorAlumnos();

            System.out.println("Curso: " + c.getId() + " | " + c.getNombre());
            System.out.println("Alumnos inscritos: " + n);

            if (n == 0 || alumnos == null) {
                System.out.println("No hay alumnos inscritos en este curso.");
                return;
            }

            for (int i = 0; i < n; i++) {
                if (alumnos[i] != null) {
                    System.out.println("- " + alumnos[i].getId()
                            + " | " + alumnos[i].getNombreCompleto());
                }
            }

        } catch (NoEncontradoException e) {
            System.out.println("No se encontró un curso con ese ID.");
        } catch (Exception e) {
            System.out.println("Error al consultar alumnos del curso: " + e.getMessage());
        }
    }

    // ============= MÉTODOS DE LECTURA Y VALIDACIÓN =============

    /**
     * Lee un valor entero desde la entrada estándar.
     * Si el usuario escribe un valor no numérico, se le vuelve
     * a pedir hasta que proporcione un número válido.
     *
     * @return Entero leído desde la entrada.
     */
    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            scanner.next(); // descartar
            System.out.print("Ingresa un número válido: ");
        }
        int n = scanner.nextInt();
        scanner.nextLine(); // consumir salto
        return n;
    }

    /**
     * Lee un número entero positivo mostrando un mensaje personalizado.
     * Si el usuario proporciona un valor menor o igual a cero,
     * se le pide de nuevo.
     *
     * @param mensaje Texto que se muestra antes de la lectura.
     * @return Entero positivo.
     */
    private int leerEnteroPositivo(String mensaje) {
        int n;
        do {
            System.out.print(mensaje);
            n = leerEntero();
            if (n <= 0) {
                System.out.println("Debe ser un número positivo.");
            }
        } while (n <= 0);
        return n;
    }

    /**
     * Lee un número entero dentro de un rango específico.
     * Si el valor está fuera del rango, se vuelve a solicitar.
     *
     * @param mensaje Mensaje a mostrar antes de la lectura.
     * @param min     Valor mínimo permitido.
     * @param max     Valor máximo permitido.
     * @return Entero dentro del rango especificado.
     */
    private int leerEnteroEnRango(String mensaje, int min, int max) {
        int n;
        do {
            System.out.print(mensaje);
            n = leerEntero();
            if (n < min || n > max) {
                System.out.println("El valor debe estar entre " + min + " y " + max + ".");
            }
        } while (n < min || n > max);
        return n;
    }

    /**
     * Lee una línea de texto no vacía.
     * Si el usuario introduce una cadena vacía o solo espacios,
     * se le solicita nuevamente.
     *
     * @param mensaje Mensaje a mostrar.
     * @return Cadena no vacía.
     */
    private String leerNoVacio(String mensaje) {
        String s;
        do {
            System.out.print(mensaje);
            s = scanner.nextLine();
            if (s.trim().isEmpty()) {
                System.out.println("Este campo no puede quedar vacío.");
            }
        } while (s.trim().isEmpty());
        return s;
    }

    /**
     * Muestra las opciones de un catálogo y obliga al usuario
     * a elegir una opción válida, coincidente exactamente
     * con alguno de los valores del catálogo.
     *
     * @param mensaje  Mensaje a mostrar antes de listar el catálogo.
     * @param catalogo Arreglo de valores válidos.
     * @return Opción elegida por el usuario.
     */
    private String leerOpcionCatalogo(String mensaje, String[] catalogo) {
        String valor;
        boolean valido;
        do {
            System.out.println(mensaje);
            for (int i = 0; i < catalogo.length; i++) {
                System.out.println("  - " + catalogo[i]);
            }
            System.out.print("Escribe exactamente una de las opciones: ");
            valor = scanner.nextLine();
            valido = Catalogos.esValorValido(valor, catalogo);
            if (!valido) {
                System.out.println("Opción inválida, intenta de nuevo.");
            }
        } while (!valido);
        return valor;
    }

    /**
     * Solicita una fecha en formato DD-MM-AAAA y utiliza la clase Fecha
     * para validar el formato. En caso de error, se vuelve a solicitar.
     *
     * @param mensaje Mensaje a mostrar antes de pedir la fecha.
     * @return Objeto {@link Fecha} válido.
     */
    private Fecha leerFechaValida(String mensaje) {
        Fecha f = null;
        boolean valido = false;
        do {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            try {
                f = Fecha.fromString(texto);
                valido = true;
            } catch (FormatoInvalidoException e) {
                System.out.println("Fecha inválida, usa el formato DD-MM-AAAA. Error: " + e.getMessage());
            }
        } while (!valido);
        return f;
    }

    /**
     * Solicita un horario y lo valida usando el método
     * {Catalogos.validarHorario}. En caso de error,
     * se informa al usuario y se vuelve a pedir.
     *
     * @param mensaje Mensaje a mostrar.
     * @return Cadena que representa un horario válido.
     */
    private String leerHorarioValido(String mensaje) {
        String horario;
        boolean valido = false;
        do {
            System.out.print(mensaje);
            horario = scanner.nextLine();
            try {
                Catalogos.validarHorario(horario);
                valido = true;
            } catch (DatosInvalidosException e) {
                System.out.println("Horario inválido: " + e.getMessage());
            }
        } while (!valido);
        return horario;
    }

    /**
     * Lee una cadena que solo puede contener letras y espacios,
     * incluyendo caracteres acentuados y la letra ñ.
     * En caso de que se introduzcan otros caracteres, se vuelve a solicitar.
     *
     * @param mensaje Mensaje a mostrar.
     * @return Cadena con solo letras y espacios.
     */
    private String leerSoloLetras(String mensaje) {
        String s;
        boolean valido;
        do {
            System.out.print(mensaje);
            s = scanner.nextLine().trim();
            // Acepta letras (mayúsculas/minúsculas), acentos, ñ y espacios
            valido = s.matches("[a-zA-ZÁÉÍÓÚáéíóúÑñÜü\\s]+");
            if (!valido) {
                System.out.println("Solo se permiten letras y espacios. Intenta de nuevo.");
            }
        } while (!valido);
        return s;
    }

    /**
     * Lee una fecha válida cuyo valor no puede ser anterior a la fecha
     * actual proporcionada. Utiliza {@link #leerFechaValida(String)} para
     * validar el formato y luego compara contra la fecha dada.
     *
     * @param mensaje Mensaje a mostrar.
     * @param hoy     Fecha mínima permitida.
     * @return Fecha igual o posterior a {@code hoy}.
     */
    private Fecha leerFechaNoAnteriorAHoy(String mensaje, Fecha hoy) {
        Fecha f = null;
        boolean valido = false;
        do {
            f = leerFechaValida(mensaje);  // ya valida el formato
            if (f.esAnteriorA(hoy)) {
                System.out.println("La fecha no puede ser anterior a hoy (" + hoy + "). Intenta de nuevo.");
            } else {
                valido = true;
            }
        } while (!valido);
        return f;
    }

    /**
     * Lee una lista de opciones separadas por comas, todas pertenecientes
     * a un catálogo de valores válidos. Si alguna opción no es válida,
     * se solicita nuevamente la entrada completa.
     *
     * @param mensaje  Mensaje a mostrar.
     * @param catalogo Arreglo de opciones válidas.
     * @return Arreglo de cadenas con las opciones seleccionadas y normalizadas.
     */
    private String[] leerOpcionesMultiples(String mensaje, String[] catalogo) {
        String linea;
        boolean valido;
        String[] seleccion;

        do {
            System.out.println(mensaje);
            System.out.println("Opciones válidas:");
            for (int i = 0; i < catalogo.length; i++) {
                System.out.println("  - " + catalogo[i]);
            }
            System.out.print("Escribe una o más opciones (separadas por coma): ");
            linea = scanner.nextLine();
            String[] partes = linea.split(",");
            valido = true;

            for (int i = 0; i < partes.length; i++) {
                String valor = partes[i].trim();
                if (!Catalogos.esValorValido(valor, catalogo)) {
                    System.out.println("Valor inválido: '" + valor + "'. Intenta de nuevo.\n");
                    valido = false;
                    break;
                }
            }

            if (valido) {
                // Normalizamos los valores recortados
                seleccion = new String[partes.length];
                for (int i = 0; i < partes.length; i++) {
                    seleccion[i] = partes[i].trim();
                }
                return seleccion;
            }

        } while (true);
    }

}
