package ut_3.ejercicio4_XStream_Hibernate;

import java.io.InputStream;
import java.math.BigDecimal;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import clasesHibernate.Alumnos;
import clasesHibernate.Cursos;
import clasesHibernate.Matriculas;
import clasesHibernate.Pagos;

import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Academia;
import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Alumno;
import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Domiciliacion;
import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Pago;

public class Importar_XStream_Hibernate {
	private static SessionFactory sf = new Configuration().configure().buildSessionFactory(); 
	
	public static void main(String[] args) {
		// Cargar los datos contenidos en el documento xml a través del javabean de XStream
		XStream xs = new XStream(new DomDriver("UTF-8"));
		
		// IMPORTANTE. Damos permiso a XStream para que acceda a las clases que hemos creado
		xs.addPermission(AnyTypePermission.ANY);
		// Definimos las reglas de correspondencia entre las clases XStream y el fichero xml 
		xs.alias("academia", Academia.class);
		xs.alias("alumno", Alumno.class);
		xs.alias("domiciliacion", Domiciliacion.class);
		xs.alias("pago", Pago.class);
		
		xs.addImplicitCollection(Academia.class, "alumnos");
		
		xs.useAttributeFor(Alumno.class, "id");
		
		xs.useAttributeFor(Pago.class, "mes");
		xs.useAttributeFor(Pago.class, "estado");
		xs.useAttributeFor(Pago.class, "importe");
		
		InputStream is = Importar_XStream_Hibernate.class
				.getClassLoader()
				.getResourceAsStream("resources/Files/academia.xml");
		
		Academia academia = (Academia) xs.fromXML(is);
		
		Session sesion = sf.openSession();
		for (Alumno a: academia.getAlumno()) {
			Transaction tx = sesion.beginTransaction();
			Alumnos alumnoHibernate = new Alumnos(a.getId(), a.getNombre(), a.getDomiciliacion().getIban(), a.getDomiciliacion().getBanco());
			sesion.persist(alumnoHibernate);
			
			// Para no duplicar los cursos los buscamos en la DB
			Cursos cursoHibernate = (Cursos) sesion.createQuery("FROM Cursos c WHERE c.denominacion = :cursoName")
					.setParameter("cursoName", a.getCurso())
					.uniqueResult();
			if (cursoHibernate == null) {
				cursoHibernate = new Cursos(a.getCurso(), BigDecimal.valueOf(a.getCuota()));
				sesion.persist(cursoHibernate);
			}
			Matriculas matriculaHibernate = new Matriculas(alumnoHibernate, cursoHibernate);
			sesion.persist(matriculaHibernate);
			
			for (Pago p: a.getPagos()) {
				Pagos pagoHibernate = new Pagos(alumnoHibernate, cursoHibernate, p.getMes(), p.isPagado(), BigDecimal.valueOf(p.getImporte()));
				sesion.persist(pagoHibernate);
			}
			tx.commit();
		}
		sesion.close();
		sf.close();
	}
}
