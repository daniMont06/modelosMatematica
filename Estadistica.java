import java.util.ArrayList;

public class Estadistica extends Matematica {
    // Atributos extra de la hija
    private String tipo;                 // "PROMEDIO" o "VARIANZA"
    private ArrayList<Double> datos;     // lista de datos numéricos

    
    public Estadistica(String nombre, String tipo) {
        super(nombre);
        this.tipo = (tipo == null) ? "PROMEDIO" : tipo.toUpperCase();
        this.datos = new ArrayList<Double>();
    }

    public Estadistica(String nombre, String tipo, ArrayList<Double> datos) {
        super(nombre);
        this.tipo = (tipo == null) ? "PROMEDIO" : tipo.toUpperCase();
        this.datos = new ArrayList<Double>();
        setDatos(datos); // copia simple
    }

    //  Gestión de datos 
    public void agregarDato(Double valor) {
        if (valor == null) return;
        datos.add(valor);
    }

    public void agregarVariosDatos(ArrayList<Double> valores) {
        if (valores == null) return;
        for (int i = 0; i < valores.size(); i++) {
            Double v = valores.get(i);
            if (v != null) datos.add(v);
        }
    }

    public void removerDatosPorIndice(int indice) {
        if (indice >= 0 && indice < datos.size()) {
            datos.remove(indice);
        }
    }

    public void limpiarDatos() {
        datos.clear();
    }

    public int cantidadDatos() {
        return datos.size();
    }

    public void cambiarTipo(String nuevoTipo) {
        if (nuevoTipo == null) return;
        this.tipo = nuevoTipo.toUpperCase(); // "PROMEDIO" o "VARIANZA"
    }

    public void setDatos(ArrayList<Double> nuevos) {
        datos.clear();
        if (nuevos == null) return;
        for (int i = 0; i < nuevos.size(); i++) {
            Double v = nuevos.get(i);
            if (v != null) datos.add(v);
        }
    }

    
    public String getTipo() { return tipo; }
    public ArrayList<Double> getDatos() { 
        ArrayList<Double> copia = new ArrayList<Double>();
        for (int i = 0; i < datos.size(); i++) copia.add(datos.get(i));
        return copia;
    }

    //Cálculo que se modifica
    @Override
    public double calcular() {
        if (datos.isEmpty()) return 0.0;

        if ("VARIANZA".equalsIgnoreCase(tipo)) {
            // calculo de la varianza
            double media = promedio();
            double suma = 0.0;
            for (int i = 0; i < datos.size(); i++) {
                double d = datos.get(i);
                double dif = d - media;
                suma += dif * dif;
            }
            return suma / datos.size();
        }

        // por defecto: PROMEDIO
        return promedio();
    }

    //Helpers
    private double promedio() {
        if (datos.isEmpty()) return 0.0;
        double s = 0.0;
        for (int i = 0; i < datos.size(); i++) {
            s += datos.get(i);
        }
        return s / datos.size();
    }

    //  Resumen 
    @Override
    public String resumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estadistica - ").append(nombre)
        .append(" | tipo: ").append(tipo)
        .append(" | n: ").append(datos.size());
        return sb.toString();
    }
}
