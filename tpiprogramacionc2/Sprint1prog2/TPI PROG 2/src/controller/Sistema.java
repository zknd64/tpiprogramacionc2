package controller;

import exceptions.CupoLlenoException;
import exceptions.SocioNoEncontradoException;
import models.EstadoInscripcion;
import models.Inscripcion;
import models.Plan;
import models.Profesor;
import models.Socio;
import models.Turno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sistema {

    private final List<Socio> socios = new ArrayList<>();
    private final List<Profesor> profesores = new ArrayList<>();
    private final List<Plan> planes = new ArrayList<>();
    private final List<Turno> turnos = new ArrayList<>();
    private final List<Inscripcion> inscripciones = new ArrayList<>();
    private int proximoIdTurno = 1;

    // ---------- Socios ----------

    public void registrarSocio(Socio socio) {
        if (socio == null) throw new IllegalArgumentException("El socio no puede ser nulo");
        for (Socio s : socios) {
            if (s.getDni().equals(socio.getDni()))
                throw new IllegalArgumentException("Ya existe un socio con DNI " + socio.getDni());
        }
        socios.add(socio);
    }

    public Socio buscarSocioPorDni(String dni) throws SocioNoEncontradoException {
        for (Socio s : socios) {
            if (s.getDni().equals(dni)) return s;
        }
        throw new SocioNoEncontradoException("No se encontró ningún socio registrado con el DNI: " + dni);
    }

    /** Dar de baja un socio = cancelar sus inscripciones activas o suspendidas. */
    public void darDeBajaSocio(String dni) throws SocioNoEncontradoException {
        Socio socio = buscarSocioPorDni(dni);
        for (Inscripcion i : inscripciones) {
            if (i.getSocio().equals(socio)
                    && (i.getEstado() == EstadoInscripcion.ACTIVA
                        || i.getEstado() == EstadoInscripcion.SUSPENDIDA)) {
                i.cancelar();
            }
        }
    }

    /** Un socio está activo si tiene una inscripción vigente. */
    public boolean socioActivo(String dni) throws SocioNoEncontradoException {
        return buscarInscripcionVigente(buscarSocioPorDni(dni)) != null;
    }

    // ---------- Profesores ----------

    public void registrarProfesor(Profesor profesor) {
        if (profesor == null) throw new IllegalArgumentException("El profesor no puede ser nulo");
        for (Profesor p : profesores) {
            if (p.getDni().equals(profesor.getDni()))
                throw new IllegalArgumentException("Ya existe un profesor con DNI " + profesor.getDni());
        }
        profesores.add(profesor);
    }

    public Profesor buscarProfesor(String dni) {
        for (Profesor p : profesores) {
            if (p.getDni().equals(dni)) return p;
        }
        throw new IllegalArgumentException("No existe un profesor con DNI " + dni);
    }

    // ---------- Planes ----------

    public void registrarPlan(Plan plan) {
        if (plan == null) throw new IllegalArgumentException("El plan no puede ser nulo");
        for (Plan p : planes) {
            if (p.getNombre().equalsIgnoreCase(plan.getNombre()))
                throw new IllegalArgumentException("Ya existe un plan llamado " + plan.getNombre());
        }
        planes.add(plan);
    }

    public Plan buscarPlan(String nombre) {
        for (Plan p : planes) {
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        throw new IllegalArgumentException("No existe el plan " + nombre);
    }

    // ---------- Inscripciones (contratar un plan) ----------

    public Inscripcion registrarInscripcion(String dniSocio, String nombrePlan,
                                            LocalDate fechaInicio, int descuento)
            throws SocioNoEncontradoException {
        Socio socio = buscarSocioPorDni(dniSocio);
        Plan plan = buscarPlan(nombrePlan);
        if (buscarInscripcionVigente(socio) != null)
            throw new IllegalStateException("El socio " + dniSocio + " ya tiene una inscripción activa");
        Inscripcion inscripcion = new Inscripcion(socio, plan, fechaInicio, descuento);
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    private Inscripcion buscarInscripcionVigente(Socio socio) {
        for (Inscripcion i : inscripciones) {
            i.actualizarEstado();
            if (i.getSocio().equals(socio) && i.verificarVigencia()) return i;
        }
        return null;
    }

    // ---------- Turnos ----------

    public Turno registrarTurno(String dniProfesor, LocalDate fecha, LocalTime horario, int cupoMaximo) {
        Profesor profesor = buscarProfesor(dniProfesor);
        Turno turno = new Turno(proximoIdTurno++, profesor, fecha, horario, cupoMaximo);
        turnos.add(turno);
        return turno;
    }

    public Turno buscarTurnoPorId(int idTurno) {
        for (Turno t : turnos) {
            if (t.getId() == idTurno) return t;
        }
        throw new IllegalArgumentException("El turno con ID " + idTurno + " no existe en el sistema");
    }

    public void reservarTurno(String dniSocio, int idTurno)
            throws SocioNoEncontradoException, CupoLlenoException {
        Socio socio = buscarSocioPorDni(dniSocio);
        Turno turno = buscarTurnoPorId(idTurno);
        Inscripcion inscripcion = buscarInscripcionVigente(socio);
        if (inscripcion == null)
            throw new IllegalStateException("El socio " + dniSocio + " no tiene una inscripción vigente");
        if (turno.tieneReserva(socio))
            throw new IllegalStateException("El socio ya reservó este turno");
        int maximo = inscripcion.getPlan().getFrecuenciaSemanal();
        if (reservasEnSemana(socio, turno.getFecha()) >= maximo)
            throw new IllegalStateException(
                    "El plan del socio permite como máximo " + maximo + " turnos por semana");
        turno.reservar(socio);
    }

    public void cancelarReservaTurno(String dniSocio, int idTurno) throws SocioNoEncontradoException {
        Socio socio = buscarSocioPorDni(dniSocio);
        buscarTurnoPorId(idTurno).cancelarReserva(socio);
    }

    public void registrarAsistenciaTurno(String dniSocio, int idTurno, boolean asistio)
            throws SocioNoEncontradoException {
        Socio socio = buscarSocioPorDni(dniSocio);
        buscarTurnoPorId(idTurno).registrarAsistencia(socio, asistio);
    }

    /** Cuenta las reservas activas del socio en la semana (lunes a domingo) de la fecha dada. */
    private int reservasEnSemana(Socio socio, LocalDate fecha) {
        LocalDate lunes = fecha.minusDays(fecha.getDayOfWeek().getValue() - 1);
        LocalDate domingo = lunes.plusDays(6);
        int cantidad = 0;
        for (Turno t : turnos) {
            if (!t.getFecha().isBefore(lunes) && !t.getFecha().isAfter(domingo) && t.tieneReserva(socio)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    // ---------- Listados para la interfaz ----------

    public List<Socio> getSocios() { return Collections.unmodifiableList(socios); }
    public List<Profesor> getProfesores() { return Collections.unmodifiableList(profesores); }
    public List<Plan> getPlanes() { return Collections.unmodifiableList(planes); }
    public List<Turno> getTurnos() { return Collections.unmodifiableList(turnos); }
    public List<Inscripcion> getInscripciones() { return Collections.unmodifiableList(inscripciones); }
}