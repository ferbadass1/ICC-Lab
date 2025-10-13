import java.util.Scanner;

public class AutoMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double total = 0;

        System.out.println("\n=== Venta de Autos ===\n");
        System.out.println("Bienvenido! Vamos a armar tu auto\n");

        // ---------------- MARCA Y PAÍS ----------------
        // Muestra las marcas de autos disponibles y solicita la elección del usuario.

        System.out.println("-- Marcas disponibles --");
        System.out.println("1. Honda");
        System.out.println("2. BYD");
        System.out.println("3. Volkswagen");
        System.out.println("4. Toyota");
        System.out.println("5. Nissan");
        System.out.print("\nElige una opción: ");
        int opcionMarca = sc.nextInt();
        sc.nextLine();

        // Convierte la opción numérica del usuario en el nombre de la marca.
        String marca = "honda";
        switch (opcionMarca) {
            case 1: marca = "honda"; break;
            case 2: marca = "byd"; break;
            case 3: marca = "volkswagen"; break;
            case 4: marca = "toyota"; break;
            case 5: marca = "nissan"; break;
            default: System.out.println("Opción no válida. Marca Honda asignada por default"); return;
        }

        // Muestra los países de fabricación y obtiene la elección del usuario.
        mostrarPaisesDisponibles(marca);
        System.out.print("Elige una opción: ");
        int opcionPais = sc.nextInt();
        sc.nextLine();

        // Obtiene los datos del país, calcula el precio y actualiza el total.
        String pais = obtenerPaisPorOpcion(marca, opcionPais);
        double precioImportacion = obtenerPrecioImportacion(marca, pais);
        total += precioImportacion;
        System.out.println("→ Precio de importación: $" + precioImportacion);
        System.out.println("Total acumulado: $" + total + "\n");

        MarcaAutomovil marcaAuto = new MarcaAutomovil(marca, pais, precioImportacion);

        // ---------------- TRANSMISIÓN ----------------
        // Muestra las opciones de transmisión y captura la elección del usuario.
        System.out.println("-- Tipo de Transmisión --");
        System.out.println("1. Manual ($40,000)");
        System.out.println("2. Automática ($80,000)");
        System.out.print("\nElige una opción: ");
        int opTrans = sc.nextInt();
        sc.nextLine();

        // Determina la transmisión y su precio, y actualiza el total.
        String transmision = (opTrans == 1) ? "Manual" : "Automática";
        double precioTransmision = (opTrans == 1) ? 40000 : 80000;
        total += precioTransmision;
        System.out.println("→ Precio transmisión: $" + precioTransmision);
        System.out.println("Total acumulado: $" + total + "\n");

        // ---------------- TIPO DE AUTO ----------------
        // Muestra los tipos de auto disponibles y captura la elección.
        System.out.println("-- Tipo de Auto --");
        System.out.println("1. Compacto ($40,000)");
        System.out.println("2. Semicompacto ($80,000)");
        System.out.println("3. Sedán ($120,000)");
        System.out.println("4. Deportivo ($200,000)");
        System.out.print("\nElige una opción: ");
        int opAuto = sc.nextInt();
        sc.nextLine();

        // Asigna el tipo de auto y su precio según la opción, y actualiza el total.
        String tipoAuto = "";
        double precioAuto = 0;
        switch (opAuto) {
            case 1: tipoAuto = "Compacto"; precioAuto = 40000; break;
            case 2: tipoAuto = "Semicompacto"; precioAuto = 80000; break;
            case 3: tipoAuto = "Sedán"; precioAuto = 120000; break;
            case 4: tipoAuto = "Deportivo"; precioAuto = 200000; break;
        }
        total += precioAuto;
        System.out.println("→ Precio tipo de auto: $" + precioAuto);
        System.out.println("Total acumulado: $" + total + "\n");

        // ---------------- AÑO DEL AUTO ----------------
        // Muestra los años disponibles y captura la elección.
        System.out.println("-- Año del Auto --");
        System.out.println("1. 2023 ($30,000)");
        System.out.println("2. 2024 ($40,000)");
        System.out.println("3. 2025 ($50,000)");
        System.out.print("\nElige una opción: ");
        int opAnio = sc.nextInt();
        sc.nextLine();

        // Calcula el año y su precio, y actualiza el total.
        int anio = 2023 + (opAnio - 1);
        double precioAnio = (opAnio == 1) ? 30000 : (opAnio == 2) ? 40000 : 50000;
        total += precioAnio;
        System.out.println("→ Precio año: $" + precioAnio);
        System.out.println("Total acumulado: $" + total + "\n");

        // ---------------- CHASIS ----------------
        // Solicita el tipo de acabado del chasis.
        System.out.println("-- Chasis --");
        System.out.println("Acabado del Chasis");
        System.out.println("1. Mate ($0)");
        System.out.println("2. Brillante ($10,000)");
        System.out.println("3. Metálico ($15,000)");
        System.out.print("\nElige una opción: ");
        int opAcabado = sc.nextInt();
        sc.nextLine();

        // Determina el acabado y su precio, y actualiza el total.
        String acabado = (opAcabado == 1) ? "Mate" : (opAcabado == 2) ? "Brillante" : "Metálico";
        double precioChasis = obtenerPrecioChasis(acabado);
        total += precioChasis;

        // Solicita el color del chasis (sin costo).
        System.out.println("\nColor del Chasis (Gratis)");
        System.out.println("1. Negro");
        System.out.println("2. Rojo");
        System.out.println("3. Azul");
        System.out.println("4. Blanco");
        System.out.println("5. Gris");
        System.out.print("\nElige una opción: ");

        int colorOpciones = sc.nextInt();
        sc.nextLine();

        // Asigna el color según la opción del usuario.
        String color = "Azul"; //Color default
        switch(colorOpciones){
            case 1: color = "Negro"; break;
            case 2: color = "Rojo"; break;
            case 3: color = "Azul";  break;
            case 4: color = "Blanco"; break;
            case 5: color = "Gris"; break;
            default:
                System.out.println("Color no válido. Color azul asignado por default");
        }

        System.out.println("→ Precio chasis: $" + precioChasis);
        System.out.println("Total acumulado: $" + total + "\n");

        // Crea el objeto Chasis con la información seleccionada.
        Chasis chasis = new Chasis(color, acabado, precioChasis);

        // ---------------- LLANTAS ----------------
        // Muestra las marcas de llantas y captura la elección.
        System.out.println("-- Llantas --");
        System.out.println("1. Yokohama ($5,000)");
        System.out.println("2. Firestone ($8,000)");
        System.out.println("3. Pirelli ($6,500)");
        System.out.println("4. Goodyear ($6,000)");
        System.out.println("5. Michelin ($10,000)");
        System.out.print("\nElige una opción: ");
        int opLlanta = sc.nextInt();
        sc.nextLine();

        // Obtiene la marca y el precio de las llantas, y actualiza el total.
        String marcaLlantas = obtenerMarcaLlantas(opLlanta);
        double precioLlantas = obtenerPrecioLlantas(marcaLlantas);
        total += precioLlantas;
        System.out.println("→ Precio llanta: $" + precioLlantas);
        System.out.println("Total acumulado: $" + total + "\n");

        // ---------------- RIN ----------------
        // Solicita el material del rin.
        System.out.println("-- Rines --");
        System.out.println("Material");
        System.out.println("1. Aluminio ($8,000)");
        System.out.println("2. Acero ($3,000)");
        System.out.print("\nElige una opción: ");
        int opMaterial = sc.nextInt();
        sc.nextLine();

        String materialRin = (opMaterial == 1) ? "Aluminio" : "Acero";
        double precioRin = obtenerPrecioRin(materialRin);
        total += precioRin;

        // Solicita el tamaño del rin.
        System.out.println("\nTamaño");
        System.out.println("1. R15");
        System.out.println("2. R16");
        System.out.println("3. R17");
        System.out.println("4. R18");
        System.out.print("\nElige una opción: ");
        int rinOpcion = sc.nextInt();
        sc.nextLine();

        // Asigna el tamaño del rin según la opción.
        int tamanoRin = 15; // Inicializar a un valor seguro (ej: 15)
        switch(rinOpcion){
            case 1: tamanoRin = 15; break;
            case 2: tamanoRin = 16; break;
            case 3: tamanoRin = 17; break;
            case 4: tamanoRin = 18; break;
            default: 
                System.out.println("Opción de tamaño de rin no válida. Usando 15 pulgadas por default");
                break;
        }


        System.out.println("→ Precio Rin: $" + precioRin);
        System.out.println("Total acumulado: $" + total + "\n");

        // Crea el objeto Llanta, sumando el precio de las llantas y del rin.
        Llanta llanta = new Llanta(marcaLlantas, tamanoRin, materialRin, precioLlantas + precioRin);

        // ---------------- SISTEMA ELECTRÓNICO ----------------
        // Pregunta por cada componente electrónico.
        System.out.println("-- Sistema Electrónico --");
        System.out.println("Pantalla digital ($3,000)" + "\n" + "1. Sí" + "\n" + "2. No ");
        System.out.println("\nElige una opción: ");
        int opPantalla = sc.nextInt();
        sc.nextLine();
        boolean pantalla = (opPantalla == 1);

        System.out.println("\nEspejos electrónicos ($5,000)" + "\n" + "1. Sí" + "\n" + "2. No ");
        System.out.println("\nElige una opción: ");
        int opEspejos = sc.nextInt();
        sc.nextLine();
        boolean espejos = (opEspejos == 1);

        System.out.println("\nSensor de reversa ($4,000)" + "\n" + "1. Sí" + "\n" + "2. No ");
        System.out.println("\nElige una opción: ");
        int opSensor = sc.nextInt();
        sc.nextLine();
        boolean sensor = (opSensor == 1);

        System.out.println("\nCámara de reversa ($7,000)" + "\n" + "1. Sí" + "\n" + "2. No ");
        System.out.println("\nElige una opción: ");
        int opCamara = sc.nextInt();
        sc.nextLine();
        boolean camara = (opCamara == 1);

        // Solicita el tipo de frenos.
        System.out.println("\nTipo de frenos:");
        System.out.println("1. Frenos de disco ($3,200)");
        System.out.println("2. Frenos de tambor ($2,100)");
        System.out.println("\nElige una opción: ");
        int opFrenos = sc.nextInt();
        sc.nextLine();

        // Determina el tipo de frenos y su precio.
        String tipoFrenos = (opFrenos == 1) ? "Frenos de disco" : "Frenos de tambor";
        double precioFrenos = (opFrenos == 1) ? 3200 : 2100;

        // Calcula el precio total del sistema electrónico y actualiza el total general.
        double precioSistema = (pantalla ? 3000 : 0) + (espejos ? 5000 : 0)
                             + (sensor ? 4000 : 0) + (camara ? 7000 : 0) + precioFrenos;
        total += precioSistema;

        System.out.println("\n→ Precio sistema electrónico: $" + precioSistema);
        System.out.println("Total acumulado: $" + total + "\n");

        // Crea el objeto SistemaElectronico.
        SistemaElectronico sistema = new SistemaElectronico(pantalla, espejos, sensor, camara, tipoFrenos, precioSistema);

        // ---------------- PELÍCULA ANTI-ASALTO ----------------
        // Pregunta si desea agregar película anti-asalto.
        System.out.println("-- Película Anti-Asalto --");
        System.out.println("Precio: $4000" + "\n" + "1. Sí" + "\n" + "2. No");
        System.out.print("\nElige una opción: ");
        int opPelicula = sc.nextInt();
        sc.nextLine();
        boolean pelicula = (opPelicula == 1);

        double precioPelicula = pelicula ? 4000 : 0;
        total += precioPelicula;
        System.out.println("→ Precio película: $" + precioPelicula);
        System.out.println("Total acumulado: $" + total + "\n");

        // ---------------- RESUMEN ----------------
        // Crea el objeto Automovil con todos los componentes seleccionados.

        Automovil auto = new Automovil(marcaAuto, llanta, chasis, sistema, transmision, tipoAuto, anio, pelicula);

        System.out.println("=== RESUMEN FINAL ===");

        System.out.println("Marca: " + marcaAuto.getMarca() + " | Precio importación: $" + marcaAuto.getPrecio());
        System.out.println("País de fabricación: " + marcaAuto.getPais());
        System.out.println("Transmisión: " + transmision + " | Precio: $" + precioTransmision);
        System.out.println("Tipo de auto: " + tipoAuto + " | Precio: $" + precioAuto);
        System.out.println("Año: " + anio + " | Precio: $" + precioAnio);
        System.out.println("Chasis: Color " + chasis.getColor() + ", Acabado " + chasis.getAcabado() +
                        " | Precio: $" + chasis.getPrecio());
        System.out.println("Llantas: " + llanta.getMarca() + " | Rin " + llanta.getTamano() + " pulgadas (" +
                        llanta.getMaterial() + ") | Precio: $" + llanta.getPrecio());
        System.out.println("Sistema electrónico:");
        System.out.println("  Pantalla digital: " + (sistema.getPantallaDigital() ? "Sí" : "No"));
        System.out.println("  Espejos electrónicos: " + (sistema.getEspejosElectronicos() ? "Sí" : "No"));
        System.out.println("  Sensor de reversa: " + (sistema.getSensorReversa() ? "Sí" : "No"));
        System.out.println("  Cámara de reversa: " + (sistema.getCamaraReversa() ? "Sí" : "No"));
        System.out.println("  Tipo de frenos: " + sistema.tipoFrenos());
        System.out.println("Precio total del Sistema Electrónico: " + sistema.getPrecio());
        System.out.println("Película anti-asalto: " + (pelicula ? "Sí" : "No") + " | Precio: $" + precioPelicula);
        System.out.println("TOTAL GENERAL: $" + total);

        // Pregunta al usuario si desea confirmar la compra.
        System.out.println("\n¿Desea comprar el auto?: " + "\n" + "1. Sí" + "\n" + "2. No ");
        int compra = sc.nextInt();
        if (compra == 1) {
            System.out.println("¡Felicidades! Su compra ha sido registrada.");
        } else {
            System.out.println("Compra cancelada. Gracias por su visita.");
        }
        // Cierra el scanner para liberar recursos.
        sc.close();
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    // Muestra los países de fabricación disponibles para una marca específica.
    public static void mostrarPaisesDisponibles(String marca) {
        System.out.println("\n-- Países disponibles --");
        switch (marca) {
            case "honda":
                System.out.println("1. México ($25,000)");
                System.out.println("2. Japón ($105,000)");
                System.out.println("3. India ($55,000)");
                break;
            case "byd": System.out.println("1. China ($30,000)"); break;
            case "volkswagen":
                System.out.println("1. México ($15,000)");
                System.out.println("2. Alemania ($80,000)");
                System.out.println("3. China ($32,000)");
                System.out.println("4. Brasil ($42,000)");
                break;
            case "toyota":
                System.out.println("1. México ($20,000)");
                System.out.println("2. Estados Unidos ($40,000)");
                System.out.println("3. Brasil ($40,000)");
                System.out.println("4. Francia ($90,000)");
                break;
            case "nissan":
                System.out.println("1. Japón ($100,000)");
                System.out.println("2. México ($18,000)");
                break;
        }
        System.out.println();
    }

    // Devuelve el nombre de un país a partir de la marca y la opción numérica del usuario.
    public static String obtenerPaisPorOpcion(String marca, int opcion) {
        switch (marca) {
            case "honda":
                switch (opcion) {
                    case 1: return "México";
                    case 2: return "Japón";
                    case 3: return "India";
                } break;
            case "byd": return "China";
            case "volkswagen":
                switch (opcion) {
                    case 1: return "México";
                    case 2: return "Alemania";
                    case 3: return "China";
                    case 4: return "Brasil";
                } break;
            case "toyota":
                switch (opcion) {
                    case 1: return "México";
                    case 2: return "Estados Unidos";
                    case 3: return "Brasil";
                    case 4: return "Francia";
                } break;
            case "nissan":
                switch (opcion) {
                    case 1: return "Japón";
                    case 2: return "México";
                } break;
        }
        return "Desconocido";
    }

    // Devuelve el precio de importación según la marca y el país de fabricación.
    public static double obtenerPrecioImportacion(String marca, String pais) {
        switch (marca) {
            case "honda":
                switch (pais.toLowerCase()) {
                    case "méxico": return 25000;
                    case "japón": return 105000;
                    case "india": return 55000;
                } break;
            case "byd": return 30000;
            case "volkswagen":
                switch (pais.toLowerCase()) {
                    case "méxico": return 15000;
                    case "alemania": return 80000;
                    case "china": return 32000;
                    case "brasil": return 42000;
                } break;
            case "toyota":
                switch (pais.toLowerCase()) {
                    case "méxico": return 20000;
                    case "estados unidos": return 40000;
                    case "brasil": return 40000;
                    case "francia": return 90000;
                } break;
            case "nissan":
                switch (pais.toLowerCase()) {
                    case "japón": return 100000;
                    case "méxico": return 18000;
                } break;
        }
        return 0;
    }

    // Devuelve el precio de un juego de llantas según su marca.
    public static double obtenerPrecioLlantas(String marca) {
        switch (marca.toLowerCase()) {
            case "yokohama": return 5000;
            case "firestone": return 8000;
            case "pirelli": return 6500;
            case "goodyear": return 6000;
            case "michelin": return 10000;
            default: return 0;
        }
    }

    // Devuelve el nombre de la marca de llantas según la opción numérica del usuario.
    public static String obtenerMarcaLlantas(int opcion) {
        switch (opcion) {
            case 1: return "Yokohama";
            case 2: return "Firestone";
            case 3: return "Pirelli";
            case 4: return "Goodyear";
            case 5: return "Michelin";
        }
        return "Desconocida";
    }

    // Devuelve el precio del rin según su material.
    public static double obtenerPrecioRin(String material) {
        if (material.equalsIgnoreCase("aluminio")) return 8000;
        else if (material.equalsIgnoreCase("acero")) return 3000;
        return 0;
    }

    // Devuelve el precio del acabado del chasis.
    public static double obtenerPrecioChasis(String acabado) {
        switch (acabado.toLowerCase()) {
            case "mate": return 0;
            case "brillante": return 10000;
            case "metálico": return 15000;
        }
        return 0;
    }
}
