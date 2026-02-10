package controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.dao.hibernate.HibernateDAO;
import modelo.dao.hibernate.HibernateDAOImpl;
import modelo.dao.mongo.MongoDAO;
import modelo.dao.mongo.MongoDAOImpl;
import modelo.dto.hibernate.Autores;
import modelo.dto.hibernate.Generos;

import modelo.dto.hibernate.Nacionalidades;
import modelo.dto.observableFX.AutorDTOPropiedadesJavaFX;
import modelo.dto.observableFX.GenericaDTOPropiedadesJavaFX;

public class Controlador {
	private static MongoDAO mongoDAO = new MongoDAOImpl();
	private static HibernateDAO hibernateDAO = new HibernateDAOImpl();
	
	
    @FXML
    private ComboBox<?> ComboAutores;
    
    @FXML
    private TableView<GenericaDTOPropiedadesJavaFX> tablaGeneros;
    @FXML
    private TableColumn<GenericaDTOPropiedadesJavaFX, String> colGeneroLibro;

    @FXML
    private TableView<GenericaDTOPropiedadesJavaFX> tablaNacionalidades;
    @FXML
    private TableColumn<GenericaDTOPropiedadesJavaFX, String> colNacionalidad;

    @FXML
    private TableView<AutorDTOPropiedadesJavaFX> tablaAutores;
    @FXML
    private TableColumn<AutorDTOPropiedadesJavaFX, Integer> colMuerteAutor;
    @FXML
    private TableColumn<AutorDTOPropiedadesJavaFX, String> colNacionalidadAutor;
    @FXML
    private TableColumn<AutorDTOPropiedadesJavaFX, Integer> colNacimientoAutor;
    @FXML
    private TableColumn<AutorDTOPropiedadesJavaFX, String> colNombreAutor;

    
    @FXML
    private TableView<AutorDTOPropiedadesJavaFX> tablaAutorias;

    @FXML
    private TableColumn<AutorDTOPropiedadesJavaFX, Integer> colMuerteAutoria;
    @FXML
    private TableColumn<AutorDTOPropiedadesJavaFX, Integer> colNacimientoAutoria;
    @FXML
    private TableColumn<AutorDTOPropiedadesJavaFX, String> colNacionalidadAutoria;
    @FXML
    private TableColumn<AutorDTOPropiedadesJavaFX, String> colNombreAutoria;

    @FXML
    private TableColumn<?, ?> colAnioLibro;
    
    @FXML
    private TableColumn<?, ?> colGeneroLibroEnLibros;

   
    @FXML
    private TableColumn<?, ?> colTituloLibro;

   
   
   

    @FXML
    private TableView<?> tablaLibros;

    
    @FXML
    private TextField txtAnioLibro;

    @FXML
    private TextField txtGeneroLibro;

    @FXML
    private TextField txtMuerteAutor;

    @FXML
    private TextField txtNacimientoAutor;

    @FXML
    private TextField txtNacionalidad;

    @FXML
    private TextField txtNacionalidadAutor;

    @FXML
    private TextField txtNombreAutor;

    @FXML
    private TextField txtNombreLibroAutoria;

    @FXML
    private TextField txtTituloLibro;
    
    @FXML
    void agregarAutor(ActionEvent event) {

    }

    @FXML
    void buscarLibroAutoria(ActionEvent event) {
    	List<Autores> autoresLibro = hibernateDAO.buscarAutoresDeLibro(this.txtNombreLibroAutoria.getText().toLowerCase());
    	cargarDatosAutores(autoresLibro, this.tablaAutorias);
    }

    @FXML
    void eliminarAutor(ActionEvent event) {

    }

    @FXML
    void eliminarAutoria(ActionEvent event) {

    }

    @FXML
    void eliminarGenero(ActionEvent event) {

    }

    @FXML
    void eliminarLibro(ActionEvent event) {

    }

    @FXML
    void eliminarNacionalidad(ActionEvent event) {

    }

    @FXML
    void guardarAutor(ActionEvent event) {

    }

    @FXML
    void guardarAutoria(ActionEvent event) {

    }

    @FXML
    void guardarGenero(ActionEvent event) {

    }

    @FXML
    void guardarLibro(ActionEvent event) {

    }

    @FXML
    void guardarNacionalidad(ActionEvent event) {

    }

    @FXML
    void modificarAutor(ActionEvent event) {

    }

    @FXML
    void modificarGenero(ActionEvent event) {

    }

    @FXML
    void modificarLibro(ActionEvent event) {

    }

    @FXML
    void modificarNacionalidad(ActionEvent event) {

    }

    @FXML
    void seleccionarAutor(MouseEvent event) {

    }

    @FXML
    void seleccionarAutoria(MouseEvent event) {

    }

    @FXML
    void seleccionarGenero(MouseEvent event) {

    }

    @FXML
    void seleccionarLibro(MouseEvent event) {

    }

    @FXML
    void initialize() {
    	
    	/**
    	 * Leeremos de MongoDB todo y lo pasaremos a la DB relacional con Hibernate. MUY IMPORTANTE
    	 */
    	
    	// Obtengo dos listas de objetos desacoplados o detached/transient, es decir no gestionadas por hibernate 
//    	List<Autores> autores = mongoDAO.getAutores();
//    	List<Libros> libros = mongoDAO.getLibros();
//    	
//    	System.out.println(autores);
//    	System.out.println(libros);
//    	
//    	// con esto persistimos los objetos en hibernate (mySQL)
//    	hibernateDAO.anadirAutores(autores);
//    	hibernateDAO.anadirLibros(libros);
    	
    	
    	// Tap Genero
    	this.colGeneroLibro.setCellValueFactory(new PropertyValueFactory("genericaNombre"));
    	cargarDatosGenerica(Generos.class,
    			Generos::getIdGenero,
    			Generos::getNombre,
    			this.tablaGeneros);
    	
    	
    	// Tap Nacionalidadades
    	this.colNacionalidad.setCellValueFactory(new PropertyValueFactory("genericaNombre"));
    	cargarDatosGenerica(Nacionalidades.class,
    			Nacionalidades::getIdNacionalidad,
    			Nacionalidades::getNombre,
    			this.tablaNacionalidades);
    	
    	// Tap Autores
    	this.colNombreAutor.setCellValueFactory(new PropertyValueFactory("nombre"));
    	this.colNacimientoAutor.setCellValueFactory(new PropertyValueFactory("nacimiento"));
    	this.colMuerteAutor.setCellValueFactory(new PropertyValueFactory("muerte"));
    	this.colNacionalidadAutor.setCellValueFactory(new PropertyValueFactory("nacionalidad"));
    	cargarDatosAutores(hibernateDAO.getAllAutoresConNacionalidad(), this.tablaAutores);
    	
    	// Tap Autorias
    	this.colNombreAutoria.setCellValueFactory(new PropertyValueFactory("nombre"));
    	this.colNacimientoAutoria.setCellValueFactory(new PropertyValueFactory("nacimiento"));
    	this.colMuerteAutoria.setCellValueFactory(new PropertyValueFactory("muerte"));
    	this.colNacionalidadAutoria.setCellValueFactory(new PropertyValueFactory("nacionalidad"));
    	
    }

    private void cargarDatosAutores(List<Autores> al, TableView<AutorDTOPropiedadesJavaFX> tablaDestino) {
    	List<AutorDTOPropiedadesJavaFX> alJFX = new ArrayList<AutorDTOPropiedadesJavaFX>();
    	for ( Autores item: al) {
    		AutorDTOPropiedadesJavaFX genericaJFX = new AutorDTOPropiedadesJavaFX(item.getIdAutor(), item.getNombre(), item.getNacionalidades().getNombre(), item.getNacimiento(),
    				item.getMuerte()!=null ? item.getMuerte() : 0);
    		alJFX.add(genericaJFX);
    	}
    	tablaDestino.setItems(FXCollections.observableArrayList(alJFX));
	}

	/***
    
    private void cargarDatosNacionalidades() {
    	List<GenericaDTOPropiedadesJavaFX> alJFX = new ArrayList<GenericaDTOPropiedadesJavaFX>();
    	for ( Nacionalidades item: hibernateDAO.getAll(Nacionalidades.class)) {
    		GenericaDTOPropiedadesJavaFX generoJFX = new GenericaDTOPropiedadesJavaFX(item.getIdNacionalidad(), item.getNombre());
    		alJFX.add(generoJFX);
    	}
    	this.tablaNacionalidades.setItems(FXCollections.observableArrayList(alJFX));
	}

	
	private void cargarDatosGeneros() {
		List<GenericaDTOPropiedadesJavaFX> algeneroJFX = new ArrayList<GenericaDTOPropiedadesJavaFX>();
    	for (Generos genero: hibernateDAO.getAll(Generos.class)) {
    		GenericaDTOPropiedadesJavaFX generoJFX = new GenericaDTOPropiedadesJavaFX(genero.getIdGenero(), genero.getNombre());
    		algeneroJFX.add(generoJFX);
    	}
    	this.tablaGeneros.setItems(FXCollections.observableArrayList(algeneroJFX));
	}
    **/
	/**
	 * Este método sustituye a los dos anteriores
	 * @param <T>
	 * @param entityClass
	 * @param idExtractor
	 * @param nombreExtractor
	 * @param tablaDestino
	 */

	private <T> void cargarDatosGenerica(Class<T> entityClass,
			Function<T, Number> idExtractor, // admita Integer, Long, int ...
			Function<T, String> nombreExtractor,
			TableView<GenericaDTOPropiedadesJavaFX> tablaDestino) {
		
		List<GenericaDTOPropiedadesJavaFX> algeneroJFX = new ArrayList<GenericaDTOPropiedadesJavaFX>();
    	for (T item: hibernateDAO.getAll(entityClass)) {
    		System.out.println("a");
    		GenericaDTOPropiedadesJavaFX genericaJFX = new GenericaDTOPropiedadesJavaFX((int)idExtractor.apply(item), nombreExtractor.apply(item));
    		algeneroJFX.add(genericaJFX);
    	}
    	tablaDestino.setItems(FXCollections.observableArrayList(algeneroJFX));
	}
	
	
	
    
    
}
