abstract class ElementoAcademico {
    protected String nombre;

    public ElementoAcademico(String nombre) {
        this.nombre = nombre;
    }

   
    public abstract void mostrarInfo();

    public String getNombre() {
        return nombre;
    }
}
