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
	private static SessionFactory sf = new Configuration().configure().buildSessionFactory();
	private static MongoClient mongoClient = MongoClients.create("mongodb://10.196.55.168:27017");
	
	public static void main(String[] args) {
		// MongoDB
		MongoDatabase database = mongoClient.getDatabase("academiaMusica");
		MongoCollection<Document> col = database.getCollection("matriculas");

		//Hibernate 
		Session sesion = sf.openSession();
		Transaction tx = sesion.beginTransaction();
		
		for(Document alumnoDoc: col.find()) {
			Alumnos alumno = new Alumnos(alumnoDoc.getString("id"), alumnoDoc.getString("nombre"), 
					alumnoDoc.getString("iban"), alumnoDoc.getString("banco"));
			sesion.save(alumno);
			
			List<Document> cursos = (List<Document>) alumnoDoc.get("cursos");
			for(Document cursoDoc: cursos) {
				// Busco el curso en la base de datos con Hibernate
				Cursos curso = (Cursos) sesion.createQuery("FROM Cursos c WHERE c.denominacion = :den")
					.setParameter("den", cursoDoc.getString("denominacion"))
					.uniqueResult();
				//No existe en la base de datos -> lo inserto
				if (curso==null) {
					curso = new Cursos(cursoDoc.getString("denominacion"),
							new BigDecimal( ((Number)cursoDoc.get("cuota_mensual")).doubleValue() ) );
					sesion.save(curso);
				}
				// Matricula del alumno en el curso
				Matriculas matricula = new Matriculas(alumno, curso);
				sesion.save(matricula);
				
				//Pagos de ese curso
				List<Document> pagos = (List<Document>) cursoDoc.get("pagos");
				for(Document pagoDoc: pagos) {
					Pagos pago = new Pagos(alumno, curso, pagoDoc.getString("mes"), pagoDoc.getBoolean("estado"), 
							new BigDecimal(pagoDoc.getInteger("importe").doubleValue()));
					sesion.save(pago);
				}
			}
		}
		tx.commit();
		sesion.close();
		mongoClient.close();
	}	
}

