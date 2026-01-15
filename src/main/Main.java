package main;
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
import java.awt.Point;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import db.BD;
import domain.Cliente;
import domain.Plato;
import domain.Reserva;
import domain.TipoPersona;
import gui.Carta;
import gui.VentanaCC;
import gui.VentanaIS;
import gui.VentanaReserva;

public class Main extends  JFrame {
	private JPanel pCentro, pBotonesCentro, pNorte, pSur, pEste, pOeste;
	protected JButton btnIS,btnCC, btnReserva, btnCarta;
	protected JLabel titulo, log, l1, texto_principal, espacio1, espacio2,hora;
	private boolean pararImg,cambio,cambio2;
	private JFrame ventanaActual;
	private JProgressBar jb;
	private Thread tpro,thora;
	private int fila,col;
	private Cliente usuarioLogueado = null;
	
	
	public Main( BD bd) {
		super();
		this.setBounds(800,400,600,700);
		this.setLocationRelativeTo(null);
		this.setTitle("Deustorante");
		// creacion de paneles

		
		pCentro = new  JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		pCentro.setBackground(Color.WHITE);
		pCentro.setOpaque(true);
		
		pBotonesCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
		pBotonesCentro.setBackground(Color.WHITE);
		pBotonesCentro.setOpaque(true);
		pBotonesCentro.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
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

		ventanaActual = this;
		
		hora = new JLabel();
		hora.setForeground(Color.WHITE);
		hora.setFont(new Font("Arial", Font.BOLD, 15));
		
		ImageIcon logo = new ImageIcon("resources/images/unnamed.png");
		Image imgLog = logo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		log = new JLabel(new ImageIcon(imgLog));
		
		titulo = new JLabel("Deustorante");
		titulo.setSize(20, 20);
		titulo.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
		
		jb = new JProgressBar(0, 100);
		jb.setValue(0);
		jb.setVisible(false);
		jb.setBackground(Color.WHITE);
		jb.setForeground(Color.BLUE);
		jb.setStringPainted(true);
		
		this.fila=-1;
		this.col=-1;
		
		cambio = false;
		
		
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
		
		btnCarta = new JButton("Carta");
		btnCarta.setOpaque(true);
		btnCarta.setBackground(Color.WHITE);
		btnCarta.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		btnCarta.setBorder(new LineBorder(Color.WHITE));
		btnCarta.setForeground(Color.BLUE);
		
		pararImg= false;
		
		l1 = new JLabel();
		l1.setPreferredSize(new Dimension(250, 250));
		l1.setHorizontalAlignment(JLabel.CENTER);
		l1.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon iconoprimero = new ImageIcon("images/images3.png");
		Image img =  iconoprimero.getImage().getScaledInstance(450,200, Image.SCALE_SMOOTH);
		l1.setIcon(new ImageIcon(img));
		
		// Uso de IA para generar el texto de forma correcta( texto_principal)
		JLabel texto_principal = new JLabel(
			    "<html>"
			  + "<div style='width:420px; margin:8px auto; padding:10px;"
			  + "font-family: Arial; font-size:11px; line-height:1.35;'>"

			  + "<p style='text-align: justify; margin-bottom:10px;'>"
			  + "Come en Deustorante: platos ricos, precios amables y porciones que entienden "
			  + "lo duro que puede ser un día universitario. También ofrecemos comida para llevar "
			  + "cuando no tienes ni un minuto libre."
			  + "</p>"

			  + "<p style='text-align: justify; margin-bottom:12px;'>"
			  + "En nuestro Deustorante, la comida está tan optimizada que ni el profesor más "
			  + "estricto podría hacerle un benchmark negativo. Zero bugs, high performance "
			  + "y un menú con +10 de energía garantizada."
			  + "</p>"

			  + "<p style='text-align:center; font-weight:bold; font-size:12px; color:#CC0000;'>"
			  + "DESCUENTOS ESPECIALES PARA LOS ALUMNOS<br>"
			  + "DE LA UNIVERSIDAD DE DEUSTO,<br>"
			  + "Y TAMBIÉN PARA LOS DE LA OTRA."
			  + "</p>"

			  + "</div></html>"
			);
		texto_principal.setOpaque(true);
		texto_principal.setBackground(Color.WHITE);
		texto_principal.setBorder(new LineBorder(Color.white));
		texto_principal.setVerticalAlignment(JLabel.CENTER);
		texto_principal.setHorizontalAlignment(JLabel.CENTER);
		texto_principal.setAlignmentX(CENTER_ALIGNMENT);
		texto_principal.setFont(new Font("Arial",Font.ITALIC,10));
		
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
		pBotonesCentro.add(btnCarta);
		pCentro.add(pBotonesCentro);
		pSur.add(hora);
		
		
		// listeners
		btnCC.addActionListener((e) -> {
		    if (!cambio) {
		        ventanaActual.setVisible(false);
		        new VentanaCC(ventanaActual, bd, this);
		    }
		});
		
		btnIS.addActionListener((e)->{
		    if(!cambio) { 
		        ventanaActual.setVisible(false);
		        new VentanaIS(ventanaActual, bd, this);  
		    } else {
		        JOptionPane.showMessageDialog(null, "Cerrando Sesion...");
		        System.exit(0);
		    }
		});
		
		
		btnReserva.addActionListener((e)->{
			if(!pararImg) {
				try {
				ventanaActual.setVisible(false);
				String datos = JOptionPane.showInputDialog(
						"Ingrese su número de personas y email separados por coma:\n correo@gmail.com"
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
					jb.setVisible(true);
					//tpro.start();
					new VentanaReserva(ventanaActual,r, bd);
					
					
				} catch (Exception err) {
					this.dispose();
					//new Main(clientes);
				}
			}
			
		});
		
		btnCarta.addActionListener((e)->{
		    if(!pararImg) {
		    	ventanaActual.setVisible(false);
		        new Carta(ventanaActual, bd, usuarioLogueado);
		    }
		});
		// hilos
		
	   Runnable rhora = new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
					long sillis = System.currentTimeMillis(); 
					Date fechaActual = new Date(sillis);     
			     	String s = sdf.format(fechaActual);
			     	hora.setText(s);
				
				}
				
			}
		};
		 thora = new Thread(rhora);
		thora.start();
		
		Runnable rImg = new Runnable() { 
			
			@Override
			public void run() {
				String[] fotos = {"resources/images/images.png","resources/images/images2.png","resources/images/images3.png"};
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
		
		 tpro = new Thread(()->{
			    for (int i = 0; i <= 100; i++) {
			       jb.setValue(i);
			        try { 
			        	Thread.sleep(20); 
			        } catch (InterruptedException ex) {
			        	}
			        }
			    jb.setVisible(false);   
		 });
		this.setVisible(true);
      
		
		
	}
	public static void main(String[] args) {
//	List<Cliente>	lClientes = new ArrayList<Cliente>();
//		 	Cliente c1 = new Cliente("juan@example.com", "1234juan",TipoPersona.ESTUDIANTE);
//	        Cliente c2 = new Cliente("maria@example.com", "mariaPass",TipoPersona.PROFESOR);
//	        Cliente c3 = new Cliente("pedro@example.com", "pedro_2025",TipoPersona.EXTERNO);
//	        Cliente c4 = new Cliente("laura@example.com", "lauraSegura",TipoPersona.PROFESOR);
//	        Cliente c5 = new Cliente("ana@example.com", "anaClave",TipoPersona.ESTUDIANTE);
//	        lClientes.add(c5);
//	        lClientes.add(c4);
//	        lClientes.add(c3);
//	        lClientes.add(c2);
//	        lClientes.add(c1);
		SwingUtilities.invokeLater(()->{
		    BD bd = new BD();
		    bd.initBD("resources/db/Deustorante.db");
			bd.crearTablas();
			new Main(bd);
			// ya han sido insertados
//		    bd.insertarCliente(c1);
//		    bd.insertarCliente(c2);
//		    bd.insertarCliente(c3);
//		    bd.insertarCliente(c4);
//		    bd.insertarCliente(c5);
			//entrantes
			bd.insertarPlato(new Plato("Ensalada mixta", 6.50, "Entrante"));
			bd.insertarPlato(new Plato("Croquetas caseras (6u)", 7.00, "Entrante"));
			bd.insertarPlato(new Plato("Sopa de cocido", 5.00, "Entrante"));
			bd.insertarPlato(new Plato("Txistorra a la sidra", 6.80, "Entrante"));

			//principales
			bd.insertarPlato(new Plato("Bacalao al pil-pil", 14.50, "Principal"));
			bd.insertarPlato(new Plato("Entrecot a la brasa (300g)", 15.00, "Principal"));
			bd.insertarPlato(new Plato("Hamburguesa de pollo (150g)", 10.00, "Principal"));
			bd.insertarPlato(new Plato("Risotto de hongos", 12.90, "Principal"));

			//postres
			bd.insertarPlato(new Plato("Tarta de queso", 5.00, "Postre"));
			bd.insertarPlato(new Plato("Flan casero", 4.50, "Postre"));
			bd.insertarPlato(new Plato("Coulant de chocolate", 3.50, "Postre"));
			bd.insertarPlato(new Plato("Helado artesano (2 bolas)", 3.80, "Postre"));

			//bebidas
			bd.insertarPlato(new Plato("Agua", 1.80, "Bebida"));
			bd.insertarPlato(new Plato("Refresco", 2.20, "Bebida"));
			bd.insertarPlato(new Plato("Cerveza", 2.50, "Bebida"));
			bd.insertarPlato(new Plato("Café", 1.50, "Bebida"));
			
		});
	}
	public  void loginCorrecto(Cliente c) {
		this.usuarioLogueado = c;
	    cambio = true;
	    btnIS.setText("Cerrar Sesion");
	    btnCC.setEnabled(false);
	    btnCC.setForeground(Color.GRAY);
	}
	

}
