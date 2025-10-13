public class SistemaElectronico {
    private boolean pantallaDigital;
    private boolean espejosElectronicos;
    private boolean sensorReversa;
    private boolean camaraReversa;
    private String tipoFrenos;
    private double precio;

    // Constructor: Inicializa el objeto SistemaElectronico con sus componentes y precio.
    public SistemaElectronico(boolean pantallaDigital, boolean espejosElectronicos, 
                              boolean sensorReversa, boolean camaraReversa, String tipoFrenos, double precio) {
        this.pantallaDigital = pantallaDigital;
        this.espejosElectronicos = espejosElectronicos;
        this.sensorReversa = sensorReversa;
        this.camaraReversa = camaraReversa;
        this.tipoFrenos = tipoFrenos;
        this.precio = precio;
    }

    // --- GETTERS ---
    public boolean getPantallaDigital(){
        return pantallaDigital;
    }
    public boolean getEspejosElectronicos(){
        return espejosElectronicos;
    }
    public boolean getSensorReversa(){
        return sensorReversa;
    }
    public boolean getCamaraReversa(){
        return camaraReversa;
    }
    public String tipoFrenos(){
        return tipoFrenos;
    }
       public double getPrecio() {
        return precio;
    }

    // toString: Devuelve un resumen de los componentes electrónicos.
    @Override
    public String toString() {
        return "Sistema electrónico [pantalla=" + pantallaDigital +
               ", espejos=" + espejosElectronicos + ", sensor reversa=" +
               sensorReversa + ", cámara reversa=" + camaraReversa + 
               "] | Precio: $" + precio;
    }
}
