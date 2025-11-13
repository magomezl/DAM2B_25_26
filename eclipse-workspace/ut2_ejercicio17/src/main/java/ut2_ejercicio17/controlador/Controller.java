package ut2_ejercicio17.controlador;



import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ut2_ejercicio17.modelo.dao.DepartamentoDAO;
import ut2_ejercicio17.modelo.dao.DepartamentoDAOImpl;
import ut2_ejercicio17.modelo.dto.DepartamentoDTO;
import ut2_ejercicio17.modelo.dto.DepartamentoDTOPropiedadesJavaFX;


public class Controller {
	@FXML
	private TextField txtNombreDpto;
	
	@FXML
	private TextField txtLocalidadDpto;
	
	@FXML
	private TableView<DepartamentoDTOPropiedadesJavaFX> tablaDepartamentos;
	
	@FXML
	private TableColumn<DepartamentoDTOPropiedadesJavaFX, Integer> colIdDpto;
	
	@FXML
	private TableColumn<DepartamentoDTOPropiedadesJavaFX, String> colNombreDpto;
	
	@FXML
	private TableColumn<DepartamentoDTOPropiedadesJavaFX, String> colLocalidadDpto;
	
	
	
	
	@FXML
	private void initialize() {
		
		// Hacemos corresponder la columnas de la tabla de la vista con las propiedades del objeto con las propiedades
		// observables DepartamentoDTOPropiedadesJavaFX
		colIdDpto.setCellValueFactory(new PropertyValueFactory<>("depNum"));
		colNombreDpto.setCellValueFactory(new PropertyValueFactory<>("depNombre"));
		colLocalidadDpto.setCellValueFactory(new PropertyValueFactory<>("depLocalidad"));
		
		ArrayList<DepartamentoDTOPropiedadesJavaFX> alDptoFX = new ArrayList<DepartamentoDTOPropiedadesJavaFX>();
		DepartamentoDAO dptoDAO = new DepartamentoDAOImpl();
		for (DepartamentoDTO dpto : dptoDAO.listarDptos()) {
			DepartamentoDTOPropiedadesJavaFX dptoFX = new DepartamentoDTOPropiedadesJavaFX(dpto.getDepNum(), 
					dpto.getDepNombre(), dpto.getDepLocalidad());
			alDptoFX.add(dptoFX);
		}
		
		ObservableList<DepartamentoDTOPropiedadesJavaFX> listaDepartamentos = FXCollections.observableArrayList(alDptoFX);
		tablaDepartamentos.setItems(listaDepartamentos);
		
	}
	
	
	@FXML
	private void guardarDepartamento(ActionEvent event) {
		DepartamentoDTO dpto = new DepartamentoDTO();
		dpto.setDepNombre(txtNombreDpto.getText());
		dpto.setDepLocalidad(txtLocalidadDpto.getText());
		
		DepartamentoDAO dptoDAO = new DepartamentoDAOImpl();
		dptoDAO.anadirDpto(dpto);
	}
	
	

}
