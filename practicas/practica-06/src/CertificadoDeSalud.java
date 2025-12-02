import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Certificado de salud con formato y validaciones:
 * CS-NOMBRE-APELLIDO-ID-AAAA-MM-DD-CLAVESALUD-TIPOVIAJE
 * CLAVESALUD: A o NA
 * TIPOVIAJE: E (estratosférico) o P (Interplanetario)
 */
public class CertificadoDeSalud {

    private String raw; // cadena completa original
    private String nombre;
    private String apellido;
    private String idPasaporte; // ahora incluido en el certificado
    private Date fechaCertificado;
    private String claveSalud; // "A" o "NA"
    private String tipoViaje;  // "E" o "P"

    public CertificadoDeSalud(String raw) throws CertificadoException {
        this.raw = raw;
        parsearYCargar();
    }

    public String getRaw() {
        return raw;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getIdPasaporte() {
        return idPasaporte;
    }

    public Date getFechaCertificado() {
        return fechaCertificado;
    }

    public String getClaveSalud() {
        return claveSalud;
    }

    public String getTipoViaje() {
        return tipoViaje;
    }

    private void parsearYCargar() throws CertificadoException {
        if (raw == null) {
            throw new CertificadoException("Certificado vacío");
        }

        String[] partes = raw.split("-");
        if (partes.length != 9) {
            throw new CertificadoException("Formato inválido. Debe ser: CS-NOMBRE-APELLIDO-ID-AAAA-MM-DD-CLAVESALUD-TIPOVIAJE");
        }

        if (!partes[0].equals("CS")) {
            throw new CertificadoException("Certificado debe comenzar con 'CS-'");
        }

        try {
            this.nombre = partes[1];
            this.apellido = partes[2];
            this.idPasaporte = partes[3];

            String yyyy = partes[4];
            String mm = partes[5];
            String dd = partes[6];
            String fechaCad = yyyy + "-" + mm + "-" + dd;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            this.fechaCertificado = sdf.parse(fechaCad);

            this.claveSalud = partes[7];
            this.tipoViaje = partes[8];
        } catch (ArrayIndexOutOfBoundsException | ParseException ex) {
            throw new CertificadoException("Error al escribir el certificado: " + ex.getMessage());
        }

        if (!claveSalud.equals("A") && !claveSalud.equals("NA")) {
            throw new CertificadoException("Clave de salud inválida (debe ser A o NA).");
        }

        if (!tipoViaje.equals("E") && !tipoViaje.equals("P")) {
            throw new CertificadoException("Tipo de viaje inválido (debe ser E o P).");
        }
    }

    /**
     * Comprueba que la fecha del certificado sea >= otra fecha dada
     */
    public boolean esValidoParaFecha(Date fechaComparacion) {
        if (fechaCertificado == null || fechaComparacion == null) {
            return false;
        }
        int cmp = fechaCertificado.compareTo(fechaComparacion);
        if (cmp > 0 || cmp == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "\nCertificado[" + raw + "]" + "\nFecha de validez: " + sdf.format(fechaCertificado) + "\nClave de Salud: " + claveSalud + "\nTipo: " + tipoViaje + "\nID Pasaporte: " + idPasaporte;
    }
}
