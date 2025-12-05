package deustorante;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Reserva  {
	private static int cont;
	private int numPersona, cod;
	private Cliente clienteResponsable;
	private int fila, columna;
	
	/**
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}
	/**
	 * @param fila the fila to set
	 */
	public void setFila(int fila) {
		this.fila = fila;
	}
	/**
	 * @return the columna
	 */
	public int getColumna() {
		return columna;
	}
	/**
	 * @param columna the columna to set
	 */
	public void setColumna(int columna) {
		this.columna = columna;
	}
	/**
	 * @return the cod
	 */
	public int getCod() {
		return cod;
	}
	/**
	 * @param cod the cod to set
	 */
	public void setCod(int cod) {
		this.cod = cod;
	}
	/**
	 * @return the numPersona
	 */
	public int getNumPersona() {
		return numPersona;
	}
	public Reserva() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reserva(int numPersona, Cliente clienteResponsable) {
		super();
		cont++;
		this.cod=cont;
		this.numPersona = numPersona;
		this.clienteResponsable = clienteResponsable;
		
	}
	/**
	 * @return the numPersona
	 */
	
	/**
	 * @param numPersona the numPersona to set
	 */
	public void setNumPersona(int numPersona) {
		this.numPersona = numPersona;
	}
	/**
	 * @return the clienteResponsable
	 */
	public Cliente getClienteResponsable() {
		return clienteResponsable;
	}
	/**
	 * @param clienteResponsable the clienteResponsable to set
	 */
	public void setClienteResponsable(Cliente clienteResponsable) {
		this.clienteResponsable = clienteResponsable;
	}
	@Override
	public String toString() {
		return "Reserva [numPersona=" + numPersona + ", clienteResponsable=" + clienteResponsable + "]";
	}
	
	
	

	
	
	
	
}
