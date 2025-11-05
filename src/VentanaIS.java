import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaIS extends JFrame{
	private JFrame ventanaAnterior;
	private JFrame ventanaActual;
	private JPanel pCentro,pSur;
	private JLabel email,contrasenya;
	private JTextField txtemail;
	private JPasswordField txtcontr;
	private JButton btnVolver,btnAceptar;
	
	public VentanaIS(JFrame va) {
		ventanaAnterior=va;
		ventanaActual = this;
		this.setTitle("Iniciar Sesión");
		this.setBounds(200,400,600,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		
		pCentro = new JPanel(new GridLayout(4,6,5,5));
		pSur = new JPanel();
		
		
		// TODO SE PUEDEN HACER MAS PEQUEÑOS
	    email = new JLabel("Email");
		contrasenya = new JLabel("Contraseña");
		txtcontr = new JPasswordField(10);
		txtemail= new JTextField(20);
		btnAceptar = new JButton("Aceptar");
		btnVolver = new JButton("Volver");
		
		this.getContentPane().add(pCentro,BorderLayout.CENTER);
		this.getContentPane().add(pSur,BorderLayout.SOUTH);
		
		pCentro.add(email);
		pCentro.add(txtemail);
		pCentro.add(contrasenya);
		pCentro.add(txtcontr);
		pSur.add(btnAceptar);
		pSur.add(btnVolver);
		
		btnVolver.addActionListener((e)->{
			this.ventanaActual.setVisible(false);
			this.ventanaAnterior.setVisible(true);
		});
		btnAceptar.addActionListener((e)->{
			String em = email.getText();
			String cont = contrasenya.getText();
			// TODO VERIFICAR QUE EXISTE ESE CLIENTE CUANDO LOS BUSQUEMOS EN LA TABLA O LISTA, POR LO QUE AÑADIR EL MODELO DE TABLA
			
		});
		
		this.setVisible(true);
	}
	
	
	
}
