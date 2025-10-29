package org.otalora.vista.admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.otalora.controlador.CampoControlador;
import org.otalora.controlador.UsuarioSesion;
import org.otalora.modelo.Usuario;
import java.awt.Font;

public class AdminPerfilVista extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
//    public static void main(String[] args) {
//        try {
//            Usuario usuarioPrueba = new Usuario();
//            usuarioPrueba.setNick("potalora");
//            usuarioPrueba.setNombre("Pablo");
//            usuarioPrueba.setApellido("Otalora");
//            usuarioPrueba.setCorreo("potalora@example.com");
//
//            UsuarioSesion.getInstancia().setUsuarioActual(usuarioPrueba);
//
//            AdminPerfilVista dialog = new AdminPerfilVista(null);
//            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//            dialog.setVisible(true);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

	/**
	 * Create the dialog.
	 */
    public AdminPerfilVista(JFrame parent) {
        Usuario usuario = UsuarioSesion.getInstancia().getUsuarioActual();
        CampoControlador campoControlador = new CampoControlador();
        int cantidadCampos = campoControlador.contarCamposAdministrador();

        setTitle("Recon Reservas-Mi Perfil");
        setSize(400, 280);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JTextArea nombreLbl = new JTextArea("Nombre: " + usuario.getNombre());
        nombreLbl.setBounds(10, 11, 360, 30);
        nombreLbl.setWrapStyleWord(true);
        nombreLbl.setLineWrap(true);
        nombreLbl.setEditable(false);
        nombreLbl.setFocusable(false);
        nombreLbl.setOpaque(false);
        contentPanel.add(nombreLbl);

        JTextArea nickLbl = new JTextArea("Nick: " + usuario.getNick());
        nickLbl.setBounds(10, 50, 360, 30);
        nickLbl.setWrapStyleWord(true);
        nickLbl.setLineWrap(true);
        nickLbl.setEditable(false);
        nickLbl.setFocusable(false);
        nickLbl.setOpaque(false);
        contentPanel.add(nickLbl);

        JTextArea apellidoLbl = new JTextArea("Apellido: " + usuario.getApellido());
        apellidoLbl.setBounds(10, 90, 360, 30);
        apellidoLbl.setWrapStyleWord(true);
        apellidoLbl.setLineWrap(true);
        apellidoLbl.setEditable(false);
        apellidoLbl.setFocusable(false);
        apellidoLbl.setOpaque(false);
        contentPanel.add(apellidoLbl);

        JTextArea correoLbl = new JTextArea("Correo: " + usuario.getCorreo());
        correoLbl.setBounds(10, 130, 360, 40);
        correoLbl.setWrapStyleWord(true);
        correoLbl.setLineWrap(true);
        correoLbl.setEditable(false);
        correoLbl.setFocusable(false);
        correoLbl.setOpaque(false);
        contentPanel.add(correoLbl);
        
        JTextArea camposAdministrados = new JTextArea("Campos administrados: " + cantidadCampos);
        camposAdministrados.setBounds(10, 168, 360, 40);
        camposAdministrados.setWrapStyleWord(true);
        camposAdministrados.setLineWrap(true);
        camposAdministrados.setEditable(false);
        camposAdministrados.setFocusable(false);
        camposAdministrados.setOpaque(false);
        contentPanel.add(camposAdministrados);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        okButton.addActionListener(e -> dispose());
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

    }

}
