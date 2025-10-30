package ut2_ejercicio17.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import ut2_ejercicio17.main.Conexion;
import ut2_ejercicio17.modelo.dto.DepartamentoDTO;

public class DepartamentoDAOImpl implements DepartamentoDAO {

	@Override
	public int anadirDpto(DepartamentoDTO dpto) {
		
		PreparedStatement sentencia;
		try {
			sentencia = new Conexion().getCon().prepareStatement(
					"INSERT INTO departamentos (dnombre, loc) VALUES (?, ?)");
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modificarDpto(int dptoNum, DepartamentoDTO dpto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<DepartamentoDTO> listarDptos() {
		// TODO Auto-generated method stub
		return null;
	}

}
