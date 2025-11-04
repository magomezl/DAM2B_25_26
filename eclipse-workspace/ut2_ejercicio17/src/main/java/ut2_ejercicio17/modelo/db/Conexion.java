package ut2_ejercicio17.modelo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static Conexion instance;
	private Connection con;
	
	private Conexion() {
		try {
			Class.forName(ParametroConexion.driverMysql);
			con = DriverManager.getConnection(ParametroConexion.cadenaConectionMysql, ParametroConexion.usuarioMysql, ParametroConexion.contraseniaMysql);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver no encontrado");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error de conexión");
			e.printStackTrace();
		}
	}
	public static Conexion getInstance() {
		if (instance==null) {
			instance = new Conexion();
		}
		return instance;
	}
	
	public Connection getCon() {
		return con;
	}
	
	public void cerrarConexion() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
