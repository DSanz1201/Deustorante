package deustorante;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Carta extends JFrame {
    private JFrame ventanaAnterior;
    private JFrame ventanaActual;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JTextArea areaCesta;
    private JLabel ltotal;
    private List<Plato> listaPlatos;
    private double totalActual = 0.0;
    private BD bd;
    private Cliente cliente;

    public Carta(JFrame va, BD bd, Cliente cliente) {
        this.ventanaAnterior = va;
        this.ventanaActual = this;
        this.bd = bd;
        this.cliente = cliente;
        this.setTitle("Realizar Pedido");
        this.setBounds(800, 400, 900, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        //cargo BD
        listaPlatos = bd.recuperarPlatos();

        String[] columnas = {"Categoría", "Plato", "Precio", "Cantidad"};
        
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        // Rellenar modelo
        for (Plato p : listaPlatos) {
            modeloTabla.addRow(new Object[]{p.getCategoria(), p.getNombre(), String.format("%.2f €", p.getPrecio()), 0});
        }

        //Tabla
        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setRowHeight(30);
        tablaPedidos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaPedidos.getTableHeader().setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        tablaPedidos.getTableHeader().setBackground(Color.CYAN);
        tablaPedidos.setShowGrid(false);
        tablaPedidos.setShowHorizontalLines(true);
        
        TableColumn columnaCantidad = tablaPedidos.getColumnModel().getColumn(3);
        JComboBox<Integer> comboBoxCantidad = new JComboBox<>();
        for(int i=0; i<=10; i++) {
        	comboBoxCantidad.addItem(i);
    	}
        columnaCantidad.setCellEditor(new DefaultCellEditor(comboBoxCantidad));
        tablaPedidos.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        
        DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
        centrar.setHorizontalAlignment(SwingConstants.CENTER);
        tablaPedidos.getColumnModel().getColumn(2).setCellRenderer(centrar);
        tablaPedidos.getColumnModel().getColumn(3).setCellRenderer(centrar);
        
        //Cesta
        JPanel panelCesta = new JPanel(new BorderLayout());
        panelCesta.setPreferredSize(new Dimension(250, 0));
        panelCesta.setBorder(BorderFactory.createTitledBorder("Tu Cesta"));
        panelCesta.setBackground(Color.WHITE);

        areaCesta = new JTextArea();
        areaCesta.setEditable(false);
        areaCesta.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        areaCesta.setText("Cesta vacía");
        
        ltotal = new JLabel("Total: 0.00 €", SwingConstants.CENTER);
        ltotal.setFont(new Font("Arial", Font.BOLD, 18));
        ltotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ltotal.setOpaque(true);
        ltotal.setBackground(Color.GRAY);

        JButton btnFinalizar = new JButton("Confirmar Pedido");
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 16));
        btnFinalizar.setBackground(Color.CYAN);
        
        JPanel panelFinalizar = new JPanel(new BorderLayout());
        panelFinalizar.add(ltotal, BorderLayout.NORTH);
        panelFinalizar.add(btnFinalizar, BorderLayout.SOUTH);

        panelCesta.add(new JScrollPane(areaCesta), BorderLayout.CENTER);
        panelCesta.add(panelFinalizar, BorderLayout.SOUTH);

        //Listener
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                   totalActual = actualizarCesta();
                }
            }
        });
        
        //Ventana principal
        this.add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);
        this.add(panelCesta, BorderLayout.EAST);
        JLabel titulo = new JLabel("", SwingConstants.CENTER);
        try {
        	titulo.setText("REALIZAR PEDIDO (" + cliente.getEmail() + ")");
        } catch (NullPointerException e) {
        	titulo.setText("REALIZAR PEDIDO (GUEST)");
        }
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.add(titulo, BorderLayout.NORTH);

        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (tablaPedidos.isEditing()) {
                    tablaPedidos.getCellEditor().stopCellEditing();
                }
            	if(cliente == null) {
            		JOptionPane.showMessageDialog(null, "Para finalizar el pedido debes iniciar sesión primero.");
            		ventanaActual.setVisible(false);
                    va.setVisible(true);
                    return;
            	}
                if (totalActual == 0) {
                    JOptionPane.showMessageDialog(null, "La cesta está vacía.");
                } else {
                	//cantidades
                    List<Integer> cantidades = new ArrayList<>();
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        cantidades.add((Integer) modeloTabla.getValueAt(i, 3));
                    }

                    //guarda en BD
                    int idPedido = bd.insertarPedido(cliente.getEmail(), totalActual, listaPlatos, cantidades);
                    
                    if (idPedido != -1) {
                        JOptionPane.showMessageDialog(null, "Pedido realizado correctamente.\nTotal: " + String.format("%.2f €", totalActual));
                        ventanaAnterior.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al guardar en la base de datos.");
                    }
                }
            }
        });

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ventanaAnterior.setVisible(true);
            }
        });
        
        this.setVisible(true);
    }

    private double actualizarCesta() {
        String texto = "";
        double totalActual = 0.0;
        boolean hayItems = false;
        
        texto = texto + " Producto          Cant   Subt.\n";
        texto = texto + " ------------------------------\n";

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            int cantidad = (int) modeloTabla.getValueAt(i, 3);
            if (cantidad > 0) {
                hayItems = true;
                String nombre = (String) modeloTabla.getValueAt(i, 1);
                double precioUnitario = listaPlatos.get(i).getPrecio(); 
                double subtotal = precioUnitario * cantidad;
                totalActual += subtotal;
                
                texto = texto + String.format(" %s  x%02d   %5.2f€\n", nombre, cantidad, subtotal);
            }
        }

        if (!hayItems) {
            areaCesta.setText("\n  Cesta vacía");
            ltotal.setText("Total: 0,00 €");
        } else {
            areaCesta.setText(texto);
            ltotal.setText(String.format("Total: %.2f €", totalActual));
        }
        
        return totalActual;
    }
}