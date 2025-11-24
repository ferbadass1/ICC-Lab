import java.util.Date;

/**
 * Clase padre Vuelo
 */
public class Vuelo {

    protected String origen; // lugar de salida (ciudad/terminal o planeta)
    protected String destino;
    protected Date fechaPartida;
    protected Pasaporte pasaporte;

    public Vuelo() {
        // constructor vacío
    }

    public Vuelo(String origen, String destino, Date fechaPartida, Pasaporte pasaporte) {
        this.origen = origen;
        this.destino = destino;
        this.fechaPartida = fechaPartida;
        this.pasaporte = pasaporte;
    }

    // Getters / Setters
    public String getOrigen() {
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public Date getFechaPartida() {
        return fechaPartida;
    }
    public void setFechaPartida(Date fechaPartida) {
        this.fechaPartida = fechaPartida;
    }
    public Pasaporte getPasaporte() {
        return pasaporte;
    }
    public void setPasaporte(Pasaporte pasaporte) {
        this.pasaporte = pasaporte;
    }

    /**
     * Validaciones comunes
     * - origen != destino (comparación ignorando mayúsculas)
     * - fechaPartida > fecha actual
     * - pasaporte no nulo y fecha de vencimiento > fecha actual
     */
    public void validar() throws VueloException {
        Date hoy = new Date();

        if (origen == null || destino == null) {
            throw new VueloException("Origen o destino no pueden ser nulos");
        }

        if (origen.equalsIgnoreCase(destino)) {
            throw new VueloException("El destino no puede ser igual al lugar del cual se parte");
        }

        if (fechaPartida == null) {
            throw new VueloException("La fecha de partida no puede ser nula");
        }

        if (!(fechaPartida.after(hoy))) {
            throw new VueloException("La fecha de partida debe ser mayor a la fecha actual");
        }

        if (pasaporte == null) {
            throw new VueloException("Pasaporte requerido");
        }

        Date vencimiento = pasaporte.getFechaVencimientoID();
        if (vencimiento == null) {
            throw new VueloException("Fecha de vencimiento de identificación no proporcionada");
        }

        if (!vencimiento.after(hoy)) {
            throw new VueloException("La fecha de vencimiento de la identificación debe ser mayor a la fecha actual");
        }
    }

}
