package org.otalora.vista.admin;

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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.otalora.controlador.EventoControlador;
import org.otalora.modelo.Evento;

public class AdminEventosVista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenu mnMenu;
	private JTable tablaEventos;

	private List<Evento> eventos;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(() -> {
//			try {
//				AdminEventosVista frame = new AdminEventosVista();
//				frame.setVisible(true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//	}

	public AdminEventosVista() {

		setTitle("Recon Reservas-Admin-Eventos");
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
		String[] columnas = { "Nombre Evento", "Fecha", "Plazas Reservadas","Nombre Campo","Cancelar Evento"};

		DefaultTableModel model = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column==4;
			}
		};
		EventoControlador controlador = new EventoControlador();
		eventos = controlador.obtenerEventosAdministrador();
		if (eventos != null) {
			for (Evento e : eventos) {
				model.addRow(new Object[] { e.getNombre(), e.getFechaHora(), e.getNumeroPlazas()-e.getNumeroPlazasRestantes(),e.getIdCampo().getNombre(),"Cancelar" });
			}

		}
		tablaEventos = new JTable(model);
		tablaEventos.setRowHeight(35);
		tablaEventos.setShowVerticalLines(false);
		tablaEventos.setRowSelectionAllowed(false);
		tablaEventos.getColumn("Cancelar Evento").setCellRenderer(new ButtonRenderer());
		tablaEventos.getColumn("Cancelar Evento").setCellEditor(new ButtonEditor(new JCheckBox(), tablaEventos));

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
			AdminPerfilVista dialog = new AdminPerfilVista(this);
			dialog.setVisible(true);
			dialog.setAlwaysOnTop(true);
		});

		JMenuItem eventos = new JMenuItem("Mis Eventos");
		eventos.setIcon(new ImageIcon(getClass().getResource("/imagenes/eventos.png")));
		mnMenu.add(eventos);
		eventos.addActionListener(e -> {
			refrescarTablaEventos();
		});
		
		JMenuItem campos = new JMenuItem("Mis Campos");
		campos.setIcon(new ImageIcon(getClass().getResource("/imagenes/Campo.png")));
		mnMenu.add(campos);
		campos.addActionListener(e->{
			AdminHomeVista frame = new AdminHomeVista();
			frame.setVisible(true);
			dispose();
		});
		
		JMenuItem crearEvento = new JMenuItem("Crear Evento");
		crearEvento.setIcon(new ImageIcon(AdminHomeVista.class.getResource("/imagenes/evento.png")));
		mnMenu.add(crearEvento);
		crearEvento.addActionListener(e -> {
			CrearEventoAdminVista dialog = new CrearEventoAdminVista();
			dialog.setVisible(true);
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
			Component headerComp = headerRenderer.getTableCellRendererComponent(table,table.getColumnModel().getColumn(col).getHeaderValue(), false, false, 0, col);
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

			setText(value == null ? "Cancelar" : value.toString());

			Evento evento = eventos.get(row);
			LocalDateTime fechaEvento = evento.getFechaHora();
			LocalDateTime ahora = LocalDateTime.now();

		       if (evento.isCancelado()) {
		            setEnabled(false);
		            setBackground(Color.GRAY);
		            setForeground(Color.WHITE);
		            setText("Cancelado");
		        } else if (fechaEvento.isBefore(ahora)) {
		            setEnabled(false);
		            setBackground(Color.LIGHT_GRAY);
		            setForeground(Color.DARK_GRAY);
		            setText("Finalizado");
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

		public ButtonEditor(JCheckBox checkBox, JTable table) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(e -> {
				
				fireEditingStopped();
				Evento eventoSeleccionado = eventos.get(row);
				LocalDateTime fechaEvento = eventoSeleccionado.getFechaHora();
				LocalDateTime fechaActual = LocalDateTime.now();
				
	            if (eventoSeleccionado.isCancelado()) {
	                JOptionPane.showMessageDialog(button, "Este evento ya está cancelado, no se puede cancelar de nuevo.",
	                        "Evento Cancelado", JOptionPane.ERROR_MESSAGE);
	            } else if (fechaEvento.isBefore(fechaActual)) {
	                JOptionPane.showMessageDialog(button, "No se puede cancelar un evento una vez ya se ha realizado",
	                        "Evento Realizado", JOptionPane.ERROR_MESSAGE);
	            } else {
					EventoControlador eventoControlador= new EventoControlador();
					if (eventoControlador.cancelarEvento(eventoSeleccionado)) {
						JOptionPane.showMessageDialog(button, "El evento ha sido cancelado",
								"Evento Cancelado", JOptionPane.INFORMATION_MESSAGE);
						refrescarTablaEventos();
					}else {
						JOptionPane.showMessageDialog(button, "Ha habido un error al cancelar el evento",
								"Evento No Cancelado", JOptionPane.ERROR_MESSAGE);
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

	public void refrescarTablaEventos() {
		contentPane.removeAll();
		cargarTablaConBotones();
		contentPane.revalidate();
		contentPane.repaint();
	}

}
