package org.otalora.vista.usuario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.otalora.controlador.ReservaControlador;
import org.otalora.modelo.Reserva;

public class ReservasVista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenu mnMenu;
	private JTable tablaReservas;

	private List<Reserva> reservas;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ReservasVista frame = new ReservasVista();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ReservasVista() {
		setResizable(false);

		setTitle("Recon Reservas-Mis Reservas");
		setSize(1439, 488);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);

		crearTablaReservas();
		setJMenuBar(createMenuBar());

		setVisible(true);
	}

	private void crearTablaReservas() {
		ReservaControlador reservaControlador = new ReservaControlador();
		reservas = reservaControlador.verReservas();

		String[] columnas = { "Nombre Evento", "Fecha", "Descripción", "Numero de Personas", "Nombre Campo", "Ciudad",
				"Calle", "Código Postal", "estado", "Cancelar Reserva" };

		DefaultTableModel model = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 9;
			}
		};

		for (Reserva r : reservas) {
			Object[] fila = { r.getEvento().getNombre(), r.getFechaHoraReserva(), r.getEvento().getDescripcion(),
					r.getNumeroPersonas(), r.getEvento().getIdCampo().getNombre(),
					r.getEvento().getIdCampo().getCiudad(), r.getEvento().getIdCampo().getCalle(),
					r.getEvento().getIdCampo().getCodigoPostal(), r.getEstado(), "Cancelar" };
			model.addRow(fila);
		}

		tablaReservas = new JTable(model);
		tablaReservas.setRowHeight(35);
		tablaReservas.setShowVerticalLines(false);
		tablaReservas.setRowSelectionAllowed(false);
		tablaReservas.getColumn("Cancelar Reserva").setCellRenderer(new ButtonRenderer());
		tablaReservas.getColumn("Cancelar Reserva")
				.setCellEditor(new ButtonEditor(new JCheckBox(), tablaReservas, reservas));

		ajustarAnchoColumnas(tablaReservas);

		JScrollPane scroll = new JScrollPane(tablaReservas);
		contentPane.add(scroll, BorderLayout.CENTER);
	}

	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		mnMenu = new JMenu("Menu");
		mnMenu.setMnemonic(KeyEvent.VK_F);
		mnMenu.getAccessibleContext().setAccessibleDescription("Menu");
		menuBar.add(mnMenu);

		JMenuItem miPerfil = new JMenuItem("Mi Perfil");
		miPerfil.setIcon(new ImageIcon(getClass().getResource("/imagenes/avatar.png")));
		mnMenu.add(miPerfil);
		miPerfil.addActionListener(e -> {
			PerfilVista dialog = new PerfilVista(this);
			dialog.setVisible(true);
		});

		JMenuItem misReservas = new JMenuItem("Mis Reservas");
		misReservas.setIcon(new ImageIcon(getClass().getResource("/imagenes/misReservas.png")));
		mnMenu.add(misReservas);

		JMenuItem eventos = new JMenuItem("Eventos");
		eventos.setIcon(new ImageIcon(getClass().getResource("/imagenes/eventos.png")));
		mnMenu.add(eventos);
		eventos.addActionListener(e -> {
			HomeVista frame = new HomeVista();
			frame.setVisible(true);
			dispose();
		});

		JMenuItem campos = new JMenuItem("Campos");
		campos.setIcon(new ImageIcon(getClass().getResource("/imagenes/Campo.png")));
		mnMenu.add(campos);
		campos.addActionListener(e -> {
			CampoVista frame = new CampoVista();
			frame.setVisible(true);
			dispose();
		});

		return menuBar;
	}

	private void ajustarAnchoColumnas(JTable table) {
		for (int col = 0; col < table.getColumnCount(); col++) {
			int anchoMaximo = 50;
			for (int fila = 0; fila < table.getRowCount(); fila++) {
				TableCellRenderer renderer = table.getCellRenderer(fila, col);
				Component comp = table.prepareRenderer(renderer, fila, col);
				anchoMaximo = Math.max(comp.getPreferredSize().width + 10, anchoMaximo);
			}

			TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
			Component headerComp = headerRenderer.getTableCellRendererComponent(table,
					table.getColumnModel().getColumn(col).getHeaderValue(), false, false, 0, col);
			anchoMaximo = Math.max(anchoMaximo, headerComp.getPreferredSize().width + 10);

			table.getColumnModel().getColumn(col).setPreferredWidth(anchoMaximo);
		}
	}

	// esto controla el aspecto del boton
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value == null ? "Cancelar" : value.toString());

			Reserva reserva = reservas.get(row);
			LocalDateTime fechaEvento = reserva.getEvento().getFechaHora();
			LocalDateTime ahora = LocalDateTime.now();

			if (fechaEvento.isBefore(ahora) || reserva.getEstado().equals("cancelada")) {
				setEnabled(false);
				setBackground(Color.LIGHT_GRAY);
				setForeground(Color.DARK_GRAY);
				setText("Cancelar");
			} else {
				setEnabled(true);
				setBackground(new JButton().getBackground());
				setForeground(Color.BLACK);
				setText("Cancelar");
			}

			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;
		private String label;
		private boolean clicked;
		private int row;
		private JTable table;
		private List<Reserva> reservas;

		ReservaControlador reservaControlador = new ReservaControlador();

		public ButtonEditor(JCheckBox checkBox, JTable table, List<Reserva> reservas) {
			super(checkBox);
			this.table = table;
			this.reservas = reservas;
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(e -> {
				fireEditingStopped();
				Reserva reservaSeleccionada = reservas.get(row);
				LocalDateTime fechaEvento = reservaSeleccionada.getEvento().getFechaHora();
				LocalDateTime fechaActual = LocalDateTime.now();
				if (fechaEvento.isBefore(fechaActual)) {
					JOptionPane.showMessageDialog(button, "Este evento ya ha sido", "Evento Finalizado",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (fechaEvento.minusHours(24).isBefore(fechaActual)) {
					JOptionPane.showMessageDialog(button,
							"No se puede cancelar una reserva con menos de 24 horas de antelación.",
							"Cancelación no permitida", JOptionPane.WARNING_MESSAGE);
					return;
				} else if (reservaSeleccionada.getEstado().equals("cancelada")) {
					JOptionPane.showMessageDialog(button, "Esta reserva ya está cancelada", "Reserva Cancelada",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					if (reservaControlador.cancelarReserva(reservaSeleccionada)) {
						JOptionPane.showMessageDialog(button, "Se ha cancelado tu reserva correctamente",
								"Reserva Cancelada", JOptionPane.INFORMATION_MESSAGE);
						refrescarTablaReservas();
					}
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.row = row;
			label = value == null ? "Cancelar" : value.toString();
			button.setText(label);
			clicked = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			clicked = false;
			return label;
		}

		@Override
		public boolean stopCellEditing() {
			clicked = false;
			return super.stopCellEditing();
		}

		@Override
		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	public void refrescarTablaReservas() {
		contentPane.removeAll();
		crearTablaReservas();
		contentPane.revalidate();
		contentPane.repaint();
	}

}
