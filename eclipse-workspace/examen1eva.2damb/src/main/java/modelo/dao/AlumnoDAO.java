package modelo.dao;

import java.util.ArrayList;

import modelo.dto.AlumnoDTO;

public interface AlumnoDAO {
	int anadirAlumno(AlumnoDTO alumno);
	ArrayList<AlumnoDTO> listarAlumnos();
	ArrayList<AlumnoDTO> leeAlumno();
}
