package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import modelo.bd.ConexionDB;
import modelo.bd.ConexionDOC;
import modelo.dto.AlumnoDTO;
import modelo.dto.EmpresaDTO;

public class AlumnoDAOImpl implements AlumnoDAO {

	@Override
	public int anadirAlumno(AlumnoDTO alumno) {
		try(PreparedStatement sentencia0 = ConexionDB.getInstance().getCon().prepareStatement(
				"SELECT * FROM alumnos WHERE nombre LIKE ? AND apellidos LIKE ? ");
			PreparedStatement sentencia = ConexionDB.getInstance().getCon().prepareStatement(
				"INSERT INTO alumnos (nombre, apellidos, ciclo, curso, id_empresa) VALUES (?, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			sentencia0.setString(1, alumno.getNombre());
			sentencia0.setString(2, alumno.getApellidos());
			
			try (ResultSet resultado = sentencia0.executeQuery()){
				// Ya existe el alumno 
				if (resultado.next()){
					return resultado.getInt(1);
				}
			}
			sentencia.setString(1, alumno.getNombre());
			sentencia.setString(2, alumno.getApellidos());
			sentencia.setString(3, alumno.getCiclo());
			sentencia.setString(4, alumno.getCurso());
			sentencia.setInt(5, alumno.getId_empresa());
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
	public ArrayList<AlumnoDTO> listarAlumnos() {
		ArrayList<AlumnoDTO> alAlumnos = new ArrayList<AlumnoDTO>();;
		try(PreparedStatement sentencia = ConexionDB.getInstance().getCon().prepareStatement(
				"SELECT * FROM alumnos")) {
			ResultSet resultado = sentencia.executeQuery();
			while(resultado.next()) {
				alAlumnos.add(new AlumnoDTO(resultado.getInt(1), resultado.getString(2), resultado.getString(3), 
						resultado.getString(4), resultado.getString(5), resultado.getInt(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alAlumnos;
	}
	
	
	@Override
	public ArrayList<AlumnoDTO> listarAlumnos(int idEmpresa) {
		ArrayList<AlumnoDTO> alAlumnos = new ArrayList<AlumnoDTO>();;
		try(PreparedStatement sentencia = ConexionDB.getInstance().getCon().prepareStatement(
				"SELECT * FROM alumnos WHERE id_empresa=?")) {
			sentencia.setInt(1, idEmpresa);
			ResultSet resultado = sentencia.executeQuery();
			while(resultado.next()) {
				alAlumnos.add(new AlumnoDTO(resultado.getInt(1), resultado.getString(2), resultado.getString(3), 
						resultado.getString(4), resultado.getString(5), resultado.getInt(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alAlumnos;
	}
	

	@Override
	public ArrayList<AlumnoDTO> leeAlumno() {
		ArrayList<AlumnoDTO> alAlumno = new ArrayList<AlumnoDTO>();
		Sheet hoja = ConexionDOC.getInstance().getCon().getSheet("alumnos");
		int numFila = 1;
		Row fila = hoja.getRow(numFila);
		while (fila!=null) {
			AlumnoDTO alu = new AlumnoDTO();
			alu.setNombre(fila.getCell(0).getStringCellValue());
			alu.setApellidos(fila.getCell(1).getStringCellValue());
			alu.setCiclo(fila.getCell(2).getStringCellValue());
			alu.setCurso(fila.getCell(3).getStringCellValue());
			if (fila.getCell(4)!=null) {
				EmpresaDTO emp = new EmpresaDTO(fila.getCell(4).getStringCellValue());
				EmpresaDAO empDAO = new EmpresaDAOImpl();
				// Si existe empresa con ese nombre no la añade porque ya estaría
				int claveEmpresa = empDAO.anadirEmpresa(emp);
				alu.setId_empresa(claveEmpresa);
			}
			alAlumno.add(alu);
			fila = hoja.getRow(numFila++);
		}
			
		return alAlumno;
	}

	@Override
	public Element creaElementoAlumno(Document doc, AlumnoDTO alu) {
		Element eleAlu = doc.createElement("alumno");
		eleAlu.setAttribute("nombre", alu.getNombre());
		eleAlu.setAttribute("apellidos", alu.getApellidos());
		eleAlu.setAttribute("ciclo", alu.getCiclo());
		eleAlu.setAttribute("curso", alu.getCurso());
		return eleAlu;
	}

}
