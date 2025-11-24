import java.util.Arrays;
import java.util.Date;

/**
 * Gestiona el almacenamiento (Pasaportes y Certificados) y las búsquedas
 */
public class AgenciaDeViajes {

    private final int MAX = 50;
    private Pasaporte[] pasajeros = new Pasaporte[MAX];
    private CertificadoDeSalud[] certificados = new CertificadoDeSalud[MAX];
    private int pasajerosCount = 0;
    private int certificadosCount = 0;

    // Métodos de Gestión y Búsqueda

    public boolean agregarPasaporte(Pasaporte pasaporte) {
        if (pasajerosCount >= MAX) {
            System.err.println("Capacidad máxima de pasajeros alcanzada.");
            return false;
        }
        pasajeros[pasajerosCount++] = pasaporte;
        return true;
    }

    public boolean agregarCertificado(CertificadoDeSalud certificado) {
        if (certificadosCount >= MAX) {
            System.err.println("Capacidad máxima de certificados alcanzada.");
            return false;
        }
        certificados[certificadosCount++] = certificado;
        return true;
    }

    public boolean existePasaporteConID(String id) {
        return encontrarPasajeroPorID(id) != null;
    }

    /**
     * Busca pasajero por ID en arreglo.
     * @return El Pasaporte o null si no existe.
     */
    public Pasaporte encontrarPasajeroPorID(String id) {
        if (id == null) return null;
        for (int i = 0; i < pasajerosCount; i++) {
            if (pasajeros[i] != null && id.equals(pasajeros[i].getNumID())) {
                return pasajeros[i];
            }
        }
        return null;
    }

    /**
     * Busca certificados por ID y tipo, y que estén aptos ('A').
     * Devuelve los certificados coincidentes.
     */
    public CertificadoDeSalud[] buscarCertificadosPorIDyTipo(String id, String tipo) {
        CertificadoDeSalud[] tmp = new CertificadoDeSalud[certificadosCount];
        int found = 0;
        for (int i = 0; i < certificadosCount; i++) {
            CertificadoDeSalud cert = certificados[i];
            if (cert != null && cert.getIdPasaporte().equals(id) && cert.getTipoViaje().equals(tipo)) {
                // Verificar que la clave sea A (apto)
                if (cert.getClaveSalud().equals("A")) {
                    tmp[found++] = cert;
                }
            }
        }
        return Arrays.copyOf(tmp, found);
    }

    // Lógica de Precarga de Datos

    public void cargarDatosPrecargados() {
        try {
            Pasaporte p1 = new Pasaporte("Juan","Perez",30,"MX","MX","ID001", null);
            p1.calcularFechaVencimiento();
            Pasaporte p2 = new Pasaporte("Ana","Lopez",25,"US","US","ID002", null);
            p2.calcularFechaVencimiento();
            Pasaporte p3 = new Pasaporte("Lara","Gomez",40,"NL","NL","ID003", null);
            p3.calcularFechaVencimiento();

            agregarPasaporte(p1);
            agregarPasaporte(p2);
            agregarPasaporte(p3);

            // certificados: dos para p1, uno para p2, uno inválido(old) for p3
            // formato: CS-NOMBRE-APELLIDO-ID-AAAA-MM-DD-CLAVESALUD-TIPOVIAJE
            CertificadoDeSalud c1 = new CertificadoDeSalud("CS-Juan-Perez-ID001-2100-01-01-A-E");
            CertificadoDeSalud c2 = new CertificadoDeSalud("CS-Juan-Perez-ID001-2100-01-02-A-P");
            CertificadoDeSalud c3 = new CertificadoDeSalud("CS-Ana-Lopez-ID002-2100-05-05-A-E");
            CertificadoDeSalud c4 = new CertificadoDeSalud("CS-Lara-Gomez-ID003-2000-01-01-NA-P"); // viejo y no apto

            agregarCertificado(c1);
            agregarCertificado(c2);
            agregarCertificado(c3);
            agregarCertificado(c4);

            System.out.println("✅ Datos de prueba precargados (3 pasajeros, 4 certificados).");

        } catch (Exception ex) {
            System.err.println("Error al precargar datos: " + ex.getMessage());
        }
    }
}