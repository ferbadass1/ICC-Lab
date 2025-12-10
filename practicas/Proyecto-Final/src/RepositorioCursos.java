/**
 * Clase Repositorio: RepositorioCursos
 *
 * Implementa un repositorio concreto para objetos Curso.
 */
public class RepositorioCursos extends RepositorioBase<Curso> {

    /**
     * Constructor del repositorio de cursos.
     *
     * @param capacidad Cantidad máxima de cursos.
     */
    public RepositorioCursos(int capacidad) {
        super(new Curso[capacidad]);    // ⬅️ IMPORTANTE
    }

    @Override
    protected Curso crearDesdeLinea(String linea) throws FormatoInvalidoException {
        Curso c = new Curso();
        c.fromLineaTexto(linea);
        return c;
    }
}
