package models;

public enum EstadoTurno implements Estado {
    RESERVADO("Reservado"),
    ASISTIO("Asistió"),
    AUSENTE("Ausente"),
    CANCELADO("Cancelado");

    private final String descripcion;

    EstadoTurno(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}