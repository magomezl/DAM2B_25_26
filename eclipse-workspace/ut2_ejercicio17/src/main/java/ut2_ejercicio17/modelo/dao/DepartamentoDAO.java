package ut2_ejercicio17.modelo.dao;

import java.util.ArrayList;

import ut2_ejercicio17.modelo.dto.DepartamentoDTO;

public interface DepartamentoDAO {
	/**
	 * 
	 * @param dpto 
	 * @return 0 en caso de que el departamento no se añada o el id del departamento añadido
	 */
	int anadirDpto(DepartamentoDTO dpto);
	int eliminarDpto(int dptoNum);
	int modificarDpto(int dptoNum, DepartamentoDTO dpto);
	ArrayList<DepartamentoDTO> listarDptos();
	/**
	 * 
	 * @param nombre nombre del departamento a buscar
	 * @param localidad localidad del departamento a buscar
	 * @return id del departamento buscado 0 en caso de no encontrar el departamento con ese nombre y localidad
	 */
	int buscarDpto(String nombre, String localidad);
	int creaDepartamentosTablaSiNoExiste();
}
