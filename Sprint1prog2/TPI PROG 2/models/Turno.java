package models;
import java.sql.Time;
import java.util.ArrayList;

public class Turno {
    private ArrayList<Socio>socios;
    private ArrayList<Socio>asistentes;
    private Profesor profesor;
    private Time horario;
    private int cupoMaximo;
    private boolean estado;
    
public Turno(Socio socio,Profesor profesor,Time horario,int cupoMaximo,boolean estado)
    {
        socios = new ArrayList<>();
        this.profesor=profesor;
        this.horario=horario;
        this.cupoMaximo=cupoMaximo;
        this.estado=estado;
}
}
