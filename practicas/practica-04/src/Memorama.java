import java.util.Scanner;
import java.util.Random;

public class Memorama {

    private char[][] simbolos;
    private boolean[][] visible;
    
    // Dimensiones del tablero
    private static final int FILAS = 4;
    private static final int COLUMNAS = 4;

    // Estado del juego
    private int movimientos;
    private int paresEncontrados;
    private Scanner scanner;

    /**
     * Constructor de la clase Memorama.
     * Inicializa los arreglos y prepara el tablero.
     */
    public Memorama() {
        // Se inicializan los arreglos con tamaño 4x4
        simbolos = new char[FILAS][COLUMNAS];
        visible = new boolean[FILAS][COLUMNAS];
        movimientos = 0;
        paresEncontrados = 0;
        scanner = new Scanner(System.in);
        
        inicializarTablero();
    }

    /**
     * Prepara el tablero con los 8 pares de símbolos
     * colocados de forma aleatoria.
     */
    private void inicializarTablero() {
        // 1. Crear los 8 pares de símbolos
        char[] pares = {'∑', '∑', 'Ø', 'Ø', 'φ', 'φ', 'α', 'α', 
                        'ℕ', 'ℕ', 'ε', 'ε', 'ℚ', 'ℚ', 'ℝ', 'ℝ'};

        // 2. Barajar los símbolos
        Random rand = new Random();
        for (int i = pares.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            // Intercambiar
            char temp = pares[i];
            pares[i] = pares[j];
            pares[j] = temp;
        }

        // 3. Llenar el tablero con los símbolos barajados
        int k = 0; // Índice para el arreglo 1D 'pares'
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                simbolos[i][j] = pares[k];
                visible[i][j] = false; // Inicia ƒcubierto
                k++;
            }
        }
    }

    /**
     * Imprime el estado actual del tablero en la terminal.
     * También imprime el sistema de coordenadas.
     */
    private void imprimirTablero() {
        // Imprimir cabecera de columnas
        System.out.print("  "); // Espacio para las filas
        for (int j = 0; j < COLUMNAS; j++) {
            System.out.print(j + " ");
        }
        System.out.println(); // Salto de línea

        // Imprimir filas
        for (int i = 0; i < FILAS; i++) {
            System.out.print(i + " "); // Cabecera de fila
            for (int j = 0; j < COLUMNAS; j++) {
                if (visible[i][j]) {
                    // Si está visible, muestra el símbolo
                    System.out.print(simbolos[i][j] + " ");
                } else {
                    // Si está cubierto, muestra un cuadrado rellenado
                    System.out.print("▄ ");
                }
            }
            System.out.println(); // Salto de línea al final de la fila
        }
        System.out.println("-----------------");
    }

    /**
     * Pide al usuario una coordenada (fila y columna).
     * @param mensaje El mensaje a mostrar (ej. "Primer símbolo...")
     * @return Un arreglo de int de tamaño 2: {fila, columna}
     */
    private int[] obtenerCoordenada(String mensaje) {
        int[] coordenada = new int[2];
        boolean entradaValida = false;

        System.out.println(mensaje);

        while (!entradaValida) {
            System.out.print("Ingresa la fila (0-3): ");
            int fila = scanner.nextInt();
            System.out.print("Ingresa la columna (0-3): ");
            int col = scanner.nextInt();

            // Validación de rango
            if (fila >= 0 && fila < FILAS && col >= 0 && col < COLUMNAS) {
                coordenada[0] = fila;
                coordenada[1] = col;
                entradaValida = true;
            } else {
                System.out.println("Coordenada inválida. Intenta de nuevo.");
            }
        }
        return coordenada;
    }
    

    /**
     * Método principal que contiene el ciclo del juego.
     * Se ejecuta hasta que todos los pares sean encontrados.
     */
    public void jugar() {
        // El juego continúa hasta que se encuentren los 8 pares
        while (paresEncontrados < 8) {
            
            // 1. Imprimir tablero cubierto
            imprimirTablero();

            // 2. Pedir primera coordenada 
            int[] coord1 = obtenerCoordenada("Primer símbolo a descubrir:");
            int r1 = coord1[0];
            int c1 = coord1[1];

            // Validar si ya está visible
            while (visible[r1][c1]) {
                System.out.println("Esa casilla ya está descubierta. Elige otra.");
                coord1 = obtenerCoordenada("Primer símbolo a descubrir:");
                r1 = coord1[0];
                c1 = coord1[1];
            }

            // 3. Revelar primer símbolo e imprimir 
            visible[r1][c1] = true;
            imprimirTablero();

            // 4. Pedir segunda coordenada 
            int[] coord2 = obtenerCoordenada("Segundo símbolo a descubrir:");
            int r2 = coord2[0];
            int c2 = coord2[1];

            // Validar si ya está visible o es la misma que la primera
            while (visible[r2][c2] || (r1 == r2 && c1 == c2)) {
                if (visible[r2][c2]) {
                    System.out.println("Esa casilla ya está descubierta. Elige otra.");
                } else {
                    System.out.println("No puedes elegir la misma casilla. Elige otra.");
                }
                coord2 = obtenerCoordenada("Segundo símbolo a descubrir:");
                r2 = coord2[0];
                c2 = coord2[1];
            }

            // 5. Revelar segundo símbolo e imprimir 
            visible[r2][c2] = true;
            imprimirTablero();

            // 6. Incrementar el movimiento 
            // (Se cuenta un movimiento por cada par descubierto)
            movimientos++;

            // 7. Comparar los símbolos
            if (simbolos[r1][c1] == simbolos[r2][c2]) {
                // Si coinciden 
                System.out.println("¡Encontraste un par!");
                paresEncontrados++;
            } else {
                // Si no coinciden 
                System.out.println("No coinciden. Se volverán a cubrir.");
                visible[r1][c1] = false;
                visible[r2][c2] = false;
                
            }
        } // Fin del bucle while (paso 1)

        // 8. Fin del juego
        System.out.println("¡Felicidades! Has encontrado todos los pares.");
        System.out.println("Cantidad total de movimientos: " + movimientos); // 
        
    }
}