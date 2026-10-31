package models;

import java.time.LocalDate;

public class Socio extends Persona {
    private LocalDate fechaNacimiento;
    private String telefonoContacto;
    private EstadoSocio estado;

    public Socio(String nombre, String apellido, String dni, String telefono,
                 LocalDate fechaNacimiento, String telefonoContacto, EstadoSocio estado) {
        super(nombre, apellido, dni, telefono);   // mismo orden que Persona
        this.fechaNacimiento = fechaNacimiento;
        this.telefonoContacto = telefonoContacto;
        this.estado = estado;
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefonoContacto() { return telefonoContacto; }
    public EstadoSocio getEstado() { return estado; }

    public void activar() { this.estado = EstadoSocio.ACTIVO; }
    public void darDeBaja() { this.estado = EstadoSocio.INACTIVO; }

    public void modificarDatos(String nombre, String apellido, String telefono, String telefonoContacto) {
        super.modificarDatos(nombre, apellido, telefono);
        this.telefonoContacto = telefonoContacto;
    }
}