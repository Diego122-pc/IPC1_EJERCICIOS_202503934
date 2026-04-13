/**
 * Nodo para la Lista Simplemente Enlazada.
 * Almacena los datos del estudiante y una referencia al siguiente nodo.
 */
public class NodoEstudiante {

    String carnet;
    String nombre;
    double nota;
    NodoEstudiante siguiente; // referencia al siguiente nodo en la cadena


    public NodoEstudiante(String carnet, String nombre, double nota) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.nota = nota;
        this.siguiente = null;
    }
}