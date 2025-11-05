import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Carta_pedido extends JFrame {
	private JFrame ventanaAnterior;
	private JFrame ventanaActual;
	private JPanel pCentro,pSur;
	
	public Carta_pedido(JFrame va) {
		ventanaAnterior=va;
		ventanaActual = this;
		this.setTitle("Pedido");
		this.setBounds(800,400,800,600);
		this.setVisible(true);
		
		this.setLocationRelativeTo(null);
		
		
		pCentro = new JPanel(new FlowLayout());
		
	    
		}
}