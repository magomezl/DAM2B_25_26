package modelo;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

/**
 * Ejercicio con métodos para hacer un CRUD MongoDB 
 * en el que deberíamos modificar lo hecho para ajustarlo a un proyecto MVC 
 */


public class Prueba {
	private static MongoDatabase db;
	private static MongoCollection librosCol, autoresCol; 
	
	public static void main(String[] args) {
		try (MongoClient mongoClient = MongoClients.create("mongodb://10.196.55.168:27017")){
			db = mongoClient.getDatabase("biblioteca");
			System.out.println("Conexión establecida con éxito");
			librosCol = db.getCollection("libros");
			autoresCol = db.getCollection("autores");
//			borrarDoc("L. Frank Baum");
//			anadirDoc();
//			anadirDocEmbebido();
//			anadirDocReferenciadoObjectId("L. Frank Baum");
//			anadirAutor();
		
//			anadirDocReferenciadoObjectId("L. Frank Baum");
			
//			mostrarPorGenero("novela");
//			modificarGenero("Fantasía", "Infantil");
			
			System.out.println("Autores con nacionalidad española " + autoresNacionalidad("Española"));
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		

	}

	private static int autoresNacionalidad(String nacionalidad) {
		Pattern patron = Pattern.compile("^" + Pattern.quote(nacionalidad) + "$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		return (int) autoresCol.countDocuments(regex("nacionalidad", patron));
	}

	private static void anadirDocReferenciadoObjectId(String autor) {
		ArrayList<String> personajes = new ArrayList<String>(Arrays.asList("Tom", "Philip", "Aliena"));
		ObjectId id = ((Document) autoresCol.find(eq("nombre", autor)).first()).getObjectId("_id");
		Document doc = new Document()
				.append("titulo", "Mago de Oz")
				.append("genero", "Fantasía")
				.append("personajes", personajes)
				.append("autor", id);
		librosCol.insertOne(doc);
		
	}

	private static void modificarGenero(String generoOld, String generoNew) {
		librosCol.updateMany(eq("genero", generoOld), set("genero", generoNew));
		
	}

	private static void mostrarPorGenero(String genero) {
		FindIterable<Document> resultados = librosCol.find(eq("genero", genero));
		for (Document doc: resultados) {
			System.out.println(doc);
		}
		
	}
	 
	private static void borrarDoc(String autor) {
		
		List<org.bson.conversions.Bson> filtros = new ArrayList<>();
		Pattern patron = Pattern.compile("^" + Pattern.quote(autor) + "$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

		filtros.add(regex("autor", patron));
		filtros.add(eq("autor.nombre", patron));
		
		ObjectId id = ((Document) autoresCol.find(eq("nombre", autor)).first()).getObjectId("_id");
		System.out.println(id);
		filtros.add(eq("autor", id));

		librosCol.deleteMany(or(filtros));
	}

	/**
	 * Añadimos libro con autor como String
	 */
	private static void anadirDocEmbebido() {
		ArrayList<String> personajes = new ArrayList<String>(Arrays.asList("tía Em", "Hombre de hojalata", "Espantapajaros", "León"));
		Document doc = new Document()
				.append("titulo", "El maravilloso Mago de Oz")
				.append("autor",
						new Document()
							.append("nombre", "L. Frank Baum")
							.append("nacionalidad", "Americano")
							.append("nacimiento", "1856-05-15"))
				.append("genero", "Fantasía")
				.append("personajes", personajes);
		librosCol.insertOne(doc);
	}
	
	
	/**
	 * Añadir Autor.
	 */
	private static void anadirAutor() {
		//TODO no permitir duplicados
		Document doc = new Document()
		.append("nombre", "L. Frank Baum")
		.append("nacionalidad", "Americano")
		.append("nacimiento", "1856-05-15");
		autoresCol.insertOne(doc);
	}
	
	
	
	/**
	 * Añadimos libro con autor embebido
	 */

	private static void anadirDoc() {
		ArrayList<String> personajes = new ArrayList<String>(Arrays.asList("tía Em", "Hombre de hojalata", "Espantapajaros", "León"));
		Document doc = new Document()
				.append("titulo", "El maravilloso Mago de Oz")
				.append("autor", "L. Frank Baum")
				.append("genero", "Fantasía")
				.append("personajes", personajes);
		db.getCollection("libros").insertOne(doc);
	}
	
	//TODO añadir autor en coleccion autores 
	// añadimos un libro que referencie el autor por ObjectId
	// eliminamos todos los libros con ese autor ya este embebido, con el nombre, o referenciado
	
	// Mostrar todos los libros cuyo autor sea de una determinada nacionalidad y cuyo año de nacimiento sea posterior al que se pase
	// Modificar el método que ya tenemos Añadir autores no duplicados
	
	
 
	
	
	
}
