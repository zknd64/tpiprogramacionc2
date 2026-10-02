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
    }

    
}