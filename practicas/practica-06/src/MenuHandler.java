import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Maneja la interacción con el usuario (menú, entrada/salida)
 * y coordina las operaciones con la AgenciaDeViajes.
 */
public class MenuHandler {

    private final Scanner sc;
    private final AgenciaDeViajes agencia;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public MenuHandler(AgenciaDeViajes agencia) {
        this.sc = new Scanner(System.in);
        this.agencia = agencia;
        sdf.setLenient(false);
    }

    /**
     * Bucle principal de la aplicación
     */
    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            String opcion = sc.nextLine().trim();

            try {
                switch (opcion) {
                    case "1":
                        generarPasaporte();
                        break;
                    case "2":
                        generarCertificado();
                        break;
                    case "3":
                        procesarVueloInternacional();
                        break;
                    case "4":
                        procesarVueloEstratosferico();
                        break;
                    case "5":
                        procesarVueloInterplanetario();
                        break;
                    case "0":
                        continuar = false;
                        System.out.println("Gracias por usar UNAM Vuelos. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intenta de nuevo.");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("❌ Error inesperado: " + ex.getMessage());
            }
        }
        sc.close();
    }

    private void mostrarMenu() {
        System.out.println("\n--- Bienvenido a UNAM Vuelos---");
        System.out.println("1. Generar Pasaporte");
        System.out.println("2. Generar Certificado de Salud");
        System.out.println("3. Realizar Vuelo Internacional");
        System.out.println("4. Realizar Vuelo Estratosferico");
        System.out.println("5. Realizar Vuelo Interplanetario");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    /**
    * Lógica de Captura y Validación de Campos Reutilizable
    */

    private String pedirCampoNoVacio(String prompt) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("❌ El campo no puede estar vacío. Intenta otra vez.");
            } else {
                return input;
            }
        }
    }

    private String pedirCodigoPais(String prompt) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = sc.nextLine().trim();
            if (!input.matches("[A-Z]{2}")) {
                System.out.println("❌ Debe ser exactamente 2 letras mayúsculas. Ej: MX");
            } else {
                return input;
            }
        }
    }

    private int pedirEdad() {
        int edad;
        while (true) {
            System.out.println("Ingresa edad (solo numero): ");
            String eStr = sc.nextLine().trim();
            try {
                edad = Integer.parseInt(eStr);
                if (edad <= 0) {
                    System.out.println("❌ La edad debe ser mayor a 0.");
                } else {
                    return edad;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("❌ Edad inválida. Ingresa un número entero.");
            }
        }
    }

    private Date pedirFechaPartida() {
        Date fechaPartida;
        while (true) {
            System.out.println("Fecha de partida (yyyy-MM-dd):");
            String fstr = sc.nextLine().trim();
            try {
                fechaPartida = sdf.parse(fstr);
                Date hoy = new Date();
                if (!fechaPartida.after(hoy)) {
                    System.out.println("❌ La fecha de partida debe ser futura.");
                    continue;
                }
                return fechaPartida;
            } catch (ParseException pe) {
                System.out.println("❌ Fecha inválida. Usa yyyy-MM-dd.");
            }
        }
    }

    private String pedirIDPasaporte(boolean debeExistir) {
        String id;
        while (true) {
            System.out.println("Ingresa numero de identificacion (ID único):");
            id = sc.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println("❌ El ID no puede estar vacío.");
                continue;
            }
            boolean existe = agencia.existePasaporteConID(id);
            if (debeExistir && !existe) {
                System.out.println("❌ No existe pasajero con ese ID. Primero genera su pasaporte (opción 1).");
            } else if (!debeExistir && existe) {
                System.out.println("❌ Ese ID ya existe. Ingresa otro.");
            } else {
                return id;
            }
        }
    }

    /**
    * Lógica del menu (acciones)
    */

    private void generarPasaporte() {
        String nombre = pedirCampoNoVacio("Ingresa nombre: ");
        String apellido = pedirCampoNoVacio("Ingresa apellido: ");
        int edad = pedirEdad();
        String nacionalidad = pedirCodigoPais("Ingresa nacionalidad (2 letras mayúsculas):");
        String paisOrigen = pedirCodigoPais("Ingresa pais de origen (2 letras mayúsculas):");
        String id = pedirIDPasaporte(false); // ID no debe existir

        Pasaporte nuevo = new Pasaporte(nombre, apellido, edad, nacionalidad, paisOrigen, id, null);
        nuevo.calcularFechaVencimiento();

        if (agencia.agregarPasaporte(nuevo)) {
            System.out.println("✅ Pasaporte generado: " + nuevo);
        }
    }

    private void generarCertificado() {
        // Se requiere que exista el pasajero con ese ID
        String idPas = pedirIDPasaporte(true); // ID debe existir

        String nombre = pedirCampoNoVacio("Nombre (tal cual en el pasaporte):");
        String apellido = pedirCampoNoVacio("Apellido (tal cual en el pasaporte):");

        Date fechaCert;
        while (true) {
            System.out.println("Fecha certificado (yyyy-MM-dd):");
            String fechaStr = sc.nextLine().trim();
            try {
                fechaCert = sdf.parse(fechaStr);
                break;
            } catch (ParseException pe) {
                System.out.println("❌ Fecha inválida. Usa yyyy-MM-dd.");
            }
        }

        String clave;
        while (true) {
            System.out.println("Clave de salud (A = apto, NA = no apto):");
            clave = sc.nextLine().trim();
            if (!(clave.equals("A") || clave.equals("NA"))) {
                System.out.println("❌ Debe ser 'A' o 'NA'.");
            } else {
                break;
            }
        }

        String tipo;
        while (true) {
            System.out.println("Tipo de viaje (E = Estratosférico, P = Interplanetario):");
            tipo = sc.nextLine().trim();
            if (!(tipo.equals("E") || tipo.equals("P"))) {
                System.out.println("❌ Debe ser 'E' o 'P'.");
            } else {
                break;
            }
        }

        // Generamos la cadena completa
        String yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd").format(fechaCert);
        String[] parts = yyyyMMdd.split("-");
        String rawExact = "CS-" + nombre + "-" + apellido + "-" + idPas + "-" + parts[0] + "-" + parts[1] + "-" + parts[2] + "-" + clave + "-" + tipo;

        try {
            CertificadoDeSalud cert = new CertificadoDeSalud(rawExact);
            if (agencia.agregarCertificado(cert)) {
                System.out.println("✅ Certificado generado: " + cert);
            }
        } catch (CertificadoException ce) {
            System.out.println("❌ Error al generar certificado: " + ce.getMessage());
        }
    }

    private void procesarVueloInternacional() {
        // Encontrar Pasajero
        Pasaporte pasajero = buscarPasajeroParaVuelo();
        if (pasajero == null) return;

        // Datos del Vuelo
        String paisPartida = pedirCodigoPais("País desde el cual parte el vuelo (2 letras mayúsculas):");
        String origen = pedirCampoNoVacio("Origen (ciudad/terminal):");
        String destino = pedirDestinoVuelo(origen);
        Date fechaPartida = pedirFechaPartida();

        VueloInternacional vuelo = new VueloInternacional(paisPartida, origen, destino, fechaPartida, pasajero);
        try {
            vuelo.validar();
            System.out.println("✈️  Puede viajar en vuelo internacional.");
        } catch (VueloException ve) {
            System.out.println("❌ No puede volar: " + ve.getMessage());
        }
    }

    private void procesarVueloEstratosferico() {
        // Encontrar Pasajero
        Pasaporte pasajero = buscarPasajeroParaVuelo();
        if (pasajero == null) return;

        // Datos del Vuelo
        String paisPartida = pedirCodigoPais("País desde el cual parte el vuelo (2 letras mayúsculas):");
        String origen = pedirCampoNoVacio("Origen (ciudad/terminal):");
        String destino = pedirDestinoVuelo(origen);
        Date fechaPartida = pedirFechaPartida();

        // Buscar Certificado
        CertificadoDeSalud certSel = seleccionarCertificado(pasajero.getNumID(), "E", "Estratosférico");
        if (certSel == null) return;

        VueloEstratosferico vuelo = new VueloEstratosferico(certSel, paisPartida, origen, destino, fechaPartida, pasajero);
        try {
            vuelo.validar();
            System.out.println("🚀 Puede viajar en vuelo estratosférico.");
        } catch (VueloException ve) {
            System.out.println("❌ No puede volar: " + ve.getMessage());
        }
    }

    private void procesarVueloInterplanetario() {
        // Encontrar Pasajero
        Pasaporte pasajero = buscarPasajeroParaVuelo();
        if (pasajero == null) return;

        // Datos del Vuelo Interplanetario
        String planetaOrigen = pedirCampoNoVacio("Planeta de origen (ej. Tierra):");
        String planetaDestino;
        while (true) {
            planetaDestino = pedirCampoNoVacio("Planeta destino (ej. Marte):");
            if (planetaDestino.equalsIgnoreCase(planetaOrigen)) {
                System.out.println("❌ El destino no puede ser el mismo planeta del origen.");
                continue;
            }
            break;
        }
        Date fechaPartida = pedirFechaPartida();

        // Buscar Certificado
        CertificadoDeSalud certSel = seleccionarCertificado(pasajero.getNumID(), "P", "Interplanetario");
        if (certSel == null) return;

        VueloInterplanetario vuelo = new VueloInterplanetario(planetaOrigen, planetaDestino, certSel, fechaPartida, pasajero);
        try {
            vuelo.validar();
            System.out.println("🪐 Puede viajar en vuelo interplanetario.");
        } catch (VueloException ve) {
            System.out.println("❌ No puede volar: " + ve.getMessage());
        }
    }

    // Auxiliares de Vuelo y Certificado

    private Pasaporte buscarPasajeroParaVuelo() {
        System.out.println("Ingresa ID del pasajero que desea volar:");
        String idBuscar = sc.nextLine().trim();
        Pasaporte pasajero = agencia.encontrarPasajeroPorID(idBuscar);
        if (pasajero == null) {
            System.out.println("❌ No existe pasajero con ese ID.");
            return null;
        }
        return pasajero;
    }

    private String pedirDestinoVuelo(String origen) {
        String destino;
        while (true) {
            System.out.println("Destino (ciudad/terminal):");
            destino = sc.nextLine().trim();
            if (destino.isEmpty()) {
                System.out.println("❌ Destino vacío.");
                continue;
            }
            if (destino.equalsIgnoreCase(origen)) {
                System.out.println("❌ El destino no puede ser igual al origen.");
                continue;
            }
            return destino;
        }
    }

    private CertificadoDeSalud seleccionarCertificado(String idPasajero, String tipoViaje, String nombreViaje) {
        CertificadoDeSalud[] matches = agencia.buscarCertificadosPorIDyTipo(idPasajero, tipoViaje);
        if (matches.length == 0) {
            System.out.println("❌ No hay certificados tipo " + tipoViaje + " ('" + nombreViaje + "') aptos para este pasajero. Genera uno (opción 2).");
            return null;
        }

        // Mostrar coincidencias y pedir elegir
        System.out.println("Certificados compatibles:");
        for (int i = 0; i < matches.length; i++) {
            System.out.println(i + ") " + matches[i]);
        }
        int elegido = -1;
        while (true) {
            System.out.println("Selecciona índice del certificado a usar:");
            String sidx = sc.nextLine().trim();
            try {
                elegido = Integer.parseInt(sidx);
                if (elegido < 0 || elegido >= matches.length) {
                    System.out.println("❌ Índice fuera de rango.");
                    continue;
                }
                return matches[elegido];
            } catch (NumberFormatException nfe) {
                System.out.println("❌ Ingresa un número válido.");
            }
        }
    }
}   