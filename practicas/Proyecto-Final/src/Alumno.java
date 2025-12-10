/**
 * Clase Modelo: Alumno
 *
 * Representa a un alumno con número de cuenta, edad y escuela de procedencia.
 * Además administra los cursos en los que el alumno está inscrito y permite
 * operaciones de registro, eliminación y validación del límite permitido.
 */
public class Alumno extends Persona implements Identificable, Persistible {

    private int numeroCuenta;
    private int edad;
    private String escuelaProcedencia;

    private Curso[] cursosInscritos;
    private int contadorCursos;

    private static final int MAX_CURSOS = 6;

    /**
     * Constructor vacío para permitir la inicialización progresiva del objeto.
     */
    public Alumno() { }

    /**
     * Constructor principal.
     *
     * @param nombreCompleto Nombre completo del alumno.
     * @param numeroCuenta Número de cuenta asignado por la institución.
     * @param edad Edad del alumno.
     * @param escuelaProcedencia Escuela de la que proviene.
     */
    public Alumno(String nombreCompleto, int numeroCuenta,
                  int edad, String escuelaProcedencia) {
        super(nombreCompleto);
        this.numeroCuenta = numeroCuenta;
        this.edad = edad;
        this.escuelaProcedencia = escuelaProcedencia;
        this.cursosInscritos = new Curso[MAX_CURSOS];
        this.contadorCursos = 0;
    }

    /**
     * Obtiene el identificador numérico del alumno.
     *
     * @return Número de cuenta del alumno.
     */
    @Override
    public int getId() {
        return numeroCuenta;
    }

    /**
     * Obtiene el identificador del alumno como cadena.
     *
     * @return Número de cuenta como texto.
     */
    @Override
    public String getIdentificador() {
        return String.valueOf(numeroCuenta);
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public int getEdad() {
        return edad;
    }

    public String getEscuelaProcedencia() {
        return escuelaProcedencia;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setEscuelaProcedencia(String escuelaProcedencia) {
        this.escuelaProcedencia = escuelaProcedencia;
    }

    /**
     * Verifica si el alumno aún puede inscribir otro curso.
     *
     * @return true si el alumno no ha alcanzado el máximo permitido.
     */
    public boolean puedeInscribirOtroCurso() {
        return contadorCursos < MAX_CURSOS;
    }

    /**
     * Agrega un curso al listado del alumno siempre que exista espacio disponible.
     *
     * @param curso Curso a inscribir.
     */
    public void agregarCurso(Curso curso) {
        if (curso == null || !puedeInscribirOtroCurso()) return;
        cursosInscritos[contadorCursos] = curso;
        contadorCursos++;
    }

    /**
     * Elimina un curso previamente inscrito por el alumno.
     *
     * @param curso Curso a eliminar.
     */
    public void quitarCurso(Curso curso) {
        if (curso == null) return;
        for (int i = 0; i < contadorCursos; i++) {
            if (cursosInscritos[i] != null &&
                cursosInscritos[i].getId() == curso.getId()) {
                for (int j = i; j < contadorCursos - 1; j++) {
                    cursosInscritos[j] = cursosInscritos[j + 1];
                }
                cursosInscritos[contadorCursos - 1] = null;
                contadorCursos--;
                break;
            }
        }
    }

    // ========= Persistible ===========

    /**
     * Convierte los datos del alumno a una línea de texto para persistencia.
     *
     * @return Cadena con los datos del alumno separados por "|".
     */
    @Override
    public String toLineaTexto() {
        return numeroCuenta + "|" + nombreCompleto + "|" +
                edad + "|" + escuelaProcedencia;
    }

    /**
     * Reconstruye el objeto Alumno a partir de una línea de texto.
     *
     * @param linea Texto con los datos del alumno.
     * @throws FormatoInvalidoException Si los datos no cumplen el formato esperado.
     */
    @Override
    public void fromLineaTexto(String linea) throws FormatoInvalidoException {
        String[] partes = linea.split("\\|");
        if (partes.length < 4) {
            throw new FormatoInvalidoException("Línea inválida para Alumno: " + linea);
        }
        try {
            this.numeroCuenta = Integer.parseInt(partes[0]);
            this.nombreCompleto = partes[1];
            this.edad = Integer.parseInt(partes[2]);
            this.escuelaProcedencia = partes[3];
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("Error al parsear Alumno: " + linea);
        }
        this.cursosInscritos = new Curso[MAX_CURSOS];
        this.contadorCursos = 0;
    }
}
