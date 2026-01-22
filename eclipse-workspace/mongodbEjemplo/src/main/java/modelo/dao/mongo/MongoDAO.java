package modelo.dao.mongo;

import java.util.List;

import org.bson.Document;

import modelo.dto.hibernate.Autores;
import modelo.dto.hibernate.Libros;

public interface MongoDAO {
	
	List<String> getGeneros();

	List<Autores> getAutores();

	List<Libros> getLibros();
	
//	Autores crearAutorDesdeDoc(Document aDoc);
}
