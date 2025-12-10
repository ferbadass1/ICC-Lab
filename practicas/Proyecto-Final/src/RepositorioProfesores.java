/**
 * Clase Repositorio: RepositorioProfesores
 *
 * Implementa un repositorio concreto para objetos Profesor.
 */
public class RepositorioProfesores extends RepositorioBase<Profesor> {

    /**
     * Constructor del repositorio de profesores.
     *
     * @param capacidad Cantidad máxima de profesores.
     */
    public RepositorioProfesores(int capacidad) {
        super(new Profesor[capacidad]);   // ⬅️ IMPORTANTE
    }

    @Override
    protected Profesor crearDesdeLinea(String linea) throws FormatoInvalidoException {
        Profesor p = new Profesor();
        p.fromLineaTexto(linea);
        return p;
    }
}
