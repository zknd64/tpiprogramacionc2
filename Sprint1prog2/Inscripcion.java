import java.util.Date;

/**
 * Write a description of class Inscripcion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Inscripcion
{
    private Socio socio;
    private Plan plan;
    private Date fechaInicio;
    private Date fechaFin;
    private int descuento;

    /**
     * Constructor for objects of class Inscripcion
     */
    public Inscripcion(Socio socio, Plan plan, Date fechaInicio, Date fechaFin, int descuento)
    {
         this.socio=socio;
         this.plan=plan;
         this.fechaInicio=fechaInicio;
         this.fechaFin=fechaFin;
         this.descuento=descuento;
    }
    public Date getFechaInicio(){
        return fechaInicio;
    }
}