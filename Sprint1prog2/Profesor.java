
public class Profesor{
    
    private int dni;
    private String nombre;
    private String apellido;
    private String telefono;

    public Profesor(int dni, String nombre,String apellido,String telefono){
        this.dni=dni;
        this.nombre=nombre;
        this.apellido=apellido;
        this.telefono=telefono;
    }
    public void modificarDatos (String nombre, String apellido, String telefono){
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }
    public int getDni(){
        return dni;
    }
    public String getNombre(){
        return nombre;
    }
    public String getApellido(){
        return apellido;
    }
    public String getTelefono(){
        return telefono;
    }
}
