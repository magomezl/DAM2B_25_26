package ut2_ejercicio17.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private Connection con;
	
	public Conexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://10.196.55.168:3306/empresa", "root", "toor");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver no encontrado");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error de conexión");
			e.printStackTrace();
		}
	}
	public Connection getCon() {
		return con;
	}
	
	public void cerrarConexion() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
