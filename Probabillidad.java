public class Probabilidad extends Matematica {
    // Atributos extra
    private int favorables;
    private int posibles;

    
    public Probabilidad(String nombre) {
        super(nombre);
        this.favorables = 0;
        this.posibles = 0;
        
    }

    public Probabilidad(String nombre, int favorables, int posibles) {
        super(nombre);
        DefinirCasos(favorables, posibles);
    }

    // Se mira lo de los casos en este método
    public void DefinirCasos(int favorables, int posibles) {
        // Validación 
        if (posibles < 0) posibles = 0;
        if (favorables < 0) favorables = 0;
        if (favorables > posibles) favorables = posibles;

        this.favorables = favorables;
        this.posibles = posibles;
    }

    public int obtenerFavorables() {
        return favorables;
    }

    public int obtenerPosibles() {
        return posibles;
    }

    public boolean esValido() {
        return posibles > 0 && favorables >= 0 && favorables <= posibles;
    }
Otra vez el cálculo polimorfítico
    @Override
    public double calcular() {
        if (!esValido()) return 0.0; 
        return (double) favorables / (double) posibles;
    }

    
    @Override
    public String resumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Probabilidad - ").append(nombre)
        .append(" | casos: ").append(favorables).append("/").append(posibles);
        return sb.toString();
    }
}
