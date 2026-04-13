
public class ListaEstudiantes {

    private NodoEstudiante head;

    public ListaEstudiantes() {
        this.head = null;
    }

    // ────────────────────────────────────────────────────
    public void agregarInicio(String carnet, String nombre, double nota) {
        NodoEstudiante nuevo = new NodoEstudiante(carnet, nombre, nota);
        nuevo.siguiente = head; // el nuevo apunta a quien era el primero
        head = nuevo;           // head ahora es el nuevo nodo
    }


    //    Recorre hasta el último nodo y enlaza el nuevo al final.
    //    O(n) — debe llegar hasta el final de la cadena.
    // ────────────────────────────────────────────────────
    public void agregarFinal(String carnet, String nombre, double nota) {
        NodoEstudiante nuevo = new NodoEstudiante(carnet, nombre, nota);

        // Caso borde: lista vacía
        if (head == null) {
            head = nuevo;
            return;
        }

        // Avanzamos hasta el ultimo nodo (el que tiene siguiente = null)
        NodoEstudiante actual = head;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }
        actual.siguiente = nuevo; // enlazamos el nuevo al final
    }

    // ────────────────────────────────────────────────────
    // 3. eliminarPorCarnet  [8 pts]
    //    Busca el nodo con ese carnet y lo salta en la cadena
    //    O(n) — en el peor caso recorre toda la lista.
    // ────────────────────────────────────────────────────


    public void eliminarPorCarnet(String carnet) {
        // Caso borde: lista vacía
        if (head == null) {
            System.out.println("La lista está vacía.");
            return;
        }

        // Caso especial: el nodo a eliminar ES el head
        if (head.carnet.equals(carnet)) {
            head = head.siguiente; // head salta al siguiente
            return;
        }

        // Caso general: buscamos el nodo ANTERIOR al que queremos eliminar
        NodoEstudiante actual = head;
        while (actual.siguiente != null) {
            if (actual.siguiente.carnet.equals(carnet)) {
                // "saltamos" el nodo: el anterior apunta al siguiente del eliminado
                actual.siguiente = actual.siguiente.siguiente;
                return;
            }
            actual = actual.siguiente;
        }

        System.out.println("Carnet " + carnet + " no encontrado.");
    }

    // ────────────────────────────────────────────────────
    // 4. buscarPorCarnet  [7 pts]
    //    Recorre la lista y retorna el nodo si lo encuentra, null si no.
    //    O(n).
    // ────────────────────────────────────────────────────
    public NodoEstudiante buscarPorCarnet(String carnet) {
        NodoEstudiante actual = head;
        while (actual != null) {
            if (actual.carnet.equals(carnet)) {
                return actual; // encontrado
            }
            actual = actual.siguiente;
        }
        return null; // no existe
    }

    // ────────────────────────────────────────────────────
    // 5. imprimirLista  [5 pts]
    //    Imprime todos los estudiantes desde head hasta null.
    //    O(n).
    // ────────────────────────────────────────────────────
    public void imprimirLista() {
        if (head == null) {
            System.out.println("(lista vacía)");
            return;
        }
        NodoEstudiante actual = head;
        int posicion = 1;
        while (actual != null) {
            System.out.println(posicion + ". Carnet: " + actual.carnet
                    + " | Nombre: " + actual.nombre
                    + " | Nota: " + actual.nota);
            actual = actual.siguiente;
            posicion++;
        }
    }

    // ────────────────────────────────────────────────────
    // 6. obtenerPromedio  [5 pts]
    //    Suma todas las notas y divide entre el total de nodo
    //    O(n).
    // ────────────────────────────────────────────────────
    public double obtenerPromedio() {
        if (head == null) return 0.0;

        double suma = 0.0;
        int cantidad = 0;
        NodoEstudiante actual = head;

        while (actual != null) {
            suma += actual.nota;
            cantidad++;
            actual = actual.siguiente;
        }
        return suma / cantidad;
    }

    // ────────────────────────────────────────────────────
    // 7. obtenerMejorNota  [5 pts]
    //    Recorre la lista manteniendo al campeon actual
    //    O(n).
    // ────────────────────────────────────────────────────
    public NodoEstudiante obtenerMejorNota() {
        if (head == null) return null;

        NodoEstudiante mejor = head; // asumimos que el primero es el mejor
        NodoEstudiante actual = head.siguiente;

        while (actual != null) {
            if (actual.nota > mejor.nota) {
                mejor = actual; // encontramos un nuevo campeón
            }
            actual = actual.siguiente;
        }
        return mejor;
    }
}