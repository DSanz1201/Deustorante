package deustorante;

import java.awt.Taskbar.State;
import java.beans.Statement;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BD {
	
private Connection con; //Nos conectarmos a la base de datos
	
	public void initBD(String nombreBD)  {
		con = null;

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeBD() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// metodo para crear las tablas a utilizar
	public void crearTablas() {
		String sqlCliente = "CREATE TABLE IF NOT EXISTS CLIENTE("
				+"EMAIL TEXT PRIMARY KEY, "
				+"CONTRASENIA TEXT, "
				+"TIPO_PERSONA TEXT)";
		String sqlReserva = "CREATE TABLE IF NOT EXISTS RESERVA("
		        + "COD_RESERVA INTEGER PRIMARY KEY AUTOINCREMENT, "
		        + "NUM_PERSONA INTEGER, "
		        + "EMAIL_CLIENTE TEXT, "
		        + "FOREIGN KEY (EMAIL_CLIENTE) REFERENCES CLIENTE(EMAIL)"
		        + ")";
		try {
			PreparedStatement ps = con.prepareStatement(sqlCliente);
			ps.executeUpdate();

			
			ps =  con.prepareStatement(sqlReserva);
			ps.executeUpdate();

			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// metodo que inserta un cliente en la BD
	public void insertarCliente(Cliente c) {
		String sql = "INSERT INTO CLIENTE (EMAIL,CONTRASENIA,TIPO_PERSONA) VALUES(?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, c.getEmail());
			ps.setString(2, c.getContrasenia());
			ps.setString(3, c.getTipo().toString());
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {

		  e.printStackTrace();
		}
		
	}
	// metodo para insertat una reserva en la BD
	public void insertarReserva(Reserva r) {
		String sql = "INSERT INTO RESERVA (NUM_PERSONA,EMAIL_CLIENTE) VALUES(?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, r.getNumPersona());
			ps.setString(2, r.getClienteResponsable().getEmail());
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
