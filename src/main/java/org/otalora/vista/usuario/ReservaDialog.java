package org.otalora.vista.usuario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import org.otalora.controlador.ReservaControlador;
import org.otalora.modelo.Evento;

public class ReservaDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JSpinner spinner;
	private JLabel mensajeError;
	private HomeVista homeVista;


	/**
	 * Launch the application.
	 */
//	public ReservaDialog() {
//	    this(null, null,null);
//		setTitle("Recon Reservas-Reservar");
//	}
//
//	public static void main(String[] args) {
//		
//		try {
//			ReservaDialog dialog = new ReservaDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public ReservaDialog(JFrame parent, Evento evento, HomeVista homeVista) {
	    super(parent, "Recon Reservas", true);
		ReservaControlador reservaControlador=new ReservaControlador();
		
		//setTitle("Recon Reservas");
		setResizable(false);
		setBounds(100, 100, 269, 157);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
	    int maxPlazas;
	    
	    if (evento != null && evento.getNumeroPlazasRestantes()<10) {
			maxPlazas=evento.getNumeroPlazasRestantes();
		}else {
			maxPlazas=10;
		}
		
		spinner = new JSpinner(new SpinnerNumberModel(1,1,maxPlazas,1));
		spinner.setBounds(131, 29, 54, 20);
		contentPanel.add(spinner);
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
		editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
		editor.getTextField().setFocusable(false);
		editor.getTextField().setEditable(false);
		editor.getTextField().setCursor(null);
		
		mensajeError = new JLabel("");
		mensajeError.setForeground(Color.RED);
		mensajeError.setBounds(22, 54, 221, 20);
		mensajeError.setVisible(false);
		contentPanel.add(mensajeError);

		
		spinner.addChangeListener(e -> {
		    int valor = (int) spinner.getValue();
		    if (valor == maxPlazas) {
		        mensajeError.setText("Límite máximo de plazas alcanzado.");
		        mensajeError.setVisible(true);
		    } else {
		        mensajeError.setVisible(false);
		    }
		});
		
		JLabel numPersonasLabel = new JLabel("Numero de Personas");
		numPersonasLabel.setBounds(22, 32, 99, 14);
		contentPanel.add(numPersonasLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton reservaBtn = new JButton("Reservar");
				reservaBtn.setActionCommand("OK");
				reservaBtn.addActionListener(e->{
					int numeroPersonas =(int) spinner.getValue();
					if(reservaControlador.reservar(evento, numeroPersonas)) {
					    JOptionPane.showMessageDialog(
					        this,
					        "Reserva realizada con éxito.",
					        "Éxito",
					        JOptionPane.INFORMATION_MESSAGE
					    );
				        if (homeVista != null) {
				            homeVista.refrescarTablaEventos();
				        }
					    dispose();
					} else {
					    JOptionPane.showMessageDialog(
					        this,
					        "ha habido un error al realizar la reserva.",
					        "Error",
					        JOptionPane.ERROR_MESSAGE
					    );
					}

					
				});
				buttonPane.add(reservaBtn);
				getRootPane().setDefaultButton(reservaBtn);
			}
			{
				JButton cancelarBtn = new JButton("Cancelar");
				cancelarBtn.setActionCommand("Cancel");
				cancelarBtn.addActionListener(e->{
					this.dispose();
				});
				buttonPane.add(cancelarBtn);
			}
		}
		setLocationRelativeTo(null);
	}
}
