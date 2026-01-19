package modelo.dao;

import java.util.List;
import java.util.Map;

import modelo.dto.hibernate.Departamentos;
/**
 * @author magomezl. Mª Aurora Gómez López
 * @version 1.0
 */
public interface DepartamentoDAO {
	
	/**
	 * Añade un departamento a la base de datos  
	 * @param dpto objeto de la clase Departamentos que se añade
	 */
	void anadirDpto(Departamentos dpto);
	
	/**
	 * Modifica los datos de un departamento
	 * @param dptoOld objeto de la clase Departamentos que contiene el departamento a modificar
	 * @param dptoNew objeto de la clase Departamentos que contiene los nuevos datos del departamento
	 */
	void modificarDpto(Departamentos dptoOld, Departamentos dptoNew);
	
	/**
	 * Modifica los datos de un departamento
	 * @param dptoOld id del objeto a modificar
	 * @param dptoNew objeto de la clase Departamentos que contiene los nuevos datos del departamento
	 */
	void modificarDpto(int dptoOld, Departamentos dptoNew);
	
	/**
	 * Elimina un departamento de la base de datos
	 * @param dpto id del departamento a eliminar
	 */
	void eliminarDpto(int dpto);
	
	void eliminarDpto(Departamentos dpto);
	
	/**
	 * Obtiene los departamentos almacenados en la DB
	 * @return Listado de departamentos 
	 */
	List<Departamentos> listarDptos();
	/**
	 * Obtiene los departamentos que se encuentran en una localidad
	 * @param localidad 
	 * @return Listado de departamentos en la localidad pasada como parámetro
	 */
	List<Departamentos> listarDptos(String localidad);
	
	Departamentos listarDptos(String nombre, String localidad);
	
	List<Departamentos> listarDptosNombre(String nombre);
	
	Departamentos listarDptos(int id);
	
	Map<Integer, String> obtenerMapaDptos();
	
}
