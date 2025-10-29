package org.otalora.vista.admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.otalora.controlador.CampoControlador;
import org.otalora.modelo.Campo;
import org.otalora.vista.usuario.HomeVista;
import org.otalora.vista.admin.AdminPerfilVista;
import org.otalora.vista.usuario.ReservasVista;

public class AdminHomeVista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaCampos;
	private List<Campo> campos;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(() -> {
//			try {
//				AdminHomeVista frame = new AdminHomeVista();
//				frame.setVisible(true);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//	}

	public AdminHomeVista() {
		setTitle("Recon Reservas - Admin - Campos");
		setSize(881, 506);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);

		crearTablaCampos();
		setJMenuBar(createMenuBar());
	}

	private void crearTablaCampos() {
		CampoControlador campoControlador = new CampoControlador();
		campos = campoControlador.obtenerCamposAdministrador();

		String[] columnas = { "Nombre", "Comunidad", "Ciudad", "Código Postal", "Calle"};

		DefaultTableModel model = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (Campo c : campos) {
			Object[] fila = {
				c.getNombre(),
				c.getComunidad(),
				c.getCiudad(),
				c.getCodigoPostal(),
				c.getCalle(),
			};
			model.addRow(fila);
		}

		tablaCampos = new JTable(model);
		tablaCampos.setRowHeight(35);
		tablaCampos.setShowVerticalLines(false);
		tablaCampos.setRowSelectionAllowed(false);

		ajustarAnchoColumnas(tablaCampos);

		JScrollPane scroll = new JScrollPane(tablaCampos);
		contentPane.add(scroll, BorderLayout.CENTER);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu mnMenu = new JMenu("Menu");
		mnMenu.setMnemonic(KeyEvent.VK_F);
		mnMenu.getAccessibleContext().setAccessibleDescription("Menu");
		menuBar.add(mnMenu);

		JMenuItem miPerfil = new JMenuItem("Mi Perfil");
		miPerfil.setIcon(new ImageIcon(getClass().getResource("/imagenes/avatar.png")));
		mnMenu.add(miPerfil);
		miPerfil.addActionListener(e -> {
			AdminPerfilVista dialog = new AdminPerfilVista(this);
			dialog.setVisible(true);
		});

		JMenuItem eventos = new JMenuItem("Mis Eventos");
		eventos.setIcon(new ImageIcon(getClass().getResource("/imagenes/eventos.png")));
		mnMenu.add(eventos);
		eventos.addActionListener(e -> {
			AdminEventosVista frame = new AdminEventosVista();
			frame.setVisible(true);
			dispose();
		});

		JMenuItem campos = new JMenuItem("Mis Campos");
		campos.setIcon(new ImageIcon(getClass().getResource("/imagenes/Campo.png")));
		mnMenu.add(campos);
		
		JMenuItem crearEvento = new JMenuItem("Crear Evento");
		crearEvento.setIcon(new ImageIcon(getClass().getResource("/imagenes/evento.png")));
		mnMenu.add(crearEvento);
		crearEvento.addActionListener(e -> {
			CrearEventoAdminVista dialog = new CrearEventoAdminVista();
			dialog.setVisible(true);
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
			Component headerComp = headerRenderer.getTableCellRendererComponent(
					table,
					table.getColumnModel().getColumn(col).getHeaderValue(),
					false, false, 0, col);
			anchoMaximo = Math.max(anchoMaximo, headerComp.getPreferredSize().width + 10);
			table.getColumnModel().getColumn(col).setPreferredWidth(anchoMaximo);
		}
	}
}
