import java.util.Date;

/**
 * Vuelo interplanetario: planetas del sistema solar, certificado tipo P, sin necesidad de nacionalidad.
 */
public class VueloInterplanetario extends Vuelo {

    private String planetaOrigen;
    private String planetaDestino;
    private CertificadoDeSalud certificado;

    private static final String[] PLANETAS = {
        "mercurio","venus","tierra","marte","jupiter","saturno","urano","neptuno"
    };

    public VueloInterplanetario(String planetaOrigen, String planetaDestino, CertificadoDeSalud certificado, Date fechaPartida, Pasaporte pasaporte) {
        super(planetaOrigen, planetaDestino, fechaPartida, pasaporte);
        this.planetaOrigen = planetaOrigen;
        this.planetaDestino = planetaDestino;
        this.certificado = certificado;
    }

    private boolean esPlanetaDelSistemaSolar(String nombre) {
        if (nombre == null) {
            return false;
        }
        String n = nombre.trim().toLowerCase();
        for (String p : PLANETAS) {
            if (p.equals(n)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void validar() throws VueloException {
        Date hoy = new Date();

        // planetas no nulos
        if (planetaOrigen == null || planetaDestino == null) {
            throw new VueloException("Planeta origen y destino son requeridos");
        }

        // comprobar planetas del sistema solar
        if (!esPlanetaDelSistemaSolar(planetaOrigen)) {
            throw new VueloException("Planeta de origen no pertenece al sistema solar: " + planetaOrigen);
        }
        if (!esPlanetaDelSistemaSolar(planetaDestino)) {
            throw new VueloException("Planeta destino no pertenece al sistema solar: " + planetaDestino);
        }

        // no puede volar hacia el mismo planeta
        if (planetaOrigen.equalsIgnoreCase(planetaDestino)) {
            throw new VueloException("El destino no puede ser el mismo planeta del que se parte");
        }

        // fecha de partida no nula y > hoy
        if (fechaPartida == null) {
            throw new VueloException("La fecha de partida no puede ser nula");
        }
        if (!(fechaPartida.after(hoy))) {
            throw new VueloException("La fecha de partida debe ser mayor a la fecha actual");
        }

        // pasaporte y su vencimiento
        if (pasaporte == null) {
            throw new VueloException("Pasaporte requerido");
        }
        Date venc = pasaporte.getFechaVencimientoID();
        if (venc == null) {
            throw new VueloException("Fecha de vencimiento de identificación no proporcionada");
        }
        if (!venc.after(hoy)) {
            throw new VueloException("La fecha de vencimiento de la identificación debe ser mayor a la fecha actual");
        }

        // Edad entre 18 y 75
        int edad = pasaporte.getEdad();
        if (edad < 18 || edad > 75) {
            throw new VueloException("Edad inválida para vuelo interplanetario (debe estar entre 18 y 75 años).");
        }

        // certificado
        if (certificado == null) {
            throw new VueloException("Certificado de salud requerido para vuelos interplanetarios.");
        }

        // tipo debe ser P
        if (!certificado.getTipoViaje().equals("P")) {
            throw new VueloException("Certificado no es del tipo interplanetario (debe contener 'P').");
        }

        // clave de salud debe ser A
        if (!certificado.getClaveSalud().equals("A")) {
            throw new VueloException("No está apto para volar (clave de salud no es 'A').");
        }

        // fecha del certificado >= fecha del viaje
        if (!certificado.esValidoParaFecha(fechaPartida)) {
            throw new VueloException("La fecha del certificado debe ser igual o posterior a la fecha de viaje.");
        }
    }
}
