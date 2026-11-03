import controller.Sistema;
import Persistence.Almacenamiento;
import view.VentanaGimnasio;

import javax.swing.SwingUtilities;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Almacenamiento almacenamiento = new Almacenamiento("gimnasio.dat");

        Sistema cargado;
        try {
            cargado = almacenamiento.cargar();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudo cargar los datos, se inicia vacío: " + e.getMessage());
            cargado = new Sistema();
        }
        final Sistema sistema = cargado;

        // Guarda automáticamente al cerrar el programa
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                almacenamiento.guardar(sistema);
            } catch (IOException e) {
                System.out.println("No se pudo guardar: " + e.getMessage());
            }
        }));

        SwingUtilities.invokeLater(() -> {
            VentanaGimnasio ventana = new VentanaGimnasio(sistema);
            ventana.setVisible(true);
        });
    }
}