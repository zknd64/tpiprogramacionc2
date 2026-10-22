import java.util.Date;

/**
 * Write a description of class Socio here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Socio extends Persona
{
    private Date fechaNacimiento;
    private int telefonoContacto;
    private String estado;

    /**
     * Constructor for objects of class Socio
     */
    public Socio(String nombre,String apellido,String dni,
    String telefono, Date fechaNacimiento,int telefonoContacto, String estado)
    
    {
        super(dni,nombre,apellido,telefono);       
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

    public void modificarDatos(String nombre, String apellido, String telefono, int telefonoContacto) {
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
        this.telefonoContacto = telefonoContacto;
    }

    // Getters

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento){
        this.fechaNacimiento=fechaNacimiento;
    }
    public int getTelefonoContacto() {
        return telefonoContacto;
    }
    public void setTelefonoContacto(int telefonoContacto){
        this.telefonoContacto=telefonoContacto;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado){
        this.estado=estado;
    }
}

    
