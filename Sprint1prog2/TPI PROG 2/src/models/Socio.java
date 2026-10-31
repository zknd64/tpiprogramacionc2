package models;

import java.time.LocalDate;

public class Socio extends Persona {
    private LocalDate fechaNacimiento;
    private String telefonoContacto;

    public Socio(String nombre, String apellido, String dni, String telefono,
                 LocalDate fechaNacimiento, String telefonoContacto) {
        super(nombre, apellido, dni, telefono);
        this.fechaNacimiento = fechaNacimiento;
        this.telefonoContacto = telefonoContacto;
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefonoContacto() { return telefonoContacto; }

    public void modificarDatos(String nombre, String apellido, String telefono, String telefonoContacto) {
        super.modificarDatos(nombre, apellido, telefono);
        this.telefonoContacto = telefonoContacto;
    }
}