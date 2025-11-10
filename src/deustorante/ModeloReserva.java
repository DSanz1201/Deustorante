package deustorante;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class ModeloReserva extends DefaultTableModel{
	private List<String> titulos = Arrays.asList("","LUNES","MARTES","MIERCOLES","JUEVES","VIERNES");
	private Reserva matriz[][];
	public ModeloReserva() {
		matriz = new Reserva[13][6];
	}
	
	@Override
	public int getRowCount() {
		if(matriz ==null) {
			return 0;
		} else {
			return matriz.length;
		}
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return titulos.size();
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return titulos.get(column);
	}
	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Object getValueAt(int row, int column) {
	    if (column == 0) {
	      
	        return String.format("%02d:00", row + 10);
	    }

	    int colMatriz = column - 1;

	    
	    if (row < 0 || row >= matriz.length || colMatriz < 0 || colMatriz >= matriz[0].length) {
	        return null;
	    }
	    else {
	    return matriz[row][colMatriz];
	    }
	}
	// TODO METODO SET
	public void actualizarModelo(int row, int column,Reserva r) {
		matriz[row][column] =r;
	}
	
}
