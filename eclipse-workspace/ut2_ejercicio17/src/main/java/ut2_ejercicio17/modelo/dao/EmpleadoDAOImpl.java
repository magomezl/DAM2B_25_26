package ut2_ejercicio17.modelo.dao;


import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ut2_ejercicio17.modelo.db.Conexion;
import ut2_ejercicio17.modelo.dto.EmpleadoDTO;

//TODO esta todo sin testear pero creo que está correcto
public class EmpleadoDAOImpl implements EmpleadoDAO {
	
	@Override
	public ArrayList<EmpleadoDTO> listarEmpleados() {
		ArrayList<EmpleadoDTO> alEmpleados = null;
		
		try(PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement("SELECT * FROM empleados");
				ResultSet resultado = sentencia.executeQuery()	) {
			alEmpleados = new ArrayList<EmpleadoDTO>();
			while (resultado.next()) {
				alEmpleados.add(new EmpleadoDTO(resultado.getInt(1), resultado.getString(2), resultado.getString(3), resultado.getString(4), resultado.getInt(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alEmpleados;
	}


	@Override
	public int anadirEmpleado(EmpleadoDTO empleado) {
		try (PreparedStatement sentencia0 = Conexion.getInstance().getCon().prepareStatement(
				"SELECT * FROM empresa.empleados WHERE nombre LIKE ? AND apellido1 LIKE ? AND apellido2 LIKE ? AND departamento = ?");
				PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement(
						"INSERT INTO empleados (nombre, apellido1, apellido2, departamento) VALUES (?, ?, ?, ?)")){
			sentencia0.setString(1, empleado.getEmpNombre());
			sentencia0.setString(2, empleado.getEmpApellido1());
			sentencia0.setString(3, empleado.getEmpApellido2());
			sentencia0.setInt(4, empleado.getEmpDpto());
			try (ResultSet resultado = sentencia0.executeQuery()){
				// Ya existe el departamento 
				if (resultado.next()){
					return -1;
				}
			}
			sentencia.setString(1, empleado.getEmpNombre());
			sentencia.setString(2, empleado.getEmpApellido1());
			sentencia.setString(3, empleado.getEmpApellido2());
			sentencia.setInt(4, empleado.getEmpDpto());
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int eliminarEmpleado(int empleadoNum) {
		try (PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement("DELETE FROM empleados WHERE id = ?")){
			sentencia.setInt(1, empleadoNum);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}


	@Override
	public int modificarEmpleado(int empleadoNum, EmpleadoDTO empleado) {
		try (PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement("UPDATE empleados SET nombre = ?, apellido1 = ?, apellido2 = ?, departamento = ? WHERE id = ?")){
			sentencia.setString(1, empleado.getEmpNombre());
			sentencia.setString(2, empleado.getEmpApellido1());
			sentencia.setString(3, empleado.getEmpApellido2());
			sentencia.setInt(4, empleado.getEmpDpto());
			sentencia.setInt(5, empleadoNum);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}


	@Override
	public int creaTablaEmpleados() {
		boolean flag = false;
		try {
			DatabaseMetaData dbmd = Conexion.getInstance().getCon().getMetaData();
			// Obtenemos la información de las tablas que contiene
			String[] tipos = {"TABLE"};
			ResultSet resul = dbmd.getTables(null, null, null, tipos);
			while(resul.next()) {
				if (resul.getString(3).equals("empleados")) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Si no existe la tabla departamentos, se crea. Si no existe la tabla departamentos se entiende que no existe la tabla empleados ya que 
		// depende de ella.
		if (!flag) {
			try(PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement(
					"CREATE TABLE empleados (id integer not null primary key autoincrement, nombre varchar(15), apellido1 varchar(15), "
					+ "apellido2 varchar(15), departamento integer, FOREIGN KEY (departamento) REFERENCES departamentos(dept_no))")){
				sentencia.executeUpdate();
				
			} catch (SQLException e) {
				return -1;
			}
		}
		return 0;
	}


	

	

	
	
}
