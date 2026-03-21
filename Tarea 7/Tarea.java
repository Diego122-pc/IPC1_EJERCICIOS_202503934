class Tarea extends ElementoAcademico {
   
    private String descripcion;
    private String fechaEntrega;

    public Tarea(String titulo, String descripcion, String fechaEntrega) {
        super(titulo);
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
    }


    public String getDescripcion() {
        return descripcion;
    }
    public String getFechaEntrega() {
        return fechaEntrega;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("    Tarea: " + nombre);
        System.out.println("    Descripción  : " + descripcion);
        System.out.println("    Entrega      : " + fechaEntrega);
    }
}
