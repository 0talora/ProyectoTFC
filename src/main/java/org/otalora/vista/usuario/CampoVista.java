package org.otalora.vista.usuario;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
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
import org.otalora.controlador.ReservaControlador;
import org.otalora.modelo.Campo;
import org.otalora.modelo.Reserva;
import org.otalora.vista.usuario.ReservasVista.ButtonEditor;
import org.otalora.vista.usuario.ReservasVista.ButtonRenderer;

public class CampoVista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenu mnMenu;
	private JTable tablaCampos;
	private List<Campo> campos;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CampoVista frame = new CampoVista();
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
	public CampoVista() {
		setResizable(false);

		setTitle("Recon Reservas-Campos");
		setSize(881, 506);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);

		crearTablaCampos();
		setJMenuBar(createMenuBar());

		setVisible(true);
	}
	private void crearTablaCampos() {
		CampoControlador campoControlador = new CampoControlador();
		campos = campoControlador.obtenerCampos();

		String[] columnas = { "Nombre", "Comunidad", "Ciudad", "Código Postal","Calle","Telefono" };

		DefaultTableModel model = new DefaultTableModel(columnas, 0) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};

		for (Campo c : campos) {
			Object[] fila = {c.getNombre(),c.getComunidad(),c.getCiudad(),c.getCodigoPostal(),c.getCalle(),c.getNumTelefono()};
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
		misReservas.addActionListener(e->{
			ReservasVista frame = new ReservasVista();
			frame.setVisible(true); 
			dispose();
		});

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

}
