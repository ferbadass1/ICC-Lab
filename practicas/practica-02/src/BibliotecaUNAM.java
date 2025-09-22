import java.util.Scanner;

public class BibliotecaUNAM {

    // Instancias de clases importantes para el programa
    Biblioteca biblioteca = new Biblioteca();
    Scanner sc = new Scanner(System.in);

    // El método main es el punto de inicio del programa.
    public static void main(String[] args) {
        // Se crea un objeto de la clase actual para poder llamar a sus métodos no estáticos.
        BibliotecaUNAM mBibliotecaUNAM = new BibliotecaUNAM();
        mBibliotecaUNAM.iniciar();
    }

    // Este método contiene el bucle principal del programa y el menú de opciones.
    public void iniciar() {
        System.out.println(TextoColor.daColorTexto("===== Biblioteca Central de la UNAM =====", TextoColor.ROJO));
        int opcion;

        do {
            mostrarMenu();
            opcion = sc.nextInt();
            // Limpia el búfer del Scanner después de leer un número para evitar errores con nextLine().
            sc.nextLine();

            // Usa un 'switch' para ejecutar la acción correspondiente a la opción elegida.
            switch (opcion) {
                case 1: registrarLibro(); 
                    break;
                case 2: listarLibros();
                    break;
                case 3: buscarPorISBN(); 
                    break;
                case 4: buscarPorTitulo(); 
                    break;
                case 5: buscarPorAutor(); 
                    break;
                case 6: buscarPorGenero(); 
                    break;
                case 7:
                    System.out.println(TextoColor.daColorTexto("Saliendo del programa...", TextoColor.MORADO));
                    break;
                default:
                    System.out.println(TextoColor.daColorTexto("Opción inválida", TextoColor.ROJO));
            }
        } while (opcion != 7);
    }

    // Muestra el menú de opciones en la consola.
    private void mostrarMenu() {
        System.out.println(TextoColor.daColorTexto(
            "\nSeleccione una opción:\n" +
            "1. Registrar un libro\n" +
            "2. Listar los libros\n" +
            "3. Buscar libro por ISBN\n" +
            "4. Buscar libros por título\n" +
            "5. Buscar libros por autor\n" +
            "6. Buscar libros por género\n" +
            "7. Salir",
            TextoColor.AZUL
        ));
    }

    // Pide al usuario los datos de un libro y lo agrega a la biblioteca.
    private void registrarLibro() {
        System.out.println("Ingrese ISBN:");
        String isbn = sc.nextLine();

        System.out.println("Ingrese el título:");
        String titulo = sc.nextLine();

        System.out.println("Ingrese el autor:");
        String autor = sc.nextLine();

        System.out.println("Ingrese el género(s):");
        String genero = sc.nextLine();

        Libro libro = new Libro(isbn, autor, titulo, genero);
        if (biblioteca.agregaLibro(libro)) {
            System.out.println(TextoColor.daColorTexto("Libro registrado con éxito:\n" + libro, TextoColor.VERDE));
        } else {
            System.out.println(TextoColor.daColorTexto("Ya existe un libro con ese ISBN.", TextoColor.ROJO));
        }
    }

    // Muestra una lista de todos los libros registrados.
    private void listarLibros() {
        String todos = biblioteca.obtenTodosLosLibros("\n");
        if (todos != null) {
            System.out.println(TextoColor.daColorTexto("Libros en la biblioteca:\n" + todos, TextoColor.VERDE));
        } else {
            System.out.println(TextoColor.daColorTexto("No hay libros registrados.", TextoColor.ROJO));
        }
    }

    // Pide un ISBN y busca el libro correspondiente.
    private void buscarPorISBN() {
        System.out.println(TextoColor.daColorTexto("Ingrese ISBN:", TextoColor.AMARILLO));
        String isbn = sc.nextLine();
        Libro libro = biblioteca.obtenLibroPorISBN(isbn);
        if (libro != null) {
            System.out.println(TextoColor.daColorTexto("Libro encontrado:\n" + libro, TextoColor.VERDE));
        } else {
            System.out.println(TextoColor.daColorTexto("No se encontró libro con ese ISBN.", TextoColor.ROJO));
        }
    }

    // Métodos para buscar libros por título, autor o género.
    private void buscarPorTitulo() {
        System.out.println(TextoColor.daColorTexto("Ingrese título:", TextoColor.AMARILLO));
        String titulo = sc.nextLine();
        String resultado = biblioteca.filtraPor(Biblioteca.TITULO, titulo, "\n");
        imprimirResultados(resultado);
    }

    private void buscarPorAutor() {
        System.out.println(TextoColor.daColorTexto("Ingrese autor:", TextoColor.AMARILLO));
        String autor = sc.nextLine();
        String resultado = biblioteca.filtraPor(Biblioteca.AUTOR, autor, "\n");
        imprimirResultados(resultado);
    }

    private void buscarPorGenero() {
        System.out.println(TextoColor.daColorTexto("Ingrese género:", TextoColor.AMARILLO));
        String genero = sc.nextLine();
        String resultado = biblioteca.filtraPor(Biblioteca.GENERO, genero, "\n");
        imprimirResultados(resultado);
    }

    // Método auxiliar para imprimir los resultados de una búsqueda.
    private void imprimirResultados(String resultado) {
        if (resultado != null) {
            System.out.println(TextoColor.daColorTexto("Resultados encontrados:\n" + resultado, TextoColor.VERDE));
        } else {
            System.out.println(TextoColor.daColorTexto("No se encontraron resultados.", TextoColor.ROJO));
        }
    }
}