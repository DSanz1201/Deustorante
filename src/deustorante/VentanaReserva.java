package deustorante;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaReserva extends JFrame{
	private JFrame ventanaActual,ventanaAnterior;
	private ModeloReserva modelo;
	private JTable tabla;
	private int fila,columna;
	private BD bd;


	
	public VentanaReserva(JFrame va,Reserva r, BD bd) {
		super();
		this.bd = bd;
		this.ventanaActual = this;
		this.ventanaAnterior = va;
		this.setTitle("Reserva");
		this.setBounds(705,600,600,705);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		// COMPONENTES
	    fila=-1;
		columna = -1;
		List<Reserva> lista = bd.recuperarReservas();
		//System.out.println("Reservas recuperadas: " + lista.size());
		modelo= new ModeloReserva(lista);
		
		//modelo= new ModeloReserva();
		tabla= new JTable(modelo);
		tabla.setShowHorizontalLines(true);
		
		
		
		
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

			  
			        String[] reposo = {"R", "E", "P", "O", "S", "O"};
			        if (row % 4 == 0 && row != 12) {
			            l.setText(reposo[column]);
			            l.setHorizontalAlignment(JLabel.CENTER);
			            l.setBackground(Color.GRAY);
			            return l;
			        }

			        
			        if (column == 0) {
			            l.setText(value != null ? value.toString() : "");
			            l.setFont(new Font("Arial", Font.BOLD, 14));
			            l.setBackground(new Color(230, 230, 230)); // gris claro
			            return l;
			        }

			        
			        Reserva rCelda = (Reserva) modelo.getValueAt(row, column);
			        boolean reservado = (rCelda != null);

			        if (reservado) {
			            l.setBackground(new Color(144, 238, 144)); // verde claro
			            l.setText("RESERVADO");
			        } else {
			            l.setBackground(Color.WHITE);
			            l.setText("");
			        }

			    
			        if (fila == row && columna == column && !reservado) {
			            l.setBackground(Color.BLUE);
			        }

			        return l;
			    }
			);
		tabla.setOpaque(true);
		tabla.setBackground(Color.WHITE);
		tabla.getTableHeader().setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		tabla.getTableHeader().setBackground(Color.cyan);
	
		
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
		
		
		
		
		this.setVisible(true);
		
	}
}
