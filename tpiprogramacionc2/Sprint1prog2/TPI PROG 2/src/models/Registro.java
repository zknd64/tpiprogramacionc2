package models;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Colección de entidades indexadas por clave única.
 * Reemplaza los for + if repetidos de Sistema y es el punto de reemplazo natural
 * por un repositorio de base de datos.
 */
public class Registro<K, V> implements Serializable {

    private final Map<K, V> elementos = new LinkedHashMap<>();

    public void agregar(K clave, V valor, String mensajeDuplicado) {
        if (elementos.putIfAbsent(clave, valor) != null) {
            throw new IllegalArgumentException(mensajeDuplicado);
        }
    }

    public Optional<V> buscar(K clave) {
        return Optional.ofNullable(elementos.get(clave));
    }

    /** Devuelve el elemento o lanza la excepción indicada (checked o unchecked). */
    public <X extends Throwable> V obtener(K clave, Supplier<? extends X> error) throws X {
        return buscar(clave).orElseThrow(error);
    }

    public List<V> todos() {
        return List.copyOf(elementos.values());
    }
}