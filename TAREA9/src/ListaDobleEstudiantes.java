
public class ListaDobleEstudiantes {

    private NodoEstudianteDoble head; // primer nodo
    private NodoEstudianteDoble tail; // último nodo

    /** Constructor: lista vacía, ambos punteros en null. */
    public ListaDobleEstudiantes() {
        this.head = null;
        this.tail = null;
    }

    // ────────────────────────────────────────────────────
    // 1. agregarInicio  [parte de los 8 pts]
    //    Crea un nodo y lo coloca antes del head actual.
    //    O(1).
    // ────────────────────────────────────────────────────
    public void agregarInicio(String carnet, String nombre, double nota) {
        NodoEstudianteDoble nuevo = new NodoEstudianteDoble(carnet, nombre, nota);

        // Caso borde: lista vacía — el nuevo es a la vez head y tail
        if (head == null) {
            head = nuevo;
            tail = nuevo;
            return;
        }

        // El nuevo apunta hacia adelante al antiguo head
        nuevo.siguiente = head;
        // El antiguo head apunta hacia atrás al nuevo nodo
        head.anterior = nuevo;
        // Actualizamos head
        head = nuevo;
        // tail no cambia (el último sigue siendo el mismo)
    }

    // ────────────────────────────────────────────────────
    // 2. agregarFinal  [parte de los 8 pts]
    //    Crea un nodo y lo coloca despues del tail actual.
    //    O(1) — gracias al puntero tail no necesitamos recorrer.
    // ────────────────────────────────────────────────────
    public void agregarFinal(String carnet, String nombre, double nota) {
        NodoEstudianteDoble nuevo = new NodoEstudianteDoble(carnet, nombre, nota);

        // Caso borde: lista vacía
        if (tail == null) {
            head = nuevo;
            tail = nuevo;
            return;
        }

        // El antiguo tail apunta hacia adelante al nuevo nodo
        tail.siguiente = nuevo;
        // El nuevo apunta hacia atrás al antiguo tail
        nuevo.anterior = tail;
        // Actualizamos tail
        tail = nuevo;
        // head no cambia
    }

    // ────────────────────────────────────────────────────
    // 3. eliminarPorCarnet  [10 pts]
    //    Busca el nodo y actualiza TANTO "prev" COMO "next" de sus vecinos.
    //    Este es el método más delicado: hay 4 sub-casos:
    //      a) lista vacía
    //      b) el nodo a eliminar es head (y puede ser el único elemento)
    //      c) el nodo a eliminar es tail
    //      d) el nodo está en el medio
    //    O(n).
    // ────────────────────────────────────────────────────
    public void eliminarPorCarnet(String carnet) {
        // Caso a: lista vacía
        if (head == null) {
            System.out.println("La lista esta vacía.");
            return;
        }

        // Buscamos el nodo a eliminar
        NodoEstudianteDoble actual = head;
        while (actual != null && !actual.carnet.equals(carnet)) {
            actual = actual.siguiente;
        }

        // Si no lo encontramos, salimos
        if (actual == null) {
            System.out.println("Carnet " + carnet + " no encontrado.");
            return;
        }

        // ---- Actualizar el enlace "hacia atrás" del nodo SIGUIENTE ----
        // Si el nodo tiene un sucesor, ese sucesor debe dejar de apuntar
        // hacia el nodo eliminado y pasar a apuntar al predecesor.
        if (actual.siguiente != null) {
            actual.siguiente.anterior = actual.anterior;
        } else {
            // Si no tiene sucesor, "actual" era el tail: actualizamos tail
            tail = actual.anterior;
        }

        // ---- Actualizar el enlace hacia adelante del nodo ANTERIOR ----
        // Si el nodo tiene un predecesor, ese predecesor debe saltar al sucesor.
        if (actual.anterior != null) {
            actual.anterior.siguiente = actual.siguiente;
        } else {
            // Si no tiene predecesor, actual era el head: actualizamos head
            head = actual.siguiente;
        }

        // Los punteros de actual quedan huérfanos; Java los recolecta.
    }

    // ────────────────────────────────────────────────────
    // 4. imprimirAdelante  [5 pts]
    //    Recorre HEAD → TAIL usando el puntero siguiente
    //    O(n).
    // ────────────────────────────────────────────────────
    public void imprimirAdelante() {
        System.out.println("=== Recorrido HEAD  TAIL ===");
        if (head == null) {
            System.out.println("(lista vacia)");
            return;
        }
        NodoEstudianteDoble actual = head;
        int pos = 1;
        while (actual != null) {
            System.out.println(pos + ". [" + actual.carnet + "] "
                    + actual.nombre + " - Nota: " + actual.nota);
            actual = actual.siguiente;
            pos++;
        }
    }

    // ────────────────────────────────────────────────────
    // 5. imprimirAtras  [7 pts]
    //    Recorre TAIL → HEAD usando el puntero anterior
    //    Solo es posible en una lista DOBLE (ventaja sobre la simple).
    //    O(n).
    // ────────────────────────────────────────────────────
    public void imprimirAtras() {
        System.out.println("=== Recorrido TAIL  HEAD ===");
        if (tail == null) {
            System.out.println("(lista vacía)");
            return;
        }
        NodoEstudianteDoble actual = tail;
        int pos = 1;
        while (actual != null) {
            System.out.println(pos + ". [" + actual.carnet + "] "
                    + actual.nombre + " - Nota: " + actual.nota);
            actual = actual.anterior;
            pos++;
        }
    }

    // ────────────────────────────────────────────────────
    // 6. insertarOrdenado  [10 pts]
    //    Inserta manteniendo orden ASCENDENTE por nota
    //
    //    Estrategia:
    //      - Si la lista esta vacía o la nota es la más baja  insertar al inicio
    //      - Si la nota es la más alta  insertar al final (usando tail, O(1)).
    //      - En otro caso recorrer hasta encontrar el punto de inserción.
    //
    //    Complejidad: O(n) en el peor caso (ver analisis.txt para justificacin)
    // ────────────────────────────────────────────────────
    public void insertarOrdenado(String carnet, String nombre, double nota) {
        NodoEstudianteDoble nuevo = new NodoEstudianteDoble(carnet, nombre, nota);

        // Caso 1: lista vacía
        if (head == null) {
            head = nuevo;
            tail = nuevo;
            return;
        }

        // Caso 2: la nota es MENOR O IGUAL que la del head  insertar al inicio
        if (nota <= head.nota) {
            nuevo.siguiente = head;
            head.anterior = nuevo;
            head = nuevo;
            return;
        }

        // Caso 3: la nota es MAYOR que la del tail  insertar al final (O(1))
        if (nota >= tail.nota) {
            tail.siguiente = nuevo;
            nuevo.anterior = tail;
            tail = nuevo;
            return;
        }

        // Caso 4: buscar la posición correcta en el medio
        // Avanzamos mientras el siguiente nodo tenga nota menor que la nueva
        NodoEstudianteDoble actual = head;
        while (actual.siguiente != null && actual.siguiente.nota < nota) {
            actual = actual.siguiente;
        }

        // Insertamos el nuevo nodo entre actual y actual.siguiente
        nuevo.siguiente = actual.siguiente;     // nuevo  sucesor
        nuevo.anterior = actual;                // nuevo  predecesor

        if (actual.siguiente != null) {
            actual.siguiente.anterior = nuevo;  // sucesor nuevo
        }
        actual.siguiente = nuevo;               // predecesor  nuevo
    }
}