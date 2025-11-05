import java.awt.AWTError;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class Main extends  JFrame {
	private JPanel pCentro, pBotonesCentro, pNorte, pSur, pEste, pOeste;
	protected JButton btnIS,btnCC, btnReserva, btnCarta_pedido, btnCarta_normal;
	protected JLabel titulo, log, l1, texto_principal, espacio1, espacio2;
	private boolean pararImg;
	private JFrame ventanaActual;
	
	
	public Main() {
		super();
		this.setBounds(800,400,600,800);
		this.setLocationRelativeTo(null);
		this.setTitle("Deustorante");
		// creacion de paneles
		//TODO PONERLES BORDE A TODOS
		pCentro = new  JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		pCentro.setBackground(Color.WHITE);
		pCentro.setOpaque(true);
		
		pBotonesCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
		pBotonesCentro.setBackground(Color.WHITE);
		pBotonesCentro.setOpaque(true);
		
		
		pEste = new JPanel();
		pEste.setOpaque(true);
		pEste.setBackground(Color.BLUE);
		
		pOeste = new JPanel();
		pOeste.setOpaque(true);
		pOeste.setBackground(Color.BLUE);
		
		pSur = new JPanel(); 
		pSur.setOpaque(true);
		pSur.setBackground(Color.BLUE);
		
		pNorte = new JPanel();
		pNorte.setBackground(Color.white);
		pNorte.setOpaque(true);
		
		// creacion de componentes
		// TODO COMO HACER QUE EL TITULO Y LOGO VAYAN PARA LA IZQUIERDA Y LOS BOTONES A LA DERECHA ( EN EL PANEL NORTE)
		ventanaActual = this;
		
		ImageIcon logo = new ImageIcon("images/unnamed.png");
		Image imgLog = logo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		log = new JLabel(new ImageIcon(imgLog));
		
		titulo = new JLabel("Deustorante");
		titulo.setSize(20, 20);
		titulo.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
		
		espacio1 = new JLabel("            ");
		
		espacio2 = new JLabel("     ");
		
		btnIS = new JButton("Iniciar Sesion");
		btnIS.setOpaque(true);
		btnIS.setBackground(Color.WHITE);
		btnIS.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		btnIS.setBorder(new LineBorder(Color.WHITE));
		btnIS.setForeground(Color.BLUE);
		
		btnCC = new JButton("Crear Cuenta");
		btnCC.setOpaque(true);
		btnCC.setBackground(Color.WHITE);
		btnCC.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		btnCC.setBorder(new LineBorder(Color.WHITE));
		btnCC.setForeground(Color.BLUE);
		
		btnReserva = new JButton("Reserva");
		btnReserva.setOpaque(true);
		btnReserva.setBackground(Color.WHITE);
		btnReserva.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		btnReserva.setBorder(new LineBorder(Color.WHITE));
		btnReserva.setForeground(Color.BLUE);
		
		btnCarta_normal = new JButton("Carta");
		btnCarta_normal.setOpaque(true);
		btnCarta_normal.setBackground(Color.WHITE);
		btnCarta_normal.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		btnCarta_normal.setBorder(new LineBorder(Color.WHITE));
		btnCarta_normal.setForeground(Color.BLUE);
		
		btnCarta_pedido = new JButton("Pedido");
		btnCarta_pedido.setOpaque(true);
		btnCarta_pedido.setBackground(Color.WHITE);
		btnCarta_pedido.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		btnCarta_pedido.setBorder(new LineBorder(Color.WHITE));
		btnCarta_pedido.setForeground(Color.BLUE);
		
		pararImg= false;
		
		l1 = new JLabel();
		l1.setPreferredSize(new Dimension(250, 250));
		l1.setHorizontalAlignment(JLabel.CENTER);
		l1.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon iconoprimero = new ImageIcon("images/images3.png");
		Image img =  iconoprimero.getImage().getScaledInstance(450,200, Image.SCALE_SMOOTH);
		l1.setIcon(new ImageIcon(img));
		
		texto_principal = new JLabel("<html><div style='text-align: center;'>"
		        + "<br>En Deustorante, fusionamos tradición e innovación para ofrecerte<br>"
		        + "platos llenos de sabor con ingredientes frescos y de calidad.<br>"
		        + "Disfruta de una experiencia gastronómica única en un ambiente acogedor,<br>"
		        + "ideal para cualquier ocasión.</div></html>");
		texto_principal.setOpaque(true);
		texto_principal.setBackground(Color.WHITE);
		texto_principal.setBorder(new LineBorder(Color.white));
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
		pNorte.add(espacio1);
		pNorte.add(btnCC);
		pNorte.add(espacio2);
		pNorte.add(btnIS);
		pCentro.add(l1);
		pCentro.add(texto_principal);
		pBotonesCentro.add(btnReserva);
		pBotonesCentro.add(Box.createRigidArea(new Dimension(20, 0)));
		pBotonesCentro.add(btnCarta_normal);
		pBotonesCentro.add(Box.createRigidArea(new Dimension(20, 0)));
		pBotonesCentro.add(btnCarta_pedido);
		pCentro.add(pBotonesCentro);		
		
		
		
		// listeners
		btnCC.addActionListener((e)->{
			if(!pararImg) {
				ventanaActual.setVisible(false);
				new VentanaCC(ventanaActual);
			}
			
		});
		
		btnIS.addActionListener((e)->{
			if(!pararImg) {
				
			    ventanaActual.setVisible(false);
			    new VentanaIS(ventanaActual);
			}
		});
		
		btnReserva.addActionListener((e)->{
			if(!pararImg) {
				try {
				ventanaActual.setVisible(false);
				String datos = JOptionPane.showInputDialog(
						"Ingrese su número de personasy email separados por coma:\n, correo@gmail.com"
					);

					String[] partes = datos.split(",");

					int num = Integer.parseInt(partes[0].trim());
					String em = partes[1].trim();

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
					Cliente c = new Cliente(em, "", p);
					Reserva r = new Reserva(num, c);
					new VentanaReserva(ventanaActual,r);
					
				} catch (NumberFormatException err) {
					this.dispose();
					new Main();
				}
			}
			
		});
		
		btnCarta_normal.addActionListener((e)->{
			if(!pararImg) {
				
				ventanaActual.setVisible(false);
				new Carta_normal(ventanaActual);
			}
			
		});
		
		btnCarta_pedido.addActionListener((e)->{
			if(!pararImg) {
				
				ventanaActual.setVisible(false);
				new Carta_pedido(ventanaActual);
			}
			
		});
		
		
		// hilos
		
		Runnable rImg = new Runnable() { 
			
			@Override
			public void run() {
				String[] fotos = {"images/images.png","images/images2.png","images/images3.png"};
				int i = 0;
				while(!pararImg) {
					if(i==fotos.length) {
						i=0;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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
			new Main();
		});
	}
	

}
