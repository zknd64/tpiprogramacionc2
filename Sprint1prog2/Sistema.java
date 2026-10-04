import java.util.ArrayList;

/**
 * Write a description of class Sistema here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sistema
{
    
    private ArrayList<Socio>socios;
    private ArrayList<Profesor>profesores;
    private ArrayList<Turno>turnos;
    private ArrayList<Inscripcion>inscripciones;
    /**
     * Constructor for objects of class Sistema
     */
    public Sistema() {
        socios = new ArrayList<>();
        profesores = new ArrayList<>();
        inscripciones = new ArrayList<>();
        turnos = new ArrayList<>();
    }

    public void registrarSocio(Socio socio) {
        socios.add(socio);
    }

    public void darDeBajaSocio(Socio socio) {
        socio.darDeBaja();
    }

    public void registrarProfesor(Profesor profesor) {
        profesores.add(profesor);
    }

    public void registrarInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
    }

    public void registrarTurno(Turno turno) {
        turnos.add(turno);
    }
}