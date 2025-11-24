import java.util.Date;

/**
 * Vuelo internacional: pide paisPartida (2 letras mayúsculas) y usa pasaporte ya registrado.
 */
public class VueloInternacional extends Vuelo {

    private String paisPartida; // 2 letras mayúsculas (país desde el cual parte el vuelo)

    public VueloInternacional(String paisPartida, String origen, String destino, Date fechaPartida, Pasaporte pasaporte) {
        super(origen, destino, fechaPartida, pasaporte);
        this.paisPartida = paisPartida;
    }

    public String getPaisPartida() {
        return paisPartida;
    }

    @Override
    public void validar() throws VueloException {
        // validaciones base
        super.validar();

        // país de origen del vuelo: 2 letras mayúsculas
        if (paisPartida == null || !paisPartida.matches("[A-Z]{2}")) {
            throw new VueloException("País de partida inválido. Debe constar de 2 letras mayúsculas.");
        }

        // Nombre completo de la persona: al menos un nombre y un apellido (ya en pasaporte)
        if (pasaporte.getNombre() == null || pasaporte.getApellido() == null) {
            throw new VueloException("Nombre y apellido son requeridos en el pasaporte.");
        }
        if (pasaporte.getNombre().trim().isEmpty() || pasaporte.getApellido().trim().isEmpty()) {
            throw new VueloException("Nombre o apellido vacíos en pasaporte.");
        }

        // Nacionalidad del pasaporte: 2 letras mayúsculas
        if (pasaporte.getNacionalidad() == null || !pasaporte.getNacionalidad().matches("[A-Z]{2}")) {
            throw new VueloException("Nacionalidad inválida en pasaporte. Debe constar de 2 letras mayúsculas.");
        }

        // Número de identificación no vacío
        if (pasaporte.getNumID() == null || pasaporte.getNumID().trim().isEmpty()) {
            throw new VueloException("El número de identificación no puede estar vacío.");
        }
    }
}
