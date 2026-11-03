import controller.Sistema;
import javax.swing.SwingUtilities;
import view.VentanaGimnasio;

public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();

        SwingUtilities.invokeLater(() -> {
            VentanaGimnasio ventana = new VentanaGimnasio(sistema);
            ventana.setVisible(true);
        });
    }
}
