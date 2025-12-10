/**
 * Clase Modelo: Alumno
 *
 * Representa a un alumno con número de cuenta, edad y escuela de procedencia.
 */
public class Alumno extends Persona implements Identificable, Persistible {

    private int numeroCuenta;
    private int edad;
    private String escuelaProcedencia;

    private Curso[] cursosInscritos;
    private int contadorCursos;

    private static final int MAX_CURSOS = 6;

    public Alumno() { }

    public Alumno(String nombreCompleto, int numeroCuenta,
                  int edad, String escuelaProcedencia) {
        super(nombreCompleto);
        this.numeroCuenta = numeroCuenta;
        this.edad = edad;
        this.escuelaProcedencia = escuelaProcedencia;
        this.cursosInscritos = new Curso[MAX_CURSOS];
        this.contadorCursos = 0;
    }

    @Override
    public int getId() {
        return numeroCuenta;
    }

    @Override
    public String getIdentificador() {
        return String.valueOf(numeroCuenta);
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public boolean puedeInscribirOtroCurso() {
        return contadorCursos < MAX_CURSOS;
    }

    public void agregarCurso(Curso curso) {
        if (curso == null || !puedeInscribirOtroCurso()) return;
        cursosInscritos[contadorCursos] = curso;
        contadorCursos++;
    }

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

    @Override
    public String toLineaTexto() {
        return numeroCuenta + "|" + nombreCompleto + "|" +
                edad + "|" + escuelaProcedencia;
    }

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
