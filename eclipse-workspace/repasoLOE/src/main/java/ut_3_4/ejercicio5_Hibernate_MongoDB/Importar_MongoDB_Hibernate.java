package ut_3_4.ejercicio5_Hibernate_MongoDB;

import java.math.BigDecimal;
import java.util.List;

import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import clasesHibernate.Alumnos;
import clasesHibernate.Cursos;
import clasesHibernate.Matriculas;
import clasesHibernate.Pagos;

public class Importar_MongoDB_Hibernate {
	private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	private static MongoClient mongoClient = MongoClients.create("mongodb://10.196.55.168:27017");
	
	public static void main(String[] args) {
		// Conexion con MongoDB
		MongoDatabase db = mongoClient.getDatabase("academiaMusica");
		MongoCollection<Document> collection = db.getCollection("matriculas");
		
		// Conexión con MySQL WorkBench a través de hibernate
		Session sesion = sessionFactory.openSession();
		Transaction tx = sesion.beginTransaction();
		
		for (Document docAlumno: collection.find()) {
			Alumnos alumno = new Alumnos(docAlumno.getString("nombre"), docAlumno.getString("id"), docAlumno.getString("iban"), docAlumno.getString("banco"));
			sesion.save(alumno);
			
			List<Document> cursos = (List<Document>) docAlumno.get("cursos");
			for(Document docCurso:  cursos) {
				// Buscamos en la DB si existe un curso con la denominación 
				Cursos curso = (Cursos) sesion.createQuery("FROM Cursos c WHERE c.denominacion = :den")
					.setParameter("den", docCurso.getString("denominacion"))
					.uniqueResult();
				// Si no existe el curso, lo guardo en la DB MySQLWorkBech a través de Hibernate
				if (curso==null) {
					curso = new Cursos(docCurso.getString("denominacion"), new BigDecimal(((Number)docCurso.get("cuota_mensual")).doubleValue()));
					sesion.save(curso);
				}
				//Matriculamos al alumno del curso
				Matriculas matricula = new Matriculas(alumno, curso);
				sesion.save(matricula);
				
				//Pagos del curso 
				List<Document> pagos = (List<Document>) docCurso.get("pagos");
				for(Document docPago:  pagos) {
					Pagos pago = new Pagos(alumno, curso, docPago.getString("mes"), docPago.getBoolean("estado"), new BigDecimal(((Number)docPago.get("importe")).doubleValue()));
					sesion.save(pago);		
				}
			}
		}
		tx.commit();
		sesion.close();
		mongoClient.close();
	}
}
