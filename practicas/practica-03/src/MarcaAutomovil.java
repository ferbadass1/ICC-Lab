public class MarcaAutomovil {
    private String nombre;
    private String paisFabricacion;
    private double precioImportacion;

    // Constructor: Inicializa el objeto MarcaAutomovil con su nombre, país y costo de importación.
    public MarcaAutomovil(String nombre, String paisFabricacion, double precioImportacion) {
        this.nombre = nombre;
        this.paisFabricacion = paisFabricacion;
        this.precioImportacion = precioImportacion;
    }

    // --- GETTERS ---
    public String getMarca(){
        return nombre;
    }
    public String getPais(){
        return paisFabricacion;
    }
    public double getPrecio() {
        return precioImportacion;
    }

    // toString: Devuelve un resumen de la marca, país y costo.
    @Override
    public String toString() {
        return "Marca: " + nombre + " | País de fabricación: " + paisFabricacion +
               " | Precio importación: $" + precioImportacion;
    }
}
