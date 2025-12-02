import java.util.Date;

/**
 * Vuelo estratosférico.
 * Extiende de VueloEspacial.
 */
public class VueloEstratosferico extends VueloEspacial {

    private String paisPartida;

    public VueloEstratosferico(CertificadoDeSalud certificado, String paisPartida, String origen, String destino, Date fechaPartida, Pasaporte pasaporte) {
        super(certificado, origen, destino, fechaPartida, pasaporte);
        this.paisPartida = paisPartida;
    }

    @Override
    public void validar() throws VueloException {
        // Validaciones de padre (Vuelo y VueloEspacial)
        super.validar();

        // Validaciones específicas de Estratosférico
        
        // País de partida
        if (paisPartida == null || !paisPartida.matches("[A-Z]{2}")) {
            throw new VueloException("País de partida inválido. Debe constar de 2 letras mayúsculas.");
        }

        // Edad entre 18 y 75
        int edad = pasaporte.getEdad();
        if (edad < 18 || edad > 75) {
            throw new VueloException("Edad inválida para vuelo estratosférico (debe estar entre 18 y 75 años).");
        }

        // Tipo del certificado debe ser E
        if (!certificado.getTipoViaje().equals("E")) {
            throw new VueloException("Certificado de salud no corresponde a un viaje estratosférico (debe tener tipo E).");
        }
    }
}