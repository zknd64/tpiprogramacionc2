import java.util.Timer;

/**
 * Write a description of class Turno here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Turno
{
    private Socio socio;
    private Profesor profesor;
    private Timer horario;
    private int cupoMaximo;
    private boolean estado;
    

    /**
     * Constructor for objects of class Turno
     */
    public Turno(Socio socio,Profesor profesor,Timer horario,int cupoMaximo,boolean estado)
    {
        this.socio=socio;
        this.profesor=profesor;
        this.horario=horario;
        this.cupoMaximo=cupoMaximo;
        this.estado=estado;
    }
    public Socio getSocio(){
        return socio;
    }
    public Profesor getprofesor(){
        return profesor;
    }
    
}