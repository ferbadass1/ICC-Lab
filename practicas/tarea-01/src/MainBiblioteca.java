import java.util.Scanner;

/**
 * Clase principal para demostrar el funcionamiento del sistema de biblioteca.
 * Utiliza un menú interactivo para que el usuario pruebe las funciones.
 */
public class MainBiblioteca {

    public static void main(String[] args) {
        // Inicializamos la biblioteca y el scanner
        Biblioteca miBiblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        // Datos de prueba para no empezar desde cero
        System.out.println("--- Datos prueba ---");

        miBiblioteca.agregarLibro("L001", "Cien Años de Soledad", "G. García Márquez", "Novela");
        miBiblioteca.agregarLibro("L002", "El Señor de los Anillos", "J.R.R. Tolkien", "Fantasía");
        miBiblioteca.agregarLibro("L003", "Introducción a Java", "Deitel", "Informática");
        miBiblioteca.agregarSocio("S001", "Ana Torres");
        miBiblioteca.agregarSocio("S002", "Carlos Ruiz");

        System.out.println("\n--- Bienvenido a la Biblioteca Central de la UNAM ---");


        // Bucle principal del menú
        while (!salir) {
            System.out.println("\n===== MENÚ DE LA BIBLIOTECA =====");
            System.out.println("1. Agregar nuevo libro");
            System.out.println("2. Agregar nuevo socio");
            System.out.println("3. Prestar un libro");
            System.out.println("4. Devolver un libro");
            System.out.println("5. Mostrar libros disponibles");
            System.out.println("6. Mostrar préstamos de un socio");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    // --- Agregar Libro ---
                    System.out.println("\n--- Agregar Nuevo Libro ---");
                    System.out.print("Ingrese ID: ");
                    String idLibro = scanner.nextLine();
                    System.out.print("Ingrese Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Ingrese Autor: ");
                    String autor = scanner.nextLine();
                    System.out.print("Ingrese Categoría: ");
                    String categoria = scanner.nextLine();
                    miBiblioteca.agregarLibro(idLibro, titulo, autor, categoria);
                    break;

                case 2:
                    // --- Agregar Socio ---
                    System.out.println("\n--- Agregar Nuevo Socio ---");
                    System.out.print("Ingrese ID: ");
                    String idSocio = scanner.nextLine();
                    System.out.print("Ingrese Nombre: ");
                    String nombre = scanner.nextLine();
                    miBiblioteca.agregarSocio(idSocio, nombre);
                    break;

                case 3:
                    // --- Prestar Libro ---
                    System.out.println("\n--- Prestar Libro ---");
                    System.out.print("Ingrese ID del Socio: ");
                    String idSocioP = scanner.nextLine();
                    System.out.print("Ingrese ID del Libro: ");
                    String idLibroP = scanner.nextLine();
                    miBiblioteca.prestarLibro(idSocioP, idLibroP);
                    break;

                case 4:
                    // --- Devolver Libro ---
                    System.out.println("\n--- Devolver Libro ---");
                    System.out.print("Ingrese ID del Socio: ");
                    String idSocioD = scanner.nextLine();
                    System.out.print("Ingrese ID del Libro: ");
                    String idLibroD = scanner.nextLine();
                    miBiblioteca.devolverLibro(idSocioD, idLibroD);
                    break;

                case 5:
                    // --- Mostrar Libros Disponibles ---
                    miBiblioteca.mostrarLibrosDisponibles();
                    break;

                case 6:
                    // --- Mostrar Préstamos de Socio ---
                    System.out.println("\n--- Consultar Préstamos de Socio ---");
                    System.out.print("Ingrese ID del Socio: ");
                    String idSocioC = scanner.nextLine();
                    miBiblioteca.mostrarPrestamosSocio(idSocioC);
                    break;

                case 0:
                    // --- Salir ---
                    salir = true;
                    System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
        scanner.close();
    }
}