package view;

import controller.Sistema;
import exceptions.CupoLlenoException;
import exceptions.SocioNoEncontradoException;
import models.Estado;
import models.Inscripcion;
import models.Plan;
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

    /** Acción de un botón: devuelve el mensaje de éxito o lanza una excepción del negocio. */
    @FunctionalInterface
    private interface Accion {
        String ejecutar() throws SocioNoEncontradoException, CupoLlenoException;
    }

    public VentanaGimnasio(Sistema sistema) {
        this.sistema = sistema;

        setTitle("SISTEMA DE TURNOS - GYM");
        setSize(700, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestanias = new JTabbedPane();
        pestanias.addTab("Socios y Profesores", crearPanelPersonas());
        pestanias.addTab("Planes", crearPanelPlanes());
        pestanias.addTab("Inscripciones", crearPanelInscripciones());
        pestanias.addTab("Turnos", crearPanelTurnos());
        pestanias.addTab("Reservas", crearPanelReservas());
        add(pestanias);
    }

    // ---------- Manejo de errores y utilidades ----------

    private void ejecutar(Accion accion) {
        try {
            JOptionPane.showMessageDialog(this, accion.ejecutar());
        } catch (DateTimeParseException ex) {
            mostrarError("Formato de fecha u hora inválido (fecha AAAA-MM-DD, hora HH:MM).");
        } catch (NumberFormatException ex) {
            mostrarError("Hay un campo numérico con un valor inválido.");
        } catch (SocioNoEncontradoException | CupoLlenoException
                 | IllegalArgumentException | IllegalStateException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private JPanel formulario(String titulo) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        return panel;
    }

    private void agregarFila(JPanel panel, String etiqueta, JComponent campo) {
        panel.add(new JLabel(etiqueta));
        panel.add(campo);
    }

    private JPanel arriba(JPanel contenido) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(contenido, BorderLayout.NORTH);
        return contenedor;
    }

    private void limpiar(JTextField... campos) {
        for (JTextField campo : campos) campo.setText("");
    }

    /** Polimorfismo: sirve para cualquier Estado (de inscripción o de turno). */
    private String describir(Estado estado) {
        return "Estado: " + estado.getDescripcion();
    }

    // ---------- Pestaña 1: Socios y Profesores ----------

    private JPanel crearPanelPersonas() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel panelSocio = formulario("Registrar Socio");
        JTextField txtNomS = new JTextField();
        JTextField txtApeS = new JTextField();
        JTextField txtDniS = new JTextField();
        JTextField txtTelS = new JTextField();
        JTextField txtFecNac = new JTextField("AAAA-MM-DD");
        JTextField txtTelCont = new JTextField();
        JButton btnRegSocio = new JButton("Registrar Socio");
        agregarFila(panelSocio, "Nombre:", txtNomS);
        agregarFila(panelSocio, "Apellido:", txtApeS);
        agregarFila(panelSocio, "DNI:", txtDniS);
        agregarFila(panelSocio, "Teléfono:", txtTelS);
        agregarFila(panelSocio, "Fec. Nacimiento:", txtFecNac);
        agregarFila(panelSocio, "Tel. Contacto:", txtTelCont);
        panelSocio.add(new JLabel(""));
        panelSocio.add(btnRegSocio);

        btnRegSocio.addActionListener(e -> ejecutar(() -> {
            LocalDate fechaNac = LocalDate.parse(txtFecNac.getText().trim());
            sistema.registrarSocio(new Socio(txtNomS.getText().trim(), txtApeS.getText().trim(),
                    txtDniS.getText().trim(), txtTelS.getText().trim(),
                    fechaNac, txtTelCont.getText().trim()));
            limpiar(txtNomS, txtApeS, txtDniS, txtTelS, txtTelCont);
            return "Socio registrado con éxito.";
        }));

        JPanel panelProfe = formulario("Registrar Profesor");
        JTextField txtNomP = new JTextField();
        JTextField txtApeP = new JTextField();
        JTextField txtDniP = new JTextField();
        JTextField txtTelP = new JTextField();
        JButton btnRegProfe = new JButton("Registrar Profesor");
        agregarFila(panelProfe, "Nombre:", txtNomP);
        agregarFila(panelProfe, "Apellido:", txtApeP);
        agregarFila(panelProfe, "DNI:", txtDniP);
        agregarFila(panelProfe, "Teléfono:", txtTelP);
        panelProfe.add(new JLabel(""));
        panelProfe.add(btnRegProfe);

        btnRegProfe.addActionListener(e -> ejecutar(() -> {
            sistema.registrarProfesor(new Profesor(txtNomP.getText().trim(), txtApeP.getText().trim(),
                    txtDniP.getText().trim(), txtTelP.getText().trim()));
            limpiar(txtNomP, txtApeP, txtDniP, txtTelP);
            return "Profesor registrado con éxito.";
        }));

        panel.add(arriba(panelSocio));
        panel.add(arriba(panelProfe));
        return panel;
    }

    // ---------- Pestaña 2: Planes ----------

    private JPanel crearPanelPlanes() {
        JPanel panel = formulario("Registrar Plan");
        JTextField txtNombre = new JTextField();
        JTextField txtFrecuencia = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtDuracion = new JTextField();
        JButton btnRegistrar = new JButton("Registrar Plan");
        agregarFila(panel, "Nombre:", txtNombre);
        agregarFila(panel, "Frecuencia semanal:", txtFrecuencia);
        agregarFila(panel, "Precio:", txtPrecio);
        agregarFila(panel, "Duración (meses):", txtDuracion);
        panel.add(new JLabel(""));
        panel.add(btnRegistrar);

        btnRegistrar.addActionListener(e -> ejecutar(() -> {
            sistema.registrarPlan(new Plan(txtNombre.getText().trim(),
                    Integer.parseInt(txtFrecuencia.getText().trim()),
                    Double.parseDouble(txtPrecio.getText().trim()),
                    Integer.parseInt(txtDuracion.getText().trim())));
            limpiar(txtNombre, txtFrecuencia, txtPrecio, txtDuracion);
            return "Plan registrado con éxito.";
        }));

        return arriba(panel);
    }

    // ---------- Pestaña 3: Inscripciones (contratar un plan) ----------

    private JPanel crearPanelInscripciones() {
        JPanel panel = formulario("Inscribir un socio a un plan");
        JTextField txtDni = new JTextField();
        JTextField txtPlan = new JTextField();
        JTextField txtInicio = new JTextField(LocalDate.now().toString());
        JTextField txtDescuento = new JTextField("0");
        JButton btnInscribir = new JButton("Contratar Plan");
        JButton btnActivo = new JButton("¿Socio activo?");
        JButton btnBaja = new JButton("Dar de baja al socio");
        agregarFila(panel, "DNI Socio:", txtDni);
        agregarFila(panel, "Nombre del Plan:", txtPlan);
        agregarFila(panel, "Fecha inicio (AAAA-MM-DD):", txtInicio);
        agregarFila(panel, "Descuento (%):", txtDescuento);
        panel.add(btnInscribir);
        panel.add(btnActivo);
        panel.add(new JLabel(""));
        panel.add(btnBaja);

        btnInscribir.addActionListener(e -> ejecutar(() -> {
            Inscripcion nueva = sistema.registrarInscripcion(txtDni.getText().trim(),
                    txtPlan.getText().trim(),
                    LocalDate.parse(txtInicio.getText().trim()),
                    Integer.parseInt(txtDescuento.getText().trim()));
            return "Inscripción registrada. Precio final: $"
                    + String.format("%.2f", nueva.calcularPrecioFinal())
                    + " - Vence el " + nueva.getFechaFin()
                    + " - " + describir(nueva.getEstado());
        }));

        btnActivo.addActionListener(e -> ejecutar(() ->
                sistema.socioActivo(txtDni.getText().trim())
                        ? "El socio tiene una inscripción vigente."
                        : "El socio NO tiene una inscripción vigente."));

        btnBaja.addActionListener(e -> ejecutar(() -> {
            sistema.darDeBajaSocio(txtDni.getText().trim());
            return "Socio dado de baja: sus inscripciones fueron canceladas.";
        }));

        return arriba(panel);
    }

    // ---------- Pestaña 4: Turnos ----------

    private JPanel crearPanelTurnos() {
        JPanel panel = formulario("Crear Nuevo Turno");
        JTextField txtDniProf = new JTextField();
        JTextField txtFecha = new JTextField(LocalDate.now().plusDays(1).toString());
        JTextField txtHora = new JTextField("HH:MM");
        JTextField txtCupo = new JTextField();
        JButton btnCrear = new JButton("Registrar Turno");
        agregarFila(panel, "DNI Profesor a cargo:", txtDniProf);
        agregarFila(panel, "Fecha (AAAA-MM-DD):", txtFecha);
        agregarFila(panel, "Horario (HH:MM):", txtHora);
        agregarFila(panel, "Cupo máximo:", txtCupo);
        panel.add(new JLabel(""));
        panel.add(btnCrear);

        btnCrear.addActionListener(e -> ejecutar(() -> {
            Turno nuevo = sistema.registrarTurno(txtDniProf.getText().trim(),
                    LocalDate.parse(txtFecha.getText().trim()),
                    LocalTime.parse(txtHora.getText().trim()),
                    Integer.parseInt(txtCupo.getText().trim()));
            limpiar(txtDniProf, txtCupo);
            return "Turno creado. Su ID es " + nuevo.getId();
        }));

        return arriba(panel);
    }

    // ---------- Pestaña 5: Reservas ----------

    private JPanel crearPanelReservas() {
        JPanel panel = formulario("Operaciones sobre turnos");
        JTextField txtDniSocio = new JTextField();
        JTextField txtIdTurno = new JTextField();
        JComboBox<String> comboAsistio = new JComboBox<>(new String[]{"Asistió", "Ausente"});
        JButton btnReservar = new JButton("Reservar Turno");
        JButton btnAsistencia = new JButton("Registrar Asistencia");
        JButton btnCancelar = new JButton("Cancelar Reserva");
        agregarFila(panel, "DNI Socio:", txtDniSocio);
        agregarFila(panel, "ID Turno:", txtIdTurno);
        agregarFila(panel, "Condición de asistencia:", comboAsistio);
        panel.add(btnReservar);
        panel.add(btnAsistencia);
        panel.add(btnCancelar);

        btnReservar.addActionListener(e -> ejecutar(() -> {
            sistema.reservarTurno(txtDniSocio.getText().trim(),
                    Integer.parseInt(txtIdTurno.getText().trim()));
            return "Turno reservado con éxito.";
        }));

        btnAsistencia.addActionListener(e -> ejecutar(() -> {
            sistema.registrarAsistenciaTurno(txtDniSocio.getText().trim(),
                    Integer.parseInt(txtIdTurno.getText().trim()),
                    comboAsistio.getSelectedIndex() == 0);
            return "Asistencia guardada correctamente.";
        }));

        btnCancelar.addActionListener(e -> ejecutar(() -> {
            sistema.cancelarReservaTurno(txtDniSocio.getText().trim(),
                    Integer.parseInt(txtIdTurno.getText().trim()));
            return "Reserva cancelada. Cupo liberado.";
        }));

        return arriba(panel);
    }
}