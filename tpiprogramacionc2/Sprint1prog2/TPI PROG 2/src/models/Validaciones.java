package models;

import java.util.Arrays;
import java.util.Objects;

/** Reglas de validación reutilizables. Cada método devuelve el valor para poder usarlo en la asignación. */
public final class Validaciones {

    private Validaciones() { }

    public static <T> T noNulo(T valor, String mensaje) {
        if (valor == null) throw new IllegalArgumentException(mensaje);
        return valor;
    }

    public static void todosNoNulos(String mensaje, Object... valores) {
        if (Arrays.stream(valores).anyMatch(Objects::isNull)) throw new IllegalArgumentException(mensaje);
    }

    public static String textoNoVacio(String texto, String mensaje) {
        if (texto == null || texto.isBlank()) throw new IllegalArgumentException(mensaje);
        return texto;
    }

    public static int positivo(int valor, String mensaje) {
        if (valor <= 0) throw new IllegalArgumentException(mensaje);
        return valor;
    }

    public static double noNegativo(double valor, String mensaje) {
        if (valor < 0) throw new IllegalArgumentException(mensaje);
        return valor;
    }

    public static int entre(int valor, int minimo, int maximo, String mensaje) {
        if (valor < minimo || valor > maximo) throw new IllegalArgumentException(mensaje);
        return valor;
    }
}