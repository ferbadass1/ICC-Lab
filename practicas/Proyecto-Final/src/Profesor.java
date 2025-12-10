/**
 * Clase Modelo: Profesor
 *
 * Representa a un profesor con su número de trabajador, niveles y categorías
 * que puede impartir, así como datos de su título.
 */
public class Profesor extends Persona implements Identificable, Persistible {

    private int numeroTrabajador;
    private String[] nivelesQueDa;
    private String[] categoriasQueDa;
    private String titulo;
    private int anioTitulo;

    /**
     * Constructor vacío.
     */
    public Profesor() { }

    /**
     * Constructor completo.
     *
     * @param nombreCompleto  Nombre del profesor.
     * @param numeroTrabajador Número de trabajador (ID único).
     * @param nivelesQueDa     Arreglo de niveles que imparte.
     * @param categoriasQueDa  Arreglo de categorías que imparte.
     * @param titulo           Título académico.
     * @param anioTitulo       Año de obtención del título.
     */
    public Profesor(String nombreCompleto, int numeroTrabajador,
                    String[] nivelesQueDa, String[] categoriasQueDa,
                    String titulo, int anioTitulo) {

        super(nombreCompleto);
        this.numeroTrabajador = numeroTrabajador;
        this.nivelesQueDa = nivelesQueDa;
        this.categoriasQueDa = categoriasQueDa;
        this.titulo = titulo;
        this.anioTitulo = anioTitulo;
    }

    @Override
    public int getId() {
        return numeroTrabajador;
    }

    @Override
    public String getIdentificador() {
        return String.valueOf(numeroTrabajador);
    }

    public int getNumeroTrabajador() {
        return numeroTrabajador;
    }

    public void setNumeroTrabajador(int numeroTrabajador) {
        this.numeroTrabajador = numeroTrabajador;
    }

    public String[] getNivelesQueDa() {
        return nivelesQueDa;
    }

    public String[] getCategoriasQueDa() {
        return categoriasQueDa;
    }

    public void setNivelesQueDa(String[] nivelesQueDa) {
    this.nivelesQueDa = nivelesQueDa;
    }

    public void setCategoriasQueDa(String[] categoriasQueDa) {
        this.categoriasQueDa = categoriasQueDa;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAnioTitulo(int anioTitulo) {
        this.anioTitulo = anioTitulo;
    }


    /**
     * Método Calculador: puedeImpartir
     *
     * Verifica si el profesor puede impartir un curso del nivel y categoría
     * especificados, según sus listas internas.
     *
     * @param nivel    Nivel del curso.
     * @param categoria Categoría del curso.
     * @return true si puede impartirlo, false en caso contrario.
     */
    public boolean puedeImpartir(String nivel, String categoria) {
        boolean nivelOk = false;
        boolean categoriaOk = false;

        if (nivelesQueDa != null) {
            for (int i = 0; i < nivelesQueDa.length; i++) {
                if (nivelesQueDa[i] != null &&
                    nivelesQueDa[i].equalsIgnoreCase(nivel)) {
                    nivelOk = true;
                    break;
                }
            }
        }

        if (categoriasQueDa != null) {
            for (int i = 0; i < categoriasQueDa.length; i++) {
                if (categoriasQueDa[i] != null &&
                    categoriasQueDa[i].equalsIgnoreCase(categoria)) {
                    categoriaOk = true;
                    break;
                }
            }
        }
        return nivelOk && categoriaOk;
    }

    /**
     * Método Calculador: verificarCategoriasProfesor
     *
     * Encargado de lanzar una excepción si alguna de las categorías asignadas
     * al profesor no corresponde a las opciones disponibles en el catálogo.
     *
     * @param categorias Cadena con categorías separadas por coma.
     * @throws CategoriaInvalidaException si alguna categoría no es válida.
     */
    public void verificarCategoriasProfesor(String categorias)
            throws CategoriaInvalidaException {

        if (categorias == null || categorias.trim().isEmpty()) {
            throw new CategoriaInvalidaException("Debe especificar al menos una categoría.");
        }

        String[] datos = categorias.split(",");

        for (int i = 0; i < datos.length; i++) {
            String cat = datos[i].trim();
            try {
                verificarCategoria(cat);
            } catch (CategoriaInvalidaException e) {
                throw new CategoriaInvalidaException(
                        "\nAlguna de las categorías ingresadas no corresponde a una de las opciones válidas: " + cat);
            }
        }
    }

    /**
     * Método Calculador: verificarCategoria
     *
     * Valida una categoría individual contra el catálogo.
     *
     * @param categoria Categoría a validar.
     * @throws CategoriaInvalidaException si la categoría no es válida.
     */
    private void verificarCategoria(String categoria)
            throws CategoriaInvalidaException {

        if (!Catalogos.esValorValido(categoria, Catalogos.CATEGORIAS)) {
            throw new CategoriaInvalidaException(
                    "Categoría inválida: " + categoria);
        }
    }

    // ========= Persistible ===========

    @Override
    public String toLineaTexto() {
        return numeroTrabajador + "|" + nombreCompleto + "|" +
                titulo + "|" + anioTitulo;
    }

    @Override
    public void fromLineaTexto(String linea) throws FormatoInvalidoException {
        String[] partes = linea.split("\\|");
        if (partes.length < 4) {
            throw new FormatoInvalidoException("Línea inválida para Profesor: " + linea);
        }
        try {
            this.numeroTrabajador = Integer.parseInt(partes[0]);
            this.nombreCompleto = partes[1];
            this.titulo = partes[2];
            this.anioTitulo = Integer.parseInt(partes[3]);
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("Error al parsear Profesor: " + linea);
        }
    }
}
