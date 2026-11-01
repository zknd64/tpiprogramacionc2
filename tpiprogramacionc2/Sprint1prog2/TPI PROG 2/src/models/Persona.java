package models;

public abstract class Persona {
    private final String dni;
    private String nombre;
    private String apellido;
    private String telefono;

    public Persona(String nombre, String apellido, String dni, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }

    public void modificarDatos(String nombre, String apellido, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        return dni.equals(((Persona) o).dni);
    }

    @Override
    public int hashCode() {
        return dni.hashCode();
    }
}