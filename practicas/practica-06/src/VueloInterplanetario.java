import java.util.Date;

/**
 * Vuelo Interplanetario.
 * Extiende de VueloEspacial.
 */
public class VueloInterplanetario extends VueloEspacial {

    public VueloInterplanetario(String origen, String destino, CertificadoDeSalud certificado, Date fechaPartida, Pasaporte pasaporte) {
        super(certificado, origen, destino, fechaPartida, pasaporte);
    }

    @Override
    public void validar() throws VueloException {
        // Validaciones de padre (Vuelo y VueloEspacial)
        super.validar();

        // Validaciones específicas de Interplanetario
        
        // Tipo del certificado debe ser P
        if (!certificado.getTipoViaje().equals("P")) {
            throw new VueloException("Certificado de salud no corresponde a un viaje interplanetario (debe tener tipo P).");
        }
    }
}