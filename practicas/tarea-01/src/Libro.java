/**
 * Clase que representa un Libro en la biblioteca.
 * @param id, título, autor, categoría y estado.
 * 
 */
public class Libro {
    private String id;
    private String titulo;
    private String autor;
    private String categoria;
    private String estado; // "disponible" o "prestado"

    public Libro(String id, String titulo, String autor, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.estado = "disponible"; // Estado inicial
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEstado() {
        return estado;
    }

    // --- Setters ---
    /**
     * Modifica el estado del libro.
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Representación en String del objeto Libro.
     */
    @Override
    public String toString() {
        return "Libro ID: " + id + " || Título: " + titulo + " || Autor: " + autor + " || Estado: " + estado;
    }
}