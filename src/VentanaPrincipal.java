import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class VentanaPrincipal extends  JFrame {
	private JPanel pCentro,pNorte,pSur,pEste,pOeste;
	private JButton btnPedido,btnIS;
	private JLabel titulo,log,l1 ;
	private boolean pararImg;
	// TODO NO APARECEN LAS IMAGENES
	
	
	public VentanaPrincipal() {
		super();
		this.setBounds(800,400,600,1000);
		this.setLocationRelativeTo(null);
		
		// creacion de paneles
		pCentro = new  JPanel(); // boxlayout?
		pEste = new JPanel();
		pOeste = new JPanel();
		pSur = new JPanel();
		pNorte = new JPanel();
		
		// creacion de componentes
		
		btnPedido = new JButton("Relizar Pedido");
		btnIS = new JButton("Iniciar Sesion");
		titulo = new JLabel("Deustorante");
		ImageIcon logo = new ImageIcon("images/unnamed.png");
		Image imgLog = logo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		log = new JLabel(new ImageIcon(imgLog));
		pararImg= false;
		l1 = new JLabel();
		l1.setHorizontalAlignment(JLabel.CENTER);
		// añadir paneles
		
		this.getContentPane().add(pCentro, BorderLayout.CENTER);	
		this.getContentPane().add(pNorte, BorderLayout.NORTH);
		this.getContentPane().add(pSur, BorderLayout.SOUTH);
		this.getContentPane().add(pOeste, BorderLayout.WEST);
		this.getContentPane().add(pEste, BorderLayout.EAST);
		
		// añadir componentes a paneles
		pNorte.add(log); 
		pNorte.add(titulo);
		pCentro.add(l1);
		
		
		
		// listeners
		
		
		
		// hilos
		Runnable rImg = new Runnable() { // Solo sale una de ellas, la 3
			
			@Override
			public void run() {
				String[] fotos = {"images/foto1.jpg","images/foto2.jpg","images/foto3.jpg"};
				int i =0;
				while(!pararImg) {
					 
					 ImageIcon icono = new ImageIcon(fotos[i]);
					 Image img =  icono.getImage().getScaledInstance(450,200, Image.SCALE_SMOOTH);
					 l1.setIcon(new ImageIcon(img));
					 l1.repaint();
					 i=(i+1)%fotos.length;
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		 };
		};		
        Thread cambImg = new Thread(rImg);
		
		cambImg.start();
		this.setVisible(true);
      
		
		
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new VentanaPrincipal();
		});
	}
	

}
