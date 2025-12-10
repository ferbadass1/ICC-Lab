/**
 * Clase Principal: SistemaGestionCursos
 *
 * Punto de entrada del programa. Configura repositorios, gestores,
 * carga datos persistidos y lanza el menú de consola.
 */
public class SistemaGestionCursos {

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

        GestorProfesores gestorProf = new GestorProfesores(repoProf, repoCur);
        GestorAlumnos gestorAlu = new GestorAlumnos(repoAlu, repoCur);
        GestorCursos gestorCur = new GestorCursos(repoCur, repoProf, repoAlu);

        MenuConsola menu = new MenuConsola(gestorCur, gestorProf, gestorAlu);
        menu.mostrarMenuPrincipal();

        try {
            repoProf.guardarTodo("profesores.txt");
            repoAlu.guardarTodo("alumnos.txt");
            repoCur.guardarTodo("cursos.txt");
        } catch (Exception e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }
}
