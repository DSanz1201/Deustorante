import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class VentanaCC extends JFrame {
	private static final long MAX_VALUE = 10_000_000;
	private JFrame ventanaAnterior;
	private JFrame ventanaActual;
	private JPanel pCentro,pSur;
	private JLabel email,contrasenya,repetir,espacio;
	private JTextField txtemail;
	private JPasswordField txtrep,txtcontr;
	private JButton btnVolver,btnAceptar;
	private JProgressBar jb ;
	private Thread tpro;
	
	
	public VentanaCC(JFrame ventanaActual2) {
		super();
		ventanaAnterior = ventanaActual2;
		ventanaActual = this;
		this.setTitle("Creacion de Cuenta Deustorante");
		this.setBounds(300, 200, 600, 400);
		this.setVisible(true);
		// CREACION PANELES
		pCentro = new JPanel(new GridLayout(6,1,5,5));
		pCentro.setOpaque(true);
		pCentro.setBackground(Color.white);
		
		pSur = new JPanel();
		pSur.setOpaque(true);
		pSur.setBackground(Color.white);
		
		
		// CREACION COMPOENENTES
		// TODO Boxlayouts
		email = new JLabel("Email");
		email.setForeground(Color.BLUE);
		
		
		
		contrasenya = new JLabel("Contraseña");
		contrasenya.setForeground(Color.BLUE);
		
		repetir = new JLabel("Confirmar Contraseña");
		repetir.setForeground(Color.BLUE);
		
		txtcontr = new JPasswordField(20);
		txtcontr.setBorder(new LineBorder(Color.BLUE));
		
		
		txtemail= new JTextField(20);
		txtemail.setMaximumSize(new Dimension(5,5));
		txtemail.setBorder(new LineBorder(Color.BLUE));
		
		txtrep = new JPasswordField(10);
		txtrep.setBorder(new LineBorder(Color.BLUE));
	
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setOpaque(true);
		btnAceptar.setBackground(Color.WHITE);
		btnAceptar.setForeground(Color.BLUE);
		btnAceptar.setBorder(new LineBorder(Color.BLUE));
		btnAceptar.setHorizontalAlignment(JButton.CENTER);
		
		
		btnVolver = new JButton("Volver");
		btnVolver.setOpaque(true);
		btnVolver.setBackground(Color.WHITE);
		btnVolver.setForeground(Color.BLUE);
		btnVolver.setBorder(new LineBorder(Color.BLUE));
		btnVolver.setHorizontalAlignment(JButton.CENTER);
		
		espacio = new JLabel("     "); 
		
		jb = new JProgressBar(0, 100);
		jb.setValue(0);
		jb.setVisible(false);
		jb.setBackground(Color.WHITE);
		jb.setForeground(Color.BLUE);
		jb.setStringPainted(true);
		
		
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
		pSur.add(espacio);
		pSur.add(btnVolver);
		this.add(jb,BorderLayout.NORTH);
		
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
				String[] opciones = {"Estudiante", "Profesor", "Externo"};
				String per = (String) JOptionPane.showInputDialog(
				    null,
				    "Seleccione su tipo de persona:",
				    "Tipo de persona",
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    opciones,
				    opciones[0]
				);
				TipoPersona p = TipoPersona.valueOf(per.toUpperCase());
				Cliente c = new Cliente(em, con, p);
				//System.out.println(c);
				// TODO AÑADIRLO A LA LIASTA DE CLIENTES
				jb.setVisible(true);
				tpro.start();
				
			}
			
		});
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
		    	ventanaActual.setVisible(false);
		        ventanaAnterior.setVisible(true);  // muestra el Main otra vez
		    }
		});
		 tpro = new Thread(()->{
			
			    for (int i = 0; i <= 100; i++) {
			       jb.setValue(i);
			        try { 
			        	Thread.sleep(20); 
			        } catch (InterruptedException ex) {
			        	}
			        }
			    this.ventanaActual.setVisible(false);
  		    	this.ventanaAnterior.setVisible(true);
			    JOptionPane.showMessageDialog(null, "Tu cuenta ha sido correctamente creada");
		 });
		this.setVisible(true);
	

		 
	}
}
