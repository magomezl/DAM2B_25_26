package ut2_ejercicio17.modelo.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ut2_ejercicio17.modelo.db.Conexion;
import ut2_ejercicio17.modelo.dto.DepartamentoDTO;
//TODO esta todo sin testear pero creo que está correcto
public class DepartamentoDAOImpl implements DepartamentoDAO {
	@Override
	public int anadirDpto(DepartamentoDTO dpto) {
		try (PreparedStatement sentencia0 = Conexion.getInstance().getCon().prepareStatement(
				"SELECT * FROM departamentos WHERE dnombre LIKE ? AND loc LIKE ?");
				PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement(
						"INSERT INTO departamentos (dnombre, loc) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
			sentencia0.setString(1, dpto.getDepNombre());
			sentencia0.setString(2, dpto.getDepLocalidad());
			try (ResultSet resultado = sentencia0.executeQuery()){
				// Ya existe el departamento 
				if (resultado.next()){
					return 0;
				}
			}
			sentencia.setString(1, dpto.getDepNombre());
			sentencia.setString(2, dpto.getDepLocalidad());
			sentencia.executeUpdate();
			try(ResultSet clave = sentencia.getGeneratedKeys()){
				if(clave.next()) {
					return clave.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int eliminarDpto(int dptoNum) {
		try (PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement("DELETE FROM departamentos WHERE dept_no = ?")){
			sentencia.setInt(1, dptoNum);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int modificarDpto(int dptoNum, DepartamentoDTO dpto) {
		try (PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement("UPDATE departamentos SET dnombre = ?, loc = ? WHERE dept_no = ?")){
			sentencia.setString(1, dpto.getDepNombre());
			sentencia.setString(2, dpto.getDepLocalidad());
			sentencia.setInt(3, dptoNum);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<DepartamentoDTO> listarDptos() {
		ArrayList<DepartamentoDTO> alDpto = null;
		try(PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement("SELECT * FROM departamentos");
				ResultSet resultado = sentencia.executeQuery()	) {
			alDpto = new ArrayList<DepartamentoDTO>();
			while (resultado.next()) {
				alDpto.add(new DepartamentoDTO(resultado.getInt(1), resultado.getString(2), resultado.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alDpto;
	}

	@Override
	public int buscarDpto(String nombre, String localidad) {
		try(PreparedStatement sentencia0 = Conexion.getInstance().getCon().prepareStatement(
				"SELECT * FROM departamentos WHERE dnombre LIKE ? AND loc LIKE ?")){
			sentencia0.setString(1, nombre);
			sentencia0.setString(2, localidad);
			
			try (ResultSet resultado = sentencia0.executeQuery()){
				// Existe el departamento 
				if (resultado.next()){
					return resultado.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int creaDepartamentosTablaSiNoExiste() {
		boolean flag = false;
		try {
			DatabaseMetaData dbmd = Conexion.getInstance().getCon().getMetaData();
			// Obtenemos la información de las tablas que contiene
			String[] tipos = {"TABLE"};
			ResultSet resul = dbmd.getTables(null, null, null, tipos);
			while(resul.next()) {
				if (resul.getString(3).equals("departamentos")) {
					flag = true;
				}
			}
			
			//Si no existe la tabla departamentos, se crea. Si no existe la tabla departamentos se entiende que no existe la tabla empleados ya que 
			// depende de ella.
			if (!flag) {
				try(PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement(
						"CREATE TABLE departamentos (dept_no integer not null primary key autoincrement, dnombre varchar(15), loc varchar(15))");
					PreparedStatement sentenciabis = Conexion.getInstance().getCon().prepareStatement(
						"CREATE TABLE empleados (id integer not null primary key autoincrement, nombre varchar(15), apellido1 varchar(15), "
						+ "apellido2 varchar(15), departamento integer, FOREIGN KEY (departamento) REFERENCES departamentos(dept_no))")){
					sentencia.executeUpdate();
					sentenciabis.executeUpdate();
					
				} catch (SQLException e) {
					return -1;
				}
			}
			return 0;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
