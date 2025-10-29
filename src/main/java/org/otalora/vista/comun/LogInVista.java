package org.otalora.vista.comun;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.otalora.controlador.LogInControlador;
import org.otalora.controlador.UsuarioSesion;
import org.otalora.vista.admin.AdminHomeVista;
import org.otalora.vista.usuario.HomeVista;

import java.awt.Color;

public class LogInVista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField UsuarioField;
	private JPasswordField ClaveField;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LogInVista frame = new LogInVista();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public LogInVista() {
		setResizable(false);
		setTitle("Recon Reservas-LogIn");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(365, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel UsuarioLabel = new JLabel("Usuario");
		UsuarioLabel.setBackground(new Color(0, 0, 0));
		UsuarioLabel.setForeground(new Color(0, 0, 0));
		UsuarioLabel.setBounds(87, 62, 61, 16);
		contentPane.add(UsuarioLabel);

		UsuarioField = new JTextField();
		UsuarioField.setBounds(160, 57, 130, 26);
		contentPane.add(UsuarioField);
		UsuarioField.setColumns(10);

		JLabel ClaveLabel = new JLabel("Contraseña");
		ClaveLabel.setBackground(new Color(0, 0, 0));
		ClaveLabel.setForeground(new Color(0, 0, 0));
		ClaveLabel.setBounds(69, 100, 71, 16);
		contentPane.add(ClaveLabel);

		ClaveField = new JPasswordField();
		ClaveField.setBounds(162, 95, 130, 26);
		contentPane.add(ClaveField);

		JTextArea lblErrorCuenta = new JTextArea("Cuenta no encontrada. Verifica la contraseña o usuario.\\nSi no tienes una cuenta debes crear una");
		lblErrorCuenta.setWrapStyleWord(true);
		lblErrorCuenta.setLineWrap(true);
		lblErrorCuenta.setEditable(false);
		lblErrorCuenta.setFocusable(false);
		lblErrorCuenta.setOpaque(false); // Fondo transparente como JLabel
		lblErrorCuenta.setForeground(new Color(255, 0, 0));
		lblErrorCuenta.setBounds(10, 11, 329, 45);
		lblErrorCuenta.setVisible(false);
		contentPane.add(lblErrorCuenta);

		// Añadimos el JCheckBox para mostrar/ocultar contraseña
		JCheckBox mostrarClaveCheckBox = new JCheckBox("Mostrar contraseña");
		mostrarClaveCheckBox.setBackground(new Color(255, 255, 255));
		mostrarClaveCheckBox.setForeground(new Color(0, 0, 0));
		mostrarClaveCheckBox.setBounds(162, 130, 153, 20);
		mostrarClaveCheckBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (mostrarClaveCheckBox.isSelected()) {
					ClaveField.setEchoChar((char) 0);
				} else {
					ClaveField.setEchoChar('●');
				}
			}
		});
		contentPane.add(mostrarClaveCheckBox);

		JButton InicioSesionButton = new JButton("Iniciar Sesión");
		InicioSesionButton.addActionListener(e -> {
			String nick = UsuarioField.getText().trim();
			String clave = new String(ClaveField.getPassword()).trim();

			System.out.println(nick);
			System.out.println(clave);

			LogInControlador logInControlador = new LogInControlador();

			if (logInControlador.logInUsuario(nick, clave)) {
				HomeVista homeVista = new HomeVista();
				homeVista.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				homeVista.setVisible(true);
				dispose();
			} else {
				lblErrorCuenta.setVisible(true);
			}
		});
		InicioSesionButton.setBounds(44, 185, 117, 29);
		contentPane.add(InicioSesionButton);

		JButton CrearCuentaButton = new JButton("Crear Cuenta");
		CrearCuentaButton.addActionListener(e -> {

			CreacionCuentaVista crearCuentaVista = new CreacionCuentaVista();
			crearCuentaVista.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			crearCuentaVista.setModal(true);
			crearCuentaVista.setVisible(true);
		});

		CrearCuentaButton.setBounds(195, 185, 117, 29);
		contentPane.add(CrearCuentaButton);

		JButton adminButton = new JButton("Admin");
		adminButton.setForeground(new Color(0, 0, 0));
		adminButton.addActionListener(e -> {
			String nick = UsuarioField.getText().trim();
			String clave = new String(ClaveField.getPassword()).trim();

			LogInControlador logInControlador = new LogInControlador();

			if (logInControlador.logInUsuario(nick, clave)) {
				if (UsuarioSesion.getInstancia().getUsuarioActual().isAdmin()) {
					AdminHomeVista frame = new AdminHomeVista();
					frame.setVisible(true);
					this.dispose();
				} else {
					lblErrorCuenta.setText("No eres administrador, no tienes acceso a esta ventana");
					lblErrorCuenta.setVisible(true);

				}
			} else {
				lblErrorCuenta.setVisible(true);
			}
		});
		adminButton.setBounds(79, 226, 203, 29);
		contentPane.add(adminButton);

	}
}