package controller;

import exceptions.CupoLlenoException;
import exceptions.SocioNoEncontradoException;
import models.Inscripcion;
import models.Plan;
import models.Profesor;
import models.Registro;
import models.Socio;
import models.Turno;
import models.Validaciones;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Sistema implements Serializable {

    private final Registro<String, Socio> socios = new Registro<>();
    private final Registro<String, Profesor> profesores = new Registro<>();
    private final Registro<String, Plan> planes = new Registro<>();
    private final Registro<Integer, Turno> turnos = new Registro<>();
    private final List<Inscripcion> inscripciones = new ArrayList<>();
    private int proximoIdTurno = 1;

    // ---------- Socios ----------

    public void registrarSocio(Socio socio) {
        Validaciones.noNulo(socio, "El socio no puede ser nulo");
        socios.agregar(socio.getDni(), socio, "Ya existe un socio con DNI " + socio.getDni());
    }

    public Socio buscarSocioPorDni(String dni) throws SocioNoEncontradoException {
        return socios.obtener(dni, () ->
                new SocioNoEncontradoException("No se encontró ningún socio registrado con el DNI: " + dni));
    }

    /** Dar de baja un socio = cancelar sus inscripciones activas o suspendidas. */
    public void darDeBajaSocio(String dni) throws SocioNoEncontradoException {
        inscripcionesAbiertasDe(buscarSocioPorDni(dni)).forEach(Inscripcion::cancelar);
    }

    /** Un socio está activo si tiene una inscripción vigente hoy. */
    public boolean socioActivo(String dni) throws SocioNoEncontradoException {
        return inscripcionQueCubre(buscarSocioPorDni(dni), LocalDate.now()).isPresent();
    }

    // ---------- Profesores ----------

    public void registrarProfesor(Profesor profesor) {
        Validaciones.noNulo(profesor, "El profesor no puede ser nulo");
        profesores.agregar(profesor.getDni(), profesor, "Ya existe un profesor con DNI " + profesor.getDni());
    }

    public Profesor buscarProfesor(String dni) {
        return profesores.obtener(dni, () -> new IllegalArgumentException("No existe un profesor con DNI " + dni));
    }

    // ---------- Planes ----------

    public void registrarPlan(Plan plan) {
        Validaciones.noNulo(plan, "El plan no puede ser nulo");
        planes.agregar(clavePlan(plan.getNombre()), plan, "Ya existe un plan llamado " + plan.getNombre());
    }

    public Plan buscarPlan(String nombre) {
        return planes.obtener(clavePlan(nombre), () -> new IllegalArgumentException("No existe el plan " + nombre));
    }

    private static String clavePlan(String nombre) {
        return nombre == null ? "" : nombre.strip().toLowerCase();
    }

    // ---------- Inscripciones (contratar un plan) ----------

    public Inscripcion registrarInscripcion(String dniSocio, String nombrePlan,
                                            LocalDate fechaInicio, int descuento)
            throws SocioNoEncontradoException {
        Socio socio = buscarSocioPorDni(dniSocio);
        Plan plan = buscarPlan(nombrePlan);
        if (inscripcionesAbiertasDe(socio).findAny().isPresent())
            throw new IllegalStateException("El socio " + dniSocio + " ya tiene una inscripción activa");
        Inscripcion inscripcion = new Inscripcion(socio, plan, fechaInicio, descuento);
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    private Stream<Inscripcion> inscripcionesAbiertasDe(Socio socio) {
        return inscripciones.stream().filter(i -> i.esDe(socio) && i.estaAbierta());
    }

    private Optional<Inscripcion> inscripcionQueCubre(Socio socio, LocalDate fecha) {
        return inscripciones.stream().filter(i -> i.esDe(socio) && i.cubre(fecha)).findFirst();
    }

    // ---------- Turnos ----------

    public Turno registrarTurno(String dniProfesor, LocalDate fecha, LocalTime horario, int cupoMaximo) {
        Turno turno = new Turno(proximoIdTurno, buscarProfesor(dniProfesor), fecha, horario, cupoMaximo);
        turnos.agregar(turno.getId(), turno, "Ya existe un turno con ID " + turno.getId());
        proximoIdTurno++;
        return turno;
    }

    public Turno buscarTurnoPorId(int idTurno) {
        return turnos.obtener(idTurno, () ->
                new IllegalArgumentException("El turno con ID " + idTurno + " no existe en el sistema"));
    }

    public void reservarTurno(String dniSocio, int idTurno)
            throws SocioNoEncontradoException, CupoLlenoException {
        Socio socio = buscarSocioPorDni(dniSocio);
        Turno turno = buscarTurnoPorId(idTurno);
        Plan plan = inscripcionQueCubre(socio, turno.getFecha())
                .orElseThrow(() -> new IllegalStateException(
                        "El socio " + dniSocio + " no tiene una inscripción vigente para la fecha del turno"))
                .getPlan();
        if (!plan.permiteReservar(reservasEnLaSemanaDe(turno, socio)))
            throw new IllegalStateException(
                    "El plan del socio permite como máximo " + plan.getFrecuenciaSemanal() + " turnos por semana");
        turno.reservar(socio);
    }

    public void cancelarReservaTurno(String dniSocio, int idTurno) throws SocioNoEncontradoException {
        buscarTurnoPorId(idTurno).cancelarReserva(buscarSocioPorDni(dniSocio));
    }

    public void registrarAsistenciaTurno(String dniSocio, int idTurno, boolean asistio)
            throws SocioNoEncontradoException {
        buscarTurnoPorId(idTurno).registrarAsistencia(buscarSocioPorDni(dniSocio), asistio);
    }

    /** Reservas activas del socio en OTROS turnos de la misma semana (lunes a domingo) que el dado. */
    private long reservasEnLaSemanaDe(Turno turno, Socio socio) {
        return turnos.todos().stream()
                .filter(t -> t != turno && t.mismaSemanaQue(turno) && t.tieneReserva(socio))
                .count();
    }

    // ---------- Listados para la interfaz ----------

    public List<Socio> getSocios() { return socios.todos(); }
    public List<Profesor> getProfesores() { return profesores.todos(); }
    public List<Plan> getPlanes() { return planes.todos(); }
    public List<Turno> getTurnos() { return turnos.todos(); }
    public List<Inscripcion> getInscripciones() { return Collections.unmodifiableList(inscripciones); }
}