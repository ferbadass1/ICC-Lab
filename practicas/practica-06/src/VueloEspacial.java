import java.util.Date;

/**
 * Clase abstracta para vuelos que salen de la atmósfera.
 * Agrupa la lógica del Certificado de Salud.
 */
public abstract class VueloEspacial extends Vuelo {
    
    protected CertificadoDeSalud certificado;

    public VueloEspacial(CertificadoDeSalud certificado, String origen, String destino, Date fechaPartida, Pasaporte pasaporte) {
        super(origen, destino, fechaPartida, pasaporte);
        this.certificado = certificado;
    }

    public CertificadoDeSalud getCertificado() {
        return certificado;
    }

    @Override
    public void validar() throws VueloException {
        // 1. Validaciones base de Vuelo (pasaporte, fechas, etc.)
        super.validar();

        // 2. Validaciones comunes de Vuelo Espacial (Certificado)
        if (certificado == null) {
            throw new VueloException("Certificado de salud requerido para vuelos espaciales.");
        }

        // Clave salud debe ser A para permitir el vuelo
        if (!certificado.getClaveSalud().equals("A")) {
            throw new VueloException("No está apto para volar (clave de salud no es 'A').");
        }

        // Fecha del certificado >= fecha del viaje
        if (!certificado.esValidoParaFecha(fechaPartida)) {
            throw new VueloException("La fecha del certificado debe ser igual o posterior a la fecha de viaje.");
        }
    }
}