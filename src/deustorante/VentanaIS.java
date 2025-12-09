package deustorante;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class VentanaIS extends JFrame{
	private JFrame ventanaAnterior;
	private JFrame ventanaActual;
	private JPanel pCentro,pSur;
	private JLabel email,contrasenya,espacio;
	private JTextField txtemail;
	private JPasswordField txtcontr;
	private JButton btnVolver,btnAceptar;
	private JProgressBar jb;
	private Thread tpro;
	private BD bd;
	private Main main;
	
	public VentanaIS(JFrame va,BD bd, Main main) {
		ventanaAnterior=va;
		ventanaActual = this;
	
		this.setTitle("Iniciar Sesión");
		this.setBounds(200,400,600,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		
		pCentro = new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		pCentro.setOpaque(true);
		pCentro.setBackground(Color.white);
		
		
		pSur = new JPanel();
		pSur.setOpaque(true);
		pSur.setBackground(Color.WHITE);
		
		this.bd=bd;
		
		this.main=main;
		espacio = new JLabel("        ");
		
	    email = new JLabel("Email");
	    email.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
	    
		contrasenya = new JLabel("Contraseña");
		contrasenya.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		
		txtcontr = new JPasswordField(10);
		txtcontr.setBorder(new LineBorder(Color.BLUE));
		
		txtemail= new JTextField(20);
		txtemail.setBorder(new LineBorder(Color.blue));
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setOpaque(true);
		btnAceptar.setBackground(Color.WHITE);
		btnAceptar.setForeground(Color.BLUE);
		btnAceptar.setBorder(new LineBorder(Color.BLUE));
		btnAceptar.setHorizontalAlignment(JButton.CENTER);
		
		
		btnVolver = new JButton("Volver");
		btnVolver.setOpaque(false);
		btnVolver.setBackground(Color.WHITE);
		btnVolver.setForeground(Color.BLUE);
		btnVolver.setBorder(new LineBorder(Color.BLUE));
		btnVolver.setHorizontalAlignment(JButton.CENTER);
		
		jb = new JProgressBar(0, 100);
		jb.setValue(0);
		jb.setVisible(false);
		jb.setBackground(Color.WHITE);
		jb.setForeground(Color.BLUE);
		jb.setStringPainted(true);
		
		txtemail.setMaximumSize(new Dimension(400, 45));
		txtcontr.setMaximumSize(new Dimension(400, 45));
		
		
		email.setAlignmentX(Component.CENTER_ALIGNMENT);
		contrasenya.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnAceptar.setPreferredSize(new Dimension(80, 40));
		btnVolver.setPreferredSize(new Dimension(80, 40));


		
		this.getContentPane().add(pCentro,BorderLayout.CENTER);
		this.getContentPane().add(pSur,BorderLayout.SOUTH);
		
		pCentro.add(email);
		pCentro.add(Box.createRigidArea(new Dimension(20, 20)));
		pCentro.add(txtemail);
		pCentro.add(Box.createRigidArea(new Dimension(400, 45)));
		pCentro.add(contrasenya);
		pCentro.add(Box.createRigidArea(new Dimension(20, 20)));
		pCentro.add(txtcontr);
		pCentro.add(Box.createRigidArea(new Dimension(400, 45)));
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
			String cont = txtcontr.getText();
			if(!bd.comprobarCliente(em,cont) || cont.equals("") || em.equals("")) {
				JOptionPane.showMessageDialog(null, "Email o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
				txtemail.setText("");
				txtcontr.setText("");
			} else {
				Cliente c = bd.obtenerCliente(em);
				if (c != null) {
		            main.loginCorrecto(c);
		            jb.setVisible(true);
		            tpro.start();
		        }
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
			    JOptionPane.showMessageDialog(null, "Bienvenido de nuevo "+txtemail.getText());
		 });

			this.setVisible(true);
	}
	
	
	
}
