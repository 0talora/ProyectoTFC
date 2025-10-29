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

import org.otalora.controlador.EventoControlador;
import org.otalora.modelo.Evento;

public class HomeVista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenu mnMenu;
	private JTable tablaEventos;

	private List<Evento> eventos;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(() -> {
//			try {
//				HomeVista frame = new HomeVista();
//				frame.setVisible(true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//	}

	public HomeVista() {
		
		

		setTitle("Recon Reservas-Eventos");
		setBounds(0, 0, 1570, 721);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);

		setJMenuBar(createMenuBar());
		cargarTablaConBotones();

		setVisible(true);
	}

	private void cargarTablaConBotones() {
		String[] columnas = { "Nombre Evento", "Fecha", "Plazas Totales", "Plazas Restantes", "Descripción",
				"Nombre Campo", "Ciudad", "Calle", "Código Postal", "Reservar" };

		DefaultTableModel model = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 9;
			}
		};
		EventoControlador controlador = new EventoControlador();
		eventos = controlador.obtenerEventos();
		if (eventos != null) {
			for (Evento e : eventos) {
				model.addRow(new Object[] { e.getNombre(), e.getFechaHora(), e.getNumeroPlazas(),
						e.getNumeroPlazasRestantes(), e.getDescripcion(), e.getIdCampo().getNombre(),
						e.getIdCampo().getCiudad(), e.getIdCampo().getCalle(), e.getIdCampo().getCodigoPostal(),
						"Reservar" });
			}

		}
		tablaEventos = new JTable(model);
		tablaEventos.setRowHeight(35);
		tablaEventos.setShowVerticalLines(false);
		tablaEventos.setRowSelectionAllowed(false);
		tablaEventos.getColumn("Reservar").setCellRenderer(new ButtonRenderer());
		tablaEventos.getColumn("Reservar").setCellEditor(new ButtonEditor(new JCheckBox(), tablaEventos));

		ajustarAnchoColumnas(tablaEventos);

		JScrollPane scrollPane = new JScrollPane(tablaEventos);
		contentPane.add(scrollPane, BorderLayout.CENTER);
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
			dialog.setAlwaysOnTop(true);
		});

		JMenuItem misReservas = new JMenuItem("Mis Reservas");
		misReservas.setIcon(new ImageIcon(getClass().getResource("/imagenes/misReservas.png")));
		mnMenu.add(misReservas);
		misReservas.addActionListener(e -> {
			ReservasVista frame = new ReservasVista();
			frame.setVisible(true);
			dispose();
		});

		JMenuItem eventos = new JMenuItem("Eventos");
		eventos.setIcon(new ImageIcon(getClass().getResource("/imagenes/eventos.png")));
		mnMenu.add(eventos);
		eventos.addActionListener(e -> {
			refrescarTablaEventos();
		});
		
		JMenuItem campos = new JMenuItem("Campos");
		campos.setIcon(new ImageIcon(getClass().getResource("/imagenes/Campo.png")));
		mnMenu.add(campos);
		campos.addActionListener(e->{
			CampoVista frame = new CampoVista();
			frame.setVisible(true);
			dispose();
		});

		return menuBar;
	}

	private void ajustarAnchoColumnas(JTable table) {
		for (int col = 0; col < table.getColumnCount(); col++) {
			int anchoMaximo = 50; // ancho mínimo por columna
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

	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			setText(value == null ? "Reservar" : value.toString());

			Evento evento = eventos.get(row);
			LocalDateTime fechaEvento = evento.getFechaHora();
			LocalDateTime ahora = LocalDateTime.now();

			if (fechaEvento.isBefore(ahora)) {
				setEnabled(false);
				setBackground(Color.LIGHT_GRAY);
				setForeground(Color.DARK_GRAY);
				setText("Finalizado");
			} else {
				setEnabled(true);
				setBackground(new JButton().getBackground());
				setForeground(Color.BLACK);
				setText("Reservar");
			}

			return this;
		}

	}

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;
		private String label;
		private boolean clicked;
		private int row;

		public ButtonEditor(JCheckBox checkBox, JTable table) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(e -> {
				fireEditingStopped();
				Evento eventoSeleccionado = eventos.get(row);
				LocalDateTime fechaReserva = eventoSeleccionado.getFechaHora();
				LocalDateTime fechaActual = LocalDateTime.now();
				if (fechaReserva.isAfter(fechaActual)) {
					ReservaDialog dialog = new ReservaDialog(HomeVista.this, eventoSeleccionado, HomeVista.this);
					dialog.setModal(true);
					dialog.setLocationRelativeTo(button);
					dialog.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(button, "Este evento ya ha finalizado.No se pueden hacer reservas",
							"Evento Finalizado", JOptionPane.ERROR_MESSAGE);
				}

			});
		}

		@Override
		public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.row = row;
			label = value == null ? "Reservar" : value.toString();
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

	public void refrescarTablaEventos() {
		contentPane.removeAll();
		cargarTablaConBotones();
		contentPane.revalidate();
		contentPane.repaint();
	}

}
