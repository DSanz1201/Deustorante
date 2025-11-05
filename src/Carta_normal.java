import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Carta_normal extends JFrame {
	private JFrame ventanaAnterior;
	private JFrame ventanaActual;
	private JPanel pCentro,pSur;
	
	public Carta_normal(JFrame va) {
		ventanaAnterior=va;
		ventanaActual = this;
		this.setTitle("Carta");
		this.setBounds(800,400,500,670);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		
		// Título arriba (opcional)
		JLabel titulo = new JLabel("CARTA DEL DÍA", SwingConstants.CENTER);
		this.add(titulo, BorderLayout.NORTH);

		// Área de texto central con scroll
		JTextArea carta = new JTextArea();
		carta.setMargin(new java.awt.Insets(20, 27, 20, 27));
		carta.setEditable(false);
		carta.setFont(new Font("Monospaced", Font.BOLD, 15));
		carta.setLineWrap(false); // mantenemos columnas alineadas
		carta.setText(
		    "==================  ENTRANTES  ==================\n" +
		    "Ensalada mixta............................  6,50€\n" +
		    "Croquetas caseras (6u)....................  7,00€\n" +
		    "Sopa de cocido ...........................  5,00€\n" +
		    "Txistorra a la sidra......................  6,80€\n" +
		    "\n" +
		    "=============  PLATOS PRINCIPALES  ==============\n" +
		    "Bacalao al pil-pil........................ 14,50€\n" +
		    "Entrecot a la brasa (300g)................ 15,00€\n" +
		    "Hamburguesa de pollo (150g)..............  10,00€\n" +
		    "Risotto de hongos......................... 12,90€\n" +
		    "\n" +
		    "===================  POSTRES  ===================\n" +
		    "Tarta de queso............................  5,00€\n" +
		    "Flan casero...............................  4,50€\n" +
		    "Coulant de chocolate......................  3,50€\n" +
		    "Helado artesano (2 bolas).................  3,80€\n" +
		    "\n" +
		    "===================  BEBIDAS  ===================\n" +
		    "Agua......................................  1,80€\n" +
		    "Refresco..................................  2,20€\n" +
		    "Cerveza...................................  2,50€\n" +
		    "Café......................................  1,50€\n" +
		    "\n" +
		    "=================   DESCUENTOS  =================\n" +
		    "Alumnado ...................................  10%\n" +
		    "Profesorado.................................  15%\n" 
		);
		
		add(carta, BorderLayout.CENTER);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
		        ventanaAnterior.setVisible(true);  // muestra el Main otra vez
		    }
		});
	}
}