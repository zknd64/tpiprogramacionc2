package models;

import java.io.Serializable;

public abstract class Persona implements Serializable {

    private final String dni;
    private String nombre;
    private String apellido;
    private String telefono;

    public Persona(String nombre, String apellido, String dni, String telefono) {
        this.dni = Validaciones.textoNoVacio(dni, "El DNI es obligatorio");
        asignarDatos(nombre, apellido, telefono);
    }

    private void asignarDatos(String nombre, String apellido, String telefono) {
        this.nombre = Validaciones.textoNoVacio(nombre, "El nombre es obligatorio");
        this.apellido = Validaciones.textoNoVacio(apellido, "El apellido es obligatorio");
        this.telefono = telefono;
    }

    public void modificarDatos(String nombre, String apellido, String telefono) {
        asignarDatos(nombre, apellido, telefono);
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return dni.equals(((Persona) o).dni);
    }

    @Override
    public int hashCode() {
        return dni.hashCode();
    }
}