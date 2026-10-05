import java.util.Date;

public class Inscripcion
{
    private Socio socio;
    private Plan plan;
    private Date fechaInicio;
    private Date fechaFin;
    private int descuento; 

    public Inscripcion(Socio socio, Plan plan, Date fechaInicio, Date fechaFin, int descuento)
    {
         this.socio = socio;
         this.plan = plan;
         this.fechaInicio = fechaInicio;
         this.fechaFin = fechaFin;
         this.descuento = descuento;
    }

    public double calcularDescuento() {
        return descuento;
    }

    public double calcularPrecioFinal() {
        return plan.calcularPrecio() - calcularDescuento();
    }

    public boolean verificarVigencia() {
        Date hoy = new Date();
        return !hoy.before(fechaInicio) && !hoy.after(fechaFin);
    }

    public Socio getSocio(){
        return socio;
    }
    
    public Plan getPlan(){
        return plan;
    }
    
    public Date getFechaInicio(){
        return fechaInicio;
    }
    
    public Date getFechaFin(){
        return fechaFin;
    }
    
    public int getDescuento(){ 
        return descuento;
    }
    
    public void setDescuento(int descuento){ 
        this.descuento = descuento;
    }
}

