/**
 * Programa principal.
 * Se encarga únicamente de inicializar la AgenciaDeViajes (Datos) y el MenuHandler (UI).
 */
public class MainAgencia{

    public static void main(String[] args) {
        // 1. Inicializar la Agencia (Datos y Lógica de Búsqueda)
        AgenciaDeViajes agencia = new AgenciaDeViajes();
        agencia.cargarDatosPrecargados();

        // 2. Inicializar y ejecutar la Interfaz de Usuario
        MenuHandler menu = new MenuHandler(agencia);
        menu.iniciar();
    }
}