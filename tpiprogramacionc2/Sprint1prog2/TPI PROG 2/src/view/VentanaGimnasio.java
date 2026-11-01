package view;

import controller.Sistema;
import exceptions.CupoLleno;
import exceptions.SocioNoEncontrado;
import models.Profesor;
import models.Socio;
import models.Turno;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class VentanaGimnasio extends JFrame {
    private final Sistema sistema;

    public VentanaGimnasio(Sistema sistema) {
        this.sistema = sistema;
        
        setTitle("SISTEMA DE TURNOS - GYM");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        // Componente de pestañas
        JTabbedPane pestañas = new JTabbedPane();

        pestañas.addTab("Socios y Profesores", crearPanelPersonas());
        pestañas.addTab("Crear Turnos", crearPanelTurnos());
        pestañas.addTab("Inscripciones y Acciones", crearPanelAcciones());

        add(pestañas);
    }

    // PESTAÑA 1: Registro de Socios y Profesores
    private JPanel crearPanelPersonas() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Formulario Socio
        JPanel panelSocio = new JPanel(new GridLayout(8, 2, 5, 5));
        panelSocio.setBorder(BorderFactory.createTitledBorder("Registrar Socio"));
        JTextField txtNomS = new JTextField();
        JTextField txtApeS = new JTextField();
        JTextField txtDniS = new JTextField();
        JTextField txtTelS = new JTextField();
        JTextField txtFecNac = new JTextField("AAAA-MM-DD");
        JTextField txtTelCont = new JTextField();
        JButton btnRegSocio = new JButton("Registrar Socio");

        panelSocio.add(new JLabel("Nombre:")); panelSocio.add(txtNomS);
        panelSocio.add(new JLabel("Apellido:")); panelSocio.add(txtApeS);
        panelSocio.add(new JLabel("DNI:")); panelSocio.add(txtDniS);
        panelSocio.add(new JLabel("Teléfono:")); panelSocio.add(txtTelS);
        panelSocio.add(new JLabel("Fec. Nacimiento:")); panelSocio.add(txtFecNac);
        panelSocio.add(new JLabel("Tel. Contacto:")); panelSocio.add(txtTelCont);
        panelSocio.add(new JLabel("")); panelSocio.add(btnRegSocio);

        btnRegSocio.addActionListener(e -> {
            try {
                LocalDate fn = LocalDate.parse(txtFecNac.getText().trim());
                Socio nuevo = new Socio(txtNomS.getText().trim(), txtApeS.getText().trim(), 
                                        txtDniS.getText().trim(), txtTelS.getText().trim(), 
                                        fn, txtTelCont.getText().trim());
                sistema.registrarSocio(nuevo);
                JOptionPane.showMessageDialog(this, "Socio registrado con éxito.");
                // Limpiar campos
                txtNomS.setText(""); txtApeS.setText(""); txtDniS.setText(""); txtTelS.setText(""); txtTelCont.setText("");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Error: Formato de fecha inválido (Use AAAA-MM-DD).", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Formulario Profesor
        JPanel panelProfe = new JPanel(new GridLayout(5, 2, 5, 5));
        panelProfe.setBorder(BorderFactory.createTitledBorder("Registrar Profesor"));
        JTextField txtNomP = new JTextField();
        JTextField txtApeP = new JTextField();
        JTextField txtDniP = new JTextField();
        JTextField txtTelP = new JTextField();
        JButton btnRegProfe = new JButton("Registrar Profesor");

        panelProfe.add(new JLabel("Nombre:")); panelProfe.add(txtNomP);
        panelProfe.add(new JLabel("Apellido:")); panelProfe.add(txtApeP);
        panelProfe.add(new JLabel("DNI:")); panelProfe.add(txtDniP);
        panelProfe.add(new JLabel("Teléfono:")); panelProfe.add(txtTelP);
        panelProfe.add(new JLabel("")); panelProfe.add(btnRegProfe);

        btnRegProfe.addActionListener(e -> {
            try {
                Profesor nuevo = new Profesor(txtNomP.getText().trim(), txtApeP.getText().trim(), 
                                              txtDniP.getText().trim(), txtTelP.getText().trim());
                sistema.registrarProfesor(nuevo);
                JOptionPane.showMessageDialog(this, "Profesor registrado con éxito.");
                txtNomP.setText(""); txtApeP.setText(""); txtDniP.setText(""); txtTelP.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(panelSocio);
        panel.add(panelProfe);
        return panel;
    }

    // PESTAÑA 2: Creación de Turnos
    private JPanel crearPanelTurnos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Crear Nuevo Turno"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtIdT = new JTextField(10);
        JTextField txtDniProf = new JTextField(10);
        JTextField txtHora = new JTextField("HH:MM");
        JTextField txtCupo = new JTextField(5);
        JButton btnCrearTurno = new JButton("Registrar Turno");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("ID Turno (Numérico):"), gbc);
        gbc.gridx = 1; panel.add(txtIdT, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("DNI Profesor Cargo:"), gbc);
        gbc.gridx = 1; panel.add(txtDniProf, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Horario (HH:MM):"), gbc);
        gbc.gridx = 1; panel.add(txtHora, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Cupo Máximo:"), gbc);
        gbc.gridx = 1; panel.add(txtCupo, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; panel.add(btnCrearTurno, gbc);

        btnCrearTurno.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdT.getText().trim());
                String dniProf = txtDniProf.getText().trim();
                LocalTime horario = LocalTime.parse(txtHora.getText().trim());
                int cupo = Integer.parseInt(txtCupo.getText().trim());

                // Reutiliza tu lógica rápida de asignación del Main original
                Profesor profeTurno = new Profesor("Profesor", "Asignado", dniProf, "000");
                Turno nuevoTurno = new Turno(id, profeTurno, LocalDate.now().plusDays(1), horario, cupo);
                
                sistema.registrarTurno(nuevoTurno);
                JOptionPane.showMessageDialog(this, "Turno ID " + id + " creado para mañana.");
                txtIdT.setText(""); txtDniProf.setText(""); txtCupo.setText("");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Error: Formato de hora inválido (HH:MM).", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: ID y Cupo deben ser numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    // PESTAÑA 3: Gestión de operaciones (Inscripción, Asistencia, Cancelación)
    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Operaciones de Turnos"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtDniSocio = new JTextField(12);
        JTextField txtIdTurno = new JTextField(8);
        JComboBox<String> comboAsistio = new JComboBox<>(new String[]{"Asistió (S)", "Ausente (N)"});

        JButton btnInscribir = new JButton("Inscribir Socio a Turno");
        JButton btnAsistencia = new JButton("Registrar Asistencia");
        JButton btnCancelar = new JButton("Cancelar Reserva");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("DNI Socio:"), gbc);
        gbc.gridx = 1; panel.add(txtDniSocio, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("ID Turno:"), gbc);
        gbc.gridx = 1; panel.add(txtIdTurno, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Condición Asistencia:"), gbc);
        gbc.gridx = 1; panel.add(comboAsistio, gbc);

        // Grid para botones posicionados abajo
        gbc.gridwidth = 2; gbc.gridx = 0;
        gbc.gridy = 3; panel.add(btnInscribir, gbc);
        gbc.gridy = 4; panel.add(btnAsistencia, gbc);
        gbc.gridy = 5; panel.add(btnCancelar, gbc);

        // LÓGICA CONECTADA AL CONTROLADOR EXISTENTE (Captura excepciones personalizadas)
        btnInscribir.addActionListener(e -> {
            try {
                String dni = txtDniSocio.getText().trim();
                int id = Integer.parseInt(txtIdTurno.getText().trim());
                sistema.registrarInscripcion(dni, id);
                JOptionPane.showMessageDialog(this, "Inscripción exitosa realizada.");
            } catch (SocioNoEncontrado | CupoLleno ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error del Sistema", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAsistencia.addActionListener(e -> {
            try {
                String dni = txtDniSocio.getText().trim();
                int id = Integer.parseInt(txtIdTurno.getText().trim());
boolean asistio = comboAsistio.getSelectedIndex() == 0;
sistema.registrarAsistenciaTurno(dni, id, asistio);
JOptionPane.showMessageDialog(this, "Asistencia guardada correctamente.");
} catch (Exception ex) {
JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
});
btnCancelar.addActionListener(e -> {
try {
String dni = txtDniSocio.getText().trim();
int id = Integer.parseInt(txtIdTurno.getText().trim());
sistema.cancelarReservaTurno(dni, id);
JOptionPane.showMessageDialog(this, "Reserva cancelada. Cupo liberado.");
} catch (Exception ex) {
JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
});
return panel;
}
}
