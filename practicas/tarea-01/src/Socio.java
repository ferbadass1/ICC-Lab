/**
 * Clase que representa a un Socio de la biblioteca.
 * Contiene su información y un arreglo de sus préstamos actuales.
 * @param id, nombre, numPrestados
 */
public class Socio {
    private String id;
    private String nombre;
    private Prestamo[] prestamosActuales;
    private int numPrestamos; // Contador de préstamos activos

    //private static final int MAX_PRESTAMOS = 10; // Límite de préstamos por socio

    public Socio(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        // Se inicializa el arreglo de préstamos
        this.prestamosActuales = new Prestamo[10]; // Límite de préstamo por socio
        this.numPrestamos = 0;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Agrega un nuevo préstamo al arreglo del socio.
     * @param prestamo
     * @return Devuelve true cuando el préstamo se agrega correctamente.
     */
    public boolean agregarPrestamo(Prestamo prestamo) {
        if (numPrestamos < 10) {
            prestamosActuales[numPrestamos] = prestamo;
            numPrestamos++;
            return true;
        } else {
            System.out.println("Error: El socio " + nombre + " ha alcanzado el límite de préstamos.");
            return false;
        }
    }

    /**
     * Busca un préstamo activo basado en el ID del libro.
     * @param idLibro El ID del libro a buscar.
     * @return El objeto Préstamo si se encuentra, o null si no.
     */
    public Prestamo buscarPrestamo(String idLibro) {
        for (int i = 0; i < numPrestamos; i++) {
            if (prestamosActuales[i].getLibro().getId().equals(idLibro)) {
                return prestamosActuales[i];
            }
        }
        return null; // No se encontró el préstamo
    }

    /**
     * Elimina un préstamo del arreglo del socio (cuando devuelve un libro).
     */
    public boolean removerPrestamo(Prestamo prestamo) {
        int indice = -1;
        // Buscar el índice del préstamo a remover
        for (int i = 0; i < numPrestamos; i++) {
            if (prestamosActuales[i] == prestamo) {
                indice = i;
                break;
            }
        }

        if (indice != -1) {
            // Desplazar los elementos del arreglo para llenar el hueco
            for (int i = indice; i < numPrestamos - 1; i++) {
                prestamosActuales[i] = prestamosActuales[i + 1];
            }
            prestamosActuales[numPrestamos - 1] = null; // Limpiar la última posición
            numPrestamos--;
            return true;
        }
        return false; // No se encontró el préstamo
    }

    /**
     * Muestra los libros que el socio tiene prestados.
     */
    public void mostrarPrestamos() {
        if (numPrestamos == 0) {
            System.out.println("El socio " + nombre + " no tiene libros prestados.");
            return;
        }
        System.out.println("Préstamos de " + nombre + ":");
        for (int i = 0; i < numPrestamos; i++) {
            System.out.println("  - " + prestamosActuales[i].toString());
        }
    }

    @Override
    public String toString() {
        return "Socio ID:" + id + " || Nombre:" + nombre + " || Préstamos: " + numPrestamos;
    }
}