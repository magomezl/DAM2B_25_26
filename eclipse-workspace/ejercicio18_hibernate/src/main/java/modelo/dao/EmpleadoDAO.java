package modelo.dao;

import java.util.List;



import modelo.dto.hibernate.Empleados;
/**
 * @author magomezl. Mª Aurora Gómez López
 * @version 1.0
 */
public interface EmpleadoDAO {
	void anadirEmpleado(Empleados emp);
	void modificarEmpleado(int emp, Empleados empNew);
	void eliminarEmpleado(int dpto);
	List<Empleados> listarEmpleados();
}
