import java.time.LocalDate;
/**
 * Clase Modelo: Fecha
 *
 * Implementa una fecha simple (día, mes, año) con comparaciones básicas.
 */
public class Fecha {

    private int dia;
    private int mes;
    private int anio;

    public Fecha(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    /**
     * Método Calculador estático: hoy
     *
     * Construye una fecha con la fecha actual del sistema.
     *
     * @return Objeto Fecha correspondiente a la fecha de hoy.
     */
    public static Fecha hoy() {
        LocalDate ahora = LocalDate.now();
        return new Fecha(ahora.getDayOfMonth(), ahora.getMonthValue(), ahora.getYear());
    }
    
    public boolean esAnteriorA(Fecha otra) {
        if (this.anio != otra.anio) return this.anio < otra.anio;
        if (this.mes != otra.mes) return this.mes < otra.mes;
        return this.dia < otra.dia;
    }

    public boolean esPosteriorA(Fecha otra) {
        if (this.anio != otra.anio) return this.anio > otra.anio;
        if (this.mes != otra.mes) return this.mes > otra.mes;
        return this.dia > otra.dia;
    }

    public boolean esEntre(Fecha inicio, Fecha fin) {
        return !this.esAnteriorA(inicio) && !this.esPosteriorA(fin);
    }

    @Override
    public String toString() {
        return dia + "-" + mes + "-" + anio;
    }

    public static Fecha fromString(String s) throws FormatoInvalidoException {
        String[] partes = s.split("-");
        if (partes.length != 3) {
            throw new FormatoInvalidoException("Fecha inválida: " + s);
        }
        try {
            int d = Integer.parseInt(partes[0]);
            int m = Integer.parseInt(partes[1]);
            int a = Integer.parseInt(partes[2]);
            return new Fecha(d, m, a);
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("Fecha inválida: " + s);
        }
    }
}
