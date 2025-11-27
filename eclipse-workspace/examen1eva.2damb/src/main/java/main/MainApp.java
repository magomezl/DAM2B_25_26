package main;

import modelo.dao.AlumnoDAO;
import modelo.dao.AlumnoDAOImpl;
import modelo.dao.EmpresaDAO;
import modelo.dao.EmpresaDAOImpl;
import modelo.dto.AlumnoDTO;
import modelo.dto.EmpresaDTO;

public class MainApp {
	private static EmpresaDAO empDAO = new EmpresaDAOImpl();
	private static AlumnoDAO aluDAO = new AlumnoDAOImpl();
	
	public static void main(String[] args) {
		
		for(EmpresaDTO emp: empDAO.leerEmpresas()) {
			empDAO.anadirEmpresa(emp);
		}
		
		for(AlumnoDTO alu: aluDAO.leeAlumno()) {
			aluDAO.anadirAlumno(alu);
		}
		
		System.out.println("DESPUES DE AÑADIR EMPRESAS");
		System.out.println("Empresas en la db");
		System.out.println(empDAO.listarEmpresas());
		System.out.println("Alumnos en la db");
		System.out.println(aluDAO.listarAlumnos());
		
		creaDocumentoDOM();

	}

	private static void creaDocumentoDOM() {
		
		
	}

}
