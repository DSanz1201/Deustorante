package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import db.BD;
import domain.Cliente;
import domain.Plato;

public class Carta extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JFrame ventanaAnterior;
    private JFrame ventanaActual;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JTextArea areaCesta;
    private JLabel ltotal;
    private List<Plato> listaPlatos;
    private double totalActual = 0.0;
    @SuppressWarnings("unused")
	private BD bd;
    @SuppressWarnings("unused")
	private Cliente cliente;

    public Carta(JFrame va, BD bd, Cliente cliente) {
        this.ventanaAnterior = va;
        this.ventanaActual = this;
        this.bd = bd;
        this.cliente = cliente;
        this.setTitle("Realizar Pedido");
        this.setBounds(800, 400, 1000, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        //Cargar BD
        listaPlatos = bd.recuperarPlatos();
        
        //Inicializar el modelo de datos
        String[] columnas = {"Categoría", "Plato", "Precio", "Cantidad"};
        
        inicializarModelo(listaPlatos, columnas);

        //Crear tabla prrincipal
        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaPedidos.setRowHeight(35);
        tablaPedidos.getTableHeader().setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        tablaPedidos.getTableHeader().setBackground(new Color(0, 119, 182));
        tablaPedidos.getTableHeader().setForeground(Color.WHITE);
        tablaPedidos.setShowGrid(false);
        tablaPedidos.setShowHorizontalLines(true);
        tablaPedidos.setSelectionBackground(new Color(173, 216, 230)); 
        tablaPedidos.setSelectionForeground(Color.BLACK);
        
        tablaPedidos.getColumnModel().getColumn(1).setPreferredWidth(150);

        //Render columna 0
        TableCellRenderer renderCol0 = new TableCellRenderer() {
            
            JLabel result = new JLabel();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
            	result.setOpaque(true);
            	
            	String contenido = "";
            	if (value != null) {
            	    contenido = value.toString();
            	}
            	String categoriaUpper = contenido.toUpperCase();
            	result.setText(categoriaUpper);
                
                result.setFont(new Font("Arial", Font.PLAIN, 14));
                result.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                
                if (isSelected) {
                    result.setBackground(new Color(173, 216, 230));
                } else {
                    result.setBackground(Color.WHITE);
                }
                result.setForeground(Color.BLACK);

                if (categoriaUpper.contains("ENTRANTE")) {
                    result.setIcon(new ImageIcon("resources/images/entrantes.png"));
                } else if (categoriaUpper.contains("PRINCIPAL")) {
                    result.setIcon(new ImageIcon("resources/images/plato.png"));
                } else if (categoriaUpper.contains("POSTRE")) {
                    result.setIcon(new ImageIcon("resources/images/postre.png"));
                } else if (categoriaUpper.contains("BEBIDA")) {
                    result.setIcon(new ImageIcon("resources/images/agua.png"));
                } else {
                    result.setIcon(null);
                }
                
                return result;
            }
        };
        
        
        tablaPedidos.getColumnModel().getColumn(0).setCellRenderer(renderCol0);

        //Render columnas 2 y 3
        DefaultTableCellRenderer restoColumnas = new DefaultTableCellRenderer();
        restoColumnas.setHorizontalAlignment(SwingConstants.CENTER);
        tablaPedidos.getColumnModel().getColumn(1).setCellRenderer(restoColumnas);
        tablaPedidos.getColumnModel().getColumn(2).setCellRenderer(restoColumnas);
        tablaPedidos.getColumnModel().getColumn(3).setCellRenderer(restoColumnas);
        
        //Seleccionar cantidad
        TableColumn columnaCantidad = tablaPedidos.getColumnModel().getColumn(3);
        JComboBox<Integer> comboBoxCantidad = new JComboBox<>();
        for(int i=0; i<=10; i++) {
        	comboBoxCantidad.addItem(i);
    	}
        columnaCantidad.setCellEditor(new DefaultCellEditor(comboBoxCantidad));
        tablaPedidos.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        
        //Cesta
        JPanel panelCesta = new JPanel(new BorderLayout());
        panelCesta.setPreferredSize(new Dimension(315, 0));
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
        ltotal.setBackground(Color.GRAY.brighter());

        //Ajustar boton finalizar
        JButton btnFinalizar = new JButton("Confirmar Pedido");
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 16));
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 16));
        btnFinalizar.setBackground(new Color(0, 119, 182));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFocusPainted(false);
        btnFinalizar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelFinalizar = new JPanel(new BorderLayout());
        panelFinalizar.add(ltotal, BorderLayout.NORTH);
        panelFinalizar.add(btnFinalizar, BorderLayout.SOUTH);
        
        //Añadir area cesta al panel cesta
        panelCesta.add(areaCesta, BorderLayout.CENTER);
        panelCesta.add(panelFinalizar, BorderLayout.SOUTH);
        
        //Añadir ventana principal y terminar de configurar
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

        
        //Listeners
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                   totalActual = actualizarCesta();
                }
            }
        });

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
                    List<Integer> cantidades = new ArrayList<>();
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        cantidades.add((Integer) modeloTabla.getValueAt(i, 3));
                    }

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
    
    
    //Metodos auxiliares
    
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
                String precioString = (String) modeloTabla.getValueAt(i, 2);
                double precioUnitario = Double.parseDouble(precioString.replace(" €", "").replace(",", "."));
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
    
    private void inicializarModelo(List<Plato> listaPlatos, String[] columnas) {
    	String[] categorias = {"ENTRANTE", "PRINCIPAL", "POSTRE", "BEBIDA"};
    	
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        
        
        for(String categoria : categorias) {
        	for (Plato p : listaPlatos) {
        		if(p.getCategoria().toUpperCase().equals(categoria)) {
        			modeloTabla.addRow(new Object[]{p.getCategoria(), p.getNombre(), String.format("%.2f €", p.getPrecio()), 0});
        		}
            }
        }
        
    }

}