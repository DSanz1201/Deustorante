import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Reserva extends JFrame {
	private JFrame ventanaAnterior;
	private JFrame ventanaActual;
	private JPanel pCentro,pSur;
	// TODO MIRAR UN POCO ESTO
	private Dia dia;
	
	public Reserva(JFrame va) {
		ventanaAnterior=va;
		ventanaActual = this;
		this.setTitle("Reserva");
		this.setBounds(800,400,800,600);
		this.setVisible(true);
		
		this.setLocationRelativeTo(null);
		
		
		pCentro = new JPanel(new FlowLayout());
		
	    
		}
}
