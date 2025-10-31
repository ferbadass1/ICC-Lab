import java.util.Scanner;

/**
 * Clase que gestiona un catálogo de juegos de mesa usando un arreglo bidimensional.
 * Almacena nombre, calificación y género de cada juego.
 * Gestiona su propio menú interactivo.
 */
public class CatalogoJuegos {

    private String[][] juegos;
    private int numJuegos;

    /**
     * Constructor del catalogo de juegos
     */
    public CatalogoJuegos() {
        this.juegos = new String[50][3]; //Puede almacenar hasta 50 juegos
        this.numJuegos = 0;
    }

    /**
     * Muestra el menú de gestión del catálogo.
     */
    public void gestionar(Scanner sc) {
        boolean salirCatalogo = false;

        do {
            System.out.println("\n--- Menú de Gestión de Juegos ---");
            System.out.println("1. Agregar Juego");
            System.out.println("2. Ordenar Juegos por Nombre");
            System.out.println("3. Ordenar Juegos por Calificación");
            System.out.println("4. Ordenar Juegos por Género");
            System.out.println("5. Ranking de Juegos (Mejor y Peor)");
            System.out.println("6. Mostrar Todos los Juegos");
            System.out.println("0. Volver al menú principal");
            System.out.print("Opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    interfazAgregarJuego(sc);
                    break;
                case 2:
                    ordenarPorNombre();
                    mostrarJuegos("Catálogo ordenado por Nombre");
                    break;
                case 3:
                    ordenarPorCalificacion();
                    mostrarJuegos("Catálogo ordenado por Calificación");
                    break;
                case 4:
                    ordenarPorGenero();
                    mostrarJuegos("Catálogo ordenado por Género");
                    break;
                case 5:
                    mostrarRanking();
                    break;
                case 6:
                    mostrarJuegos("Catálogo Actual");
                    break;
                case 0:
                    salirCatalogo = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
                    break;
            }
        } while (!salirCatalogo);
    }

    /**
     * Pide los datos de un juego y lo agrega al catálogo.
     */
    private void interfazAgregarJuego(Scanner sc) {
        System.out.println("\n== Agregar Juego Nuevo ==");
        if (numJuegos >= juegos.length) {
            System.out.println("Error: El catálogo está lleno. No se pueden agregar más juegos.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim(); // el .trim() elimina espacios al inicio o al final

        System.out.print("Calificación: ");
        String calificacion = sc.nextLine().trim();

        System.out.print("Género: ");
        String genero = sc.nextLine().trim();

        agregarJuego(nombre, calificacion, genero);
        System.out.println("¡Juego '" + nombre + "' agregado!");
    }
     
    /**
     * Agrega un nuevo juego al catálogo.
     */
    private void agregarJuego(String nombre, String calificacion, String genero) {
        juegos[numJuegos][0] = nombre;
        juegos[numJuegos][1] = calificacion;
        juegos[numJuegos][2] = genero;
        numJuegos++;
    }

    /**
     * Muestra todos los juegos del catálogo.
     */
    private void mostrarJuegos(String titulo) {
        if (numJuegos == 0) {
            System.out.println("\nEl catálogo está vacío.");
            return;
        }

        System.out.println("\n--- " + titulo + " ---");
        System.out.printf("%-20s | %-12s | %-15s\n", "Nombre", "Calificación", "Género");
        System.out.println("---------------------------------------------------------");

        for (int i = 0; i < numJuegos; i++) {
            String nombre = juegos[i][0] != null ? juegos[i][0] : "";
            String cal = juegos[i][1] != null ? juegos[i][1] : "";
            String gen = juegos[i][2] != null ? juegos[i][2] : "";
            System.out.printf("%-20s | %-12s | %-15s\n", nombre, cal, gen);
        }
    }

    /**
     * Muestra el ranking (mejor y peor calificados).
     */
    private void mostrarRanking() {
        if (numJuegos == 0) {
            System.out.println("No hay juegos en el catálogo para mostrar un ranking.");
            return;
        }

        System.out.println("\n== Ranking de Juegos ==");
        mostrarMejorCalificado();
        mostrarPeorCalificado();
    }
   
    /**
     * Muestra el juego con la peor calificación.
     */
    private void mostrarPeorCalificado() {
        int indicePeor = 0;
        double peorCal = Double.parseDouble(juegos[0][1]);

        for (int i = 1; i < numJuegos; i++) {
            double calActual = Double.parseDouble(juegos[i][1]);
            if (calActual < peorCal) {
                peorCal = calActual;
                indicePeor = i;
            }
        }

        System.out.println("Juego Peor Calificado: " + juegos[indicePeor][0] +
                " (Cal: " + juegos[indicePeor][1] + ")");
    }

    /**
     * Muestra el juego con la mejor calificación.
     */
    private void mostrarMejorCalificado() {
        int indiceMejor = 0;
        double mejorCal = Double.parseDouble(juegos[0][1]);

        for (int i = 1; i < numJuegos; i++) {
            double calActual = Double.parseDouble(juegos[i][1]);
            if (calActual > mejorCal) {
                mejorCal = calActual;
                indiceMejor = i;
            }
        }

        System.out.println("Juego Mejor Calificado: " + juegos[indiceMejor][0] +
                " (Cal: " + juegos[indiceMejor][1] + ")");
    }

    /**
     * Ordenamiento por burbuja (Bubble Sort).
     * columna: 0 = Nombre, 1 = Calificación, 2 = Género
     */
    private void ordenar(int columna) {
        if (numJuegos == 0) {
            System.out.println("No hay juegos para ordenar.");
            return;
        }

        for (int i = 0; i < numJuegos - 1; i++) {
            for (int j = 0; j < numJuegos - i - 1; j++) {
                boolean intercambiar = false;

                if (columna == 1) { // Calificación
                    double cal1 = Double.parseDouble(juegos[j][1]);
                    double cal2 = Double.parseDouble(juegos[j + 1][1]);
                    if (cal1 > cal2) {
                        intercambiar = true;
                    }
                } else { // Nombre o Género
                    String a = juegos[j][columna] != null ? juegos[j][columna] : "";
                    String b = juegos[j + 1][columna] != null ? juegos[j + 1][columna] : "";
                    if (a.compareToIgnoreCase(b) > 0) {
                        intercambiar = true;
                    }
                }

                if (intercambiar) {
                    String[] temp = juegos[j];
                    juegos[j] = juegos[j + 1];
                    juegos[j + 1] = temp;
                }
            }
        }
    }

    /** Ordena por Nombre */
    public void ordenarPorNombre() {
        ordenar(0);
    }

    /** Ordena por Calificación */
    public void ordenarPorCalificacion() {
        ordenar(1);
    }

    /** Ordena por Género */
    public void ordenarPorGenero() {
        ordenar(2);
    }
}
