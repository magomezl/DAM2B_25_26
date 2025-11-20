package ut2_ejercicio17.main;

import java.sql.SQLException;
import java.util.Scanner;

import ut2_ejercicio17.modelo.dao.DepartamentoDAO;
import ut2_ejercicio17.modelo.dao.DepartamentoDAOImpl;
import ut2_ejercicio17.modelo.dao.EmpleadoDAO;
import ut2_ejercicio17.modelo.dao.EmpleadoDAOImpl;
import ut2_ejercicio17.modelo.db.Conexion;
import ut2_ejercicio17.modelo.dto.DepartamentoDTO;
import ut2_ejercicio17.modelo.dto.EmpleadoDTO;

public class MainApp {
	private static DepartamentoDAO dptDAO = new DepartamentoDAOImpl();
	private static EmpleadoDAO empDAO = new EmpleadoDAOImpl();

	public static void main(String[] args) {
		
		
		dptDAO.creaDepartamentosTablaSiNoExiste();
		
		Scanner sc = new Scanner(System.in);
		int opcion;
		do {
			System.out.println("\n===== MENÚ PRINCIPAL =====");
			System.out.println("1. Gestionar Departamentos");
			System.out.println("2. Gestionar Empleados");
			System.out.println("0. Salir");
			System.out.print("Elige una opción: ");
			opcion = sc.nextInt();
			
			switch (opcion) {
			case 1:
				menuDepartamentos(sc);
				break;
			case 2:
				menuEmpleados(sc);
				break;
			case 0:
				
				//TODO método para cerrar conexión
				
				System.out.println("¡Hasta pronto!");
				break;
			default:
				System.out.println("Opción no válida.");
			}
		} while (opcion != 0);
		sc.close();
	}
	
	private static void menuDepartamentos(Scanner sc) {
		
		int opcion;
		do {
			System.out.println("\n--- GESTIÓN DE DEPARTAMENTOS ---");
			System.out.println("1. Listar departamentos");
			System.out.println("2. Añadir departamento");
			System.out.println("3. Eliminar departamento");
			System.out.println("4. Modificar departamento");
			System.out.println("0. Volver al menú principal");
			System.out.print("Elige una opción: ");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				System.out.println("Listando departamentos...");
				System.out.println(dptDAO.listarDptos());
		    	break;
			case 2:
				sc.nextLine();
				System.out.println("Añadiendo departamento...");
				
				DepartamentoDTO dpto = new DepartamentoDTO();
				System.out.print("Introduce el nombre del departamento: ");
				dpto.setDepNombre(sc.nextLine());
				System.out.print("Introduce la localidad del departamento: ");
				dpto.setDepLocalidad(sc.nextLine());
				//TODO retocar anadirDpto
				System.out.println("Departamento añadido con clave (0 indica que no se añadió) " + dptDAO.anadirDpto(dpto));
				
				break;
			case 3:
				sc.nextLine();
				System.out.println("Eliminando departamento...");
				System.out.print("Introduce el nombre del departamento: ");
				String nombre = sc.nextLine();
				System.out.print("Introduce la localidad del departamento: ");
				String localidad = sc.nextLine();
				
				System.out.println(dptDAO.eliminarDpto(dptDAO.buscarDpto(nombre, localidad))==0 ? 
						"No se eliminó el departamento por no existir" : "Departamento eliminado con éxito");
				break;
			case 4:
				sc.nextLine();
				System.out.println("Modificando departamento...");
				break;
			case 0:
				System.out.println("Volviendo al menú principal...");
				break;
			default:
				System.out.println("Opción no válida.");
			}
		} while (opcion != 0);
	}

	private static void menuEmpleados(Scanner sc) {
		
		int opcion;
		do {
			System.out.println("\n--- GESTIÓN DE EMPLEADOS ---");
			System.out.println("1. Listar empleados");
			System.out.println("2. Añadir empleado");
			System.out.println("3. Eliminar empleado");
			System.out.println("4. Modificar empleado");
			System.out.println("0. Volver al menú principal");
			System.out.print("Elige una opción: ");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				System.out.println("Listando empleados...");
				System.out.println(empDAO.listarEmpleados());
				break;
			case 2:
				System.out.println("Añadiendo empleado...");
				sc.nextLine();
				System.out.println("Añadiendo empleado...");
				
				try {
					Conexion.getInstance().getCon().setAutoCommit(false);
					EmpleadoDTO emp = new EmpleadoDTO();
					System.out.print("Nombre: ");
					emp.setEmpNombre(sc.nextLine());
					System.out.print("Primer Apellido: ");
					emp.setEmpApellido1(sc.nextLine());
					System.out.print("Segundo Apellido: ");
					emp.setEmpApellido2(sc.nextLine());
					
					DepartamentoDTO dpto = new DepartamentoDTO();
					System.out.print("Nombre del departamento: ");
					dpto.setDepNombre(sc.nextLine());
					System.out.print("Localidad del departamento: ");
					dpto.setDepLocalidad(sc.nextLine());
					
					emp.setEmpDpto(dptDAO.anadirDpto(dpto));
					
					empDAO.anadirEmpleado(emp);
					Conexion.getInstance().getCon().commit();
					
				} catch (SQLException e) {
					try {
						Conexion.getInstance().getCon().rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
				}finally {
					try {
						Conexion.getInstance().getCon().setAutoCommit(true);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				break;
			case 3:
				System.out.println("Eliminando empleado...");
				break;
			case 4:
				System.out.println("Modificando empleado...");
				break;
			case 0:
				System.out.println("Volviendo al menú principal...");
				break;
			default:
				System.out.println("Opción no válida.");
			}
		} while (opcion != 0);
	}

}
