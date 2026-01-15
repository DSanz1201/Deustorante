package db;

import java.beans.Statement;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Cliente;
import domain.Plato;
import domain.Reserva;
import domain.TipoPersona;

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
		        + "FILA INTEGER, "
		        + "COLUMNA INTEGER, "
		        + "FOREIGN KEY (EMAIL_CLIENTE) REFERENCES CLIENTE(EMAIL)"
		        + ")";
		String sqlPlato = "CREATE TABLE IF NOT EXISTS PLATO("
		        + "NOMBRE TEXT PRIMARY KEY, "
		        + "PRECIO REAL, "
		        + "CATEGORIA TEXT)";
		String sqlPedido = "CREATE TABLE IF NOT EXISTS PEDIDO("
		        + "COD_PEDIDO INTEGER PRIMARY KEY AUTOINCREMENT, "
		        + "EMAIL_CLIENTE TEXT, "
		        + "FECHA_HORA TEXT, "
		        + "TOTAL REAL, "
		        + "FOREIGN KEY (EMAIL_CLIENTE) REFERENCES CLIENTE(EMAIL)"
		        + ")";
		String sqlLineaPedido = "CREATE TABLE IF NOT EXISTS LINEA_PEDIDO("
		        + "COD_PEDIDO INTEGER, "
		        + "NOMBRE_PLATO TEXT, "
		        + "CANTIDAD INTEGER, "
		        + "PRECIO_UNITARIO REAL, "
		        + "PRIMARY KEY (COD_PEDIDO, NOMBRE_PLATO), "
		        + "FOREIGN KEY (COD_PEDIDO) REFERENCES PEDIDO(COD_PEDIDO), "
		        + "FOREIGN KEY (NOMBRE_PLATO) REFERENCES PLATO(NOMBRE)"
		        + ")";

		try {
			PreparedStatement ps = con.prepareStatement(sqlCliente);
			ps.executeUpdate();

			
			ps =  con.prepareStatement(sqlReserva);
			ps.executeUpdate();
			
			ps =  con.prepareStatement(sqlPlato);
			ps.executeUpdate();
			
			ps =  con.prepareStatement(sqlPedido);
			ps.executeUpdate();
			
			ps =  con.prepareStatement(sqlLineaPedido);
			ps.executeUpdate();
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// metodo que comprueba si el cliente esta en la BD
	public boolean comprobarCliente(String email,String contr) {
		boolean enc =false;
		String sql = "SELECT * FROM CLIENTE WHERE EMAIL=? AND CONTRASENIA=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, contr);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				enc=true;
			}
			rs.close();
			ps.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enc;
		
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

		} catch (SQLException e) {

		  e.printStackTrace();
		}
		
	}
	// metodo para inserta una reserva en la BD
	public void insertarReserva(Reserva r) {
		String sql = "INSERT INTO RESERVA (NUM_PERSONA,EMAIL_CLIENTE,FILA,COLUMNA) VALUES(?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, r.getNumPersona());
			ps.setString(2, r.getClienteResponsable().getEmail());
			ps.setInt(3, r.getFila());
			ps.setInt(4, r.getColumna());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// metodo para insertar un plato en la BD
	public void insertarPlato(Plato p) {
	    String sql = "INSERT OR IGNORE INTO PLATO (NOMBRE, PRECIO, CATEGORIA) VALUES(?,?,?)";
	    try {
	    	PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, p.getNombre());
	        ps.setDouble(2, p.getPrecio());
	        ps.setString(3, p.getCategoria());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	}
	// metodo para insertar pedido en la BD
	public int insertarPedido(String emailCliente, double total, List<Plato> platos, List<Integer> cantidades) {
	    int codPedido = -1;
	    String sqlCabecera = "INSERT INTO PEDIDO (EMAIL_CLIENTE, FECHA_HORA, TOTAL) VALUES(?, datetime('now'), ?)";
	    String sqlLinea = "INSERT INTO LINEA_PEDIDO (COD_PEDIDO, NOMBRE_PLATO, CANTIDAD, PRECIO_UNITARIO) VALUES(?,?,?,?)";
	    
	    try {
	    	//Cabecera
	        PreparedStatement ps = con.prepareStatement(sqlCabecera, java.sql.Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, emailCliente);
	        ps.setDouble(2, total);
	        ps.executeUpdate();
	        
	      //ID
	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	            codPedido = rs.getInt(1);
	        }
	        ps.close();
	        
	        //Platos
	        if (codPedido != -1) {
	            PreparedStatement psLinea = con.prepareStatement(sqlLinea);
	            
	            for (int i = 0; i < platos.size(); i++) {
	                if (cantidades.get(i) > 0) {
	                    psLinea.setInt(1, codPedido);
	                    psLinea.setString(2, platos.get(i).getNombre());
	                    psLinea.setInt(3, cantidades.get(i));
	                    psLinea.setDouble(4, platos.get(i).getPrecio());
	                    psLinea.executeUpdate();
	                }
	            }
	            psLinea.close();
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1;
	    }
	    return codPedido;
	}
	
	// metodo que devuelve todas las reservas de un lugar en concreto
	public List<Reserva> recuperarReservas() {
	    List<Reserva> lista = new ArrayList<>();

	    String sql = "SELECT NUM_PERSONA, EMAIL_CLIENTE, FILA, COLUMNA FROM RESERVA"; // el * por alguna razon da problemas

	    try (PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        while (rs.next()) {
	            int num = rs.getInt("NUM_PERSONA");
	            String email = rs.getString("EMAIL_CLIENTE");
	            int fila = rs.getInt("FILA");
	            int columna = rs.getInt("COLUMNA");
	            Cliente c = new Cliente(email, "", TipoPersona.EXTERNO);

	            Reserva r = new Reserva(num, c);
	            r.setFila(fila);
	            r.setColumna(columna);

	            lista.add(r);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return lista;
	}
	
	//metodo que devuelve todos los platos
	public List<Plato> recuperarPlatos() {
	    List<Plato> lista = new ArrayList<>();
	    String sql = "SELECT * FROM PLATO ORDER BY CATEGORIA DESC, NOMBRE";
	    try (PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        while (rs.next()) {
	            lista.add(new Plato(
	                rs.getString("NOMBRE"),
	                rs.getDouble("PRECIO"),
	                rs.getString("CATEGORIA")
	            ));
	        }
	    } catch (SQLException e) { e.printStackTrace(); }
	    return lista;
	}
	
	// Método para recuperar los datos completos de un cliente
		public Cliente obtenerCliente(String email) {
			String sql = "SELECT * FROM CLIENTE WHERE EMAIL=?";
			Cliente c = null;
			
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, email);
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {
					String pass = rs.getString("CONTRASENIA");
					String tipoString = rs.getString("TIPO_PERSONA");
					
					TipoPersona tipo = TipoPersona.valueOf(tipoString);
					
					c = new Cliente(email, pass, tipo);
				}
				rs.close();
				ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return c;
		}
}
	

