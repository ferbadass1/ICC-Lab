import java.util.Scanner;

/**
 * Clase principal de la Práctica 4.
 * Muestra un menú para elegir entre el Catálogo de Juegos o el Memorama.
 */
public class Practica4 {

    public static void main(String[] args) {
        // Creamos el catálogo
        CatalogoJuegos miCatalogo = new CatalogoJuegos();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        do {
            System.out.println("\n--- BIENVENIDO A TUS JUEGOS ---");
            System.out.println("Por favor, elige una opción:");
            System.out.println("1. Gestionar Catálogo de Juegos de Mesa");
            System.out.println("2. Jugar Memorama");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    miCatalogo.gestionar(sc);
                    break;
                case 2:
                    jugarMemorama();
                    break;
                case 0:
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, introduce 0, 1 o 2.");
                    break;
            }

        } while (!salir);

        sc.close();
    }

    /**
     * Ejecuta la lógica del Memorama.
     */
    public static void jugarMemorama() {
        System.out.println("\n--- Módulo: Memorama ---");
        System.out.println("¡Bienvenido al Memorama!");

        Memorama miJuego = new Memorama();
        miJuego.jugar();

        System.out.println("\n--- Fin del Memorama ---");
    }
}
