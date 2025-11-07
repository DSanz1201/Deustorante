import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		
		Object[][] datos1 = {
				//Entrantes
				{"Ensalada mixta", "6,50€"},
				{"Croquetas caseras (6u)", "7,00€"},
				{"Sopa de cocido", "5,00€"},
				{"Txistorra a la sidra", "6,80€"}
		};
		
		Object[][] datos2 = {
				// Platos principales
				{"Bacalao al pil-pil", "14,50€"},
				{"Entrecot a la brasa (300g)", "15,00€"},
				{"Hamburguesa de pollo (150g)", "10,00€"},
				{"Risotto de hongos", "12,90€"}
		};
		
		Object[][] datos3 = {
				// Postres
				{"Tarta de queso", "5,00€"},
				{"Flan casero", "4,50€"},
				{"Coulant de chocolate", "3,50€"},
				{"Helado artesano (2 bolas)", "3,80€"}
		};
		
		Object[][] datos4 = {   
				// Bebidas
				{"Agua", "1,80€"},
				{"Refresco", "2,20€"},
				{"Cerveza", "2,50€"},
				{"Café", "1,50€"}
		};
		
		String[] columnas1 = {"ENTRANTES", "Precio"};
		String[] columnas2 = {"PLATOS PRINCIPALES", "Precio"};
		String[] columnas3 = {"POSTRES", "Precio"};
		String[] columnas4 = {"BEBIDAS", "Precio"};
		
		DefaultTableModel modelo1 = new DefaultTableModel(datos1, columnas1);
		DefaultTableModel modelo2 = new DefaultTableModel(datos2, columnas2);
		DefaultTableModel modelo3 = new DefaultTableModel(datos3, columnas3);
		DefaultTableModel modelo4 = new DefaultTableModel(datos4, columnas4);
		
		JTable tabla1 = new JTable(modelo1);
		JTable tabla2 = new JTable(modelo2);
		JTable tabla3 = new JTable(modelo3);
		JTable tabla4 = new JTable(modelo4);
		
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JScrollPane(tabla1));
        panel.add(new JScrollPane(tabla2));
        panel.add(new JScrollPane(tabla3));
        panel.add(new JScrollPane(tabla4));
        
        JButton finalizar_compra = new JButton("Realizar pedido");
        
        panel.add(finalizar_compra);
        
        finalizar_compra.addActionListener((e)->{
			ventanaActual.setVisible(false);
			new Main();
			JOptionPane.showMessageDialog(null, "Pedido realizado");
		});
        
        MouseAdapter listener = (new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

            	String[] opciones = {"1", "2", "3", "4", "5"};
                JOptionPane.showInputDialog(
                null,
                "Seleccione la cantidad deseada del artículo:",
                "Cantidad",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
                );
            }
        });
        
        tabla1.addMouseListener(listener);
        tabla2.addMouseListener(listener);
        tabla3.addMouseListener(listener);
        tabla4.addMouseListener(listener);
        

        add(panel);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
		        ventanaAnterior.setVisible(true);
		    }
		});
	    
		}
}