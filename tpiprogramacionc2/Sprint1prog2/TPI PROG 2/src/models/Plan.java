package models;

import java.io.Serializable;

public class Plan implements Serializable {

    private final String nombre;
    private final int frecuenciaSemanal;
    private final double precio;
    private final int duracionMeses;

    public Plan(String nombre, int frecuenciaSemanal, double precio, int duracionMeses) {
        this.nombre = Validaciones.textoNoVacio(nombre, "El plan necesita un nombre");
        this.frecuenciaSemanal = Validaciones.positivo(frecuenciaSemanal, "La frecuencia semanal debe ser mayor a 0");
        this.precio = Validaciones.noNegativo(precio, "El precio no puede ser negativo");
        this.duracionMeses = Validaciones.positivo(duracionMeses, "La duración debe ser mayor a 0");
    }

    /** El plan decide si admite una reserva más, dadas las que el socio ya tiene en la semana. */
    public boolean permiteReservar(long reservasEnLaSemana) {
        return reservasEnLaSemana < frecuenciaSemanal;
    }

    public String getNombre() { return nombre; }
    public int getFrecuenciaSemanal() { return frecuenciaSemanal; }
    public double getPrecio() { return precio; }
    public int getDuracionMeses() { return duracionMeses; }
}