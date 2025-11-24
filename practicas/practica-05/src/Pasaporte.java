import java.util.Date;
import java.util.Calendar;

/**
 * Clase Pasaporte con comentarios originales del usuario.
 */
public class Pasaporte {

    //Datos basicos del pasajero
    private String nombre;
    private String apellido;
    private int edad;
    private String nacionalidad;
    private String paisOrigen;

    //DatosID
    private String numID;
    private Date fechaVencimientoID;

    //Constructor vacío para inicializar el objeto antes de capturar datos.
    public Pasaporte(){}

    public Pasaporte(String nombre, String apellido, int edad, String nacionalidad, String paisOrigen, String numID, Date fechaVencimientoID){
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
        this.paisOrigen = paisOrigen;
        this.numID = numID;
        this.fechaVencimientoID = fechaVencimientoID;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public int getEdad() {
        return edad;
    }
    public String getNacionalidad() {
        return nacionalidad;
    }
    public String getPaisOrigen() {
        return paisOrigen;
    }
    public String getNumID() {
        return numID;
    }
    public Date getFechaVencimientoID() {
        return fechaVencimientoID;
    }

    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }
    public void setNumID(String numID) {
        this.numID = numID;
    }
    public void setFechaVencimientoID(Date fechaVencimientoID) {
        this.fechaVencimientoID = fechaVencimientoID;
    }

    public void calcularFechaVencimiento(){
        // Obtiene la fecha y hora actuales
        Calendar calendario = Calendar.getInstance();
        // Suma 5 años a la fecha actual
        calendario.add(Calendar.YEAR, 5);
        this.fechaVencimientoID = calendario.getTime();
    }

    @Override
    public String toString() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String f = fechaVencimientoID == null ? "N/A" : sdf.format(fechaVencimientoID);
        return "Pasaporte[" + numID + "] " + nombre + " " + apellido + "\nEdad: " + edad + "\nNacionalidad: " + nacionalidad + "\nPais de Origen: " + paisOrigen + "\nFecha de Vencimiento: " + f;
    }

}
