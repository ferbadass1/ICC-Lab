import java.util.Date;

/**
 * Vuelo Internacional tradicional.
 * Extiende directamente de Vuelo (Abstracto).
 */
public class VueloInternacional extends Vuelo {

    private String paisPartida;

    public VueloInternacional(String paisPartida, String origen, String destino, Date fechaPartida, Pasaporte pasaporte) {
        super(origen, destino, fechaPartida, pasaporte);
        this.paisPartida = paisPartida;
    }

    @Override
    public void validar() throws VueloException {
        super.validar();

        if (paisPartida == null || !paisPartida.matches("[A-Z]{2}")) {
            throw new VueloException("País de partida inválido. Debe constar de 2 letras mayúsculas.");
        }
        
        // Validar que el origen o el destino coincidan con el país de partida
    }
}