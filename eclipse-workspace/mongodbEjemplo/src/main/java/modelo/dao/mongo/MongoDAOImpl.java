package modelo.dao.mongo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import modelo.dto.hibernate.Autores;
import modelo.dto.hibernate.Generos;
import modelo.dto.hibernate.Libros;
import modelo.dto.hibernate.Nacionalidades;

public class MongoDAOImpl implements MongoDAO {
	private static MongoClient mongoClient = MongoClients.create("mongodb://10.196.55.168:27017");
	private static MongoDatabase db = mongoClient.getDatabase("Biblioteca_II");
	

	@Override
	public List<String> getGeneros() {
		
		TreeSet<String> generosOrdenados = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		for (String gen:  db.getCollection("libros").distinct("genero", String.class)) {
			if (gen!=null) {
				if (!gen.trim().isEmpty()) {
					generosOrdenados.add(gen.trim().toLowerCase());
				}
			}
		}
		return new ArrayList<String>(generosOrdenados);
	}


	@Override
	public List<Autores> getAutores() {
		List<Autores> lista = new ArrayList<Autores>();
		for( Document doc: db.getCollection("autores").find()) {
			lista.add(crearAutorDesdeDoc(doc));
		}
		return lista;
	}


	@Override
	public List<Libros> getLibros() {
		List<Libros> lista = new ArrayList<Libros>();
		for( Document doc: db.getCollection("libros").find()) {
			Libros l = new Libros();
			l.setTitulo(doc.getString("titulo"));
			// En mongo cada libro puede tener más de un genero o uno solo
			// pero en Hibernate solo tenemos 1 genero por libro
			// Cogemos si hay más de un genero el primero
			
			// Gestión de la propiedad generos que es de la clase Generos
			Object g = doc.get("genero");
			String generoAAsignar; 
			if (g instanceof String) {
				generoAAsignar = (String) g;
			}else {
				List<String> generoList = (List<String>) g;
				generoAAsignar = generoList.get(0);
//				generoAAsignar = ((List<String>) g).get(0);
			}
			Generos gen = new Generos();
			gen.setNombre(generoAAsignar);
			l.setGeneros(gen);

			//Gestión de la propiedad autoreses
			Object a = doc.get("autor");
			List<Autores> autoresHibernate = new ArrayList<Autores>();
			Document aDoc = new Document();
			if (a instanceof Document) {
				// embebido
				aDoc = (Document) a;
				autoresHibernate.add(crearAutorDesdeDoc(aDoc));
			}else if (a instanceof ObjectId) {
				ObjectId oid = (ObjectId) a;
				aDoc = db.getCollection("autores").find(eq("_id", oid)).first();
				autoresHibernate.add(crearAutorDesdeDoc(aDoc));
			}else if (a instanceof List) {
				// Puede ser una lista de Document o de objectId
				for(Object elem: (List<?>)a) {
					if (elem instanceof Document) {
						aDoc = (Document) elem; 
						autoresHibernate.add(crearAutorDesdeDoc(aDoc));
					}else if (elem instanceof ObjectId) {
						aDoc = db.getCollection("autores").find(eq("_id", elem)).first();
						autoresHibernate.add(crearAutorDesdeDoc(aDoc));
					}
				}
			}
			Set<Autores> autoresSet = new HashSet<>();
			for(Autores autorH: autoresHibernate) {
				autoresSet.add(autorH);
			}
			l.setAutoreses(autoresSet);
			//TODO falta
			lista.add(l);
		}
		return lista;
	}


	private Autores crearAutorDesdeDoc(Document doc) {
		
		Autores a = new Autores();
		a.setNombre(doc.getString("nombre"));
		a.setNacimiento(doc.getInteger("anio_nacimiento"));
		a.setMuerte(doc.getInteger("anio_muerte"));
		
		Nacionalidades n = new Nacionalidades();
		n.setNombre(doc.getString("nacionalidad"));
		a.setNacionalidades(n);
		
		//Los libros los rellenamos después (muchos a muchos)
		a.setLibroses(new HashSet<>());

		return a;
	}
}
