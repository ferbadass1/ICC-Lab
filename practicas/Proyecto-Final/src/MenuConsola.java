import java.util.Scanner;

/**
 * Clase Vista: MenuConsola
 *
 * Implementa la interfaz de texto para interactuar con el usuario
 * y delega la lógica en los gestores. Incluye validaciones para
 * capturar datos válidos de forma iterativa.
 */
public class MenuConsola {

    private GestorCursos gestorCursos;
    private GestorProfesores gestorProfesores;
    private GestorAlumnos gestorAlumnos;
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
     * Método Calculador: mostrarMenuPrincipal
     *
     * Muestra el menú principal y redirige a los submenús.
     */
    public void mostrarMenuPrincipal() {
        int opcion = -1;
        do {
            System.out.println("\n=== SISTEMA DE GESTION DE CURSOS ===");
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

    public void menuProfesores() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Profesores ---");
            System.out.println("1. Registrar profesor");
            System.out.println("2. Listar profesores");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> registrarProfesor();
                case 2 -> listarProfesores();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    /**
     * Método Calculador: registrarProfesor
     *
     * Captura los datos de un profesor y los valida de forma iterativa.
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


    private void listarProfesores() {
        Profesor[] profesores = gestorProfesores.listarProfesores();
        System.out.println("\nProfesores registrados:");

        if (profesores == null) {
            System.out.println("No hay profesores registrados.");
            return;
        }

        boolean hay = false;
        for (int i = 0; i < profesores.length; i++) {
            if (profesores[i] != null) {
                hay = true;
                System.out.println("- " + profesores[i].getId()
                        + " | " + profesores[i].getNombreCompleto());
            }
    }

    if (!hay) {
        System.out.println("No hay profesores registrados.");
    }
}


    // ================== MENÚ ALUMNOS ==================

    public void menuAlumnos() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Alumnos ---");
            System.out.println("1. Registrar alumno");
            System.out.println("2. Listar alumnos");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> registrarAlumno();
                case 2 -> listarAlumnos();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    /**
     * Método Calculador: registrarAlumno
     */
    private void registrarAlumno() {
        try {
            System.out.println("\n--- Registro de Alumno ---");
            int num = leerEnteroPositivo("Número de cuenta: ");
            String nombre = leerSoloLetras("Nombre completo (solo letras): ");
            int edad = leerEnteroEnRango("Edad (>= 15): ", 15, 120);
            String escuela = leerSoloLetras("Escuela de procedencia (solo letras): ");

            Alumno a = new Alumno(nombre, num, edad, escuela);
            gestorAlumnos.registrarAlumno(a);
            System.out.println("Alumno registrado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al registrar alumno: " + e.getMessage());
        }
    }

    private void listarAlumnos() {
        Alumno[] alumnos = gestorAlumnos.listarAlumnos();
        System.out.println("\nAlumnos registrados:");

        if (alumnos == null) {
            System.out.println("No hay alumnos registrados.");
            return;
        }

        boolean hay = false;
        for (int i = 0; i < alumnos.length; i++) {
            if (alumnos[i] != null) {
                hay = true;
                System.out.println("- " + alumnos[i].getId()
                        + " | " + alumnos[i].getNombreCompleto());
            }
        }

        if (!hay) {
            System.out.println("No hay alumnos registrados.");
        }
    }


    // ================== MENÚ CURSOS ==================

    public void menuCursos() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Cursos ---");
            System.out.println("1. Registrar curso");
            System.out.println("2. Listar cursos");
            System.out.println("3. Asignar profesor a curso");
            System.out.println("4. Inscribir alumno en curso");
            System.out.println("5. Desinscribir alumno de curso");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> registrarCurso();
                case 2 -> listarCursos();
                case 3 -> asignarProfesorACurso();
                case 4 -> inscribirAlumnoEnCurso();
                case 5 -> desinscribirAlumnoDeCurso();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    /**
     * Método Calculador: registrarCurso
     *
     * Captura todos los datos de un curso, con validación iterativa:
     * id, nombre, categoría, nivel, horario, plataforma, cupo, descripción
     * y las 4 fechas (inicio/fin inscripciones, inicio/fin curso).
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

    private void listarCursos() {
        Curso[] cursos = gestorCursos.listarCursos();
        System.out.println("\nCursos registrados:");

        if (cursos == null) {
            System.out.println("No hay cursos registrados.");
            return;
        }

        boolean hay = false;
        for (int i = 0; i < cursos.length; i++) {
            if (cursos[i] != null) {
                hay = true;
                System.out.println("- " + cursos[i].getId()
                        + " | " + cursos[i].toLineaTexto());
            }
        }

        if (!hay) {
            System.out.println("No hay cursos registrados.");
        }
    }


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

    // ============= MÉTODOS DE LECTURA Y VALIDACIÓN =============

    /**
     * Lee un entero de la entrada estándar.
     * Si el usuario escribe algo inválido, se le pide de nuevo.
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
     * Lee un entero positivo con mensaje.
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
     * Lee un entero en un rango [min, max].
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
     * Lee una línea no vacía.
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
     * a elegir una válida.
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
     * Solicita una fecha y la valida con el formato DD-MM-AAAA.
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
     * Solicita un horario y lo valida con las reglas de Catalogos.validarHorario.
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
     * Lee una línea que solo puede contener letras y espacios.
     * No se permiten números ni otros caracteres.
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
     * Lee una fecha válida en formato DD-MM-AAAA que no puede ser anterior a hoy.
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
 * a un catálogo de valores válidos.
 *
 * @param mensaje  Mensaje a mostrar.
 * @param catalogo Arreglo de opciones válidas.
 * @return Arreglo de strings con las opciones seleccionadas.
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
        System.out.print("Escribe una o más opciones separadas por coma: ");
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
