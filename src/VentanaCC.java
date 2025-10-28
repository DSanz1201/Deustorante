import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaCC extends JFrame {
	private JFrame ventanaAnterior;
	private JFrame ventanaActual;
	private JPanel pCentro,pSur;
	private JLabel email,contrasenya,repetir;
	private JTextField txtemail;
	private JPasswordField txtrep,txtcontr;
	private JButton btnVolver,btnAceptar;
	
	
	public VentanaCC(JFrame ventanaActual2) {
		super();
		ventanaAnterior = ventanaActual2;
		ventanaActual = this;
		this.setTitle("Creacion de Cuenta Deustorante");
		this.setBounds(300, 200, 600, 400);
		this.setVisible(true);
		// CREACION PANELES
		pCentro = new JPanel(new GridLayout(6,1,5,5));
		pSur = new JPanel();
		
		// CREACION COMPOENENTES
		// TODO SE PUEDEN HACER MAS PEQUEÑOS
		email = new JLabel("Email");
		contrasenya = new JLabel("Contraseña");
		repetir = new JLabel("Confirmar Contraseña");
		txtcontr = new JPasswordField(10);
		txtemail= new JTextField(20);
		txtrep = new JPasswordField(10);
		btnAceptar = new JButton("Aceptar");
		btnVolver = new JButton("Volver");
		//AÑADIR PANELES
		this.getContentPane().add(pCentro,BorderLayout.CENTER);
		this.getContentPane().add(pSur,BorderLayout.SOUTH);
		
		
		// AÑADIR COMPONENTES
		pCentro.add(email);
		pCentro.add(txtemail);
		pCentro.add(contrasenya);
		pCentro.add(txtcontr);
		pCentro.add(repetir);
		pCentro.add(txtrep);
		pSur.add(btnAceptar);
		pSur.add(btnVolver);
		
		btnVolver.addActionListener((e)->{
			this.ventanaActual.setVisible(false);
			this.ventanaAnterior.setVisible(true);
		});
		
		btnAceptar.addActionListener((e)->{
			String em = txtemail.getText();
			String con = txtcontr.getText();
			String rep = txtrep.getText();
			if(!con.equals(rep)) {
				JOptionPane.showMessageDialog(null, "Nombre o contraseña incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
				txtrep.setText("");
				txtcontr.setText("");
			}
			else{
				String txt =JOptionPane.showInputDialog("Eres estudiante , profesor o del exterior");
				TipoPersona tp= TipoPersona.valueOf(txt.toUpperCase());
				Cliente c = new Cliente(em, con, tp);
				// TODO NECESITAMOS VERIFICAR ALGO MAS?
				//TODO AÑADIR CLIENTE A LA TABLA/LISTA DE CLIENTES CUANDO LA CREEMOS
				JOptionPane.showMessageDialog(null, "Tu cuenta ha sido correctamente creada");
				this.ventanaActual.setVisible(false);
				this.ventanaAnterior.setVisible(true);
				
			}
			
		});
		
		this.setVisible(true);
	}

}
