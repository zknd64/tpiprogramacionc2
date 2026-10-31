package controller;
import java.util.ArrayList;
import java.util.List;
import models.Socio;
import models.Profesor;
import models.Turno;
import models.Inscripcion;
import exceptions.CupoLleno;
import exceptions.SocioNoEncontrado;

public class Sistema {
    
    private ArrayList<Socio> socios;
    private ArrayList<Profesor> profesores;
    private ArrayList<Turno> turnos;
    private ArrayList<Inscripcion> inscripciones;

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
        if (socios.contains(socio)) {
            socio.darDeBaja();
        }
    }

    public void registrarProfesor(Profesor profesor) {
        profesores.add(profesor);
    }

    public void registrarTurno(Turno turno) {
        turnos.add(turno);
    }

    public Socio buscarSocioPorDni(String dni) throws SocioNoEncontrado {
        for (Socio s : socios) {
            if (s.getDni().equals(dni)) {
                return s;
            }
        }
        throw new SocioNoEncontrado("No se encontró ningún socio registrado con el DNI: " + dni);
    }

    public Turno buscarTurnoPorId(int idTurno) {
        for (Turno t : turnos) {
            if (t.getId() == idTurno) {
                return t;
            }
        }
        return null;
    }

    public void registrarInscripcion(String dniSocio, int idTurno) throws SocioNoEncontrado, CupoLleno {
        Socio socio = buscarSocioPorDni(dniSocio);

        Turno turno = buscarTurnoPorId(idTurno);
        if (turno == null) {
            throw new IllegalArgumentException("El turno con ID " + idTurno + " no existe en el sistema.");
        }

        if (turno.cupoDisponible() <= 0) {
            throw new CupoLleno("El turno seleccionado ya no cuenta con cupos disponibles.");
        }

        try {
            turno.reservar(socio);
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("cupo")) {
                throw new CupoLleno(e.getMessage());
            } else {
                throw new IllegalStateException(e.getMessage());
            }
        }
    }

    public void cancelarReservaTurno(String dniSocio, int idTurno) throws SocioNoEncontrado {
        Socio socio = buscarSocioPorDni(dniSocio);
        Turno turno = buscarTurnoPorId(idTurno);

        if (turno != null) {
            turno.cancelarReserva(socio);
        } else {
            throw new IllegalArgumentException("El turno indicado no existe.");
        }
    }

    public void registrarAsistenciaTurno(String dniSocio, int idTurno, boolean asistio) throws SocioNoEncontrado {
        Socio socio = buscarSocioPorDni(dniSocio);
        Turno turno = buscarTurnoPorId(idTurno);

        if (turno != null) {
            turno.registrarAsistencia(socio, asistio);
        } else {
            throw new IllegalArgumentException("El turno indicado no existe.");
        }
    }

    public void registrarInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
    }
}