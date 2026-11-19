package models;

import java.time.LocalDate;

public class Socio extends Persona {

    private final LocalDate fechaNacimiento;
    private String telefonoContacto;

    public Socio(String nombre, String apellido, String dni, String telefono,
                 LocalDate fechaNacimiento, String telefonoContacto) {
        super(nombre, apellido, dni, telefono);
        this.fechaNacimiento = validarFechaNacimiento(fechaNacimiento);
        this.telefonoContacto = telefonoContacto;
    }

    private static LocalDate validarFechaNacimiento(LocalDate fecha) {
        Validaciones.noNulo(fecha, "La fecha de nacimiento es obligatoria");
        if (fecha.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        return fecha;
    }

    public void modificarDatos(String nombre, String apellido, String telefono, String telefonoContacto) {
        super.modificarDatos(nombre, apellido, telefono);
        this.telefonoContacto = telefonoContacto;
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefonoContacto() { return telefonoContacto; }
}