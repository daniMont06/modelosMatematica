import java.util.ArrayList;

public class Geometria extends Matematica {
    // atributos extra
    private String figura; // "CIRCULO", "RECTANGULO", "TRIANGULO", "CUADRADO"

    // ===== Constructores =====
    public Geometria(String nombre, String figura) {
        super(nombre);
        this.figura = (figura == null) ? "CIRCULO" : figura.toUpperCase();
        
    }

    public Geometria(String nombre, String figura, ArrayList<Double> params) {
        super(nombre);
        this.figura = (figura == null) ? "CIRCULO" : figura.toUpperCase();
        definirParametros(params); // carga en orden según la figura
    }

    //  Cambiar figura 
    public void CambiarFigura(String figura) {
        if (figura == null) return;
        this.figura = figura.toUpperCase();
        // Al cambiar de figura, limpiamos parámetros para evitar confusiones
        this.parametros.clear();
    }

    //  Definir parámetros por figura
    // CIRCULO:    [ radio ]
    // RECTANGULO: [ base, altura ]
    // TRIANGULO:  [ base, altura ]
    // CUADRADO:   [ lado ]
    public void definirParametros(ArrayList<Double> params) {
        this.parametros.clear();
        if (params == null) return;
        // Copia simple (sin streams), ignorando nulls
        for (int i = 0; i < params.size(); i++) {
            Double v = params.get(i);
            if (v != null) this.parametros.add(v);
        }
    }

    //  Cálculo del área  
    @Override
    public double calcular() {
        if ("CIRCULO".equalsIgnoreCase(figura)) {
            if (parametros.size() < 1) return 0.0;
            double r = parametros.get(0);
            if (r < 0) return 0.0;
            return Math.PI * r * r;
        }

        if ("RECTANGULO".equalsIgnoreCase(figura)) {
            if (parametros.size() < 2) return 0.0;
            double base = parametros.get(0);
            double altura = parametros.get(1);
            if (base < 0 || altura < 0) return 0.0;
            return base * altura;
        }

        if ("TRIANGULO".equalsIgnoreCase(figura)) {
            if (parametros.size() < 2) return 0.0;
            double base = parametros.get(0);
            double altura = parametros.get(1);
            if (base < 0 || altura < 0) return 0.0;
            return (base * altura) / 2.0;
        }

        if ("CUADRADO".equalsIgnoreCase(figura)) {
            if (parametros.size() < 1) return 0.0;
            double lado = parametros.get(0);
            if (lado < 0) return 0.0;
            return lado * lado;
        }

        // Figura no reconocida
        return 0.0;
    }

    // Resumen
    @Override
    public String resumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Geometria - ").append(nombre)
        .append(" | figura: ").append(figura)
        .append(" | params: [");
        for (int i = 0; i < parametros.size(); i++) {
            sb.append(parametros.get(i));
            if (i < parametros.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public String getFigura() { return figura; }
}
