package models;
import java.time.LocalDate;

 public class Inscripcion {
    private final Socio socio;
    private final Plan plan;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final int descuento; 
    private EstadoInscripcion estado;

    public Inscripcion(Socio socio, Plan plan, LocalDate fechaInicio, int descuento) {
        if (socio == null || plan == null || fechaInicio == null)
            throw new IllegalArgumentException("Socio, plan y fecha de inicio son obligatorios");
        if (descuento < 0 || descuento > 100)
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        this.socio = socio;
        this.plan = plan;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaInicio.plusMonths(plan.getDuracionMeses());
        this.descuento = descuento;
        this.estado = EstadoInscripcion.ACTIVA;
    }

    public double calcularDescuento() {
        return plan.getPrecio() * descuento / 100.0;
    }

    public double calcularPrecioFinal() {
        return plan.getPrecio() - calcularDescuento();
    }

    public boolean verificarVigencia() {
        LocalDate hoy = LocalDate.now();
        return estado == EstadoInscripcion.ACTIVA
                && !hoy.isBefore(fechaInicio)
                && !hoy.isAfter(fechaFin);
    }
}

    

