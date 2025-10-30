package ut2_ejercicio17.main;

import ut2_ejercicio17.modelo.dao.DepartamentoDAO;
import ut2_ejercicio17.modelo.dao.DepartamentoDAOImpl;
import ut2_ejercicio17.modelo.dto.DepartamentoDTO;

public class Main {
	
	public static void main(String[] args) {
		
		DepartamentoDAO dptDAO = new DepartamentoDAOImpl();
		DepartamentoDTO dptoDTO = new DepartamentoDTO();
		dptoDTO.setDepNombre("Emprendimiento");
		dptoDTO.setDepLocalidad("Burgos");
		dptDAO.anadirDpto(dptoDTO);

	}

}
