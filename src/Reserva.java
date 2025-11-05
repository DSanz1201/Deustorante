import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Reserva  {
	private static int cont;
	private int numPersona, cod;
	private Cliente clienteResponsable;
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
	public int getNumPersona() {
		return numPersona;
	}
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
