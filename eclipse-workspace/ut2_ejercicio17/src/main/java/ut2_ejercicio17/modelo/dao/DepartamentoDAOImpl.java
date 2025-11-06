package ut2_ejercicio17.modelo.dao;

import java.sql.Connection;
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
				"SELECT * FROM empresa.departamentos WHERE dnombre LIKE ? AND loc LIKE ?");
				PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement(
						"INSERT INTO departamentos (dnombre, loc) VALUES (?, ?)")){
			sentencia0.setString(1, dpto.getDepNombre());
			sentencia0.setString(2, dpto.getDepLocalidad());
			try (ResultSet resultado = sentencia0.executeQuery()){
				// Ya existe el departamento 
				if (resultado.next()){
					return -1;
				}
			}
			sentencia.setString(1, dpto.getDepNombre());
			sentencia.setString(2, dpto.getDepLocalidad());
			return sentencia.executeUpdate();
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
}
