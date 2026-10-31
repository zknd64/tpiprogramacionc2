package models;

public class DetalleTurno {
    private final Socio socio;
    private EstadoTurno estado = EstadoTurno.RESERVADO;

    public DetalleTurno(Socio socio) {
        this.socio = socio;
    }

    public Socio getSocio() { return socio; }
    public EstadoTurno getEstado() { return estado; }

    public void cancelar() {
        estado = EstadoTurno.CANCELADO;
    }

    public void registrarAsistencia(boolean asistio) {
        estado = asistio ? EstadoTurno.ASISTIO : EstadoTurno.AUSENTE;
    }
}