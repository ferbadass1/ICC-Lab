public class Chasis {
    private String color;
    private String acabado;
    private double precio;

    // Constructor: Inicializa el objeto Chasis con su color, acabado y precio.
    public Chasis(String color, String acabado, double precio) {
        this.color = color;
        this.acabado = acabado;
        this.precio = precio;
    }

    // --- GETTERS ---
    public double getPrecio() {
        return precio;
    }
    public String getColor(){
        return color;
    }
    public String getAcabado(){
        return acabado;
    }

    // toString: Devuelve un resumen del chasis. 
    @Override
    public String toString() {
        return "Chasis color " + color + " (" + acabado + ") | Precio: $" + precio;
    }
}
