package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import modelo.bd.ConexionDB;
import modelo.bd.ConexionDOC;
import modelo.dto.EmpresaDTO;

public class EmpresaDAOImpl implements EmpresaDAO {

	@Override
	public int anadirEmpresa(EmpresaDTO empresa) {
		try(PreparedStatement sentencia0 = ConexionDB.getInstance().getCon().prepareStatement(
				"SELECT * FROM empresas WHERE nombre LIKE ?");
			PreparedStatement sentencia = ConexionDB.getInstance().getCon().prepareStatement(
				"INSERT INTO empresas (nombre, direccion, telefono, persona_contacto, email) VALUES (?, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {
			sentencia0.setString(1, empresa.getNombre());
			try (ResultSet resultado = sentencia0.executeQuery()){
				// Ya existe la empresa 
				if (resultado.next()){
					return resultado.getInt("id_empresa");
				}
			}
			sentencia.setString(1, empresa.getNombre());
			sentencia.setString(2, empresa.getDireccion());
			sentencia.setString(3, empresa.getTelefono());
			sentencia.setString(4, empresa.getPersona_contacto());
			sentencia.setString(5, empresa.getEmail());
			sentencia.executeUpdate();
			try(ResultSet clave = sentencia.getGeneratedKeys()){
				if (clave.next()) {
					return clave.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<EmpresaDTO> listarEmpresas() {
		ArrayList<EmpresaDTO> alEmpresa = new ArrayList<EmpresaDTO>();;
		try(PreparedStatement sentencia = ConexionDB.getInstance().getCon().prepareStatement(
				"SELECT * FROM empresas")) {
			ResultSet resultado = sentencia.executeQuery();
			while(resultado.next()) {
				alEmpresa.add(new EmpresaDTO(resultado.getInt(1), resultado.getString(2), resultado.getString(3), 
						resultado.getString(4), resultado.getString(5), resultado.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alEmpresa;
	}

	@Override
	public ArrayList<EmpresaDTO> leerEmpresas() {
		ArrayList<EmpresaDTO> alEmpresa = new ArrayList<EmpresaDTO>();
		Sheet hoja = ConexionDOC.getInstance().getCon().getSheet("empresas");
		int numFila = 1;
		Row fila = hoja.getRow(numFila);
		while (fila!=null) {
			EmpresaDTO emp = new EmpresaDTO();
			emp.setNombre(fila.getCell(0).getStringCellValue());
			emp.setDireccion(fila.getCell(1).getStringCellValue());
			emp.setTelefono(fila.getCell(2).getStringCellValue());
			emp.setPersona_contacto(fila.getCell(3).getStringCellValue());
			emp.setEmail(fila.getCell(4).getStringCellValue());
			alEmpresa.add(emp);
			fila = hoja.getRow(numFila++);
		}
		return alEmpresa;
	}

}
