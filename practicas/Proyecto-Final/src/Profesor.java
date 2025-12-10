/**
 * Clase Modelo: Profesor
 *
 * Representa a un profesor con su número de trabajador, los niveles y
 * categorías que puede impartir, así como los datos de su título académico.
 * Además, permite verificar compatibilidad con cursos y ofrece soporte
 * para persistencia en texto.
 */
public class Profesor extends Persona implements Identificable, Persistible {

    /** Número de trabajador del profesor (identificador único). */
    private int numeroTrabajador;

    /** Arreglo de niveles educativos que el profesor puede impartir. */
    private String[] nivelesQueDa;

    /** Arreglo de categorías o áreas que el profesor puede impartir. */
    private String[] categoriasQueDa;

    /** Título académico principal del profesor. */
    private String titulo;

    /** Año en el que el profesor obtuvo su título académico. */
    private int anioTitulo;

    /**
     * Constructor vacío.
     * Permite crear el objeto y asignar sus campos posteriormente.
     */
    public Profesor() { }

    /**
     * Constructor completo.
     *
     * @param nombreCompleto   Nombre del profesor.
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

    /**
     * Obtiene el identificador numérico del profesor,
     * que corresponde a su número de trabajador.
     *
     * @return Número de trabajador del profesor.
     */
    @Override
    public int getId() {
        return numeroTrabajador;
    }

    /**
     * Devuelve el identificador único de la persona en formato texto.
     * En el caso de profesor, es el número de trabajador convertido a cadena.
     *
     * @return Identificador único en formato {@code String}.
     */
    @Override
    public String getIdentificador() {
        return String.valueOf(numeroTrabajador);
    }

    /**
     * Obtiene el número de trabajador del profesor.
     *
     * @return Número de trabajador.
     */
    public int getNumeroTrabajador() {
        return numeroTrabajador;
    }

    /**
     * Asigna el número de trabajador del profesor.
     *
     * @param numeroTrabajador Nuevo número de trabajador.
     */
    public void setNumeroTrabajador(int numeroTrabajador) {
        this.numeroTrabajador = numeroTrabajador;
    }

    /**
     * Obtiene el arreglo de niveles educativos que el profesor puede impartir.
     *
     * @return Arreglo de niveles.
     */
    public String[] getNivelesQueDa() {
        return nivelesQueDa;
    }

    /**
     * Obtiene el arreglo de categorías de cursos que el profesor puede impartir.
     *
     * @return Arreglo de categorías.
     */
    public String[] getCategoriasQueDa() {
        return categoriasQueDa;
    }

    /**
     * Obtiene el título académico del profesor.
     *
     * @return Título académico.
     */
    public String getTitulo() { 
        return titulo; 
    }
    
    /**
     * Obtiene el año de obtención del título académico.
     *
     * @return Año del título.
     */
    public int getAnioTitulo() { 
        return anioTitulo; 
    }

    /**
     * Asigna los niveles educativos que el profesor puede impartir.
     *
     * @param nivelesQueDa Arreglo de niveles.
     */
    public void setNivelesQueDa(String[] nivelesQueDa) {
        this.nivelesQueDa = nivelesQueDa;
    }

    /**
     * Asigna las categorías o áreas que el profesor puede impartir.
     *
     * @param categoriasQueDa Arreglo de categorías.
     */
    public void setCategoriasQueDa(String[] categoriasQueDa) {
        this.categoriasQueDa = categoriasQueDa;
    }

    /**
     * Asigna el título académico del profesor.
     *
     * @param titulo Nuevo título académico.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Asigna el año de obtención del título académico.
     *
     * @param anioTitulo Año del título.
     */
    public void setAnioTitulo(int anioTitulo) {
        this.anioTitulo = anioTitulo;
    }

    /**
     * Método Calculador: puedeImpartir
     *
     * Verifica si el profesor puede impartir un curso del nivel y categoría
     * especificados. Si no se registraron niveles o categorías para el profesor,
     * se asume que puede impartir cualquiera (comportamiento tolerante).
     *
     * @param nivel     Nivel del curso.
     * @param categoria Categoría del curso.
     * @return {@code true} si puede impartirlo, {@code false} en caso contrario.
     */
    public boolean puedeImpartir(String nivel, String categoria) {
        String nivelCurso = (nivel == null) ? "" : nivel.trim();
        String catCurso = (categoria == null) ? "" : categoria.trim();

        // Si no se definieron niveles para el profesor, asumimos que puede todos.
        boolean nivelOk = true;
        if (nivelesQueDa != null && nivelesQueDa.length > 0) {
            nivelOk = false;
            for (int i = 0; i < nivelesQueDa.length; i++) {
                if (nivelesQueDa[i] != null &&
                    nivelesQueDa[i].trim().equalsIgnoreCase(nivelCurso)) {
                    nivelOk = true;
                    break;
                }
            }
        }

        // Si no se definieron categorías, asumimos que puede todas.
        boolean categoriaOk = true;
        if (categoriasQueDa != null && categoriasQueDa.length > 0) {
            categoriaOk = false;
            for (int i = 0; i < categoriasQueDa.length; i++) {
                if (categoriasQueDa[i] != null &&
                    categoriasQueDa[i].trim().equalsIgnoreCase(catCurso)) {
                    categoriaOk = true;
                    break;
                }
            }
        }

        return nivelOk && categoriaOk;
    }

    /**
     * Verifica una lista de categorías, separadas por comas, y lanza una
     * excepción si alguna de ellas no pertenece al catálogo disponible.
     *
     * @param categorias Cadena con categorías separadas por coma.
     * @throws CategoriaInvalidaException Si alguna categoría no es válida.
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
     * Valida una categoría individual contra el catálogo global
     * de categorías permitidas.
     *
     * @param categoria Categoría a validar.
     * @throws CategoriaInvalidaException Si la categoría no es válida.
     */
    private void verificarCategoria(String categoria)
            throws CategoriaInvalidaException {

        if (!Catalogos.esValorValido(categoria, Catalogos.CATEGORIAS)) {
            throw new CategoriaInvalidaException(
                    "Categoría inválida: " + categoria);
        }
    }

    // ========= Persistible ===========

    /**
     * Convierte el profesor a una línea de texto serializada para almacenamiento.
     *
     * Formato:
     * <pre>
     * id | nombre | titulo | anioTitulo | nivel1,nivel2 | cat1,cat2
     * </pre>
     *
     * @return Cadena con la representación textual del profesor.
     */
    @Override
    public String toLineaTexto() {
        // ID del profesor (número de trabajador)
        int id = this.numeroTrabajador;

        // Nombre y título, cuidando de no usar '|'
        String nombreSeguro = (nombreCompleto == null) ? "" : nombreCompleto.replace("|", "/");
        String tituloSeguro = (titulo == null) ? "" : titulo.replace("|", "/");

        // Niveles que da: separados por coma
        StringBuilder sbNiveles = new StringBuilder();
        if (nivelesQueDa != null) {
            for (int i = 0; i < nivelesQueDa.length; i++) {
                if (nivelesQueDa[i] != null && !nivelesQueDa[i].trim().isEmpty()) {
                    if (sbNiveles.length() > 0) {
                        sbNiveles.append(",");
                    }
                    sbNiveles.append(nivelesQueDa[i].trim());
                }
            }
        }

        // Categorías que da: separadas por coma
        StringBuilder sbCategorias = new StringBuilder();
        if (categoriasQueDa != null) {
            for (int i = 0; i < categoriasQueDa.length; i++) {
                if (categoriasQueDa[i] != null && !categoriasQueDa[i].trim().isEmpty()) {
                    if (sbCategorias.length() > 0) {
                        sbCategorias.append(",");
                    }
                    sbCategorias.append(categoriasQueDa[i].trim());
                }
            }
        }

        // Formato:
        // id | nombre | titulo | anioTitulo | nivel1,nivel2 | cat1,cat2
        return id + "|" +
            nombreSeguro + "|" +
            tituloSeguro + "|" +
            anioTitulo + "|" +
            sbNiveles.toString() + "|" +
            sbCategorias.toString();
    }

    /**
     * Reconstruye el objeto profesor a partir de una línea de texto
     * generada por {@link #toLineaTexto()}.
     *
     * Soporta tanto el formato antiguo de 4 campos como el nuevo de 6.
     *
     * @param linea Línea de texto con los datos del profesor.
     * @throws FormatoInvalidoException Si la estructura o tipos de datos
     *                                  no son válidos.
     */
    @Override
    public void fromLineaTexto(String linea) throws FormatoInvalidoException {
        if (linea == null || linea.trim().isEmpty()) {
            throw new FormatoInvalidoException("Línea vacía para Profesor.");
        }

        String[] partes = linea.split("\\|");

        // Soportar tanto el formato viejo (4 campos) como el nuevo (6 campos)
        if (partes.length < 4) {
            throw new FormatoInvalidoException("Línea inválida para Profesor: " + linea);
        }

        try {
            // 0: id
            this.numeroTrabajador = Integer.parseInt(partes[0].trim());

            // 1: nombre
            this.nombreCompleto = partes[1];

            // 2: título
            this.titulo = partes[2];

            // 3: año del título
            this.anioTitulo = Integer.parseInt(partes[3].trim());

            // Valores por defecto
            this.nivelesQueDa = null;
            this.categoriasQueDa = null;

            // 4: niveles (opcional)
            if (partes.length >= 5 && partes[4] != null && !partes[4].trim().isEmpty()) {
                String[] trozosNiveles = partes[4].split(",");
                this.nivelesQueDa = new String[trozosNiveles.length];
                for (int i = 0; i < trozosNiveles.length; i++) {
                    this.nivelesQueDa[i] = trozosNiveles[i].trim();
                }
            }

            // 5: categorías (opcional)
            if (partes.length >= 6 && partes[5] != null && !partes[5].trim().isEmpty()) {
                String[] trozosCategorias = partes[5].split(",");
                this.categoriasQueDa = new String[trozosCategorias.length];
                for (int i = 0; i < trozosCategorias.length; i++) {
                    this.categoriasQueDa[i] = trozosCategorias[i].trim();
                }
            }

        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("Error numérico al leer Profesor: " + linea);
        }
    }

}
