package ut2_ejercicio17.modelo.db;

public class ParametroConexion {
	/**
	public static final String driverMysql = "com.mysql.cj.jdbc.Driver";
	public static final String cadenaConectionMysql = "jdbc:mysql://10.196.55.168:3306/empresa";
	public static final String usuarioMysql = "root";
	public static final String contraseniaMysql = "toor";
	**/

	public static final String driverMysql = "org.sqlite.JDBC";
	public static final String cadenaConectionMysql = "jdbc:sqlite:src\\main\\resources\\dbSQLite\\empresa.db";
	public static final String usuarioMysql = "";
	public static final String contraseniaMysql = "";
	
}
