package controlador;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import modelo.dao.DepartamentoDAO;
import modelo.dao.DepartamentoDAOImpl;
import modelo.dao.EmpleadoDAO;
import modelo.dao.EmpleadoDAOImpl;
import modelo.dto.PropObservables_javafx.DepartamentoDTOPropiedadesJavaFX;
import modelo.dto.PropObservables_javafx.EmpleadoDTOPropiedadesJavaFX;
import modelo.dto.hibernate.Departamentos;
import modelo.dto.hibernate.Empleados;

public class Controller {
	
	private static DepartamentoDAO dptoDAO = new DepartamentoDAOImpl();
	private static DepartamentoDTOPropiedadesJavaFX dptoSeleccionado;
	private static EmpleadoDTOPropiedadesJavaFX empleadoSeleccionado;
	private static EmpleadoDAO empDAO = new EmpleadoDAOImpl();
	private Map<Integer, String> mapaDepartamentos; 

	@FXML
	private ComboBox<Departamentos> ComboDptoEmpleado;

	@FXML
	private TableColumn<EmpleadoDTOPropiedadesJavaFX, String> colApellido1Empleado;

	@FXML
	private TableColumn<EmpleadoDTOPropiedadesJavaFX, String> colApellido2Empleado;

	@FXML
	private TableColumn<EmpleadoDTOPropiedadesJavaFX, String> colDptoEmpleado;

	@FXML
	private TableColumn<DepartamentoDTOPropiedadesJavaFX, Integer> colIdDpto;

	@FXML
	private TableColumn<EmpleadoDTOPropiedadesJavaFX, Integer> colIdEmpleado;

	@FXML
	private TableColumn<DepartamentoDTOPropiedadesJavaFX, String> colLocalidadDpto;

	@FXML
	private TableColumn<DepartamentoDTOPropiedadesJavaFX, String> colNombreDpto;

	@FXML
	private TableColumn<EmpleadoDTOPropiedadesJavaFX, String> colNombreEmpleado;

	@FXML
	private TableView<DepartamentoDTOPropiedadesJavaFX> tablaDepartamentos;

	@FXML
	private TableView<EmpleadoDTOPropiedadesJavaFX> tablaEmpleados;

	@FXML
	private TextField txtApellido1Empleado;

	@FXML
	private TextField txtApellido2Empleado;

	@FXML
	private TextField txtLocalidadDpto;

	@FXML
	private TextField txtNombreDpto;

	@FXML
	private TextField txtNombreEmpleado;

	@FXML
	void eliminarDepartamento(ActionEvent event) {
		dptoDAO.eliminarDpto(dptoSeleccionado.getDepNum());
		cargarDatosDptos();
	}

	@FXML
	void eliminarEmpleado(ActionEvent event) {
		empDAO.eliminarEmpleado(empleadoSeleccionado.getEmpNum());
		cargarDatosEmpleados();
	}

	@FXML
	void guardarDepartamento(ActionEvent event) {
		dptoDAO.anadirDpto(new Departamentos(txtNombreDpto.getText(), txtLocalidadDpto.getText(), null));
		cargarDatosDptos();
	}

	@FXML
	void guardarEmpleado(ActionEvent event) {
		empDAO.anadirEmpleado(new Empleados(ComboDptoEmpleado.getSelectionModel().getSelectedItem(), txtNombreEmpleado.getText(), txtApellido1Empleado.getText(), txtApellido2Empleado.getText()));
		cargarDatosEmpleados();
	}

	@FXML
	void modificarDepartamento(ActionEvent event) {
		dptoDAO.modificarDpto(dptoSeleccionado.getDepNum(), new Departamentos(txtNombreDpto.getText(), txtLocalidadDpto.getText(), null));
		cargarDatosDptos();
	}

	@FXML
	void modificarEmpleado(ActionEvent event) {
		empDAO.modificarEmpleado(empleadoSeleccionado.getEmpNum(), 
				new Empleados(ComboDptoEmpleado.getSelectionModel().getSelectedItem(), txtNombreEmpleado.getText(), txtApellido1Empleado.getText(), txtApellido2Empleado.getText()));
		cargarDatosEmpleados();
	
	}

	@FXML
	void seleccionarDpto(MouseEvent event) {
		dptoSeleccionado =  tablaDepartamentos.getSelectionModel().getSelectedItem();
		if (dptoSeleccionado!=null) {
			txtNombreDpto.setText(dptoSeleccionado.getDepNombre());
			txtLocalidadDpto.setText(dptoSeleccionado.getDepLocalidad());
		}
	}

	 @FXML
	 void seleccionarEmpleado(MouseEvent event) {
		 empleadoSeleccionado =  tablaEmpleados.getSelectionModel().getSelectedItem();
			if (empleadoSeleccionado!=null) {
				txtNombreEmpleado.setText(empleadoSeleccionado.getEmpNombre());
				txtApellido1Empleado.setText(empleadoSeleccionado.getEmpApellido1());
				txtApellido2Empleado.setText(empleadoSeleccionado.getEmpApellido2());
				ComboDptoEmpleado.setValue(dptoDAO.listarDptos(empleadoSeleccionado.getEmpDpto()));
			}
	 }

	
	@FXML
	void initialize() {
		// Indicamos con que se cargarán las columnas de la tableView tablaDepartamentos
		colIdDpto.setCellValueFactory(new PropertyValueFactory<>("depNum"));
		colNombreDpto.setCellValueFactory(new PropertyValueFactory<>("depNombre"));
		colLocalidadDpto.setCellValueFactory(new PropertyValueFactory<>("depLocalidad"));

		cargarDatosDptos();

		// Indicamos con que se cargarán las columnas de la tableView tablaEmpleados
		colIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("empNum"));
		colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<>("empNombre"));
		colApellido1Empleado.setCellValueFactory(new PropertyValueFactory<>("empApellido1"));
		colApellido2Empleado.setCellValueFactory(new PropertyValueFactory<>("empApellido2"));
		// Callback es una interface de JavaFX que define el método call() que ejecutará para cada celda.
		mapaDepartamentos = dptoDAO.obtenerMapaDptos();
		colDptoEmpleado.setCellValueFactory(
				new Callback<>() {

					@Override
					public ObservableValue<String> call(
							CellDataFeatures<EmpleadoDTOPropiedadesJavaFX, String> param) {
						// Objeto de la fila procesado
						
						EmpleadoDTOPropiedadesJavaFX empleado = param.getValue(); 
    					String nombreDepto = mapaDepartamentos.getOrDefault(empleado.getEmpDpto(), "Desconocido");
    					return new SimpleStringProperty(nombreDepto);
					}
				}
		);
		cargarDatosEmpleados();
	}

	private void cargarDatosEmpleados() {
		List<EmpleadoDTOPropiedadesJavaFX> alEmpJFX = new ArrayList<EmpleadoDTOPropiedadesJavaFX>();
		for (Empleados emp: empDAO.listarEmpleados()) {
			EmpleadoDTOPropiedadesJavaFX empJFX = new EmpleadoDTOPropiedadesJavaFX(emp.getId(), emp.getNombre(), emp.getApellido1(), emp.getApellido2(), emp.getDepartamentos().getDeptNo());
			alEmpJFX.add(empJFX);
		}
		tablaEmpleados.setItems(FXCollections.observableArrayList(alEmpJFX));
		
		txtNombreEmpleado.setText(null);
		txtApellido1Empleado.setText(null); 
		txtApellido2Empleado.setText(null);
		ComboDptoEmpleado.getSelectionModel().clearSelection();
	}

	private void cargarDatosDptos() {
		List<DepartamentoDTOPropiedadesJavaFX> alDptoJFX = new ArrayList<DepartamentoDTOPropiedadesJavaFX>();
		for (Departamentos dpto: dptoDAO.listarDptos()) {
			DepartamentoDTOPropiedadesJavaFX dptoJFX = new DepartamentoDTOPropiedadesJavaFX(dpto.getDeptNo(), dpto.getDnombre(), dpto.getLoc());
			alDptoJFX.add(dptoJFX);
		}
		tablaDepartamentos.setItems(FXCollections.observableArrayList(alDptoJFX));
		// El combo tiene que estar tipado en su declaración con la clase de la que toma los datos 
		// en este caso Departamentos y en esta clase tiene que haber un método toString que muestre como 
		// se verán los datos en el combo
		ComboDptoEmpleado.getItems().clear();
		ComboDptoEmpleado.getItems().addAll(dptoDAO.listarDptos());
		txtNombreDpto.setText(null);
		txtLocalidadDpto.setText(null);
		
	}
}
