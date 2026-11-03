package models;

public enum EstadoInscripcion implements Estado {
    ACTIVA("Activa"),
    VENCIDA("Vencida"),
    SUSPENDIDA("Suspendida"),
    CANCELADA("Cancelada");

    private final String descripcion;

    EstadoInscripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
