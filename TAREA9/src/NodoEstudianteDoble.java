
public class NodoEstudianteDoble {

    String carnet;
    String nombre;
    double nota;
    NodoEstudianteDoble anterior;  // apunta al nodo que esta ANTES
    NodoEstudianteDoble siguiente; // apunta al nodo que está DESPUÉS

    /** Constructor: crea un nodo con los datos; ambos punteros quedan en null. */
    public NodoEstudianteDoble(String carnet, String nombre, double nota) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.nota = nota;
        this.anterior = null;
        this.siguiente = null;
    }
}