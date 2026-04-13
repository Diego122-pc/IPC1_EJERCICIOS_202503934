
public class Main {

    public static void main(String[] args) {


        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   PARTE 1 – Lista Simplemente Enlazada  ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        ListaEstudiantes lista = new ListaEstudiantes();


        lista.agregarInicio("202300003", "Carlos",  72.5);
        lista.agregarInicio("202300002", "Beatriz", 88.0);
        lista.agregarInicio("202300001", "Ana",     95.0);


        lista.agregarFinal("202300004", "Diego",   60.0);
        lista.agregarFinal("202300005", "Elena",   78.3);

        System.out.println("--- Lista completa ---");
        lista.imprimirLista();

        System.out.printf("%nPromedio de notas: %.2f%n", lista.obtenerPromedio());

        NodoEstudiante mejor = lista.obtenerMejorNota();
        System.out.println("Mejor nota: " + mejor.nombre + " (" + mejor.nota + ")\n");


        NodoEstudiante encontrado = lista.buscarPorCarnet("202300003");
        System.out.println("Buscar 202300003 → " + (encontrado != null
                ? encontrado.nombre : "no encontrado"));

        NodoEstudiante noExiste = lista.buscarPorCarnet("999999999");
        System.out.println("Buscar 999999999 → " + (noExiste != null
                ? noExiste.nombre : "no encontrado") + "\n");

        System.out.println("Eliminando 202300001 (head)...");
        lista.eliminarPorCarnet("202300001");
        lista.imprimirLista();

        System.out.println("\nEliminando 202300005 (tail)...");
        lista.eliminarPorCarnet("202300005");
        lista.imprimirLista();

        System.out.println("\nEliminando 202300003 (medio)...");
        lista.eliminarPorCarnet("202300003");
        lista.imprimirLista();

       
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║  PARTE 2 – Lista Doblemente Enlazada    ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        ListaDobleEstudiantes doble = new ListaDobleEstudiantes();

        doble.agregarInicio("202300010", "Fernanda", 91.0);
        doble.agregarInicio("202300011", "Gustavo",  84.5);
        doble.agregarFinal ("202300012", "Helena",   70.0);
        doble.agregarFinal ("202300013", "Iván",     55.5);

        doble.imprimirAdelante();
        System.out.println();
        doble.imprimirAtras();


        System.out.println("\n--- Demostración de insertarOrdenado (lista nueva) ---");
        ListaDobleEstudiantes listaOrdenada = new ListaDobleEstudiantes();
        listaOrdenada.insertarOrdenado("202300014", "Jorge", 77.0);
        listaOrdenada.insertarOrdenado("202300015", "Karla", 95.0);
        listaOrdenada.insertarOrdenado("202300016", "Leo",   40.0);
        listaOrdenada.insertarOrdenado("202300017", "Marta", 88.5);
        listaOrdenada.insertarOrdenado("202300018", "Noel",  60.0);

        System.out.println("Lista ordenada ascendente por nota:");
        listaOrdenada.imprimirAdelante();

        System.out.println("\nEliminando head (Gustavo, 84.5)...");
        doble.eliminarPorCarnet("202300011");
        doble.imprimirAdelante();

        System.out.println("\nEliminando tail (Karla, 95.0)...");
        doble.eliminarPorCarnet("202300015");
        doble.imprimirAdelante();

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║  PARTE 3 – Secuencia del diagrama Heap  ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        ListaEstudiantes listaDiagrama = new ListaEstudiantes();
        listaDiagrama.agregarInicio("202300001", "Ana",   85.0);
        listaDiagrama.agregarInicio("202300002", "Luis",  90.0);
        listaDiagrama.agregarFinal ("202300003", "María", 78.0);
        listaDiagrama.eliminarPorCarnet("202300001");

        System.out.println("Estado final de la lista (debe ser Luis → María):");
        listaDiagrama.imprimirLista();
    }
}