import controller.Sistema;
import javax.swing.SwingUtilities;
import view.VentanaGimnasio;

public class Main {
    public static void main(String[] args) {
        // Inicializa el controlador único (Modelo/Controlador compartidos)
        Sistema sistema = new Sistema();

        // Lanza la interfaz gráfica de Swing
        SwingUtilities.invokeLater(() -> {
            VentanaGimnasio ventana = new VentanaGimnasio(sistema);
            ventana.setVisible(true);
        });
    }
}
