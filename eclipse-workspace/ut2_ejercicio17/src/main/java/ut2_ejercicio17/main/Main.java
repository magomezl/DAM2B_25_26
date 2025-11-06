package ut2_ejercicio17.main;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import ut2_ejercicio17.modelo.dao.DepartamentoDAO;
import ut2_ejercicio17.modelo.dao.DepartamentoDAOImpl;
import ut2_ejercicio17.modelo.db.Conexion;
import ut2_ejercicio17.modelo.dto.DepartamentoDTO;

public class Main extends Application{
	
	public static void main(String[] args) {
		
		DepartamentoDAO dptDAO = new DepartamentoDAOImpl();
		DepartamentoDTO dptoDTO = new DepartamentoDTO();
//		dptoDTO.setDepNombre("Formacion");
//		dptoDTO.setDepLocalidad("Murcia");
//		dptDAO.anadirDpto(dptoDTO);
//		
//		dptDAO.eliminarDpto(8);
//		dptDAO.eliminarDpto(9);
//		dptDAO.eliminarDpto(10);
//		dptDAO.eliminarDpto(11);
//		dptDAO.eliminarDpto(12);
//		dptDAO.eliminarDpto(13);
//		dptDAO.eliminarDpto(14);
//		dptDAO.eliminarDpto(15);
//		
//		
//		dptoDTO.setDepNombre("RRHH");
//		dptoDTO.setDepLocalidad("Madrid");
//		dptDAO.modificarDpto(16, dptoDTO);
		
		ArrayList<DepartamentoDTO> alDpto = new ArrayList<DepartamentoDTO>();
		
		alDpto = dptDAO.listarDptos();
		for(DepartamentoDTO dpto: alDpto) {
			System.out.println(dpto);
		}
		Conexion.getInstance().cerrarConexion();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
