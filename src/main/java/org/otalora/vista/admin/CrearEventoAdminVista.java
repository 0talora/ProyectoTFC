package org.otalora.vista.admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import org.otalora.controlador.CampoControlador;
import org.otalora.controlador.EventoControlador;
import org.otalora.modelo.Campo;
import org.otalora.modelo.Evento;

public class CrearEventoAdminVista extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JTextField nombreEventoField;
    private JComboBox<String> campoComboBox;
    private List<Campo> camposAdmin;
    private JSpinner fechaSpinner;
    private JSpinner plazasSpinner;
    private JTextArea descripcionArea;
    private Evento evento;

    public CrearEventoAdminVista() {
        setTitle("Crear Evento");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(""));
        setBounds(100, 100, 420, 400);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null); // Absolute layout
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        CampoControlador campoControlador = new CampoControlador();
        camposAdmin = campoControlador.obtenerCamposAdministrador();

        initComponentes();
        initBotones();
    }

    private void initComponentes() {
        JLabel lblNombreEvento = new JLabel("Nombre Evento:");
        lblNombreEvento.setBounds(20, 20, 120, 20);
        contentPanel.add(lblNombreEvento);

        nombreEventoField = new JTextField();
        nombreEventoField.setBounds(150, 20, 230, 25);
        contentPanel.add(nombreEventoField);

        JLabel lblCampo = new JLabel("Campo:");
        lblCampo.setBounds(20, 60, 120, 20);
        contentPanel.add(lblCampo);

        campoComboBox = new JComboBox<>();
        String[] nombresCampos = camposAdmin.stream().map(Campo::getNombre).toArray(String[]::new);
        campoComboBox.setModel(new DefaultComboBoxModel<>(nombresCampos));
        campoComboBox.setBounds(150, 60, 230, 25);
        contentPanel.add(campoComboBox);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(20, 100, 120, 20);
        contentPanel.add(lblFecha);

        fechaSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        fechaSpinner.setBounds(150, 100, 230, 25);
        fechaSpinner.setEditor(new JSpinner.DateEditor(fechaSpinner, "dd/MM/yyyy HH:mm"));
        contentPanel.add(fechaSpinner);

        JLabel lblPlazas = new JLabel("Número de plazas:");
        lblPlazas.setBounds(20, 140, 120, 20);
        contentPanel.add(lblPlazas);

        plazasSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        plazasSpinner.setBounds(150, 140, 230, 25);
        contentPanel.add(plazasSpinner);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(20, 180, 120, 20);
        contentPanel.add(lblDescripcion);

        descripcionArea = new JTextArea();
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(descripcionArea);
        scrollDescripcion.setBounds(150, 180, 230, 120);
        contentPanel.add(scrollDescripcion);
    }

    private void initBotones() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton crearBtn = new JButton("Crear Evento");
        crearBtn.addActionListener(e -> {
            if (validarCampos()) {
                Campo campoSeleccionado = getCampoSeleccionado();
                LocalDateTime fechaEvento = ((Date) fechaSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                String nombreEvento = getNombreEvento();
                int numeroPlazas = getNumeroPlazas();
                String descripcion = getDescripcion();

                evento = new Evento(campoSeleccionado,fechaEvento,nombreEvento,numeroPlazas,numeroPlazas,descripcion);
                
                EventoControlador eventoControlador = new EventoControlador();
                if(eventoControlador.crearEvento(evento)) {
					JOptionPane.showMessageDialog(crearBtn, "El evento ha sido creado",
							"Evento Creado", JOptionPane.INFORMATION_MESSAGE);
                	dispose();
                }else{
					JOptionPane.showMessageDialog(crearBtn, "El evento no se ha podido crear",
							"Evento No Creado", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
        buttonPane.add(crearBtn);
        getRootPane().setDefaultButton(crearBtn);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> {
        	dispose();
        });
        buttonPane.add(cancelButton);
    }

    private boolean validarCampos() {
        if (nombreEventoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del evento es obligatorio.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (campoComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un campo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Date fechaSeleccionada = (Date) fechaSpinner.getValue();
        if (fechaSeleccionada.before(new Date())) {
            JOptionPane.showMessageDialog(this, "La fecha del evento debe ser en el futuro.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public String getNombreEvento() {
        return nombreEventoField.getText().trim();
    }

    public Campo getCampoSeleccionado() {
        int index = campoComboBox.getSelectedIndex();
        if (index >= 0) {
            return camposAdmin.get(index);
        }
        return null;
    }

    public Date getFechaEvento() {
        return (Date) fechaSpinner.getValue();
    }

    public int getNumeroPlazas() {
        return (Integer) plazasSpinner.getValue();
    }

    public String getDescripcion() {
        return descripcionArea.getText().trim();
    }
}
