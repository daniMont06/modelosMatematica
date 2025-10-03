import java.util.ArrayList;

public class Controlador {
    //  Atributos 
    private ArrayList<Estadistica> modelosEstadistica;
    private ArrayList<Probabilidad> modelosProbabilidad;
    private ArrayList<Geometria> modelosGeometria;

    private ArrayList<String> historialResultados;

    private String subMenuActual;  // "PRINCIPAL", "REGISTRAR", "EJECUTAR", "PROB", "EST", "GEO"
    private boolean enEjecucion;

    
    public Controlador() {
        this.modelosEstadistica = new ArrayList<Estadistica>();
        this.modelosProbabilidad = new ArrayList<Probabilidad>();
        this.modelosGeometria = new ArrayList<Geometria>();
        this.historialResultados = new ArrayList<String>();
        this.subMenuActual = "PRINCIPAL";
        this.enEjecucion = true;
    }

    //  Menú 
    public String getMenuActual() {
        if ("PRINCIPAL".equals(subMenuActual)) {
            return  "1) Registrar modelo\n"
                + "2) Ejecutar cálculos\n"
                + "3) Listar modelos\n"
                + "4) Ver historial de resultados\n"
                + "0) Salir\n";
        }
        if ("REGISTRAR".equals(subMenuActual)) {
            return  "1) Registrar PROBABILIDAD\n"
                + "2) Registrar ESTADÍSTICA\n"
                + "3) Registrar GEOMETRÍA\n"
                + "9) Volver\n";
        }
        if ("EJECUTAR".equals(subMenuActual)) {
            return  "1) PROBABILIDAD\n"
                + "2) ESTADÍSTICA\n"
                + "3) GEOMETRÍA\n"
                + "4) Ejecutar TODOS\n"
                + "9) Volver\n";
        }
        if ("PROB".equals(subMenuActual) || "EST".equals(subMenuActual) || "GEO".equals(subMenuActual)) {
            return  "1) Listar modelos del tipo\n"
                + "2) Ejecutar por NOMBRE\n"
                + "9) Volver\n";
        }
        return ""; // por si acaso
    }

    public void procesarOpcion(int opcion) {
        // Navegación simple por menús; las acciones reales (registrar/ejecutar)
        // las hace el main llamando a los métodos públicos de abajo.
        if ("PRINCIPAL".equals(subMenuActual)) {
            if (opcion == 1) subMenuActual = "REGISTRAR";
            else if (opcion == 2) subMenuActual = "EJECUTAR";
            else if (opcion == 3) subMenuActual = "LISTAR_GLOBAL"; // el main puede leer esto y llamar listar
            else if (opcion == 4) subMenuActual = "HISTORIAL";     // idem
            else if (opcion == 0) enEjecucion = false;
        } else if ("REGISTRAR".equals(subMenuActual)) {
            if (opcion == 1) subMenuActual = "PROB";
            else if (opcion == 2) subMenuActual = "EST";
            else if (opcion == 3) subMenuActual = "GEO";
            else if (opcion == 9) subMenuActual = "PRINCIPAL";
        } else if ("EJECUTAR".equals(subMenuActual)) {
            if (opcion == 1) subMenuActual = "PROB";
            else if (opcion == 2) subMenuActual = "EST";
            else if (opcion == 3) subMenuActual = "GEO";
            else if (opcion == 4) subMenuActual = "EJECUTAR_TODOS"; // el main puede reaccionar llamando ejecutarTodos()
            else if (opcion == 9) subMenuActual = "PRINCIPAL";
        } else if ("PROB".equals(subMenuActual) || "EST".equals(subMenuActual) || "GEO".equals(subMenuActual)) {
            // 1 = listar del tipo, 2 = ejecutar por nombre, 9 = volver a EJECUTAR
            if (opcion == 1) {
                // el main puede detectar subMenuActual y llamar listarModelosPorTipo(...)
                // no cambiamos de submenú para que el main muestre aquí mismo la lista si quiere
            } else if (opcion == 2) {
                // el main pedirá el nombre y llamará ejecutarPorNombre(subMenuActual, nombre)
            } else if (opcion == 9) {
                subMenuActual = "EJECUTAR";
            }
        } else if ("LISTAR_GLOBAL".equals(subMenuActual) || "HISTORIAL".equals(subMenuActual) || "EJECUTAR_TODOS".equals(subMenuActual)) {
            // Estas son "pantallas de acción" coordinadas por el main; al terminar vuelve al principal
            subMenuActual = "PRINCIPAL";
        }
    }

    public boolean isEnEjecucion() {
        return enEjecucion;
    }

    //  Registro
    public boolean registarProbabilidades(String nombre, int favorables, int posibles) {
        if (nombre == null) return false;
        if (existeNombreGlobal(nombre)) return false;
        Probabilidad p = new Probabilidad(nombre, favorables, posibles);
        modelosProbabilidad.add(p);
        return true;
    }

    public boolean registarEstaditica(String nombre, String tipo, ArrayList<Double> datos) {
        if (nombre == null) return false;
        if (existeNombreGlobal(nombre)) return false;
        Estadistica e;
        if (datos == null) e = new Estadistica(nombre, tipo);
        else e = new Estadistica(nombre, tipo, datos);
        modelosEstadistica.add(e);
        return true;
    }

    public boolean registrarGeometria(String nombre, String figura, ArrayList<Double> params) {
        if (nombre == null) return false;
        if (existeNombreGlobal(nombre)) return false;
        Geometria g;
        if (params == null) g = new Geometria(nombre, figura);
        else g = new Geometria(nombre, figura, params);
        modelosGeometria.add(g);
        return true;
    }

    //Listados y consultas
    public ArrayList<String> listaModelosGlobal() {
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < modelosProbabilidad.size(); i++) {
            out.add("PROB: " + modelosProbabilidad.get(i).getNombre());
        }
        for (int i = 0; i < modelosEstadistica.size(); i++) {
            out.add("EST: " + modelosEstadistica.get(i).getNombre());
        }
        for (int i = 0; i < modelosGeometria.size(); i++) {
            out.add("GEO: " + modelosGeometria.get(i).getNombre());
        }
        return out;
    }

    public ArrayList<String> listaModelosPorTipo(String tipo) {
        ArrayList<String> out = new ArrayList<String>();
        if (tipo == null) return out;

        if ("PROB".equalsIgnoreCase(tipo)) {
            for (int i = 0; i < modelosProbabilidad.size(); i++) {
                out.add(modelosProbabilidad.get(i).getNombre());
            }
        } else if ("EST".equalsIgnoreCase(tipo)) {
            for (int i = 0; i < modelosEstadistica.size(); i++) {
                out.add(modelosEstadistica.get(i).getNombre());
            }
        } else if ("GEO".equalsIgnoreCase(tipo)) {
            for (int i = 0; i < modelosGeometria.size(); i++) {
                out.add(modelosGeometria.get(i).getNombre());
            }
        }
        return out;
    }

    public ArrayList<String> getHistorial() {
        // devolver copia simple
        ArrayList<String> copia = new ArrayList<String>();
        for (int i = 0; i < historialResultados.size(); i++) {
            copia.add(historialResultados.get(i));
        }
        return copia;
    }

    //  Ejecución 
    public double ejecutarPorNombre(String tipo, String nombre) {
        if (tipo == null || nombre == null) return Double.NaN;

        if ("PROB".equalsIgnoreCase(tipo)) {
            Probabilidad p = buscarProbPorNombre(nombre);
            if (p == null) return Double.NaN;
            double v = p.calcular();
            guardarResultado(p.getNombre(), v);
            return v;
        }

        if ("EST".equalsIgnoreCase(tipo)) {
            Estadistica e = buscarEstPorNombre(nombre);
            if (e == null) return Double.NaN;
            double v = e.calcular();
            guardarResultado(e.getNombre(), v);
            return v;
        }

        if ("GEO".equalsIgnoreCase(tipo)) {
            Geometria g = buscarGeoPorNombre(nombre);
            if (g == null) return Double.NaN;
            double v = g.calcular();
            guardarResultado(g.getNombre(), v);
            return v;
        }

        return Double.NaN;
    }

    public ArrayList<Double> ejecutarTodos() {
        ArrayList<Double> valores = new ArrayList<Double>();

        for (int i = 0; i < modelosProbabilidad.size(); i++) {
            Probabilidad p = modelosProbabilidad.get(i);
            double v = p.calcular();
            guardarResultado(p.getNombre(), v);
            valores.add(v);
        }
        for (int i = 0; i < modelosEstadistica.size(); i++) {
            Estadistica e = modelosEstadistica.get(i);
            double v = e.calcular();
            guardarResultado(e.getNombre(), v);
            valores.add(v);
        }
        for (int i = 0; i < modelosGeometria.size(); i++) {
            Geometria g = modelosGeometria.get(i);
            double v = g.calcular();
            guardarResultado(g.getNombre(), v);
            valores.add(v);
        }

        return valores;
    }

    // gestión de todo jajaja
    public boolean eliminarPorNombre(String tipo, String nombre) {
        if (tipo == null || nombre == null) return false;

        if ("PROB".equalsIgnoreCase(tipo)) {
            for (int i = 0; i < modelosProbabilidad.size(); i++) {
                if (nombre.equals(modelosProbabilidad.get(i).getNombre())) {
                    modelosProbabilidad.remove(i);
                    return true;
                }
            }
            return false;
        }

        if ("EST".equalsIgnoreCase(tipo)) {
            for (int i = 0; i < modelosEstadistica.size(); i++) {
                if (nombre.equals(modelosEstadistica.get(i).getNombre())) {
                    modelosEstadistica.remove(i);
                    return true;
                }
            }
            return false;
        }

        if ("GEO".equalsIgnoreCase(tipo)) {
            for (int i = 0; i < modelosGeometria.size(); i++) {
                if (nombre.equals(modelosGeometria.get(i).getNombre())) {
                    modelosGeometria.remove(i);
                    return true;
                }
            }
            return false;
        }

        return false;
    }

    public void limpiarTodo() {
        modelosProbabilidad.clear();
        modelosEstadistica.clear();
        modelosGeometria.clear();
        historialResultados.clear();
        subMenuActual = "PRINCIPAL";
        enEjecucion = true;
    }

    // helpers, estos son privados
    private void guardarResultado(String nombre, double valor) {
        String s = nombre + " = " + valor;
        historialResultados.add(s);
    }

    private boolean existeNombreGlobal(String nombre) {
        // busca por nombre exacto en las tres listas
        for (int i = 0; i < modelosProbabilidad.size(); i++) {
            if (nombre.equals(modelosProbabilidad.get(i).getNombre())) return true;
        }
        for (int i = 0; i < modelosEstadistica.size(); i++) {
            if (nombre.equals(modelosEstadistica.get(i).getNombre())) return true;
        }
        for (int i = 0; i < modelosGeometria.size(); i++) {
            if (nombre.equals(modelosGeometria.get(i).getNombre())) return true;
        }
        return false;
    }

    private Probabilidad buscarProbPorNombre(String nombre) {
        for (int i = 0; i < modelosProbabilidad.size(); i++) {
            Probabilidad p = modelosProbabilidad.get(i);
            if (nombre.equals(p.getNombre())) return p;
        }
        return null;
    }

    private Estadistica buscarEstPorNombre(String nombre) {
        for (int i = 0; i < modelosEstadistica.size(); i++) {
            Estadistica e = modelosEstadistica.get(i);
            if (nombre.equals(e.getNombre())) return e;
        }
        return null;
    }

    private Geometria buscarGeoPorNombre(String nombre) {
        for (int i = 0; i < modelosGeometria.size(); i++) {
            Geometria g = modelosGeometria.get(i);
            if (nombre.equals(g.getNombre())) return g;
        }
        return null;
    }
}
