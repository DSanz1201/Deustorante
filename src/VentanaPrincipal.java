import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
	private JPanel pCentro,pBotonesCentro,pNorte,pSur,pEste,pOeste;
	protected JButton btnIS,btnCC, btnReserva, btnCarta_pedido;
	protected JLabel titulo,log,l1,texto_principal;
	private boolean pararImg;
	private JFrame ventanaActual;
	
	
	public VentanaPrincipal() {
		super();
		this.setBounds(800,400,600,1000);
		this.setLocationRelativeTo(null);
		this.setTitle("Deustorante");
		// creacion de paneles
		//TODO PONERLES BORDE A TODOS
		pCentro = new  JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		pBotonesCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
		pEste = new JPanel();
		pOeste = new JPanel();
		pSur = new JPanel(); 
		pNorte = new JPanel();
		
		// creacion de componentes
		// TODO COMO HACER QUE EL TITULO Y LOGO VAYAN PARA LA IZQUIERDA Y LOS BOTONES A LA DERECHA ( EN EL PANEL NORTE)
		ventanaActual = this;
		
		btnIS = new JButton("Iniciar Sesion");
		btnReserva = new JButton("Reserva");
		btnCarta_pedido = new JButton("Carta / Pedido");
		
		titulo = new JLabel("Deustorante");
		titulo.setSize(20, 20);
		titulo.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		
		btnCC = new JButton("Crear Cuenta");
		
		ImageIcon logo = new ImageIcon("images/unnamed.png");
		Image imgLog = logo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		log = new JLabel(new ImageIcon(imgLog));
		
		pararImg= false;
		
		l1 = new JLabel();
		l1.setPreferredSize(new Dimension(250, 250));
		texto_principal = new JLabel("<html><div style='text-align: center;'>"
		        + "<br>En Deustorante, fusionamos tradición e innovación para ofrecerte<br>"
		        + "platos llenos de sabor con ingredientes frescos y de calidad.<br>"
		        + "Disfruta de una experiencia gastronómica única en un ambiente acogedor,<br>"
		        + "ideal para cualquier ocasión.</div></html>");
		l1.setHorizontalAlignment(JLabel.CENTER);
		l1.setAlignmentX(CENTER_ALIGNMENT);
		texto_principal.setVerticalAlignment(JLabel.CENTER);
		texto_principal.setHorizontalAlignment(JLabel.CENTER);
		texto_principal.setAlignmentX(CENTER_ALIGNMENT);
		
		// añadir paneles
		
		this.getContentPane().add(pCentro, BorderLayout.CENTER);	
		this.getContentPane().add(pNorte, BorderLayout.NORTH);
		this.getContentPane().add(pSur, BorderLayout.SOUTH);
		this.getContentPane().add(pOeste, BorderLayout.WEST);
		this.getContentPane().add(pEste, BorderLayout.EAST);
		
		// añadir componentes a paneles
		pNorte.add(log); 
		pNorte.add(titulo);
		pNorte.add(btnCC);
		pNorte.add(btnIS);
		pCentro.add(l1);
		pCentro.add(texto_principal);
		pBotonesCentro.add(btnReserva);
		pBotonesCentro.add(btnCarta_pedido);
		pCentro.add(pBotonesCentro);		
		
		
		
		// listeners
		btnCC.addActionListener((e)->{
			if(!pararImg) {
				pararImg = true;
				ventanaActual.setVisible(false);
				new VentanaCC(ventanaActual);
			}
			
		});
		
		btnIS.addActionListener((e)->{
			if(!pararImg) {
				pararImg=true;
			    ventanaActual.setVisible(false);
			    new VentanaIS(ventanaActual);
			}
		});
		
		btnReserva.addActionListener((e)->{
			if(!pararImg) {
				pararImg = true;
				ventanaActual.setVisible(false);
				new Reserva(ventanaActual);
			}
			
		});
		
		btnCarta_pedido.addActionListener((e)->{
			if(!pararImg) {
				pararImg = true;
				ventanaActual.setVisible(false);
				new Carta_pedido(ventanaActual);
			}
			
		});
		
		
		// hilos
		
		Runnable rImg = new Runnable() { // TODO  Solo sale una de ellas, la 3
			
			@Override
			public void run() {
				String[] fotos = {"images/foto1.jpg","images/foto2.jpg","images/foto3.jpg"};
				int i = 0;
				while(!pararImg) {
					if(i==fotos.length) {
						i=0;
					}
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					 //ImageIcon icono = new ImageIcon(fotos[i]);
					 ImageIcon icono = new ImageIcon(fotos[i]);
					 Image img =  icono.getImage().getScaledInstance(450,200, Image.SCALE_SMOOTH);
					 l1.setIcon(new ImageIcon(img));
					 l1.repaint(); 
					 i=i+1;
					 
					
				
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
