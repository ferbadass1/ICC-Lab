import java.util.Date;

/**
 * Vuelo estratosférico: requiere certificado de salud de tipo E y edad entre 18 y 75.
 * También requiere paisPartida (2 letras).
 */
public class VueloEstratosferico extends Vuelo {

    private CertificadoDeSalud certificado;
    private String paisPartida;

    public VueloEstratosferico(CertificadoDeSalud certificado, String paisPartida, String origen, String destino, Date fechaPartida, Pasaporte pasaporte) {
        super(origen, destino, fechaPartida, pasaporte);
        this.certificado = certificado;
        this.paisPartida = paisPartida;
    }

    public CertificadoDeSalud getCertificado() {
        return certificado;
    }

    @Override
    public void validar() throws VueloException {
        // Validaciones base
        super.validar();

        // País de partida del vuelo
        if (paisPartida == null || !paisPartida.matches("[A-Z]{2}")) {
            throw new VueloException("País de partida inválido. Debe constar de 2 letras mayúsculas.");
        }

        // Edad entre 18 y 75 (dato del pasaporte)
        int edad = pasaporte.getEdad();
        if (edad < 18 || edad > 75) {
            throw new VueloException("Edad inválida para vuelo estratosférico (debe estar entre 18 y 75 años).");
        }

        if (certificado == null) {
            throw new VueloException("Certificado de salud requerido para vuelos estratosféricos.");
        }

        // Tipo del certificado debe ser E
        if (!certificado.getTipoViaje().equals("E")) {
            throw new VueloException("Certificado de salud no corresponde a un viaje estratosférico (debe tener tipo E).");
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
