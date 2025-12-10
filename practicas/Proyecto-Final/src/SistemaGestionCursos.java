/**
 * Clase Principal: SistemaGestionCursos
 *
 * Punto de entrada del programa. Configura repositorios, gestores,
 * carga datos persistidos y lanza el menú de consola.
 */
public class SistemaGestionCursos {

    /**
     * Método principal del sistema.
     *
     * Crea los repositorios en memoria, intenta cargar los datos desde
     * archivos de texto, reconstruye las relaciones entre cursos, profesores
     * y alumnos, configura los gestores y finalmente muestra el menú de
     * consola para interactuar con el usuario. Al finalizar, guarda de nuevo
     * la información en los archivos correspondientes.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        RepositorioProfesores repoProf = new RepositorioProfesores(100);
        RepositorioAlumnos repoAlu = new RepositorioAlumnos(200);
        RepositorioCursos repoCur = new RepositorioCursos(100);

        try {
            repoProf.cargarTodo("profesores.txt");
            repoAlu.cargarTodo("alumnos.txt");
            repoCur.cargarTodo("cursos.txt");
        } catch (Exception e) {
            System.out.println("Aviso al cargar archivos: " + e.getMessage());
        }

        // Re-conectar profesor y alumnos a cada curso a partir de los IDs guardados
        Curso[] cursos = repoCur.getTodos();
        for (int i = 0; i < cursos.length; i++) {
            Curso c = cursos[i];
            if (c == null) continue;

            // Profesor
            int idProf = c.getIdProfesorGuardado();
            if (idProf != -1) {
                try {
                    Profesor p = repoProf.buscarPorId(idProf);
                    c.setProfesorAsignado(p);
                } catch (NoEncontradoException e) {
                    // profesor no existe, lo ignoramos
                }
            }

            // Alumnos
            int[] idsAlumnos = c.getIdsAlumnosGuardados();
            int totalGuardados = c.getContadorAlumnosGuardados();
            for (int j = 0; j < totalGuardados; j++) {
                int idAlu = idsAlumnos[j];
                try {
                    Alumno a = repoAlu.buscarPorId(idAlu);
                    // agregamos relación en curso y en alumno SIN revalidar fechas
                    if (c.getContadorAlumnos() < c.getNumeroEstudiantesMax()) {
                        c.alumnosInscritos[c.contadorAlumnos] = a;
                        c.contadorAlumnos++;
                        a.agregarCurso(c);
                    }
                } catch (NoEncontradoException e) {
                    // alumno no encontrado, lo ignoramos
                }
            }
        }

        GestorProfesores gestorProf = new GestorProfesores(repoProf, repoCur);
        GestorAlumnos gestorAlu = new GestorAlumnos(repoAlu, repoCur);
        GestorCursos gestorCur = new GestorCursos(repoCur, repoProf, repoAlu);

        MenuConsola menu = new MenuConsola(gestorCur, gestorProf, gestorAlu);
        menu.mostrarMenuPrincipal();

        try {
            repoProf.guardarTodo("profesores.txt");
            repoAlu.guardarTodo("alumnos.txt");
            repoCur.guardarTodo("cursos.txt");
            System.out.println("Datos guardados correctamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }
}
