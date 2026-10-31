
/**
 * Write a description of class Persona here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Persona
{
    // instance variables - replace the example below with your own
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;

    /**
     * Constructor for objects of class Persona
     */
    public Persona(String nombre, String apellido, String dni, String telefono)
    {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
    }
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre = nombre;}
    
    public String getApellido(){return apellido;}
    public void setApellido(String apellido){this.apellido = apellido;}
    
    public String getDni(){return dni;}
    public void setDni(String dni){this.dni = dni;}
    
    public String getTelefono(){return telefono;}
    public void setTelefono(String telefono){this.telefono = telefono;}
}