import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Clase coordinadora principal.
 * Gestiona los arreglos de libros y socios, y las operaciones de préstamo y devolución.
 * 
 */
public class Biblioteca {
    private Libro[] libros;
    private Socio[] socios;
    private int numLibros;
    private int numSocios;

    private static final int MAX_LIBROS = 100;
    private static final int MAX_SOCIOS = 50;
    private static final double MULTA_POR_DIA = 1.0;

    public Biblioteca() {
        // Inicializa los arreglos con su capacidad máxima
        this.libros = new Libro[MAX_LIBROS];
        this.socios = new Socio[MAX_SOCIOS];
        this.numLibros = 0;
        this.numSocios = 0;
    }

    // --- Métodos de registro ---

    /**
     * Agrega un nuevo libro al inventario de la biblioteca.
     * 
     */
    public void agregarLibro(String id, String titulo, String autor, String categoria) {
        if (numLibros < MAX_LIBROS) {
            Libro nuevoLibro = new Libro(id, titulo, autor, categoria);
            libros[numLibros] = nuevoLibro;
            numLibros++;
            System.out.println("Libro agregado: " + titulo + " || ID: " + id);
        } else {
            System.out.println("Error: Inventario de libros lleno.");
        }
    }

    /**
     * Registra un nuevo socio en el sistema.
     */
    public void agregarSocio(String id, String nombre) {
        if (numSocios < MAX_SOCIOS) {
            Socio nuevoSocio = new Socio(id, nombre);
            socios[numSocios] = nuevoSocio;
            numSocios++;
            System.out.println("Socio agregado: " + nombre + " || ID: " + id);
        } else {
            System.out.println("Error: Registro de socios lleno.");
        }
    }

    // --- Métodos de búsqueda ---

    public Libro buscarLibro(String idLibro) {
        for (int i = 0; i < numLibros; i++) {
            if (libros[i].getId().equals(idLibro)) {
                return libros[i];
            }
        }
        return null; // No encontrado
    }

    public Socio buscarSocio(String idSocio) {
        for (int i = 0; i < numSocios; i++) {
            if (socios[i].getId().equals(idSocio)) {
                return socios[i];
            }
        }
        return null; // No encontrado
    }

    // --- Métodos de consulta ---

    /**
     * Muestra todos los libros que están actualmente disponibles.
     * 
     */
    public void mostrarLibrosDisponibles() {
        System.out.println("\n--- Libros Disponibles ---");
        boolean encontrados = false;
        for (int i = 0; i < numLibros; i++) {
            if (libros[i].getEstado().equals("disponible")) {
                System.out.println(libros[i].toString());
                encontrados = true;
            }
        }
        if (!encontrados) {
            System.out.println("No hay libros disponibles en este momento.");
        }
    }

    /**
     * Muestra la lista de préstamos de un socio específico.
     * 
     */
    public void mostrarPrestamosSocio(String idSocio) {
        Socio socio = buscarSocio(idSocio);
        if (socio != null) {
            socio.mostrarPrestamos();
        } else {
            System.out.println("Error: No se encontró al socio con ID " + idSocio);
        }
    }

    // --- Métodos de gestión (Préstamos y Devoluciones) ---

    /**
     * Gestiona la operación de prestar un libro a un socio.
     * 
     */
    public void prestarLibro(String idSocio, String idLibro) {
        // System.out.println("\nIntentando préstamo: Socio " + idSocio + ", Libro " + idLibro);
        Socio socio = buscarSocio(idSocio);
        if (socio == null) {
            System.out.println("Error: El socio no existe.");
            return;
        }

        Libro libro = buscarLibro(idLibro);
        if (libro == null) {
            System.out.println("Error: El libro no existe.");
            return;
        }

        if (!libro.getEstado().equals("disponible")) {
            System.out.println("Error: El libro no está disponible.");
            return;
        }

        // Si se cumplen las condiciones
        Prestamo nuevoPrestamo = new Prestamo(libro);
        if (socio.agregarPrestamo(nuevoPrestamo)) {
            libro.setEstado("prestado");
            System.out.println("Préstamo exitoso: " + socio.getNombre() + " tomó prestado el libro: " + libro.getTitulo());
            System.out.println("Fecha de vencimiento: " + nuevoPrestamo.getFechaVencimiento());
        }
    }

    /**
     * Gestiona la operación de devolver un libro.
     *
     */
    public void devolverLibro(String idSocio, String idLibro) {
        //System.out.println("\nIntentando devolución: Socio " + idSocio + ", Libro " + idLibro);
        Socio socio = buscarSocio(idSocio);
        if (socio == null) {
            System.out.println("Error: El socio no existe.");
            return;
        }

        Libro libro = buscarLibro(idLibro);
        if (libro == null) {
            System.out.println("Error: El libro no existe.");
            return;
        }

        Prestamo prestamo = socio.buscarPrestamo(idLibro);
        if (prestamo == null) {
            System.out.println("Error: El socio " + socio.getNombre() + " no tiene este libro prestado.");
            return;
        }

        // Procesar devolución
        libro.setEstado("disponible");
        boolean removido = socio.removerPrestamo(prestamo);

        if (removido) {
            System.out.println("Devolución exitosa: " + libro.getTitulo());

            // Calcular multa
            LocalDate hoy = LocalDate.now();
            long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaVencimiento(), hoy);

            if (diasRetraso > 0) {
                double multa = diasRetraso * MULTA_POR_DIA;
                System.out.println("¡MULTA! Días de retraso: " + diasRetraso + ". Total a pagar: $" + multa);
            } else {
                System.out.println("Libro devuelto a tiempo. No hay multa.");
            }
        } else {
            System.out.println("Error interno: No se pudo remover el préstamo del socio.");
        }
    }
}