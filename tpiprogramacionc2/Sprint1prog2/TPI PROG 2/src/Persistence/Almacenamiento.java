package Persistence;

import controller.Sistema;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Almacenamiento {
    private final String archivo;

    public Almacenamiento(String archivo) {
        this.archivo = archivo;
    }

    public void guardar(Sistema sistema) throws IOException {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
            salida.writeObject(sistema);
        }
    }

    /** Si el archivo no existe (primera vez), devuelve un Sistema vacío. */
    public Sistema cargar() throws IOException, ClassNotFoundException {
        File f = new File(archivo);
        if (!f.exists()) return new Sistema();
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(f))) {
            return (Sistema) entrada.readObject();
        }
    }
}