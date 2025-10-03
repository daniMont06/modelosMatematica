import java.util.ArrayList;

public abstract class Matematica { //La hice abstacta para que los hijos cambien obligatoriamente todo jajaja
    
    protected String nombre;
    protected ArrayList<Double> parametros;

    
    public Matematica(String nombre) {
        this.nombre = (nombre == null) ? "" : nombre;
        this.parametros = new ArrayList<Double>();
    }

    // Gestión de parámetros
    public void agregarParametro(Double valor) {
        if (valor == null) return;
        parametros.add(valor);
    }

    public void actualizarParametro(int indice, Double valor) {
        if (valor == null) return;
        if (indice >= 0 && indice < parametros.size()) {
            parametros.set(indice, valor);
        }
        // si el índice no es válido, simplemente no hace nada 
    }

    public Double obtenerParametro(int indice) {
        if (indice >= 0 && indice < parametros.size()) {
            return parametros.get(indice);
        }
        return null; // null si no existe
    }

    
    public Double getParametro(int indice) {
        return obtenerParametro(indice);
    }

    public int cantidadParametros() {
        return parametros.size();
    }

    public void limpiarParametros() {
        parametros.clear();
    }

    public void setParametros(ArrayList<Double> params) {
        parametros.clear();
        if (params == null) return;
        // copiar de forma simple (sin streams)
        for (int i = 0; i < params.size(); i++) {
            Double v = params.get(i);
            if (v != null) parametros.add(v);
        }
    }

    // El nombre
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = (nombre == null) ? "" : nombre;
    }

    // Esta cosa es la que se va a modificar todas veces, va a hacer algo diferente cada vez
    public abstract double calcular();

    //  Resumen simple de todo
    public String resumen() {
        String tipo = this.getClass().getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append(tipo).append(" - ").append(nombre).append(" | params: [");
        for (int i = 0; i < parametros.size(); i++) {
            sb.append(parametros.get(i));
            if (i < parametros.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
