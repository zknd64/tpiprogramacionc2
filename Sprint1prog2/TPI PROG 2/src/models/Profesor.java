package src.models;
public class Profesor extends Persona{

public Profesor(String nombre, String apellido, String dni, String telefono) {
        super(nombre, apellido, dni, telefono);
    }
    public void modificarDatos (String nombre, String apellido, String telefono){
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }
}
