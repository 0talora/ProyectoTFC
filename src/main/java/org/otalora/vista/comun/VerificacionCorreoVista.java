package org.otalora.vista.comun;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;

public class VerificacionCorreoVista extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private boolean verificado = false;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VerificacionCorreoVista frame = new VerificacionCorreoVista();
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
    public VerificacionCorreoVista(Frame parent, int codigoEsperado) {
        super(parent, "Verificación de Código", true); // Modal
        setResizable(false);
        setSize(300, 180);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Introduce el código enviado a tu correo:");
        JTextField campoCodigo = new JTextField();
        campoCodigo.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JButton botonVerificar = new JButton("Verificar");
        JLabel resultado = new JLabel("", JLabel.CENTER);

        botonVerificar.addActionListener(e -> {
            try {
                int ingresado = Integer.parseInt(campoCodigo.getText().trim());
                if (ingresado == codigoEsperado) {
                    verificado = true;
                    dispose();
                } else {
                    resultado.setText("Código incorrecto ✖");
                    resultado.setForeground(Color.RED);
                }
            } catch (NumberFormatException ex) {
                resultado.setText("Formato inválido");
                resultado.setForeground(Color.RED);
            }
        });

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.add(label);
        centro.add(Box.createRigidArea(new Dimension(0, 10)));
        centro.add(campoCodigo);
        centro.add(Box.createRigidArea(new Dimension(0, 10)));
        centro.add(botonVerificar);
        centro.add(Box.createRigidArea(new Dimension(0, 10)));
        centro.add(resultado);

        add(centro, BorderLayout.CENTER);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public boolean fueVerificado() {
        return verificado;
    }

}
