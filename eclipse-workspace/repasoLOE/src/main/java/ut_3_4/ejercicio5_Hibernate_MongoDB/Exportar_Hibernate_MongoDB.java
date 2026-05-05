package ut_3_4.ejercicio5_Hibernate_MongoDB;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class Exportar_Hibernate_MongoDB {
	private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	private static MongoClient mongoClient = MongoClients.create("mongodb://10.196.55.168:27017");
	
	public static void main(String[] args) {
		// Conexion con MongoDB
		MongoDatabase db = mongoClient.getDatabase("academiaMusica");
		MongoCollection<Document> collection = db.getCollection("matriculas");

		// Conexión con MySQL WorkBench a través de hibernate
		Session sesion = sessionFactory.openSession();

		// Guardar los IDs de los alumnos de MongoDB para no duplicar alumnos
		Set<String> alumnosMongo = new HashSet<>();
		for (Document docAlumno: collection.find()) {
			alumnosMongo.add(docAlumno.getString("id"));
		}

		// Tomo los alumnos de Hibernate
		List<Alumnos> listaAlumnos = sesion.createQuery("FROM Alumnos", Alumnos.class).list();
		Document docAlumno = new Document(); 
		Document docCurso = new Document();
		List<Document> listaCursos = new ArrayList<>();
		List<Document> listaPagos = new ArrayList<>();
		
		for(Alumnos alumno: listaAlumnos) {
			// Proceso solo aquellos cuyo id no está en MongoDB, es decir en el set alumnosMongo
			if (!alumnosMongo.contains(alumno.getId())) {
				docAlumno = new Document()
						.append("id", alumno.getId())
						.append("nombre", alumno.getNombre())
						.append("iban", alumno.getIban())
						.append("banco", alumno.getBanco());
				listaCursos = new ArrayList<>();
				
				for (Object objMatricula: alumno.getMatriculases()) {
					Matriculas matricula = (Matriculas) objMatricula;
					Cursos curso = matricula.getCursos();

					docCurso = new Document()
							.append("curso_id", curso.getId())
							.append("denominacion", curso.getDenominacion())
							.append("cuota_mensual", curso.getCuotaMensual().doubleValue());

							listaPagos = new ArrayList<>();		
					for (Object objPago: alumno.getPagoses()) {
						Pagos pago = (Pagos) objPago;

						Document docPago = new Document()
								.append("mes", pago.getMes())
								.append("estado", pago.isEstado())
								.append("importe", pago.getImporte().doubleValue());

						listaPagos.add(docPago);
					}
				}
				docCurso.append("pagos", listaPagos);	
				listaCursos.add(docCurso);	
			}
			docAlumno.append("cursos", listaCursos);
			collection.insertOne(docAlumno);
		}
		sesion.close();
		mongoClient.close();
	}
}
