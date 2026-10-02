import java.util.Date;

/**
 * Write a description of class Socio here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Socio
{
    // instance variables - replace the example below with your own
    private String nombre;
    private String apellido;
    private int dni;
    private int telefono;
    private Date fechaNacimiento;
    private int telefonoContacto;
    private String estado;

    /**
     * Constructor for objects of class Socio
     */
    public Socio(String nombre,String apellido,int dni,
    int telefono, Date fechaNacimiento,int telefonoContacto, String estado)
    
    {
        this.nombre=nombre;
        this.apellido=apellido;
        this.dni=dni;
        this.telefono=telefono;
        this.fechaNacimiento=fechaNacimiento;
        this.telefonoContacto=telefonoContacto;
        this.estado=estado;
    }
    
    public void activar() {
        this.estado = "Activo";
    }

    public void darDeBaja() {
        this.estado = "Inactivo";
    }

    public void modificarDatos(String nombre, String apellido, int telefono, int telefonoContacto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.telefonoContacto = telefonoContacto;
    }

    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getDni() {
        return dni;
    }

    public int getTelefono() {
        return telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getTelefonoContacto() {
        return telefonoContacto;
    }

    public String getEstado() {
        return estado;
    }
}

    
