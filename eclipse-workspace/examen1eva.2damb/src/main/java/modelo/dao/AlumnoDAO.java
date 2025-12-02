package modelo.dao;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import modelo.dto.AlumnoDTO;

public interface AlumnoDAO {
	int anadirAlumno(AlumnoDTO alumno);
	ArrayList<AlumnoDTO> listarAlumnos();
	ArrayList<AlumnoDTO> listarAlumnos(int idEmpresa);
	ArrayList<AlumnoDTO> leeAlumno();
	Element creaElementoAlumno(Document doc, AlumnoDTO alu);
}
