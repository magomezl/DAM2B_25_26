package ut2_ejercicio17.modelo.dao;

import java.util.ArrayList;

import ut2_ejercicio17.modelo.dto.EmpleadoDTO;



public interface EmpleadoDAO {
	int anadirEmpleado(EmpleadoDTO dpto);
	int eliminarEmpleado(int dptoNum);
	int modificarEmpleado(int dptoNum, EmpleadoDTO dpto);
	ArrayList<EmpleadoDTO> listarEmpleados();
	//Para SQLite
	int creaTablaEmpleados();
	/**
	 * 
	 * @param nombre nombre del departamento a buscar
	 * @param localidad localidad del departamento a buscar
	 * @return id del departamento buscado 0 en caso de no encontrar el departamento con ese nombre y localidad
	 */
		
}

