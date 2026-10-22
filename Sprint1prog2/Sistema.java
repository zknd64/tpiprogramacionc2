import java.util.ArrayList;
import java.util.List;

public class Sistema{
    
    private ArrayList<Socio>socios;
    private ArrayList<Profesor>profesores;
    private ArrayList<Turno>turnos;
    private ArrayList<Inscripcion>inscripciones;

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