class Curso extends ElementoAcademico {
    private String codigo;
    private String tutorResponsable;
    private Tarea[] tareas;
    private int cantidadTareas;
    private static final int MAX_TAREAS = 50;

    public Curso(String nombre, String codigo, String tutorResponsable) {
        super(nombre);
        this.codigo = codigo;
        this.tutorResponsable = tutorResponsable;
        this.tareas = new Tarea[MAX_TAREAS];
        this.cantidadTareas = 0;
    }

    public void agregarTarea(Tarea tarea) {
        if (cantidadTareas < MAX_TAREAS) {
            tareas[cantidadTareas] = tarea;
            cantidadTareas++;
        } else {
            System.out.println("  ⚠ No se pueden agregar más tareas a este curso.");
        }
    }

    public Tarea[] getTareas() {
        return tareas;
    }

    public int getCantidadTareas() {
        return cantidadTareas;
    }

    public String getCodigo() { return codigo; }
    public String getTutorResponsable() { return tutorResponsable; }

    @Override
    public void mostrarInfo() {
        System.out.println(" Curso  : " + padRight(nombre, 28) + "║");
        System.out.println(" Código : " + padRight(codigo, 28) + "║");
        System.out.println(" Tutor  : " + padRight(tutorResponsable, 28) + "║");

        if (cantidadTareas == 0) {
            System.out.println("    (Sin tareas registradas)");
        } else {
            System.out.println("  Tareas (" + cantidadTareas + "):");
            for (int i = 0; i < cantidadTareas; i++) {
                tareas[i].mostrarInfo();
            }
        }
    }

    private String padRight(String s, int n) {
        if (s.length() > n) s = s.substring(0, n - 3) + "...";
        return String.format("%-" + n + "s", s);
    }
}
