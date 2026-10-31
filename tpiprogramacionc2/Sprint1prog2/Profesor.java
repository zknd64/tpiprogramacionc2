
public class Profesor extends Persona{

    public Profesor(String dni, String nombre,String apellido,String telefono){
        super(dni, nombre, apellido, telefono);
    }
    public void modificarDatos (String nombre, String apellido, String telefono){
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }
    
}
