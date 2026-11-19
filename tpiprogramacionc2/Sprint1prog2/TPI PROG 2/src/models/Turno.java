package models;

import exceptions.CupoLlenoException;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Turno implements Serializable {

    private final int id;
    private final Profesor profesor;
    private final LocalDate fecha;
    private final LocalTime horario;
    private final int cupoMaximo;
    private final List<DetalleTurno> detalles = new ArrayList<>();

    public Turno(int id, Profesor profesor, LocalDate fecha, LocalTime horario, int cupoMaximo) {
        Validaciones.todosNoNulos("Profesor, fecha y horario son obligatorios", profesor, fecha, horario);
        this.id = id;
        this.profesor = profesor;
        this.fecha = fecha;
        this.horario = horario;
        this.cupoMaximo = Validaciones.positivo(cupoMaximo, "El cupo máximo debe ser mayor a 0");
    }

    private Optional<DetalleTurno> detalleActivo(Socio socio) {
        return detalles.stream()
                .filter(d -> d.getSocio().equals(socio) && d.getEstado() != EstadoTurno.CANCELADO)
                .findFirst();
    }

    private DetalleTurno obtenerDetalleActivo(Socio socio) {
        return detalleActivo(socio)
                .orElseThrow(() -> new IllegalStateException("El socio no tiene reserva en este turno"));
    }

    public int cupoDisponible() {
        long ocupados = detalles.stream().filter(d -> d.getEstado() != EstadoTurno.CANCELADO).count();
        return cupoMaximo - (int) ocupados;
    }

    public boolean tieneReserva(Socio socio) {
        return detalleActivo(socio).isPresent();
    }

    public boolean yaOcurrio() {
        return LocalDateTime.of(fecha, horario).isBefore(LocalDateTime.now());
    }

    public boolean mismaSemanaQue(Turno otro) {
        return inicioDeSemana(fecha).equals(inicioDeSemana(otro.fecha));
    }

    private static LocalDate inicioDeSemana(LocalDate dia) {
        return dia.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public void reservar(Socio socio) throws CupoLlenoException {
        Validaciones.noNulo(socio, "El socio no puede ser nulo");
        if (yaOcurrio()) throw new IllegalStateException("No se puede reservar un turno que ya ocurrió");
        if (tieneReserva(socio)) throw new IllegalStateException("El socio ya reservó este turno");
        if (cupoDisponible() <= 0) throw new CupoLlenoException("No hay cupo disponible en este turno");
        detalles.add(new DetalleTurno(socio));
    }

    public void cancelarReserva(Socio socio) {
        obtenerDetalleActivo(socio).cancelar();
    }

    public void registrarAsistencia(Socio socio, boolean asistio) {
        obtenerDetalleActivo(socio).registrarAsistencia(asistio);
    }

    public int getId() { return id; }
    public Profesor getProfesor() { return profesor; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHorario() { return horario; }
    public int getCupoMaximo() { return cupoMaximo; }
    public List<DetalleTurno> getDetalles() { return Collections.unmodifiableList(detalles); }
}