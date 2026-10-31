package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Turno {
    private final Profesor profesor;
    private final LocalDate fecha;
    private final LocalTime horario;
    private final int cupoMaximo;
    private final List<DetalleTurno> detalles;

    public Turno(Profesor profesor, LocalDate fecha, LocalTime horario, int cupoMaximo) {
        if (profesor == null || fecha == null || horario == null)
            throw new IllegalArgumentException("Profesor, fecha y horario son obligatorios");
        if (cupoMaximo <= 0)
            throw new IllegalArgumentException("El cupo máximo debe ser mayor a 0");
        this.profesor = profesor;
        this.fecha = fecha;
        this.horario = horario;
        this.cupoMaximo = cupoMaximo;
        this.detalles = new ArrayList<>();
    }

    private DetalleTurno buscarDetalleActivo(Socio socio) {
        for (DetalleTurno d : detalles) {
            if (d.getSocio().equals(socio) && d.getEstado() != EstadoTurno.CANCELADO) {
                return d;
            }
        }
        return null;
    }

    public int cupoDisponible() {
        int ocupados = 0;
        for (DetalleTurno d : detalles) {
            if (d.getEstado() != EstadoTurno.CANCELADO) ocupados++;
        }
        return cupoMaximo - ocupados;
    }

    public boolean tieneReserva(Socio socio) {
        return buscarDetalleActivo(socio) != null;
    }

    public void reservar(Socio socio) {
        if (socio == null) throw new IllegalArgumentException("El socio no puede ser nulo");
        if (tieneReserva(socio)) throw new IllegalStateException("El socio ya reservó este turno");
        if (cupoDisponible() <= 0) throw new IllegalStateException("No hay cupo disponible");
        detalles.add(new DetalleTurno(socio));
    }

    public void cancelarReserva(Socio socio) {
        DetalleTurno d = buscarDetalleActivo(socio);
        if (d == null) throw new IllegalStateException("El socio no tiene reserva en este turno");
        d.cancelar();
    }

    public void registrarAsistencia(Socio socio, boolean asistio) {
        DetalleTurno d = buscarDetalleActivo(socio);
        if (d == null) throw new IllegalStateException("El socio no tiene reserva en este turno");
        d.registrarAsistencia(asistio);
    }

    public Profesor getProfesor() { return profesor; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHorario() { return horario; }
    public int getCupoMaximo() { return cupoMaximo; }
    public List<DetalleTurno> getDetalles() { return Collections.unmodifiableList(detalles); }
}
