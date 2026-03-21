import java.util.Scanner;

public class Main {

    private static Curso[] cursos = new Curso[50];
    private static int cantidadCursos = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Sistema de Gestion Usac");
        System.out.println("");

        int opcion = -1;
        while (opcion != 4) {
            mostrarMenu();
            opcion = leerEntero("Ingrese su opcion: ");
            switch (opcion) {
                case 1 -> crearCurso();
                case 2 -> agregarTarea();
                case 3 -> mostrarTodo();
                case 4 -> System.out.println("\n¡Hasta luego!\n");
                default -> System.out.println("\n Opcion invalida. Intente de nuevo.\n");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("1. Crear curso             ");
        System.out.println("2. Agregar tarea a curso   ");
        System.out.println("3. Mostrar cursos y tareas ");
        System.out.println("4. Salir                   ");
    }

    private static void crearCurso() {
        System.out.println("\n--- Crear nuevo curso ---");
        System.out.print("Nombre del curso    : ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Codigo del curso    : ");
        String codigo = scanner.nextLine().trim();

        System.out.print("Tutor responsable   : ");
        String tutor = scanner.nextLine().trim();

        if (nombre.isEmpty() || codigo.isEmpty() || tutor.isEmpty()) {
            System.out.println("\nTodos los campos son obligatorios.");
            return;
        }

        for (int i = 0; i < cantidadCursos; i++) {
            if (cursos[i].getCodigo().equalsIgnoreCase(codigo)) {
                System.out.println("\n  Ya existe un curso con ese codigo.");
                return;
            }
        }

        if (cantidadCursos < cursos.length) {
            cursos[cantidadCursos] = new Curso(nombre, codigo, tutor);
            cantidadCursos++;
            System.out.println("\n Curso \"" + nombre + "\" creado exitosamente.");
        } else {
            System.out.println("\n No se pueden registrar mas cursos.");
        }
    }

    private static void agregarTarea() {
        if (cantidadCursos == 0) {
            System.out.println("\n  No hay cursos registrados. Cree un curso primero.");
            return;
        }

        System.out.println("\n--- Agregar tarea ---");
        System.out.println("Cursos disponibles:");
        for (int i = 0; i < cantidadCursos; i++) {
            System.out.println("  " + (i + 1) + ". " + cursos[i].getNombre()
                    + " (" + cursos[i].getCodigo() + ")");
        }

        int indice = leerEntero("Seleccione el numero del curso: ") - 1;
        if (indice < 0 || indice >= cantidadCursos) {
            System.out.println("\n Numero de curso invalido.");
            return;
        }

        Curso cursoSeleccionado = cursos[indice];

        System.out.print("Titulo de la tarea    : ");
        String titulo = scanner.nextLine().trim();

        System.out.print("Descripcion           : ");
        String descripcion = scanner.nextLine().trim();

        System.out.print("Fecha de entrega      : ");
        String fecha = scanner.nextLine().trim();

        if (titulo.isEmpty() || descripcion.isEmpty() || fecha.isEmpty()) {
            System.out.println("\n Todos los campos son obligatorios.");
            return;
        }

        cursoSeleccionado.agregarTarea(new Tarea(titulo, descripcion, fecha));
        System.out.println("\n  Tarea \"" + titulo + "\" agregada a \""
                + cursoSeleccionado.getNombre() + "\".");
    }

    private static void mostrarTodo() {
        System.out.println("\n=== Cursos y Tareas Registradas ===");
        if (cantidadCursos == 0) {
            System.out.println("  (No hay cursos registrados aun)");
            return;
        }
        for (int i = 0; i < cantidadCursos; i++) {
            cursos[i].mostrarInfo();
            System.out.println();
        }
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            int valor = Integer.parseInt(scanner.nextLine().trim());
            return valor;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
