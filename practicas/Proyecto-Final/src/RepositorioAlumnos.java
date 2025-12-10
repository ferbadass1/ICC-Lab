/**
 * Clase Repositorio: RepositorioAlumnos
 *
 * Implementa un repositorio concreto para objetos Alumno.
 */
public class RepositorioAlumnos extends RepositorioBase<Alumno> {

    /**
     * Constructor del repositorio de alumnos.
     *
     * @param capacidad Cantidad máxima de alumnos que se pueden almacenar.
     */
    public RepositorioAlumnos(int capacidad) {
        super(new Alumno[capacidad]);   // ⬅️ AQUÍ ESTABA EL PROBLEMA
    }

    @Override
    protected Alumno crearDesdeLinea(String linea) throws FormatoInvalidoException {
        Alumno a = new Alumno();
        a.fromLineaTexto(linea);
        return a;
    }
}
