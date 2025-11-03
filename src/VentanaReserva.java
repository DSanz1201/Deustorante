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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaReserva extends JFrame{
	private JFrame ventanaActual,ventanaAnterior;
	private ModeloReserva modelo;
	private JTable tabla;
	private int fila,columna;

	
	public VentanaReserva(JFrame va,Reserva r) {
		super();
		this.ventanaActual = this;
		this.ventanaAnterior = va;
		this.setTitle("Reserva");
		this.setBounds(705,600,600,705);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		// COMPONENTES
	    fila=-1;
		 columna = -1;
		modelo= new ModeloReserva();
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
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		tabla.setDefaultRenderer(Object.class, (JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column)->{
			JLabel l = new JLabel();
			l.setOpaque(true);
			if(column ==0) {
				l.setText(value.toString());
				l.setHorizontalAlignment(JLabel.CENTER);
				l.setFont(new Font("Arial",Font.BOLD,15));
			}
			if(fila== row && column == columna && column!=0 ) {
				l.setBackground(Color.BLUE);
			}
			return l;
		});
		tabla.setOpaque(true);
		tabla.setBackground(Color.WHITE);
		tabla.getTableHeader().setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		tabla.getTableHeader().setBackground(Color.cyan);
		// TODO PUEDO HACER LOS HEADERS MAS GRANDES
		
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
					int res = JOptionPane.showConfirmDialog(null, "Quieres cerrar el Calendario de Resrvas");
					if(res==0) {
						// TODO PQ NO ME COGE ESTAS VENTANAS
						ventanaActual.setVisible(false);
						ventanaAnterior.setVisible(true);
						
					}
				}
				
			}
		});
		
		
		this.setVisible(true);
	}
}
