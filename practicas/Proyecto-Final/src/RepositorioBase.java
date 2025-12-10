/**
 * Clase Abstracta: RepositorioBase&lt;T&gt;
 *
 * Implementa un repositorio genérico usando arreglos,
 * sin listas ni colecciones, con soporte de persistencia
 * en archivos de texto.
 *
 * @param <T> Tipo genérico que debe ser {Identificable} y {Persistible}.
 */
public abstract class RepositorioBase<T extends Identificable & Persistible> {

    /** Arreglo interno donde se almacenan los elementos del repositorio. */
    protected T[] elementos;

    /** Número actual de elementos válidos almacenados en el arreglo. */
    protected int contador;

    /**
     * Constructor del repositorio.
     *
     * Se recibe un arreglo ya creado por la subclase concreta, que
     * determina la capacidad máxima del repositorio.
     *
     * @param arreglo Arreglo donde se almacenarán los elementos.
     */
    public RepositorioBase(T[] arreglo) {
        this.elementos = arreglo;
        this.contador = 0;
    }

    /**
     * Método Calculador: agregar
     *
     * Agrega un elemento al arreglo si hay espacio disponible.
     *
     * @param t Elemento a agregar.
     * @throws CapacidadLlenaException si el arreglo ya está lleno.
     */
    public void agregar(T t) throws CapacidadLlenaException {
        if (t == null) return;
        if (contador >= elementos.length) {
            throw new CapacidadLlenaException("Capacidad llena en el repositorio.");
        }
        elementos[contador] = t;
        contador++;
    }

    /**
     * Método Calculador: buscarPorId
     *
     * Busca un elemento por su identificador.
     *
     * @param id Identificador entero.
     * @return Elemento encontrado.
     * @throws NoEncontradoException si no existe el elemento.
     */
    public T buscarPorId(int id) throws NoEncontradoException {
        for (int i = 0; i < contador; i++) {
            if (elementos[i].getId() == id) {
                return elementos[i];
            }
        }
        throw new NoEncontradoException("No se encontró el elemento con id " + id);
    }

    /**
     * Método Calculador: eliminarPorId
     *
     * Elimina el elemento con el identificador indicado.
     * Tras la eliminación, compacta el arreglo desplazando los
     * elementos posteriores una posición hacia la izquierda.
     *
     * @param id Identificador del elemento.
     * @throws NoEncontradoException si no existe.
     */
    public void eliminarPorId(int id) throws NoEncontradoException {
        boolean encontrado = false;
        for (int i = 0; i < contador; i++) {
            if (elementos[i].getId() == id) {
                for (int j = i; j < contador - 1; j++) {
                    elementos[j] = elementos[j + 1];
                }
                elementos[contador - 1] = null;
                contador--;
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new NoEncontradoException("No se encontró el elemento con id " + id);
        }
    }

    /**
     * Devuelve el arreglo interno de elementos.
     * Puede contener posiciones en la parte final
     * si no se ha llenado completamente la capacidad.
     *
     * @return Arreglo de elementos almacenados.
     */
    public T[] getTodos() {
        return elementos;
    }

    // ================== PERSISTENCIA ==================

    /**
     * Guarda todos los elementos del repositorio en un archivo de texto.
     *
     * Cada elemento se escribe en una línea, utilizando para ello
     * su método {Persistible#toLineaTexto()}.
     *
     * @param ruta Ruta del archivo donde se guardarán los datos.
     * @throws java.io.IOException Si ocurre un error de entrada/salida
     *                             durante la escritura del archivo.
     */
    public void guardarTodo(String ruta) throws java.io.IOException {
        java.io.BufferedWriter bw = new java.io.BufferedWriter(
                new java.io.FileWriter(ruta));
        for (int i = 0; i < contador; i++) {
            bw.write(elementos[i].toLineaTexto());
            bw.newLine();
        }
        bw.close();
    }

    /**
     * Carga todos los elementos desde un archivo de texto,
     * reemplazando el contenido actual del repositorio.
     *
     * Cada línea del archivo se convierte en un objeto {T}
     * mediante el método {crearDesdeLinea(String)} de la subclase.
     *
     * Si el archivo no existe, el método no hace nada.
     *
     * @param ruta Ruta del archivo desde donde se leerán los datos.
     * @throws java.io.IOException         Si ocurre un error de lectura.
     * @throws FormatoInvalidoException    Si alguna línea no cumple el formato
     *                                     esperado para reconstruir el objeto.
     */
    public void cargarTodo(String ruta)
            throws java.io.IOException, FormatoInvalidoException {
        java.io.File archivo = new java.io.File(ruta);
        if (!archivo.exists()) return;

        java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.FileReader(archivo));
        String linea;
        contador = 0;
        while ((linea = br.readLine()) != null && contador < elementos.length) {
            T obj = crearDesdeLinea(linea);
            elementos[contador] = obj;
            contador++;
        }
        br.close();
    }

    /**
     * Método Abstracto: crearDesdeLinea
     *
     * Cada subclase sabe cómo construir su objeto {T} desde una
     * línea de texto previamente serializada.
     *
     * @param linea Línea leída del archivo.
     * @return Objeto {@code T} creado a partir de la línea.
     * @throws FormatoInvalidoException si la línea no tiene el formato correcto.
     */
    protected abstract T crearDesdeLinea(String linea) throws FormatoInvalidoException;
}
