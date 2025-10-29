                   package org.otalora.vista.comun;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.otalora.controlador.CreacionCuentaControlador;
import org.otalora.dao.impl.UsuarioDAO;
import org.otalora.modelo.Usuario;
import org.otalora.seguridad.VerificacionCorreo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreacionCuentaVista extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField nickField;
	private JTextField nombreField;
	private JTextField apellidoField;
	private JTextField correoField;
	private JPasswordField passwordField;
	private JLabel errorLabel;


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			CreacionCuentaVista dialog = new CreacionCuentaVista();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public CreacionCuentaVista() {
		setTitle("Recon Reservas-Creación de Cuenta");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(""));
		setSize(428, 268);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
		contentPanel.setLayout(null);
		{
			JLabel repetirClaveLabel = new JLabel("Contraseña");
			repetirClaveLabel.setBounds(58, 115, 71, 14);
			contentPanel.add(repetirClaveLabel);
		}
		{
			JLabel correoLabel = new JLabel("Correo eletrónico");
			correoLabel.setBounds(20, 89, 109, 14);
			contentPanel.add(correoLabel);
		}
		{
			JLabel apellidoLabel = new JLabel("Apellido");
			apellidoLabel.setBounds(77, 63, 52, 14);
			contentPanel.add(apellidoLabel);
		}
		{
			JLabel nombreLabel = new JLabel("Nombre");
			nombreLabel.setBounds(77, 40, 52, 14);
			contentPanel.add(nombreLabel);
		}
		{
			JLabel nickLabel = new JLabel("Nickname");
			nickLabel.setBounds(66, 14, 63, 14);
			contentPanel.add(nickLabel);
		}
		
		nickField = new JTextField();
		nickField.setBounds(141, 11, 249, 20);
		contentPanel.add(nickField);
		nickField.setColumns(10);
		
		nombreField = new JTextField();
		nombreField.setBounds(141, 36, 249, 20);
		contentPanel.add(nombreField);
		nombreField.setColumns(10);
		
		apellidoField = new JTextField();
		apellidoField.setBounds(141, 60, 249, 20);
		contentPanel.add(apellidoField);
		apellidoField.setColumns(10);
		
		correoField = new JTextField();
		correoField.setBounds(141, 86, 249, 20);
		contentPanel.add(correoField);
		correoField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(141, 111, 249, 20);
		contentPanel.add(passwordField);
		
		// en el constructor, después de instanciar correoField:
		errorLabel = new JLabel("");  // Inicializa el JLabel
		errorLabel.setForeground(Color.RED);  // Coloca el color rojo para los errores
		errorLabel.setBounds(143, 175, 249, 20);  // Ajusta la posición del errorLabel para que no se sobreponga al passwordField
		contentPanel.add(errorLabel);

		
		JCheckBox mostrarClaveCheckBox = new JCheckBox("Mostrar contraseña");
		mostrarClaveCheckBox.setBounds(141, 138, 153, 20);
        mostrarClaveCheckBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (mostrarClaveCheckBox.isSelected()) {
                	passwordField.setEchoChar((char)0);
                } else {
                	passwordField.setEchoChar('●');
                }
            }
        });		
        contentPanel.add(mostrarClaveCheckBox);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton CrearCuentaButton = new JButton("Crear Cuenta");
				CrearCuentaButton.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        String nick = nickField.getText().trim();
				        String nombre = nombreField.getText().trim();
				        String apellido = apellidoField.getText().trim();
				        String correo = correoField.getText().trim();
				        String clave = new String(passwordField.getPassword()).trim();

				        Usuario usuario = new Usuario(nick, nombre, apellido, correo, clave);

				        // Llamamos al método de creación de cuenta
				        Object resultado = CreacionCuentaControlador.crearCuenta(usuario, CreacionCuentaVista.this);

				        if (resultado instanceof String) {
				            // Si el resultado es un String, significa que hay errores
				            String mensajeError = (String) resultado;  // Obtiene el mensaje de error
				            // Muestra un cuadro de diálogo con el mensaje de error
				            JOptionPane.showMessageDialog(CreacionCuentaVista.this, mensajeError, "Error de Creación de Cuenta", JOptionPane.ERROR_MESSAGE);
				        } else if (resultado instanceof Boolean && (Boolean) resultado) {
				            // Si el resultado es true, la cuenta se creó correctamente
				            dispose(); // Cierra la ventana si la cuenta fue creada
				        } else {
				            // Si el resultado es false (falló la creación de la cuenta)
				            String mensajeError = "Hubo un error al crear la cuenta. Intenta nuevamente.";
				            JOptionPane.showMessageDialog(CreacionCuentaVista.this, mensajeError, "Error de Creación de Cuenta", JOptionPane.ERROR_MESSAGE);
				        }
				    }
				});
				CrearCuentaButton.setActionCommand("OK");
				buttonPane.add(CrearCuentaButton);
				getRootPane().setDefaultButton(CrearCuentaButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
