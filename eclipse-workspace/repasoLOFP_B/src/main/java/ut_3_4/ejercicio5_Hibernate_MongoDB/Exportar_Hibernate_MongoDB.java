package ut_3_4.ejercicio5_Hibernate_MongoDB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

	private static SessionFactory sf = new Configuration().configure().buildSessionFactory();
    private static MongoClient mongoClient = MongoClients.create("mongodb://10.196.55.168:27017");

    public static void main(String[] args) {
        // MongoDB
        MongoDatabase database = mongoClient.getDatabase("academiaMusica");
        MongoCollection<Document> col = database.getCollection("matriculas");

        //Hibernate 
        Session sesion = sf.openSession();
        
        //Para evitar duplicados en MongoDB
        Set<String> alumnosMongo = new HashSet<>();
        for(Document doc: col.find()) {
        	alumnosMongo.add(doc.getString("id"));
        }
        
        List<Alumnos> listaAlumnos = sesion.createQuery("FROM Alumnos", Alumnos.class).list();
        Document docAlumno = new Document();
        Document docCurso = new Document();
        Document docPago = new Document();
        
        List<Document> listaCursos = new ArrayList<>();
        List<Document> listaPagos = new ArrayList<>();
        
        for (Alumnos alumno: listaAlumnos) {
        	//Proceso solo aquellos cuyo id no está en MongoDB, es decir en alumnosMongo
        	if (!alumnosMongo.contains(alumno.getId())) {
        		docAlumno = new Document()
        				.append("id", alumno.getId())
        				.append("nombre", alumno.getNombre())
        				.append("iban", alumno.getIban())
        				.append("banco", alumno.getBanco());
        		listaCursos = new ArrayList<>();
        		for(Object objMatricula: alumno.getMatriculases()) {
        			Matriculas matricula = (Matriculas) objMatricula;
        			Cursos curso =  matricula.getCursos();

        			docCurso = new Document()
        					.append("curso_id", curso.getId())
        					.append("denominacion", curso.getDenominacion())
        					.append("cuota_mensual", curso.getCuotaMensual().doubleValue());
        			listaPagos = new ArrayList<>();
        			for(Object objPago: curso.getPagoses()) {
        				Pagos pago = (Pagos) objPago;

        				docPago = new Document()
        						.append("mes", pago.getMes())
        						.append("estado", pago.isEstado())
        						.append("importe", pago.getImporte().doubleValue());
        				listaPagos.add(docPago);
        			}
        			docCurso.append("pagos", listaPagos);
        			listaCursos.add(docCurso);
        		}
        		docAlumno.append("cursos", listaCursos);
        		col.insertOne(docAlumno);
        	}

        }
        sesion.close();
        mongoClient.close();
    }
}
