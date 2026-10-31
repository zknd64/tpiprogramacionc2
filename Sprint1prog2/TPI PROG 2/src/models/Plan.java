package models;

public class Plan {
    private final String nombre;
    private final int frecuenciaSemanal;
    private final double precio;
    private final int duracionMeses;

    public Plan(String nombre, int frecuenciaSemanal, double precio, int duracionMeses) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El plan necesita un nombre");
        if (frecuenciaSemanal <= 0)
            throw new IllegalArgumentException("La frecuencia semanal debe ser mayor a 0");
        if (precio < 0)
            throw new IllegalArgumentException("El precio no puede ser negativo");
        if (duracionMeses <= 0)
            throw new IllegalArgumentException("La duración debe ser mayor a 0");
        this.nombre = nombre;
        this.frecuenciaSemanal = frecuenciaSemanal;
        this.precio = precio;
        this.duracionMeses = duracionMeses;
    }

    public String getNombre() { return nombre; }
    public int getFrecuenciaSemanal() { return frecuenciaSemanal; }
    public double getPrecio() { return precio; }
    public int getDuracionMeses() { return duracionMeses; }
}
   