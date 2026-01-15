package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import db.BD;
import domain.Reserva;

public class VentanaReserva extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame ventanaActual,ventanaAnterior;
	private ModeloReserva modelo;
	private JTable tabla;
	private int fila,columna;
	private BD bd;
	private ImageIcon icono=new ImageIcon("resources/images/correct.png");
	
	public VentanaReserva(JFrame va,Reserva r, BD bd) {
		super();
		this.bd = bd;
		this.ventanaActual = this;
		this.ventanaAnterior = va;
		this.setTitle("Reserva");
		this.setBounds(705,600,600,705);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		Image img = icono.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		icono = new ImageIcon(img);
		
		// COMPONENTES
	    fila=-1;
		columna = -1;
		List<Reserva> lista = bd.recuperarReservas();
		//System.out.println("Reservas recuperadas: " + lista.size());
		modelo= new ModeloReserva(lista);
		
		//modelo= new ModeloReserva();
		tabla= new JTable(modelo);
		tabla.setShowHorizontalLines(true);
		
		
		JButton btnAuto = new JButton("Auto-asignar primera hora libre");
		btnAuto.setOpaque(true);
		btnAuto.setBackground(new Color(11, 60, 111)); 
		btnAuto.setForeground(Color.WHITE);
		btnAuto.setFont(new Font("Arial", Font.BOLD, 14));
		btnAuto.setFocusPainted(false);

		JPanel panelBoton = new JPanel();
		panelBoton.setBackground(Color.WHITE);
		panelBoton.add(btnAuto);
		panelBoton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 5, 0));

		this.getContentPane().add(panelBoton, BorderLayout.SOUTH);
		
		tabla.setRowHeight(50);
		this.getContentPane().add(tabla.getTableHeader(),BorderLayout.NORTH);
		this.getContentPane().add(tabla,BorderLayout.CENTER);
		
		tabla.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				fila = tabla.rowAtPoint(p);
				columna = tabla.columnAtPoint(p);
				tabla.repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		tabla.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				fila =-1;
				columna =-1;
				tabla.repaint();
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
				
			}
			
			 @Override
			    public void mouseClicked(MouseEvent e) {
			        Point p = e.getPoint();
			        fila = tabla.rowAtPoint(p);
			        columna = tabla.columnAtPoint(p);

			        if (fila < 0 || columna < 0) return;
			        if (columna == 0) return;
			        if (fila % 4 == 0 && fila != 12) return;
			        Reserva rExistente = (Reserva) modelo.getValueAt(fila, columna);
			        if (rExistente != null) {
			            JOptionPane.showMessageDialog(null, "Este horario ya está reservado","Error",JOptionPane.ERROR_MESSAGE);
			            return;
			        }
			        int colMatriz = columna - 1;
			        r.setFila(fila);
			        r.setColumna(colMatriz);
			        modelo.actualizarModelo(fila, colMatriz, r);
			        bd.insertarReserva(r);

			        tabla.repaint();
			        JOptionPane.showMessageDialog(null, "Has reservado");
			        ventanaActual.setVisible(false);
			        ventanaAnterior.setVisible(true);
			    }
		});
		 
		tabla.setDefaultRenderer(
			    Object.class,
			    (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) -> {

			        JLabel l = new JLabel();
			        l.setOpaque(true);
			        l.setHorizontalAlignment(JLabel.CENTER);
			        l.setVerticalAlignment(JLabel.CENTER);

			        String[] reposo = {"R", "E", "P", "O", "S", "O"};

			        // Filas de reposo
			        if (row % 4 == 0 && row != 12) {
			            l.setText(reposo[column]);
			            l.setBackground(new Color(0, 51, 102)); 
			            l.setForeground(Color.WHITE);  
			            l.setIcon(null);
			            return l;
			        }

			        // Primera columna (horas)
			        if (column == 0) {
			            l.setText(value != null ? value.toString() : "");
			            l.setFont(new Font("Arial", Font.BOLD, 14));
			            l.setBackground(Color.WHITE);
			            l.setIcon(null);
			            return l;
			        }

			        Reserva rCelda = (Reserva) modelo.getValueAt(row, column);
			        boolean reservado = (rCelda != null);

			        if (reservado) {
			            l.setIcon(icono);   
			            l.setText("");
			            l.setBackground(Color.WHITE);
			        } else {
			            l.setIcon(null);
			            l.setText("");
			            l.setBackground(Color.WHITE);
			        }

			        // Hover (solo si no está reservada)
			        if (fila == row && columna == column && !reservado) {
			            l.setBackground(Color.BLUE);
			        }

			        return l;
			    }
			);
		tabla.setOpaque(true);
		tabla.setBackground(Color.WHITE);
		tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
		tabla.getTableHeader().setBackground(new Color(0, 119, 182));
		tabla.getTableHeader().setForeground(Color.WHITE);
		
		tabla.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					int res = JOptionPane.showConfirmDialog(null, "Quieres cerrar el Calendario de Reservas");
					if(res==0) {
						ventanaActual.setVisible(false);
						ventanaAnterior.setVisible(true);
						
					}
				}
				
			}
		});
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
		    	ventanaActual.setVisible(false);
		        ventanaAnterior.setVisible(true); 
		    }
		});
		
		 btnAuto.addActionListener(e -> {
	            int[] pos = modelo.buscarPrimeraLibre(0, 1);

	            if (pos == null) {
	                JOptionPane.showMessageDialog(null, "No queda ningún hueco libre");
	                return;
	            }

	            int filaLibre = pos[0];
	            int colLibre = pos[1];

	            r.setFila(filaLibre);
	            r.setColumna(colLibre);

	            modelo.actualizarModelo(filaLibre, colLibre, r);
	            bd.insertarReserva(r);

	            tabla.repaint();
	            JOptionPane.showMessageDialog(null, "Reserva auto-asignada en fila " + filaLibre + ", columna " + colLibre);

	            ventanaActual.setVisible(false);
	            ventanaAnterior.setVisible(true);
	        });
		
		
		this.setVisible(true);
		
	}
}
