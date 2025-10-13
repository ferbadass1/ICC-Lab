public class Automovil {

    private MarcaAutomovil marca;
    private Llanta llanta;
    private Chasis chasis;
    private SistemaElectronico sistema;
    private String transmision;
    private String tipoAuto;
    private int anio;
    private boolean peliculaAntiAsalto;

    // Constructor: Inicializa un nuevo objeto Automovil con todos sus componentes y características.
    public Automovil(MarcaAutomovil marca, Llanta llanta, Chasis chasis, SistemaElectronico sistema,
                     String transmision, String tipoAuto, int anio, boolean peliculaAntiAsalto) {
        this.marca = marca;
        this.llanta = llanta;
        this.chasis = chasis;
        this.sistema = sistema;
        this.transmision = transmision;
        this.tipoAuto = tipoAuto;
        this.anio = anio;
        this.peliculaAntiAsalto = peliculaAntiAsalto;
    }

    // ------------------ GETTERS ------------------
    public MarcaAutomovil getMarcaAuto() { 
        return marca; }
    public Llanta getLlanta() { 
        return llanta; }
    public Chasis getChasis() { 
        return chasis; }
    public SistemaElectronico getSistema() { 
        return sistema; }
    public String getTransmision() { 
        return transmision; }
    public String getTipoAuto() { 
        return tipoAuto; }
    public int getAnio() { 
        return anio; }
    public boolean tienePeliculaAntiAsalto() { 
        return peliculaAntiAsalto; }

    // toString: Devuelve un resumen del automóvil en una sola línea.  
    @Override
    public String toString() {
        return "Automovil [marca=" + marca.getMarca() + ", pais=" + marca.getPais() +
            ", transmision=" + transmision + ", tipo=" + tipoAuto + ", anio=" + anio +
            ", chasis=" + chasis.getColor() + " " + chasis.getAcabado() +
            ", llantas=" + llanta.getMarca() + ", rin=" + llanta.getTamano() +
            ", peliculaAntiAsalto=" + (peliculaAntiAsalto ? "Sí" : "No") +
            "] | Precio Total: $" + calcularPrecioTotal();
    }

    // ------------------ PRECIO TOTAL ------------------
    // calcularPrecioTotal: Suma los precios de los objetos que componen el auto.
    public double calcularPrecioTotal() {
        double total = 0;
        total += marca.getPrecio();
        total += chasis.getPrecio();
        total += llanta.getPrecio();
        total += sistema.getPrecio();
        return total;
    }
}

