package Persistence;

import controller.Sistema;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Almacenamiento {

    private final Path archivo;

    public Almacenamiento(String archivo) {
        this.archivo = Path.of(archivo);
    }

    /** Escribe en un temporal y recién después reemplaza: un corte a mitad de guardado no corrompe el archivo. */
    public void guardar(Sistema sistema) throws IOException {
        Path temporal = Path.of(archivo + ".tmp");
        try (ObjectOutputStream salida = new ObjectOutputStream(Files.newOutputStream(temporal))) {
            salida.writeObject(sistema);
        }
        Files.move(temporal, archivo, StandardCopyOption.REPLACE_EXISTING);
    }

    /** Si el archivo no existe (primera vez), devuelve un Sistema vacío. */
    public Sistema cargar() throws IOException, ClassNotFoundException {
        if (Files.notExists(archivo)) return new Sistema();
        try (ObjectInputStream entrada = new ObjectInputStream(Files.newInputStream(archivo))) {
            return (Sistema) entrada.readObject();
        }
    }
}