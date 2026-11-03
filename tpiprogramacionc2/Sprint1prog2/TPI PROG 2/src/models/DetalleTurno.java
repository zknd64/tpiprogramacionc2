package models;

import java.io.Serializable;

public class DetalleTurno implements Serializable {
    private final Socio socio;
    private EstadoTurno estado = EstadoTurno.RESERVADO;

    public DetalleTurno(Socio socio) {
        if (socio == null) throw new IllegalArgumentException("El socio no puede ser nulo");
        this.socio = socio;
    }

    public Socio getSocio() { return socio; }
    public EstadoTurno getEstado() { return estado; }

    public void cancelar() {
        if (estado != EstadoTurno.RESERVADO)
            throw new IllegalStateException("Solo se puede cancelar una reserva pendiente");
        estado = EstadoTurno.CANCELADO;
    }

    public void registrarAsistencia(boolean asistio) {
        if (estado != EstadoTurno.RESERVADO)
            throw new IllegalStateException("La asistencia ya fue registrada o la reserva está cancelada");
        estado = asistio ? EstadoTurno.ASISTIO : EstadoTurno.AUSENTE;
    }
}