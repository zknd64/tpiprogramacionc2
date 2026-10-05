import java.util.Timer;
import java.util.ArrayList;

/**
 * Write a description of class Turno here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Turno
{
    private ArrayList<Socio>socios;
    private ArrayList<Socio>asistentes;
    private Profesor profesor;
    private Timer horario;
    private int cupoMaximo;
    private boolean estado;
    

    /**
     * Constructor for objects of class Turno
     */
    public Turno(Socio socio,Profesor profesor,Timer horario,int cupoMaximo,boolean estado)
    {
        socios = new ArrayList<>();
        this.profesor=profesor;
        this.horario=horario;
        this.cupoMaximo=cupoMaximo;
        this.estado=estado;
    }
    public Profesor getprofesor(){
        return profesor;
    }
    public Timer getHorario(){
        return horario;
    }
    public int getCupoMaximo(){
        return cupoMaximo;
    }
    public boolean getEstado(){
        return estado;
    }
    // saber si quedan cupos disponibles
    public boolean validarCupoDisponible() {
    return socios.size() < cupoMaximo;
}
    // registrar la reserva
    public void registrarReserva(Socio socio) {
    if (validarCupoDisponible()) {
        socios.add(socio);
    }
}
// asistencia
public boolean verificarAsistencia(Socio socio) {
    for (Socio s : asistentes) {
        if (s.getDni()==(socio.getDni())){
            return true;
        }
    }
    return false;
    }
}