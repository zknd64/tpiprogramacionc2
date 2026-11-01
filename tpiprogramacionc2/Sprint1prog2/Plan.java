
public class Plan{
    private int frecuenciaSemanal;
    private double precio;
    private int duracion;
    
    public Plan(int frecuenciaSemanal,double precio, int duracion){
        this.frecuenciaSemanal=frecuenciaSemanal;
        this.precio=precio;
        this.duracion=duracion;
    }
    public double calcularPrecio(){
        return precio;
    }
}