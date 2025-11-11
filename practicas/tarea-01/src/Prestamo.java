import java.time.LocalDate;

/**
 * Clase que representa un préstamo activo.
 * Almacena el libro prestado, la fecha del préstamo y la fecha de vencimiento.
 * @param libro, fechaPrestamo, fechaVencimiento;
 * 
 */
public class Prestamo{
    private Libro libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;

    public Prestamo(Libro libro) {
        this.libro = libro;
        this.fechaPrestamo = LocalDate.now(); // Fecha actual
        this.fechaVencimiento = this.fechaPrestamo.plusDays(7); // 7 días después
    }

    // --- Getters ---
    public Libro getLibro() {
        return libro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Representación en String del objeto Préstamo.
     */
    @Override
    public String toString() {
        return "Préstamo Libro: " + libro.getTitulo() + " || Vence: " + fechaVencimiento;
    }
}