package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public class Inscripcion implements Serializable {

    private static final Set<EstadoInscripcion> ESTADOS_ABIERTOS =
            EnumSet.of(EstadoInscripcion.ACTIVA, EstadoInscripcion.SUSPENDIDA);

    private final Socio socio;
    private final Plan plan;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final int descuento; // porcentaje, de 0 a 100
    private EstadoInscripcion estado;

    public Inscripcion(Socio socio, Plan plan, LocalDate fechaInicio, int descuento) {
        Validaciones.todosNoNulos("Socio, plan y fecha de inicio son obligatorios", socio, plan, fechaInicio);
        this.socio = socio;
        this.plan = plan;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaInicio.plusMonths(plan.getDuracionMeses());
        this.descuento = Validaciones.entre(descuento, 0, 100, "El descuento debe estar entre 0 y 100");
        this.estado = EstadoInscripcion.ACTIVA;
    }

    public double calcularDescuento() {
        return plan.getPrecio() * descuento / 100.0;
    }

    public double calcularPrecioFinal() {
        return plan.getPrecio() - calcularDescuento();
    }

    public boolean esDe(Socio otro) {
        return socio.equals(otro);
    }

    /** Abierta = activa o suspendida (todavía no vencida ni cancelada). */
    public boolean estaAbierta() {
        return ESTADOS_ABIERTOS.contains(getEstado());
    }

    /** ¿La inscripción permite asistir en esa fecha? */
    public boolean cubre(LocalDate fecha) {
        return estaAbierta() && !fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin);
    }

    public boolean verificarVigencia() {
        return cubre(LocalDate.now());
    }

    public void suspender() {
        if (getEstado() != EstadoInscripcion.ACTIVA)
            throw new IllegalStateException("Solo se puede suspender una inscripción activa");
        estado = EstadoInscripcion.SUSPENDIDA;
    }

    public void reactivar() {
        if (getEstado() != EstadoInscripcion.SUSPENDIDA)
            throw new IllegalStateException("Solo se puede reactivar una inscripción suspendida");
        estado = EstadoInscripcion.ACTIVA;
    }

    public void cancelar() {
        if (!estaAbierta())
            throw new IllegalStateException("Solo se puede cancelar una inscripción activa o suspendida");
        estado = EstadoInscripcion.CANCELADA;
    }

    /** El vencimiento se resuelve de forma perezosa: nadie tiene que acordarse de actualizarlo. */
    private void actualizarEstado() {
        if (ESTADOS_ABIERTOS.contains(estado) && LocalDate.now().isAfter(fechaFin)) {
            estado = EstadoInscripcion.VENCIDA;
        }
    }

    public EstadoInscripcion getEstado() {
        actualizarEstado();
        return estado;
    }

    public Socio getSocio() { return socio; }
    public Plan getPlan() { return plan; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public int getDescuento() { return descuento; }
}