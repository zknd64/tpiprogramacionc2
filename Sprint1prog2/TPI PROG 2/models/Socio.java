package models;

import java.util.Date;

public class Socio extends Persona {
    private Date fechaNacimiento;
    private int telefonoContacto;
    private EstadoSocio estado; 

    public Socio(String nombre, String apellido, String dni, String telefono, Date fechaNacimiento, int telefonoContacto, EstadoSocio estado) {
        super(dni, nombre, apellido, telefono);       
        this.fechaNacimiento = fechaNacimiento;
        this.telefonoContacto = telefonoContacto;
        this.estado = estado;
    }

    public EstadoSocio getEstado() {
        return estado;
    }

    public void activar() {
        this.estado = EstadoSocio.ACTIVO;
    }

    public void darDeBaja() {
        this.estado = EstadoSocio.INACTIVO;
    }

    public void modificarDatos(String nombre, String apellido, String telefono, int telefonoContacto) {
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
        this.telefonoContacto = telefonoContacto;
    }
}