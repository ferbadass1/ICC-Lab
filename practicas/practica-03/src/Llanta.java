public class Llanta {
    private String marca;
    private int tamanoRin;
    private String materialRin;
    private double precio;

    // Constructor: Inicializa el objeto Llanta con sus características y precio total.
    public Llanta(String marca, int tamanoRin, String materialRin, double precio) {
        this.marca = marca;
        this.tamanoRin = tamanoRin;
        this.materialRin = materialRin;
        this.precio = precio;
    }

    // --- GETTERS ---
    public String getMarca(){
        return marca;
    }
    public int getTamano(){
        return tamanoRin;
    }
    public String getMaterial(){
        return materialRin;
    }
    public double getPrecio() {
        return precio;
    }

    // toString: Devuelve un resumen de las llantas y rines.
    @Override
    public String toString() {
        return "Llantas: " + marca + " | Rin " + tamanoRin + " pulgadas (" + materialRin + 
               ") | Precio: $" + precio;
    }
}
