package src.models;
import java.sql.Time;
import java.util.ArrayList;

public class Turno {
    private ArrayList<Socio>socios;
    private ArrayList<Socio>asistentes;
    private Profesor profesor;
    private Time horario;
    private int cupoMaximo;
    private EstadoTurno estado;
    
public Turno(Socio socio,Profesor profesor,Time horario,int cupoMaximo,EstadoTurno estado)
    {
        socios = new ArrayList<>();
        this.profesor=profesor;
        this.horario=horario;
        this.cupoMaximo=cupoMaximo;
        this.estado=estado;
}
public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
}
}
